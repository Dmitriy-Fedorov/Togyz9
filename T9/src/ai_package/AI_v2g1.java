package ai_package;

import java.util.ArrayList;

import main_package.Desk;
import main_package.Kazan;
import main_package.print_T9;

public class AI_v2g1 /*extends AI_v2*/{
	private static Desk player[] = {new Desk(),new Desk()};
	private static Kazan Kazan[] = {new Kazan(),new Kazan()};
	private /*static*/ Desk virtDesk[] = {new Desk(true),new Desk(true)};
	private /*static*/ Kazan virtKazan[] = {new Kazan(true),new Kazan(true)};
	private ArrayList<Desk> deskList = new ArrayList<>();
	private ArrayList<Kazan> kazanList = new ArrayList<>();
	ArrayList<int[]> arrayInt = new ArrayList<>();
	
	private AI_v2 gen[] = {new AI_v2(virtDesk[1],virtDesk[0],virtKazan[1],virtKazan[0]),
			new AI_v2(virtDesk[1],virtDesk[0],virtKazan[1],virtKazan[0])};
	
	public AI_v2g1(){
		System.out.println("Default constructor AI_v2g1");
	}
	
	public AI_v2g1(Desk player_1,Desk player_0,Kazan Kazan_1,Kazan Kazan_0){
		player[1] = player_1;
		player[0] = player_0;
		Kazan[1] = Kazan_1;
		Kazan[0] = Kazan_0;
	}
	
	//get array and score; and put this in virtual desks
	public void memorizeDesk(/*Desk player_1,Desk player_0,Kazan Kazan_1,Kazan Kazan_0*/){
		int[] cell1 = player[1].getDesk();
		int[] cell0 = player[0].getDesk();
		this.virtDesk[1].setDesk(cell1);
		this.virtDesk[0].setDesk(cell0);
		this.virtKazan[1].setKazan(Kazan[1].checkScore());
		this.virtKazan[0].setKazan(Kazan[0].checkScore());
		/*
		System.out.println("Virtual desk print:");
		print_T9.printDesk(1,virtDesk[1],virtDesk[0],virtKazan[1],virtKazan[0]);
		System.out.println("Real desk print:");
		*/
	}
	
	public void storeDesk(/*Desk player_1,Desk player_0,Kazan Kazan_1,Kazan Kazan_0*/){
		int[] cell[] = {player[1].getDesk(),player[0].getDesk()};
		int[] kazan = {Kazan[1].checkScore(),Kazan[0].checkScore()};
	}
	
	public void memorizeDeskAsList(int index,Desk d1,Desk d0){
		deskList.add(index,new Desk(true));
		deskList.add(index+1,new Desk(true));
		kazanList.add(index,new Kazan(true));
		kazanList.add(index+1,new Kazan(true));
		int[] cell1 = d1.getDesk();
		int[] cell0 = d0.getDesk();
		deskList.get(index).setDesk(cell0);
		deskList.get(index+1).setDesk(cell1);
		kazanList.get(index).setKazan(d0.checkScore());
		kazanList.get(index+1).setKazan(d1.checkScore());
	}
	
	void clearDeskList(){
		deskList.clear();
		System.gc();
	}
	
	public int sandboxForGeneration0(int player_){
		int score=0, scoreMax=0;
		int store[] = {-1,-1,-1};
		this.memorizeDeskAsList(0, player[1], player[0]);
		arrayInt.add(gen[player_].dumbTreePlay_v2(player_, "get_array", 1, false));
		/* no problem
		System.out.println("\ndepth 0:");
		print_T9.printDesk(player_, deskList.get(1), deskList.get(0),
				kazanList.get(1), kazanList.get(0));
		*/
		for(int i=0;i<3;i++){
			
			System.out.println("\n depth i: 1 and 0: " + arrayInt.get(0)[i]);
			print_T9.printDesk(player_, deskList.get(1), deskList.get(0),
					kazanList.get(1), kazanList.get(0));
			
			this.doMoveAndAddArray(player_, arrayInt.get(0)[i],0);
			this.memorizeDeskAsList(2, deskList.get(0), deskList.get(1));
			/*
			System.out.println("\n depth i: 3 and 2: "+ arrayInt.get(0)[i]);
			print_T9.printDesk(player_, deskList.get(3), deskList.get(2),
					kazanList.get(3), kazanList.get(2));
			*/
			for(int j=0;j<3;j++){
				/*
				System.out.println("\n depth j: 1 and 0: "+ arrayInt.get(1)[i]);
				print_T9.printDesk(player_, deskList.get(1), deskList.get(0),
						kazanList.get(1), kazanList.get(0));
				*/
				/*
				System.out.println("\n depth j do: 3 and 2: "+ arrayInt.get(1)[i]);
				print_T9.printDesk(player_, deskList.get(3), deskList.get(2),
						kazanList.get(3), kazanList.get(2));
				*/

				this.doMoveAndAddArray((player_+1)%2, arrayInt.get(1)[i],2);
				/*
				System.out.println("\n depth j posle: 3 and 2: "+ arrayInt.get(1)[i]);
				print_T9.printDesk(player_, deskList.get(3), deskList.get(2),
						kazanList.get(3), kazanList.get(2));
				*/
				gen[player_].setPlayground(deskList.get(3), deskList.get(2), kazanList.get(3), kazanList.get(2));
				
				for(int k=0; k<3;k++){
					score = gen[player_].dumbTreePlay_v2(player_, 1, 1, false);
					if(score>scoreMax){
						scoreMax = score;
						store[0]=i;
						store[1]=j;
						store[2]=k;
					}
					
				}
				
				//this.memorizeDeskAsList(2, deskList.get(0), deskList.get(1));
			}
			
			this.memorizeDeskAsList(0, player[1], player[0]);
			
		}
		
		return store[0];
	}
	
	void doMoveAndAddArray(int player_,int hod, int index){
		print_T9.doMove(player_, hod, deskList.get(index+1), deskList.get(index), kazanList.get(index+1), kazanList.get(index), true);
		arrayInt.add(gen[(player_+1)%2].dumbTreePlay_v2((player_+1)%2, "get_array", 1, false));
	}
	
	
	
}
