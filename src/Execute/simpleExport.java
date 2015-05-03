package Execute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import Functions.CollectionOperator;
import Functions.LSH;
import Functions.MinHash;
import Import.Importer;

public class simpleExport {
	public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, IOException{
		String DB=args[0];
		String path=args[1];
		String tree_path=args[2];
		int k=Integer.parseInt(args[3]);
		int l=Integer.parseInt(args[4]);
		int semantic_Switch=Integer.parseInt(args[5]);
		int n=Integer.parseInt(args[6]);
		int inputsize=Integer.parseInt(args[7]);
		Importer imp=new Importer(DB,path,tree_path,inputsize,semantic_Switch);
        HashMap<Integer, String> recordList = imp.recordList;
        HashMap<Integer, ArrayList<Integer>> semanList = imp.semanticSig;
        int gramFactor=2;
        MinHash mHasher = new MinHash(gramFactor, k * l);
        System.out.println(inputsize+" records has been imported");
        
        HashMap<Integer, ArrayList<Integer>> sigList = new HashMap<Integer, ArrayList<Integer>>();
        for (int key : recordList.keySet()) {
            sigList.put(key, mHasher.sig(recordList.get(key)));
        }
 
        System.out.println("minHash is done.");

        ArrayList<HashMap<List<Integer>, HashSet<Integer>>> lshBuckets;

        LSH lsher = new LSH(k, l, n);
        lshBuckets = lsher.lshBucket(sigList);
        System.out.println("LSH is done.");
        System.out.println();

        HashSet<HashSet<Integer>> rawBlockSets = LSH.rawBlockSets(lshBuckets);
        HashSet<HashSet<Integer>> pureBlockSets = CollectionOperator.removeSubSet(rawBlockSets);

        if(semantic_Switch==1){
        	lshBuckets = lsher.lshBucketO(lshBuckets,semanList);
        	rawBlockSets = LSH.rawBlockSets(lshBuckets);
            pureBlockSets = CollectionOperator.removeSubSet(rawBlockSets);
        }
        if(semantic_Switch==2){
        	lshBuckets = lsher.lshBucketN(lshBuckets,semanList);
        	rawBlockSets = LSH.rawBlockSets(lshBuckets);
            pureBlockSets = CollectionOperator.removeSubSet(rawBlockSets);
        }
        String filename=DB+"_"+k+"_"+l+"_"+semantic_Switch+"_"+n+"_"+inputsize+".csv";
        File csv = new File("./"+filename);
        FileWriter fw=new FileWriter(csv, false);
        BufferedWriter bw=new BufferedWriter(fw);
        for(HashSet<Integer> block:pureBlockSets) {
        	StringBuffer sb=new StringBuffer();
            for (int entry:block) {  
                sb.append(entry);
                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            bw.write(sb.toString());
            bw.newLine();
        }
        bw.close();
       
	}
}
