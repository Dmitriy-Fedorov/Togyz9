package mainframe;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import AI.AI_hash;
import Interface.ANSI_color;

public class Hodder implements ANSI_color{
	
	private Desk game;
	private String igrok_1;
	private String igrok_2;
	private int depth=3,width=1;
	public Hodder(Desk desk){
		this.game = desk;
		this.igrok_1 = desk.get_ID(0);
		this.igrok_2 = desk.get_ID(1);
	}
	
	public void doMove(){
		//true  p2_hod
		//false p1_hod
		int n = -1;
		if(game.chey_hod()){
			n = selectPlayer(igrok_2);
			game.move(1, n);
		}else{
			n = selectPlayer(igrok_1);
			game.move(0, n);
		}
		if(n == -1)System.err.println("Hodder.doMove: n=-1");
		//return 0;
	}
	
	
	
	/**
	 * @param opponent
	 * @return
	 */
	public int selectPlayer(String opponent){
		
		int n_1to9 = -1;
		Scanner sc = new Scanner(System.in);
		AI_hash hash = new AI_hash(game);
		
		if(opponent.equals("human")){
			n_1to9 = Integer.parseInt(Debug.getInput(sc,game.get_CellSize()));	
			//in order to check if cell empty
			while(game.checkZero(n_1to9-1)){
				System.out.print("Cell is empty or tuzdyk.\nTry another cell:\n");
				n_1to9 = Integer.parseInt(Debug.getInput(sc, game.get_CellSize()));
			}
			
		}else if(opponent.equals("AI_random")){
				n_1to9 = ThreadLocalRandom.current().nextInt(1, game.get_CellSize() + 1);
			while(game.checkZero(n_1to9-1)){
				n_1to9 = ThreadLocalRandom.current().nextInt(1, game.get_CellSize() + 1);
			}
			System.out.println(n_1to9);
			
		}else if(opponent.equals("AI_hash")){
			n_1to9 = hash.hash(depth,width);
		}
		return n_1to9;
	}

}
