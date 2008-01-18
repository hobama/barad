package barad.instrument.thread;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

import barad.instrument.instrumenter.SymbolicExecutionClassLoader;
import barad.symboliclibrary.ui.widgets.SymbolicComposite;

/**
 * This is a separate thread that performs symbolic execution
 * of the instrumented classes
 * @author svetoslavganov
 */
public class SymbolicExecutionThread extends Thread {
	private Logger log;
	private String className;
	private volatile Thread mainThread;
	private SymbolicExecutionClassLoader symbolicExecutionClassLoader;
	private static Class<?> symbolicComposite;
	private static Class<?> symbolicIntegerInterface;
	
	/**
	 * Creates an instance of this class
	 * @param mainThread The main program thread
	 */
	public SymbolicExecutionThread(Thread mainThread, SymbolicExecutionClassLoader symbolicExecutionClassLoader) {
		log = Logger.getLogger(getClass());
		this.mainThread = mainThread;
		this.symbolicExecutionClassLoader = symbolicExecutionClassLoader; 
		symbolicComposite = symbolicExecutionClassLoader.loadClass("barad.symboliclibrary.ui.widgets.SymbolicComposite", true);
		symbolicIntegerInterface = symbolicExecutionClassLoader.loadClass("barad.symboliclibrary.integers.IntegerInterface", true);
		log.info("Symbolic execution thread spawned...");
	}

	public void executeSymbolically(String className) {
		this.className = className.replace('/', '.');
		this.interrupt();
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
				Thread.sleep(100000);
			} catch (InterruptedException ie) {
				/*do nothing*/
			}
			if (className != null) {
				//load the class into JVM
				Class<?> clazz = symbolicExecutionClassLoader.loadClass(className);
				//instantiate and execute event handler if the class is SymbolicComposite		
				if (symbolicComposite.isAssignableFrom(clazz)) {
					executeSymbolically(clazz);
				}
				//reset
				className = null;
				log.info("Symbolic execution of " + className + " completed.");
			}
		}
		log.info("Symbolic execution thread terminated");
	}
		
	/**
	 * Execurte symbolically the class i.e. istantiate and call executeEventHandlerMethods method
	 * @param clazz The SymbolicConmposite to be executed symbolically
	 */
	private void executeSymbolically(Class<?> clazz) {
		log.info("Executing symbolically " + className + "...");
		try {
			Constructor<?> constructor = clazz.getDeclaredConstructor(new Class<?>[]{symbolicComposite, symbolicIntegerInterface});
			SymbolicComposite instance = (SymbolicComposite)constructor.newInstance(new Object[]{null, null});
			//execute the event handlers
			instance.executeEventHandlerMethods();
		} catch(InstantiationException ie) {
			log.error("Error duringinstantiation of a testes class", ie);	
		} catch (NoSuchMethodException nsme) {
			log.error("Error duringinstantiation of a testes class", nsme);
		} catch (IllegalAccessException iae) {
			log.error("Error duringinstantiation of a testes class", iae);
		} catch (InvocationTargetException ite) {
			log.error("Unexpected error during the symbolic execution of class: " + className, ite);
		}
		log.info("Symbolic execution completed");
	}
}
