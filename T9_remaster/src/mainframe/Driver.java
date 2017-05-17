package mainframe;

import Interface.ANSI_color;
import Interface.PLAYERS_list;

public class Driver implements ANSI_color{

	public static void main(String[] args) {
		Desk game = T9_interpreter.createDesk(args);
		Hodder hodder = new Hodder(game);
				
		while(true){
			System.out.println(game);
			while(!game.checkWin()){
				hodder.doMove();
				System.out.println(game);
			}
			
			System.out.println(ANSI_BLUE+"Games played:" + game.get_game_played_count());			
			System.out.println(game.get_name(0) + " win count:" + game.get_win_count(0));
			System.out.println(game.get_name(1) + " win count:" + game.get_win_count(1));
			System.out.println("Draw count:" + game.get_draw_count()+ANSI_RESET);
			game.reset();
			System.gc();
			
		}

	}

}
