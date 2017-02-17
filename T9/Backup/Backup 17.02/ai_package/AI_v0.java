package ai_package;
import java.util.concurrent.ThreadLocalRandom;

//nextInt is normally exclusive of the top value,
//so add 1 to make it inclusive


public class AI_v0 {
	//private static Desk player[] = {new Desk(),new Desk()};
	//private static Kazan Kazan[] = {new Kazan(),new Kazan()};
	private static int randomNum = 0;
	


		
		
		public static int randomMove(){
			randomNum = ThreadLocalRandom.current().nextInt(0, 8 + 1);
			return randomNum;
		}
		
		

}
