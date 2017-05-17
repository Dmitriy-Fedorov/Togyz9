package mainframe;

import java.util.Scanner;

public class Debug {
	
	
	public static String getInput(Scanner in, int cellSize) { // Get valid user input
		
	    String text = "";
	    while (true) { // Keep looping until valid input is found
	    	
	        text = in.nextLine(); // Get input from stdin
	        if(text.equals("exit")){
	        	System.exit(0);
	        }
	        if(isInteger(text,cellSize)) // Check if they put in integer
	            break; // Exit loop
	        System.out.print("Try again:\n"); // Wasn't valid, prompt again
	    } 
	    return text; // Return valid user input
	}
	
	
	private static boolean isInteger(String str, int cellSize) { // Check if string is integer
	    try {
	        int a = Integer.parseInt(str); // If this doesn't fail then it's integer
	        if(a<1 || a>cellSize)
	        	return false;		//input should be between 0 and 8
	        else     return true;
	    } catch(NumberFormatException e) {
	        return false; // Wasn't integer
	    }
	}
	
	public static boolean isInteger(String str) { // Check if string is integer
	    try {
	        Integer.parseInt(str); // If this doesn't fail then it's integer
	        return true;
	    } catch(NumberFormatException e) {
	        return false; // Wasn't integer
	    }
	}
	

}

