package AI;

import java.util.Comparator;

import Interface.ANSI_color;
import mainframe.Desk;

public class KeyDesk implements Comparator<KeyDesk>, Comparable<KeyDesk>,ANSI_color{
	private Desk game;
	private String key;
	private boolean cheyHod;
	
	KeyDesk(Desk game,String key){
		this.game = game;
		this.key = key;
	}
	
	Desk getDesk(){
		return game;
	}
	
	String getKey(){
		return key;
	}

	@Override
	public int compare(KeyDesk o1, KeyDesk o2) {
		Desk d1 = o1.getDesk();
		Desk d2 = o2.getDesk();
		cheyHod = !d1.chey_hod();
		
		int delta1 = d1.get_score(1) - d1.get_score(0);
		int delta2 = d2.get_score(1) - d2.get_score(0);
		
		if(!cheyHod){
			delta1 = -delta1;
			delta2 = -delta2;
		}
		return delta1-delta2;
	}
	
	@Override
	public String toString(){
		return game.toString(testColorScheme1);
		
	}

	@Override
	public int compareTo(KeyDesk o) {
		Desk d1 = this.getDesk();
		Desk d2 = o.getDesk();
		cheyHod = d1.chey_hod();
		
		int delta1 = d1.get_score(1) - d1.get_score(0);
		int delta2 = d2.get_score(1) - d2.get_score(0);
		int deltaSum1 = d1.get_ball_sum(1) - d1.get_ball_sum(0);
		int deltaSum2 = d2.get_ball_sum(1) - d2.get_ball_sum(0);
		
		if(!cheyHod){
			delta1 = -delta1;
			delta2 = -delta2;
			deltaSum1 = -deltaSum1;
			deltaSum2 = -deltaSum2;
		}
		if(delta1==delta2){
			return deltaSum1-deltaSum2;
		}
		return delta1-delta2;
	}

}
