package Package1;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Etat 

{	
	 /** ATTRIBUTS **/
	 boolean initial;						// Indique si état initial
	 boolean terminal;						// Indique si état terminal
	 int id;							    // identifiant de l'état
	 String id_deterministe;				// identifiant après déterminisation (ensemble d'états)
	 HashMap<Character, Etats> transitions;	// HashMap qui lie un Character (key) et un ensemble (Set) d'états (value)
	 
	 
	 /** GETTERS **/
	 public boolean estInitial() {	return initial;	 }
	 public boolean estTerminal() {	return terminal; }
	 public int idEtat() {return id;}
	 public HashMap<Character, Etats> TransitionsEtat( ) { return transitions; }
	 public String getIdDeterministe() { return this.id_deterministe;}
	 
	 /** SETTERS **/
	 public void mettreInitial (boolean initial)  { this.initial = initial; }
     public void mettreTerminal(boolean terminal) { this.terminal = terminal; }
     public void mettreId (int id) {this.id=id;}
     public void setIdDeterministe(String ed) {this.id_deterministe = ed;}
     

     /** CONSTRUCTEURS **/
 	public Etat() 
 	{	
 		this.transitions = new HashMap<Character, Etats>();	
 	}

 	public Etat(int id) 
 	{
 		this.transitions = new HashMap<Character, Etats>();
 		this.id = id;
 		this.terminal = false;
 		this.initial = false; 
 	}

 	public Etat(int id, boolean initial, boolean terminal) 
 	{
 		this.transitions = new HashMap<Character, Etats>();
 		this.initial = initial;
 		this.terminal = terminal;
 		this.id = id; 
 	}

	public Etat(int id, boolean initial, boolean terminal, String id_det) {
		this.transitions = new HashMap<Character, Etats>();
		this.initial = initial;
		this.terminal = terminal;
		this.id = id; 
		this.id_deterministe = id_det;
	}
 	
	public Etat(int id, boolean initial, boolean terminal, HashMap<Character, Etats> transitions) {
		this.transitions = transitions;
		this.initial = initial;
		this.terminal = terminal;
		this.id = id; 
	}

	public Etat(Etat e) {
		this.transitions = (HashMap<Character, Etats>)e.TransitionsEtat().clone();
		this.initial = e.estInitial();
		this.terminal = e.estTerminal();
		this.id = e.idEtat(); 
		this.id_deterministe = e.getIdDeterministe();
	}
	
	/** METHODES**/
	
	//Les successeurs de l'état par c lettre de la transition
	//retourne un ensemble d'etats des etats successeurs	
	public Etats SuccEtat(char c)
	{
	if(transitions.containsKey(c))  
		{ return transitions.get(c); }
	else
	    { return new Etats(); }
	}
 	
	//Tous les successeurs de cet état
	public Etats SuccEtat()
	{
		if(!transitions.isEmpty())
		{
			Etats tmp = new Etats();
			for(Etats etats: transitions.values())
			{
				tmp.addAll(etats);
			}
			return tmp;
		}
		else
		{   return new Etats(); }

	}

	
    // Ajouter une transition à un état
	// c lettre de la transition
	// e etat atteint par la transition	 
	public void ajouterTransition(Character c, Etat e)
	{
		if(this.transitions.containsKey(c))
		{
			Etats tmp = this.transitions.get(c);
			tmp.add(e);
		}
		else
		{
			Etats tmp = new Etats();
			tmp.add(e);
			this.transitions.put(c, tmp);
		}
	}
	
	
	//Supprimme la transition de l'état
	public void supprimmerTransition(char c)
	{
		if(this.transitions.containsKey(c))
		{
			this.transitions.remove(c);
		}
		
	}


	//Recuperer l'alphabet de l'etat, retourne l'ensemble des lettres des transitions
	public Set<Character> alphabetEtat()
	{	if(this.transitions!=null)
		{ return transitions.keySet();}
		else
		{return (new HashSet<Character>());}

	}
	
	//Test Si la transition e1-- (lettre)--->e2 existe
	public boolean TransitionExiste(Etat e1, Etat e2, Character lettre)
	{
		Etats etats = new Etats();
		etats = e1.transitions.get(lettre);
		if(etats.getEtat(e2.idEtat()) == null)
		{return false;}
		else
		{return true;}
		
	}


	//Redéfinition de la méthode equals 
	public boolean egale(Object objet) 
	{
		if (objet == null || this.getClass() != objet.getClass()) 
		{ return false; } 
		
		else 
		{
			final Etat etat = (Etat) objet;
			return (id == etat.id && this.terminal==etat.terminal && this.initial==etat.initial);
		}
	}

	
	//Redéfiniton de la méthode toString
	//On affiche l'id, initial et terminal
	//Then, pour chauque lettre, on affiche tout les états auquels elle mene
	public String toString()
	{
		String res = "___________________________________________\n";
		res += "id : "+id;
		res += "   initial : "+initial;
		res += "   terminal : "+terminal+"\n";
		
		for(Character c : this.alphabetEtat())
		{
			res += "Pour la lettre "+c+" les suivants sont : /";
			
			for(Etat etat : this.SuccEtat(c))
			{
				res +=etat.id+" /";
			}
			res += "\n";
		}
		return res;
	}
	
	public boolean accepte(String s, int i)
	{
		if(i == s.length()) { return this.estTerminal(); }
		if(this.SuccEtat(s.charAt(i)) != null)
		{
			return this.SuccEtat(s.charAt(i)).accepte(s, ++i);
		}
		return false;
	}
	
}