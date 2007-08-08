package edu.utexas.barad.agent.swt.tester;

import edu.utexas.barad.agent.swt.Robot;
import edu.utexas.barad.agent.swt.proxy.widgets.DisplayProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.WidgetProxy;
import edu.utexas.barad.agent.swt.proxy.SWTProxyFactory;
import edu.utexas.barad.agent.proxy.IProxyInvocationHandler;
import edu.utexas.barad.agent.AgentUtils;
import edu.utexas.barad.common.Cache;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * University of Texas at Austin
 * Barad Project, Jul 26, 2007
 */
public class WidgetTesterFactory implements WidgetTester.Factory {
    private static final Logger logger = Logger.getLogger(WidgetTesterFactory.class);
    private static WidgetTesterFactory Default;

    private static Object DefaultLock = new Object();

    public static WidgetTesterFactory getDefault() {
        synchronized (DefaultLock) {
            if (Default == null)
                Default = new WidgetTesterFactory(Robot.getDefault());
            return Default;
        }
    }

    /**
     * The name of the framework's core tester package.
     *
     * @see #isCore(String)
     * @see #packages
     */
    public static final String CORE_PACKAGE = WidgetTester.class.getPackage().getName();

    /**
     * A {@link java.util.List} of package names to be searched for {@link WidgetTester} {@link Class}es. The
     * core framework's {@link WidgetTester}s' package, abbot.swt.tester, is always included and
     * cannot be removed/excluded.
     *
     * @see #CORE_PACKAGE
     */
    private final List packages = Collections.synchronizedList(Arrays.asList(CORE_PACKAGE));

    /**
     * A cache of {@link WidgetTester}s that can be looked up by {@link WidgetProxy} {@link Class}.
     */
    private final Cache<Class, WidgetTester> testers = new Cache<Class, WidgetTester>() {
        protected WidgetTester newValue(Class widgetClass) {
            return createTester(widgetClass);
        }
    };

    /**
     * A cache of {@link WidgetTester} {@link Class}es that can be looked up by {@link WidgetProxy}
     * {@link Class}.
     */
    private final Cache<Class, Class> classes = new Cache<Class, Class>() {
        protected Class newValue(Class widgetClass) {
            return findTesterClass(widgetClass);
        }
    };

    private final Robot swtRobot;

    public WidgetTesterFactory(Robot swtRobot) {
        this.swtRobot = swtRobot;
    }

    /**
     * Adds a package name to the list of packages to be searched for {@link WidgetTester}s
     *
     * @param packageName
     * @return <code>true</code> if the packageName was added, <code>false</code> otherwise
     * @see #packages
     * @see #findTesterClass(Class)
     */
    public synchronized boolean addPackage(String packageName) {
        checkPackageName(packageName);
        return packages.add(packageName);
    }

    /**
     * Removes a package name from the list of packages to be searched for {@link WidgetTester}s
     *
     * @param packageName
     * @return <code>true</code> if the packageName was removed, <code>false</code> otherwise
     * @see #packages
     * @see #findTesterClass(Class)
     */
    public synchronized boolean removePackage(String packageName) {
        checkPackageName(packageName);
        return packages.remove(packageName);
    }

    private void checkPackageName(String packageName) {
        if (packageName == null)
            throw new IllegalArgumentException("packageName cannot be null");
        // TODO Validate package name.
        if (isCore(packageName))
            throw new IllegalArgumentException("cannot add or remove core package");
    }

    /**
     * Returns a {@link WidgetTester} for the specified {@link WidgetProxy} {@link Class}.
     *
     * @param widgetClass
     * @return the {@link WidgetTester}
     */
    public synchronized WidgetTester getTester(Class widgetClass) {
        SWTProxyFactory proxyFactory = SWTProxyFactory.getInstance();
        if (proxyFactory.isProxyClass(widgetClass)) {
            widgetClass = proxyFactory.getActualClass(widgetClass);
        }
        checkWidgetClass(widgetClass);
        return testers.get(widgetClass);
    }

    /**
     * Returns a {@link WidgetTester} for the specified {@link WidgetProxy} {@link Class}.
     *
     * @param widget the {@link WidgetProxy} for which a {@link WidgetTester} is wanted
     * @return a {@link WidgetTester}
     */
    public WidgetTester getTester(WidgetProxy widget) {
        checkWidget(widget);
        IProxyInvocationHandler invocationHandler = (IProxyInvocationHandler) Proxy.getInvocationHandler(widget);
        Object actualInstance = invocationHandler.getActualInstance();
        return getTester(actualInstance.getClass());
    }

