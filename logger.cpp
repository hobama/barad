/*
 * University of Texas at Austin
 * Barad Project, Jul 1, 2007
 */
#include "logger.h"

using namespace barad;
using namespace std;

Logger* Logger::pInstance = NULL;

Logger::Logger() {
	pFile = new ofstream("barad.log");
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
	write("DEBUG: ");
	write(message, file, line);
}

void Logger::warn(const string& message, const char* file, int line) const {
	write("WARN: ");
	write(message, file, line);
}

void Logger::error(const string& message, const char* file, int line) const {
	write("ERROR: ");
	write(message, file, line);
}

void Logger::fatal(const string& message, const char* file, int line) const {
	write("FATAL: ");
	write(message, file, line);
}