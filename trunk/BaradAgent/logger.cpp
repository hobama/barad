/*
 * University of Texas at Austin
 * Barad Project, Jul 1, 2007
 */
#include "logger.h"

using namespace barad;
using namespace std;

string Logger::FILENAME = "barad-native.log";
Logger* Logger::pInstance = NULL;

Logger::Logger() {
	pFile = new ofstream(FILENAME.c_str(), ios_base::app);
}

Logger::~Logger() {
	if (pFile) {
		pFile->close();
	}
	delete(pFile);
}

Logger* Logger::getInstance() {
	if (pInstance == NULL) {
		pInstance = new Logger();
	}
	return pInstance;
}

void Logger::write(const string& message, const char* file, int line) const {
	if (pFile) {
		if (file) {
			*pFile << "[file=" << file;
			if (line > -1) {
				*pFile << ", line=" << line << "] ";
			} else {
				*pFile << "] ";
			}
		}
		*pFile << message;		
		pFile->flush();
	} else {
		if (file) {
			cerr << "[file=" << file;
			if (line > -1) {
				cerr << ", line=" << line << "] ";
			} else {
				cerr << "] ";
			}
		}
		cerr << message;
		cerr.flush();
	}
}

void Logger::debug(const string& message, const char* file, int line) const {
#ifdef _DEBUG
	write(getTimestamp());
	write(" DEBUG: ");
	write(message, file, line);
#endif // _DEBUG
}

void Logger::info(const string& message, const char* file, int line) const {
	write(getTimestamp());
	write(" INFO: ");
#ifdef _DEBUG
	write(message, file, line);
#else
	write(message, NULL, -1);
#endif // _DEBUG
}

void Logger::warn(const string& message, const char* file, int line) const {
	write(getTimestamp());
	write(" WARN: ");
#ifdef _DEBUG
	write(message, file, line);
#else
	write(message, NULL, -1);
#endif // _DEBUG
}

void Logger::error(const string& message, const char* file, int line) const {
	write(getTimestamp());
	write(" ERROR: ");
#ifdef _DEBUG
	write(message, file, line);
#else
	write(message, NULL, -1);
#endif // _DEBUG
}

void Logger::fatal(const string& message, const char* file, int line) const {
	write(getTimestamp());
	write(" FATAL: ");
#ifdef _DEBUG
	write(message, file, line);
#else
	write(message, NULL, -1);
#endif // _DEBUG
}