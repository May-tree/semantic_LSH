package Functions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Random;

public class MinHash {
	private ArrayList<ArrayList<Integer>> hashValues;
    public static HashMap<String, Integer> uHstb = new HashMap<String, Integer>();
    private final int gramFactor;
    public int numOfFunc;
    public static final int MAX_INT_SMALLER_TWIN_PRIME = 2147482949;
    public static int max = Integer.MAX_VALUE;
    private String DB;

    public MinHash(int gramF, int numOfF, String DB) {
        this.DB=DB;
    	gramFactor = gramF;
        numOfFunc = numOfF;
        hashValues=new ArrayList<ArrayList<Integer>>();
        produceHashValues();
    }
    private void produceHashValues(){
    	for(int i=0;i<numOfFunc;i++){
    		Random rand= new Random();
    		ArrayList<Integer> values=new ArrayList<Integer>();
    		for(int j=0;j<3;j++){
    			values.add(rand.nextInt(1000000000));
    		}
    		hashValues.add(deepCopy(values));
    	}
    }
    public ArrayList<Integer> deepCopy(ArrayList<Integer> somelist){
    	ArrayList<Integer> copy=new ArrayList<Integer>();
    	for(int i:somelist){
    		copy.add(i);
    	}
    	return copy;
    }

    //extend the universal character table, which grows with the signature computing process one by one  
    public static void addToUniversalHstb(HashSet<String> set) {
        for (String t : set) {
            if (uHstb.containsKey(t) == false) {
                uHstb.put(t, uHstb.size() + 1);
            }
        }
    }

    //Hash a certain row number with a certain seed, which represents a certain hashfunction 
    private int hashFunc(int orig, int seed) {
        return (int) ((hashValues.get(seed).get(0) * (orig >> 4) + hashValues.get(seed).get(1) * orig + hashValues.get(seed).get(2)) % MAX_INT_SMALLER_TWIN_PRIME);
    }
    
    
    
    //old
//    private int hashFunc(int orig, int seed) {
//        Random rand = new Random(seed);
//        int[] seedsValues = new int[3];
//        seedsValues[0] = rand.nextInt(1000000000);
//        seedsValues[1] = rand.nextInt(1000000000);
//        seedsValues[2] = rand.nextInt(1000000000);
//        return (int) ((seedsValues[0] * (orig >> 4) + seedsValues[1] * orig + seedsValues[2]) % MAX_INT_SMALLER_TWIN_PRIME);
//    }

    //Initialize the signature List for any record
    private ArrayList<Integer> initSL() {
        ArrayList<Integer> List = new ArrayList<Integer>();
        for (int i = 0; i < numOfFunc; i++) {
            List.add(max);
        }
        return List;
    }

    //Compute the signature List for a certain record
    public ArrayList<Integer> sig(String record) {
    	HashSet<String> recordset;
    	
    	//new
    	if(DB.equals("CORA")){
    		recordset= CollectionOperator.shingle(record, gramFactor);
    	}
    	else{
    		recordset = CollectionOperator.shingleNC(record, gramFactor);
    	}
    	
    	//old
//    	recordset= CollectionOperator.shingle(record, gramFactor);
    	
        addToUniversalHstb(recordset);
        ArrayList<Integer> record_sig;
        record_sig = initSL();
        for (int i = 0; i < numOfFunc; i++) {
            for (String element : recordset) {
                record_sig.set(i, Math.min(record_sig.get(i), hashFunc(uHstb.get(element), i)));
            }
        }
        return record_sig;
    }

    public void establishUniverseDic(String record) {
        HashSet<String> recordset = CollectionOperator.shingle(record, gramFactor);
        addToUniversalHstb(recordset);
    }

}
