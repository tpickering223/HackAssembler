package HackAssembler;

import java.io.*;
import java.util.*;

public class Assembler {


	private static SymbolTable variables;
	private static int ROM_LINE;
	private static Code c = new Code();
	public Assembler() {
		variables = new SymbolTable();
		ROM_LINE = 0;
	}
	public static void main(String[] args) throws IOException {
		File asmFile;
		if(args.length == 0) {
			System.out.println("No Args[] detected, enter a filepath for the file to be assembled: ");
			Scanner in = new Scanner(System.in);
			String path = in.nextLine();
			asmFile = new File(path);
			in.close();
		}
		else {
			asmFile = new File(args[0]);
		}
		storeSymbols(asmFile);
		
		String result = asmFile.getAbsolutePath().replace(".asm", ".hack");	// Create binary file, .asm --> .hack
		
		assemble(asmFile, result);
		System.out.println("Assembly Completed.");
		System.out.println("Program terminated.");
	}

	// First pass of the input file, storing symbols with their addresses.
	public static void storeSymbols(File asm) throws FileNotFoundException {
		ROM_LINE = 0;
		variables = new SymbolTable();
		Scanner read = new Scanner(asm);
		while(read.hasNextLine()) {
			Parser p = new Parser();
			String input = read.nextLine();
			input = input.trim();
			input = p.removeComments(input);
			if(input.equals("")) {
				ROM_LINE--;
			}
			if(input.contains("(") && input.contains(")") && input.charAt(0) != '/') {	// Detect (LABEL) syntax
				String symbol = input.substring(1, input.length()-1);	// Substring only containing LABEL without ( and )
				variables.addEntry(symbol, ROM_LINE);			// Add it to the Symbol Table, this method will self-check to see if it's a duplicate or not.
					
			}
			else {
				ROM_LINE++;
			}
		}
		read.close();
	}
	
	// Start translating values to binary and building the .hack file.
	public static void assemble(File asm, String hackfile) throws IOException {
		boolean valid = false;
		Scanner read = new Scanner(asm);
		FileWriter dothack = new FileWriter(hackfile);
		String command;
		while(read.hasNextLine() && (command = read.nextLine()) != null) {
			Parser p = new Parser();
			valid = p.parse(command);
			command = command.trim();
			command = p.removeComments(command);
			if(valid && (!command.contains("(") || !command.contains(")"))) {
				if(command.charAt(0) == '@') {
					String address = p.getAddress();
					
					if(addressIsInt(address)) {
						String binaryAddress = Integer.toBinaryString(Integer.parseInt(address));
						String resultingAddress = "0" + binaryAddress;
						// Pad out any binary strings of a length less than 16
						if(resultingAddress.length() < 16) {
							resultingAddress = String.format("%" + (16) + "s", resultingAddress).replace(' ', '0');
						}
						dothack.write(resultingAddress + "\n");
					}
					else {
						
						variables.addEntry(address, variables.getNextFreeSpot());
						String variableRAMAddress = Integer.toBinaryString(variables.getAddress(address));
						
						String resultingAddress = "0" + variableRAMAddress;
						// Pad out any binary strings of a length less than 16
						if(resultingAddress.length() < 16) {
							resultingAddress = String.format("%" + (16) + "s", resultingAddress).replace(' ', '0');
						}
						
						dothack.write(resultingAddress + "\n");
					}
					
				}
				else {
					String jump;
					String comp;
					String dest;
					//Retrieve the binary encodings of dest=comp;jmp
					if(p.getJMP() == null)
					{
						jump = c.jump("nu11");	// the Ls are replaced with 1s because Java's Hashmap throws exception on the String literal of null.
					}
					else {
						jump = c.jump(p.getJMP());
					}
					
					comp = c.comp(p.getComp());
					
					if(p.getDest() == null) {
						dest = c.dest("nu11");
					}
					else {
						dest = c.dest(p.getDest());
					}
					
					dothack.write("111" + comp + dest + jump + "\n");
					
				}
			}
		}
		
		read.close();
		dothack.close();
	}
	
	public static boolean addressIsInt(String address) {
		boolean result = true;
		for(int i = 0; i < address.length(); i++) {
			if(Character.isDigit(address.charAt(i))) {
				//Do nothing
			}
			else {
				return false;
			}
			
		}
		return result;
	}
}