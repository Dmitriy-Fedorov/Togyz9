package mainframe;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;




public class Hodder {
	private static Desk player[] = {new Desk("Player 0"),new Desk("Player 1")};
		
	public static int doMove(boolean hod,int n, Desk player_1,Desk player_0, boolean echo){
		player[1] = player_1;	//true hod
		player[0] = player_0;	//false hod
		if(hod){
			player[1].move(n, player[0]);
		}else{
			player[0].move(n, player[1]);
		}
		return 0;
	}
	
	
	public static int selectPlayer(boolean hod, String opponent,Desk player_1,Desk player_0){
		
		player[1] = player_1;
		player[0] = player_0;
		int n_1to9 = -1,hod_;
		if(hod)
			hod_ = 1;
		else
			hod_ = 0;
		
		Scanner sc = new Scanner(System.in);
		
		if(opponent.equals("human")){
			n_1to9 = Integer.parseInt(Debug.getInput(sc));	
			//in order to check if cell empty
			while(player[hod_].checkZero(n_1to9-1)){
				System.out.print("Cell is empty or tuzdyk.\nTry another cell:\n");
				n_1to9 = Integer.parseInt(Debug.getInput(sc));
			}
		}else if(opponent.equals("AI_random")){
				n_1to9 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
			while(player[hod_].checkZero(n_1to9-1)){
				n_1to9 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
			}
		}
		
		return n_1to9;
	}

}
