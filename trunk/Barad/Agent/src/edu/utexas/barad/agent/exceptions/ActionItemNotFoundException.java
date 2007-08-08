package edu.utexas.barad.agent.exceptions;

/**
 * University of Texas at Austin
 * Barad Project, Jul 26, 2007
 */
public class ActionItemNotFoundException extends AgentRuntimeException {
    public ActionItemNotFoundException() {
        super();
    }

    public ActionItemNotFoundException(String message) {
        super(message);
    }

    public ActionItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionItemNotFoundException(Throwable cause) {
        super(cause);
    }
}