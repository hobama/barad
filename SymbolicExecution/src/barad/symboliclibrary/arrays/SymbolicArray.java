package barad.symboliclibrary.arrays;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;

import barad.symboliclibrary.common.SymbolicEntity;
import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IVAR;
import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Class to represent arrys during symbolic execution.
 * TODO: Figure out if this implementaton is correct as 
 * principle, test, elaborate
 * @author svetoslavganov
 *
 * @param <T> The type of the symbolic array
 */
public class  SymbolicArray<T> extends SymbolicEntity {
	public static final long serialVersionUID = 1L;
	private int size;
	private LinkedList<T> elements;
	private int iteration;
	private T lastAddedElement;
	
	/**
	 * Creates a new symbolic array
	 * @param size The size of the array
	 */
	public SymbolicArray(ICONST size) {
		super("SymbolicArray");
		this.size = size.getValue();
		this.elements = new LinkedList<T>();
	}
	
	/**
	 * Used internally for cloning
	 */
	private SymbolicArray() {
		super("SymbolicArray");
	}
	
	/**
	 * Used only in the construction phase to populate
	 * @param The position to which the element should be inserted
	 * @param element The element to be added
	 */
	public void addElement(ICONST position, T element) {
		elements.add(position.getValue(), element);
	}
	
	/**
	 * Sets a certain position of the array to certain value. If the
	 * position parameter is symbolic variable then all possible outcomes
	 * are explored  i.e. provided to the user by calling nextInstance()
	 * @param position The insertion position
	 * @param element The value to insert
	 */
	public void setElementAt(IntegerInterface position, T element) {
		if (position instanceof ICONST) {
			ICONST iconst = (ICONST)position;
			iteration = 0;
			lastAddedElement = null;
			elements.add(iconst.getValue(), element);
		} else if (position instanceof IVAR){
			iteration = 0;
			lastAddedElement = element;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Gets an element at a certain position in the array. If the
	 * parameter is symbolic variable all possible outcomes are explored
	 * by calling this method
	 * @param position
	 * @return
	 */
	public T getElementAt(IntegerInterface position) {
		T result = null;
		if (position instanceof ICONST) {
			iteration = 0;
			lastAddedElement = null;
			ICONST iconst = (ICONST)position;
			result = elements.get(iconst.getValue());
		} else if (position instanceof IVAR){
			result = elements.get(iteration++);
		} else {
			throw new IllegalArgumentException();
		}
		return result;
	}
	
	/**
	 * Returns the next possible result if the last call to
	 * getElementAt(IntegerInterface position) was variable
	 * @return The next possible element if such exist, null 
	 * otherwise
	 */
	public T getElement() {
		T result = null;
		if (hasNextIteration()) {
			result = elements.get(iteration++);
		}
		return result;
	}
	
	public boolean hasNextInstance() {
		return (iteration < size) && (lastAddedElement != null);
	}
	
	/**
	 * Returns the next instance of the symbolic array if the position parameter 
	 * of the last call to setElementAt(IntegerInterface position, T element) was
	 * a symbolic variablle i.e. we could insert the value in any position of the 
	 * array.
	 * @return Next possible instance i.e. instance with one of the possible 
	 * insertion outcomes, null if no more possibilies exist
	 * @throws CloneNotSupportedException If any of the objects in the array
	 * is not cloneable
	 */
	@SuppressWarnings("unchecked")
	public SymbolicArray<T> nextInstance() throws CloneNotSupportedException {
		SymbolicArray<T> nextInstance = null;
		if (hasNextInstance()) {
			nextInstance = new SymbolicArray<T>();
			nextInstance.size = size;
			nextInstance.elements = new LinkedList<T>();
			for (T element: elements) {
				Class<?> clazz = element.getClass();
				Object result = null;
				try {
					Method method = clazz.getMethod("clone", new Class<?>[]{}); 
					result = method.invoke(element, new Object[]{});
				} catch (NoSuchMethodException nsme) {
					throw new CloneNotSupportedException();
				} catch (IllegalAccessException iae) {
					throw new CloneNotSupportedException();
				} catch (InvocationTargetException ite) {
					throw new CloneNotSupportedException();
				}
				nextInstance.elements.add((T)result);
			}
			iteration++;
		}
		return nextInstance;
	}
	
	/**
	 * Determines if the possible accessess posibilities are
	 * exhauasted. We have possibilities if the index parameter
	 * of the last getElementAt(IntegerInterface position) was
	 * a symbolic variable i.e. it could take any value from 0
	 * to the length of the symbolic array. 
	 * @return True if more possibilities exist, false otherwise
	 */
	public boolean hasNextIteration() {
		return (iteration < size);
	}
}
