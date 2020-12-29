package Package1;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

public class FichierAutomate {
	
	//Génère un fichier .txt à partir d'un Automate
	//On écrit d'abord les états,  ensuite les transitions
	public void Automate_To_Fichier(Automate automate, String nom_fichier) throws IOException
	{
		/* Variables */
		Character lettre=' ';
		Etats Successeurs= new Etats();
		char initial, terminal;
		String ligne=" ";


        FileWriter fichier = new FileWriter(nom_fichier, false);

		//Ecriture des états
		for(Etat etat : automate)
		{
			initial = (etat.estInitial()) ? 't' : 'f' ; 
			terminal = (etat.estTerminal()) ? 't' : 'f' ; 	
			ligne="-"+etat.idEtat()+"/"+initial+"/"+terminal+"\n";
			fichier.write(ligne);
		}
		  
		fichier.write("\n\n");
		
		//Ecriture des transitions
		for(Etat etat: automate)
		{			
			for(Map.Entry<Character, Etats> transition : etat.TransitionsEtat().entrySet())
			{
			    lettre = transition.getKey();
			    Successeurs=etat.SuccEtat(lettre);
			    ligne=etat.idEtat()+"/"+lettre+"/";
			    for(Etat etat2: Successeurs)
			    {
			    	ligne+=etat2.idEtat()+",";
			    }
				ligne=Supprimmer_Dernier(ligne);
				fichier.write(ligne+"\n");
			}
			
		}
        fichier.close();
	}
	
	
	//Génère un automate à partir d'un fichier .txt
	//Le fichier texte est décomposé en deux parties
	//La premiere partie pour la déclarations des états
	//La deuxieme partie pour la déclarations des transitions entre états
	public Automate Fichier_To_Automate (String NomFichier) throws IOException
	{
			/* Variables */ 
	       String Ligne = null;
	       String[] Decompose_Transition = new String[10];
	       String[] Decompose_Etat_Arrivee= new String[10];
	       String[] Decompose_Etat = new String[10];
	       Etats etats = new Etats();
	       
	       try {
	    	   
	           // FileReader et BuffereReader.
	           FileReader fileReader = new FileReader(NomFichier);
	           BufferedReader bufferedReader = new BufferedReader(fileReader);
	        
	           while((Ligne = bufferedReader.readLine()) != null) 
	           {	
	        	   if(Ligne.length() > 0) 
	        	   {	   
	        		 //On est dans la partie déclarative des ETATS
	          		 if(Ligne.startsWith("-"))
	        		 {
	        		   Ligne=Ligne.substring(1);
	              	   Decompose_Etat=Ligne.split("/");
	              	   etats.add(Creation_Etat( Decompose_Etat));
	              	 }
	          		 //On est dans la partie déclarative des TRANSITIONS
	        		 else
	        		 {
	        		   Decompose_Transition=Ligne.split("/");
	        		   Decompose_Etat_Arrivee=Decompose_Transition[2].split(","); 
	        		   Creation_Transition(etats,Decompose_Transition, Decompose_Etat_Arrivee);
	        		 } 
	        	   }
	               
	           }   

	           // Fermerture du fichier
	           bufferedReader.close();         
	       }

	       catch(IOException ex) {System.out.println("Error reading file '"+ NomFichier + "'");}	       
	       Automate automate= new Automate(etats);
	       return automate;

	}
	
	
	public Etat Creation_Etat(String[] Decompose_Etat )
	{	
		/*   Variables  */
		Etat etat=null;
		etat = new Etat();
		etat.id=Integer.parseInt(Decompose_Etat[0]);
		etat.initial = Decompose_Etat[1].startsWith("t");
		etat.terminal = Decompose_Etat[2].startsWith("t");
		return etat;
	}
	
	
	void Creation_Transition(Etats etats, String[] Decompose_Transition, String[] Decompose_Etat_Arrivee)
	{
		/*   Variables  */
		Etat depart = null, arrivee=null;
		depart=etats.getEtat(Integer.parseInt(Decompose_Transition[0]));	
		Character c=Decompose_Transition[1].charAt(0); 

			for (String s : Decompose_Etat_Arrivee)
			{
				arrivee=etats.getEtat(Integer.parseInt(s));
				depart.ajouterTransition(c, arrivee);
			}
	}
	
	
	public static void Affichage_Fichier() throws IOException
	{
       
	   //Nom du fichier à ouvrir.
       String NomFichier = "automate1.txt";
       
       // On lit ligne par ligne
       String Ligne = null;
       String[] Decompose_Transition;
       String[] Decompose_Etat_Arrivee;
       String[] Decompose_Etat = new String[10];
       
       try {
    	   
           // FileReader et BuffereReader.
           FileReader fileReader = new FileReader(NomFichier);
           BufferedReader bufferedReader = new BufferedReader(fileReader);
        
           while((Ligne = bufferedReader.readLine()) != null) 
           {	
        	   
        	 if(Ligne.length() > 0) 
        	 {
        		 if(Ligne.startsWith("-"))
        		 {
        			   Ligne=Ligne.substring(1);
	              	   System.out.println("La ligne est : "+Ligne);
	              	   Decompose_Etat=Ligne.split("/");
	            	   System.out.println("La ligne decomposé est :\n");
	                   for (String s : Decompose_Etat) 
	                   {System.out.print(""+s+"    ");}
	              	   
        		 }
        		 else
        		 {
               System.out.println("La ligne est : "+Ligne);
               Decompose_Transition=Ligne.split("/");
               Decompose_Etat_Arrivee=Decompose_Transition[2].split(",");
               
        	   System.out.println("La ligne decomposé est :\n");
               for (String s : Decompose_Transition) 
               {System.out.print(""+s+"    ");}
                System.out.print("\n");
               
         	   System.out.println("Les états d'arrivés de cette ligne sont  :\n");
               for (String s : Decompose_Etat_Arrivee) 
           	   {System.out.print(""+s+"  ");}
                System.out.print("\n\n");
        		 }
        	 }

           }   

           // Fermerture du fichier
           bufferedReader.close();         
       }
       catch(FileNotFoundException ex) {System.out.println("Unable to open file '" + NomFichier + "'");}
       catch(IOException ex) {System.out.println("Error reading file '"+ NomFichier + "'");}
		

	}
	
	
	private static String Supprimmer_Dernier(String str) 
	{
		if(str.length() > 0)
		{  return str.substring(0, str.length() - 1); }
		else
		{ return str; }
	}
	
