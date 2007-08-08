/*
 * University of Texas at Austin
 * Barad Project, Aug 1, 2007
 */
#include "messageboxhelper.h"

const ATOM DIALOG_CLASS_ATOM = (ATOM) 32770;

const char BUTTON_CLASS_NAME[] = "Button";
const char STATIC_CLASS_NAME[] = "Static";

const char OK_TEXT[] = "OK";
const char CANCEL_TEXT[] = "Cancel";
const char YES_TEXT[] = "&Yes";
const char NO_TEXT[] = "&No";
const char ABORT_TEXT[] = "&Abort";
const char RETRY_TEXT[] = "&Retry";
const char IGNORE_TEXT[] = "&Ignore";

using namespace barad;
using namespace std;

BOOL CALLBACK EnumMessageBoxButtonsProc(HWND hWndChild, LPARAM lParam) {
	MessageBoxHelper* pMessageBoxHelper = reinterpret_cast<MessageBoxHelper*> (lParam);

	int length = GetWindowTextLength(hWndChild);
	char* pText = new char[length + 1];
	GetWindowText(hWndChild, pText, length + 1);

	char* pClassName = new char[7];
	GetClassName(hWndChild, pClassName, 7);
	if (strcmp(pClassName, STATIC_CLASS_NAME) == 0) {
		pMessageBoxHelper->staticText = pText;
	} else if (strcmp(pClassName, BUTTON_CLASS_NAME) == 0) {	
		if (strcmp(pText, OK_TEXT) == 0) {
			pMessageBoxHelper->hWndOKButton = hWndChild;
		}
		if (strcmp(pText, CANCEL_TEXT) == 0) {
			pMessageBoxHelper->hWndCancelButton = hWndChild;
		}
		if (strcmp(pText, YES_TEXT) == 0) {
			pMessageBoxHelper->hWndYesButton = hWndChild;
		}
		if (strcmp(pText, NO_TEXT) == 0) {
			pMessageBoxHelper->hWndNoButton = hWndChild;
		}
		if (strcmp(pText, ABORT_TEXT) == 0) {
			pMessageBoxHelper->hWndAbortButton = hWndChild;
		}
		if (strcmp(pText, RETRY_TEXT) == 0) {
			pMessageBoxHelper->hWndRetryButton = hWndChild;
		}
		if (strcmp(pText, IGNORE_TEXT) == 0) {
			pMessageBoxHelper->hWndIgnoreButton = hWndChild;
		}		
	}

	delete[] pText;
	delete[] pClassName;	

	return TRUE;
}

BOOL CALLBACK EnumChildProc(HWND hWndChild, LPARAM lParam) {	
	WINDOWINFO info;
	GetWindowInfo(hWndChild, &info);

	bool modal = (info.dwStyle & DS_MODALFRAME) != 0;
	MessageBoxHelper* pMessageBoxHelper = reinterpret_cast<MessageBoxHelper*> (lParam);
	if (modal && info.atomWindowType == DIALOG_CLASS_ATOM && GetParent(hWndChild) == pMessageBoxHelper->hWndOwner) {
		pMessageBoxHelper->hWndMessageBox = hWndChild;
		EnumChildWindows(hWndChild, EnumMessageBoxButtonsProc, lParam);
		return FALSE;
	}
	return TRUE;
}

JNIEXPORT void JNICALL Java_edu_utexas_barad_agent_swt_widgets_MessageBoxHelper_clickButton(JNIEnv* jni, jclass clazz, jint handle) {
	HWND hWndButton = reinterpret_cast<HWND> (handle);
	if (hWndButton) {
		SendMessage(hWndButton, WM_LBUTTONDOWN, 0, 0);
		SendMessage(hWndButton, WM_LBUTTONUP, 0, 0);
	}
}

JNIEXPORT jobject JNICALL Java_edu_utexas_barad_agent_swt_widgets_MessageBoxHelper_newInstance(JNIEnv* jni, jclass clazz, jint ownerHandle) {
	MessageBoxHelper messageBoxHelper;
	messageBoxHelper.hWndOwner = reinterpret_cast<HWND> (ownerHandle);
	LOG_DEBUG("Attempt to create new instance of MessageBoxHelper, ownerHandle=" + Utils::toString(ownerHandle) + "\n");
	if (messageBoxHelper.hWndOwner == NULL) {		
		LOG_DEBUG("Returning null because owner handle is null.\n");
		return NULL;
	}

	HWND hWndDesktop = GetDesktopWindow();
	EnumChildWindows(hWndDesktop, EnumChildProc, reinterpret_cast<LPARAM> (&messageBoxHelper));

	if (messageBoxHelper.hWndMessageBox != NULL) {
		jmethodID constructorID = jni->GetMethodID(clazz, "<init>", "(IIIIIIIIILjava/lang/String;)V");
		if (jni->ExceptionCheck()) {
			Utils::handleJNIException(jni);
			LOG_ERROR("Couldn't find MessageBoxHelper constructor method ID.\n");
			return NULL;
		}
		
		jstring staticText = jni->NewStringUTF(messageBoxHelper.staticText.c_str());
		if (jni->ExceptionCheck()) {
			Utils::handleJNIException(jni);
			LOG_ERROR("Couldn't create jstring for static text.\n");
			return NULL;
		}

		jobject newInstance = jni->NewObject(clazz, constructorID, messageBoxHelper.hWndOwner, messageBoxHelper.hWndMessageBox, messageBoxHelper.hWndOKButton, messageBoxHelper.hWndCancelButton, messageBoxHelper.hWndYesButton, messageBoxHelper.hWndNoButton, messageBoxHelper.hWndAbortButton, messageBoxHelper.hWndRetryButton, messageBoxHelper.hWndIgnoreButton, staticText);
		if (jni->ExceptionCheck()) {
			Utils::handleJNIException(jni);
			LOG_ERROR("Couldn't create new instance of MessageBoxHelper.\n");
			return NULL;
		}

		jni->ReleaseStringUTFChars(staticText, messageBoxHelper.staticText.c_str());
		if (jni->ExceptionCheck()) {
			Utils::handleJNIException(jni);
			LOG_ERROR("Couldn't release jstring for static text.\n");
			return NULL;
		}

		LOG_DEBUG("Returning new MessageBoxHelper instance, handle=" + Utils::toString(messageBoxHelper.hWndMessageBox) + "\n");
		return newInstance;
	}

	LOG_DEBUG("MessageBox doesn't exist for owner handle, handle=" + Utils::toString(ownerHandle) + ".\n");
	return NULL;
}