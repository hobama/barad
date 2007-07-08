package edu.utexas.barad.common.swt;

import java.io.Serializable;
import java.rmi.dgc.VMID;

/**
 * University of Texas at Austin
 * Barad Project, Jul 5, 2007
 */
public class GUID implements Serializable {
    private VMID vmid = new VMID();

    public GUID() {
        // Empty implementation.
    }

    public boolean equals(Object object) {
        if (object instanceof GUID) {
            GUID another = (GUID) object;
            return another.vmid.equals(vmid);
        }
        return false;
    }

    public int hashCode() {
        return vmid.hashCode();
    }

    public String toString() {
        return vmid.toString();
    }
}