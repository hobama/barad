/*
 * University of Texas at Austin
 * Barad Project, Jul 1, 2007
 */
#include "utils.h"
#include "logger.h"

using namespace barad;

namespace barad {
	string Utils::BARAD_HOME_VARIABLE_NAME = "BARAD_HOME";

	void Utils::fatalError(const string& message, int exitCode) {
		LOG_FATAL(message + "\n");
		exit(exitCode);
	}

	void Utils::checkJVMTIError(jvmtiEnv* jvmti, jvmtiError error, const string& message) {
		if (error != JVMTI_ERROR_NONE) {
			char* pErrorString;
			(void) jvmti->GetErrorName(error, &pErrorString);

			Logger* pLogger = Logger::getInstance();
			LOG_ERROR("JVMTI: " + Logger::toString(error) + (pErrorString == NULL ? "Unknown" : pErrorString) + message + "\n");
		}
	}

	void Utils::deallocate(jvmtiEnv* jvmti, void* ptr) {
		if (jvmti != NULL) {
			jvmtiError error = jvmti->Deallocate(reinterpret_cast<unsigned char*>(ptr));
			checkJVMTIError(jvmti, error, "Cannot deallocate memory.");
		}
	}

	void* Utils::allocate(jvmtiEnv* jvmti, jint len) {
		void* ptr;    
		jvmtiError error = jvmti->Allocate(len, reinterpret_cast<unsigned char**>(&ptr));
		checkJVMTIError(jvmti, error, "Cannot allocate memory");
		return ptr;
	}

	string Utils::getBaradHome() {
		static int BUFSIZE = 4096;
		string baradHome;

		char* pValue = (char*) malloc(BUFSIZE * sizeof(size_t));
		if (pValue == NULL) {
			fatalError("Out of memory.");
		}

		// Read the BARAD_HOME environment variable.
		int retval = GetEnvironmentVariable(BARAD_HOME_VARIABLE_NAME.c_str(), pValue, BUFSIZE);
		if (retval == 0) {
			int error = GetLastError();
			if (error == ERROR_ENVVAR_NOT_FOUND) {			
				throw BaradHomeNotFoundException();
			}
		} else if (BUFSIZE < retval) {
			pValue = (char*) realloc(pValue, retval * sizeof(size_t));   
			if (pValue == NULL) {
				fatalError("Out of memory.");
			}
			retval = GetEnvironmentVariable(BARAD_HOME_VARIABLE_NAME.c_str(), pValue, retval);
			if (!retval) {
				fatalError("GetEnvironmentVariable failed, GetLastError=" + Logger::toString(GetLastError()));
			}
		}

		if (pValue != NULL) {
			baradHome = pValue;
		}
		return baradHome;
	}

	void Utils::addFileToBootClasspath(jvmtiEnv* jvmti, const string& fileName) {
		string baradHome;
		try {
			baradHome = getBaradHome();
		} catch(BaradHomeNotFoundException) {
			fatalError("BARAD_HOME environment variable not found.");
		}
	   
		string fileSeparator;
	#ifdef WIN32
		fileSeparator = "\\";
	#else
		fileSeparator = "/";
	#endif
	    
		int fileNameMaxLength = baradHome.length() + fileName.length() * 2 + fileSeparator.length() * 2 + 4 /* "lib" NULL */;
		if (fileNameMaxLength > FILENAME_MAX + 1) {
			fatalError("Path to jar file is too long.");
		}
		
		string jarPath = baradHome + fileSeparator + "lib" + fileSeparator + fileName;
		jvmtiError error = jvmti->AddToBootstrapClassLoaderSearch(jarPath.c_str());
		checkJVMTIError(jvmti, error, "Cannot add to boot classpath.");
	}
}