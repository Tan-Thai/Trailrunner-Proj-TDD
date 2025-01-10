package se.iths;

import java.util.Scanner;
import se.iths.utility.ScannerSingleton;

public class Main {
    public static Scanner scanner;

    public static void main(String[] args) {
        scanner = ScannerSingleton.getInstance().getScanner();


        ScannerSingleton.getInstance().closeScanner();
    }

}
