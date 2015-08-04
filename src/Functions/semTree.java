package Functions;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class semTree {
	public int idx;
	public List<String> leaves;
	public HashMap<String,HashSet<String>> leaveMap;
	public class Node{
		String name;
		int kids;
		List<Node> children;
		public Node(String name,int kids){
			this.name=name;
			this.kids=kids;
			children=new ArrayList<Node>();
		}
	}
	private Node root;
	public semTree(String path) throws FileNotFoundException{
		File file = new File(path);
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(file);  
		scan.useDelimiter("\\Z");  
		String content = scan.next(); 
		String[] parse=content.split(" ");
		root=new Node(parse[0],Integer.parseInt(parse[1]));
		leaves=new ArrayList<String>();
		idx=2;
		leaveMap=new HashMap<String,HashSet<String>>();
		DFS(root,parse,new HashSet<String>());
	}
	public void DFS(Node ptr,String[] parse){
		if(ptr.kids==0){
			leaves.add(ptr.name);
			return;
		}
		for(int time=0;time<ptr.kids;time++){
			Node child=new Node(parse[idx],Integer.parseInt(parse[idx+1]));
			idx+=2;
			ptr.children.add(child);
			DFS(child,parse);
		}
		return;
	}
	public HashSet<String> DFS(Node ptr,String[] parse,HashSet<String> leaveSet){
		if(ptr.kids==0){
			HashSet<String> lev=new HashSet<String>();
			lev.add(ptr.name);
			leaveMap.put(ptr.name, lev);
			leaves.add(ptr.name);
			leaveSet.add(ptr.name);
			return new HashSet<String>(leaveSet);
		}
		for(int time=0;time<ptr.kids;time++){
			Node child=new Node(parse[idx],Integer.parseInt(parse[idx+1]));
			idx+=2;
			ptr.children.add(child);
			if(!leaveMap.containsKey(ptr.name)){
				leaveMap.put(ptr.name,new HashSet<String>());
			}
			leaveMap.get(ptr.name).addAll(DFS(child,parse,leaveMap.get(ptr.name)));
		}
		return leaveMap.get(ptr.name);
	}
	public ArrayList<Integer> getSigs(HashSet<String> leaveSet){
		HashSet<String> bigSet=new HashSet<String>();
		for(String leave:leaveSet){
			bigSet.addAll(leaveMap.get(leave));
		}
		ArrayList<Integer> sigs=new ArrayList<Integer>();
		for(int i=0;i<leaves.size();i++){
			if(bigSet.contains(leaves.get(i))){
				sigs.add(1);
			}
			else{
				sigs.add(0);
			}
		}
		return sigs;
	}
	public static void main(String[] args) throws FileNotFoundException{
		semTree st=new semTree("tree_CORA2.txt");
		for(String node:st.leaveMap.keySet()){
			HashSet<String> temp=st.leaveMap.get(node);
			System.out.println(node+": ");
			for(String t:temp){
				System.out.print(t+" ");
			}
			System.out.println();
		}
	}
}
