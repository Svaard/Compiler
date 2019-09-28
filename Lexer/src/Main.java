import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;

public class Main {

	public static void main(String[] args) {
		// The name of the file to open.
		String fileName = args[0];
		ArrayList<ArrayList<Character>> lines = new ArrayList<ArrayList<Character>>();
		ArrayList<Character> chars;
		Scanner sc = null;
		try {
			//File code = new File(fileName);
			sc = new Scanner(new BufferedReader(new FileReader(fileName)));
			while(sc.hasNextLine()) {
				chars = new ArrayList<Character>();
				String scanned = sc.nextLine();
				char[] chs = scanned.toCharArray();
				for(char c : chs) {
					chars.add(c);
					//System.out.print(c);
				}
				lines.add(chars);
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("Unable to open file '" + fileName + "'");                
		}
		catch(@SuppressWarnings("hiding") IOException e) {
			System.out.println("Error reading file '" + fileName + "'");                   
		}
		finally {
			if (sc != null) {
				sc.close();
			}
		}
		Lexer a5Lexicon = new Lexer(lines);
		try {
			File output = new File("output.alex");
			PrintWriter printWriter = new PrintWriter(output);
			// get arraylist of arraylists from lexer object and print to file
			for (int i = 0; i < a5Lexicon.lines.size(); i++){
				printWriter.print(a5Lexicon.prettyPrint(a5Lexicon.lines.get(i), i));
			}
			printWriter.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Unable to open file");
		}
	}
}
