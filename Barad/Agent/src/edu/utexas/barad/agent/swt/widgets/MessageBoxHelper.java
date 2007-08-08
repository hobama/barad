package edu.utexas.barad.agent.swt.widgets;

import edu.utexas.barad.agent.swt.proxy.widgets.ShellProxy;
import edu.utexas.barad.common.swt.WidgetID;

/**
 * University of Texas at Austin
 * Barad Project, Aug 5, 2007
 */
public final class MessageBoxHelper {
    private int ownerHandle;
    private int messageBoxHandle;

    private int okButtonHandle;
    private int cancelButtonHandle;
    private int yesButtonHandle;
    private int noButtonHandle;
    private int abortButtonHandle;
    private int retryButtonHandle;
    private int ignoreButtonHandle;

    private String staticText;

    private WidgetID parentID;

    public static MessageBoxHelper newInstance(ShellProxy shell, WidgetID parentID) {
        MessageBoxHelper newInstance = newInstance(shell.__fieldGethandle());
        if (newInstance != null) {
            newInstance.setParentID(parentID);
        }
        return newInstance;
    }

    public void clickButton(Button button) {
        switch (button) {
            case OK:
                if (okButtonHandle == 0) {
                    throw new IllegalStateException("OK button doesn't exist.");
                }
                clickButton(okButtonHandle);
                break;
            case CANCEL:
                if (cancelButtonHandle == 0) {
                    throw new IllegalStateException("Cancel button doesn't exist.");
                }
                clickButton(cancelButtonHandle);
                break;
            case YES:
                if (yesButtonHandle == 0) {
                    throw new IllegalStateException("Yes button doesn't exist.");
                }
                clickButton(yesButtonHandle);
                break;
            case NO:
                if (noButtonHandle == 0) {
                    throw new IllegalStateException("No button doesn't exist.");
                }
                clickButton(noButtonHandle);
                break;
            case ABORT:
                if (abortButtonHandle == 0) {
                    throw new IllegalStateException("Abort button doesn't exist.");
                }
                clickButton(abortButtonHandle);
                break;
            case RETRY:
                if (retryButtonHandle == 0) {
                    throw new IllegalStateException("Retry button doesn't exist.");
                }
                clickButton(retryButtonHandle);
                break;
            case IGNORE:
                if (ignoreButtonHandle == 0) {
                    throw new IllegalStateException("Ignore button doesn't exist.");
                }
                clickButton(ignoreButtonHandle);
                break;
            default:
                throw new RuntimeException("Unknown button: " + button);
        }
    }

    public boolean hasOKButton() {
        return okButtonHandle != 0;
    }

    public boolean hasCancelButton() {
        return cancelButtonHandle != 0;
    }

    public boolean hasYesButton() {
        return yesButtonHandle != 0;
    }

    public boolean hasNoButton() {
        return noButtonHandle != 0;
    }

    public boolean hasAbortButton() {
        return abortButtonHandle != 0;
    }

    public boolean hasRetryButton() {
        return retryButtonHandle != 0;
    }

    public boolean hasIgnoreButton() {
        return ignoreButtonHandle != 0;
    }

    public String getStaticText() {
        return staticText;
    }

    public int getOwnerHandle() {
        return ownerHandle;
    }

    public void setOwnerHandle(int ownerHandle) {
        this.ownerHandle = ownerHandle;
    }

    public int getMessageBoxHandle() {
        return messageBoxHandle;
    }

    public void setMessageBoxHandle(int messageBoxHandle) {
        this.messageBoxHandle = messageBoxHandle;
    }

    public int getOkButtonHandle() {
        return okButtonHandle;
    }

    public void setOkButtonHandle(int okButtonHandle) {
        this.okButtonHandle = okButtonHandle;
    }

    public int getCancelButtonHandle() {
        return cancelButtonHandle;
    }

    public void setCancelButtonHandle(int cancelButtonHandle) {
        this.cancelButtonHandle = cancelButtonHandle;
    }

    public int getYesButtonHandle() {
        return yesButtonHandle;
    }

    public void setYesButtonHandle(int yesButtonHandle) {
        this.yesButtonHandle = yesButtonHandle;
    }

    public int getNoButtonHandle() {
        return noButtonHandle;
    }

    public void setNoButtonHandle(int noButtonHandle) {
        this.noButtonHandle = noButtonHandle;
    }

