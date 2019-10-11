import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.String;

public class Lexer {
	ArrayList<ArrayList<Character>> lines;
	private static int charCount; // keep track of current character from input stream
	private static int peekPos; // keep track of character we are looking forward to
	private static Map<Integer, String> lexicon = new HashMap<Integer, String>();

	public Lexer(ArrayList<ArrayList<Character>> lines) {
		this.lines = lines;
		lexicon.put(6, ",");
		lexicon.put(7, ";");
		lexicon.put(10, "prog");
		lexicon.put(11, "main");
		lexicon.put(12, "fcn");
		lexicon.put(13, "class");
		lexicon.put(15, "float");
		lexicon.put(16, "int");
		lexicon.put(17, "string");
		lexicon.put(18, "if");
		lexicon.put(19, "elseif");
		lexicon.put(20, "else");
		lexicon.put(21, "while");
		lexicon.put(22, "input");
		lexicon.put(23, "print");
		lexicon.put(24, "new");
		lexicon.put(25, "return");
		lexicon.put(26, "var");
		lexicon.put(31, "<");
		lexicon.put(32, ">");
		lexicon.put(33, "{");
		lexicon.put(34, "}");
		lexicon.put(35, "[");
		lexicon.put(36, "]");
		lexicon.put(37, "(");
		lexicon.put(38, ")");
		lexicon.put(41, "*");
		lexicon.put(42, "^");
		lexicon.put(43, ":");
		lexicon.put(44, ".");
		lexicon.put(45, "=");
		lexicon.put(46, "-");
		lexicon.put(47, "+");
		lexicon.put(48, "/");
		lexicon.put(51, "->");
		lexicon.put(52, "==");
		lexicon.put(53, "!=");
		lexicon.put(54, "<=");
		lexicon.put(55, ">=");
		lexicon.put(56, "<<");
		lexicon.put(57, ">>");
	}

	//tokenize a single line represented by an ArrayList of chars
	public static ArrayList<String> tokenize(ArrayList<Character> line){
		ArrayList<String> tokens = new ArrayList<String>();
		for(charCount = 0; charCount < line.size(); charCount++) {
			//peek position begins at the next character unless there isn't one; otherwise, peek() will return ' '
			if(charCount < line.size() - 1) {
				peekPos = charCount + 1;
			} 
			else {
				peekPos = -1;
			}
			//get current character
			char c = line.get(charCount);
			//first check for whitespace, skip further checks if found
			if(c == ' ' || c == '\t') {
				continue;
			}
			//check if character is a comment, skip the rest of the line if found
			else if(c == '/') {
				if(peek(line) == '/') {
					break;
				}
				//not a comment, but fSlash
				tokens.add(Character.toString(c));
			}
			//check for identifier (or keyword) - LU
			else if(Character.isLetter(c) || c == '_') {
				while(peekPos < line.size()) {
					//LUD *
					if(Character.isLetter(peek(line)) || c == '_' || Character.isDigit(peek(line))) {
						advance(line);
					} 
					else {
						break;
					}
				}
				//identifier ran to the end of the line
				if(peekPos == -1) {
					peekPos = line.size() - 1;
				}
				String id = "";
				for(int i = charCount; i < peekPos; i++) {
					id += line.get(i);
				}
				tokens.add(id);
				charCount = peekPos - 1;
			}
			//check for positive int or float
			else if(Character.isDigit(c)) {
				while(peekPos < line.size()) {
					if(Character.isDigit(peek(line)) || peek(line) == '.') {
						advance(line);
					} 
					else {
						break;
					}
				}
				//number ran to the end of the line
				if(peekPos == -1) {
					peekPos = line.size() - 1;
				}
				String num = "";
				for(int i = charCount; i < peekPos; i++) {
					num += line.get(i);
				}
				tokens.add(num);
				charCount = peekPos - 1;
			}
			//begin symbol checks
			else if(c == '-') {
				//arrow
				if(peek(line) == '>') {
					tokens.add("->");
					charCount++;
				} 
				//minus
				else if(peek(line) == ' ') {
					tokens.add("-");
					charCount++;
				}
				//negative integer
				else if(Character.isDigit(peek(line))) {
					advance(line);
					while(peekPos < line.size()) {
						if(Character.isDigit(peek(line)) || peek(line) == '.') {
							advance(line);
						} 
						else {
							break;
						}
					}
					//number ran to the end of the line
					if(peekPos == -1) {
						peekPos = line.size() - 1;
					}
					String negNum = "";
					for(int i = charCount; i < peekPos; i++) {
						negNum += line.get(i);
					}
					tokens.add(negNum);
					charCount = peekPos;
				}
			} 
			else if(c == '=') {
				//equals
				if(peek(line) == '=') {
					tokens.add("==");
					charCount++;
				}
				//assignment op
				else {
					tokens.add("=");
				}
			} 
			else if(c == '!') {
				//not equals
				if(peek(line) == '=') {
					tokens.add("!=");
					charCount++;
				}
				//standalone ! (will give an error token, but must be parsed)
				else {
					tokens.add("!");
				}
			} 
			else if(c == '<') {
				//less than or equal
				if(peek(line) == '=') {
					tokens.add("<=");
					charCount++;
				}
				//shift-left
				else if(peek(line) == '<') {
					tokens.add("<<");
					charCount++;
				}
				//paired angle1
				else {
					tokens.add("<");
				}
			} 
			else if(c == '>') {
				//greater than or equal
				if(peek(line) == '=') {
					tokens.add("<=");
					charCount++;
				}
				//shift-right
				else if(peek(line) == '>') {
					tokens.add(">>");
					charCount++;
				}
				//paired angle2
				else {
					tokens.add(">");
				}
			} 
			else if(c == '"') {
				while(peekPos < line.size()) {
					if(peek(line) == '"') {
						break;
					} 
					else {
						advance(line);
						if(peekPos == -1) {
							break;
						}
					}
				}
				//double-quote not ended
				if(peekPos == -1) {
					System.out.println("ERROR: Double-quote opened but not ended on the same line");
					System.exit(1);
				}
				//add string literal token
				String lit = "";
				for(int i = charCount; i <= peekPos; i++) {
					lit += line.get(i);
				}
				tokens.add(lit);
				charCount = peekPos;
			}
			//other symbols do not need additional checks
			else {
				tokens.add(Character.toString(c));
			}
		}
		return tokens;
	}

