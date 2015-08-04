package Execute;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import Functions.CollectionOperator;




public class exportGroundOfTruth {
	public static HashMap<Integer, HashSet<Integer>> originCluster=new HashMap<Integer, HashSet<Integer>>();
	public static void main(String[] args) throws IOException{
		String DB=args[0];
		String path=args[1];
		int size=Integer.parseInt(args[2]);
		File csv = new File(path);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(csv));
        br.readLine();
        int cluster_col;
        String splitby;
        if(DB.equals("CORA")){
        	cluster_col=18;
        	splitby="\\|";
        }
        else{
        	cluster_col=1;
        	splitby=",";
        }
        for(int i=0;i<size;i++){
        	String line_str=br.readLine();
            String[] line=line_str.replace("\"", "").split(splitby);
            int cluster=Integer.valueOf(line[cluster_col]);
            int id=Integer.valueOf(line[0]);
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
        HashSet<HashSet<Integer>> originGlobalPair = CollectionOperator.pairGraph(originCluster);
        String file_name="GOT_"+DB+"_"+size;
        File csv2 = new File("./"+file_name);
        FileWriter fw=new FileWriter(csv2, false);
        BufferedWriter bw = new BufferedWriter(fw);
        for(HashSet<Integer> pair:originGlobalPair) {
        	StringBuffer sb=new StringBuffer();
            for (int entry:pair) {  
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
