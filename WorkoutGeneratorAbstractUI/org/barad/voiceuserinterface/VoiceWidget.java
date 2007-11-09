package org.barad.voiceuserinterface;

public abstract class VoiceWidget {
	private int index;
	private boolean enabled;
	
	public VoiceWidget() {
		this.enabled = true;
		this.index = 0;
	}
	
	abstract public void interact();
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
