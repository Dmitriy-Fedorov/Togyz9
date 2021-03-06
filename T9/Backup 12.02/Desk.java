
public class Desk extends Kazan{
	private int yamka[] = {9,9,9,9,9,9,9,9,9};
	//tuzdyk sortcut
	//private int yamka[] = {2,2,9,9,90,9,9,11,2};
	private int destinationCELL;
	private int tuzdyk = -1;
	private boolean isTuzdyk = false;
	private int arraySum;
	private boolean editable = false;
	
	Desk(){
		
	}
	
	Desk(boolean editable_){
		editable = editable_;
	}
	
	void setDesk(int a[]){
		if(editable){
			for(int i=0;i<9;i++){
				yamka[i]=a[i];
			}
		}else{
			System.out.println("This desk is not editable");
		}

	}
	
	int[] getDesk(){
		return yamka;
	}
	
	void resetDesk(){
		for(int i=0;i<9;i++){
			yamka[i]=9;
		}
		tuzdyk=-1;
		isTuzdyk=false;
		
	}
	
	//for AI
	int getArraySum(){
		arraySum=0;
		for(int i=0;i<9;i++){
			arraySum+=yamka[i];
		}
		return arraySum;
	}
	
	int getDestinationCELL(int play){
		//to check flow
		System.out.print("destCELL "+play+ ":" +(destinationCELL+1));
		return destinationCELL;
	}
	
	boolean checkZero(int n){
		if(yamka[n]==0)	return true;
		else return false;
	}
	
	boolean checkOne(int n){
		if(yamka[n]==1)	return true;
		else return false;
	}
	
	void setZero(int n){
		yamka[n]=0;
	}
	void setOne(int n){
		yamka[n]=1;
	}
	
	boolean checkTuzdyk(int n){
		if(yamka[n]==3 && n!=8)	return true;
		else return false;
	}
	
	boolean statusTuzdyk(){
		return isTuzdyk;
	}
	
	void setTuzdyk(int TUZ){
		tuzdyk = TUZ;
		isTuzdyk = true;
	}
	
	int getTuzdykCellNumber(){
		return tuzdyk;
	}
	
	int checkValue(int n){
		return yamka[n];
	}
	
	int increment(int begin){
		if(yamka[begin]==0){
			//just in case if debug will not handle error
			System.out.println("Cell is empty");
			return -1;
		}else if(yamka[begin]==1){
			if(begin == 8){
				destinationCELL=0;
				//System.out.println("dest opponent " + destinationCELL);
				return 1;
			}else{
				yamka[begin+1]++;
				destinationCELL = begin+1;
				//System.out.println("dest my " + destinationCELL);
				return 0;
			}
		}else{
			for(int i=begin +1;i<9;i++){
				yamka[i]++;
			}
			//destinationCELL=1;	//no need in this line
			return yamka[begin]-(9-begin);
		}
		
	}
	
	int increment(int remainder,int from){
		// always should began from zero
		if(remainder > 9){
			for(int i = from; i<9;i++){
				yamka[i]++;
			}
			return remainder - 9;
		}else if(remainder >=0 && remainder <= 9){
			for(int i = from; i <= remainder-1; i++){
				yamka[i]++;
			}
			destinationCELL = remainder -1;
			//System.out.println("remainder-1 " + destinationCELL);
			return 0;
		}else{
			return -1;
		}
		
	}
	
	int emptying(int n){
		int a = yamka[n];
		yamka[n] = 0;
		return a;
	}
	
	void printDesk(int hod){
		if(hod%2 == 0){
			for(int i = 0;i<9;i++){
				if(i!=tuzdyk)
					System.out.format("%3d",yamka[i]);
				else
					System.out.print("  *");
			}
				
		}else{
			for(int i = 8;i>=0;i--){
				if(i!=tuzdyk)
					System.out.format("%3d",yamka[i]);
				else
					System.out.print("  *");
			}
				
		}
			
	}
	

}
