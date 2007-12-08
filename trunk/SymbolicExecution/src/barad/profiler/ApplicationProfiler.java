package barad.profiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * This class cretaes a profile of an application by exploring all Java class files. 
 * The implememntation traverses recursively all subfolders and jar files. 
 * 
 * @output File containing the classes of the application
 * @output File containing the methods of the application
 * @output File containing the fileds of the application
 * @output File containing the bytecode instructions of the application
 * @author svetoslavganov
 */
public class ApplicationProfiler implements ProfilerProperties {
	
	public static Logger log;
	private static HashSet<String> classSet;
	private static HashSet<String> methodSet;
	private static HashSet<String> fieldSet;
	private static HashSet<String> instructionSet;
	private static HashMap<String, HashSet<String>> graph;
	
	public static void main(String[] args) {	
		log = Logger.getLogger(ApplicationProfiler.class);
		classSet = new HashSet<String>();
		methodSet = new HashSet<String>();
		fieldSet = new HashSet<String>();
		instructionSet = new HashSet<String>();
		graph = new HashMap<String, HashSet<String>>();
		LinkedList<File> files = new LinkedList<File>(Arrays.asList(new File(APP_PATH).listFiles()));
		if (files.size() > 0) {
			log.debug("TOTAL NUMBER OF FILES:" + files.size());
			explore(files);
		}else{
			System.out.println("NO FILES IN THE FOLDER");
			return;
		}
		logSet(classSet, "CLASSES SUMMARY");
		logSet(methodSet, "METHODS SUMMARY");
		logSet(fieldSet, "FIELDS SUMMARY");
		logSet(instructionSet, "INSTRUCTIONS SUMMARY");
		buildGraph(methodSet);
		printGraph();
	}
	
	/**
	 * Explores Java Class files by visiting  recursively 
	 * all folders and Jar files 
	 * 
	 * @param files - List with file records to be explored
	 */
	private static void explore(LinkedList<File> files) {
		if (files.size() == 0) return;
		File file = files.removeFirst();
		if (file.isFile()) {	
			if (file.getName().endsWith("jar")) {
				try{
					log.debug("OPENED JAR FILE: " + file.getName());
					JarFile jarFile = new JarFile(file);				
					Enumeration jarEntries = jarFile.entries();
					while (jarEntries.hasMoreElements()) {
						JarEntry nextEntry = (JarEntry)jarEntries.nextElement();
						if (!nextEntry.isDirectory() && nextEntry.getName().endsWith(".class")) {
							InputStream jis = jarFile.getInputStream(nextEntry);
							classSet.add(nextEntry.getName());
							exploreClass(jis);
						}
					}					
					log.debug("FILE CLOSED " + file.getName());
				}catch(IOException ioe){
					log.error("Error when trying to explore jar file " + ioe);
				}
			}
			else if (file.getName().endsWith("class")) { 
				try{
					FileInputStream fis = new FileInputStream(file);
					log.debug("OPENED CLASS FILE: " + file.getName());
					classSet.add(file.getName());
					exploreClass(fis);
					fis.close();
					log.debug("FILE CLOSED" + System.getProperty("line.separator"));
				}catch(Exception e){e.printStackTrace();}
			}
		}else if (file.isDirectory()) {
			explore(new LinkedList<File>(Arrays.asList(new File(file.getPath()).listFiles())));
		}
		explore(files);
	}

	/**
	 * Creates a profile of a Java class file
	 * 
	 * @param classFile Inputs stream to the class file to be explored
	 */
	private static void exploreClass(InputStream classFile) {
		try{
			ClassWriter cw = new ClassWriter(0);
			InstrAdapter ia = new InstrAdapter(cw, methodSet, fieldSet, instructionSet);
			ClassReader cr = new ClassReader(classFile);
			cr.accept(ia, 0);
		}catch(Exception e){e.printStackTrace();}
	}
	
	/**
	 *	Log the contents of a set
	 * 
	 * @param set The set to be logged
	 * @param log Logger instance 
	 * @param message Message to be displayed at the top
	 */
	private static void logSet(HashSet<String> set, String message) {
		ApplicationProfiler.log.info(message);
		ApplicationProfiler.log.info("Size:" + set.size());
		for (String s: set) {
			ApplicationProfiler.log.info(s);
		}
	}
	
	private static void buildGraph(HashSet<String> methodSet) {
		for (String s: methodSet) {
			HashSet<String> neighbors = new HashSet<String>(methodSet);
			neighbors.remove(s);
			graph.put(s, neighbors);
		}
	}
	
	private static void printGraph() {
		for (String vertex: graph.keySet()) {
			System.out.println("Vertex: " + vertex);
			for (String neighbor: graph.get(vertex)) {
				System.out.println("Neighbor: " + neighbor);
			}
			System.out.println();
		}
	}
}

