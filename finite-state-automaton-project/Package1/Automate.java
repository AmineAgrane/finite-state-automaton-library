package Package1;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Set;
import java.util.Map;
import java.util.Random;
import java.io.IOException;


public class Automate extends Etats 

{

	/** CONSTANTES **/
	public static final char EPSILON = '&';
	public static final Character UNION = '|';
	public static final Character CONCAT = '.';
	public static final Character ETOILE = '*';
	public static final Character FIN = '$';
	public static final ArrayList<Character> METACARACTERES = new ArrayList<Character>() {
		{
			add(UNION);
			add(CONCAT);
			add(ETOILE);
			add(FIN);
		}
	};

	

    /** VARIABLES **/
    private Etats initiaux;							
    private Etats terminaux;
	private ArrayList<Character> alphabet;	
	private ArrayList<Etat> etats;		
    
   /** CONSTRUCTEURS **/
	public Automate()
	 {
        initiaux = new Etats();
        terminaux = new Etats();
		alphabet = new ArrayList<Character>();
		etats = new ArrayList<Etat>();
	}
	
	public Automate(Etats Ensemble_Etats)
	 {
       initiaux = new Etats();
       terminaux = new Etats();
	   alphabet = new ArrayList<Character>();
	   etats = new ArrayList<Etat>();
	   for(Etat e : Ensemble_Etats)
	   {
		   	this.AjouterEtat(e);
		   	etats.add(e);
	   }
	   
	 }
	
	public Automate(ArrayList<Etat> al)
	{
		this.initiaux = new Etats();
		this.terminaux = new Etats();
		this.alphabet = new ArrayList<Character>();
		this.etats = al;
		this.update();
	 }
	
    /** SETTERS **/
	
	public void setAlphabet(ArrayList<Character> alphabet) { this.alphabet = alphabet; }
	public void setEtats(ArrayList<Etat> etats) { this.etats = etats; }
	public void setInitiaux(Etats initiaux) { this.initiaux = initiaux; }
	public void setTerminaux(Etats terminals) { this.terminaux = terminals; }
	public void setInitiaux() 
	{
		this.initiaux= new Etats();
		for(Etat etat : this)
		{  if(etat.estInitial()) { this.initiaux.add(etat);  }  } 
	}
	public void setTerminaux() 
	{
		this.terminaux= new Etats();
		for(Etat etat : this)
		{   if(etat.estTerminal()) {this.terminaux.add(etat);}   } 
	}
	
    /** GETTERS **/
	public ArrayList<Character> getAlphabet() { return this.alphabet; }
	public ArrayList<Etat> getEtats() { return this.etats; }
	public Etats getInitiaux() { return initiaux; }
	public Etats getFinaux() { return terminaux; }


	
	
	/*** Méthodes ***/
	
	//Ajouter l'état e à l'automate
	void AjouterEtat(Etat e)
	{
		this.add(e);
		if(e.estInitial()) {this.initiaux.add(e);}
		if(e.estTerminal()) {this.terminaux.add(e);}

	}
	
	/******************************************************************************************************
	 * genererAutomate() -> Automate : automate alatoire
	 * @commentaire :
	 * 	Gnration d'un automate alatoire
	 * @params : 
	 * 	Automate : automate
	 * @return : 
	 * 	Automate : automate alatoire
	 ******************************************************************************************************/
    public Automate genererAutomate(int nb_etats, HashSet<Character> alphabet)
    {
       	int nb_transitions=0, etat_suivant=0;
    	Automate automate = new Automate();    Etats etats = new Etats();    Etat etat = new Etat();
    	Character lettre = ' ';
    	
    	//Création des N états
    	for(int i=1; i<=nb_etats;i++)
    	{
    		etat=new Etat(i,BooleenAleatoire(),BooleenAleatoire());
    		etats.add(etat);
    	}
    	
    	//Pour chaque état, on lui donne un nombre T de transition
    	for (Etat e: etats)
    	{
    		nb_transitions=NombreAleatoire(1,(nb_etats/2+1)); 
    		for(int i=1; i<=nb_transitions; i++)
    		{
    			etat_suivant=NombreAleatoire(1,etats.size());
    			lettre = CharacterAleatoire(alphabet);
    			e.ajouterTransition(lettre, etats.getEtat(etat_suivant));
    		
    		}	   		
    		
    	}
    	automate = new Automate(etats);
    	automate.update();
    	return automate;
    }
    
	/******************************************************************************************************
	 * estSansEpsilon() -> boolean : true si l'automate ne contient pas d'epsilon-transitions, false sinon
	 * @commentaire :
	 * 	Dtermine si l'automate contient des epsilon-transitions ou non.
	 * @params : 
	 * 	/
	 * @return : 
	 * 	boolean :
	 * 	- true si l'automate ne contient pas d'epsilon-transitions
	 * 	- false sinon
	 ******************************************************************************************************/
	public boolean estSansEpsilon()
    {
    	if (this.alphabet.contains(EPSILON)) return false; else return true;
    }
	
