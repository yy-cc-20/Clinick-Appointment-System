package boundary;

import java.util.Scanner;

public class SingletonScanner {
    private static final Scanner scanner = new Scanner(System.in);
    // final: keep the scanner tied to System.in
    
    // Catch the exception thrown by scanner, so the program will not stop because of the exception
    public static String nextLine() {
    	String str = "";
    	try {
    		str = SingletonScanner.scanner.nextLine();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return str;
    }
   
    public static boolean hasNextLine() {
    	boolean bool = false;
    	try {
    		bool = SingletonScanner.scanner.hasNextLine();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return bool;
    }
    
    public static void close() {
    	try {
    		SingletonScanner.scanner.close();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public static void main(String[] args) {
    	System.out.println(SingletonScanner.nextLine());
    }
}
