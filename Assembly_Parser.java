import java.io.*;
import java.util.*;

public class Assembly_Parser {
	public static void main(String[] args) throws Exception {
	    String[] commands = {"and", "or", "add", "addi", "sll", "sub", "slt", "beq", "bne", "lw", "sw", "j", "jr", "jal"};
	    String[] registers = {"0", "zero", "v0", "v1", "a0", "a1", "a2", "a3", "t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8", "t9", "s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "sp", "ra",
	    		               "$0", "$zero", "$v0", "$v1", "$a0", "$a1", "$a2", "$a3", "$t0", "$t1", "$t2", "$t3", "$t4", "$t5", "$t6", "$t7", "$t8", "$t9", "$s0", "$s1", "$s2", "$s3", "$s4", "$s5", "$s6", "$s7", "$sp", "$ra"};
        List<String>list = new ArrayList<String>();
        List<String>arg1 = new ArrayList<String>();
		String[] labels = new String[40];
		String[] cmds = new String[40];
		String[] args1 = new String[40];
		String[] args2 = new String[40];
		String[] args3 = new String[40];
		int i = 1;
		try{
	        File input = new File("input1.txt");
	        File output = new File("output1.txt");
	        Scanner sc =new Scanner(input);
	        PrintWriter printer = new PrintWriter(output);
	        while(sc.hasNext()){
	        	
	        	String line = sc.nextLine();                                       /* Reads line by line the text file content        */
	        	String condensed_string;                                           /* Make a copy so that the original does not alter */
	        	condensed_string = line.replaceAll("\\s+","");                     /* Gets rid of all the white space                 */
	        	
	        	if(condensed_string.trim().isEmpty()) {}                           /* Check for empty lines                           */
	        	
	        	else if(condensed_string.charAt(0) == '#'){}                       /* Check if the first element is a comment sig     */
	        	
	        	else{ 
	        		  String instruction_copy = line;
	        		  String i_copy = line;
	        		  String arg1_copy = line;         /* Finds rd in format rd, rs, rt */
	        		  String arg2_copy = line;         /* Finds rs in format rd, rs, rt */
	        		  String arg2_copy2 = line;        /* Finds rs in format rd, rs, rt - specific for lw and sw */
	        		  String arg2_copy3 = line;        /* Finds rs in format rd, rs, rt - specific for lw and sw */
	        		  String arg2_copy4 = line;        /* Finds rs in format rd, rs, rt - specific for lw and sw */
	        		  String arg3_copy = line;         /* Finds rt in format rd, rs, rt */
	        		  
	        		  /* Creates an array for the rd arguments */
	        		  arg1_copy = arg1_copy.trim();
	        		  if(arg1_copy.contains("$")) {
	        			  arg1_copy = arg1_copy.split("\\$")[1];
	        		     if(arg1_copy.contains(",")) {
	        		    	 arg1_copy = arg1_copy.split("\\,")[0];
	        		    	 args1[i] = arg1_copy.trim();
	        		     }
	        		  }
	        		  
	        		  /* Creates an array for the rs arguments, ignores the instructions - sw, lw, j, jal, and jr */
	        		  arg2_copy = arg2_copy.trim();
	        		  if(arg2_copy.contains("$")) {
	        			  if(arg2_copy.contains("sw") || arg2_copy.contains("lw") || arg2_copy.contains("j")) {}
	        			  else {
	        			     arg2_copy = arg2_copy.split("\\$")[2];
	        		         if(arg2_copy.contains(",")) {
	        		    	    arg2_copy = arg2_copy.split("\\,")[0];
	        		    	    args2[i] = arg2_copy.trim();
	        		         }
	        		     }
	        		  }
	        		  
	        		  /* Creates an array for the rs arguments, ignores the instructions - j, jal, and jr */
	        		  arg2_copy2 = arg2_copy2.trim();
	        		  if(arg2_copy2.contains("$")) {
	        			  if(arg2_copy2.contains("sw") || arg2_copy2.contains("lw")) {
	        				  arg2_copy2 = arg2_copy2.split("\\,")[1];
	        				  arg2_copy2 = arg2_copy2.split("\\(")[0].trim();
	        				  args3[i] = arg2_copy2;
	        			  }
	        			  else {}
	        		  }
	        		  
	        		  /* Creates an array for the rs arguments, ignores the instructions - j, jal, and jr */
	        		  arg2_copy3 = arg2_copy3.trim();
	        		  if(arg2_copy3.contains("$")) {
	        			  if(arg2_copy3.contains("sw") || arg2_copy3.contains("lw")) {
	        				  arg2_copy3 = arg2_copy3.split("\\$")[2];
	        				  arg2_copy3 = arg2_copy3.split("\\)")[0];
	        				  args2[i] = arg2_copy3;
	        			  }
	        			  else {}
	        		  }
	        		  
	        		  /* Creates an array for the rs arguments */
	        		  arg2_copy4 = arg2_copy4.trim();
        			  if(arg2_copy4.contains("j")) {
    	        		  if(arg2_copy4.contains("$")) {
        				     arg2_copy4 = arg2_copy4.split("\\$")[1];
        				     args1[i] = arg2_copy4.trim();
    	        		  }
    	        		  else {
    	        			 
    	        			  arg2_copy4 = arg2_copy4.split("\\s+")[1];
         				      args1[i] = arg2_copy4.trim();
    	        		  }
        			  }
        			  else {}
	        		  
	        		  
	        		  
	        		  /* Creates an array for the rt arguments, ignores the instructions - sw, lw, j, jal, and jr */
	        		  arg3_copy = arg3_copy.trim();
	        		  if(arg3_copy.contains("$")) {
	        			  if(arg3_copy.contains("sw") || arg3_copy.contains("lw") || arg3_copy.contains("j")) {}
	        			  else {
	        				  arg3_copy = arg3_copy.split(",")[2]; 
	        				  arg3_copy = arg3_copy.split("#")[0];                      
	        				  args3[i] = arg3_copy.trim();
	        		     }
	        		  }

	        		  cmds = instruction_list(instruction_copy, list, cmds, i);
	        		  labels = labels_locator(instruction_copy, labels, i);
	        		  i = increment_i(i_copy, i);
	        	}
	        	
	        	                               
	        }
	        
	        
	        for(int l = 1; l < args1.length -1 ; l++) {
	        	  System.out.print(cmds[l] + "   ");
	  	    	  System.out.print(args1[l] + "   ");
	  	    	  System.out.print(args2[l] + "   ");
	  	    	  System.out.print(args3[l] + "   ");
	  	    	  System.out.print(labels[l] + "   ");
	  	    	  System.out.print("\n");
	  	    } 
	  	    
	       
	        /* Checks for invalid instructions */
	        instruction_processesor(list, commands, cmds);
	        
	        /* Writing opcodes to output text file */
	        write_to_out(list, printer, cmds, args1, args2, args3, registers, labels); 
	        
	       
	        
	        /* Must close scanner and printer */
	        sc.close();
	        printer.close();
		}
		catch(FileNotFoundException e){
			System.err.println("File not found.");
		}
	}
	
