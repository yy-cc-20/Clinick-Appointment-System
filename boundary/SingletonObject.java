// Singleton design pattern: create only one object in the whole program

import java.util.Scanner;

public class SingleObject {
    public static final Scanner scanner = new Scanner(System.in);
    // final: keep the scanner tied to System.in
}
