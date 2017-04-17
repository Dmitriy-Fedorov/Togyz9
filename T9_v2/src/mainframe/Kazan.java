package mainframe;

public class Kazan {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	private int score = 0;
	private int previousScore=0;
	boolean editable = false;
	private int winCount = 0;
	
	public Kazan(){
		
	}
	
	public Kazan(boolean editable){
		this.editable = editable;
	}
	
	public Kazan(int score,boolean editable){
		this.editable = editable;
		this.score = score;
	}
	
	/**
	 * @param score
	 * set score in Kazan to desired value, if Kazan is editable
	 */
	public void setKazan(int score){
		if(editable){
			this.score = score;
		}else{
			System.out.println(ANSI_RED+"This kazan is not editable"+ANSI_RESET);
		}
	}
	
	public boolean checkWin(boolean dummy){
		if(score > 81)	return true;
		else return false;
	}
	
	void winCounterPlusPlus(){
		winCount++;
	}
	
	int getWinCount(){
		return winCount;
	}
	
	public int checkScore(){
		return score;
	}
	
	public double getNormalizedScore(){
		double a = score;
		return a/81;
	}
	
	public void reset(){
		score = 0;
		previousScore=0;
	}
	
	public void put(int a){
		previousScore = score;
		score += a;	
	}
	
	public int getIncrement(){
		return score - previousScore;
	}
}
