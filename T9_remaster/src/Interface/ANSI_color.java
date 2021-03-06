package Interface;

public interface ANSI_color {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	String[] defaultColorScheme = {ANSI_WHITE,ANSI_GREEN,ANSI_YELLOW};
	String[] testColorScheme1 = {ANSI_GREEN,ANSI_WHITE,ANSI_YELLOW};
	String[] testColorScheme2 = {ANSI_GREEN,ANSI_BLUE,ANSI_YELLOW};
	String[] testColorScheme3 = {ANSI_GREEN,ANSI_PURPLE,ANSI_YELLOW};
	
	public static String toYellow(String s){
		return ANSI_YELLOW+s+ANSI_RESET;
	}
}
