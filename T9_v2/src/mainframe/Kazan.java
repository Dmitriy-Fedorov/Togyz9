package mainframe;

public class Kazan {
	private int score = 0;
	private int previousScore=0;
	boolean editable = false;
	
	public Kazan(){
		
	}
	
	public Kazan(boolean editable){
		this.editable = editable;
	}
	
	public Kazan(int score,boolean editable){
		this.editable = editable;
		this.score = score;
	}
	
	public void setKazan(int a){
		if(editable){
			score = a;
		}else{
			System.out.println("This kazan is not editable");
		}
	}
	
	public boolean checkWin(){
		if(score > 81)	return true;
		else return false;
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
