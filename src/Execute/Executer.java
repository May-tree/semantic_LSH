package Execute;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Executer {
	public static int k;
	public static int l;
	public static int n;
	public static String DB;
	public static String path;
	public static String tree_path;
	public static int semantic_Switch; //0:NO_SEM 1:OR 2:AND 3:MAX
    public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, IOException{
    	DB=args[0];
    	path=args[1];
    	tree_path=args[2];
    	k=Integer.parseInt(args[3]);
    	l=Integer.parseInt(args[4]);
    	semantic_Switch=Integer.parseInt(args[5]);
    	n=Integer.parseInt(args[6]);
    	int inputsize=Integer.parseInt(args[7]);
    	infoRecorder ir=new infoRecorder(DB,path,tree_path,k,l,semantic_Switch,n,inputsize);
    	ir.settingDisplay();
    	ir.timeRecord();
    }
    
    
}
