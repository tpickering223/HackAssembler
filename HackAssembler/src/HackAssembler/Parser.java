package HackAssembler;

public class Parser {
	String dest;
	String comp;
	String jmp;
	String address;
	public Parser() {
		dest = null;
		comp = null;
		jmp = null;
		address = null;
	}
	
	
	// Given a command in the form of a line, parses it, determining if it's an A-Instruction or a C-Instruction and assigning the proper variables to its parts.
	public boolean parse(String command) {
		if(!command.isEmpty() || command != null) {
			command = command.trim();
			command = removeComments(command);		// Clean command of comments and whitespace.
			if(command.contains("@")) {				// Command is an A-Instruction if an @ exists at the beginning of it.
				address = command.substring(1, command.length());
				return true;
			}
			else {									// Command might be in the form of C-Instruction syntax: dest=comp;jump
				if(command.contains("=")) {			// Presence of an equal sign indicates a dest field.
					String[] sub = command.split("=");
					dest = sub[0];
					if(sub[1].contains(";")) {		// Presence of a semicolon indicates a jump field.
						String[] e = sub[1].split(";");
						comp = e[0];
						jmp = e[1];
						return true;
					}
					else {
						comp = sub[1];
						return true;
					}
				}
				else if (command.contains("+") || command.contains("-") || command.contains("&") || command.contains("|") || command.contains(";")) {	// Presence of a comp (without a dest field) field if any of these symbols exist.
					if(command.contains(";")) {
						String[] sub = command.split(";");
						comp = sub[0];
						jmp = sub[1];
						return true;
					}
					else {
						comp = command;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/* Below are the getters for the syntax parts: 
	 * A-Instruction: @value, getAddress() retrieves "value"
	 * C-Instruction: dest=comp;jump
	 * getDest() retrieves dest.
	 * getComp() retrieves comp.
	 * getJMP() retrieves jump.
	 */
	public String getDest() {
		return dest;
	}
	
	public String getJMP()	{
		return jmp;
	}
	
	public String getComp() {
		return comp;
	}
	
	public String getAddress() {
		return address;
	}
	
	// Helper function that removes comments within the command's line.
	public String removeComments(String line) {
		if(line.contains("//")) {
			line = line.substring(0, line.indexOf('/'));
			line = line.trim();
			return line;
		}
		else {
			return line;
		}
	}
}
