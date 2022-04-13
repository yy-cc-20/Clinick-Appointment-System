package boundary;

import java.util.Scanner;

/*
 * 1. Use singleton design pattern for Scanner object
 * 
 * 2. Methods in this class: similar to scanner's methods but will not throw exception.
 * 		Use these method instead of the scanner's methods, 
 * 		so the program will not stop because of the thrown exception.
 */

public class SingletonScanner {
    private static final Scanner scanner = new Scanner(System.in); // final: keep the scanner tied to System.in
    
    // Catch the exception thrown by scanner, so the program will not stop because of the exception
    public static String nextLine() {
    	String str = "";
    	try {
    		str = SingletonScanner.scanner.nextLine();
    	} catch(Exception e) {
    		//e.printStackTrace();
    	}
    	return str;
    }
   
    // Catch the exception thrown by scanner, so the program will not stop because of the exception
    public static boolean hasNextLine() {
    	boolean bool = false;
    	try {
    		bool = SingletonScanner.scanner.hasNextLine();
    	} catch(Exception e) {
    		//e.printStackTrace(); // Hide the error message because we do not want the user to see it
    	}
    	return bool;
    }
    
    // Catch the exception thrown by scanner, so the program will not stop because of the exception
    public static void close() {
    	try {
    		SingletonScanner.scanner.close();
    	} catch(Exception e) {
    		//e.printStackTrace();
    	}
    }
    /*
    // SingletonScanner test
    public static void main(String[] args) {
    	System.out.println(SingletonScanner.nextLine());
    }*/
}
