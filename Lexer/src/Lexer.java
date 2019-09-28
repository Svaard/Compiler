import java.util.ArrayList;

public class Lexer {
	ArrayList<Character> array;
	private int charCount; // keep track of current character from input stream
	private int peekCount; // keep track of character we are looking forward to
	
	// TODO map tokens to id numbers?
	// TODO deterministic finite automata using switch statements
	
	
	
	public Lexer(ArrayList<Character> array) {
		this.array = array;
	}
	
	public char peek() {
		return array.get(charCount + 1);
	}
	
	public void advance() {
		peekCount ++;
	}
}