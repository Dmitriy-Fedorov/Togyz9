
public class Kazan {
	private int score = 0;
	private int previousScore=0;
	Kazan(){
		
	}
	
	boolean checkWin(){
		if(score > 81)	return true;
		else return false;
	}
	
	int checkScore(){
		return score;
	}
	
	double getNormalizedScore(){
		double a = score;
		return a/81;
	}
	
	void reset(){
		score = 0;
		previousScore=0;
	}
	
	void put(int a){
		previousScore = score;
		score += a;
		
	}
	
	int getIncrement(){
		return score - previousScore;
	}
	

}
