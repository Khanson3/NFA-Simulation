# NFA-Simulation
CS 373: Automata Theory and Formal Languages non-deterministic finite automata simulation project

Due 2/17
Write a program to simulate the execution of a partial non deterministic finite automata (partial since we
don't have epsilon transitions). Your program can be written in Java, C, or C++, and needs to be able to
be compiled and executed on the computers in EB-G7 (or a Linux or Mac computer I have access to). If
you do not know Java, C, or C++, you will need to talk to me to discuss options for you to complete this
assignment.
Your program will read the definition of the machine from a file (the first command line argument), with
the second command line argument being the string to simulate the machine running on (the input to the
automata). The output of your program will be written to standard output. The output will consist of
either: 1) the word accept followed by a blank space followed by the list of accept states (blank space
delimited) that the automata can end up in after reading in the input string (if there is a way for the
automata to end in an accept state after reading the input); or 2) the word reject followed by a blank
space followed by the list of states (blank space delimited) that the automata can end up in after reading
the string (if there is no way for the automata to end in an accept state after reading the input). Your
output needs to end with the newline character.
The input file will be tab delimited (should be easily parsed by Java, C, or C++).
For this program, the states will be numbered between 0 and 1,000 (not necessarily contiguous or
entered in order). There are two types of special states – the start state (only one) and the accept states
(0 or more).
There are two types of input lines: state lines and transition lines.
The state lines are of the form:
state x start
state x accept
state x acceptstart
state x start accept
where x is a number in [0, 1000]. States that are niether accept or start states will not have an input line.
Note in the above, there is a tab between accept and start in “statex acceptstart”.
Here are some examples:
state 7 start accept
state 10 acceptstart
state 20 accept
state 27 start
There is no guarantee that the first state is the accept state or that the states are in order or they are
contiguous.
The remainder of the file defines the transitions. For this machine, the transition format is “p,x->q” where
p is the state that the machine is in, x is the symbol that the machine reads, and q is the state that the
machine transitions to. There will be at most 100,000 transitions.
The format of the transitions in the file will be:
1 of 6
Due 2/17
transition p x q
where p and q are states in [0, 1000], and x is the symbol to read. For this program you can assume that
x will be a digit {0, 1, …, 9} or a lower case letter {a, b, …, z}.
Since the machine is non deterministic, there may be multiple states that the automata can transition to
for a single state and input symbol combination.
The input will be a string, consisting of digits and lower case letters. Initially the machine will be looking
at the left most symbol of the input string and in the start state (just like the finite automata that we are
currently discussing in class).
If the machine can end in an accept state after reading the input string, then your program should output
accept followed by a blank space followed by the list accept states that the automata can end in after
reading the entire input. The states can be output in any order, but each state is to be only listed once.
If the machine can never end in an accept state after reading the input string, then your program should
output reject followed by a blank space followed by the list of all states that the automata can end in
after reading the input. The states can be output in any order, but each state is to be only listed once. If
there is no transition for the given state and input symbol, then you should assume that the current
branch of the computation ends in a non accept state (although, you will not have a state for this branch
to add to the list of states reached after reading the input).
For java, standard input is System.in, standard output is System.out, and standard error is System.err.
For C, standard input is stdin, standard output is stdout, and standard error is stderr.
For C++, standard input is cin, standard output is cout, and standard error is cerr.
