package jargenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

/**
 * Writes the class files from all porvided paths
 * recursively to a JAR file conatining the java agent
 * used for bytecode instrumenatation and all other
 * necessary classes.
 * @author svetoslavganov
 *
 */
public class JarGenerator {
	private String preMainClass;
	private String jarFileName;
	private String[] paths;
	
	/**
	 * Creates a new JarGenerator instance
	 * @param parameters Array of parameters
	 * @see Javadoc for the main method
	 */
	public JarGenerator(String[] parameters) {
		if (parameters.length < 3) {
			System.out.println("More parameters expected! See javadoc for reference");
			return;
		}else{
			preMainClass = parameters[0];
			jarFileName = parameters[1];
			paths = new String[parameters.length - 2];
			for (int i = 2; i < parameters.length; i++) {
				paths[i - 2] = parameters[i];
			}
		}
	}
	
	/**
	 * Generate jar file with appropriate manifest for instrumentation
	 * it traverses all the paths provided as parameters to the 
	 * constructor.
	 */
	public void generateJarFile() {
		File jarFile = new File(jarFileName);
		if (jarFile.exists()) {
			jarFile.delete();
		} 
		Manifest manifest = new Manifest();
		manifest.getMainAttributes().putValue("Manifest-Version", "1.0");
		manifest.getMainAttributes().putValue("Created-By", "1.6.0_01 (Sun Microsystems Inc.)"); 
		manifest.getMainAttributes().putValue("Class-path", "swt.jar asm-3.0.jar");
		manifest.getMainAttributes().putValue("Premain-Class", preMainClass.trim());
		try{
			JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFileName), manifest);
			for (String path: paths) {
				File file = new File(path);
				if (file.exists()) {
					LinkedList<File> files = new LinkedList<File>();
					if (file.isDirectory()) {
						files.addAll(Arrays.asList(file.listFiles()));
					} else {
						files.add(file);
					}
					addToJarFile(jos, files);
				} else {
					System.out.println("Folder " + file.getPath() + " does not exist");
				}
			}
			jos.flush();
			jos.close();
		}catch(IOException ioe){
			System.out.println("Error while creating file " + jarFileName + " " + ioe);
		}
	}
	
	/**
	 * Add all files from a list to the JAR file. Explores
	 * sub-directories recursively. Adds only class files.
	 * @param jos The JAR output stream
	 * @param files The list of potential files to be added
	 */
	public void addToJarFile(JarOutputStream jos, LinkedList<File> files) {
		while (files.size() > 0) {
			File file = files.removeLast();
			if (file.isFile()) {
				if (file.getName().endsWith(".class")) {
					writeJarEntry(jos, file);
				}
			} else {
				addToJarFile(jos, new LinkedList<File>(Arrays.asList(file.listFiles())));
			}
		}
			
	}
	
	/**
	 * Write a file as a JAR entry
	 * @param jos The JAT output stream
	 * @param file the file to be written
	 */
	private void writeJarEntry(JarOutputStream jos, File file) {	
		try{
			System.out.println("[ADDING] " + file.getPath());
			FileInputStream fis = new FileInputStream(file);
		    jos.putNextEntry(new ZipEntry(file.getPath().substring(14)));
		    int size;
		    byte [] chunk = new byte [1024];
		    while ((size = fis.read(chunk)) > -1) {
		        jos.write(chunk, 0, size);
		    }
		    fis.close();
		    jos.flush();
		    jos.closeEntry();
		    jos.flush();
		}
		catch(Exception e){System.out.println(e.getMessage());
		}
	}

	/**
	 * Runs an instance of the JarGenerator and packs an agent
	 * file needed to instrument java bytecode.
	 * @param List of parameters:
	 * 			[0] The name of the premain class (Class with method: public static void premain(String options, Instrumentation instrumentation))
	 *              This is the whole class name (including the package)
	 * 		    [1] The name of the generated jar file
	 * 			[2...n] Paths that are to be traversed recursively to add the class files in the JAR
	 *              Incorrect ones are silently ignored.    
	 */
	public static void main(String[] args) {
		JarGenerator jg = new JarGenerator(args);
		jg.generateJarFile();
		File file = new File(args[1]);
		if (file.exists()) {
			System.out.println(args[1] + " successfuly generated!");
		} else {
			System.out.println(args[1] +  " GENERATION ERROR!!!");
		}
	}
}