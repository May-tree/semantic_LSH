package Import;
import Functions.leaveMap;
import Functions.semTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Importer {
    public HashMap<Integer, String> recordList;
    public HashMap<Integer, ArrayList<Integer>> semanticSig;
    public HashMap<Integer, HashSet<Integer>> originCluster;
    public HashSet<Integer> idset;
    public semTree sTree;
    public long mappingStart,mappingEnd,consTreeStart,consTreeEnd;
    public String path;
    public String tree_path;
    public int inputsize;
    public int Semantic_Switch;
    public Importer(String DB,String path,String tree_path,int inputsize,int Semantic_Switch) throws ClassNotFoundException, FileNotFoundException, IOException{
    	recordList = new HashMap<Integer, String>();
    	this.Semantic_Switch=Semantic_Switch;
    	if(Semantic_Switch>0){
    		semanticSig = new HashMap<Integer, ArrayList<Integer>>();
    	}
        originCluster = new HashMap<Integer, HashSet<Integer>>();
        idset = new HashSet<Integer>();
        this.path=path;
        this.tree_path=tree_path;
        if(DB.equals("CORA")){
        	CoraImport(inputsize);
        }
        else{
        	NcVoterImport(inputsize);
        }
    }
    public void NcVoterImport(int size) throws ClassNotFoundException, FileNotFoundException, IOException {
    	if(Semantic_Switch>0){
    		consTreeStart=System.currentTimeMillis();
    		sTree=new semTree(tree_path);
        	consTreeEnd=System.currentTimeMillis();
    	}
        File csv = new File(path);
        @SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(csv));
        br.readLine();
        if(Semantic_Switch>0){
        	mappingStart=System.currentTimeMillis();
        }
        for(int i=0;i<size;i++){
            String[] line=br.readLine().replace("\"", "").split(",");
            StringBuilder longString = new StringBuilder();
            longString.append(line[2]);
            longString.append(" ");
            longString.append(line[3]); 
            int cluster=Integer.valueOf(line[1]);
            int id=Integer.valueOf(line[0]);
            if(Semantic_Switch>0){
	            String[] props=new String[2];
	            props[0]=line[4];
	            props[1]=line[5];
	            
	            // mapping to leaves
	            HashSet<String> leaveSet=leaveMap.getNCVoterLeaves(props);
	            
	            // get signatures
	            ArrayList<Integer> semanticSig1=sTree.getSigs(leaveSet);
	            semanticSig.put(id, semanticSig1);
            }
            recordList.put(id,longString.toString());
            idset.add(id);
            if (originCluster.containsKey(cluster)) {
                HashSet<Integer> tempSet = originCluster.get(cluster);
                tempSet.add(id);
                originCluster.put(cluster, tempSet);
            } else {
                HashSet<Integer> tempSet = new HashSet<Integer>();
                tempSet.add(id);
                originCluster.put(cluster, tempSet);
            }
        }
        if(Semantic_Switch>0){
        	mappingEnd=System.currentTimeMillis();
        }
    }
    public void CoraImport(int size) throws ClassNotFoundException, FileNotFoundException, IOException {
    	if(Semantic_Switch>0){
    		consTreeStart=System.currentTimeMillis();
    		sTree=new semTree(tree_path);
        	consTreeEnd=System.currentTimeMillis();
    	}
        File csv = new File(path);
        @SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(csv));
        br.readLine();    
        if(Semantic_Switch>0){
        	mappingStart=System.currentTimeMillis();
        }
        for(int i=0;i<size;i++){
        	String origin=br.readLine();
        	origin=origin.substring(0,origin.length()-23);
            String[] line=origin.replace("\"", "").split("\\|");
            StringBuilder longString = new StringBuilder();
            longString.append(line[1]); //title
            longString.append(" ");
            if(line.length>18){
            	longString.append(line[18]); //authours
            }
            int cluster=Integer.valueOf(line[17]); //cluster id
            int id=Integer.valueOf(line[0]); //id
            if(Semantic_Switch>0){
            	String[] props=new String[6];
                props[0]=line[9]; //tech
                props[1]=line[15]; //type
                props[2]=line[4]; //note
                props[3]=line[6]; //journal
                props[4]=line[10]; //institution
                props[5]=line[11]; //booktitle
                HashSet<String> leaveSet=leaveMap.getCoraLeaves(props);
                ArrayList<Integer> semanticSig1=sTree.getSigs(leaveSet);
                semanticSig.put(id, semanticSig1);
            }
            recordList.put(id,longString.toString());
            idset.add(id);
            if (originCluster.containsKey(cluster)) {
                HashSet<Integer> tempSet = originCluster.get(cluster);
                tempSet.add(id);
                originCluster.put(cluster, tempSet);
            } else {
                HashSet<Integer> tempSet = new HashSet<Integer>();
                tempSet.add(id);
                originCluster.put(cluster, tempSet);
            }
        }
        if(Semantic_Switch>0){
        	mappingEnd=System.currentTimeMillis();
        }
    }
}
