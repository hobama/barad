package edu.utexas.barad.agent.exceptions;

/**
 * University of Texas at Austin
 * Barad Project, Aug 7, 2007
 */
public class GenerateTestCasesException extends AgentRuntimeException {
    public GenerateTestCasesException() {
        super();
    }

    public GenerateTestCasesException(String message) {
        super(message);
    }

    public GenerateTestCasesException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenerateTestCasesException(Throwable cause) {
        super(cause);
    }
}