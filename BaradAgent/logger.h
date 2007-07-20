/*
 * University of Texas at Austin
 * Barad Project, Jul 1, 2007
 */
#ifndef LOGGER_H
#define LOGGER_H

#include <iostream>
#include <sstream>
#include <fstream>
#include <string>
#include <ctime>

namespace barad {
	using namespace std;

	class Logger {
	private:
		static string FILENAME;
		static Logger* pInstance;
		ofstream* pFile;

		Logger();
		Logger(const Logger&) {}
		Logger& operator= (const Logger&) {
			return *this;
		}		
		void write(const string&, const char* file = 0, int line = -1) const;
		inline static string getTimestamp() {
			time_t t;
			time(&t);
			string timestamp;

#ifdef WIN32
			char timebuf[26];
			if (ctime_s(timebuf, 26, &t)) {
				cerr << "Couldn't get local time." << endl;
			} else {
				timebuf[24] = '\0'; // Remove newline.
				timestamp = timebuf;
			}
#else
			// Do nothing.
#endif // WIN32
			return timestamp;
		}

	public:
		virtual ~Logger();
		static Logger* getInstance();

		/**
		 * \brief Log a message string using the debug level.
		 */
		void debug(const string& message, const char* file = NULL, int line = -1) const;

		/**
		 * \brief Log a message string using the info level.
		 */
		void info(const string& message, const char* file = NULL, int line = -1) const;

		/**
		 * \brief Log a message string using the warning level.
		 */
		void warn(const string& message, const char* file = NULL, int line = -1) const;

		/**
		 * \brief Log a message string using the error level.
		 */
		void error(const string& message, const char* file = NULL, int line = -1) const;

		void fatal(const string& message, const char* file = NULL, int line = -1) const;		
	};
}

#define LOG_DEBUG(message) { \
	barad::Logger::getInstance()->debug(message, __FILE__, __LINE__);}

#define LOG_INFO(message) { \
	barad::Logger::getInstance()->info(message, __FILE__, __LINE__);}

#define LOG_WARN(message) { \
	barad::Logger::getInstance()->warn(message, __FILE__, __LINE__);}

#define LOG_ERROR(message) { \
	barad::Logger::getInstance()->error(message, __FILE__, __LINE__);}

#define LOG_FATAL(message) { \
	barad::Logger::getInstance()->fatal(message, __FILE__, __LINE__);}

#endif // LOGGER_H