    public synchronized void setTester(Class widgetClass, WidgetTester tester) {
        SWTProxyFactory proxyFactory = SWTProxyFactory.getInstance();
        if (proxyFactory.isProxyClass(widgetClass)) {
            widgetClass = proxyFactory.getActualClass(widgetClass);
        }
        checkWidgetClass(widgetClass);
        checkWidgetTester(tester);
        testers.put(widgetClass, tester);
    }

    /**
     * Returns <code>true</code> if the specified package name is the framework's core package
     * name, <code>false</code> otherwise.
     *
     * @param packageName
     * @return <code>true</code> if packageName is the framework's core package name,
     *         <code>false</code> otherwise.
     */
    private boolean isCore(String packageName) {
        return CORE_PACKAGE.equals(packageName);
    }

    private void checkWidget(WidgetProxy widget) {
        if (widget == null)
            throw new IllegalArgumentException("widget cannot be null");
        checkDisplay(widget.getDisplay());
        // if (widget.isDisposed())
        // throw new IllegalArgumentException("widget is disposed: " + widget);
    }

    private void checkWidgetClass(Class clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz cannot be null");
        }
        Class widgetClass = AgentUtils.swtClassForName(SWTProxyFactory.ORG_ECLIPSE_SWT_WIDGETS_WIDGET);
        if (!widgetClass.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("invalid widget class: " + clazz);
        }
    }

    private void checkWidgetTester(WidgetTester tester) {
        if (tester == null)
            throw new IllegalArgumentException("tester cannot be null");
        checkDisplay(tester.getDisplay());
    }

    private void checkDisplay(DisplayProxy display) {
        if (display == null) {
            throw new IllegalArgumentException("display cannot be null");
        }
        if (!display.equals(swtRobot.getDisplay())) {
            throw new IllegalArgumentException("invalid display");
        }
    }

    /**
     * @param widgetClass
     * @return a {@link WidgetTester}
     * @see #testers
     */
    private WidgetTester createTester(Class widgetClass) {
        Class testerClass = getTesterClass(widgetClass);
        if (testerClass != null) {
            Constructor constructor = getConstructor(
                    testerClass,
                    new Class[]{Robot.class});
            if (constructor != null)
                return newInstance(constructor, new Object[]{swtRobot});
        }
        return null;
    }

    private Constructor getConstructor(Class testerClass, Class[] parameterTypes) {
        try {
            return testerClass.getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            logger.warn(e);
        }
        return null;
    }

    private WidgetTester newInstance(Constructor constructor, Object[] arguments) {
        try {
            return (WidgetTester) constructor.newInstance(arguments);
        } catch (IllegalArgumentException e) {
            logger.warn(e);
        } catch (InstantiationException e) {
            logger.warn(e);
        } catch (IllegalAccessException e) {
            logger.warn(e);
        } catch (InvocationTargetException e) {
            logger.warn(e);
        }
        return null;
    }

    private Class getTesterClass(Class widgetClass) {
        checkWidgetClass(widgetClass);
        return classes.get(widgetClass);
    }

    /**
     * @param widgetClass
     * @return the {@link WidgetTester} {@link Class} (or <code>null</code> if there isn't one)
     * @see #classes
     */
    private Class findTesterClass(Class widgetClass) {
        String testerClassName = widgetClass.getSimpleName() + "Tester";
        for (Iterator iterator = packages.iterator(); iterator.hasNext();) {
            String packageName = (String) iterator.next();
            String fullTesterClassName = packageName + "." + testerClassName;
            Class testerClass = null;
            if (isCore(packageName))
                testerClass = resolveClass(fullTesterClassName, null);
            else
                testerClass = resolveClass(fullTesterClassName, widgetClass.getClassLoader());
            if (testerClass != null)
                return testerClass;
        }

        // Try superclass.
        if (widgetClass != WidgetProxy.class)
            return findTesterClass(widgetClass.getSuperclass());

        return null;
    }

    private Class resolveClass(String fullClassName, ClassLoader classLoader) {
        try {
            if (classLoader == null)
                return Class.forName(fullClassName);
            return Class.forName(fullClassName, true, classLoader);
        } catch (ClassNotFoundException e) {
            // Exception ignored. Empty block intended.
        }
        return null;
    }
}