	/* Increments i to put into labels array */
	public static int increment_i(String line, int i) {
		line = line.trim();
		if(line.contains("#")){
	       line = line.split("#")[0];                 
	       if(is_label(line)){  
	           i++;
    	   }
	       else if(line.contains("$")){
    	      i++;
    	   }
	       else{
	          i++;
	       }
		}
		else if(line.contains("$")){
			i++;
		}
		else if(line.contains(" ")){
    		i++;
    	}
		else{
			if(is_label(line)){
		    
			}
			else{
			   i++;
			}
		}
		return i;
	}
	
	/* Puts the labels in the labels array at i index, i being the line the label was found on */
	public static String[] labels_locator(String line, String[] labels, int i) {
		line = line.trim();
		if(line.contains("#")){
	       line = line.split("#")[0];                                        /* Gets rid of any comments that happens after instructions */
	       System.out.print(line);
	       System.out.print("\n");
	       if(is_label(line)){                                               /* Only the label */
	    	   String[] parts = line.split("\\:");
	           String part = parts[0];
	           part = no_money_sign(part);
	           labels[i] = part;
	           
    	   }
		}
		else{
			if(is_label(line)){
				
				line = line.split(":")[0];
				
				labels[i] = line;                                             /* Adds label to the index that represents the line it was found on */
				
			}
		}
		return labels;	
	}
	