	/******************************************************************************************************
	 * estDeterminsite() -> boolean : true si dterministe, false sinon
	 * @commentaire :
	 * 	Dtermine si l'automate est dterministe ou non.
	 * @params : 
	 * 	/
	 * @return : 
	 * 	boolean :
	 * 	- true si dterministe
	 * 	- false sinon
	 ******************************************************************************************************/
	public boolean estDeterministe()
	{
		if(this.isEmpty() || this.size() == 1) { return true; }
		if(this.initiaux.size() > 1) { return false; }
		
		//On parcours tout les états de l'automate
		//Pour chaque état, on parcours toutes ses lettres
		//Si pour une lettre il existe plus d'un état correspondant => Non déterministe
		for(Etat etat : this)
		{
			for(Character a : etat.alphabetEtat())
			{
				if(etat.SuccEtat(a).size() > 1)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	
	/******************************************************************************************************
	 * estComplet() -> boolean : true si complet, false sinon
	 * @commentaire :
	 * 	Détermine si l'automate est complet ou non.
	 * @params : 
	 * 	/
	 * @return : 
	 * 	boolean :
	 * 	- true si complet
	 * 	- false sinon
	 ******************************************************************************************************/
	public boolean estComplet()
	{
		Set<Character> alphabet = this.alphabetEnsemble();
		if(this.isEmpty() || this.size() == 1) { return true; }
		
		//On parcours tout les états de l'automate
		//Pour chaque état, on parcours toutes ses lettres
		//Si à un état et une lettre donnés il n'y a pas d'associations => Incomplet
		for(Etat etat : this)
		{
			for(Character a : alphabet)
			{
				if(etat.transitions.containsKey(a)==false)
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	/******************************************************************************************************
	 * determiniser() -> Automate : automate dterminis
	 * @commentaire :
	 * 	Dterminise l'automate
	 * @params : 
	 * 	/
	 * @return : 
	 * 	Automate : automate dterminis
	 * @throws IOException 
	 ******************************************************************************************************/
	public Automate determiniser(Automate automate) throws IOException {
		automate.update();
		automate = supprEpsilonTransitions(automate);
		// RESULTAT
		Automate res = new Automate();
		
		// VARIABLES
		ArrayList<Etat> a_traiter = new ArrayList<Etat>();			// file
		Etat 	etat_courant = new Etat(),
				nouvel_etat = new Etat();
		
		ArrayList<Etats> tab_correspondant = new ArrayList<Etats>(); 	// tableau correspondant etat - ens. d'états
		Etats etat_correspondant = new Etats();							// assure la correspondance etat - ens. d'états
		
		
		// automate "tampon"
		Automate a_clone = new Automate();
		a_clone.setEtats(fullCloneAL(automate.getEtats()));
		a_clone.update();
		
		// INITIALISATION - initialisation de l'état initial
		for (Etat e : a_clone.getInitiaux()) etat_correspondant.add(e);
		tab_correspondant.add(etat_correspondant);
		a_traiter.add(new Etat(tab_correspondant.indexOf(etat_correspondant), false, etat_correspondant.contientTerminal(), etat_correspondant.listeId()));
		
		
		
		
		// DETERMINISATION
		while (!a_traiter.isEmpty()) {
			// init. var
			
			
			// Dépiler + ajout à l'automate déterministe
			etat_courant = a_traiter.get(0);
			a_traiter.remove(0);
			res.add(etat_courant);
			
			for (Character c : tab_correspondant.get(etat_courant.idEtat()).alphabetEnsemble()) {
				etat_correspondant = new Etats();
				for (Etat e : tab_correspondant.get(etat_courant.idEtat())) {
					if (e.TransitionsEtat().get(c) != null) {
						for (Etat e_t : e.TransitionsEtat().get(c)) etat_correspondant.add(e_t);
					}
				}
				if (!tab_correspondant.contains(etat_correspondant)) {		// i.e. apparaît pour la première fois
					tab_correspondant.add(etat_correspondant);
					a_traiter.add(new Etat(tab_correspondant.indexOf(etat_correspondant), false, etat_correspondant.contientTerminal(), etat_correspondant.listeId()));
				}
				// CREATION DE LA TRANSITION
				// Soit l'état extrémité existe déjà (if) i.e. il se trouve dans res
				// Soit il n'existe pas encore (else) et il se trouve dans a_traiter
				
				// id = tab_correspondant.indexOf(etat_correspondant)
				if (res.contientEtatId(tab_correspondant.indexOf(etat_correspondant))) {
					etat_courant.ajouterTransition(c, res.getEtat(tab_correspondant.indexOf(etat_correspondant)));
				} else {
					for (Etat e : a_traiter) {
						if (e.idEtat() == tab_correspondant.indexOf(etat_correspondant)) etat_courant.ajouterTransition(c, e);
					}
				}
			}	
		}
		
		
		// Définition de l'état initial - etat id 0 (premier état correspondant)
		if (!res.isEmpty()) res.getEtat(0).mettreInitial(true);
		
		res.update();
		return res;
	}	
	
	/******************************************************************************************************
	 * supprEpsilonTransitions() -> Automate : automate sans epsilon-transitions
	 * @commentaire :
	 * 	Retourne l'automate quivalent sans epsilon-transitions.
	 * 	On parcourt tous les tats de l'automate avec P ;
	 * 	pour chaque P, on recupre l'ensemble des ses successeurs avec '&' (EPSILON) qu'on met dans Q.
	 * 	On parcourt les successeurs Q par q	
	 * @params : 
	 * 	Automate : automate
	 * @return : 
	 * 	Automate : automate sans epsilon-transitions
	 * @throws IOException 
	 ******************************************************************************************************/
	public Automate supprEpsilonTransitions(Automate automate) throws IOException {
		ArrayList<Etat> initiaux = new ArrayList<Etat>(),
						a_visiter = new ArrayList<Etat>(),
						visite = new ArrayList<Etat>();;
		
		Etat etat_courant = new Etat();
		
		Automate 	a_inter0 = new Automate(fullCloneAL(automate.getEtats())),
					a_inter1 = new Automate();
		
		
		do {
			// INITIALISATION DE L'AUTOMATE CORRESPONDANT
			// /!\ 	(A) - epsilon -> (B)
			//		(A) <- epsilon - (B)
			// /!\ boucle epsilon-transition
			a_inter1 = new Automate();
			for (Etat e : a_inter0) {
				if (e.estInitial()) {
					if (e.estTerminal()) a_inter1.add(new Etat(e.idEtat(), true, true)); else a_inter1.add(new Etat(e.idEtat(), true, false));
				} else {
					if (e.estTerminal()) a_inter1.add(new Etat(e.idEtat(), false, true)); else a_inter1.add(new Etat(e.idEtat(), false, false));
				}
			}
		
			for (Etat e : a_inter0) {
				
				// ANTI BOUCLE
				visite.clear();
				visite.add(e);
				
				for (Map.Entry<Character, Etats> t : e.TransitionsEtat().entrySet()) {
					if (t.getKey() == EPSILON) {
						for (Etat e1 : t.getValue()) {	// PROFONDEUR 1						
							if (e1.estTerminal()) a_inter1.getEtat(e.idEtat()).mettreTerminal(true);
							if (!visite.contains(e1)) a_visiter.add(e1);
						}
						while (!a_visiter.isEmpty()) {
							// Depiler
							etat_courant = a_visiter.get(0);
							a_visiter.remove(0);
							visite.add(etat_courant);
							
							if (etat_courant.TransitionsEtat().containsKey(EPSILON)) {
								// parcours des epsilon-transitions
								for (Map.Entry<Character, Etats> t1 : etat_courant.TransitionsEtat().entrySet()) {
									if (t1.getKey() == EPSILON) {
										for (Etat e2 : t1.getValue()) {	// PROFONDEUR 2
											if (!visite.contains(e2) && !a_visiter.contains(e2)) a_visiter.add(e2);
										}
									} else {
										for (Etat e2 : t1.getValue()) a_inter1.getEtat(e.idEtat()).ajouterTransition(t1.getKey(), a_inter1.getEtat(e2.idEtat()));
									}
								}
							} else {
								if (!e.TransitionsEtat().get(EPSILON).contains(etat_courant)) a_inter1.getEtat(e.idEtat()).ajouterTransition(EPSILON, a_inter1.getEtat(etat_courant.idEtat()));
								for (Map.Entry<Character, Etats> t1 : etat_courant.TransitionsEtat().entrySet()) {
									for (Etat e2 : t1.getValue()) a_inter1.getEtat(e.idEtat()).ajouterTransition(t1.getKey(), a_inter1.getEtat(e2.idEtat()));
								}
							}
						}
						
						
					} else {
						for (Etat e1 : t.getValue()) a_inter1.getEtat(e.idEtat()).ajouterTransition(t.getKey(), a_inter1.getEtat(e1.idEtat()));
					}
				}
			}
			a_inter0 = a_inter1;
			a_inter0.update();
		} while (!a_inter0.estSansEpsilon());
		
		return a_inter0;
	}

	

	/******************************************************************************************************
	 * completion() -> Automate : automate complet
	 * @commentaire :
	 * 	Compltion d'un automate
	 * @params : 
	 * 	Automate : automate
	 * @return : 
	 * 	Automate : automate complet
	 ******************************************************************************************************/
	public Automate Complet (Automate automate)
	{
		//automate = automate.supprEpsilonTransitions(automate);
		
		if(automate.isEmpty()) {return new Automate();}
		if(automate.estComplet()) {return automate;}
		
		/* Création état poubelle */
		
		Etat poubelle = new Etat(MaxId(automate)+1,false,true);
		poubelle.id_deterministe="Trash";
		automate.AjouterEtat(poubelle);
		
		Set<Character> alphabet = automate.alphabetEnsemble();
		
		for(Etat etat : automate)
		{
			for(Character c : alphabet)
			{   
				if(etat.transitions.containsKey(c) == false) { etat.ajouterTransition(c, poubelle); }
			}
			
		}
			
			return automate;
	}
   

	/******************************************************************************************************
	 * complementaire() -> Automate : automate complmentaire
	 * @commentaire :
	 * 	Génère l'automate complmentaire de l'automate entré en argument ; algo. :
	 * 	1 - déterministe ;
	 * 	2 - complet ;
	 * 	3 - les états terminaux deviennent non terminaux, et inversement.
	 * @params : 
	 * 	Automate : automate
	 * @return : 
	 * 	Automate : automate complmentaire
	 * @throws IOException 
	 ******************************************************************************************************/
	public Automate complementaire(Automate automate) throws IOException
	{
		automate = automate.supprEpsilonTransitions(automate);
		automate = automate.determiniser(automate);
		automate = Complet(automate);		
		for(Etat etat : automate)
		{   if(etat.id!=automate.size() - 1)
			{ etat.mettreTerminal(!etat.estTerminal());}
		}
		automate.setTerminaux(automate);
		automate.setInitiaux(automate);

		return automate;
	}


	/******************************************************************************************************
	 * miroir() -> Automate : automate miroir
	 * @commentaire :
	 * 	Génère l'automate miroir de l'automate entr en argument ; algo. :
	 * 	1 - changer les états initiaux et les états finaux ;
	 * 	2 - inverser le sens des transitions ;
	 * 	3 - dterminiser l'automate produit (si ncessaire).
	 * @params : 
	 * 	Automate : automate
	 * @return : 
	 * 	Automate : automate miroir
	 ******************************************************************************************************/
	public Automate miroir(Automate automate)
	{
		
		Automate miroir = new Automate();
		for(Etat etat : automate)
		{
			miroir.AjouterEtat(new Etat(etat.idEtat(), etat.estTerminal(), etat.estInitial() )); 
		}

		for(Etat etat : automate)
		{
			for(Map.Entry<Character, Etats> transition : etat.TransitionsEtat().entrySet())
			{
				for(Etat successeur : transition.getValue())
				{
					miroir.getEtat(successeur.idEtat()).ajouterTransition(transition.getKey(), miroir.getEtat((etat.idEtat())));
				}
			}
		}
		
		//automate = automate.determiniser();
		miroir.update();
		return miroir;
	}
	
	public Automate minimiser(Automate automate) throws IOException {
		Automate minimal = new Automate();
		minimal = miroir(automate);
		minimal = determiniser(minimal);
		minimal = minimal.accessible(minimal);
		minimal = miroir(minimal);
		minimal = determiniser(minimal);
		
		return minimal;
	}
	
	public Automate accessible(Automate automate) {
		automate.update();
		Automate accessible = new Automate();
		
		
		HashSet<Etat> etats_accessibles = new HashSet<Etat>();
		ArrayList<Etat> a_visiter = new ArrayList<Etat>();		// file
		Etat etat_actuel = new Etat();
		
		// INIT
		accessible.setEtats(fullCloneAL(automate.getEtats()));
		accessible.update();
		
		// Détection des états accessibles
		for (Etat e_i : accessible.getInitiaux()) {
			if (!etats_accessibles.contains(e_i)) a_visiter.add(e_i);
			while (!a_visiter.isEmpty()) {
				// Depiler
				etat_actuel = a_visiter.get(0);
				a_visiter.remove(0);
				
				etats_accessibles.add(etat_actuel);
				
				for (Map.Entry<Character, Etats> t : etat_actuel.TransitionsEtat().entrySet()) {
					for (Etat e : t.getValue()) {
						if (!etats_accessibles.contains(e)) a_visiter.add(e);
					}
				}
			}
		}

		// Suppression des états inaccessibles
		for (Etat e : accessible) {
			if (etats_accessibles.contains(e)) {
				for (Map.Entry<Character, Etats> t : etat_actuel.TransitionsEtat().entrySet()) {
					for (Etat e_t : t.getValue()) {
						if (!etats_accessibles.contains(e_t)) t.getValue().remove(e_t);
					}
				}
			} else {
				accessible.remove(e);
			}
		}
		
		accessible.update();
		return accessible;
	}
	
	//Redefinition de toString()
	public String toString()
	{
		String resultat="";
		resultat += "Nombre d'état Initiaux dans l'automate : "+ this.NombreInitiaux() + " Etats\n";
		resultat += "Nombre d'état Terminaux dans l'automate : "+ this.NombreTerminaux() + " Etats\n";
			return (resultat+=super.toString());
	}

	//Retourne un booléen de façon aléatoire (true ou false)
	public boolean BooleenAleatoire()
	{
	    return Math.random() < 0.5;
	}

	
	//Retourne un Character aléatoire d'un Set donné
	public Character CharacterAleatoire(HashSet<Character> alphabet)
	{

		int i=0, item = new Random().nextInt(alphabet.size()); 
		for(Character c :alphabet)
		{
		    if (i == item)
		    { return c;}
		    i++;
		}
		return alphabet.iterator().next();
		
	}
	
	//Retourne un nombre entier aléatoire se trouvant entre min et max
	public int NombreAleatoire(int min, int max) 
	{

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	//Retourne l'id maximal de l'automate passé en argument
	public int MaxId(Automate automate)
	{
		int max = -1;
		for (Etat etat : automate)
		{
			if (etat.idEtat() > max)
			{
				max = etat.idEtat();
			}
			
		}
       return max;
	}
	
	// Test si le mot est accepte par l'automate
	public boolean AccepteMot(String s)
	{
		return initiaux.accepte(s, 0);
	}

	/******************************************************************************************************************
	 * regexAutomate()
	 * @commentaire :
	 * 	Conversion d'une expression régulière en automate fini non-déterministe
	 * 	Inspiré de l'algorithme de Thompson.
	 * @params :
	 * 	regex : Chaîne de caractère (expression régulière à transformer)
	 * @return :
	 * 	/
	 * @throws IOException 
	 ******************************************************************************************************************/
	public Automate regexAutomate(String regex) throws IOException {
		// VARIABLES
		char reg[] = regex.toCharArray();
		String regex_postfix = regexPostfix(regex);
		ArrayList<Character> char_regex = new ArrayList<Character>() {{ for (Character c : reg)  add(c); }};
		ArrayList<Character> 	alphabet_regex = getAlphabetRegex(regex), 
								regex_file = new ArrayList<Character>();
		HashMap<Character, Automate> automate_elementaire = new HashMap<Character, Automate>(); 
		Automate res_automate = new Automate(), a_tmp, op1, op2, automate_empile;
		
		HashSet<Character> metacaracteres = new HashSet<Character>(Arrays.asList(UNION, CONCAT, ETOILE));
		HashMap<String, ArrayList<Character>> correspondance;
		
		Stack<Automate> automate_pile = new Stack<Automate>();
		// AUTOMATES ELEMENTAIRES - Conversion de chaque lettre de l'alphabet(regex) en un automate chacun
		for (int i = 0; i < alphabet_regex.size(); i++) {
			a_tmp = new Automate();
			Etat e0 = new Etat(0, true, false, new HashMap<Character, Etats>());
			Etat e1 = new Etat(1, false, true, new HashMap<Character, Etats>());
			
			e0.ajouterTransition(alphabet_regex.get(i), e1);
			
			a_tmp.getEtats().add(e0);
			a_tmp.getEtats().add(e1);
			
			a_tmp.update();
			
			automate_elementaire.put(alphabet_regex.get(i), a_tmp);
		}
		
		
		for (Character c : regex_postfix.toCharArray()) regex_file.add(c);

		
		for (Character c : regex_file) {
			if (!METACARACTERES.contains(c)) { 		// c = LETTRE
				automate_pile.push(new Automate(fullCloneAL(automate_elementaire.get(c).getEtats())));
			} else {
				if (c == CONCAT) {
					op2 = automate_pile.pop();
					op1 = automate_pile.pop();
					FichierAutomate fa= new FichierAutomate(); fa.Automate_To_DOT(op2, "OP2.dot"); fa.Automate_To_DOT(op1, "OP1.dot");
					automate_empile = concatenerThompson(op1, op2);
					automate_pile.push(automate_empile);
				} else if (c == UNION) {
					op2 = automate_pile.pop();
					op1 = automate_pile.pop();
					automate_empile = unionThompson(op1, op2);
					automate_pile.push(automate_empile);
				} else if (c == ETOILE) {
					op1 = automate_pile.pop();
					automate_empile = etoileThompson(op1);
					automate_pile.push(automate_empile);
				}
			}
		}
		
		res_automate = automate_pile.pop();
		res_automate.update();
		//this.etats = automate.getEtats();
		//this.update();
		return res_automate;
	}

	/******************************************************************************************************************
	 * regexPostfix()
	 * @commentaire :
	 * 	Conversion d'une expression régulière sous sa forme postfixée
	 * @params :
	 * 	regex : String - Chaîne de caractère (expression régulière à transformer)
	 * @return :
	 * 	regex : String - Chaîne de caractère (expression régulière sous forme postfixée)
	 ******************************************************************************************************************/
	private String regexPostfix(String regex) {
		String postfix_regex = "", regex_norm = "";
		char char_regex[] = (regex+FIN).toCharArray();
		Stack<Character> operateurs = new Stack<Character>();
		
		
		// NORMALISATION
		for (int i = 0; i < char_regex.length - 1; i++) {
			regex_norm += char_regex[i];
			if (char_regex[i] == '*' || !METACARACTERES.contains(char_regex[i]) && char_regex[i] != '(') {
				if (!METACARACTERES.contains(char_regex[i+1]) && char_regex[i+1] != ')')
					regex_norm += '.';
			}
		}
		
		char_regex = (regex_norm+FIN).toCharArray();
		
		// FORME POSTFIX
		for (int i = 0; i < char_regex.length - 1; i++) {
			if (!METACARACTERES.contains(char_regex[i]) && char_regex[i] != '(' && char_regex[i] != ')' || char_regex[i] == ETOILE) {
				postfix_regex += char_regex[i];
			} else {
				if (!operateurs.isEmpty()) {
					if (char_regex[i] == ')') {
						while (!operateurs.isEmpty() && operateurs.peek() != '(') {
							postfix_regex += operateurs.pop();
						}
						operateurs.pop();
					} else if (METACARACTERES.contains(char_regex[i])) {
						while (!operateurs.isEmpty() && METACARACTERES.contains(operateurs.peek())) {
							postfix_regex += operateurs.pop();
						}
					}	
				}
				if (char_regex[i] != ')') operateurs.push(char_regex[i]);
			}
		}
		
		while (!operateurs.isEmpty()) {
			postfix_regex += operateurs.pop();
		}
		return postfix_regex;
	}
	
	/******************************************************************************************************************
	 * etoileThompson()
	 * @commentaire :
	 * 	Hypothèse : L'opérande (automate) n'a qu'un seul état initial (resp. terminal)
	 * 
	 * @params :
	 * 	- a0 : Automate
	 * @return :
	 * 	- a : Automate (résultat de la concaténation de a1 et a2)
	 ******************************************************************************************************************/
	private Automate etoileThompson(Automate a0) {
		// VARIABLES
		Automate a = new Automate();
		Etat 	initial = new Etat(0, true, false, new HashMap<Character, Etats>()), 
				terminal = new Etat(0, false, true, new HashMap<Character, Etats>());
		ArrayList<Etat> etats = new ArrayList<Etat>();	
		
		// UTILITAIRES
		ArrayList<Etat> e0_cpy = fullCloneAL(a0.getEtats()),
						a_visiter = new ArrayList<Etat>();
		Automate	a0_cpy = new Automate(e0_cpy);
		HashSet<Etat> visite = new HashSet<Etat>();
		Etat 	etat_tmp = new Etat(), 
				initial_a0 = new Etat();
		int nb_etats = 1;
		
		// INITIALISATION
		etats.add(initial);
		etats.add(terminal);
		
		initial.ajouterTransition(EPSILON, terminal);
		
		// Terminaux(A0) - EPSILON -> Initiaux(A0) (A_SUPPRIMER)
	
		for (Etat e_a0_i : a0_cpy.getInitiaux()) {
			initial.ajouterTransition(EPSILON, e_a0_i);
			a_visiter.add(e_a0_i);
			
			do {
				// Dépiler
				etat_tmp = a_visiter.get(0);
				a_visiter.remove(0);
				if (!visite.contains(etat_tmp)) {
					visite.add(etat_tmp);
						
					for (Map.Entry<Character, Etats> t : etat_tmp.TransitionsEtat().entrySet()) {
						for (Etat e : t.getValue()) {
							if (!visite.contains(e) && e != terminal) {
								if (e.estTerminal()) {
									e.mettreTerminal(false);
									e.ajouterTransition(EPSILON, e_a0_i);
									e.ajouterTransition(EPSILON, terminal);
								}
								a_visiter.add(e);
							}
						}
					}
					
					etat_tmp.mettreInitial(false);
					etat_tmp.mettreTerminal(false);
					etat_tmp.mettreId(nb_etats);
					nb_etats++;
					etats.add(etat_tmp);
					
				}
				
			} while(!a_visiter.isEmpty());

		}
		
		
		
		terminal.mettreId(nb_etats);
		a.setEtats(etats);
		a.update();

		return a;
	}
	
	/******************************************************************************************************************
	 * unionThompson()
	 * @commentaire :
	 * 	Hypothèse : Les opérandes (automates) n'ont qu'un seul état initial (resp. terminal)
	 * 
	 * @params :
	 * 	- a1 : Automate
	 * 	- a2 : Automate
	 * @return :
	 * 	- a : Automate (résultat de la concaténation de a1 et a2)
	 ******************************************************************************************************************/
	private Automate unionThompson(Automate a1, Automate a2) {
		// VARIABLES
		Automate a = new Automate();
		Etat 	initial = new Etat(0, true, false, new HashMap<Character, Etats>()), 
				terminal = new Etat(0, false, true, new HashMap<Character, Etats>());
		ArrayList<Etat> etats = new ArrayList<Etat>();	
		
		// UTILITAIRES
		ArrayList<Etat> e1_cpy = fullCloneAL(a1.getEtats()),
						e2_cpy = fullCloneAL(a2.getEtats()),
						a_visiter = new ArrayList<Etat>();
		Automate	a1_cpy = new Automate(e1_cpy),
					a2_cpy = new Automate(e2_cpy);
		HashSet<Etat> visite = new HashSet<Etat>();
		Etat 	etat_tmp = new Etat(), 
				terminal_a1 = new Etat(), 
				terminal_a2 = new Etat();
		int nb_etats = 1;
		
		// INITIALISATION
		etats.add(initial);
		etats.add(terminal);
		
		visite.add(terminal);
		
		// A1
		for (Etat e_a1 : a1_cpy.getInitiaux()) {
			initial.ajouterTransition(EPSILON, e_a1);
			e_a1.mettreInitial(false);
			if (!visite.contains(e_a1)) a_visiter.add(e_a1);
			do {
				
				// Dépiler
				etat_tmp = a_visiter.get(0);
				a_visiter.remove(0);
				visite.add(etat_tmp);
				
				for (Map.Entry<Character, Etats> t : etat_tmp.TransitionsEtat().entrySet()) {
					for (Etat e : t.getValue()) {			
						if (!visite.contains(e) && e != terminal_a1) {
							a_visiter.add(e);
							if (e.estTerminal()) {
								e.mettreTerminal(false);
								terminal_a1 = e;
								e.ajouterTransition(EPSILON, terminal);
							}
						}
					}
				}

				
				etat_tmp.mettreId(nb_etats);
				nb_etats++;
				etats.add(etat_tmp);
				
			} while (!a_visiter.isEmpty());

		}
		
		//A2
		
		visite.clear();
		visite.add(terminal);
		
		for (Etat e_a2 : a2_cpy.getInitiaux()) {
			initial.ajouterTransition(EPSILON, e_a2);
			e_a2.mettreInitial(false);
			if (!visite.contains(e_a2)) a_visiter.add(e_a2);
			do {
				
				// Dépiler
				etat_tmp = a_visiter.get(0);
				a_visiter.remove(0);
				visite.add(etat_tmp);
				
				
				for (Map.Entry<Character, Etats> t : etat_tmp.TransitionsEtat().entrySet()) {
					for (Etat e : t.getValue()) {			
						if (!visite.contains(e) && e != terminal_a2) {
							a_visiter.add(e);
							if (e.estTerminal()) {
								e.mettreTerminal(false);
								terminal_a2 = e;
								e.ajouterTransition(EPSILON, terminal);
							}
						}
					}
				}

				etat_tmp.mettreId(nb_etats);
				nb_etats++;
				etats.add(etat_tmp);
				
			} while (!a_visiter.isEmpty());
		}
		
		terminal.mettreId(nb_etats);
		
		a.setEtats(etats);
		a.update();
		return a;
	}

	
	/******************************************************************************************************************
	 * concatenerThompson()
	 * @commentaire :
	 * 	Hypothèse : Les opérandes (automates) n'ont qu'un seul état initial (resp. terminal)
	 * 
	 * @params :
	 * 	- a1 : Automate
	 * 	- a2 : Automate
	 * @return :
	 * 	- a : Automate (résultat de la concaténation de a1 et a2)
	 ******************************************************************************************************************/
	private Automate concatenerThompson(Automate a1, Automate a2) {
	Automate res = new Automate();
		
		Automate	a1_cpy = new Automate(fullCloneAL(a1.getEtats())),
					a2_cpy = new Automate(fullCloneAL(a2.getEtats()));
		
		Etat 	terminal_a1 = new Etat(),
				initial_a2 = new Etat();
		
		int n = 0;
		
		for (Etat e : a1_cpy) {
			res.add(e);
			
		}
		
		for (Etat e : a1_cpy.getFinaux()) terminal_a1 = e;
		terminal_a1.mettreTerminal(false);
		
		for (Etat e : a2_cpy.getInitiaux()) initial_a2 = e;
		
		for(Map.Entry<Character, Etats> t : initial_a2.TransitionsEtat().entrySet()) {
			for (Etat e : t.getValue()) {
				if (e != initial_a2) terminal_a1.ajouterTransition(t.getKey(), e); else terminal_a1.ajouterTransition(t.getKey(), terminal_a1);
			}	
		}
		
		for (Etat e : a2_cpy) {
			if (e != initial_a2) {
				res.add(e);
				for(Map.Entry<Character, Etats> t : e.TransitionsEtat().entrySet()) {
					for (Etat e_t : t.getValue()) {
						if (e_t == initial_a2) {
							e.TransitionsEtat().remove(t.getKey(), e_t);
							e.ajouterTransition(t.getKey(), terminal_a1);
						}
					}	
				}
			}
		}
		
		
		// renommage incohérent
		HashSet<Etat> visite = new HashSet<Etat>();
		ArrayList<Etat> a_visiter = new ArrayList<Etat>();
		Etat etat_courant = new Etat();
		
		res.update();
		for (Etat e : res.getInitiaux()) {
			if (!visite.contains(e)) {
				a_visiter.add(e);
			}
			while (!a_visiter.isEmpty()) {
				// Dépiler
				etat_courant = a_visiter.get(0);
				a_visiter.remove(0);
				visite.add(etat_courant);
				
				for (Map.Entry<Character, Etats> t : etat_courant.TransitionsEtat().entrySet()) {
					for (Etat e_t : t.getValue()) if (!visite.contains(e_t)) a_visiter.add(e_t);
				}
				
				// renommage
				etat_courant.mettreId(n);
				n++;
			}	
		}
		
		// renommage incohérent
		/*for (Etat e : res) {
			e.mettreId(n);
			n++;
		}*/
		
		res.update();
		return res;
	}

	
	// UPDATE
	public void update() {
		// RESET
		this.initiaux = new Etats();
		this.terminaux = new Etats();
		this.alphabet = new ArrayList<Character>();
		
		if (this.isEmpty()) for(Etat e : this.etats) this.add(e);
		if (this.etats.isEmpty()) for(Etat e : this) this.etats.add(e);
		
		
		for(Etat e : this.etats) {
			if (e.estInitial()) this.initiaux.add(e);
			if(e.estTerminal()) this.terminaux.add(e);
		}
		
		
		for (Etat e : this.etats) {
			for (Map.Entry<Character, Etats> t : e.TransitionsEtat().entrySet()) {
				if (!this.alphabet.contains(t.getKey())) this.alphabet.add(t.getKey());
			}
		}
	}
		
	/******************************************************************************************************************
	 * fullCloneAL()
	 * @commentaire :
	 * 	Clône complet d'un ensemble d'états et de leurs transitions respectives.
	 * 
	 * @params :
	 * 	- etats : Ensemble (tableau à éléments uniques) d'Etat (ensemble à clôner)
	 * @return :
	 * 	- clone : Ensemble (tableau à éléments uniques) d'Etat (clône)
	 ******************************************************************************************************************/
	public ArrayList<Etat> fullCloneAL(ArrayList<Etat> etats) {
		ArrayList<Etat> clone = new ArrayList<Etat>(),
						initiaux = new ArrayList<Etat>(),
						visite = new ArrayList<Etat>(),
						a_visiter = new ArrayList<Etat>();
		Etat etat_tmp = new Etat();
		
		// getInits + clone
		for (Etat e : etats) {
			clone.add(new Etat(e.idEtat(), e.estInitial(), e.estTerminal()));
		}
		for (Etat e : etats) {
			for (Map.Entry<Character, Etats> t : e.TransitionsEtat().entrySet()) {
				for (Etat e_t : t.getValue()) {
					for (Etat e_clone : clone) if (e_clone.idEtat() == e_t.idEtat()) etat_tmp = e_clone;
					for (Etat e_clone : clone) if (e_clone.idEtat() == e.idEtat()) e_clone.ajouterTransition(t.getKey(), etat_tmp);
				}
			}
		}
		return clone;
	}

	
	/******************************************************************************************************************
	 * getAlphabetRegex()
	 * @commentaire :
	 * 	Retourne l'alphabet d'un regex
	 * 
	 * @params :
	 * 	- regex : Chaîne de caractère (expression régulière évaluée)
	 * @return :
	 * 	- alphabet_regex : Ensemble (tableau à éléments uniques) de Character (alphabet obtenu)
	 ******************************************************************************************************************/
	private ArrayList<Character> getAlphabetRegex(String regex) {
		ArrayList<Character> alphabet_regex = new ArrayList<Character>();
		ArrayList<Character> metacaracteres = new ArrayList<Character>() {{
			add('(');
			add(')');
			add('|');
			add('*');
			add('+');
			add('.');
		}};
		char char_regex[] = regex.toCharArray();
		
		for (int i = 0; i < char_regex.length; i++) {
			if (!metacaracteres.contains(char_regex[i]) && !alphabet_regex.contains(char_regex[i]))
				alphabet_regex.add(char_regex[i]);
		}
		return alphabet_regex;
	}
}
