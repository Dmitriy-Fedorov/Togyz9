package main_package;

import java.io.IOException;



public class Move {
	
public static void main(String[] args) {
	
	Desk player[] = {new Desk(),new Desk()};
	Kazan Kazan[] = {new Kazan(),new Kazan()};
	String toPrint;
	String[] igrok = {"human","random","smartRandom","Deep1"};
	String igrok0 = igrok[0];
	String igrok1 = igrok[0];
	
	int hod_number = 0;
	int winCount[]={0,0};//, a[]={11,11,1,9,1,2,11,11,2},b[]={1,2,4,0,1,12,12,1,12};
	//Scanner sc = new Scanner(System.in);
	/*
	player[1].setDesk(b);
	player[0].setDesk(a);
	hod_number++;
	*/
	print_T9.printDesk(0,player[1],player[0],Kazan[1],Kazan[0]);
	//AI_1 ai_1 = new AI_1(player[1],player[0],Kazan[1],Kazan[0]);
	//T9_server server = new T9_server();
	//server.startServer();
	//T9_server.t1.start();

	
	
	
	
	while(true){
	
	while(!Kazan[0].checkWin() && !Kazan[1].checkWin()
			&& !player[0].isDeskEmpty()	&& !player[1].isDeskEmpty()){
		//which player's move
		int hod = hod_number%2;		
		
		//get input from player
		int n = -1;
		
		if(hod%2==0){
			n = print_T9.selectPlayer(hod, igrok0, player[1],player[0],Kazan[1],Kazan[0]);
			//player[hod%2].getDestinationCELL(n);
		}else{
			n = print_T9.selectPlayer(hod, igrok1, player[1],player[0],Kazan[1],Kazan[0]);
			//player[hod%2].getDestinationCELL(n);
		}
		
		if(n==-1){
			System.out.println("Error, n=-1, check selectPlayer class");
		}
		
		
		
		//n range from 0 to 8
		print_T9.doMove(hod,n,player[1],player[0],Kazan[1],Kazan[0],true);
		
		
		
		
		
		
		try {
			print_T9.generateArray(hod,hod_number,n,player[1],player[0],
					Kazan[1],Kazan[0],igrok1,igrok0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		//State of the desk after move
		print_T9.printDesk((hod+1)%2,player[1],player[0],Kazan[1],Kazan[0]);
		hod_number++;
	}
	if(player[1].isDeskEmpty()){
		System.out.println("Player " + 0 + " win!");
		winCount[0]++;
	}else if(player[0].isDeskEmpty()){
		System.out.println("Player " + 0 + " win!");
		winCount[1]++;
	}else if(Kazan[0].checkWin()){
		System.out.println("Player " + 0 + " win!");
		winCount[0]++;
	}else if(Kazan[1].checkWin()){
		System.out.println("Player " + 1 + " win!");
		winCount[1]++;
	}
	System.out.println("Player 0 win count:" + winCount[0]);
	System.out.println("Player 1 win count:" + winCount[1]);
	toPrint = String.format("%s: %d,\t %s: %d,\t%d",igrok0,winCount[0],igrok1,winCount[1],hod_number);
	WriteFile data = new WriteFile( igrok0 + "_vs_" + igrok1 , true );
	try {
		data.writeToFile(toPrint);
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	hod_number=0;
	player[1].resetDesk();;
	player[0].resetDesk();;
	Kazan[1].reset();;
	Kazan[0].reset();;
	}
	}


}


