package HackAssembler;

import java.util.Hashtable;

public class SymbolTable {
	private Hashtable<String, Integer> symbolTable;	// Stores the variables with addresses/values in a pair of strings.
	private int nextFreeSpot;
	
	public SymbolTable() {
		symbolTable = new Hashtable<String, Integer>(100);
		
		symbolTable.put("SP", 0);
		symbolTable.put("LCL", 1);
		symbolTable.put("ARG", 2);
		symbolTable.put("THIS", 3);
		symbolTable.put("THAT", 4);
		for(int i = 0; i < 16; i++) {
			String r = "R" + i;
			symbolTable.put(r, i);
		}
		nextFreeSpot = 16;
		symbolTable.put("SCREEN", 16384);
		symbolTable.put("KBD", 24576);
	}
	
	public boolean addEntry(String variable, int address) {
		if(!symbolTable.containsKey(variable)) {
			symbolTable.put(variable, address);
			if(address == nextFreeSpot) {
				nextFreeSpot++;
			}
			return true;
		}
		System.out.println("Duplicate symbol: " + variable + " refrained from being added to table. Value is: " + symbolTable.get(variable));
		return false;
	}
	public boolean contains(String variable) {
		return symbolTable.containsKey(variable);
	}
	
	public int getAddress(String variable) {
		return symbolTable.get(variable);
	}
	
	public int getNextFreeSpot() {
		return nextFreeSpot;
	}
}
