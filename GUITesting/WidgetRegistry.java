import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.Map.Entry;

import org.eclipse.swt.internal.SWTEventListener;
import org.eclipse.swt.widgets.Widget;

/**
 * Class that stores in a WeakHashMap widgets and their the event handlers registered
 * for them 
 * 
 * @author svetoslavganov
 *
 */
public class WidgetRegistry {
	
	private static WeakHashMap<Widget, HashSet<SWTEventListener>> registry = new WeakHashMap<Widget, HashSet<SWTEventListener>>();
	
	/**
	 * Returns a linked list of all event listeners that are 
	 * registered for events of a SWT widget 
	 * @param widget The widget for which all registered event lsiteners are looked up
	 * @return Linked list with event lsiteners registered for any event of the widget
	 */
	public static LinkedList<SWTEventListener> getListenersLinkedList(Widget widget) {
		return new LinkedList<SWTEventListener>(registry.get(widget)); 
	}
	
	/**
	 * Returns a hash set of all event listeners that are 
	 * registered for any event of a SWT widget 
	 * @param widget The widget for which all registered event lsiteners are looked up
	 * @return Linked list with event lsiteners registered for any event of the widget
	 */
	public static HashSet<SWTEventListener> getListenersHashSet(Widget widget) {
		return registry.get(widget); 
	}
	
	/**
	 * Returns all the widgets is a hash set  
	 * @return Hash set with all widgets
	 */
	public static HashSet<Widget> getWidgets() {
		return new HashSet<Widget>(registry.keySet()); 
	}
	
	/**
	 * Returns all registered event listners
	 * @return Hash set with all registered event listeners
	 */
	public static HashSet<HashSet<SWTEventListener>> getAllListeners() {
		return new HashSet<HashSet<SWTEventListener>>(registry.values()); 
	}

	/**
	 * Determines if a widget is present in the registry
	 * @param widget Widget for which is queried
	 * @return True if this widget is present in the registry
	 */
	public static boolean containsWidget(Widget widget) {
		return registry.containsKey(widget);
	}
	
	/**
	 * Determines if a event listener is registered for an event in any widget
	 * @param listener Event listner for which is queried
	 * @return True if this event listner is registerd for any event of any widget
	 */
	public static boolean containsListener(SWTEventListener listner) {
		return registry.containsValue(listner);
	}
	
	/**
	 * Returns a hash set of all entries of the registry
	 * @return Hash set with all widget/listener paitrs
	 */
	public static HashSet<Map.Entry<Widget, HashSet<SWTEventListener>>> entrySet() {
		return (HashSet<Map.Entry<Widget, HashSet<SWTEventListener>>>)registry.entrySet();
	}
	
	/**
	 * Asociates a widget with a set of event lsitners
	 * @param widget The widget to associate
	 * @param listenerSet The set of event listners
	 * @return The previous set of listners associated with that widget, if any. Otherwise, null 
	 */
	public static HashSet<SWTEventListener> putWidget(Widget widget, HashSet<SWTEventListener> listenerSet) {
		return registry.put(widget, listenerSet);
	}

	/**
	 * Clears the registry 
	 */
	public static void clear() {
		registry.clear();
	}
	
	/**
	 * Returns the number of registered widgets
	 * @return The number of registered widgets
	 */
	public static int size() {
		return registry.size();
	}
	
	/**
	 * Add a widget to the registry
	 * @param widget The widget to be added
	 */
	public static void addWidget(Widget widget) {
		if (!containsWidget(widget)) {
			putWidget(widget, new HashSet<SWTEventListener>());
		}
	}
	
	/**
	 * Add a listener to the registry.
	 * @param widget The widget to for whose event is registered the listener
	 * @param listener The listener to be added
	 */
	public static void addWidget(Widget widget, SWTEventListener listener) {
		if (!containsWidget(widget)) {
			HashSet<SWTEventListener> set = new HashSet<SWTEventListener>();
			set.add(listener);
			putWidget(widget, set);
		} else {
			getListenersHashSet(widget).add(listener);
		}
	}
	
	/**
	 * Map an event listener to a widget. If the widget is not in the
	 * registry new pair widget/listener is added
	 * @param widget The widget to which the listener is mapped
	 * @param listener The listener to map
	 */
	public static void addListner(Widget widget, SWTEventListener listener) {
		if (containsWidget(widget)) {
			getListenersHashSet(widget).add(listener);
		} else {
			addWidget(widget, listener);
		}
	}
	
	/**
	 * Remove a widget from the registry
	 * @param widget The widget to remove
	 * @return The listeners mapped to the widget, null otherwise
	 */
	public static HashSet<SWTEventListener> removeWidget(Widget widget) {
		return registry.remove(widget);
	}
	
	/**
	 * Prints all mapped widget/listener pairs
	 */
	public static void printMappings() {
		for (Entry<Widget, HashSet<SWTEventListener>> e : registry.entrySet()) {
			String widgetName = "[WIDGET] " + e.getKey().toString() + '\t';
			for (Object l : e.getValue().toArray()) {
				System.out.println(widgetName + "[LISTENER] " + l.toString());
			}
		}
		System.out.println();
	}
}

















