package mainframe;

public class Desk extends Kazan{
	private int cell[] = {9,9,9,9,9,9,9,9,9};
	//tuzdyk debug shortcut
	//private int cell[] = {2,2,9,9,90,9,9,11,2};
	private int tuzdyk_0to8 = -1;
	private boolean isTuzdyk = false;
	private boolean editable = false;
	private String name;
	public Desk(String name){
		this.name = name;
	}
	
	public Desk(String name, int[] cells ,int score, boolean editable){
		super(score, editable);
		this.name = name;
		this.cell = cells;
		this.editable = editable;
	}
	
	
	//aim not to use this	//renamed in new version (from "setDesk")
	public void setDesk(int a[],int score){
		if(editable){
			for(int i=0;i<9;i++){
				cell[i]=a[i];
			}
		}else{
			System.out.println("This desk is not editable");
		}
		super.setKazan(score);

	}
	
	public int[] getDesk(){
		return this.cell;
	}
	public String getName(){
		return this.name;
	}
	
	//used for win state check
	public boolean isDeskEmpty(){
		for(int i=0;i<9;i++){
			if(cell[i]!=0){
				return false;
			}
		}
		System.out.println("Desk is empty");
		return true;
	}
	
	public boolean checkZero(int n_0to8){
		if(cell[n_0to8]==0)	return true;
		else return false;
	}
	public int checkValue(int n_0to8){
		return cell[n_0to8];
	}
	private boolean checkEven(int n_0to8){
		if(cell[n_0to8]%2==0)	return true;
		else return false;
	}
	
	private void setZero(int n_0to8){
		cell[n_0to8]=0;
	}
	private void setOne(int n_0to8){
		cell[n_0to8]=1;
	}
	
	//0to8 format
	private boolean checkTuzdyk(int n_0to8){
		if(cell[n_0to8]==3 && n_0to8!=8)	return true;
		else return false;
	}
	
	// modified
	private void setTuzdyk(int n_0to8, Desk opponent, boolean echo){
		if(!this.isTuzdyk){
			if(this.checkTuzdyk(n_0to8) && opponent.tuzdyk_0to8==-1){
				this.tuzdyk_0to8 = n_0to8;
				this.isTuzdyk = true;
				opponent.put(3);
				this.setZero(n_0to8);
			}else if(this.checkTuzdyk(n_0to8) && opponent.tuzdyk_0to8!=n_0to8){
				this.tuzdyk_0to8 = n_0to8;
				this.isTuzdyk = true;
				opponent.put(3);
				this.setZero(n_0to8);
			}
			
		}
		/*else{
			this.emptying(this.tuzdyk, opponent);
			if(echo)
				System.out.println("Tuzdyk is already exist or it can not be set at this cell");
		}*/
		
	}
	
	private void refreshTuzdyk(Desk opponent){
		if(this.getTuzdykCellNumber()!=-1){
			opponent.put(this.cell[this.getTuzdykCellNumber()]);
			this.setZero(this.getTuzdykCellNumber());
		}
		if(opponent.getTuzdykCellNumber()!=-1){
			this.put(opponent.cell[opponent.getTuzdykCellNumber()]);
			opponent.setZero(opponent.getTuzdykCellNumber());
		}
	}
	private int getTuzdykCellNumber(){
		return tuzdyk_0to8;
	}
	
	private int emptying(int n_0to8, Desk opponent){
		int a = this.cell[n_0to8];
		this.cell[n_0to8] = 0;
		opponent.put(a);
		return a;
	}
	
	private int increment(int begin_1to9){
		int begin_0to8 = begin_1to9 -1;
		if(cell[begin_0to8]==0){
			//just in case if debug will not handle error
			System.out.println("Cell is empty (Desk.increment(int begin) )");
			return -1;
		}else if(cell[begin_0to8]==1){
			if(begin_0to8 == 8){
				this.setZero(8);
				return 1; 
			}else{
				cell[begin_0to8+1]++;
				this.setZero(begin_0to8);
				return 0;
			}
		}else if(begin_0to8+cell[begin_0to8]>9){
			for(int i=begin_0to8+1; i<9; i++){
				cell[i]++;
			}
			int temp = (cell[begin_0to8]-(9-begin_0to8));
			this.setOne(begin_0to8);
			return temp;
		}else{
			for(int i=begin_0to8 +1;i<begin_0to8+cell[begin_0to8];i++){
				cell[i]++;
			}
			this.setOne(begin_0to8);
			return 0;
		}
	}
	
