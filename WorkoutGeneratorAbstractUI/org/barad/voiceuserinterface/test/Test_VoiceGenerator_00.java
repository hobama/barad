package org.barad.voiceuserinterface.test;

import org.barad.voiceuserinterface.VoiceWidgetManager;
import org.junit.Test;

public class Test_VoiceGenerator_00 extends BaseTestCase{
	@Test
	public void test_entireVoiceGenerator() throws Exception {
		VoiceWidgetManager voiceWidgetManager = VoiceWidgetManager.getInstance();
		voiceWidgetManager.speak("My name is Svetoslav", 200f);
	}
}
