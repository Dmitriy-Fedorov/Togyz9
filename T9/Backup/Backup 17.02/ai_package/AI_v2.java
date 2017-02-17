package ai_package;
//import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import main_package.*;

public class AI_v2 {
	private static Desk player[] = {new Desk(),new Desk()};
	private static Kazan Kazan[] = {new Kazan(),new Kazan()};
	private /*static*/ Desk virtDesk[] = {new Desk(true),new Desk(true)};
	private /*static*/ Kazan virtKazan[] = {new Kazan(true),new Kazan(true)};
	//private ArrayList<Desk> reDesk = new ArrayList<>();
	//private ArrayList<Kazan> reKazan = new ArrayList<>();
	
	public AI_v2(){
		
	}
	
	public AI_v2(Desk player_1,Desk player_0,Kazan Kazan_1,Kazan Kazan_0){
		player[1] = player_1;
		player[0] = player_0;
		Kazan[1] = Kazan_1;
		Kazan[0] = Kazan_0;
	}
	
	//get array and score; and put this in virtual desks
	void memorizeDesk(){
		int[] cell1=player[1].getDesk();
		int[] cell0=player[0].getDesk();
		virtDesk[1].setDesk(cell1);
		virtDesk[0].setDesk(cell0);
		virtKazan[1].setKazan(Kazan[1].checkScore());
		virtKazan[0].setKazan(Kazan[0].checkScore());
		/*
		System.out.println("Virtual desk print:");
		print_T9.printDesk(1,virtDesk[1],virtDesk[0],virtKazan[1],virtKazan[0]);
		System.out.println("Real desk print:");
		*/
	}
	
	public int dumbTreePlay_v2(int player_,int dummy_depth,int dummy_width,boolean echo){
		
		int[] scoreTemp = new int[9];
		int tempDest, tophod;
		
		for(int i=0;i<9;i++){
			tempDest = player[player_].getDestinationCELL(i,echo,"1to9 format");
			//System.out.println("tempDest"+i+" :"+tempDest);
			if(tempDest>0){
				scoreTemp[i] = this.getVirtScoreTemp((player_+1)%2, tempDest);
				//System.out.println("scoreTemp"+i+" :"+scoreTemp[i]);
			}
		}
		
		tophod = getTopHod(scoreTemp);
				//print_T9.doMove(i%2, storeMove[i], virtDesk[1], virtDesk[0], virtKazan[1], virtKazan[0],false);
				//print_T9.printDesk(i%2, virtDesk[1], virtDesk[0], virtKazan[1], virtKazan[0]);
			
		
		return tophod;
	}
	
	private int getVirtScoreTemp(int player_,int tempDest){
		
		int ifeven = player[player_].checkValue(tempDest-1)+1;	//0to8 format convert
		//System.out.println("ifeven"+" :"+ifeven);
		if(ifeven%2==0)
			return ifeven;
		else
			return 0;
	}
	
	private int getTopHod(int[] scoreTemp){
		int max1=0,max2=0,max3=0,temp=-1,tempTop;
		int[] top3 = {0,0,0};
		
		for(int i=0;i<9;i++){
			//System.out.println("scoreTemp"+i+" :"+scoreTemp[i]);
			if(scoreTemp[i]>max3){
				temp=max3;
				max3=scoreTemp[i];
				tempTop=top3[2];
				top3[2]=i;
				if(temp>max2){
					top3[1]=tempTop;
					max2=temp;
				}else if(temp>max2){
					top3[0]=tempTop;
					max1=temp;
				}
					
			}else if(scoreTemp[i]>max2){
				temp=max2;
				max2=scoreTemp[i];
				tempTop=top3[1];
				top3[1]=i;
				if(temp>max1){
					top3[0]=tempTop;
					max1=temp;
				}
					
			}else if(scoreTemp[i]>max1){
				top3[0]=i;
				max1=scoreTemp[i];
				//temp=max2;
			}

		}
		
		if(max3==0){
			int randomNum = ThreadLocalRandom.current().nextInt(0, 8 + 1);
			System.out.println("random move "+top3[0]+top3[1]+top3[2]);
			return randomNum;
			
		}else{
			System.out.println("top3 hod :"+top3[0]+top3[1]+top3[2]);
			return top3[ThreadLocalRandom.current().nextInt(0, 2 + 1)];
		}
		
		
		
	}

}