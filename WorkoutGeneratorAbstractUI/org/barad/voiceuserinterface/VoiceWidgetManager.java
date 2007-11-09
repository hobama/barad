package org.barad.voiceuserinterface;

import org.apache.log4j.Logger;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class VoiceWidgetManager {
	private static Logger log = Logger.getLogger(VoiceWidgetManager.class);
	private static VoiceWidgetManager voiceWidgetManager;
    private static Voice voice;
    private static VoiceManager voiceManager;

    public static VoiceWidgetManager getInstance() {
    	if (voiceWidgetManager == null) {
    		voiceWidgetManager = new VoiceWidgetManager();
    	}
    	return voiceWidgetManager;
    }
    
    protected VoiceWidgetManager() {
    	voiceManager = VoiceManager.getInstance();
    	voice = voiceManager.getVoice("kevin16"/*VOICE_NAME*/);
    	if (voice == null) {
    		String message = "Cannot find a voice named " + voice + ".  Please specify a different voice.";
    		log.error(message);
    		throw new IllegalArgumentException(message);
    	}
    	voice.allocate();
    }
    	
    public void speak(String phrase, float wordsPerMinute) {
    	float temp = voice.getRate();
    	voice.setRate(wordsPerMinute);
    	voice.speak(phrase);
    	voice.setRate(temp);
    }
      
    public String listen() {
    	return null;
    }
    
    @Override
    protected void finalize() {
    	voice.deallocate();
    }
}
















