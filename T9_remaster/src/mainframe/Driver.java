package mainframe;

public class Driver {

	public static void main(String[] args) {
		String igrok1 = "AI_random";
		String igrok2 = "AI_random";
		
		Desk game = new Desk("player_1","player_2");
		Hodder hodder = new Hodder(game);
		
		boolean hod = false; //player 0 -> false
		
		
		
		while(true){
			System.out.println(game);
			
			while(!game.checkWin()){
				int n = -1;
				
				if(!hod){
					n = hodder.selectPlayer(igrok1);
				}else{
					n = hodder.selectPlayer(igrok2);
				}
				
				if(n==-1){
					System.out.println("Error, n=-1, check selectPlayer class");
				}
				
				hodder.doMove( n);
				
				
				
				System.out.println(game);
			}
						
			System.out.println(game.get_name(0) + " win count:" + game.get_win_count(0));
			System.out.println(game.get_name(1) + " win count:" + game.get_win_count(1));
			hod = false;
			game.reset();
			System.gc();
			
		}

	}

}
