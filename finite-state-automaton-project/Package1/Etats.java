package Package1;
import java.util.HashSet;
import java.util.Set;

public class Etats extends HashSet<Etat> 

{
	
/*** Méthodes ***/
	
	// Retourne l'état dont l'id est passé en parametre, null sinon	 
	public Etat getEtat(int id)
	{
		for(Etat etat : this)
		{
		  if(etat.idEtat() == id)	
		     { return etat; }
		}
		return null;
	}
	
	// Test si l'ensemble d'etat contient un etat terminal
	public boolean contientTerminal()
	{
		for(Etat etat : this)
		{
		 if(etat.estTerminal()) { return true; }
		}
		return false;
	}
	
	
	// Test si l'ensemble d'etat contient un etat initial
	public boolean contientInitial()
	{
		for(Etat etat : this)
		{
		 if(etat.estInitial()) { return true; }
		}
		return false;
	}
	
	// Test si l'ensemble d'état contient l'état dans l'id est passé en argument
	public boolean contientEtatId(int id)
	{
		for (Etat e : this) if (e.idEtat() == id) return true;
		return false;
	}
	
	// Retourne le nombre d'états Initiaux
	public int NombreInitiaux()
	{
		int nombre=0;
		for(Etat etat : this)
		{
		 if(etat.estInitial()) { nombre++; }
		}
		return nombre;
	}
	
	// Retourne le nombre d'états Terminaux
	public int NombreTerminaux()
	{
		int nombre=0;
		for(Etat etat : this)
		{
		 if(etat.estTerminal()) { nombre++; }
		}
		return nombre;
	}
	
	// Recupère l'alphabet de l'ensemble des états
	public  Set<Character> alphabetEnsemble()
	{
		Set<Character> alphabet = new HashSet<Character>();
		for(Etat etat : this)
		{
			alphabet.addAll( etat.alphabetEtat());		
		}
		return alphabet;
	}
	
	// Retourne l'ensemble des etats atteignable par la lettre c
	public Etats SuccEtats(char c)
	{
		Etats a = new Etats();
		for(Etat etat : this)
		{
			Etats tmp = etat.SuccEtat(c);
			a.addAll(tmp);
		}
		return a;
	}

	// Retourne l'ensemble des etats atteignables 
	public Etats SuccEtats()
	{
		Etats a = new Etats();
		for(Etat etat : this)
		{
			Etats sorties = etat.SuccEtat();
			a.addAll(sorties);
		}
		return a;
	}
	
	// Redefinition de toString()
	public String toString()
	{
		String resultat = " ";
		resultat += "Nombre d'tat dans cet ensemble : "+ this.size() + " Etats\n";
		for(Etat etat : this)
		{
			resultat += "\n"+etat.toString();
		}
		return resultat;
	}



	/**
	 * Test si l'ensemble d'etat accepte le sous mot demarrant a la position i
	 * @param s mot a tester
	 * @param i position de depart
	 * @return resultat du test
	 */
	public boolean accepte(String s, int i)
	{
		if(i == s.length()) { return this.contientTerminal(); }
		if(this.SuccEtats(s.charAt(i)) != null)
		{
			return this.SuccEtats(s.charAt(i)).accepte(s, ++i);
		}
		return false;
	}


	// Test si deux ensembles d'etats ont les memes etats
	// ensemble_a_comparer l'ensemble d'etat auquel comparer notre ensemble this
	public boolean egale(Etats ensemble_a_comparer)
	{
		for(Etat etat : this)
		{
			if(ensemble_a_comparer.getEtat(etat.idEtat()) == null) { return false; }
		}
		
		for(Etat etat : ensemble_a_comparer)
		{
			if(this.getEtat(etat.idEtat()) == null) { return false; }
		}
		
		return true;
	}
		
	public String listeId() {
		String res = "";
		for (Etat e : this) {
			res += String.valueOf(e.idEtat()) + " ,";
		}
		if (res.length() > 2 ) res = res.substring(0, res.length() - 2);
		return res;
	}



}
