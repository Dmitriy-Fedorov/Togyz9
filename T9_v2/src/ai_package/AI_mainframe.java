package ai_package;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class AI_mainframe {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
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
	
	ArrayList<String> sortArrayList(ArrayList<String> keyList,Map<String, ArrayList<Integer> > hashMap,boolean cheyHod){
		if(keyList.size()==0){
			
		}
		int index0,index1;
		if(cheyHod){
			index0 = 20; //current player
			index1 = 10; //next player
		}else{
			index0 = 10;
			index1 = 20;
		}
		
		//{chey_hod :0,desk0[9] :1-9,score0 :10,desk1[9] :11-19,score1 :20}
		Collections.sort(keyList, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
            	int score0 = hashMap.get(lhs).get(index0)-hashMap.get(lhs).get(index1);
            	int score1 = hashMap.get(rhs).get(index0)-hashMap.get(rhs).get(index1);
            	if(score1!=score0)
            		return score1 - score0;
            	else{
            		int even0=0,even1=0;
            		int ind0 = index0-10;
            		int ind1 = index1-10;
            		for(int i=1;i<10;i++){
            			even0 += hashMap.get(lhs).get(ind0+i);
            			even1 += hashMap.get(rhs).get(ind0+i);
            		}
            		return even1-even0;
            	}
            }
        });
		return keyList;
	}
}
