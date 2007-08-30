package barad.profiler;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static barad.profiler.ProfilerProperties.INSTR_CLASSES_DIR;


/**
 * Class that builds a map for matching opcodes with mnemonic
 * codes. The relationship is not hardcoded in order to save time
 * since the file with the value pairs was already present.
 * 
 * @author svetoslavganov
 */
public class OpcodeToMnemonicMap {

	public static HashMap<Integer, String> getMap() {
		HashMap<Integer, String>map = new HashMap<Integer, String>();
		try{File file = new File(INSTR_CLASSES_DIR + "OPCODES.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String nextLine;
			while ((nextLine = reader.readLine()) != null) {
				if (nextLine.trim().length() == 0) continue;
				StringTokenizer tokenizer = new StringTokenizer(nextLine); 
				String key = tokenizer.nextToken();
				tokenizer.nextToken();
				String value = tokenizer.nextToken();
				map.put(Integer.parseInt(key), value);
			}
		}catch(Exception e){e.printStackTrace();}
		return map;
	}
	
	private static void printPairs(HashMap<Integer, String> map) {
		for(Map.Entry<Integer, String> e: map.entrySet()) {
			System.out.println("Opcode: " + e.getKey() + " Mnemonoc: " + e.getValue());
		}
	}
	
	public static void main(String[] args) { 
		HashMap<Integer, String> map = OpcodeToMnemonicMap.getMap();
		OpcodeToMnemonicMap.printPairs(map);
	}
}