    public int getAbortButtonHandle() {
        return abortButtonHandle;
    }

    public void setAbortButtonHandle(int abortButtonHandle) {
        this.abortButtonHandle = abortButtonHandle;
    }

    public int getRetryButtonHandle() {
        return retryButtonHandle;
    }

    public void setRetryButtonHandle(int retryButtonHandle) {
        this.retryButtonHandle = retryButtonHandle;
    }

    public int getIgnoreButtonHandle() {
        return ignoreButtonHandle;
    }

    public void setIgnoreButtonHandle(int ignoreButtonHandle) {
        this.ignoreButtonHandle = ignoreButtonHandle;
    }

    public WidgetID getParentID() {
        return parentID;
    }

    public void setParentID(WidgetID parentID) {
        this.parentID = parentID;
    }

    private static native void clickButton(int hWndButton);

    private static native MessageBoxHelper newInstance(int hWndOwner);

    MessageBoxHelper(int ownerHandle, int messageBoxHandle, int okButtonHandle, int cancelButtonHandle, int yesButtonHandle, int noButtonHandle, int abortButtonHandle, int retryButtonHandle, int ignoreButtonHandle, String staticText) {
        this.ownerHandle = ownerHandle;
        this.messageBoxHandle = messageBoxHandle;
        this.okButtonHandle = okButtonHandle;
        this.cancelButtonHandle = cancelButtonHandle;
        this.yesButtonHandle = yesButtonHandle;
        this.noButtonHandle = noButtonHandle;
        this.abortButtonHandle = abortButtonHandle;
        this.retryButtonHandle = retryButtonHandle;
        this.ignoreButtonHandle = ignoreButtonHandle;
        this.staticText = staticText;
    }

    @Override
    public int hashCode() {
        int hashCode = staticText != null ? staticText.hashCode() : 0;
        if (hasOKButton()) {
            hashCode += "OK".hashCode();
        }
        if (hasCancelButton()) {
            hashCode += "Cancel".hashCode();
        }
        if (hasYesButton()) {
            hashCode += "Yes".hashCode();
        }
        if (hasNoButton()) {
            hashCode += "No".hashCode();
        }
        if (hasAbortButton()) {
            hashCode += "Abort".hashCode();
        }
        if (hasRetryButton()) {
            hashCode += "Retry".hashCode();
        }
        if (hasIgnoreButton()) {
            hashCode += "Ignore".hashCode();
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof MessageBoxHelper) {
            MessageBoxHelper another = (MessageBoxHelper) object;
            if (another.hasOKButton() != this.hasOKButton()) {
                return false;
            }
            if (another.hasCancelButton() != this.hasCancelButton()) {
                return false;
            }
            if (another.hasYesButton() != this.hasYesButton()) {
                return false;
            }
            if (another.hasNoButton() != this.hasNoButton()) {
                return false;
            }
            if (another.hasAbortButton() != this.hasAbortButton()) {
                return false;
            }
            if (another.hasRetryButton() != this.hasRetryButton()) {
                return false;
            }
            if (another.hasIgnoreButton() != this.hasIgnoreButton()) {
                return false;
            }
            if (another.staticText == null && this.staticText != null) {
                return false;
            }
            if (another.staticText != null && this.staticText == null) {
                return false;
            }
            if (another.staticText != null && !another.staticText.equals(this.staticText)) {
                return false;
            }
            if (!another.parentID.equals(this.parentID)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "MessageBoxHelper{" +
                "ownerHandle=" + ownerHandle +
                ", messageBoxHandle=" + messageBoxHandle +
                ", okButtonHandle=" + okButtonHandle +
                ", cancelButtonHandle=" + cancelButtonHandle +
                ", yesButtonHandle=" + yesButtonHandle +
                ", noButtonHandle=" + noButtonHandle +
                ", abortButtonHandle=" + abortButtonHandle +
                ", retryButtonHandle=" + retryButtonHandle +
                ", ignoreButtonHandle=" + ignoreButtonHandle +
                ", staticText='" + staticText + '\'' +
                ", parentID=" + parentID +
                '}';
    }

    public static enum Button {
        OK("OK"),
        CANCEL("Cancel"),
        YES("Yes"),
        NO("No"),
        ABORT("Abort"),
        RETRY("Retry"),
        IGNORE("Ignore");

        private String displayName;

        Button(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        @Override
        public String toString() {
            return getDisplayName();

        }
    }
}