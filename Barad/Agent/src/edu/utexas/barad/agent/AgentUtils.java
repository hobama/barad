package edu.utexas.barad.agent;

import edu.utexas.barad.agent.exceptions.AgentRuntimeException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * University of Texas at Austin
 * Barad Project, Jul 4, 2007
 */
public class AgentUtils {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private static ClassLoader swtClassLoader;

    private static native ClassLoader getSWTClassLoaderImpl() throws ClassNotFoundException;

    public static ClassLoader getSWTClassLoader() throws ClassNotFoundException {
        if (swtClassLoader != null) {
            return swtClassLoader;
        }

        ClassLoader classLoader = getSWTClassLoaderImpl();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        swtClassLoader = classLoader;
        return swtClassLoader;
    }

    public static Class swtClassForName(String swtClassName) {
        try {
            ClassLoader classLoader = getSWTClassLoader();
            return Class.forName(swtClassName, true, classLoader);
        } catch (ClassNotFoundException e) {
            throw new AgentRuntimeException(e);
        }
    }

    public static String getStackTrace(Throwable t) {
        if (t == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        t.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }

    private AgentUtils() {
        // Private constructor.
    }
}