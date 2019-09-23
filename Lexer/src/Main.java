import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// The name of the file to open.
        String fileName = args[1];

        try {
        	File code = new File(fileName);
        	Scanner sc  = new Scanner(code);
        	// TODO store each char in ArrayList for future use?
                    
        }
        catch(FileNotFoundException e) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException e) {
            System.out.println("Error reading file '" + fileName + "'");                   
            // e.printStackTrace();
        }

	}

}
