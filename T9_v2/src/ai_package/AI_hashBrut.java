package ai_package;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import mainframe.Desk;

public class AI_hashBrut extends AI_mainframe{
	Map<String, ArrayList<Integer> > hashMap = new HashMap<String, ArrayList<Integer> >();
	private ArrayList<String> keyList = new ArrayList<>();
	
	public AI_hashBrut(){
		
	}

	public int hashBrut(int[] setting,Desk in_player_1,Desk in_player_0, boolean cheyHod){
		int deep ,cutWidth;
		try{
			deep = setting[0];
			cutWidth=setting[1];
		}catch(Exception e){
			System.out.println(e);
			deep = 1;
			cutWidth = 9;
		}
		String key = "dg",keyTemp;
		int keyIndex = 0,keyListLength;
		boolean cheyHodFinal = cheyHod;
		//for(int i=0;i<=deep;i++){
		//	key = key + "0";
		//}
		//String defaultKey = key;
		
		keyList.add(key+0);
		ArrayList<Integer> counter = new ArrayList<>();
		
		ArrayList<Integer> init = Desk.toDatagram(cheyHod, in_player_1, in_player_0);
		hashMap.put(key+0, init);
		//Desk.printDatagram(hashMap.get(key+0));
		
		for(int level=1; level <= deep; level++){
						
			keyListLength = keyList.size();
			
			
			for(;keyIndex<keyListLength;keyIndex++){
				
				for(int n=1;n<=9;n++){
					keyTemp = key+level;
					
					for(int i=counter.size()-1; i>=0; i--){
						keyTemp = keyTemp+counter.get(i);
					}
					keyTemp = keyTemp + n;
					
					deepMover(n, keyTemp, hashMap.get(keyList.get(keyIndex)), cheyHod);
					if(hashMap.containsKey(keyTemp)){
						keyList.add(keyTemp);
					}
					
					//Desk.printDatagram(hashMap.get(key));
					//System.out.println(keyList);
					
				}
				listCounter(counter);
				//method for choosing best 3 datagram in one level
				
			}
			keyCleaner2(level, cutWidth, cheyHod);
			cheyHod = !cheyHod;
			
		}
		//System.out.println(hashMap.get("dg11"));
		//this.keyCleaner(3, false);
		System.out.println(ANSI_BLUE+"AI_hashbrut.hashBrut hashMap size: "+hashMap.size()+ANSI_RESET);
		
		//int n = hodderRandom(deep);
		int n = hodderMAX(deep,cheyHodFinal);
		this.reset();
		System.out.println(ANSI_BLUE+"AI_hashbrut.hashBrut n="+n+ANSI_RESET);
		return n;
	}
	
	int hodderRandom(int deep){
		ArrayList<String> keyOfLastLevel = keyOfLevelN(deep);
		System.out.println("AI_hashBrut.hodderRandom size "+keyOfLastLevel.size());
		if(keyOfLastLevel.size()==0)
			return super.randomMove();
		int random = ThreadLocalRandom.current().nextInt(0, keyOfLastLevel.size());
		
		System.out.println(keyOfLastLevel.get(random));
		String parse = ""+ keyOfLastLevel.get(random).charAt(3);
		return Integer.parseInt(parse);
	}
	
	int hodderMAX(int deep,boolean cheyHod){
		ArrayList<String> keyOfLastLevel = keyOfLevelN(deep);
		System.out.println("AI_hashBrut.hodderMAX "+keyOfLastLevel.size());
		if(keyOfLastLevel.size()==0)
			return super.randomMove();
		super.sortArrayList(keyOfLastLevel, hashMap, cheyHod);
		int deltaScore = hashMap.get(keyOfLastLevel.get(0)).get(10)-hashMap.get(keyOfLastLevel.get(0)).get(20);
		System.out.println("AI_hashBrut.hodderMAX "+keyOfLastLevel.get(0)+" deltaScore " + deltaScore);
		String parse = ""+ keyOfLastLevel.get(0).charAt(3);
		return Integer.parseInt(parse);
	}
	
	/**
	 * @return ArrayList of keys on specified level
	 */
	ArrayList<String> keyOfLevelN(int level){
		String temp;
		ArrayList<String> keyOfLevelN = new ArrayList<>();
		for(int i=0;i< keyList.size();i++){
			//System.out.println("key level: "+keyList.get(i).substring(2,3));
			temp = "" + keyList.get(i).charAt(2);
			if(level == Integer.parseInt(temp)){
				keyOfLevelN.add(keyList.get(i));
				//System.out.println("level "+level+" key: "+keyList.get(i));
			}
			
		}
		return keyOfLevelN;
	}
	
