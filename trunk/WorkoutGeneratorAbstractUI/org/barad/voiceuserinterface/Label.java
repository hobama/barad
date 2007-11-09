package org.barad.voiceuserinterface;

public class Label extends VoiceWidget{
	private VoiceWidget widget;
	private String text;
	private float wordsPerMinute;
	
	public void interact() {
		VoiceWidgetManager voiceWidgetManager = VoiceWidgetManager.getInstance();
		if (text != null && !text.equals("")) {
			voiceWidgetManager.speak(text, wordsPerMinute);
		}
		if (widget != null && widget.isEnabled()) {
			widget.interact();
		}
	}
	
	public float getWordsPerMinute() {
		return wordsPerMinute;
	}
	
	public void setWordsPerMinute(float wordsPerMinute) {
		this.wordsPerMinute = wordsPerMinute;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public VoiceWidget getWidget() {
		return widget;
	}
	
	public void setWidget(VoiceWidget widget) {
		this.widget = widget;
	}
}
