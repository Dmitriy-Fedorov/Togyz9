package ai_package;

import main_package.Desk;
import main_package.Kazan;

public class AI_v2g1 extends AI_v2{
	private static Desk player[] = {new Desk(),new Desk()};
	private static Kazan Kazan[] = {new Kazan(),new Kazan()};
	private /*static*/ Desk virtDesk[] = {new Desk(true),new Desk(true)};
	private /*static*/ Kazan virtKazan[] = {new Kazan(true),new Kazan(true)};
	//private ArrayList<Desk> reDesk = new ArrayList<>();
	//private ArrayList<Kazan> reKazan = new ArrayList<>();
	
	public AI_v2g1(){
		
	}
	
	public AI_v2g1(Desk player_1,Desk player_0,Kazan Kazan_1,Kazan Kazan_0){
		player[1] = player_1;
		player[0] = player_0;
		Kazan[1] = Kazan_1;
		Kazan[0] = Kazan_0;
	}
	
	//get array and score; and put this in virtual desks
	public void memorizeDesk(Desk player_1,Desk player_0,Kazan Kazan_1,Kazan Kazan_0){
		int[] cell1=player[1].getDesk();
		int[] cell0=player[0].getDesk();
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
	
	
	
}