	public static String[] instruction_list(String line, List<String> list, String[] cmds, int i){
		line = line.trim();
		if(line.contains("#")){
	       line = line.split("#")[0];                                        /* Gets rid of any comments that happens after instructions */
	       if(is_label(line)){
	    	   String[] parts = line.split("\\:");
	           String part = parts[1].trim();
	           if(part.length() > 1) {
	        	   part = no_money_sign(part);
	        	   part = part.split(" ")[0];
	        	   cmds[i] = part;
	           }
	           else {
	              part = no_money_sign(part);
	    	      list.add(part.trim());                                        /* Add the line of the label at end of string */
	    	      cmds[i] = part.trim();
	           }
    	   }
	       else if(line.contains("$")){
    			line = line.split("\\$")[0];
    			list.add(line.trim());
 	    	    cmds[i] = line.trim();

    	   }
	       else{
	          list.add(line.trim());                                         /* Trim cuts the whitespace before and after the string */
	          cmds[i] = line.trim();
	       }
		}
		else if(line.contains("$")){
			if(is_label(line)) {
				line = line.split("\\:")[1];
				if(line.contains("$")) {
				   line = line.split("\\$")[0].trim();	
				}
				cmds[i] = line.trim();
			}
			else {
			   line = line.split("\\$")[0];
			   list.add(line.trim());
			   cmds[i] = line.trim();
			}
		}
		else if(line.contains(" ")){
    			line = line.split(" ")[0];
    			list.add(line.trim());
    			 cmds[i] = line.trim();
    	}
		else{
			if(is_label(line)){
				String[] parts = line.split(":");
				if(parts.length == 1) {}
				else {
				   String part1 = parts[1];
				   if(part1.length() > 1) {
				      String[] parts1 = part1.split(" ");
				      String parts1_2 = parts1[0];
				      cmds[i] = parts1_2;
				   }
				   else {
					   cmds[i] = part1;
				   }
				  
				}
			}
			else{
				 list.add(line.trim());
				 cmds[i] = line.trim();
		    }
		}
		return cmds;
	}
	
	/* Checks for invalid instructions */
	public static void instruction_processesor(List<String> list, String[] commands, String[] cmds) throws Exception {
		for(int y = 0; y < cmds.length; y++){                                  /* This loops writes out the elements that were    */
		   if(cmds[y] == null) {}
		   else { check_instruction(cmds[y], commands); }
        }	
	}
	
	
	
