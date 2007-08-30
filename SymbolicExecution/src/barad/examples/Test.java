package barad.examples;

public class Test {	
	public static void main(String[] args) {
		String desc = "(I)V";
		System.out.println("BEFORE: " + desc);
		desc = modifyMethodDescriptor(desc);
		System.out.println("AFTER: " + desc);
	}
	
	private static String modifyMethodDescriptor(String desc) {
		StringBuilder resultBuilder = new StringBuilder();
		String[] names = null;
		int index = desc.indexOf('(');
		String temp = desc;
		if (index > -1) {
			resultBuilder.append('(');
			temp = temp.substring(1);
			names = temp.split("[)]"); 
		} else {
			names = new String[]{temp};
		}
		for (int i = 0; i < names.length; i++) {
			String[] classNames = names[i].split(";");
			for (String s: classNames) {
				resultBuilder.append(updateClassName(s));
			}
			if (index > -1) {
				resultBuilder.append(')');
				index = -1;
			}	
		}
		return resultBuilder.toString();
	}
	
	private static String updateClassName(String name) {
		String newName = name;
		if (name.equals("I")) {
			newName = "barad/symboliclibrary/integer/ICONST;";
		} else if (false) {
			//add conditions fo all basic interfaces i.e DoubleInterface and so on
		} else if (!name.equals("") && !name.equals("V") && !name.equals("Z")) {
			newName = newName + ";";
		}
		return newName;
	}


}
