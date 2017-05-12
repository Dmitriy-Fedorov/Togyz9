package mainframe;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Desk implements ANSI_color, DESK_parameters {
	private Integer cellSize = new Integer(9);
		
	private Map<String,Object> desk = new TreeMap<>();
	//private Map<String,Object> reset;
	
	private ArrayList<Integer> cell_0 = new ArrayList<>();
	private ArrayList<Integer> cell_1 = new ArrayList<>();
	
	public Desk(String p1_name, String p2_name){
		for(int i=0;i<cellSize;i++){
			cell_0.add(cellSize);
			cell_1.add(cellSize);
		}
		desk.put(p0_cell, cell_0);
		desk.put(p1_cell, cell_1);
		desk.put(is_editable, false);
		
		desk.put(DESK_parameters.p0_name, p1_name);
		desk.put(DESK_parameters.p1_name, p2_name);
		
		desk.put(p0_score, 0);
		desk.put(p1_score, 0);
		
		desk.put(p0_is_tuzdyk, false);
		desk.put(p1_is_tuzdyk, false);
		desk.put(p0_tuzdyk_0to8, -1);
		desk.put(p1_tuzdyk_0to8, -1);
		
		desk.put(hod_number, 0);
		desk.put(chey_hod, false);
		
		//reset = new TreeMap<>(desk);
		desk.put(p0_win_count, 0);
		desk.put(p1_win_count, 0);
	}
	
	private void put(int from_0to1, int value){
		int tempScore = get_score(from_0to1);
		tempScore += value;
		set_score(from_0to1, tempScore);
	}
	
	private int whichDeskEmpty(){
		int sum_0 = 0, sum_1 = 0;
		for(int i=0;i<cellSize;i++){
			sum_0 += get_cell_value(0, i);
			sum_1 += get_cell_value(1, i);
		}
		if(sum_0 !=0 && sum_1 !=0){
			return -1;
		}else if(sum_0 == 0){
			System.out.println("Desk.whichDeskEmpty: Desk 1 is empty");
			put(1,sum_1);
			return 0;
		}else if(sum_1 == 0){
			System.out.println("Desk.whichDeskEmpty: Desk 2 is empty");
			put(0,sum_0);
			return 1;
		}else{
			System.out.println("Desk.whichDeskEmpty: Both Desks are empty");
			return 2;
		}		
	}
	
	public boolean checkZero(int from_1to2,int n_0to8){
		if(get_cell_value(from_1to2, n_0to8)==0)	return true;
		else return false;
	}
	public boolean checkZero(int n_0to8){
		int from_1to2;
		if(chey_hod()){
			from_1to2 = 1;
		}else{
			from_1to2 = 0;
		}
		if(get_cell_value(from_1to2, n_0to8)==0)	return true;
		else return false;
	}
	
	private boolean checkEven(int from_1to2,int n_0to8){
		if(get_cell_value(from_1to2, n_0to8)%2==0)	return true;
		else return false;
	}
	private void setZero(int from_1to2, int n_0to8){
		ArrayList<Integer> temp = get_cells(from_1to2);
		temp.set(n_0to8, 0);
	}
	
	private void setOne(int from_1to2, int n_0to8){
		ArrayList<Integer> temp = get_cells(from_1to2);
		temp.set(n_0to8, 1);
	}
	private boolean checkTuzdyk(int from_1to2,int n_0to8){
		if(get_cell_value(from_1to2, n_0to8)==3 && n_0to8!=(cellSize-1))	return true;
		else return false;
	}
	
	private void setTuzdyk(int from_1to2, int n_0to8, boolean echo){
		if(!is_Tuzdyk(from_1to2)){
			if(this.checkTuzdyk(from_1to2, n_0to8) && getTuzdykCellNumber((from_1to2+1)%2)==-1){
				setTuzdykCellNumber(from_1to2, n_0to8);
				set_is_Tuzdyk(from_1to2, true);
				put((from_1to2+1)%2,3);
				setZero(from_1to2, n_0to8);
			}else if(this.checkTuzdyk(from_1to2, n_0to8) && getTuzdykCellNumber((from_1to2+1)%2)!=n_0to8){
				setTuzdykCellNumber(from_1to2, n_0to8);
				set_is_Tuzdyk(from_1to2, true);
				put((from_1to2+1)%2,3);
				setZero(from_1to2, n_0to8);
			}
			
		}
		
	}
	
	private void refreshTuzdyk(){
		if(getTuzdykCellNumber(0)!=-1){
			put(1,get_cell_value(0, getTuzdykCellNumber(0)));
			this.setZero(0, getTuzdykCellNumber(0));
		}
		if(getTuzdykCellNumber(1)!=-1){
			put(0,get_cell_value(1, getTuzdykCellNumber(1)));
			this.setZero(1, getTuzdykCellNumber(1));
		}
	}
	
	private int emptying(int from_1to2, int n_0to8){
		int a = get_cell_value(from_1to2, n_0to8);
		setZero(from_1to2, n_0to8);
		put((from_1to2+1)%2,a);
		return a;
	}
	
	private int increment(int from_1to2, int begin_1to9, boolean toPrint){
		int begin_0to8 = begin_1to9 -1;
		if(get_cell_value(from_1to2, begin_0to8)==0){
			//just in case if debug will not handle error
			if(toPrint)
				System.out.println("Cell is empty (Desk.increment(int begin) )");
			return -1;
		}else if(get_cell_value(from_1to2, begin_0to8)==1){
			if(begin_0to8 == cellSize-1){
				this.setZero(from_1to2, cellSize-1);
				return 1; 
			}else{
				increment_cell_value(from_1to2, begin_0to8+1);
				setZero(from_1to2, begin_0to8);
				return 0;
			}
		}else if(begin_0to8+get_cell_value(from_1to2, begin_0to8)>cellSize){
			for(int i=begin_0to8+1; i<cellSize; i++){
				increment_cell_value(from_1to2, i);
			}
			int temp = (get_cell_value(from_1to2, begin_0to8)-(cellSize-begin_0to8));
			setOne(from_1to2, begin_0to8);
			return temp;
		}else{
			for(int i=begin_0to8 +1;i<begin_0to8+get_cell_value(from_1to2, begin_0to8);i++){
				increment_cell_value(from_1to2, i);
			}
			setOne(from_1to2, begin_0to8);
			return 0;
		}
	}
	
	private int incrementFrom0(int from_1to2, int by){
		if(by <= cellSize){
			for(int i =0; i < by; i++){
				increment_cell_value(from_1to2, i);
			}
			return 0;
		}else{
			for(int i=0;i<cellSize;i++){
				increment_cell_value(from_1to2, i);
			}
			return by - cellSize;
		}
	}
	
	public int getDestinationCELL(int from_1to2, int input_0to8){
		int temp, input_1to9 = input_0to8+1, dest_1to9;
		if(get_cell_value(from_1to2, input_0to8)==1){
			if(input_1to9==cellSize){
				dest_1to9 = 1;
			}else{
				dest_1to9 = -input_1to9 - 1;
			}
		}else if(get_cell_value(from_1to2, input_0to8)==0){
			dest_1to9 = -input_1to9;
		}else{
			/*	1to18
			18 17 16 15 14 13 12 11 10 
			 1  2  3  4  5  6  7  8  9
			  	
			  	0to17
			17 16 15 14 13 12 11 10  9
			 0  1  2  3  4  5  6  7  8
			*/
			temp = (input_0to8 + get_cell_value(from_1to2, input_0to8))%(cellSize*2);
			if(temp>9){
				dest_1to9 = temp-cellSize;
			}else if(temp == 0){
				dest_1to9 = cellSize;
			}else{
				dest_1to9 = -temp;
			}
			
		}
		return dest_1to9;
	}	
	
	public int move(int from_1to2, int begin_1to9, boolean toPrint){
		int score = get_score(from_1to2);
		int destCell_1to9 = getDestinationCELL(from_1to2, begin_1to9-1);
		int remainder = increment(from_1to2, begin_1to9,toPrint); //check +
		boolean enemy = false;
		//if enemy false destCell is on my desk
		
		//System.out.println("Desk.move, remainder: " + remainder);
		while(remainder > 0){
			
			if(remainder > 0){
				remainder = incrementFrom0((from_1to2+1)%2,remainder);
				enemy = true;
				if(remainder > 0){
					remainder = incrementFrom0(from_1to2, remainder);
					enemy = false;
				}
			}
		}
		
		if(toPrint)
			System.out.println(ANSI_YELLOW+"Desk.move, destCell_1to9: "+destCell_1to9+ANSI_RESET);
		if(enemy){
			setTuzdyk((from_1to2+1)%2, destCell_1to9 - 1, false);
			if(checkEven((from_1to2+1)%2, destCell_1to9-1)){
				emptying((from_1to2+1)%2, destCell_1to9 - 1);
			}
		}
		refreshTuzdyk();
		increment_hod_number();
		whichDeskEmpty();
		switch_chey_hod();
		
		return get_score(from_1to2)-score;
	}
	
	@SuppressWarnings("unchecked")
	private int get_cell_value(int from_1to2,int cell_0to8){
		String key = "p"+from_1to2+"_cell";
		ArrayList<Integer> temp = (ArrayList<Integer>)desk.get(key);
		return temp.get(cell_0to8);
	}
	
	private void set_cell_value(int from_1to2,int cell_0to8, int value){
		String key = "p"+from_1to2+"_cell";
		ArrayList<Integer> temp = (ArrayList<Integer>)desk.get(key);
		temp.set(cell_0to8, value);
	}
	
	private void increment_cell_value(int from_1to2,int n_0to8){
		String key = "p"+from_1to2+"_cell";
		//ArrayList<Integer> temp = (ArrayList<Integer>)desk.get(key);
		//int temp_cell = temp.get(n_0to8);
		int temp_cell = ((ArrayList<Integer>)desk.get(key)).get(n_0to8);
		((ArrayList<Integer>)desk.get(key)).set(n_0to8, ++temp_cell);
	}
	
	private ArrayList<Integer> get_cells(int from_1to2){
		String key = "p"+from_1to2+"_cell";
		ArrayList<Integer> temp = (ArrayList<Integer>)desk.get(key);
		return temp;
	}
	
	public String get_name(int from_1to2){
		String key = "p"+from_1to2+"_name";
		if(!(desk.get(key) instanceof String)){
			System.err.println("Desk.getName: object is not instance of String");
		}
		return (String)desk.get(key);
	}
	
	public int get_win_count(int from_1to2){
		String key = "p"+from_1to2+"_win_count";
		return (int)desk.get(key);
	}
	
	private int get_score(int from_1to2){
		String key = "p"+from_1to2+"_score";
		return (int)desk.get(key);
	}
	private void set_score(int from_1to2, int value){
		String key = "p"+from_1to2+"_score";
		desk.put(key, value);
	}
	
	private boolean is_Tuzdyk(int from_1to2){
		String key = "p"+from_1to2+"_is_tuzdyk";
		return (boolean)desk.get(key);
	}
	private void set_is_Tuzdyk(int from_1to2, boolean set){
		String key = "p"+from_1to2+"_is_tuzdyk";
		desk.put(key, set);
	}
	private int getTuzdykCellNumber(int from_1to2){
		String key = "p"+from_1to2+"_tuzdyk_0to8";
		return (int)desk.get(key);
	}
	private void setTuzdykCellNumber(int from_1to2, int n_0to8){
		String key = "p"+from_1to2+"_tuzdyk_0to8";
		desk.put(key, n_0to8);
	}
	
	private int get_hod_number(){
		String key = "hod_number";
		return (int)desk.get(key);
	}
	private void increment_hod_number(){
		String key = "hod_number";
		int temp =  (int)desk.get(key);
		desk.put(key, ++temp);
	}
	
	public boolean chey_hod(){
		String key = "chey_hod";
		return (boolean)desk.get(key);
	}
	private void switch_chey_hod(){
		String key = "chey_hod";
		boolean temp = (boolean)desk.get(key);
		desk.put(key, !temp);
	}
	
	public boolean checkWin(){
		int winScore = cellSize*cellSize;
		if( get_score(0) > winScore ){
			incrementWinCount(0);
			return true;
		}else if(get_score(1) > winScore){
			incrementWinCount(1);
			return true;
		}else if(get_score(0)==winScore && get_score(1)==winScore){
			return true;
		}else{
			return false;
		}
	}
	
	private void incrementWinCount(int from_1to2){
		String key = "p"+from_1to2+"_win_count";
		int win_count = (int)desk.get(key);
		desk.put(key, ++win_count);
	}
	
	private String cellToString(int from_1to2, boolean reverse){
		String to = "";
		if(!reverse){
			for(int i = 0;i<cellSize;i++){
				if(i!=this.getTuzdykCellNumber(from_1to2))
					to = to + String.format("%3d", get_cell_value(from_1to2, i));
				else
					to = to + "  *";
			}
		}else{
			for(int i = cellSize-1;i>=0;i--){
				if(i!=this.getTuzdykCellNumber(from_1to2))
					to = to + String.format("%3d", get_cell_value(from_1to2, i));
				else
					to = to + "  *";
			}
		}
		
		return to;
	}
	
	@Override
	public String toString(){
		String toString = "Hod #"+get_hod_number()+"\n";
		toString = toString+String.format("%s\t%s%s\t Score: %d\t Win: %d%s", cellToString(1,true),ANSI_GREEN,get_name(1),get_score(1),get_win_count(1),ANSI_RESET);
		if(chey_hod()){
			toString = toString + " *\n";
		}else{
			toString = toString + "\n";
		}
		toString = toString+String.format("%s\t%s%s\t Score: %d\t Win: %d%s", cellToString(0,false),ANSI_GREEN,get_name(0),get_score(0),get_win_count(0),ANSI_RESET);
		if(!chey_hod()){
			toString = toString + " *\n";
		}
		return toString;
		
	}
	
	public void reset(){
		/*
		int temp0 = (int)desk.get("p0_win_count");
		int temp1 = (int)desk.get("p1_win_count");
		desk.clear();
		desk.putAll(reset);
		desk.put("p0_win_count", temp0);
		desk.put("p1_win_count", temp1);*/
		for(int i=0;i<cellSize;i++){
			cell_0.set(i,cellSize);
			cell_1.set(i,cellSize);
		}
		desk.put("p0_cell", cell_0);
		desk.put("p1_cell", cell_1);
		desk.put("is_editable", false);
			
		desk.put("p0_score", 0);
		desk.put("p1_score", 0);
		
		desk.put("p0_is_tuzdyk", false);
		desk.put("p1_is_tuzdyk", false);
		desk.put("p0_tuzdyk_0to8", -1);
		desk.put("p1_tuzdyk_0to8", -1);
		
		desk.put("hod_number", 0);
		desk.put("chey_hod", false);
	}
	
	
	
	
	
	
	
	
	
	
}