	public void Automate_To_DOT(Automate automate, String nom_fichier) throws IOException
	{
		/* Variables */
		Character lettre=' ';
		Etats Successeurs= new Etats();
		Set<Character> alphabet = new HashSet<Character>();
		Set<Character> alphabetTransition = new HashSet<Character>();
		boolean existe=false;

        FileWriter fichier = new FileWriter(nom_fichier, false);
        fichier.write("digraph G {\r\n" + 
        		"    node [shape = circle];\r\n" + 
        		"    edge [label = \"&epsilon;\"];\n\n");

		//Ecriture des états
		for(Etat etat : automate)
		{	fichier.write(Etat_DOT(etat));	}
		
		fichier.write("\n\n\n");
		
		//Ecriture des transitions
		
		for(Etat etat1: automate)
		{
			alphabet = etat1.alphabetEtat();
			for (Etat etat2: automate)
			{
				alphabetTransition = new HashSet<Character>();
					for (Character c : alphabet)
					{
						existe=etat1.TransitionExiste(etat1, etat2, c);
						if(existe) alphabetTransition.add(c);
					}
				//Transition_to_dot prends etat1, etat2 et le hashset de cara entre ces deux des etats
				if(!alphabetTransition.isEmpty())
				{
					 fichier.write(Transition_DOT2(etat1,etat2,alphabetTransition));
				}
			}		
		}

		fichier.write("}");
        fichier.close();
	}
	
	
	public String Etat_DOT(Etat etat)
	{
		String S=" ";
		if (etat.getIdDeterministe() == null) {
			if(etat.estInitial() && etat.estTerminal())
			{S=etat.idEtat()+" [label = \"q"+etat.idEtat()+"\", shape = doublecircle, color=green]; "; } 
			else if(etat.estInitial())
			{S=etat.idEtat()+" [label = \"q"+etat.idEtat()+"\", color=green]; "; } 
			else if(etat.estTerminal())
			{S=etat.idEtat()+" [label = \"q"+etat.idEtat()+"\", shape = doublecircle]; "; } 
			else
			{S=etat.idEtat()+" [label = \"q"+etat.idEtat()+"\"]; "; }
		} else {
			if(etat.estInitial() && etat.estTerminal())
			{S=etat.idEtat()+" [label = \"{"+etat.getIdDeterministe()+"}\", shape = doublecircle, color=green]; "; } 
			else if(etat.estInitial())
			{S=etat.idEtat()+" [label = \"{"+etat.getIdDeterministe()+"}\", color=green]; "; } 
			else if(etat.estTerminal())
			{S=etat.idEtat()+" [label = \"{"+etat.getIdDeterministe()+"}\", shape = doublecircle]; "; } 
			else
			{S=etat.idEtat()+" [label = \"{"+etat.getIdDeterministe()+"}\"]; "; }
		}
			
		
		return S+"\n";
	}

	
	public String Transition_DOT(Etat etat_init, Character lettre, Etat etat_final)
	{
		String S= " ";
		if(lettre=='&')
		{S= etat_init.idEtat()+" -> "+etat_final.idEtat();}
		else 
		{S= etat_init.idEtat()+" -> "+etat_final.idEtat()+"[label = \""+lettre+"\"];";}
		
		return S+"\n";
	}
	
	
	public String Transition_DOT2(Etat etat1, Etat etat2, Set<Character> alphabetTransition)
	{
		String epsilon=null;
		String lettre="";
		String S="";

		for (Character c : alphabetTransition)
		{
			if( c == '&') { epsilon= "&epsilon;";   }
			else
			{
			lettre+=c+",";					
			}
			
		}

		if(epsilon != null)
		{S= etat1.idEtat()+" -> "+etat2.idEtat()+"[label = \""+epsilon+","+lettre;  }
		else
		{S= etat1.idEtat()+" -> "+etat2.idEtat()+"[label = \""+lettre;}
		
		S=Supprimmer_Dernier(S);
		S=S+"\"];";
		return S+"\n";
	}
	
	public void SuppressionLignesDouble(String NomFichier) throws IOException 
	{
	    BufferedReader lecteur = new BufferedReader(new FileReader(NomFichier));
	    Set<String> lignes = new HashSet<String>(10000); 
	    String ligne;
	    while ((ligne = lecteur.readLine()) != null)
	    {     lignes.add(ligne);  }
	    lecteur.close();
	    

	    BufferedWriter writer = new BufferedWriter(new FileWriter(NomFichier));
	    for (String unique : lignes) 
	    {
	        writer.write(unique);
	        writer.newLine();
	    }
	    writer.close();
	}
	
	
	

}