	private int incrementFrom0(int by){
		if(by <= 9){
			for(int i =0; i < by; i++){
				this.cell[i]++;
			}
			return 0;
		}else{
			for(int i=0;i<9;i++){
				this.cell[i]++;
			}
			return by - 9;
		}
	}
	
	// Format types "1to9" and "0to8"(default)
	private int getDestinationCELL(int input_0to8,boolean echo,String format){
		int temp, input_1to9 = input_0to8+1, dest_1to9;
		if(cell[input_0to8]==1){
			if(input_1to9==9){
				dest_1to9 = 1;
			}else{
				dest_1to9 = -input_1to9 - 1;
			}
		}else{
			/*	1to18
			18 17 16 15 14 13 12 11 10 
			 1  2  3  4  5  6  7  8  9
			  	
			  	0to17
			17 16 15 14 13 12 11 10  9
			 0  1  2  3  4  5  6  7  8
			*/
			temp = (input_0to8 + cell[input_0to8])%18;
			if(temp>9){
				dest_1to9 = temp-9;
			}else if(temp == 0){
				dest_1to9 = 9;
			}else{
				dest_1to9 = -temp;
			}
			
		}
		return dest_1to9;
	}	
		
	public int move(int begin_1to9, Desk opponent){
		
		int destCell_1to9 = this.getDestinationCELL(begin_1to9-1, false, "1to9");
		int remainder = this.increment(begin_1to9); //check +
		boolean enemy = false;
		//if enemy false destCell is on my desk
		
		//System.out.println("Desk.move, remainder: " + remainder);
		while(remainder > 0){
			
			if(remainder > 0){
				remainder = opponent.incrementFrom0(remainder);
				enemy = true;
				if(remainder > 0){
					remainder = this.incrementFrom0(remainder);
					enemy = false;
				}
			}
		}
		
		System.out.println("Desk.move, destCell_1to9: "+destCell_1to9);
		if(enemy){
			opponent.setTuzdyk(destCell_1to9 - 1, this, false);
			if(opponent.checkEven(destCell_1to9-1)){
				opponent.emptying(destCell_1to9 - 1, this);
			}
		}
		this.refreshTuzdyk(opponent);
		
		return 0;
	}
	
	public static void memorizeDesks(Desk player1,Desk player0){
		
	}
	
	public static String printDesk(Desk player1, Desk player0, boolean hod, int[] winCount){
		//hod true->player1, hod false-> player0
		String toPrint1 = "";
		String toPrint0 = "";
		String toPrint = "";
		
		for(int i = 8;i>=0;i--){
			if(i!=player1.tuzdyk_0to8)
				toPrint1 = toPrint1 + String.format("%3d", player1.cell[i]);
			else
				toPrint1 = toPrint1 + "  *";
		}
		toPrint1=toPrint1+String.format("\t%s\tScore: %d   Win: %d", player1.name, player1.checkScore(),winCount[1]);
		if(hod){
			toPrint1=toPrint1+" *";
		}
		for(int i = 0;i<9;i++){
			if(i!=player0.tuzdyk_0to8)
				toPrint0 = toPrint0 + String.format("%3d", player0.cell[i]);
			else
				toPrint0 = toPrint0 + "  *";
		}
		toPrint0=toPrint0+String.format("\t%s\tScore: %d   Win: %d", player0.name, player0.checkScore(),winCount[0]);
		if(!hod){
			toPrint0=toPrint0+" *";
		}
		toPrint = String.format("%s \n%s", toPrint1, toPrint0);
		return toPrint;
	}
	
	
	@Override
	public boolean checkWin(){
		if(super.checkWin() || this.isDeskEmpty())
			return true;
		else
			return false;
	}
	
	//renamed in new version (from "resetDesk")
	@Override
	public void reset(){
		for(int i=0;i<9;i++){
			this.cell[i]=9;
		}
		this.tuzdyk_0to8=-1;
		this.isTuzdyk=false;
		//new version
		super.reset();
	}
	
	@Override
	public String toString(){
		String to = "";
		for(int i = 0;i<9;i++){
			if(i!=this.tuzdyk_0to8)
				to = to + String.format("%3d", cell[i]);
			else
				to = to + "  *";
		}
		return to;
	}
}
