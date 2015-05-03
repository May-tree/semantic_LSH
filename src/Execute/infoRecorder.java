package Execute;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import Functions.CollectionOperator;
import Functions.LSH;
import Functions.MinHash;
import Import.Importer;

public class infoRecorder {
	private String DB;
	private String path;
	private String tree_path;
	private int k;
	private int l;
	private int n;
	private int inputsize;
	private int semantic_Switch;
	private int gramFactor=2;
	private long minHashStart,minHashEnd,minHashStartM,minHashEndM,lshStart,lshEnd,lshStartM,lshEndM;
	private long lshOEnd,lshOStart,lshNEnd,lshNStart,lshOEndM,lshOStartM,lshNEndM,lshNStartM;
	public void settingDisplay(){
		System.out.println("Overall Experiment Setting:");
    	System.out.println("Database: "+DB);
    	String semanSwi[]={"No_Semantic","Logical OR","Logical AND","MAXIMUM"};
    	if(semantic_Switch==0){
    		System.out.println("Semantic Setting: "+semanSwi[0]);
    	}
    	else{
    		System.out.println("Semantic Setting: "+semanSwi[semantic_Switch]+" with w="+n);
    	}
    	System.out.println("k: "+k);
    	System.out.println("l: "+l);
    	System.out.println();
	}
	public infoRecorder(String DB,String path,String tree_path,int k,int l,int semantic_Switch,int n,int inputsize){
		this.DB=DB;
		this.path=path;
		this.tree_path=tree_path;
		this.k=k;
		this.l=l;
		this.n=n;
		this.semantic_Switch=semantic_Switch;
		this.inputsize=inputsize;
	}
	public void timeRecord() throws ClassNotFoundException, FileNotFoundException, IOException{
		Importer imp=new Importer(DB,path,tree_path,inputsize,semantic_Switch);
        HashMap<Integer, HashSet<Integer>> originCluster = imp.originCluster;
        HashSet<HashSet<Integer>> originGlobalPair = CollectionOperator.pairGraph(originCluster);
        int Nd = originGlobalPair.size();
        int Np;
        int Nb;
        int Nbd;
        HashMap<Integer, String> recordList = imp.recordList;
        HashMap<Integer, ArrayList<Integer>> semanList = imp.semanticSig;
        
        
        

        
        System.out.println(inputsize+" records has been imported");
        Runtime mruntime = Runtime.getRuntime();
        System.gc();
        minHashStartM=mruntime.totalMemory()-mruntime.freeMemory();
        minHashStart = System.currentTimeMillis();
        MinHash mHasher = new MinHash(gramFactor, k * l);
        HashMap<Integer, ArrayList<Integer>> sigList = new HashMap<Integer, ArrayList<Integer>>();
        for (int key : recordList.keySet()) {
            sigList.put(key, mHasher.sig(recordList.get(key)));
        }
        minHashEnd = System.currentTimeMillis();
//        System.gc();
        minHashEndM=mruntime.totalMemory()-mruntime.freeMemory();

        
        
        System.out.println("minHash is done.");
        lshStart=System.currentTimeMillis();
        ArrayList<HashMap<List<Integer>, HashSet<Integer>>> lshBuckets;
        Runtime cruntime = Runtime.getRuntime();
        System.gc();
        lshStartM=cruntime.totalMemory()-cruntime.freeMemory();
        LSH lsher = new LSH(k, l, n);
        lshBuckets = lsher.lshBucket(sigList);
        System.out.println("LSH is done.");
        System.out.println();
//        System.gc();
        lshEndM=cruntime.totalMemory()-cruntime.freeMemory();
        lshEnd=System.currentTimeMillis();
        lshOEnd=lshOStart=lshNEnd=lshNStart=lshOEndM=lshOStartM=lshNEndM=lshNStartM=0;
        HashSet<HashSet<Integer>> rawBlockSets = LSH.rawBlockSets(lshBuckets);
        HashSet<HashSet<Integer>> pureBlockSets = CollectionOperator.removeSubSet(rawBlockSets);
        HashSet<HashSet<Integer>> binaryBlockSets = LSH.binaryBlockSets(pureBlockSets);
        LSH.obtainNb(pureBlockSets);
        LSH.obtainNp(binaryBlockSets, originGlobalPair);
        Np = LSH.Np; //number of true positives from lSH blocking
        Nb = LSH.Nb; //number of pairs in lsh blocking considering reduncencies
        Nbd= LSH.Nbd; //number of unique pairs in lsh blocking 
        System.out.println("After pure LSH:");
        System.out.println("pairs in ground_of_truth: "+Nd);
        System.out.println("true positives: "+Np);
        System.out.println("unique pairs: "+Nbd);
        System.out.println("no of pairs: "+Nb);
        System.out.println();
        if(semantic_Switch==1){
        	Runtime oruntime = Runtime.getRuntime();
        	System.gc();
        	lshOStartM=oruntime.totalMemory()-oruntime.freeMemory();
        	lshOStart=System.currentTimeMillis();
        	lshBuckets = lsher.lshBucketO(lshBuckets,semanList);
        	lshOEnd=System.currentTimeMillis();
        	lshOEndM=oruntime.totalMemory()-oruntime.freeMemory();
        	rawBlockSets = LSH.rawBlockSets(lshBuckets);
            pureBlockSets = CollectionOperator.removeSubSet(rawBlockSets);
            binaryBlockSets = LSH.binaryBlockSets(pureBlockSets);
            LSH.obtainNb(pureBlockSets);
            LSH.obtainNp(binaryBlockSets, originGlobalPair);
            Np = LSH.Np; //number of true positives from lSH blocking
            Nb = LSH.Nb; //number of pairs in lsh blocking considering reduncencies
            Nbd= LSH.Nbd; //number of unique pairs in lsh blocking 
            System.out.println("After incorporating Logical OR Semantics:");
            System.out.println("pairs in ground_of_truth: "+Nd);
            System.out.println("true positives: "+Np);
            System.out.println("unique pairs: "+Nbd);
            System.out.println("no of pairs: "+Nb);
            System.out.println();
        }
        if(semantic_Switch==2){
        	Runtime nruntime = Runtime.getRuntime();
        	System.gc();
        	lshNStartM=nruntime.totalMemory()-nruntime.freeMemory();
        	lshNStart=System.currentTimeMillis();
        	lshBuckets = lsher.lshBucketN(lshBuckets,semanList);
        	lshNEnd=System.currentTimeMillis();
        	lshNEndM=nruntime.totalMemory()-nruntime.freeMemory();
        	rawBlockSets = LSH.rawBlockSets(lshBuckets);
            pureBlockSets = CollectionOperator.removeSubSet(rawBlockSets);
            binaryBlockSets = LSH.binaryBlockSets(pureBlockSets);
            LSH.obtainNb(pureBlockSets);
            LSH.obtainNp(binaryBlockSets, originGlobalPair);
            Np = LSH.Np; //number of true positives from lSH blocking
            Nb = LSH.Nb; //number of pairs in lsh blocking considering reduncencies
            Nbd= LSH.Nbd; //number of unique pairs in lsh blocking 
            System.out.println("After incorporating Logical AND Semantics:");
            System.out.println("pairs in ground_of_truth: "+Nd);
            System.out.println("true positives: "+Np);
            System.out.println("unique pairs: "+Nbd);
            System.out.println("no of pairs: "+Nb);
            System.out.println();
        }
        System.out.println("Runtime:");
        if(semantic_Switch>0){
        	System.out.println((imp.consTreeEnd-imp.consTreeStart)+"ms for tree construction");
            System.out.println((imp.mappingEnd-imp.mappingStart)+"ms for leave mapping");
        }
        System.out.println((minHashEnd-minHashStart)+"ms for LSH builing");
        System.out.println((lshEnd-lshStart)+"ms for LSH calculation");
        if(semantic_Switch==1){
        	System.out.println((lshOEnd-lshOStart)+"ms for incorporating Logical Or Semantics");
        }
        if(semantic_Switch==2){
        	System.out.println((lshNEnd-lshNStart)+"ms for incorporating Logical And Semantics");
        }
        System.out.println();
        System.out.println("Memory usage:");
        System.out.println((minHashEndM-minHashStartM)+"bytes for LSH building");
        System.out.println((lshEndM-lshStartM)+"bytes for LSH calculation");
        if(semantic_Switch==1){
        	System.out.println((lshOEndM-lshOStartM)+"bytes for incorporating Logical Or Semantics");
        }
        if(semantic_Switch==2){
        	System.out.println((lshNEndM-lshNStartM)+"bytes for incorporating Logical And Semantics");
        }
        System.out.println();
        System.out.println();
	}
}
