package barad.profiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import static barad.profiler.ProfilerProperties.OPCODE_TO_MNEMONIC_MAP_FILE;

/**
 * Class that builds a map for matching opcodes with mnemonic
 * codes. The relationship is not hardcoded in order to save time
 * since the file with the value pairs was already present.
 * 
 * @author svetoslavganov
 */
public class OpcodeToMnemonicMap {
	public static Logger log = Logger.getLogger(OpcodeToMnemonicMap.class);
	private static HashMap<Integer, String> opcodeToMnemonicMap;

	public static HashMap<Integer, String> getMap() {
		if (opcodeToMnemonicMap == null) {
			HashMap<Integer, String>map = new HashMap<Integer, String>();
			try{File file = new File(OPCODE_TO_MNEMONIC_MAP_FILE);
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
			reader.close();
			opcodeToMnemonicMap = map;
			}catch(Exception e){
				log.error(e);
			}
		}
		return opcodeToMnemonicMap;
	}

	private static void printPairs(HashMap<Integer, String> map) {
		if (opcodeToMnemonicMap != null) {
			for(Map.Entry<Integer, String> e: map.entrySet()) {
				log.debug("Opcode: " + e.getKey() + " Mnemonoc: " + e.getValue());
			}
		} else {
			log.debug("The opcode to mnemonic map is not initalized!!!");
		}
	}

	public static void main(String[] args) { 
		HashMap<Integer, String> map = OpcodeToMnemonicMap.getMap();
		OpcodeToMnemonicMap.printPairs(map);
	}
}
