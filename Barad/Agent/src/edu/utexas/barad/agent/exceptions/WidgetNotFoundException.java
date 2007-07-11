package edu.utexas.barad.agent.exceptions;

import edu.utexas.barad.common.swt.GUID;

/**
 * University of Texas at Austin
 * Barad Project, Jul 11, 2007
 */
public class WidgetNotFoundException extends AgentRuntimeException {
    private GUID guid;

    public WidgetNotFoundException(String message, GUID guid) {
        super(message);
        this.guid = guid;
    }

    public GUID getGuid() {
        return guid;
    }
}