import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

//nextInt is normally exclusive of the top value,
//so add 1 to make it inclusive


public class AI_v0 {
	private static Desk player[] = {new Desk(),new Desk()};
	private static Kazan Kazan[] = {new Kazan(),new Kazan()};
	private static int randomNum = 0;
	


		static int generateArray(int playerNum,int hod,int hodFrom
				,Desk player_1,Desk player_0,Kazan Kazan_1,Kazan Kazan_0){
			player[1] = player_1;
			player[0] = player_0;
			Kazan[1] = Kazan_1;
			Kazan[0] = Kazan_0;
			
			String file[] = {"player_0","player_1"};
			String toPrint = String.format("%3d,%3d",hod,hodFrom+1);
			for(int i=0;i<9;i++){
				toPrint = String.format("%s,%3d", toPrint,player[hod%2].checkValue(i));
			}
			toPrint = String.format("%s,%3d,%3d,%.3f,%b", toPrint
					,player[hod%2].getArraySum()
					,Kazan[hod%2].checkScore()
					,Kazan[hod%2].getNormalizedScore()
					,Kazan[hod%2].checkWin());
			WriteFile data = new WriteFile( file[playerNum] , true );
			
			try {
				data.writeToFile( toPrint);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return 0;
		}
		
		static int randomMove(){
			randomNum = ThreadLocalRandom.current().nextInt(0, 8 + 1);
			return randomNum;
		}
		
		

}
