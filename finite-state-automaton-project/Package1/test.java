package Package1;
import java.io.IOException;
import java.util.HashSet;

public class test {
	
	static FichierAutomate fa= new FichierAutomate();
	static Automate automate1= new Automate();	
	static Automate automate2= new Automate();	
	static Automate Test= new Automate();	

	
	public static void main(String[] args) throws IOException  {

		Demo(2,5);
	}
	
	public static void Demo (int representation, int operation) throws IOException
	{
		/*  Variables */
		String regex="((ab)|c*)";
		
		int nb_etats = 3;
		Automate automate = new Automate ();
		HashSet<Character> alphabet = new HashSet();
	    alphabet.add('a');  
	    alphabet.add('b');	
	    alphabet.add('c');	 
	    alphabet.add('&');	

		switch(representation) {
		case 1: // automate1.txt
	    automate = fa.Fichier_To_Automate("automate1.txt");
	    break;
	    
		case 2: // Génération aléatoire
		automate=automate.genererAutomate(nb_etats, alphabet);
		break;
		
		case 3: // Regex
		automate = automate.regexAutomate(regex);	
		break;
		}
		
		fa.Automate_To_DOT(automate,  "automateAvant.dot");
		
		// Opérations sur l'automate
		switch(operation)
		{
		case 1: // Complet
		fa.Automate_To_DOT(Test.Complet(automate), "automateApres.dot");
		
		break;
		
		case 2: // Complémentaire
		fa.Automate_To_DOT(Test.complementaire(automate), "automateApres.dot");
		break;
	
		case 3: // Suppression des epsilons productions
		fa.Automate_To_DOT(Test.supprEpsilonTransitions(automate), "automateApres.dot");	
		break;
		
		case 4: // Miroir
		automate=Test.miroir(automate);
		fa.Automate_To_DOT(automate.determiniser(automate), "automateApres.dot");
		System.out.println("Mot reconnu : "+automate.determiniser(automate).AccepteMot("cba"));
		
		break;
		
		case 5: // Déterminisation
		fa.Automate_To_DOT(Test.determiniser(automate), "automateApres.dot");
		break;
		
		case 6: //Minimisation
		fa.Automate_To_DOT(Test.minimiser(automate), "automateApres.dot");
		break;
		
		}
		
		
	}
	


}
