package mainframe;


public class Driver {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public static void main(String[] args) {
		int choose0 = 5;
		int choose1 = 1, asd=0;
		int[] setting = new int[2];
		if(args.length!=0){
			choose0 = Integer.parseInt(args[0]);
			choose1 = Integer.parseInt(args[1]);
			setting[0] = Integer.parseInt(args[2]);
			setting[1] = Integer.parseInt(args[3]);
		}
		String[] igrok = {"human","AI_random","AI_brut","AI_deep1","AI_Deep2","AI_hashBrut"};
		String igrok0 = igrok[choose0];
		String igrok1 = igrok[choose1];
		
		String igrok0_ = igrok0 + String.format("_%d", asd++);
		igrok0_ = String.format("%15s", igrok0_);
		String igrok1_ = igrok1 + String.format("_%d", asd++);
		igrok1_ = String.format("%15s", igrok1_);
		Desk player[] = {new Desk(igrok0_),new Desk(igrok1_)};
		
		
		int hod_number=0;
		int winCount[]={0,0};
		boolean hod = false; //player 0 -> false
		
		
		
		while(true){
			System.out.println("Hod: #" + hod_number);
			System.out.println(Desk.printDesk(player[1], player[0], false));
			
			while(!player[0].checkWin(false) && !player[1].checkWin(false)){
				int n = -1;
				
				if(!hod){
					n = Hodder.selectPlayer(hod, igrok0, player[1],player[0],setting);
				}else{
					n = Hodder.selectPlayer(hod, igrok1, player[1],player[0],setting);
				}
				
				if(n==-1){
					System.out.println("Error, n=-1, check selectPlayer class");
				}
				
				Hodder.doMove(hod, n, player[1], player[0]);
				
				
				
				hod_number++;
				System.out.println("Driver.main Hod: #" + hod_number);
				hod = !hod;
				System.out.println(Desk.printDesk(player[1], player[0],hod));
			}
			
			if(player[0].checkWin(true))
				winCount[0]++;
			if(player[1].checkWin(true))
				winCount[1]++;
			
			System.out.println(player[0].getName() + " win count:" + winCount[0]);
			System.out.println(player[1].getName() + " win count:" + winCount[1]);
			hod = false;
			hod_number=0;
			player[1].reset();
			player[0].reset();
			System.gc();
			
		}

	}

}
