import java.io.*;
import java.util.*;

public class Assembly_Parser {
	public static void main(String[] args) throws Exception {
	    String[] commands = {"and", "or", "add", "addi", "sll", "sub", "slt", "beq", "bne", "lw", "sw", "j", "jr", "jal"};
        List<String>list = new ArrayList<String>();
		String[] labels = new String[100];
		int i = 1;
		try{
	        File input = new File("input1.txt");
	        File output = new File("output1.txt");
	        Scanner sc =new Scanner(input);
	        PrintWriter printer =new PrintWriter(output);
	        while(sc.hasNext()){
	        	
	        	String line = sc.nextLine();                       /* Reads line by line the text file content        */
	        	String copy;                                       /* Make a copy so that the original does not alter */
	        	copy = line.replaceAll("\\s+","");                 /* Gets rid of all the white space                 */
	        	
	        	if(copy.trim().isEmpty()){}                        /* Check for empty lines                           */
	        	
	        	else if(copy.charAt(0) == '#'){}                   /* Check if the first element is a comment sig     */
	        	
	        	else{
	        		line = line.trim();
	        		if(line.contains("#")){
	        	       line = line.split("#")[0];                 /* Gets rid of any comments that happens after instructions */
	        	       if(is_label(line)){
	        	    	   labels[i] = line;
	        	    	   String[] parts = line.split("\\:");
	        	           String part = parts[1];
	        	           if(part.contains("$")){
			        			part = part.split("\\$")[0];
	        	    	   }
	        	    	   list.add(part.trim());    /* Add the line of the label at end of string */
	        	    	   i++;
		        	   }
	        	       else if(line.contains("$")){
		        			line = line.split("\\$")[0];
		        			list.add(line.trim());
		        			i++;
		        	   }
	        	       else{
	        	          list.add(line.trim());                     /* Trim cuts the whitespace before and after the string */
	        	          i++;
	        	       }
	        		}
	        		else if(line.contains("$")){
	        			line = line.split("\\$")[0];
	        			list.add(line.trim());
	        		}
	        		else if(line.contains(" ")){
		        			line = line.split(" ")[0];
		        			list.add(line.trim());
		        			i++;
		        	}
	        		else{
	        			if(is_label(line)){
	        	    	   labels[i] = line;       /* Adds label to the index that represents the line it was found on */
		        	       i++;
	        			}
	        			else{
	        			   list.add(line.trim());                    
	        			   i++;
	        			}
	        		}
	        	}                                                  /* Add the line to the list                             */
	        }
	        for(String str:list){                                  /* This loops writes out the elements that were    */
	        	   check_instruction(str, commands);
	        }	                                                           
	        for(String str:list){                                  /* This loops writes out the elements that were    */
	        	   String opr = opcode(str);
	        	   printer.write(opr);                                /* added to the list into a output text file.      */
	               printer.write("\n");                               /* Format: [t1, t2, t3]                            */
	        	
	        }
	        sc.close();
	        printer.close();
		}
		catch(FileNotFoundException e){
			System.err.println("File not found.");
		}
	}
	
	public static String label_check(String label) throws Exception{
		for(int i = 0; i < label.length(); i++){
			char c = label.charAt(i);
			if ((c > 57 || c <48) && (c < 97 || c > 122) && (c < 65 || c > 90)){
				throw new Exception("Not a valid label");
			}
		}
		return label;
	}
	
	public static Boolean is_label(String s){
		if(s.contains(":")){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static void check_instruction(String instr, String[] instructions)throws Exception{
	    if(!Arrays.asList(instructions).contains(instr)){
	       throw new Exception("NOT A VALID COMMAND");
	    }
	}
	
	public static String opcode(String cmd){
		String opcode = null;
		switch(cmd){
		case "and":
			opcode = "000000";
			break;
		case "or":
			opcode = "000000";
			break;
		case "add":
			opcode = "000000";
			break;
		case "addi":
			opcode = "001000";
			break;
		case "sll":
			opcode = "000000";
			break;
		case "sub":
			opcode = "000000";
			break;
		case "slt":
			opcode = "000000";
			break;
		case "beq":
			opcode = "000100";
			break;
		case "bne":
			opcode = "000101";
			break;
		case "lw":
			opcode = "100011";
			break;
		case "sw":
			opcode = "101011";
			break;
		case "j":
			opcode = "000010";
			break;
		case "jr":
			opcode = "000000";
			break;
		case "jal":
			opcode = "000011";
			break;
		}
		return opcode;
	}
	
	

}
