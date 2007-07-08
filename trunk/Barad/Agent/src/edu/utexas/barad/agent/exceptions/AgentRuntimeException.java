package edu.utexas.barad.agent.exceptions;

import edu.utexas.barad.agent.AgentUtils;

/**
 * University of Texas at Austin
 * Barad Project, Jul 1, 2007 
 */
public class AgentRuntimeException extends RuntimeException {
    public AgentRuntimeException() {
        super();
    }

    public AgentRuntimeException(String message) {
        super(message);
    }

    public AgentRuntimeException(String message, Throwable cause) {
        super(message + AgentUtils.LINE_SEPARATOR + AgentUtils.getStackTrace(cause));
    }

    public AgentRuntimeException(Throwable cause) {
        super(AgentUtils.getStackTrace(cause));
    }
}