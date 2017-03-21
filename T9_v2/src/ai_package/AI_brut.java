package ai_package;
import java.util.concurrent.ThreadLocalRandom;

import mainframe.Desk;

public class AI_brut {
	private static int randomNum_1to9 = 0;
	
	Desk player_AI[] = {new Desk("     AI_brut_0*", true),
			new Desk("     AI_brut_1*", true)};
	
	public AI_brut(){
		
	}
	
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
}
