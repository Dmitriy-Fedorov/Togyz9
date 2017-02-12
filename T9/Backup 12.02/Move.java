import java.util.Scanner;

public class Move {
	
public static void main(String[] args) {
	
	Desk player[] = {new Desk(),new Desk()};
	Kazan Kazan[] = {new Kazan(),new Kazan()};
	
	int hod_number = 0;
	Scanner sc = new Scanner(System.in);
	print_T9.printDesk(0,player[1],player[0],Kazan[1],Kazan[0]);
	
	while(true){
	
	while(!Kazan[0].checkWin() && !Kazan[1].checkWin()){
		//which player's move
		int hod = hod_number%2;		
		
		//get input from player
		int n=1;
		if(hod%2==3){
		n = Integer.parseInt(Debug.getInput(sc))-1;	
		//in order to check if yamka empty
		while(player[hod].checkZero(n)){					
			System.out.print("Cell is empty or tuzdyk.\nTry another cell:\n");
			n = Integer.parseInt(Debug.getInput(sc))-1;
										}	
		}else if(hod%2==1){
			n = AI.randomMove();
			while(player[hod].checkZero(n)){
				n = AI.randomMove();
			}
			System.out.println((n+1));
		}else if(hod%2==0){
			n = AI.randomMove();
			while(player[hod].checkZero(n)){
				n = AI.randomMove();
			}
			System.out.println((n+1));
		}
		//n range from 0 to 8
		print_T9.doMove(hod,n,player[1],player[0],Kazan[1],Kazan[0]);
		
		try {
			AI.generateArray(hod,hod_number,n,player[1],player[0],Kazan[1],Kazan[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//State of the desk after move
		print_T9.printDesk((hod+1)%2,player[1],player[0],Kazan[1],Kazan[0]);
		hod_number++;
	}
	
	System.out.println("Player " + ((hod_number+1)%2) + " win!");
	hod_number=0;
	player[1].resetDesk();;
	player[0].resetDesk();;
	Kazan[1].reset();;
	Kazan[0].reset();;
	}
	}


}


