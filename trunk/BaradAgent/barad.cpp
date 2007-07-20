/*
 * University of Texas at Austin
 * Barad Project, Jul 1, 2007
 */
#include "barad.h"

using namespace barad;
using namespace std;

static jvmtiEnv* jvmti = NULL;
static string baradAgentClass = "edu/utexas/barad/agent/AgentMain";
static string swtDisplayClass = "Lorg/eclipse/swt/widgets/Display;";
static jobject swtClassLoaderGlobalRef = NULL;

static void JNICALL callbackVMInit(jvmtiEnv* jvmti, JNIEnv* jni, jthread thread) {
	LOG_DEBUG("Received VMInit event.\n");

	// Start the client, which will register the client in the RMI registry.
	jclass clazz = jni->FindClass(baradAgentClass.c_str());
	Utils::handleJNIException(jni);

	if (clazz != NULL) {
		jmethodID method = jni->GetStaticMethodID(clazz, "main", "([Ljava/lang/String;)V"); // MethodNotFoundException?
		Utils::handleJNIException(jni);
		if (method != NULL) {
			// Pass the process ID and process file name to the agent.
			string processID;
			string fileName;
#ifdef WIN32
			processID = Utils::toString(GetCurrentProcessId());
			
			TCHAR szPath[MAX_PATH];
			if (!GetModuleFileName(NULL, szPath, MAX_PATH)) {
				LOG_ERROR("Couldn't get module file name for current process, error=" + Utils::toString(GetLastError()) + "\n");
				fileName = "Unknown";
			} else {
				fileName = szPath;
			}
#else
			processID = -1;
			fileName = "Unknown";
#endif // WIN32			
			
			// Start the agent.
			jstring fileNameJString = jni->NewStringUTF(fileName.c_str());
			jstring processIDJString = jni->NewStringUTF(processID.c_str());
			jobjectArray args = jni->NewObjectArray(2, jni->FindClass("java/lang/String"), fileNameJString);
			jni->SetObjectArrayElement(args, 1, processIDJString);

			jni->CallStaticVoidMethod(clazz, method, args);
			if (jni->ExceptionCheck()) {
				Utils::handleJNIException(jni);
				LOG_ERROR("Couldn't start Barad agent: exception occurred in AgentMain#main(String[]).\n");
				return;
			}

			jni->DeleteLocalRef(fileNameJString);
			jni->DeleteLocalRef(processIDJString);
		} else {
			LOG_ERROR("Couldn't start Barad agent: AgentMain#main(String[]) method not found.\n");
			return;
		}
	} else {
		LOG_ERROR("Couldn't start Barad agent: AgentMain class not found.\n");
		return;
	}

	LOG_INFO("Barad agent started.\n");
}

static void JNICALL callbackVMStart(jvmtiEnv* jvmti, JNIEnv* jni) {
	LOG_DEBUG("Received VMStart event.\n");
}

static void JNICALL callbackVMDeath(jvmtiEnv* jvmti, JNIEnv* jni) {	
	LOG_DEBUG("Received VMDeath event.\n");
}

