package ai_package;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import mainframe.Desk;

public class AI_brut {
	private static int randomNum_1to9 = 0;
	
	Desk player_AI[] = {new Desk("     AI_brut_0*", true),
			new Desk("     AI_brut_1*", true)};
	
	public AI_brut(){
		
	}
	Map<String, ArrayList<Integer> > hashMap = new HashMap<String, ArrayList<Integer> >();
	
	public static int randomMove(){
		randomNum_1to9 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
		return randomNum_1to9;
	}
	
	public int brut(Desk player_1,Desk player_0, boolean hod, int hod_number){
		int maxScore = 0, temp, index1, index0, destCell_1to9,n_1to9=0;
		Desk.transferDesks(player_1, player_AI[1]);
		Desk.transferDesks(player_0, player_AI[0]);
		
		if(hod){
			index1 = 1;
			index0 = 0;
		}else{
			index1 = 0;
			index0 = 1;
		}
		//System.out.println("Virt desk\n" + Desk.printDesk(player_AI[1], player_AI[0], hod));
		
		for(int i=1;i<=9;i++){
			destCell_1to9 = player_AI[index1].getDestinationCELL(i-1);
			//System.out.println("AI_brut.brut, destCell_1to9: " + destCell_1to9);
			if(destCell_1to9>0){
				temp = player_AI[index0].checkValue(destCell_1to9-1)+1;
				//System.out.println("AI_brut.brut, temp: " + temp);
				if(temp%2==0 && temp >maxScore){
					maxScore = temp;
					n_1to9 = i;
					//System.out.println("AI_brut.brut, maxScore: " + maxScore);
				}
			}
			
		}
		if(maxScore == 0){
			n_1to9 = randomMove();
			while(player_AI[index1].checkZero(n_1to9-1)){
				n_1to9 = randomMove();
			}
		}
		
		
		return n_1to9;
	}
	
	public int deepBrut(Desk player_1,Desk player_0, boolean hod, int deep){
		int[] score, top_1to9;
		
		score = this.depth(deep, player_1, player_0, hod);
		top_1to9 = top3(score);
		if(score[top_1to9[0]-1]==0 && score[top_1to9[1]-1]==0 && score[top_1to9[2]-1]==0 ){
			return randomMove();
		}
		int random = ThreadLocalRandom.current().nextInt(0, 2 + 1);
		return top_1to9[random];
	}
	
	int[] depth(int deep,Desk in_player_1,Desk in_player_0, boolean hod){
		Desk player_1 = new Desk("    temp1_deep_"+deep,true);
		Desk player_0 = new Desk("    temp0_deep_"+deep,true);
		int[] score = new int[9];
		Desk.transferDesks(in_player_1, player_1);
		Desk.transferDesks(in_player_0, player_0);
		
		if(deep == 1){
			for(int i=1;i<=9;i++){
				score[i-1] = this.move(i, player_1, player_0, hod);
			}
		}else if(deep > 1){
			score = depth(deep-1,player_1,player_0,!hod);
		}
		
		return score;
	}
	
	int move(int n_1to9, Desk player_1,Desk player_0,boolean hod){
		int index1, index0;
		Desk[] deskArray = {player_0,player_1};
		
		if(hod){
			index1 = 1;
			index0 = 0;
		}else{
			index1 = 0;
			index0 = 1;
		}
		return deskArray[index1].checkMove(deskArray[index0], n_1to9);
	}
	
	int arrayMax(int[] a){
		int max = 0, cell_1to9 = 1;
		for(int i=1;i<=9;i++){
			if(a[i-1]>max){
				max = a[i-1];
				cell_1to9 = i;
			}
		}
		return cell_1to9;
	}
	
	int[] top3(int[] a){
		int[] top = new int[3];
		int[] dublicate = new int[9];
		
		for(int i=0; i<9; i++){
			dublicate[i]=a[i];
		}
		
		top[0] = this.arrayMax(dublicate);
		dublicate[top[0]-1]=-10;
		top[1] = this.arrayMax(dublicate);
		dublicate[top[1]-1]=-10;
		top[2] = this.arrayMax(dublicate);
		
		return top;
	}
	
////////////////////////////////////////////////////////////////////////////	
	public int pridumayName(int deep,Desk in_player_1,Desk in_player_0, boolean cheyHod){
		String key = "dg",keyTemp;
		int keyIndex = 0,keyListLength,offset = 2,m;
		//for(int i=0;i<=deep;i++){
		//	key = key + "0";
		//}
		//String defaultKey = key;
		ArrayList<String> keyList = new ArrayList<>();
		keyList.add(key+0);
		ArrayList<Integer> counter = new ArrayList<>();
		//counter.add(1);
		
		ArrayList<Integer> init = Desk.toDatagram(cheyHod, in_player_1, in_player_0);
		hashMap.put(key+0, init);
		//Desk.printDatagram(hashMap.get(key));
		
		for(int level=1; level <= deep; level++){
			//key = stringIndexer(key,offset,0,level);
			
			
			keyListLength = keyList.size();
			
			for(;keyIndex<keyListLength;keyIndex++){
				
				for(int n=1;n<=9;n++){
					//key = stringIndexer(key,offset,level,n);
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
				
			}
			cheyHod = !cheyHod;
		}
		//System.out.println(hashMap.get("dg11"));
		
		System.out.println("hashMap size: "+hashMap.size());
		return 1;
	}
	
	// datagram {chey_hod :0,desk0[9] :1-9,score0 :10,desk1[9] :11-19,score1 :20}	
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
			
		deskArray[index1].move(n, deskArray[index0]);
		
		System.out.println(Desk.printDesk(deep1_n_level, deep0_n_level, cheyHod));
		
		ArrayList<Integer> asd = Desk.toDatagram(cheyHod, deep1_n_level, deep0_n_level);
		//if(!checkEmptyCell)
			this.hashMap.put(key, asd);
		
		
		return datagram;
	}
	
	String stringIndexer(String str, int offset, int index, int data){
		return str.substring(0, offset + index)+ data + str.substring(offset + index+1);
	}
	
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
}
