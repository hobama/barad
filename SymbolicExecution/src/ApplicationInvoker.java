import java.net.MalformedURLException;
import java.net.URL;  
import java.net.URLClassLoader;  
import java.lang.reflect.Method;  
import java.lang.reflect.Modifier;  
   
public class ApplicationInvoker {    
    public static void main(String[] args) {
    	String className = args[0];
    	String[] arguments = new String[args.length];
        URL[] classpathURLs = getClasspathURLs();  
        MyClassLoader loader = new MyClassLoader(classpathURLs);
        try {  
            Class<?> clazz = loader.loadClass(className);  
            Method main = clazz.getMethod("main", new Class[]{String[].class});  
            int modifiers = main.getModifiers();  
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {  
                main.invoke(null, new Object[]{arguments});  
            } else {  
                throw new NoSuchMethodException();  
            }  
        } catch (Exception e) {  
            System.err.println("Error running class " + className + e.getMessage());  
            e.printStackTrace();  
        }  
    }  
   
    private static URL[] getClasspathURLs() {  
        URL[] urls = new URL[1];  
        String value = "file:///C|/workspace/JarGenerator/jargenerator/JarGenerator.clas";
        try {
        	URL url = new URL(value);
        	urls[0] = url;
        } catch (MalformedURLException mue) {
        	System.out.println("The URL: " + value + " is malformed");
        	mue.printStackTrace();
        }  
        return urls;  
    }
    
    public static class MyClassLoader extends URLClassLoader {
    	public MyClassLoader(URL[] urls) {
    		super(urls);
    	}
    	 
 		public Class<?> loadClass(String name, boolean resolve) {
 			Class clazz = null;
 			try {
 				clazz = super.loadClass(name, resolve);
 			} catch (ClassNotFoundException cnfe) {
 			 	System.out.println(cnfe);
 			}
 	        return clazz;
 	    }

 	    public Class getClass(byte[] data) {
 	       return super.defineClass(null, data, 0, data.length);
 	    } 
 	}
 }  