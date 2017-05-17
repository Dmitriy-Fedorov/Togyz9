package mainframe;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import Interface.ANSI_color;
import Interface.DESK_parameters;

public class Desk implements ANSI_color, DESK_parameters {
	private int cellSize = 9;
	
	private Map<String,Object> desk = new TreeMap<>();
	private Map<String,Object> reset;
	
	private ArrayList<Integer> cell_0 = new ArrayList<>();
	private ArrayList<Integer> cell_1 = new ArrayList<>();
	
	public Desk(){
		System.err.println("Desk: Empty desk is created");
	}
	
	/*public Desk(Desk game){
		desk.put(p0_cell, game.cell_0);
		desk.put(p1_cell, game.cell_1);			
		desk.put(p0_score, game.get_score(0));
		desk.put(p1_score, game.get_score(1));	
		desk.put(p0_is_tuzdyk, game.desk.get(p0_is_tuzdyk));
		desk.put(p1_is_tuzdyk, game.desk.get(p1_is_tuzdyk));
		desk.put(p0_tuzdyk_0to8, game.desk.get(p0_tuzdyk_0to8));
		desk.put(p1_tuzdyk_0to8, game.desk.get(p1_tuzdyk_0to8));		
		desk.put(hod_number, game.desk.get(hod_number));
		desk.put(chey_hod, game.desk.get(chey_hod));
	}*/
	
	public Desk(TreeMap<String,Object> desk){
		this.desk = desk;
	}
	
	public Desk(String p1_name, String p2_name){
		this(p1_name,p2_name,9);
	}
	
	public Desk(String p1_name, String p2_name, int cellSize){
		for(int i=0;i<cellSize;i++){
			cell_0.add(cellSize);
			cell_1.add(cellSize);
		}
		desk.put(p0_cell, cell_0);
		desk.put(p1_cell, cell_1);			
		desk.put(p0_score, 0);
		desk.put(p1_score, 0);	
		desk.put(p0_is_tuzdyk, false);
		desk.put(p1_is_tuzdyk, false);
		desk.put(p0_tuzdyk_0to8, -1);
		desk.put(p1_tuzdyk_0to8, -1);		
		desk.put(hod_number, 0);
		desk.put(chey_hod, false);
		desk.put(destCell, 0);
		desk.put(p0_sum, cellSize*cellSize);
		desk.put(p1_sum, cellSize*cellSize);
		
		//remains same after reset;
		desk.put(is_editable, false);
		desk.put(DESK_parameters.p0_name, p1_name+"_1");
		desk.put(DESK_parameters.p1_name, p2_name+"_2");
		desk.put(p0_ID, p1_name);
		desk.put(p1_ID, p2_name);
		desk.put(p0_win_count, 0);
		desk.put(p1_win_count, 0);
		desk.put(draw_count, 0);
		desk.put(game_played_count, 0);
		desk.put(DESK_parameters.cellSize, cellSize);
		this.cellSize = (int)desk.get(DESK_parameters.cellSize);
		
		desk.put(previous_move, new TreeMap<>(desk));
	}
	
	private void put(int from_0to1, int value){
		int tempScore = get_score(from_0to1);
		tempScore += value;
		set_score(from_0to1, tempScore);
	}
	
	public Map<String, Object> getGame(Desk game){
		return game.desk;
	}
	public Desk get_ShadowCopy(String name){
		Desk shadow = new Desk(name, name);
		for(int i=0; i < cellSize; i++){
			shadow.cell_0.set(i,this.cell_0.get(i));
			shadow.cell_1.set(i,this.cell_1.get(i));
		}
		shadow.set_score(0, this.get_score(0));
		shadow.set_score(1, this.get_score(1));
		shadow.cellSize = this.get_CellSize();
		shadow.desk.put(chey_hod, this.desk.get(chey_hod));
		shadow.desk.put(p0_is_tuzdyk, this.desk.get(p0_is_tuzdyk));
		shadow.desk.put(p1_is_tuzdyk, this.desk.get(p1_is_tuzdyk));
		shadow.desk.put(p0_tuzdyk_0to8, this.get_tuzdykCellNumber(0));
		shadow.desk.put(p1_tuzdyk_0to8, this.get_tuzdykCellNumber(1));
		shadow.desk.put(hod_number,1000 + this.get_hod_number());
		return shadow;
	}
	public void saveGame(){
		desk.put(previous_move, new TreeMap<>(this.desk));
		desk.put(p0_cell_backup, new ArrayList<>(this.cell_0));
		desk.put(p1_cell_backup, new ArrayList<>(cell_1));
	}
	
	@SuppressWarnings("unchecked")
	public void restoreGame(){
		this.desk = (Map<String, Object>) desk.get(previous_move);
		this.cell_0 = (ArrayList<Integer>) desk.get(p0_cell_backup);
		this.cell_1 = (ArrayList<Integer>) desk.get(p1_cell_backup);
	}
	