JNIEXPORT jint JNICALL Agent_OnLoad(JavaVM* jvm, char* options, void* reserved) {
	jint retval = jvm->GetEnv((void **) &jvmti, JVMTI_VERSION_1_0);
	if (retval != JNI_OK || jvmti == NULL) {
		/* 
		 * This means that the VM was unable to obtain this version of the
		 * JVMTI interface, this is a fatal error.
		 */
		Utils::fatalError("Unable to access JVMTI, is your J2SE a 1.5 or newer version?  JNIEnv's GetEnv() returned" + Utils::toString(retval));
	}

	jvmtiEventCallbacks callbacks;
	(void) memset(&callbacks, 0, sizeof(callbacks));
    callbacks.VMInit = &callbackVMInit;
	callbacks.VMStart = &callbackVMStart;
	callbacks.VMDeath = &callbackVMDeath;

    jvmtiError error = jvmti->SetEventCallbacks(&callbacks, (jint) sizeof(callbacks));
	Utils::checkJVMTIError(jvmti, error, "An error occurred setting callbacks -- this will be ignored.");

	/* 
	 * At first the only initial events we are interested in are VM
     * initialization, VM death, and Class File Loads.
     * Once the VM is initialized we will request more events.
     */
    error = jvmti->SetEventNotificationMode(JVMTI_ENABLE, JVMTI_EVENT_VM_INIT, (jthread) NULL);
	error = jvmti->SetEventNotificationMode(JVMTI_ENABLE, JVMTI_EVENT_VM_START, (jthread) NULL);
    error = jvmti->SetEventNotificationMode(JVMTI_ENABLE, JVMTI_EVENT_VM_DEATH, (jthread) NULL);
	Utils::checkJVMTIError(jvmti, error, "An error occurred setting event notification -- this will be ignored.");

	// Set the classpath.
	try {
		LOG_DEBUG("BARAD_HOME=" + Utils::getBaradHome() + "\n");
		Utils::addFileToBootClasspath(jvmti, "baradagent.jar");	
		Utils::addFileToBootClasspath(jvmti, "log4j-1.2.14.jar");
	} catch(BaradHomeNotFoundException) {
		LOG_ERROR("BARAD_HOME environment variable not found.\n");
	}

	LOG_INFO("Barad agent loaded.\n");

	return JNI_OK;
}

/*
 * Agent_OnUnload: This is called immediately before the shared library is
 * unloaded. This is the last code executed.
 */
JNIEXPORT void JNICALL Agent_OnUnload(JavaVM* jvm) {
	// Make sure all malloc/calloc/strdup space is freed.
}

JNIEXPORT jobject JNICALL Java_edu_utexas_barad_agent_AgentUtils_getSWTClassLoaderImpl(JNIEnv* jni, jclass clazz) {
	// Check that JVMTI is initialized.
	if (jvmti == NULL) {
		jclass illegalStateException = jni->FindClass("java/lang/IllegalStateException");
		if (jni->ThrowNew(illegalStateException, "JVMTI is not initialized.") < 0) {
			Utils::fatalError("Couldn't throw exception: JVMTI is not initialized.");
		}
	}
	
	// Do we have a global reference cached already?  If so, just return the object we have.
	if (swtClassLoaderGlobalRef != NULL) {
		LOG_DEBUG("Found cached global reference to SWT classloader.\n");
		return swtClassLoaderGlobalRef;
	}

	jobject classLoader = NULL;
	jint count = 0;
	jclass* classes = NULL;
	jvmtiError error = jvmti->GetLoadedClasses(&count, &classes);
	Utils::checkJVMTIError(jvmti, error, "GetLoadedClasses()");

	bool foundSignature = false;
	for (int i = 0; i < count; i++) {
		char* signature_ptr = NULL;
		error = jvmti->GetClassSignature(classes[i], &signature_ptr, NULL);
		Utils::checkJVMTIError(jvmti, error, "GetClassSignature()");

		if (signature_ptr != NULL) {
			string signatureActual = signature_ptr;
			LOG_DEBUG("signature=" + signatureActual + "\n");

			if (signatureActual.compare(swtDisplayClass) == 0) {
				// We found it!
				error = jvmti->GetClassLoader(classes[i], &classLoader);
				Utils::checkJVMTIError(jvmti, error, "GetClassLoader()");
				Utils::deallocate(jvmti, signature_ptr);
				foundSignature = true;
				break;
			} else {
				Utils::deallocate(jvmti, signature_ptr);
			}
		}
	}

	Utils::deallocate(jvmti, classes);

	// Couldn't find the class, or it hasn't been loaded yet...
	if (!foundSignature) {
		jclass classNotFoundException = jni->FindClass("java/lang/ClassNotFoundException");
		if (jni->ThrowNew(classNotFoundException, swtDisplayClass.c_str()) < 0) {
			Utils::fatalError("Couldn't throw exception, signature=" + swtDisplayClass);
		}
	}

	// Cache the global reference.
	swtClassLoaderGlobalRef = jni->NewGlobalRef(classLoader);
	return swtClassLoaderGlobalRef;
}