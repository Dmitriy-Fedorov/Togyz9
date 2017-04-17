package ai_package;

import java.util.concurrent.ThreadLocalRandom;

public class AI_mainframe {
	private static int randomNum_1to9 = 0;
	
	AI_mainframe(){
		
	}
	
	public static int randomMove(){
		randomNum_1to9 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
		return randomNum_1to9;
	}
	
	String stringIndexer(String str, int offset, int index, int data){
		return str.substring(0, offset + index)+ data + str.substring(offset + index+1);
	}
}
