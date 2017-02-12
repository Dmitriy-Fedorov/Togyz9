
public class print_T9 {
	/*
	static Desk player1 = new Desk();
	static Desk player0 = new Desk();
	static Kazan Kazan1 = new Kazan();
	static Kazan Kazan0 = new Kazan();
	*/
	private static Desk player[] = {new Desk(),new Desk()};
	private static Kazan Kazan[] = {new Kazan(),new Kazan()};
	

	static int printDesk(int hod, Desk player_1,Desk player_0,Kazan Kazan_1,Kazan Kazan_0){
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
	static int doMove(int hod,int n, Desk player_1,Desk player_0,Kazan Kazan_1,Kazan Kazan_0){
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
			System.out.println("\teven");
		}else{
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
}
