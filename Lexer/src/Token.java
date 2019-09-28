
public enum Token {

	comma(6, ","),
	semi(7, ";"),
	kprog(19, "prog"),
	kmain(11, "main"),
	kfcn(12, "fcn"),
	kclass(13, "class"),
	kfloat(15,"float"),
	kint(16, "int"),
	kstring(17, "string"),
	kif(18, "if"),
	kelseif(19, "elseif"),
	kelse(20, "else"),
	kwhile(21, "while"),
	kinput(22, "input"),
	kprint(23, "print"),
	knew(24, "new"),
	kreturn(25, "return"),
	kvar(26, "var"),
	angle1(31, "<"),
	angle2(32, ">"),
	brace1(33, "{"),
	brace2(34, "}"),
	bracket1(35, "["),
	bracket2(36, "]"),
	parens1(37, "("),
	parens2(38, ")"),
	aster(41, "*"),
	caret(42, "^"),
	colon(43, ":"),
	dot(44, "."),
	equal(45, "="),
	minus(46, "-"),
	plus(47, "+"),
	slash(48, "/"),
	ampersand(49, "&"),
	oparrow(51, "->"),
	opeq(52, "=="),
	opne(53, "!="),
	ople(54, "<+"),
	opge(55, ">="),
	opshl(56, "<<"),
	opshr(57, ">>");
	
	int tokenNum;
	String tokenVal;
	
	Token(int tokenNum, String tokenVal){
		this.tokenNum = tokenNum;
		this.tokenVal = tokenVal;
	}
}
