package org.barad.voiceuserinterface.test;

import org.barad.voiceuserinterface.Label;
import org.junit.Test;

public class Test_Label_00 extends BaseTestCase {
 
	@Test
	public void test_entireLabel_00() throws Exception {
		Label label1 = new Label();
		label1.setWordsPerMinute(200f);
		label1.setText("Svetoslav");
		Label label2 = new Label();
		label2.setWordsPerMinute(300f);
		label2.setText("Ganov");
		label1.setWidget(label2);
		label1.interact();
	}
}
