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

namespace barad {
	using namespace std;

	class Logger {
	private:
		static Logger* pInstance;
		ofstream* pFile;

		Logger();
		Logger(const Logger&) {}
		Logger& operator= (const Logger&) {
			return *this;
		}		
		void write(const string&, const char* file = 0, int line = -1) const;

	public:
		virtual ~Logger();
		static Logger* getInstance();

		/**
		 * \brief Log a message string using a debug level.
		 */
		void debug(const string& message, const char* file = NULL, int line = -1) const;

		/**
		 * \brief Log a message string using a warning level.
		 */
		void warn(const string& message, const char* file = NULL, int line = -1) const;

		/**
		 * \brief Log a message string using an error level.
		 */
		void error(const string& message, const char* file = NULL, int line = -1) const;

		void fatal(const string& message, const char* file = NULL, int line = -1) const;

		template <class T> static string toString(const T& t) {
			ostringstream oss;
			oss << t;
			return oss.str();
		}
	};
}

#define LOG_DEBUG(message) { \
	barad::Logger::getInstance()->debug(message, __FILE__, __LINE__);}

#define LOG_WARN(message) { \
	barad::Logger::getInstance()->warn(message, __FILE__, __LINE__);}

#define LOG_ERROR(message) { \
	barad::Logger::getInstance()->error(message, __FILE__, __LINE__);}

#define LOG_FATAL(message) { \
	barad::Logger::getInstance()->fatal(message, __FILE__, __LINE__);}

#endif // LOGGER_H