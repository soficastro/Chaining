import java.util.*;

class BC {
    
    public ArrayList<String> facts;
    public ArrayList<String> rules;
    public Stack<String> fact_stack;
    public String question; //what we're looking for
    
    public BC(String r, String q) {
        facts = new ArrayList<String>();
        rules = new ArrayList<String>();
        fact_stack = new Stack<String>();
        separate(r, q);
        question = q;
  
    }
    
    public void separate(String r, String question) {
        
        fact_stack.push(question);
        
        String[] parts = r.split(";");
        
    	for ( int i = 0; i < parts.length; i++ ) {

    		if (!parts[i].contains("->")) //it's not a rule, but a fact
    			facts.add(parts[i]);
    		else {                        // it's a rule
    			rules.add(parts[i]);
    		}
    	}
    }
    
    public boolean algorithm() {
        while(!fact_stack.isEmpty()) {
            String top = fact_stack.pop();
            //is top a fact?
            if(!facts.contains(top)) {
                //top is not a fact. is top a conclusion?
                ArrayList<String> premises = new ArrayList<String>();
                for( int i = 0; i < rules.size(); i++ ) {
                    if(isConclusion(rules.get(i), top))
                    {
                        //not necessary
                        ArrayList<String> allPremises = new ArrayList<String>();
                        allPremises = getAllPremises(rules.get(i));
                        if(checkTop(top, allPremises))
                            facts.add(top);
                        
                        
                        //top is a conclusion to this rule, get the premises
                        premises = getPremises(rules.get(i));
                        
                    }
                }
                if (premises.size() == 0)
                    return false; //no new premises were generated, bad
                else {
					for( int i = 0; i < premises.size(); i++ ) {
							if (!facts.contains(premises.get(i)))
									fact_stack.push(premises.get(i));
						}
                    
                }
            } 
        }
        
        return true;
        
    }
    
    public boolean isConclusion(String rule, String q) {
        String conclusion = rule.split("->")[1];
        return (conclusion.equals(q));
    }
    
    public ArrayList<String> getPremises(String rule) {
        
    	ArrayList<String> premises = new ArrayList<String>();

    	String premise = rule.split("->")[0];
    	
    	String[] parts = premise.split("&");
    	
    	for( int i = 0; i < parts.length; i++ ) {
    			if (!fact_stack.contains(parts[i]))
    					premises.add(parts[i]);
    	}
    	return premises; //that aren't already in the stack
    }
    
    public ArrayList<String> getAllPremises(String rule) {
        
    	ArrayList<String> premises = new ArrayList<String>();

    	String premise = rule.split("->")[0];
    	
    	String[] parts = premise.split("&");
    	
    	for( int i = 0; i < parts.length; i++ ) {
    			premises.add(parts[i]);
    	}
    	
    	 System.out.println(premises);
    	return premises;
    }
    
    public boolean checkTop(String c, ArrayList<String> premises) {
        
        boolean fact = false;
        
        for (int i = 0; i < premises.size(); i++ ) {
            if(!facts.contains(premises.get(i)))
                fact = false;
            else fact = true;
        }
        
        return fact;
    }
    
}

public class Main {
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
		
	    BC bc = new BC(rules, question);
		
		if(bc.algorithm())
		    System.out.println(question + " is true");
		else
		    System.out.println(question + " is false");
	}
}
