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
		int tree_style=Integer.parseInt(args[3]);
		int k=Integer.parseInt(args[4]);
		int l=Integer.parseInt(args[5]);
		int semantic_Switch=Integer.parseInt(args[6]);
		int n=Integer.parseInt(args[7]);
		int inputsize=Integer.parseInt(args[8]);
		Importer imp=new Importer(DB,path,tree_path,tree_style,inputsize,semantic_Switch);
		int size=imp.idset.size();
		double dbsize=(double)size;
		double numOfPairs = dbsize/10000 * (dbsize - 1) / 2;
        HashMap<Integer, HashSet<Integer>> originCluster = imp.originCluster;
        HashSet<HashSet<Integer>> originGlobalPair = CollectionOperator.pairGraph(originCluster);
        int Nd = originGlobalPair.size();
        int Np;
        int Nbd;
        int Nb;
        HashMap<Integer, String> recordList = imp.recordList;
        HashMap<Integer, ArrayList<Integer>> semanList = imp.semanticSig;
        int gramFactor=2;
        MinHash mHasher = new MinHash(gramFactor, k * l,DB);
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
        HashSet<HashSet<Integer>> binaryBlockSets = LSH.binaryBlockSets(pureBlockSets);
        LSH.obtainNb(pureBlockSets);
        LSH.obtainNp(binaryBlockSets, originGlobalPair);
        Nb=LSH.Nb;
        Np = LSH.Np; //number of true positives from lSH blocking
        Nbd= LSH.Nbd; //number of unique pairs in lsh blocking 
        String filename_0=DB+"_"+k+"_"+l+"_"+inputsize+"_STATS.csv";
//      String filename=DB+"_"+k+"_"+l+"_"+semantic_Switch+"_"+n+"_"+inputsize+".csv";
	    File csv_0 = new File("./"+filename_0);
	    FileWriter fw_0=new FileWriter(csv_0, false);
	    BufferedWriter bw_0=new BufferedWriter(fw_0);
        double PC = (double) Np / (double) Nd;
        double PQ = (double) Np / (double) Nbd;
        double PQ_meta=(double) Np / (double) Nb;
        double RR = 1 - (double) Nbd/10000 / (double) numOfPairs;
        double FM = (double) (PQ * PC) / (double) (PQ + PC);
        bw_0.write("#PURE LSH");
        bw_0.newLine();
        bw_0.write("PC:"+Double.toString(PC));
        bw_0.newLine();
        bw_0.write("PQ:"+Double.toString(PQ));
        bw_0.newLine();
        bw_0.write("PQ_META:"+Double.toString(PQ_meta));
        bw_0.newLine();
        bw_0.write("RR:"+Double.toString(RR));
        bw_0.newLine();
        bw_0.write("FM:"+Double.toString(FM));
        bw_0.newLine();
        String filename=DB+"_"+k+"_"+l+"_"+inputsize+".csv";
//      String filename=DB+"_"+k+"_"+l+"_"+semantic_Switch+"_"+n+"_"+inputsize+".csv";
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
        if(semantic_Switch==1){
        	lshBuckets = lsher.lshBucketO(lshBuckets,semanList);
        	rawBlockSets = LSH.rawBlockSets(lshBuckets);
            pureBlockSets = CollectionOperator.removeSubSet(rawBlockSets);
            binaryBlockSets = LSH.binaryBlockSets(pureBlockSets);
            LSH.obtainNb(pureBlockSets);
            LSH.obtainNp(binaryBlockSets, originGlobalPair);
            Np = LSH.Np; //number of true positives from lSH blocking
            Nbd= LSH.Nbd; //number of unique pairs in lsh blocking
            Nb=LSH.Nb;
            PC = (double) Np / (double) Nd;
            PQ = (double) Np / (double) Nbd;
            PQ_meta=(double) Np / (double) Nb;
            RR = 1 - (double) Nbd/10000 / (double) numOfPairs;
            FM = (double) (PQ * PC) / (double) (PQ + PC);
            bw_0.write("#SA-LSH");
            bw_0.newLine();
            bw_0.write("PC:"+Double.toString(PC));
            bw_0.newLine();
            bw_0.write("PQ:"+Double.toString(PQ));
            bw_0.newLine();
            bw_0.write("PQ_META:"+Double.toString(PQ_meta));
            bw_0.newLine();
            bw_0.write("RR:"+Double.toString(RR));
            bw_0.newLine();
            bw_0.write("FM:"+Double.toString(FM));
        }
        if(semantic_Switch==2){
        	lshBuckets = lsher.lshBucketN(lshBuckets,semanList);
        	rawBlockSets = LSH.rawBlockSets(lshBuckets);
            pureBlockSets = CollectionOperator.removeSubSet(rawBlockSets);
            binaryBlockSets = LSH.binaryBlockSets(pureBlockSets);
            LSH.obtainNb(pureBlockSets);
            LSH.obtainNp(binaryBlockSets, originGlobalPair);
            Np = LSH.Np; //number of true positives from lSH blocking
            Nbd= LSH.Nbd; //number of unique pairs in lsh blocking
            Nb=LSH.Nb;
            PC = (double) Np / (double) Nd;
            PQ = (double) Np / (double) Nbd;
            PQ_meta=(double) Np / (double) Nb;
            RR = 1 - (double) Nbd/10000 / (double) numOfPairs;
            FM = (double) (PQ * PC) / (double) (PQ + PC);
            bw_0.write("#SA-LSH");
            bw_0.newLine();
            bw_0.write("PC:"+Double.toString(PC));
            bw_0.newLine();
            bw_0.write("PQ:"+Double.toString(PQ));
            bw_0.newLine();
            bw_0.write("PQ_META:"+Double.toString(PQ_meta));
            bw_0.newLine();
            bw_0.write("RR:"+Double.toString(RR));
            bw_0.newLine();
            bw_0.write("FM:"+Double.toString(FM));
        }
        bw_0.close();
        
       
	}
}
