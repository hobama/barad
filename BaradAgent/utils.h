/*
 * University of Texas at Austin
 * Barad Project, Jul 1, 2007
 */
#ifndef UTILS_H
#define UTILS_H

#include "exceptions.h"
#include "windows.h"

#include <string>
#include <jni.h>
#include <jvmti.h>

namespace barad {
	using std::string;

	class Utils {
	private:
		static string BARAD_HOME_VARIABLE_NAME;

	public:
		/** 
		 * \brief Logs an error and exits.
		 */
		static void fatalError(const string& message, int exitCode = 1);

		/**
		 * \brief Every JVMTI interface returns an error code, which should be checked to avoid any cascading errors down the line.
		 * The interface GetErrorName() returns the actual enumeration constant
		 * name, making the error messages much easier to understand.
		 */
		static void checkJVMTIError(jvmtiEnv* jvmti, jvmtiError error, const string& message = "");

		/**
		 * \brief All memory allocated by JVMTI must be freed by the JVMTI Deallocate interface.
		 */
		static void deallocate(jvmtiEnv* jvmti, void* ptr);

		/**
		 * \brief Allocation of JVMTI managed memory.
		 */
		static void* allocate(jvmtiEnv* jvmti, jint len);		

		static string getBaradHome();

		static void addFileToBootClasspath(jvmtiEnv* jvmti, const string& fileName);

		static void handleJNIException(JNIEnv* jni);

		template <class T> static string toString(const T& t) {
			ostringstream oss;
			oss << t;
			return oss.str();
		}
	private:
		Utils() {}		
	};
}

#endif UTILS_H