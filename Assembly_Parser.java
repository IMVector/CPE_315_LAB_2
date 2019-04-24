import java.io.*;
import java.util.*;

public class Assembly_Parser {

	public static void main(String[] args) {
		List<String>list = new ArrayList<String>();
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
	        	       line = line.split("#")[0];   
	        	       list.add(line.trim());
	        		}
	        		else{list.add(line.trim());}
	        	}                                                  /* Add the line to the list                        */
	  	
	        }
	        printer.write("[");
	        for(String str:list){                                  /* This loops writes out the elements that were    */
	        	printer.write(str);                                /* added to the list into a output text file.      */
	        	printer.write(",  ");
	            printer.write("\n");                                   /* Format: [t1, t2, t3]                            */
	        }
	        printer.write("]");
	        sc.close();
	        printer.close();
		}
		catch(FileNotFoundException e){
			System.err.println("File not found.");
		}

	}

}
