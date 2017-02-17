package main_package;
import java.io.IOException;
import java.util.Scanner;

import ai_package.*;

public class print_T9 {

	private static Desk player[] = {new Desk(),new Desk()};
	private static Kazan Kazan[] = {new Kazan(),new Kazan()};
	

	public static int printDesk(int hod, Desk player_1,Desk player_0,Kazan Kazan_1,Kazan Kazan_0){
		player[1] = player_1;
		player[0] = player_0;
		Kazan[1] = Kazan_1;
		Kazan[0] = Kazan_0;
		
		player[1].printDesk(1);
		System.out.print("\tPlayer 1\tScore:"+ Kazan[1].checkScore());
		
		if(hod == 1)System.out.print(" *\n");
		else System.out.print("\n");
		
		player[0].printDesk(0);
		System.out.print("\tPlayer 0\tScore:"+ Kazan[0].checkScore());
		if(hod == 0)System.out.print(" *\n");
		else System.out.print("\n");
		return 0;
	}
	

	public static int doMove(int hod,int n, Desk player_1,Desk player_0,Kazan Kazan_1,Kazan Kazan_0,boolean echo){
		player[1] = player_1;
		player[0] = player_0;
		Kazan[1] = Kazan_1;
		Kazan[0] = Kazan_0;
		System.gc();
		/*
		Scanner sc = new Scanner(System.in);
		
		while(n==player[hod].getTuzdykCellNumber()){
			System.out.print("You can not move from tuzdik.\nTry again:\n");
			n = Integer.parseInt(Debug.getInput(sc))-1;
		}
		*/
		int HOD = hod;
		int balls = player[HOD].checkValue(n);
		//doMove
		player[(HOD%2)].getDestinationCELL(n,echo,"0to8 format");
		int remainder = player[HOD].increment(n);
		
		int destCell=1;
		
		//in order to check the flow of remainder
		//System.out.println("kod1 "+remainder);
		
		//set value of selected cell to 1 or 0
		//exceptions <0 are handled in debug class
		if(balls>1)
			player[HOD].setOne(n);
		if(balls==1)
			player[HOD].setZero(n);
		
		//move balls in circle until there is 0 of them
		while(remainder > 0){
			remainder = player[(HOD+1)%2].increment(remainder,0);
			HOD++;
			//in order to check the flow of remainder
			//System.out.println("kod2 "+remainder);
		}
		
		//destination cell
		
		destCell = player[(HOD%2)].getDestinationCELL(HOD%2);
		
		
		//put into kazan
		if( (HOD%2)!=hod && player[(HOD%2)].checkValue(destCell)%2==0){
			Kazan[hod].put(player[(HOD%2)].emptying(destCell));
			if(echo)
				System.out.println("\teven");
		}else{
			if(echo)
				System.out.println("\todd");
		}
		
		//tuzdik set
		if(!player[(HOD%2)].statusTuzdyk()){

			if((HOD%2)!=hod && player[(HOD%2)].checkTuzdyk(destCell)
					&& handleSameTuzdyk(player[1],player[0],destCell)){
				//change statusTuzdyk to true and set it zero
				player[(HOD%2)].setTuzdyk(destCell);	
				Kazan[hod].put(player[(HOD%2)].emptying(destCell));
			}
		}
		
		//tuzdik handle
		if(player[0].statusTuzdyk()){
			Kazan[1].put(player[0].emptying(player[0].getTuzdykCellNumber()));
		}
		if(player[1].statusTuzdyk()){
			Kazan[0].put(player[1].emptying(player[1].getTuzdykCellNumber()));
		}
		
		return 1;
	}
	
	
	static boolean handleSameTuzdyk(Desk player_1,Desk player_0,int destCell){
		player[1] = player_1;
		player[0] = player_0;
		System.gc();
		
		if(player[0].getTuzdykCellNumber() == -1 && player[1].getTuzdykCellNumber() == -1){
			//System.out.println("-1-1-1-1-");
			return true;
		}
		else if(player[0].getTuzdykCellNumber()==destCell){
			//System.out.println(false + "0");
			return false;
		}else if(player[1].getTuzdykCellNumber()==destCell){
			//System.out.println(false + "1");
			return false;
		}else{
			//System.out.println(true);
			return true;
		}
	}
	
	public static int generateArray(int playerNum,int hod,int hodFrom
			,Desk player_1,Desk player_0,Kazan Kazan_1,Kazan Kazan_0,
			String igrok1,String igrok0){
		player[1] = player_1;
		player[0] = player_0;
		Kazan[1] = Kazan_1;
		Kazan[0] = Kazan_0;
		
		String file[] = {"pl0_"+igrok0,"pl1_"+igrok1};
		String toPrint = String.format("%3d,%3d",hod,hodFrom+1);
		for(int i=0;i<9;i++){
			toPrint = String.format("%s,%3d", toPrint,player[hod%2].checkValue(i));
		}
		toPrint = String.format("%s,%3d,%3d,%5.3f,%b", toPrint
				,player[hod%2].getArraySum()
				,Kazan[hod%2].checkScore()
				,Kazan[hod%2].getNormalizedScore()
				,Kazan[hod%2].checkWin());
		WriteFile data = new WriteFile( file[playerNum] , true );
		
		try {
			data.writeToFile( toPrint);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	
	public static int selectPlayer(int hod, String opponent,
			Desk player_1,Desk player_0,Kazan Kazan_1,Kazan Kazan_0){
		
		int n=-1;
		player[1] = player_1;
		player[0] = player_0;
		Kazan[1] = Kazan_1;
		Kazan[0] = Kazan_0;
		
		AI_v1 ai_1 = new AI_v1(player[1],player[0],Kazan[1],Kazan[0]);
		AI_v2 ai_2 = new AI_v2(player[1],player[0],Kazan[1],Kazan[0]);
		AI_v2g1 ai_2g1 = new AI_v2g1(player[1],player[0],Kazan[1],Kazan[0]);
		Scanner sc = new Scanner(System.in);
		
		if(opponent.equals("human")){
			
			n = Integer.parseInt(Debug.getInput(sc))-1;	
			//in order to check if yamka empty
			while(player[hod].checkZero(n)){
				
				System.out.print("Cell is empty or tuzdyk.\nTry another cell:\n");
				n = Integer.parseInt(Debug.getInput(sc))-1;
				
			}
			
		}else if(opponent.equals("random")){
			
			n = AI_v0.randomMove();
			while(player[hod].checkZero(n)){
				n = AI_v0.randomMove();
			}
			System.out.println((n+1));
			
		}else if(opponent.equals("smartRandom")){
			
			n=ai_1.smartRandomPlay(1);
			System.out.println((n+1));
			
		}else if(opponent.equals("client")){
			//not yet working
			n=-1;
			
		}else if(opponent.equals("Deep1")){
			n = ai_2.dumbTreePlay_v2(hod, 0, 0, false);
			ai_2.memorizeDesk();
			System.out.println((n+1));
		}else if(opponent.equals("Deep2")){
			n = ai_2g1.sandboxForGeneration0(hod);
			
			System.out.println((n+1));
		}
		
		
		
		
		return n;
	}
	
}