	void keyCleaner(int level,int topN, boolean cheyHod){
		ArrayList<String> keyOfLevelN = keyOfLevelN(level);
		ArrayList<int[]> max3 = new ArrayList<>();
		//List<Integer> sortedSet = new SortedList<Integer>();
		int index0,index1;
		int score0, score1;
		
		
		if(cheyHod){
			index0 = 20; //current player
			index1 = 10; //next player
		}else{
			index0 = 10;
			index1 = 20;
		}
		super.sortArrayList(keyOfLevelN, hashMap, cheyHod);
		
		//Set<ArrayList<Integer>> sortedSet = new TreeSet<ArrayList<Integer>>(new datagramComparator());
		for(int i=0;i<keyOfLevelN.size();i++){
			int[] delta = new int[2];
			score0 = hashMap.get(keyList.get(i)).get(index0);
			score1 = hashMap.get(keyList.get(i)).get(index1);
			delta[0] = i;
			delta[1] = score0 - score1;
			max3.add(delta);
		}
		/*System.out.println("max3 before:"+ max3);
		for(int i=0;i<max3.size();i++){
			System.out.println("["+max3.get(i)[0]+ ","+max3.get(i)[1]+"]");
		}*/
		Collections.sort(max3, new Comparator<int[]>() {
            @Override
            public int compare(int[] lhs, int[] rhs) {
               return rhs[1]-lhs[1];
            }
        });
		
		/*System.out.println("max3 after:"+ max3);
		for(int i=0;i<max3.size();i++){
			System.out.println("["+max3.get(i)[0]+ ","+max3.get(i)[1]+"]");
		}*/
		for(int i=topN;i<max3.size();i++){
			int index = max3.get(i)[0];
			hashMap.remove(keyOfLevelN.get(index));
			keyList.remove(keyOfLevelN.get(index));
		}
	}
	void keyCleaner2(int level,int topN, boolean cheyHod){
		ArrayList<String> keyOfLevelN = keyOfLevelN(level);
		ArrayList<int[]> max3 = new ArrayList<>();
		//List<Integer> sortedSet = new SortedList<Integer>();
		int index0,index1;
		int score0, score1;
		if(cheyHod){
			index0 = 20; //current player
			index1 = 10; //next player
		}else{
			index0 = 10;
			index1 = 20;
		}
		
		super.sortArrayList(keyOfLevelN, hashMap, cheyHod);
		

		for(int i=0;i<keyOfLevelN.size();i++){
			int deltaScore = hashMap.get(keyOfLevelN.get(i)).get(index0)-hashMap.get(keyOfLevelN.get(i)).get(index1);
			if(i>=topN){
				hashMap.remove(keyOfLevelN.get(i));
				keyList.remove(keyOfLevelN.get(i));
				//System.out.println("AI_hashBrut.keyCleaner2 "+keyOfLevelN.get(i)+" deltaScore "+deltaScore);
			}else{
				System.out.println("AI_hashBrut.keyCleaner2* "+keyOfLevelN.get(i)+" deltaScore "+deltaScore);
				Desk.printDatagram(hashMap.get(keyOfLevelN.get(i)));
			}
			
		}
	}
	/**
	 * @param n: move from this cell
	 * @param key: encoded state of desk
	 * @param datagram: {chey_hod :0,desk0[9] :1-9,score0 :10,desk1[9] :11-19,score1 :20}
	 * @param cheyHod: true for player1, false for player0
	 * @return datagram and buts in hashMap with specified key
	 */
	ArrayList<Integer> deepMover(int n,String key, ArrayList<Integer> datagram, boolean cheyHod){
		int index1, index0;
		boolean checkEmptyCell;
		Desk deep1_n_level = new Desk("    key1_"+key,true);
		Desk deep0_n_level = new Desk("    key0_"+key,true);
		Desk.fromDatagram(datagram, deep1_n_level, deep0_n_level);
		Desk[] deskArray = {deep0_n_level,deep1_n_level};
		
		if(cheyHod){
			index1 = 1;
			index0 = 0;
		}else{
			index1 = 0;
			index0 = 1;
		}
		checkEmptyCell = deskArray[index1].checkZero(n-1);
			
		deskArray[index1].move(n, deskArray[index0],false);
		
		//System.out.println(ANSI_YELLOW+Desk.printDesk(deep1_n_level, deep0_n_level, cheyHod)+ANSI_RESET);
		
		ArrayList<Integer> asd = Desk.toDatagram(cheyHod, deep1_n_level, deep0_n_level);
		if(!checkEmptyCell)
			this.hashMap.put(key, asd);
		
		
		return datagram;
	}
	
	
	
	/**
	 * 9-based counter with dynamic vector length. 
	 * <p>
	 * This method is used to generate "level index" for keys in hashBrut method
	 */
	void listCounter(ArrayList<Integer> count){
		if(count.size()==0){
			count.add(0);
		}
		int temp0 = count.get(0);
		count.set(0,temp0 + 1);
		
		for(int i=0;i<count.size();i++){
			if(count.get(i)>9){
				count.set(i, 1);
				if(i+1<count.size()){
					count.set(i+1, count.get(i+1)+1);
				}else{
					count.add(1);
				}
				
			}
			
		}
	}
	
	void reset(){
		hashMap.clear();
		keyList.clear();
	}
}
