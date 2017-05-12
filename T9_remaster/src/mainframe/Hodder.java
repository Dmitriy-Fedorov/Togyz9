package mainframe;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Hodder implements ANSI_color{
	
	private Desk game;
	
	public Hodder(Desk desk){
		this.game = desk;
	}
	
	public int doMove(int n){
		//true p2_hod
		//false p1_hod
		if(game.chey_hod()){
			game.move(1, n, true);
		}else{
			game.move(0, n, true);
		}
		return 0;
	}
	
	
	
	/**
	 * @param opponent
	 * @return
	 */
	public int selectPlayer(String opponent){
		
		int n_1to9 = -1;
		Scanner sc = new Scanner(System.in);
		
		if(opponent.equals("human")){
			n_1to9 = Integer.parseInt(Debug.getInput(sc));	
			//in order to check if cell empty
			while(game.checkZero(n_1to9-1)){
				System.out.print("Cell is empty or tuzdyk.\nTry another cell:\n");
				n_1to9 = Integer.parseInt(Debug.getInput(sc));
			}
			
		}else if(opponent.equals("AI_random")){
				n_1to9 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
			while(game.checkZero(n_1to9-1)){
				n_1to9 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
			}
			System.out.println(n_1to9);
			
		}		
		return n_1to9;
	}

}
