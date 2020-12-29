# Introduction :
Graph visualization is a way of representing structural information as diagrams of abstract graphs and networks. It has important applications in networking, bioinformatics, software engineering, database and web design, machine learning, and in visual interfaces for other technical domains..

The aim of this project is to design and implement a library allowing to generate and represent finite state machine also know as finite automaton. The library will also implement algorithms to perform operations that are specific to finite state automaton : Determination, Minimization, Complementary, Elimination of ***ε*** transitions, ...

# Definition of a finite automaton :
A nondeterministic finite automaton (NFA), or nondeterministic finite-state machine is represented formally by a 5-tuple : ***(Q, Σ, Δ, q0, F)***, consisting of :
   - a finite set of states ***Q***
   - a finite set of input symbols ***Σ*** .
   - a transition function ***Δ: Q×Σ → P(Q)***.
   - an initial state ***q0*** in ***Q***.
   - a set of states ***F*** distinguished as final states ***F ⊆ Q***

# Finite automaton algorithms : 
This library implments the following algorithms : 
  - **Random generation of Nondeterministic finite automaton**
  - **Elimination of ε-transitions**
  - **Completion of a finite automaton**
  - **Transpose of a finite automaton**
  - **Transform a Nondeterministic finite automaton to a Deterministic finite automaton**
  - **Minimization of a Deterministic finite automaton**
  - **Complementary of a Deterministic finite automaton** 
  - **Generate a finite automaton from a regex (Thompson's construction algorithm)** 


### Random generation of Nondeterministic finite automaton :
You can generate random Nondeterministic finite automaton using this library. There is two parameter to specify :
***N*** which is the number of states and ***Σ*** which is the set of symbol of the finite automaton. The ***ε*** is represented with the '&' character.
         
<p align="center">
   <img src="https://github.com/AmineAgrane/finite-state-automaton-library/blob/main/doc/random.png" height="600" align="center"/>
</p>

### Elimination of ε-transitions in a finite state machine : 
An epsilon transition allows a finite state machine to change its state spontaneously, i.e. without consuming an input symbol. It may appear in almost all kinds of nondeterministic finite state machine in formal language theory. Nondeterministic finite automaton with ***ε*** can be converted to NFA without ***ε***. We achieve this transformation by using the following method : 


1. Find out all the ε transitions from each state from ***Q***. That will be called as ***ε***-closure{***qi***} where ***qi ∈ Q***.
2. Then ***δ'*** transitions can be obtained. The ***δ'*** transitions mean a ***ε***-closure on ***δ*** moves.
3. Repeat Step-2 for each input symbol and each state of given NFA.
4. Using the resultant states, the transition table for equivalent NFA without ***ε*** can be built.

<p align="center">
   <img src="https://github.com/AmineAgrane/finite-state-automaton-library/blob/main/doc/epsilon%20production.png" height="400" align="center"/>
</p>

### Completion of a finite automaton : 
A finite automaton is said to be complete if, for any state ***p*** and symbol ***x***, there is at least one state ***q*** such that ***(p,x,q)*** is a transition of the automaton. Having a complete automaton is of particular interest in different treatments:
- word recognition : whatever the current state and the analyzed symbol, there is always a transition;
- transition to the complementary language: the automaton must be complete to carry out the required transformations;
- calculation of the minimal automaton recognizing the same language.

A simple way to get a complete automaton equivalent (i.e. recognizing the same language) to a non complete automaton is to add a so-called "trash" state, and to associate the missing transitions to it :
1. Add the trash state ***P*** to the finite automaton.
2. All transitions of the incomplete automaton ***A*** are kept in the complete automaton ***A'***.
3. Add a transition ***(q,x,P)*** for each missing transition ***(q,x,q)*** in ***A***
4. Add a transition ***(P,x,P)*** for each symbol of the alphabet

<p align="center">
   <img src="https://github.com/AmineAgrane/finite-state-automaton-library/blob/main/doc/complet.png" height="400" align="center"/>
</p>

### Transpose of a finite automaton : 
In automaton theory, the transposed automaton of a finite automaton ***A*** noted ***A^{R}***, is another finite automaton, which recognizes mirrors of words recognized by ***A*** For example, if ***A*** recognizes the word "aababa", then  ***A^{R}*** recognizes the word "ababaa". This is also called a mirror automaton. Another notation is ***A^{t}***. The transposed automaton is notably used in Brzozowski's algorithm for the minimization of a deterministic finite automaton.

<p align="center">
   <img src="https://github.com/AmineAgrane/finite-state-automaton-library/blob/main/doc/transpose.png" height="400" align="center"/>
</p>

### Transform a Nondeterministic finite automaton to a Deterministic finite automaton:
A deterministic finite automaton (DFA) also known as deterministic finite-state machine (DFSM) is a finite-state machine that accepts or rejects a given string of symbols, by running through a state sequence uniquely determined by the string. Deterministic refers to the uniqueness of the computation run. In  Nondeterministic finite automaton, when a specific input is given to the current state, the machine goes to multiple states. It can have zero, one or more than one move on a given input symbol. On the other hand, in a Deterministic finite automaton, when a specific input is given to the current state, the machine goes to only one state. DFA has only one move on a given input symbol.

To convert a Nondeterministic finite automaton to a Deterministic finite automaton, we apply the following steps : 
1. Initially ***Q'*** = ***ϕ***.
2. Add ***q0*** of NFA to ***Q'***. Then find the transitions from this start state
3. In ***Q'***, find the possible set of states for each input symbol. If this set of states is not in ***Q'***, then add it to ***Q'***.
4. In DFA, the final state will be all the states which contain ***F*** (final states of NFA)

<p align="center">
   <img src="https://github.com/AmineAgrane/finite-state-automaton-library/blob/main/doc/determination.png" height="400" align="center"/>
</p>

### Minimization of a Deterministic finite automaton :
DFA minimization is the task of transforming a given deterministic finite automaton (DFA) into an equivalent DFA that has a minimum number of states. Here, two DFAs are called equivalent if they recognize the same regular language.   

<p align="center">
   <img src="https://github.com/AmineAgrane/finite-state-automaton-library/blob/main/doc/minimization.png" height="400" align="center"/>
</p>

### Complement of a Deterministic finite automaton : 
Let ***L*** a language defined on an alphabet ***A***. The complement language of ***L*** over ***A*** is the set of all the words belonging to ***A**** that do not belong to ***L*** It may be interesting, in a global process of manipulation of an existing automaton, to switch from one language to its complement one. This mechanism is interesting when the textual definition of an automaton contains a negation. For example, to build an automaton that recognizes all words that contain neither the sequence *"abc"* nor *"cba"*, it may be simpler to first build an automaton that recognizes all words that contain both, and then switch to its complement language.

<p align="center">
   <img src="https://github.com/AmineAgrane/finite-state-automaton-library/blob/main/doc/complement.png" height="400" align="center"/>
</p>

### Generate a finite automaton from a regex (Thompson's construction algorithm) : 
In computer science, Thompson's construction algorithm, also called the McNaughton-Yamada-Thompson algorithm, is a method of transforming a regular expression into an equivalent nondeterministic finite automaton (NFA). This NFA can be used to match strings against the regular expression. This algorithm is credited to Ken Thompson.

Regular expressions and nondeterministic finite automata are two representations of formal languages. For instance, text processing utilities use regular expressions to describe advanced search patterns, but NFAs are better suited for execution on a computer. Hence, this algorithm is of practical interest, since it can compile regular expressions into NFAs. From a theoretical point of view, this algorithm is a part of the proof that they both accept exactly the same languages, that is, the regular languages.

<p align="center">
   <img src="https://github.com/AmineAgrane/finite-state-automaton-library/blob/main/doc/regex.png" height="500" align="center"/>
</p>



## Installing Graphiz & EclipseGraphviz : 

This project uses a number of open source library to work properly:

* [graphiz](https://graphviz.org/) Graphviz is an open source graph visualization software. The Graphviz layout programs take descriptions of graphs in a simple text language, and make diagrams in useful formats, such as images and SVG for web pages; PDF or Postscript for inclusion in other documents; or display in an interactive graph browser. Graphviz has many useful features for concrete diagrams, such as options for colors, fonts, tabular node layouts, line styles, hyperlinks, and custom shapes. 

<p align="center">
   <img src="https://github.com/AmineAgrane/finite-state-automaton-library/blob/main/doc/graphiz.PNG" height="300" align="center"/></center>
</p>

* [EclipseGraphviz](https://github.com/abstratt/eclipsegraphviz) : EclipseGraphviz is an Eclipse plug-in that provides a Java API for Graphviz and an Image Viewer view that allows easily visualizing the graphical output of your .dot files as you save changes to them. EclipseGraphviz provides an “Image Viewer” view that will show a rendered class diagram whenever a .uml file is selected or a TextUML Viewer editor is currently selected. To open the Image Viewer, go Window > Show view > Other… > EclipseGraphviz > Image viewer.

<p align="center">
   <img src="https://github.com/AmineAgrane/finite-state-automaton-library/blob/main/doc/eclipseGraphiz.PNG" height="500" align="center"/></center>
</p>

