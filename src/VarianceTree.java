import java.util.ArrayList;
import java.util.HashMap;

public class VarianceTree {

	public Node buildVarianceTree(ArrayList<ArrayList<String>> dataSet, ArrayList<String> attributeList) {
		Utilities uti = new Utilities();	
		HashMap<String,ArrayList<ArrayList<String>>> reduceMap = new HashMap<String,ArrayList<ArrayList<String>>>();
		
	
		double positiveCount = 0;
		double negativeCount = 0;
		
		for(int i=1; i < dataSet.size();i++){
			if(dataSet.get(i).get(dataSet.get(i).size()-1).equalsIgnoreCase("1")){
				positiveCount++;
			}
			else{
				negativeCount++;
			}
		}
		
		
		if ((attributeList.size() < 1) || (positiveCount == (dataSet.size() - 1))) {
			return new Node("1");
		}else if((negativeCount == (dataSet.size() - 1))){
			return new Node("0");
		}else{
			FindBestAtt fb = new FindBestAtt();
			
			String bestAttribute = fb.bestAttributeVariance(dataSet,attributeList);
			reduceMap = uti.reducedMap(dataSet,bestAttribute);
			
			ArrayList<String> newAttList = new ArrayList<String>();
			for(String val: attributeList){
				if(!val.equalsIgnoreCase(bestAttribute)){
					newAttList.add(val);
				}
			}


			if (reduceMap.size() < 2){
				String value = "0";
				if( positiveCount > negativeCount){
					value = "1";
				}

				return new Node(value);
			}

			
			return new Node(bestAttribute,buildVarianceTree(reduceMap.get("0"),newAttList),buildVarianceTree(reduceMap.get("1"),newAttList));
		}

	}
}	