	private int whichDeskEmpty(){
		int sum_0 = 0, sum_1 = 0;
		for(int i=0;i<cellSize;i++){
			sum_0 += get_cell_value(0, i);
			sum_1 += get_cell_value(1, i);
		}
		desk.put(p0_sum, sum_0);
		desk.put(p1_sum, sum_1);
		
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
	
	public boolean checkZero(int from_0to1,int n_0to8){
		if(get_cell_value(from_0to1, n_0to8)==0)	return true;
		else return false;
	}
	public boolean checkZero(int n_0to8){
		int from_0to1;
		if(chey_hod()){
			from_0to1 = 1;
		}else{
			from_0to1 = 0;
		}
		if(get_cell_value(from_0to1, n_0to8)==0)	return true;
		else return false;
	}
	
	private boolean checkEven(int from_0to1,int n_0to8){
		if(get_cell_value(from_0to1, n_0to8)%2==0)	return true;
		else return false;
	}
	private void setZero(int from_0to1, int n_0to8){
		ArrayList<Integer> temp = get_cells(from_0to1);
		temp.set(n_0to8, 0);
	}
	
	private void setOne(int from_0to1, int n_0to8){
		ArrayList<Integer> temp = get_cells(from_0to1);
		temp.set(n_0to8, 1);
	}
	private boolean checkTuzdyk(int from_0to1,int n_0to8){
		if(get_cell_value(from_0to1, n_0to8)==3 && n_0to8!=(cellSize-1))	return true;
		else return false;
	}
	
	private void setTuzdyk(int from_0to1, int n_0to8, boolean echo){
		if(!is_Tuzdyk(from_0to1)){
			if(this.checkTuzdyk(from_0to1, n_0to8) && get_tuzdykCellNumber((from_0to1+1)%2)==-1){
				set_TuzdykCellNumber(from_0to1, n_0to8);
				set_is_Tuzdyk(from_0to1, true);
				put((from_0to1+1)%2,3);
				setZero(from_0to1, n_0to8);
			}else if(this.checkTuzdyk(from_0to1, n_0to8) && get_tuzdykCellNumber((from_0to1+1)%2)!=n_0to8){
				set_TuzdykCellNumber(from_0to1, n_0to8);
				set_is_Tuzdyk(from_0to1, true);
				put((from_0to1+1)%2,3);
				setZero(from_0to1, n_0to8);
			}
			
		}
		
	}
	
	private void refreshTuzdyk(){
		if(get_tuzdykCellNumber(0)!=-1){
			put(1,get_cell_value(0, get_tuzdykCellNumber(0)));
			this.setZero(0, get_tuzdykCellNumber(0));
		}
		if(get_tuzdykCellNumber(1)!=-1){
			put(0,get_cell_value(1, get_tuzdykCellNumber(1)));
			this.setZero(1, get_tuzdykCellNumber(1));
		}
	}
	
	private int emptying(int from_0to1, int n_0to8){
		int a = get_cell_value(from_0to1, n_0to8);
		setZero(from_0to1, n_0to8);
		put((from_0to1+1)%2,a);
		return a;
	}
	
	private int increment(int from_0to1, int begin_1to9){
		int begin_0to8 = begin_1to9 -1;
		if(get_cell_value(from_0to1, begin_0to8)==0){
			//just in case if debug will not handle error
			System.out.println("Desk.increment: Cell"+(begin_0to8+1)+" is empty");
			return -1;
		}else if(get_cell_value(from_0to1, begin_0to8)==1){
			if(begin_0to8 == cellSize-1){
				this.setZero(from_0to1, cellSize-1);
				return 1; 
			}else{
				increment_cell_value(from_0to1, begin_0to8+1);
				setZero(from_0to1, begin_0to8);
				return 0;
			}
		}else if(begin_0to8+get_cell_value(from_0to1, begin_0to8)>cellSize){
			for(int i=begin_0to8+1; i<cellSize; i++){
				increment_cell_value(from_0to1, i);
			}
			int temp = (get_cell_value(from_0to1, begin_0to8)-(cellSize-begin_0to8));
			setOne(from_0to1, begin_0to8);
			return temp;
		}else{
			for(int i=begin_0to8 +1;i<begin_0to8+get_cell_value(from_0to1, begin_0to8);i++){
				increment_cell_value(from_0to1, i);
			}
			setOne(from_0to1, begin_0to8);
			return 0;
		}
	}
	
	private int incrementFrom0(int from_0to1, int by){
		if(by <= cellSize){
			for(int i =0; i < by; i++){
				increment_cell_value(from_0to1, i);
			}
			return 0;
		}else{
			for(int i=0;i<cellSize;i++){
				increment_cell_value(from_0to1, i);
			}
			return by - cellSize;
		}
	}
	
	private int get_DestinationCELL(int from_0to1, int input_0to8){
		int temp, input_1to9 = input_0to8+1, dest_1to9;
		if(get_cell_value(from_0to1, input_0to8)==1){
			if(input_1to9==cellSize){
				dest_1to9 = 1;
			}else{
				dest_1to9 = -input_1to9 - 1;
			}
		}else if(get_cell_value(from_0to1, input_0to8)==0){
			dest_1to9 = -input_1to9;
		}else{
			/*	1to18
			18 17 16 15 14 13 12 11 10 
			 1  2  3  4  5  6  7  8  9
			  	
			  	0to17
			17 16 15 14 13 12 11 10  9
			 0  1  2  3  4  5  6  7  8
			*/
			temp = (input_0to8 + get_cell_value(from_0to1, input_0to8))%(cellSize*2);
			if(temp>cellSize){
				dest_1to9 = temp-cellSize;
			}else if(temp == 0){
				dest_1to9 = cellSize;
			}else{
				dest_1to9 = -temp;
			}
			
		}
		desk.put(destCell, dest_1to9);
		return dest_1to9;
	}	
	
	public int get_DestinationCELL(){
		return (int)desk.get(destCell);
	}
	
	private int get_move1to9(){
		return (int)desk.get(move_n1to9);
	}
	
	public Desk move(int from_0to1, int begin_1to9){
		desk.put(move_n1to9, begin_1to9);
		int destCell_1to9 = get_DestinationCELL(from_0to1, begin_1to9-1);
		int remainder = increment(from_0to1, begin_1to9); //check +
		boolean enemy = false;
		//if enemy false destCell is on my desk
		
		//System.out.println("Desk.move, remainder: " + remainder);
		while(remainder > 0){
			
			if(remainder > 0){
				remainder = incrementFrom0((from_0to1+1)%2,remainder);
				enemy = true;
				if(remainder > 0){
					remainder = incrementFrom0(from_0to1, remainder);
					enemy = false;
				}
			}
		}
		
		if(enemy){
			setTuzdyk((from_0to1+1)%2, destCell_1to9 - 1, false);
			if(checkEven((from_0to1+1)%2, destCell_1to9-1)){
				emptying((from_0to1+1)%2, destCell_1to9 - 1);
			}
		}
		refreshTuzdyk();
		increment_hod_number();
		whichDeskEmpty();
		switch_chey_hod();
		
		return this;
	}
	
	public Desk move(int begin_1to9){
		int from_0to1;
		if(this.chey_hod())
			from_0to1 = 1;
		else
			from_0to1 = 0;
		return this.move(from_0to1, begin_1to9);
	}
	
	@SuppressWarnings("unchecked")
	private int get_cell_value(int from_0to1,int cell_0to8){
		String key = "p"+from_0to1+"_cell";
		ArrayList<Integer> temp = (ArrayList<Integer>)desk.get(key);
		return temp.get(cell_0to8);
	}
	
	private void set_cell_value(int from_0to1,int cell_0to8, int value){
		String key = "p"+from_0to1+"_cell";
		ArrayList<Integer> temp = (ArrayList<Integer>)desk.get(key);
		temp.set(cell_0to8, value);
	}
	
	private void increment_cell_value(int from_0to1,int n_0to8){
		String key = "p"+from_0to1+"_cell";
		//ArrayList<Integer> temp = (ArrayList<Integer>)desk.get(key);
		//int temp_cell = temp.get(n_0to8);
		int temp_cell = ((ArrayList<Integer>)desk.get(key)).get(n_0to8);
		((ArrayList<Integer>)desk.get(key)).set(n_0to8, ++temp_cell);
	}
	
	private ArrayList<Integer> get_cells(int from_0to1){
		String key = "p"+from_0to1+"_cell";
		ArrayList<Integer> temp = (ArrayList<Integer>)desk.get(key);
		return temp;
	}
	
	public int get_ball_sum(int from_0to1){
		String key = "p"+from_0to1+"_sum";
		return (int)desk.get(key);
	}
	
	public int get_CellSize(){
		return cellSize;
	}
	
	public String get_name(int from_0to1){
		String key = "p"+from_0to1+"_name";
		if(!(desk.get(key) instanceof String)){
			System.err.println("Desk.getName: object is not instance of String");
		}
		return (String)desk.get(key);
	}
	
	public String get_ID(int from_0to1){
		String key = "p"+from_0to1+"_ID";
		if(!(desk.get(key) instanceof String)){
			System.err.println("Desk.get_ID: object is not instance of String");
		}
		return (String)desk.get(key);
	}
	
	public int get_win_count(int from_0to1){
		String key = "p"+from_0to1+"_win_count";
		return (int)desk.get(key);
	}
	
	public int get_draw_count(){
		return (int)desk.get(draw_count);
	}
	public int get_game_played_count(){
		return (int)desk.get(game_played_count);
	}
	
	public int get_score(int from_0to1){
		String key = "p"+from_0to1+"_score";
		return (int)desk.get(key);
	}
	private void set_score(int from_0to1, int value){
		String key = "p"+from_0to1+"_score";
		desk.put(key, value);
	}
	
	private boolean is_Tuzdyk(int from_0to1){
		String key = "p"+from_0to1+"_is_tuzdyk";
		return (boolean)desk.get(key);
	}
	private void set_is_Tuzdyk(int from_0to1, boolean set){
		String key = "p"+from_0to1+"_is_tuzdyk";
		desk.put(key, set);
	}
	private int get_tuzdykCellNumber(int from_0to1){
		String key = "p"+from_0to1+"_tuzdyk_0to8";
		return (int)desk.get(key);
	}
	private void set_TuzdykCellNumber(int from_0to1, int n_0to8){
		String key = "p"+from_0to1+"_tuzdyk_0to8";
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
		return (boolean)desk.get(chey_hod);
	}
	private void switch_chey_hod(){
		String key = "chey_hod";
		boolean temp = (boolean)desk.get(key);
		desk.put(key, !temp);
	}
	
	private void incrementWinCount(int from_0to1){
		String key = "p"+from_0to1+"_win_count";
		int win_count = (int)desk.get(key);
		desk.put(key, ++win_count);
	}
	
	private void incrementDrawCount(){
		int temp_count = (int)desk.get(draw_count);
		desk.put(draw_count, ++temp_count);
	}
	
	private void incrementGamePlayedCount(){
		int temp_count = (int)desk.get(game_played_count);
		desk.put(game_played_count, ++temp_count);
	}
	
	private String get_name_alligned(int from_0to1){
		int p0_length = get_name(0).length();
		int p1_length = get_name(1).length();
		int[] length = {p0_length,p1_length};
		String temp = get_name(from_0to1);
		if(length[from_0to1] < length[(from_0to1+1)%2]){
			for(int i = length[from_0to1];i <= length[(from_0to1+1)%2];i++){
				temp += " ";
			}
			return temp;
		}else{
			return temp;
		}
	}
	
	private String cellToString(int from_0to1, boolean reverse){
		String to = "";
		if(!reverse){
			for(int i = 0;i<cellSize;i++){
				if(i!=this.get_tuzdykCellNumber(from_0to1))
					to = to + String.format("%3d", get_cell_value(from_0to1, i));
				else
					to = to + "  *";
			}
		}else{
			for(int i = cellSize-1;i>=0;i--){
				if(i!=this.get_tuzdykCellNumber(from_0to1))
					to = to + String.format("%3d", get_cell_value(from_0to1, i));
				else
					to = to + "  *";
			}
		}
		
		return to;
	}
	
	public boolean checkWin(){
		int winScore = cellSize*cellSize;
		if( get_score(0) > winScore ){
			incrementGamePlayedCount();
			incrementWinCount(0);
			return true;
		}else if(get_score(1) > winScore){
			incrementGamePlayedCount();
			incrementWinCount(1);
			return true;
		}else if(get_score(0)==winScore && get_score(1)==winScore){
			System.out.println(ANSI_RED+"Draw"+ANSI_RESET);
			incrementGamePlayedCount();
			incrementDrawCount();
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String toString(){
		return toString(defaultColorScheme);
	}
	
	public String toString(String[] colorScheme){
		String DeskColor = colorScheme[0];
		String InfoColor = colorScheme[1];
		String HodDestColor = colorScheme[2];
		
		String toString = HodDestColor+"Hod #"+get_hod_number()+ANSI_RESET+"\n";
		if(get_hod_number()!=0){
			toString+=HodDestColor+"From "+get_move1to9()+" to "+get_DestinationCELL()+ANSI_RESET+"\n";
		}
		toString = toString+String.format("%s%s\t%s%s\t Score: %d\t Win: %d%s",DeskColor, cellToString(1,true),InfoColor,get_name_alligned(1),get_score(1),get_win_count(1),ANSI_RESET);
		if(chey_hod()){
			toString = toString + " *\n";
		}else{
			toString = toString + "\n";
		}
		toString = toString+String.format("%s%s\t%s%s\t Score: %d\t Win: %d%s",DeskColor, cellToString(0,false),InfoColor,get_name_alligned(0),get_score(0),get_win_count(0),ANSI_RESET);
		if(!chey_hod()){
			toString = toString + " *";
		}
		return toString;
		
	}
	
	public void reset(){
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
