package edu.utexas.barad.studio.exceptions;

/**
 * University of Texas at Austin
 * Barad Project, Jul 7, 2007
 *
 * @author Chip Killmar (ckillmar@mail.utexas.edu)
 */
public class StudioException extends Exception {
    public StudioException() {
        super();
    }

    public StudioException(String message) {
        super(message);
    }

    public StudioException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudioException(Throwable cause) {
        super(cause);
    }
}