package edu.utexas.barad.agent.exceptions;

import edu.utexas.barad.agent.AgentUtils;

/**
 * University of Texas at Austin
 * Barad Project, Jul 4, 2007 
 */
public class AgentException extends Exception {
    public AgentException() {
        super();
    }

    public AgentException(String message) {
        super(message);
    }

    public AgentException(String message, Throwable cause) {
        super(message + AgentUtils.LINE_SEPARATOR + AgentUtils.getStackTrace(cause));
    }

    public AgentException(Throwable cause) {
        super(AgentUtils.getStackTrace(cause));
    }
}