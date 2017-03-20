package ai_package;
import java.util.concurrent.ThreadLocalRandom;

public class AI_smartRandom {
	private static int randomNum_1to9 = 0;
	
	public static int randomMove(){
		randomNum_1to9 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
		return randomNum_1to9;
	}
}
