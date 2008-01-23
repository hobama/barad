package barad.instrument.instrumenter;

import junit.framework.Assert;

import org.junit.Test;

import util.BaseTestSuite;

public class Test_Method_00 extends BaseTestSuite {
	@Test
	public void testEquals00() {
		Method lhs = new Method();
		lhs.setClazz("clazz");
		lhs.setDesc("desc");
		lhs.setName("name");
		Method rhs = new Method();
		rhs.setClazz("clazz");
		rhs.setDesc("desc");
		rhs.setName("name");
		Assert.assertTrue(lhs.equals(rhs));
	}
	
	@Test
	public void testEquals01() {
		Method lhs = new Method();
		lhs.setClazz("blah");
		lhs.setDesc("desc");
		lhs.setName("name");
		Method rhs = new Method();
		rhs.setClazz("clazz");
		rhs.setDesc("desc");
		rhs.setName("name");
		Assert.assertFalse(lhs.equals(rhs));
	}
	
	@Test
	public void testEquals02() {
		Method lhs = new Method();
		lhs.setClazz("clazz");
		lhs.setDesc("blah");
		lhs.setName("name");
		Method rhs = new Method();
		rhs.setClazz("clazz");
		rhs.setDesc("desc");
		rhs.setName("name");
		Assert.assertFalse(lhs.equals(rhs));
	}
	
	@Test
	public void testEquals03() {
		Method lhs = new Method();
		lhs.setClazz("clazz");
		lhs.setDesc("desc");
		lhs.setName("blah");
		Method rhs = new Method();
		rhs.setClazz("clazz");
		rhs.setDesc("desc");
		rhs.setName("name");
		Assert.assertFalse(lhs.equals(rhs));
	}
	
	@Test
	public void testEquals04() {
		Method lhs = new Method();
		lhs.setDesc("desc");
		lhs.setName("name");
		Method rhs = new Method();
		rhs.setDesc("desc");
		rhs.setName("name");
		Assert.assertTrue(lhs.equals(rhs));
	}
	
	@Test
	public void testEquals05() {
		Method lhs = new Method();
		lhs.setName("name");
		Method rhs = new Method();
		rhs.setName("name");
		Assert.assertTrue(lhs.equals(rhs));
	}
	
	@Test
	public void testEquals06() {
		Method lhs = new Method();
		Method rhs = new Method();
		Assert.assertTrue(lhs.equals(rhs));
	}
	
	@Test
	public void testEquals07() {
		Method lhs = new Method();
		lhs.setDesc("desc");
		lhs.setName("name");
		Method rhs = new Method();
		rhs.setClazz("clazz");
		rhs.setDesc("desc");
		rhs.setName("name");
		Assert.assertFalse(lhs.equals(rhs));
	}
	
	@Test
	public void testEquals08() {
		Method lhs = new Method();
		lhs.setDesc("desc");
		lhs.setName("name");
		Method rhs = new Method();
		rhs.setName("name");
		Assert.assertFalse(lhs.equals(rhs));
	}
}
