package ai_package;

import java.util.ArrayList;
import java.util.Comparator;

public class datagramComparator implements Comparator<ArrayList<Integer>> {
	
	datagramComparator(){
		
	}
	@Override
	public int compare(ArrayList<Integer> data0, ArrayList<Integer> data1) {
		int delta0 = data0.get(10)-data0.get(20);
		int delta1 = data1.get(10)-data1.get(20);
		return delta0 - delta1;
	}

}
