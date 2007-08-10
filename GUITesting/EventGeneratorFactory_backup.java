import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.eclipse.swt.widgets.Widget;

import eventgenerators.EventGenerator;

/**
 * Factory for event generators. Uses reflection to create a new instance.
 * @author svetoslavganov
 *
 */
public class EventGeneratorFactory_backup {

	private static Logger log = Util.getLog();
	
	/**
	 * Create a new instance of an EventGenerator class from its name.
	 * Checks if the class name is a valid one.
	 * @param className Name of an event generator class. 
	 * @param properties Additional properties used during event generation.
	 * @see Javadoc for event generators
	 * @return New event generator instance. Null if any exceprion occurs during
	 * instantiation or the class name is invalid.
	 */
	@SuppressWarnings("unchecked")
	public static EventGenerator newEventGeneatorInstance(String className, Widget widget, Properties properties) {
		if (Util.getEventGeneratorClassNames().contains(className)) {
			log.error(EventGeneratorFactory_backup.class, "There is no such event generator " + className);
			return null;
		}	
		EventGenerator eventGenerator = null;
		Class[] constructorArguments = new Class[] {Widget.class, Properties.class};
		Constructor<? extends EventGenerator> constructor;
		try {
			Class<? extends EventGenerator> clazz = (Class<? extends EventGenerator>)Class.forName(className);
			constructor = clazz.getConstructor(constructorArguments);
			eventGenerator = createEventGenerator(constructor, new Object[]{widget, properties});
		} catch (NoSuchMethodException nsme) {
			log.error(EventGeneratorFactory_backup.class, "Class constructor was not identified " + nsme);
		}
		catch (ClassNotFoundException cnfe) {
			log.error(EventGeneratorFactory_backup.class, "Class not found during event generator creation " + cnfe);
		}
		//if (TesterProperties.VERBOSE && eventGenerator != null) {
			log.error(EventGeneratorFactory_backup.class, "Generated " + eventGenerator);
		//}
		return eventGenerator;
	}
	
	/**
	 * Create a new instance from constructor and constructor arguments.
	 * @param constructor The construcor to be invoked.
	 * @param initargs Arguents fro the constructor.
	 * @return New event generator instance. Null if any exceprion occurs.
	 */
	private static EventGenerator createEventGenerator(Constructor<? extends EventGenerator> constructor, Object[] initargs) {
		EventGenerator eventGenerator = null;
		try {
			eventGenerator = constructor.newInstance(initargs);
		} catch (InstantiationException ie) {
			log.error(EventGeneratorFactory_backup.class, "Error during instantiation of an object " + ie);
		} catch (IllegalArgumentException iae) {
			log.error(EventGeneratorFactory_backup.class, "Invalid argument(s) during instantiation of an object " + iae);
		} catch (InvocationTargetException ite) {
			log.error(EventGeneratorFactory_backup.class, "Invalid argument(s) during instantiation of an object " + ite);
		} catch (IllegalAccessException iae) {
			log.error(EventGeneratorFactory_backup.class, "Illegal access during instantiation of an object " + iae);
		}
		return eventGenerator;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		EventGeneratorFactory_backup.log = log;
	}
	
	@SuppressWarnings("all")
	public static void main(String[] args) {
		EventGeneratorFactory_backup.setLog(new Logger(System.getProperty("user.dir") , "TEST.txt"));
		log.error(EventGeneratorFactory_backup.class, "TEST");
		EventGenerator eg = EventGeneratorFactory_backup.newEventGeneatorInstance("eventgenerators.ArmEventGenerator", null, null);
	}
}
