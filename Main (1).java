import java.util.*;

class FC {
    public ArrayList<String> rules;
    public ArrayList<String> facts;
    
    public FC(String r) {
        facts = new ArrayList<String>();
        rules = new ArrayList<String>();
        separate(r);
    }
    
    public void separate(String r) {
	    
        String[] parts = r.split(";");
        
    	for ( int i = 0; i < parts.length; i++ ) {

    		if (!parts[i].contains("->")) //it's not a rule, but a fact
    			facts.add(parts[i]);
    		else {                        // it's a rule
    			rules.add(parts[i]);
    		}
    	}
    }
    
    public boolean algorithm(String q) {
        
        while(true) {
            int factSize = facts.size();
            
            for ( int i = 0; i < rules.size(); i++ ) {
                boolean flag = true;
                ArrayList<String> premises = getAllPremises(rules.get(i));
                for ( int j = 0; j < premises.size(); j++ ) {
                    if( !facts.contains(premises.get(j) )) {
                        flag = false;
                        break;
                    }
                }
                if (flag)
                    facts.add(getConclusion(rules.get(i)));
            }
            
            if (facts.contains(q)) return true;
            
            int newFactSize = facts.size();
            
            if (factSize == newFactSize)
                return false;
            
        }
    }
    
    public boolean verifyRule(String rule) {
        
        boolean isSubset = true;
        
        ArrayList<String> premises = getAllPremises(rule);
        
        for ( int j = 0; j < premises.size(); j++ ) {
            if ( !facts.contains(premises.get(j)) ) 
                isSubset = false;
        }
        return isSubset;
    }
    
    public String getConclusion(String rule) {
        String conclusion = rule.split("->")[1];
        return conclusion;
    }
    
    public ArrayList<String> getAllPremises(String rule) {
        
    	ArrayList<String> premises = new ArrayList<String>();

    	String premise = rule.split("->")[0];
    	
    	String[] parts = premise.split("&");
    	
    	for( int i = 0; i < parts.length; i++ ) {
		premises.add(parts[i]);
    	}
	return premises;
    }
}

public class Main
{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
	    String rules, question;
	    
		System.out.println("Insert rules and facts");
		System.out.println("Formatation example: a&b->c;d&e->b;f&g->a;x->z;f->g;d->e;d;f");
		rules = input.nextLine();
		
		System.out.println("What are you looking for?");
		System.out.println("Formatation example: c");
		question = input.nextLine();
		
		//rules = "a&b->c;d&e->b;f&g->a;x->z;f->g;d->e;d;f";
		//question = "c";
		
	    FC fc = new FC(rules);
		
		if(fc.algorithm(question))
		    System.out.println(question + " is true");
		else
		    System.out.println(question + " is false");
	}
}
