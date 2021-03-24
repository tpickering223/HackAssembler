package HackAssembler;

import java.util.HashMap;


public class Code {
	private static HashMap<String, String> dests;
	private static HashMap<String, String> comps;
	private static HashMap<String, String> jumps;
	
	public Code() {
		dests = new HashMap<String, String>(8);
		comps = new HashMap<String, String>(28);
		jumps = new HashMap<String, String>(8);
		
		// dest code values from 6.2.2
		dests.put("nu11", "000");
		dests.put("M", "001");
		dests.put("D", "010");
		dests.put("MD", "011");
		dests.put("A", "100");
		dests.put("AM", "101");
		dests.put("AD", "110");
		dests.put("AMD", "111");
		
		// jump code values from 6.2.2
		jumps.put("nu11", "000");
		jumps.put("JGT", "001");
		jumps.put("JEQ", "010");
		jumps.put("JGE", "011");
		jumps.put("JLT", "100");
		jumps.put("JNE", "101");
		jumps.put("JLE", "110");
		jumps.put("JMP", "111");
		
		// comp code values from 6.2.2
		comps.put("0", "0101010");
		comps.put("1", "0111111");
		comps.put("-1", "0111010");
		comps.put("D", "0001100");
		comps.put("A", "0110000");
		comps.put("M", "1110000");
		comps.put("!D", "0001101");
		comps.put("!A", "0110001");
		comps.put("!M", "1110001");
		comps.put("-D", "0001111");
		comps.put("-A", "0110011");
		comps.put("-M", "1110011");
		comps.put("D+1", "0011111");
		comps.put("A+1", "0110111");
		comps.put("M+1", "1110111");
		comps.put("D-1", "0001110");
		comps.put("A-1", "0110010");
		comps.put("M-1", "1110010");
		comps.put("D+A", "0000010");
		comps.put("D+M", "1000010");
		comps.put("D-A", "0010011");
		comps.put("D-M", "1010011");
		comps.put("A-D", "0000111");
		comps.put("M-D", "1000111");
		comps.put("D&A", "0000000");
		comps.put("D&M", "1000000");
		comps.put("D|A", "0010101");
		comps.put("D|M", "1010101");
	}
	
	
	// Returns binary value of the given dest code
	public String dest(String dest_code) {
		return dests.get(dest_code);
	}
	
	// Returns binary value of the given jump code
	public String jump(String jmp_code) {
		return jumps.get(jmp_code);
	}
	
	// Returns binary value of the given comp code
	public String comp(String comp_code) {
		return comps.get(comp_code);
	}
}
