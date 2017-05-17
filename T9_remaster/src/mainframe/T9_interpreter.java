package mainframe;

import Interface.PLAYERS_list;

public class T9_interpreter implements PLAYERS_list{
	private String[] args;
	private Desk game;
	int p1,p2;
	//Hodder hodder = new Hodder(game);
	
	T9_interpreter(Desk game, String[] args){
		this.game = game;
		this.args = args;
		p1 = Integer.parseInt(args[0]);
		p2 = Integer.parseInt(args[1]);
	}
	
	public static Desk createDesk(String[] args){
		int p1 = 0, p2 = 0;
		if(args.length >= 2){
			p1 = Integer.parseInt(args[0]);
			p2 = Integer.parseInt(args[1]);
		}
		
		if(args.length == 2){
			return new Desk(player_list[p1],player_list[p2]);
			
		}else if(args.length == 4){
			int size = Integer.parseInt(args[3]);
			if(args[2].equals("-size")){
				return new Desk(player_list[p1],player_list[p2],size);
			}
			else{
				return new Desk();
			}
		}
		else{
			return new Desk(human,human);
		}
		
	}
	
	public static String getSettings(String args){
		
		return "";
	}
	
}
