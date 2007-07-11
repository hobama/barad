package edu.utexas.barad.studio.exceptions;

/**
 * University of Texas at Austin
 * Barad Project, Jul 7, 2007
 *
 * @author Chip Killmar (ckillmar@mail.utexas.edu)
 */
public class StudioRuntimeException extends RuntimeException {
    public StudioRuntimeException() {
        super();
    }

    public StudioRuntimeException(String message) {
        super(message);
    }

    public StudioRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudioRuntimeException(Throwable cause) {
        super(cause);
    }
}