	/* Takes arraylist, reads commands, finds opcodes for commands, prints to output */
	public static void write_to_out(List<String> list, PrintWriter printer, String[] cmds, String[] args1, String[] args2, String[] args3, String[] registers, String[] labels) {
		for(int y = 0; y < cmds.length; y++){                                 
			   if(cmds[y] == null) {}
			   else {
				  if(args1[y] == null) {}
				  else {
					  if(args2[y] == null) {
						 if(cmds[y].equals("jal") || cmds[y].equals("j")) {
							 int li = Arrays.asList(labels).indexOf(args1[y]);
							 String imm = convertBinary(li - 1, 26);
							 String opr = jump_opcode(cmds[y], args1[y], imm);
							 printer.write(opr);                                
		                     printer.write("\n");
						 }
						 if(cmds[y].equals("jr")) {
					         String opr = jump_opcode(cmds[y], args1[y], "");
					         printer.write(opr);                                
		                     printer.write("\n");
						
						 }
					  }
					  else {
						 if(args3[y] == null) {}
						 else {
							if(Arrays.asList(registers).contains(args3[y])) {                            /* If instruction is R-format */
								String opr = opcode(cmds[y], args1[y], args2[y], args3[y]);   
		     	                printer.write(opr);                                
		                        printer.write("\n");
							}
							else if(Arrays.asList(labels).contains(args3[y])) {                          /* If instruction contains a label */
							    int li = Arrays.asList(labels).indexOf(args3[y]);
							    int diff = 0;
							    int jumps = 0;
							    
							    for(int k = 1; k < args3.length; k++) {                                  /* 1.Find the index of label in label array */
							       String s = args3[k];                                                  /* 2. Find the index of the label occurences in mips file */
							       if(s == null) {}                                                      /* 3. Subtract 1 from 2 and add one */
							       else {                                                                /* 4. Turn into negative number and convert into binary # */
                                      if(s.equals(labels[li])) {
                                    	   diff = (k - li) + 1;
										   jumps = 0 - diff;
										   args3[k] = null;                                               /* Loops for every instruction, setting args3[k] to null helps not repeat the same index */
		                                   break;
									  }
							       }  
							    }
							    
							    String imm = convertBinary(jumps, 16);
							    String opr = branch_opcode(cmds[y], args1[y], args2[y], imm);   
		     	                printer.write(opr);                                                              /* added to the list into a output text file.      */
		                        printer.write("\n");
							}
							else {                                                                              /* Instructions with immediate as a third argument */
								if(cmds[y].equals("addi")) {
									String imm = convertBinary(Integer.parseInt(args3[y]), 16);
									String opr = immediate_opcode(cmds[y], args1[y], args2[y], imm); 
									printer.write(opr);                                                      
				                    printer.write("\n");
								}
								else if(cmds[y].equals("sll")) {
									String imm1 = convertBinary(Integer.parseInt(args3[y]), 5);
									String opr1 = sll_opcode(cmds[y], args1[y], args2[y], args3[y], imm1);
									printer.write(opr1);                                                        
			                        printer.write("\n");
								}
								else {
									if(cmds[y].equals("bne") || cmds[y].equals("beq")) {
										int li = Arrays.asList(labels).indexOf(args3[y]);
										System.out.print(Integer.toString(li));
										System.out.print("\n");
										String imm1 = convertBinary(li, 16);                                         /* For branch instructions */
										String opr2 = branch_opcode(cmds[y], args1[y], args2[y], imm1); 
				     	                printer.write(opr2);                                                        
				                        printer.write("\n");
									}
									else {
										
										String imm1 = convertBinary(Integer.parseInt(args3[y]), 16);                                         /* For branch instructions */
										String opr2 = immediate_opcode(cmds[y], args1[y], args2[y], imm1); 
				     	                printer.write(opr2);                                                        
				                        printer.write("\n");
									}
								}
							}    
						 }
					  }
				  }
	           }
	    }
	}
	
	
	public static String convertBinary(int num, int bin_length){
	     int binary[] = new int[bin_length];
	     String b = "";
	     int index = 0;
	     if(num < 0) {
	    	 String bine = Integer.toBinaryString(num);	
	    	
	    	 String[] conv = bine.split("(?!^)"); 
	    	 
	    	 for(int i = 31;i >= bin_length; i--){
		 	   
		 	       b = conv[i] + b;
		 	 }
	    	 return b;
	     }
	     else {
	        while(bin_length > 0){
	           binary[index] = num%2;
	           num = num/2;
	           index++;
	           bin_length--;
	        }
	        for(int i = index-1;i >= 0;i--){
	 	       String bin = Integer.toString(binary[i]);
	 	       b = b + bin;
	 	     }
	 	     return b;
	     }
	     
	  }
	
