/*
 * University of Texas at Austin
 * Barad Project, Jul 1, 2007
 */
#ifndef EXCEPTIONS_H
#define EXCEPTIONS_H

#include <stdexcept>
#include <string>

namespace barad {
	using std::runtime_error;
	using std::string;

	class BaradException : public runtime_error {
	public:
		BaradException() : runtime_error("An unexpected error occurred.") {}
		BaradException(string message) : runtime_error(message) {}
	};

	class BaradHomeNotFoundException : public BaradException {};
}

#endif // EXCEPTIONS_H