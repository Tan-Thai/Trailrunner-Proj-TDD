package se.iths.utility;

import java.util.Scanner;

public class ScannerSingleton {
    private static ScannerSingleton instance;
    private final Scanner scanner;

    private ScannerSingleton() {
        scanner = new Scanner(System.in);
    }

    public static ScannerSingleton getInstance() {
        if (instance == null) {
            synchronized (ScannerSingleton.class) {
                if (instance == null) {
                    instance = new ScannerSingleton();
                }
            }
        }
        return instance;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