	/* Gets rid of characters after first money sign */
	public static String no_money_sign(String line) {
		if(line.contains("$")){
			line = line.split("\\$")[0];
	   }
	   return line;
	}
	
	/* Checks if labels have valid characters */
	public static String label_check(String label) throws Exception{
		for(int i = 0; i < label.length(); i++){
			char c = label.charAt(i);
			if ((c > 57 || c <48) && (c < 97 || c > 122) && (c < 65 || c > 90)){
				throw new Exception("Not a valid label");
			}
		}
		return label;
	}
	
	/* Checks for labels */
	public static Boolean is_label(String s){
		if(s.contains(":")){
			return true;
		}
		else{
			return false;
		}
	}
	
	/* Check for valid instructions */
	public static void check_instruction(String instr, String[] instructions)throws Exception{
	    if(!Arrays.asList(instructions).contains(instr)){
	       throw new Exception("NOT A VALID COMMAND:");
	    }
	}
	
	/* Find opcode for instruction */
	public static String opcode(String cmd, String arg1, String arg2, String arg3){
		String opcode = null;
		String a1 = arg_opcode(arg1);   /* rd */
		String a2 = arg_opcode(arg2);   /* rs */      /* rd rs rt */
		String a3 = arg_opcode(arg3);   /* rt */
		switch(cmd){
		case "and":
			opcode = "000000 " + a2 + " " +  a3 + " " + a1 + " 00000 100100";   /* rs rt rd */
			break;
		case "or":
			opcode = "000000 " + a2 + " " + a3  + " " + a1 + " 00000 100101";
			break;
		case "add":
			opcode = "000000 " + a2 + " " +  a3 + " " + a1 + " 00000 100000";
			break;
		case "sub":
			opcode = "000000 " + a2 + " " +  a3 + " " + a1 + " 00000 100010";
			break;
		case "slt":
			opcode = "000000 " + a2 + " " +  a3 + " " + a1 + " 00000 101010";
			break;
		case "lw":
			opcode = "100011 " + a2 + " rt offset";
			break;
		case "sw":
			opcode = "101011 " + a2 + " " + a3 + " offset";
			break;
		case "j":
			opcode = "000010 target";
			break;
		case "jr":
			opcode = "000000 " + a2 + " 000000000000000 001000";
			break;
		case "jal":
			opcode = "000011 target";
			break;
		}
		return opcode;
	}
	
	/* Find opcode for instruction */
	public static String jump_opcode(String cmd, String arg1, String imm){
		String opcode = null;
		String a1 = arg_opcode(arg1);   /* rt */
		switch(cmd){
		case "j":
			opcode = "000010 " + imm;
			break;
		case "jr":
			opcode = "000000 " + a1 + " 000000000000000 001000";
			break;
		case "jal":
			opcode = "000011 " + imm;
			break;
	    }
		return opcode;
	}
	
	/* Find opcode for instruction */
	public static String immediate_opcode(String cmd, String arg1, String arg2, String imm){
		String opcode = null;
		String a1 = arg_opcode(arg1);   /* rt */
		String a2 = arg_opcode(arg2);   /* rs  */      /* rd rs rt */
		switch(cmd){
		case "addi":
			opcode = "001000 " + a2 + " " + a1 + " " + imm;
			break;
		case "sw":
			opcode = "101011 " + a2 + " " + a1 + " " + imm;
			break;
		case "lw":
			opcode = "100011 " + a2 + " " + a1 + " " + imm;
			break;
	    }
		return opcode;
	}
	
	/* Find opcode for instruction */
	public static String sll_opcode(String cmd, String arg1, String arg2, String arg3, String imm){
		String opcode = null;
		String a1 = arg_opcode(arg1);
		String a2 = arg_opcode(arg2);   /* rt */
		String a3 = arg_opcode(arg3);   /* rs */      /* rd rs rt */
		switch(cmd){
		case "sll":
			opcode = "000000 " + "00000 " + a2  + " " + a1 + " " +  imm + " 000000";        /* sa is shift amount */
			break;
		}
		return opcode;
	}
	
