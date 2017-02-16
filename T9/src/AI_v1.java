
public class AI_v1 {
	private static Desk player[] = {new Desk(),new Desk()};
	private static Kazan Kazan[] = {new Kazan(),new Kazan()};
	private /*static*/ Desk virtDesk[] = {new Desk(true),new Desk(true)};
	private /*static*/ Kazan virtKazan[] = {new Kazan(true),new Kazan(true)};
	
	AI_v1(){
		
	}
	
	AI_v1(Desk player_1,Desk player_0,Kazan Kazan_1,Kazan Kazan_0){
		player[1] = player_1;
		player[0] = player_0;
		Kazan[1] = Kazan_1;
		Kazan[0] = Kazan_0;
	}
	
	//get array and score; and put this in virtual desks
	void memorizeDesk(){
		int cell1[]=player[1].getDesk();
		int cell0[]=player[0].getDesk();
		virtDesk[1].setDesk(cell1);
		virtDesk[0].setDesk(cell0);
		virtKazan[1].setKazan(Kazan[1].checkScore());
		virtKazan[0].setKazan(Kazan[0].checkScore());
		/*
		System.out.println("Virtual desk print:");
		print_T9.printDesk(1,virtDesk[1],virtDesk[0],virtKazan[1],virtKazan[0]);
		System.out.println("Real desk print:");
		*/
	}
	
	int smartRandomPlay(int player){
		int[] storeMove = new int[20], storeMaxMove = new int[20];
		int storeMaxScore = 0,score;
		for(int j=0;j<20;j++){
			memorizeDesk();
			
			for(int i=0;i<20;i++){
				storeMove[i] = AI_v0.randomMove();
				
				while(virtDesk[i%2].checkZero(storeMove[i])){
					storeMove[i] = AI_v0.randomMove();
				}
				print_T9.doMove(i%2, storeMove[i], virtDesk[1], virtDesk[0], virtKazan[1], virtKazan[0],false);
				//print_T9.printDesk(i%2, virtDesk[1], virtDesk[0], virtKazan[1], virtKazan[0]);
			}
			score = virtKazan[player].checkScore();
			if(score>storeMaxScore){
				storeMaxScore = score;
				storeMaxMove = storeMove;
			}
		}
		//System.out.println((n+1));
		
		return storeMaxMove[0];
	}

}
