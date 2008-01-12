package barad.instrument.thread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;

import barad.symboliclibrary.path.Path;

/**
 * This is a separate thread that performs symbolic execution
 * of the instrumented classes
 * @author svetoslavganov
 */
public class SymbolicExecutionThread extends Thread {
	private Logger log;
	private String className;
	private byte[] instrumentedClass;
	private volatile Thread mainThread;
	private List<String> methodsToExecute;
	private SymbolicClassLoader symbolicClassLoader;

	/**
	 * @param className Name of the class to be symbolicaly executed
	 * @param classDefinitionAsByteArray The byte array with the 
	 * @param eventHandlerEventPairs A list of the methods which 
	 * should executed symbolically
	 * parameter to this method
	 */
	public void executeSymbolically(String className, byte[] instrumentedClass, List<String> methodsToExecute) {
		this.className = className.replace('/', '.');
		this.instrumentedClass = instrumentedClass;
		this.methodsToExecute = methodsToExecute;
		this.symbolicClassLoader = new SymbolicClassLoader();
		this.interrupt();
	}
	
	/**
	 * Creates an instance of this class
	 * @param mainThread The main program thread
	 */
	public SymbolicExecutionThread(Thread mainThread) {
		log = Logger.getLogger(getClass());
		this.mainThread = mainThread;
		log.info("Symbolic execution thread spawned...");
	}
	
	/**
	 * This thread runs until the main thread dies. It sleeps for
	 * 1000 milliseconds and if a class name, class definition and
	 * methods to execute are set executes symbolically all the
	 * specified methods
	 */
	@Override
	public void run() { 
		while (mainThread.isAlive()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
				/*do nothing*/
			}
			if (className != null && instrumentedClass != null && methodsToExecute != null) {
				log.info("Executing symbolically " + className + "...");
				Class<?> clazz = symbolicClassLoader.getClass(instrumentedClass, className);
				Object instance = getInstance(clazz);
				for (String method: methodsToExecute) {
					//set the invoked method
					Path.setExecutedMethod(method);
					//invoke the method
					invokeMethod(clazz, instance, method);
					//generate test cases
					Path.generateTestSuite();
				}
				className = null;
				instrumentedClass = null;
				methodsToExecute = null;
				log.info("Symbolic execution of " + className + " completed.");
			}
		}
		log.info("Symbolic execution thread terminated");
	}
		
	/**
	 * Instantiates on object of a given class
	 * @param clazz The class
	 * @return Instance of the class, null if any exception 
	 * is thrown during the instantiation
	 */
	private Object getInstance(Class clazz) {
		Object instance = null;
		try {
			instance = clazz.newInstance();
		} catch(InstantiationException ie) {
			log.error("Exception while instantiating an object of the instrumented class" + ie, ie);
		} catch (IllegalAccessException iae) {
			log.error("Exception while trying to access the instrumented class " + iae, iae);
		}
		return instance;
	}
		  
	/**
	 * Invoke a method on an instance of a specified class
	 * @param ownerClazz The class of the instance
	 * @param ownerInstance An instance of the class
	 * @param methodName The name of the method to be executed
	 * NOTE: The class is instrumented in such a way that all
	 * symbolic inputs needed for the tested method are created
	 * in the method we call here i.e. the methods we call here
	 * instantiate objects for the input parameters of methods
	 * under test and the call them. This delegation avoids use 
	 * of reflection
	 */
	private void invokeMethod(Class<?> ownerClazz, Object ownerInstance, String methodName) {
		try {
			Method method = ownerClazz.getDeclaredMethod(methodName, new Class[]{});
		    try {
		    	method.invoke(ownerInstance, new Object[]{});
		    } catch (InvocationTargetException ite) {
		    	log.error("Exception in the main method of the instrumented class " + ite, ite);
		    	
		    } catch (IllegalAccessException iae) {
		    	log.error("Inaccessibe method or incorrect parameters " + iae, iae);
		    }
		} catch (NoSuchMethodException nsme) {
			log.error("The instrumented class has no main method " + nsme, nsme);
		}
	}
	
	/**
	 * Custom class loader that provides public method for
	 * loading classes from a byte array definition and name
	 * @author svetoslavganov
	 */
	public static class SymbolicClassLoader extends ClassLoader {
		private Logger log = Logger.getLogger(getClass());
		
		/**
		 * Loads a class from class definition as byte array
		 * and class name
		 * @param name The name of the class
		 * @param resolve Flag if true the class is resolved
		 * @return The Class object associated with this class
		 */
		public Class<?> loadClass(String name, boolean resolve) {
			Class clazz = null;
			try {
				clazz = super.loadClass(name, resolve);
			} catch (ClassNotFoundException cnfe) {
				log.error("Error during class loading", cnfe);
			}
	        return clazz;
	     }

		/**
		 * Loads a class from class definition as byte array
		 * and class name
		 * @param classDefinition The class as a byte array
		 * @param className the name of the class
		 * @return The Class object associated with this class
		 */
	     public Class getClass(byte[] classDefinition, String className) {
	         Class clazz = super.defineClass(className, classDefinition, 0, classDefinition.length);
	         super.resolveClass(clazz);
	    	 return clazz;
	     } 
	}
}