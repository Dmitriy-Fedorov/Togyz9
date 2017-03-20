package mainframe;


public class Driver {

	public static void main(String[] args) {
		int choose0 = 1;
		int choose1 = 1, asd=0;
		String[] igrok = {"human","AI_random","AI_smartRandom","AI_Deep1","AI_Deep2"};
		String igrok0 = igrok[choose0];
		String igrok1 = igrok[choose1];
		Desk player[] = {new Desk(igrok0+"_"+asd++),new Desk(igrok1+"_"+asd++)};
		
		
		int hod_number=0;
		int winCount[]={0,0};
		boolean hod = false; //player 0 -> false
		
		
		
		while(true){
			System.out.println("Hod: " + hod_number);
			System.out.println(Desk.printDesk(player[1], player[0], false, winCount));
			
			while(!player[0].checkWin() && !player[1].checkWin()){
				int n = -1;
				
				if(hod){
					n = Hodder.selectPlayer(hod, igrok0, player[1],player[0]);
				}else{
					n = Hodder.selectPlayer(hod, igrok1, player[1],player[0]);
				}
				
				if(n==-1){
					System.out.println("Error, n=-1, check selectPlayer class");
				}
				
				Hodder.doMove(hod, n, player[1], player[0], false);
				
				
				
				hod_number++;
				System.out.println("Hod: " + hod_number);
				hod = !hod;
				System.out.println(Desk.printDesk(player[1], player[0],hod, winCount));
			}
			
			if(player[0].checkWin())
				winCount[0]++;
			if(player[1].checkWin())
				winCount[1]++;
			
			System.out.println(player[0].getName() + " win count:" + winCount[0]);
			System.out.println(player[1].getName() + " win count:" + winCount[1]);
			hod = false;
			hod_number=0;
			player[1].reset();
			player[0].reset();
			
			
		}

	}

}