	/* Find opcode for instruction */
	public static String branch_opcode(String cmd, String arg1, String arg2, String arg3){
		String opcode = null;
		String a1 = arg_opcode(arg1);   /* rd */
		String a2 = arg_opcode(arg2);   /* rs */      /* rd rs rt */
		switch(cmd){
        case "beq":
			opcode = "000100 " + a1 + " " +  a2 + " " + arg3;
			break;
		case "bne":
			opcode = "000101 " + a1 + " " + a2 + " " + arg3;
			break;
		}
		return opcode;
	}
	
	
	/* Find opcode for instruction */
	public static String arg_opcode(String cmd){
		String opcode = null;
		switch(cmd){
		case "zero":
			opcode = "00000";
			break;
		case "0":
			opcode = "00000";
			break;
		case "v0":
			opcode = "00010";
			break;
		case "v1":
			opcode = "00011";
			break;
		case "a0":
			opcode = "00100";
			break;
		case "a1":
			opcode = "00101";
			break;
		case "a2":
			opcode = "00110";
			break;
		case "a3":
			opcode = "00111";
			break;
		case "t0":
			opcode = "01000";
			break;
		case "t1":
			opcode = "01001";
			break;
		case "t2":
			opcode = "01010";
			break;
		case "t3":
			opcode = "01011";
			break;
		case "t4":
			opcode = "01100";
			break;
		case "t5":
			opcode = "01101";
			break;
		case "t6":
			opcode = "01110";
			break;
		case "t7":
			opcode = "01111";
			break;
		case "t8":
			opcode = "11000";
			break;
		case "t9":
			opcode = "11001";
			break;
		case "s0":
			opcode = "10000";
			break;
		case "s1":
			opcode = "10001";
			break;
		case "s2":
			opcode = "10010";
			break;
		case "s3":
			opcode = "10011";
			break;
		case "s4":
			opcode = "10100";
			break;
		case "s5":
			opcode = "10101";
			break;
		case "s6":
			opcode = "10110";
			break;
		case "s7":
			opcode = "10111";
			break;
		case "sp":
			opcode = "11101";
			break;
		case "ra":
			opcode = "11111";
			break;
	    case "$zero":
		   opcode = "00000";
		   break;
	    case "$0":
		   opcode = "00000";
		   break;
	    case "$v0":
			opcode = "00010";
			break;
		case "$v1":
			opcode = "00011";
			break;
		case "$a0":
			opcode = "00100";
			break;
		case "$a1":
			opcode = "00101";
			break;
		case "$a2":
			opcode = "00110";
			break;
		case "$a3":
			opcode = "00111";
			break;
		case "$t0":
			opcode = "01000";
			break;
		case "$t1":
			opcode = "01001";
			break;
		case "$t2":
			opcode = "01010";
			break;
		case "$t3":
			opcode = "01011";
			break;
		case "$t4":
			opcode = "01100";
			break;
		case "$t5":
			opcode = "01101";
			break;
		case "$t6":
			opcode = "01110";
			break;
		case "$t7":
			opcode = "01111";
			break;
		case "$t8":
			opcode = "11000";
			break;
		case "$t9":
			opcode = "11001";
			break;
		case "$s0":
			opcode = "10000";
			break;
		case "$s1":
			opcode = "10001";
			break;
		case "$s2":
			opcode = "10010";
			break;
		case "$s3":
			opcode = "10011";
			break;
		case "$s4":
			opcode = "10100";
			break;
		case "$s5":
			opcode = "10101";
			break;
		case "$s6":
			opcode = "10110";
			break;
		case "$s7":
			opcode = "10111";
			break;
		case "$sp":
			opcode = "11101";
			break;
		case "$ra":
			opcode = "11111";
			break;
	   }
	return opcode;
	}
}
