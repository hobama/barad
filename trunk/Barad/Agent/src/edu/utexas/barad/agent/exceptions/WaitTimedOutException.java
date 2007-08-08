package edu.utexas.barad.agent.exceptions;

/**
 * University of Texas at Austin
 * Barad Project, Jul 25, 2007
 */
public class WaitTimedOutException extends AgentRuntimeException {
    public WaitTimedOutException() {
        super();
    }

    public WaitTimedOutException(String message) {
        super(message);
    }

    public WaitTimedOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public WaitTimedOutException(Throwable cause) {
        super(cause);
    }
}