	public static char peek(ArrayList<Character> line) {
		if(peekPos == -1) {
			return ' ';
		}
		return line.get(peekPos);
	}

	public static void advance(ArrayList<Character> line) {
		if(peekPos == line.size() - 1) {
			peekPos = -1;
		} 
		else {
			peekPos ++;
		}
	}

	public static int getToken(String parsed) {
		try {
			Integer.parseInt(parsed);
			return 3;
		} 
		catch(Exception e) {}
		try {
			Float.parseFloat(parsed);
			return 4;
		} 
		catch(Exception e) {}
		if (parsed.charAt(0) == '$') {
			return 0;
		} 
		else if (parsed.charAt(0) == '"' && parsed.charAt(parsed.length() - 1) == '"') {
			return 5;
		} 
		else if (lexicon.containsValue(parsed)) {
			for (int key : lexicon.keySet()) {
				if (lexicon.get(key).equalsIgnoreCase(parsed)) {
		    		return key;
		    		}
			}
		} 
		else if (Character.isLetter(parsed.charAt(0)) || parsed.charAt(0) == '_') {
			return 2;
		} 
		return 99;
	}

	public String prettyPrint(ArrayList<Character> line, int lineNum) {
		lineNum ++;
		ArrayList<String> tokens = tokenize(line);
		String printLine = "";
		String form = "";
		System.out.println();
		for (int i = 0; i < tokens.size(); i++) {
			int tokenValue = getToken(tokens.get(i));
			if(tokenValue == 0) {
				form = String.format("(Tok: %3d  lin= %2d  str= \"\")\r\n", tokenValue, lineNum);
				printLine += form;
				System.out.println(form);
			} 
			else if(tokenValue == 3) {
				form = String.format("(Tok: %3d  lin= %2d  str= \"%s\" int= %2s)\r\n", tokenValue, lineNum, tokens.get(i), tokens.get(i));
				printLine += form;
				System.out.println(form);
			} 
			else if(tokenValue == 4) {
				form = String.format("(Tok: %3d  lin= %2d  str= \"%s\" flo= %2s)\r\n", tokenValue, lineNum, tokens.get(i), tokens.get(i));
				printLine += form;
				System.out.println(form);
			} 
			else if(tokenValue == 5) {
				form = String.format("(Tok: %3d  lin= %2d  str= %s)\r\n", tokenValue, lineNum, tokens.get(i));
				printLine += form;
				System.out.println(form);
			} 
			else {
				form = String.format("(Tok: %3d  lin= %2d  str= \"%s\")\r\n", tokenValue, lineNum, tokens.get(i));
				printLine += form;
				System.out.println(form);
			}
		}
	  	return printLine;
	}
}
