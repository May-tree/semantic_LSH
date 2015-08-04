package Functions;

import java.util.Arrays;
import java.util.HashSet;

public class leaveMap {
	public static HashSet<String> getCoraLeaves(String[] line,int n){
		switch(n){
		case 1:return getCoraLeaves1(line);
		case 2:return getCoraLeaves2(line);
		case 3:return getCoraLeaves3(line);
		case 4:return getCoraLeaves4(line);
		default:return getCoraLeaves0(line);
		}
	}
	public static HashSet<String> getCoraLeaves4(String[] line){
        HashSet<String> leaves=new HashSet<String>();
        String tech=line[0];
        String type=line[1];
        String note=line[2];
        String journal=line[3];
        String institution=line[4];
        String booktitle=line[5];

        if(!booktitle.equals("")&&(booktitle.contains("Proceedings")||booktitle.contains("Conference"))){
            leaves.add("CON");
            return leaves;
        }
        if(!type.equals("") && type.contains("thesis")){   
            leaves.add("TH");
            return leaves;
        }
        if(!type.equals("") && type.contains("Technical")){
            leaves.add("RP");
            return leaves;
        }
        if(!note.equals("") && note.contains("ISBN")){
            leaves.add("BK");
            return leaves;
        }
        
        if(!journal.equals("")){
            if(!institution.equals("")){
                leaves.add("CON");
                leaves.add("NPR");
                return leaves;
            }
            else{
                if(!booktitle.equals("")){
//                    leaves.add("JN");
                	leaves.add("PR");
                    leaves.add("CON");
                    return leaves;  
                }
                else{
//                    leaves.add("JN");
                	leaves.add("PR");
                    return leaves;
                }
            }
        }
        else{
            if(!tech.equals("")){
                if(!booktitle.equals("")){
                    leaves.add("RP");
                    leaves.add("CON");
                    return leaves;
                }
                else{
                    leaves.add("RP");
                    return leaves;
                }
            }
            else{
                if(!booktitle.equals("")){
                    if(!institution.equals("")){
                        leaves.add("CON");
                        leaves.add("NPR");
                        return leaves;
                    }
                    else{
                        leaves.add("CON");
                        return leaves;
                    } 
                }
                else{
                    if(!institution.equals("")){
                        leaves.add("NPR");
                        return leaves;
                    }
                    else{
                        leaves.add("PUB");
                        return leaves;
                    }
                }
            }
        }
    }
	public static HashSet<String> getCoraLeaves3(String[] line){
        HashSet<String> leaves=new HashSet<String>();
        String tech=line[0];
        String type=line[1];
        String note=line[2];
        String journal=line[3];
        String institution=line[4];
        String booktitle=line[5];

        if(!booktitle.equals("")&&(booktitle.contains("Proceedings")||booktitle.contains("Conference"))){
            leaves.add("CON");
            return leaves;
        }
        if(!type.equals("") && type.contains("thesis")){   
            leaves.add("TH");
            return leaves;
        }
        if(!type.equals("") && type.contains("Technical")){
            leaves.add("RP");
            return leaves;
        }
        if(!note.equals("") && note.contains("ISBN")){
//            leaves.add("BK");
            leaves.add("PR");
            return leaves;
        }
        
        if(!journal.equals("")){
            if(!institution.equals("")){
                leaves.add("CON");
                leaves.add("NPR");
                return leaves;
            }
            else{
                if(!booktitle.equals("")){
                    leaves.add("JN");
                    leaves.add("CON");
                    return leaves;  
                }
                else{
                    leaves.add("JN");
                    return leaves;
                }
            }
        }
        else{
            if(!tech.equals("")){
                if(!booktitle.equals("")){
                    leaves.add("RP");
                    leaves.add("CON");
                    return leaves;
                }
                else{
                    leaves.add("RP");
                    return leaves;
                }
            }
            else{
                if(!booktitle.equals("")){
                    if(!institution.equals("")){
                        leaves.add("CON");
                        leaves.add("NPR");
                        return leaves;
                    }
                    else{
                        leaves.add("CON");
                        return leaves;
                    } 
                }
                else{
                    if(!institution.equals("")){
                        leaves.add("NPR");
                        return leaves;
                    }
                    else{
                        leaves.add("PUB");
                        return leaves;
                    }
                }
            }
        }
    }
	public static HashSet<String> getCoraLeaves2(String[] line){
        HashSet<String> leaves=new HashSet<String>();
        String tech=line[0];
        String type=line[1];
        String note=line[2];
        String journal=line[3];
        String institution=line[4];
        String booktitle=line[5];

        if(!booktitle.equals("")&&(booktitle.contains("Proceedings")||booktitle.contains("Conference"))){
            leaves.add("CON");
            return leaves;
        }
        if(!type.equals("") && type.contains("thesis")){   
            leaves.add("TH");
            return leaves;
        }
        if(!type.equals("") && type.contains("Technical")){
            leaves.add("RP");
            return leaves;
        }
        if(!note.equals("") && note.contains("ISBN")){
            leaves.add("BK");
            return leaves;
        }
        
        if(!journal.equals("")){
            if(!institution.equals("")){
                leaves.add("CON");
                leaves.add("root");
//                leaves.add("NPR");
                return leaves;
            }
            else{
                if(!booktitle.equals("")){
                    leaves.add("JN");
                    leaves.add("CON");
                    return leaves;  
                }
                else{
                    leaves.add("JN");
                    return leaves;
                }
            }
        }
        else{
            if(!tech.equals("")){
                if(!booktitle.equals("")){
                    leaves.add("RP");
                    leaves.add("CON");
                    return leaves;
                }
                else{
                    leaves.add("RP");
                    return leaves;
                }
            }
            else{
                if(!booktitle.equals("")){
                    if(!institution.equals("")){
                        leaves.add("CON");
                        leaves.add("root");
//                        leaves.add("NPR");
                        return leaves;
                    }
                    else{
                        leaves.add("CON");
                        return leaves;
                    } 
                }
                else{
                    if(!institution.equals("")){
//                        leaves.add("NPR");
                    	leaves.add("root");
                        return leaves;
                    }
                    else{
//                        leaves.add("PUB");
                    	leaves.add("root");
                        return leaves;
                    }
                }
            }
        }
    }
	public static HashSet<String> getCoraLeaves1(String[] line){
        HashSet<String> leaves=new HashSet<String>();
        String tech=line[0];
        String type=line[1];
        String note=line[2];
        String journal=line[3];
        String institution=line[4];
        String booktitle=line[5];

        if(!booktitle.equals("")&&(booktitle.contains("Proceedings")||booktitle.contains("Conference"))){
            leaves.add("CON");
            return leaves;
        }
        if(!type.equals("") && type.contains("thesis")){   
            leaves.add("TH");
            return leaves;
        }
        if(!type.equals("") && type.contains("Technical")){
            leaves.add("RP");
            return leaves;
        }
        if(!note.equals("") && note.contains("ISBN")){
            leaves.add("BK");
            return leaves;
        }
        
        if(!journal.equals("")){
            if(!institution.equals("")){
                leaves.add("CON");          
//                leaves.add("NPR");
                leaves.add("PUB");
                return leaves;
            }
            else{
                if(!booktitle.equals("")){
                    leaves.add("JN");
                    leaves.add("CON");
                    return leaves;  
                }
                else{
                    leaves.add("JN");
                    return leaves;
                }
            }
        }
        else{
            if(!tech.equals("")){
                if(!booktitle.equals("")){
                    leaves.add("RP");
                    leaves.add("CON");
                    return leaves;
                }
                else{
                    leaves.add("RP");
                    return leaves;
                }
            }
            else{
                if(!booktitle.equals("")){
                    if(!institution.equals("")){
                        leaves.add("CON");
                        leaves.add("PUB");
//                        leaves.add("NPR");
                        return leaves;
                    }
                    else{
                        leaves.add("CON");
                        return leaves;
                    } 
                }
                else{
                    if(!institution.equals("")){
//                        leaves.add("NPR");
                        leaves.add("PUB");
                        return leaves;
                    }
                    else{
                        leaves.add("PUB");
                        return leaves;
                    }
                }
            }
        }
    }
	public static HashSet<String> getCoraLeaves0(String[] line){
        HashSet<String> leaves=new HashSet<String>();
        String tech=line[0];
        String type=line[1];
        String note=line[2];
        String journal=line[3];
        String institution=line[4];
        String booktitle=line[5];

        if(!booktitle.equals("")&&(booktitle.contains("Proceedings")||booktitle.contains("Conference"))){
            leaves.add("CON");
            return leaves;
        }
        if(!type.equals("") && type.contains("thesis")){   
            leaves.add("TH");
            return leaves;
        }
        if(!type.equals("") && type.contains("Technical")){
            leaves.add("RP");
            return leaves;
        }
        if(!note.equals("") && note.contains("ISBN")){
            leaves.add("BK");
            return leaves;
        }
        
        if(!journal.equals("")){
            if(!institution.equals("")){
                leaves.add("CON");
                leaves.add("NPR");
                return leaves;
            }
            else{
                if(!booktitle.equals("")){
                    leaves.add("JN");
                    leaves.add("CON");
                    return leaves;  
                }
                else{
                    leaves.add("JN");
                    return leaves;
                }
            }
        }
        else{
            if(!tech.equals("")){
                if(!booktitle.equals("")){
                    leaves.add("RP");
                    leaves.add("CON");
                    return leaves;
                }
                else{
                    leaves.add("RP");
                    return leaves;
                }
            }
            else{
                if(!booktitle.equals("")){
                    if(!institution.equals("")){
                        leaves.add("CON");
                        leaves.add("NPR");
                        return leaves;
                    }
                    else{
                        leaves.add("CON");
                        return leaves;
                    } 
                }
                else{
                    if(!institution.equals("")){
                        leaves.add("NPR");
                        return leaves;
                    }
                    else{
                        leaves.add("PUB");
                        return leaves;
                    }
                }
            }
        }
    }
	public static HashSet<String> getNCVoterLeaves(String[] props,int n){
		HashSet<Character> genders = new HashSet<Character>(Arrays.asList('f','m'));
		HashSet<Character> races = new HashSet<Character>(Arrays.asList('w', 'b','a','i','m','o'));
        HashSet<String> leaves=new HashSet<String>();
        String gender=props[0];
        String race=props[1];
        if(gender.equals("u")){
        	if(race.equals("u")){
        		for(char g:genders){
        			StringBuffer sb=new StringBuffer();
        			sb.append(g);
        			for(char r:races){
        				sb.append(r);
        				leaves.add(sb.toString());
        				sb.deleteCharAt(sb.length()-1);
        			}
        			
        		}
        		return leaves;
        	}
        	else{
        		for(char g:genders){
        			StringBuffer sb=new StringBuffer();
        			sb.append(g);
        			sb.append(race);
        			leaves.add(sb.toString());
        		}
        		return leaves;
        	}
        }
        else{
        	StringBuffer sb=new StringBuffer();
			sb.append(gender);
        	if(race.equals("u")){
    			for(char r:races){
    				sb.append(r);
    				leaves.add(sb.toString());
    				sb.deleteCharAt(sb.length()-1);
    			}
    			return leaves;
        	}
        	else{
        		sb.append(race);
    			leaves.add(sb.toString());
        		return leaves;
        	}
        }
    }
	public static void main(String[] args){
//		HashSet<String> leaves=getNCVoterLeaves("f,w");
//		for(String leave:leaves){
//			System.out.print(leave);
//			System.out.print(" ");
//		}
	}
}
