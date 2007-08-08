/*
 * University of Texas at Austin
 * Barad Project, Aug 1, 2007
 */
#ifndef MESSAGEBOXHELPER_H
#define MESSAGEBOXHELPER_H

#include "logger.h"
#include "utils.h"
#include "edu_utexas_barad_agent_swt_widgets_MessageBoxHelper.h"
#include "windows.h"

#include <string>
#include <jni.h>
#include <jvmti.h>

namespace barad {
	using namespace std;

	class MessageBoxHelper {
	public:
		HWND hWndOwner;
		HWND hWndMessageBox;

		HWND hWndOKButton;
		HWND hWndCancelButton;
		HWND hWndYesButton;
		HWND hWndNoButton;
		HWND hWndRetryButton;
		HWND hWndAbortButton;
		HWND hWndIgnoreButton;

		string staticText;

		MessageBoxHelper() : hWndOwner(NULL), hWndMessageBox(NULL), hWndOKButton(NULL), hWndCancelButton(NULL), hWndYesButton(NULL), hWndNoButton(NULL), hWndRetryButton(NULL), hWndAbortButton(NULL), hWndIgnoreButton(NULL) {}
		~MessageBoxHelper() {}

	private:
		MessageBoxHelper(const MessageBoxHelper&) {}
		MessageBoxHelper& operator= (const MessageBoxHelper&) { return *this; }		
	};
}

#endif // MESSAGEBOXHELPER_H