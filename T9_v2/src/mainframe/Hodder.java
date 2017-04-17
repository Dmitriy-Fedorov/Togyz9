package mainframe;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import ai_package.AI_brut;
import ai_package.AI_hashBrut;




public class Hodder {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	private static Desk player[] = {new Desk("Player 0"),new Desk("Player 1")};
		
	public static int doMove(boolean hod,int n, Desk player_1,Desk player_0){
		player[1] = player_1;	//true hod
		player[0] = player_0;	//false hod
		if(hod){
			player[1].move(n, player[0],true);
		}else{
			player[0].move(n, player[1],true);
		}
		return 0;
	}
	
	
	/**
	 * @param hod
	 * @param opponent
	 * @param player_1
	 * @param player_0
	 * @param settings for AI_hashBrut:{deep,cutting breadth}
	 * @return
	 */
	public static int selectPlayer(boolean hod, String opponent,Desk player_1,Desk player_0,int[] setting){
		
		player[1] = player_1;
		player[0] = player_0;
		int n_1to9 = -1,hod_;
		AI_brut brut = new AI_brut();
		AI_hashBrut hashBrut = new AI_hashBrut();
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
			System.out.println(n_1to9);
			
		}else if(opponent.equals("AI_brut")){
			n_1to9 = brut.brut(player[1], player[0], hod, 0 /*dummy*/);
			System.out.println(n_1to9);
			
		}else if(opponent.equals("AI_deep1")){
			n_1to9 = brut.deepBrut(player[1], player[0], hod, 1);
			while(player[hod_].checkZero(n_1to9-1)){
				n_1to9 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
			}
			System.out.println(n_1to9);
			
		}else if(opponent.equals("AI_hashBrut")){
			n_1to9 = hashBrut.hashBrut(setting,player[1], player[0], hod);
			while(player[hod_].checkZero(n_1to9-1)){
				n_1to9 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
			}
			//System.out.println(ANSI_YELLOW+"Hodder.selectPlayer n1to9="+n_1to9+ANSI_RESET);
		}
		
		return n_1to9;
	}

}
