package edu.utexas.barad.agent.exceptions;

/**
 * University of Texas at Austin
 * Barad Project, Jul 26, 2007
 */
public class ActionFailedException extends AgentRuntimeException {
    public ActionFailedException() {
        super();
    }

    public ActionFailedException(String message) {
        super(message);
    }

    public ActionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionFailedException(Throwable cause) {
        super(cause);
    }
}