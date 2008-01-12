package barad.symboliclibrary.widgets;

import junit.framework.Assert;

import org.junit.Test;

import util.BaseTestSuite;
import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.ui.widgets.SymbolicCombo;
import barad.symboliclibrary.ui.widgets.SymbolicComposite;
import barad.symboliclibrary.ui.widgets.SymbolicText;

/**
 * This class tests all symbolic widgets
 * @author svetoslavganov
 *
 */
public class Test_SymbolicWidgets_00 extends BaseTestSuite {

	@Test
	public void test_getIndex00() throws Exception {
		SymbolicComposite symbolicComposite = new SymbolicComposite();
		SymbolicText text1 = new SymbolicText(symbolicComposite, new ICONST(1));
		SymbolicText text2 = new SymbolicText(symbolicComposite, new ICONST(1));
		Assert.assertEquals(0, text1.getIndex());
		Assert.assertEquals(1, text2.getIndex());
	}
	
	@Test
	@SuppressWarnings("unused")
	public void test_getChildren00() throws Exception {
		SymbolicComposite symbolicComposite = new SymbolicComposite();
		SymbolicText text = new SymbolicText(symbolicComposite, new ICONST(1));
		SymbolicCombo combo = new SymbolicCombo(symbolicComposite, new ICONST(1));
		Assert.assertEquals("org.eclipse.swt.widgets.Text", symbolicComposite.getChildren().get(0).getSWTClassEquivalent());
		Assert.assertEquals("org.eclipse.swt.widgets.Combo", symbolicComposite.getChildren().get(1).getSWTClassEquivalent());

	}
}
