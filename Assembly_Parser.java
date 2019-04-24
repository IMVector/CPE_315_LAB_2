import java.io.*;
import java.util.*;

public class Assembly_Parser {

	public static void main(String[] args) throws Exception {
		List<String>list = new ArrayList<String>();
		int i = 0;
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
	        		if(line.contains("#")){
	        	       line = line.split("#")[0];                 /* Gets rid of any comments that happens after instructions */
	        	       if(is_label(line)){
		        	       String[] parts = line.split(":");       /* Split the string at the colon */
		        	       String part1 = parts[0];                /* The first part of the split string */
		        	       list.add(part1.trim());
		        	       String part2 = parts[1];                /* The second part of the split string */
		        	       list.add(part2.trim());
		        	    }
	        	       else{
	        	          list.add(line.trim());                     /* Trim cuts the whitespace before and after the string */
	        	          i++;
	        	       }
	        		}
	        		else{
	        			if(is_label(line)){
		        	          String[] parts = line.split(":");       
		        	          String part1 = parts[0];                
		        	          list.add(part1.trim());
		        	          String part2 = parts[1];
		        	          if(part2.trim().isEmpty()){}
		        	          else{list.add(part2.trim());}
	        			}
	        			else{
	        			   list.add(line.trim());                    
	        			   i++;
	        			}
	        		}
	        	}                                                  /* Add the line to the list                             */
	        }
	        printer.write("[");
	        for(String str:list){                                  /* This loops writes out the elements that were    */
	        	printer.write(str);                                /* added to the list into a output text file.      */
	        	printer.write(",  ");
	            printer.write("\n");                               /* Format: [t1, t2, t3]                            */
	        }
	        printer.write("]");
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

}
