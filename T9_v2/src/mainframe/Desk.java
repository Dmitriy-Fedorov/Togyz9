package mainframe;

import java.util.ArrayList;

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
	
	public Desk(String name, boolean editable){
		super(editable);
		this.name = name;
		this.editable = editable;
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
	
	/**
	 * @param n_0to8
	 * @return true if specified cell is empty
	 */
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
	public int getDestinationCELL(int input_0to8){
		int temp, input_1to9 = input_0to8+1, dest_1to9;
		if(cell[input_0to8]==1){
			if(input_1to9==9){
				dest_1to9 = 1;
			}else{
				dest_1to9 = -input_1to9 - 1;
			}
		}else if(cell[input_0to8]==0){
			dest_1to9 = -input_1to9;
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
		int score = this.checkScore();
		int destCell_1to9 = this.getDestinationCELL(begin_1to9-1);
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
		
		return this.checkScore()-score;
	}
	
	public int checkMove(Desk opponent, int n_1to9){
		int destCell = this.getDestinationCELL(n_1to9-1);
		if(destCell>0){
			return opponent.checkValue(n_1to9-1)+1;
		}else
			return 0;
	}
	
	public static ArrayList<int[]> memorizeDesks(Desk player1,Desk player0, int hod_number){
		ArrayList<int[]> container = new ArrayList<>();
		int[] p1 = {player1.checkScore(),hod_number};
		int[] p0 = {player0.checkScore(),hod_number};
		container.add(player1.cell);	//0
		container.add(p1);				//1
		container.add(player0.cell);	//2
		container.add(p0);				//3
		return container;
	}
	
	public static int restoreDesks(Desk player1,Desk player0,ArrayList<int[]> container){
		player1.setDesk(container.get(0), container.get(1)[0]);
		player0.setDesk(container.get(2), container.get(3)[0]);
		/*
		for(int i=0;i<9;i++){
			System.out.print(container.get(2)[i]);
		}
		System.out.println("-cont2 \n");
		for(int i=0;i<9;i++){
			System.out.print(container.get(0)[i]);
		}
		System.out.println("-cont0 \n");*/
		System.out.println("Virt desk\n" + Desk.printDesk(player1, player0, true));
		return container.get(1)[1];
	}
	
	public static void transferDesks(Desk player1,Desk player0,Desk to_player1,Desk to_player0){
		to_player1.setDesk(player1.cell, player1.checkScore());
		to_player0.setDesk(player0.cell, player0.checkScore());
		System.out.println("Virt desk\n" + Desk.printDesk(to_player1, to_player0, true));
	}
	
	public static void transferDesks(Desk from_player,Desk to_player){
		to_player.setDesk(from_player.cell, from_player.checkScore());
	}
	
	
	public static ArrayList<Integer> toDatagram(boolean cheyHod,Desk player1,Desk player0){
		ArrayList<Integer> datagram = new ArrayList<>();
		// datagram {chey_hod :0,desk0[9] :1-9,score0 :10,desk1[9] :11-19,score1 :20}
		if(cheyHod){
			datagram.add(1);
		}else{
			datagram.add(0);
		}
		for(int i=0;i<9;i++){
			datagram.add(player0.cell[i]);
		}
		datagram.add(player0.checkScore());
		//System.out.println("p0 score: "+ player0.checkScore());
		for(int i=0;i<9;i++){
			datagram.add(player1.cell[i]);
		}
		datagram.add(player1.checkScore());
		//System.out.println(datagram);
		return datagram;
	}
	
	public static void fromDatagram(ArrayList<Integer> datagram, Desk modify1, Desk modify0){
		// datagram {chey_hod :0,desk0[9] :1-9,score0 :10,desk1[9] :11-19,score1 :20}
		//int[] cell0 = new int[9],cell1 = new int[9];
		for(int i=1;i<10;i++){
			//cell0[i-1] = datagram.get(i);
			modify0.cell[i-1] = datagram.get(i);
		}
		modify0.setKazan(datagram.get(10));
		for(int i=11;i<19;i++){
			//cell1[i-11] = datagram.get(i);
			modify1.cell[i-11] = datagram.get(i);
		}
		modify1.setKazan(datagram.get(20));
	}
	
	/**
	 * @param datagram
	 * This method prints out datagram in formated way 
	 */
	public static void printDatagram(ArrayList<Integer> datagram){
		Desk player1 = new Desk("    temp1",true);
		Desk player0 = new Desk("    temp0",true);
		Desk.fromDatagram(datagram, player1, player0);
		boolean hod;
		if(datagram.get(0)==0)
			hod = false;
		else
			hod = true;
		
		System.out.println(Desk.printDesk(player1, player0, hod));
	}
	
	public static String printDesk(Desk player1, Desk player0, boolean hod){
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
		toPrint1=toPrint1+String.format("%s\tScore: %d\tWin: %d", player1.name, player1.checkScore(),player1.getWinCount());
		if(hod){
			toPrint1=toPrint1+" *";
		}
		for(int i = 0;i<9;i++){
			if(i!=player0.tuzdyk_0to8)
				toPrint0 = toPrint0 + String.format("%3d", player0.cell[i]);
			else
				toPrint0 = toPrint0 + "  *";
		}
		toPrint0=toPrint0+String.format("%s\tScore: %d\tWin: %d", player0.name, player0.checkScore(),player0.getWinCount());
		if(!hod){
			toPrint0=toPrint0+" *";
		}
		toPrint = String.format("%s \n%s", toPrint1, toPrint0);
		return toPrint;
	}
	
	
	@Override
	public boolean checkWin(){
		if(super.checkWin() || this.isDeskEmpty()){
			super.winCounterPlusPlus();
			return true;
		}else
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
