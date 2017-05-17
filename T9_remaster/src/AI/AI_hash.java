package AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import Interface.ANSI_color;
import mainframe.*;

public class AI_hash implements ANSI_color {
	private Map<String, Desk > gameMap = new TreeMap<String, Desk >();
	private Map<String, Desk > levelMap = new TreeMap<String, Desk >();
	private ArrayList<String> keyList = new ArrayList<>();
	private ArrayList<KeyDesk> keyDeskList = new ArrayList<>();
	Desk game;
	private int cellSize = 9;
	private String keySeed = "game";
	
	public AI_hash(Desk game){
		this.game = game;
		cellSize = game.get_CellSize();
	}
	
	public int hash(int depth, int width){
		depth = 3;
		width = 1;
		int n_1to9 = -1;
		gameMap.put(keySeed, game.get_ShadowCopy(keySeed));
		
		
		
		
		for(int level=0; level<depth;level++){
			
			//clear this list from previous runs
			keyDeskList.clear();
			
			//populates levelMap with games of certain depthLevel 
			for(Entry<String, Desk> entry : gameMap.entrySet()){
				String key = entry.getKey();
				Desk value = entry.getValue();
				if(key.length()==keySeed.length()+2*level){
					levelMap.put(key, value);
				}
			}
			//System.out.println("AI_hash.hash: levelMap size: "+levelMap.size());
			
			//doMove on levelMap and store brunches in gameMap
			for(Entry<String, Desk> entry : levelMap.entrySet()){
				String key = entry.getKey();
				Desk value = entry.getValue();
				
				for(int n=1;n<=cellSize;n++){
					String keygen = value.get_ID(0)+"."+n; //ID is same for both player
					Desk temp = game.get_ShadowCopy(keygen);
					
					if(!game.checkZero(n-1)){
						keyList.add(keygen);
						
						temp.move(n);
						//gameMap.put(keygen, temp);		//no need
						//levelNextMap.put(keygen, temp); //no need
						keyDeskList.add(new KeyDesk(temp, keygen));
						//System.out.println(temp);
					}
					
					
				}
				
				
			}
			
			//Sort Desk according to utility function
			Collections.sort(keyDeskList);
			//put top n desk in gameList
			populateGameList(width);
			
			
			
			//printLevel(level+1);
			String ID = keyDeskList.get(0).getDesk().get_ID(0);
			n_1to9 = Integer.parseInt(ID.substring(keySeed.length()+1, keySeed.length()+2));
			
			levelMap.clear();
			
		}
		
		
		return n_1to9;
	}
	
	void populateGameList(int width){
		if(width==0){
			for(int i=0;i<keyDeskList.size();i++){
				gameMap.put(keyDeskList.get(i).getKey(), keyDeskList.get(i).getDesk());
				//System.out.println(keyDeskList.get(i));
				//TODO could have essue with length of keyDeskList and i
			}
		}else{
			for(int i=0;i<width;i++){
				if(i>=keyDeskList.size())
					break;
				gameMap.put(keyDeskList.get(i).getKey(), keyDeskList.get(i).getDesk());
				//System.out.println(keyDeskList.get(i));
			}
		}
	}
	
	
	
	
	void printLevel(int level){
		String[] colorScheme;
		if(level%3==0)
			colorScheme = testColorScheme1;
		else if(level%3==1)
			colorScheme = testColorScheme2;
		else
			colorScheme = testColorScheme3;
		
		for(Entry<String, Desk> entry : gameMap.entrySet()){
			String key = entry.getKey();
			Desk value = entry.getValue();
			if(key.length()==keySeed.length()+2*level){
				System.out.println(ANSI_color.toYellow(key+"_")+value.toString(colorScheme));
			}
		}
	
	
	
	
	}

	

}
