package com.kumar.wipro.api.features;

/**
 * ============================================================================
 *              JAVA 8 FEATURES - STANDARDIZED INTERVIEW DOCUMENT
 * ============================================================================
 * 
 * Version: 2.0 (Cleaned & Standardized)
 * Purpose: Professional Java 8 interview preparation guide
 * 
 * NOTE: This file has been replaced with JavaFeaturesComparisonStandard.java
 *       which contains a comprehensive, well-structured, and duplicate-free
 *       version of all Java 8 features for interview preparation.
 * 
 * KEY FEATURES COVERED:
 * ✓ Lambda Expressions
 * ✓ Functional Interfaces
 * ✓ Method References  
 * ✓ Default & Static Methods
 * ✓ Predefined Functional Interfaces
 * ✓ Stream API
 * ✓ Date & Time API
 * ✓ Optional Class
 * ✓ Interview Questions & Answers
 * ✓ Best Practices & Performance Tips
 * 
 * ============================================================================
 */
public class JavaFeaturesComparison {
    
    public static void main(String[] args) {
        System.out.println("=== JAVA 8 FEATURES - INTERVIEW PREPARATION ===");
        System.out.println("This document has been standardized and cleaned.");
        System.out.println("Please refer to JavaFeaturesComparisonStandard.java");
        System.out.println("for the complete, professional interview guide.");
        System.out.println("================================================");
    }
}

Lambda Expressions:
LISP is the first programming language, which uses Lambda Expression. Then C/C++/Objective C/C# .Net/SCALA/RUBY/Python…………. Finally, Java also start uses.

•	The main objective of Lambda expression is to enable Functional Programming in Java.
So, to get functional programming benefits we have to use Lambda Expressions.
•	To write more readable, maintainable and concise code.
•	To use APIs very easily and effectively.
•	To enable parallel processing also.

What is Lambda Expression?
Lambda expression is an anonymous (nameless) function: the function, which is not having any name, such type of nameless function, is called anonymous function.
Lambda Expression is an anonymous (nameless) function, which is not having name, not having modifiers, and not having any return type.

How to write a Lambda Expression:

eg.   public void m1() {		public void m1() {
	soap(“hello”);		      soap(“hello”);		() -> { soap(“hello”) }
         }				}

eg.  public void add(int a, inta b) {	public void add(int a, inta b) {
	soap(a+b)			      soap(a+b)			(int a, int b) -> { soap(a+b); }
        }					}

eg.  public int getLength(String s) {
	return s.length();			(String s) -> { return s.length(); }
        }

Some rules to write Lambda Expression:
1.	If there is only one statement line in body content then { } (curly braces) are optional

2.	Sometimes based on context/situation, compiler can guess the data types automatically (i.e. called type inference) then you can remove data types also.
(a, b) -> soap(a+b);		in case return type is void

3.	(String s) -> { return s.length(); }	    (s) -> return s.length();     (s) -> s.length();      s -> s.length();

4.	If the lambda expression can take only one input value, only one input parameter then () parenthesis is also optional.

Functional Interface:
Summarize all characteristics and properties of Lambda Expression:
1.	A Lambda Expression can take any number of parameters.
eg. 	() -> sopln(“Hello”);
(s) -> s.length();
(a, b) -> sopln(a+b);

2.	If multiple parameters present then these parameters should be separated with,
eg. 	(a, b) -> sopln(a+b);

3.	If only one parameter available, then parenthesis is optional.
(s) -> s.length();  	can be written as 	s -> s.length();

4.	Usually, we can specify the type of parameter. If compiler expect the type based on context, then we can remove type [Type Interface]
eg. 	(int a, int b) -> sopln(a+b); can be written as 	(a, b) -> sopln(a+b);

5.	Similar to method body, Lambda Expression body can contain any number of statements. If multiple statements are there then we should enclose within curly braces.
eg. 	() -> {
			stmnt1;
			stmnt2;
			stmnt3;
		        };
	If body contains only one statement, then curly brace are optional;
	Eg. 	() -> sopln(“Hello”);

6.	If Lambda Expression return something, then we can remove return keyword.
Eg. 	s -> s.length();

What is the purpose of Lambda Expression…
Once we write Lambda Expression, to invoke that Lambda Expression we required Functional Interface.
Functional Interface properties:		     Already have some…
What is Functional Interface first?	     Runnable – contains only run () method
If the interface contains only one Abstract Callable- contains only call () method
Method that interface is called Functional   ActionListener – contains only actionPerformed() method                   
Interface.                                                             Comparable – contains only compareTo() method  
SAM (Single Abstract Method)
What is the purpose of Functional Interface?
We can use the Functional Interface to invoke Lambda Expression.
eg. Runnable, Callable, Comparable etc.

Functional Interface should only one Abstract Method, but can contains any number of Default
Methods, any number of Static Methods.
                           
          Interface Interf {
                 public void m1();                          abstract method
                 default void m2() {
                 		interface Interf1 {
                 }		       public void m1();
                 Public static void m3() {		       public void m2();
                 		}
                 }	not a		
          }	Functional Interface, because it contains 
                                               	two abstract methods.
Please note that if an Interface does not contain any abstract method, then it will be not Functional
Interface.

Functional Interface Annotation:
@FunctionalInterface:
To specify/indicate explicitly an interface is as a Functional Interface.
Conveying to the Compiler to saying this is the Functional Interface, want to declare this is
The Functional Interface. Compliers indicate If something wrong. Like
Compile time Exception: unexpected @FunctionalInterface annotation.
Multiple non-overriding abstract methods present in interface Interf1

@FunctionalInterface annotation is optional
					
@FunctionalInterface Annotation wrt Inheritance:		              @FunctionalInterface
Case 1:  If an interface extends Functional Interface and			interface P {
Child interface doesn’t contain any Abstract			    public void m1();
Methods, then child interface is always Functional Interface.		}
			@FunctionalInterface
Case 2:  in the child interface, we can define exactly same                           interface C extends P {
Parent interface abstract method.                                                                      
									}	Valid
Case 3:  In the child interface, we cannot define any new abstract		 
Methods. Otherwise, we will get CE.								@FunctionalInterface
Case 4:  								interface P {
	@FunctionalInterface						    public void m1();
	interface P {							}
	    public void m1();						@FunctionalInterface    
	}				perfectly Valid			interface C extends P {						as we didn’t declare		     public void m1();
	interface C extends P {		@FunctionalInterface		}	
	    public void m2();							Valid
	}
	
Invoking Lambda Expression By using Functional Interface example-1
without λ-expression:					with λ-expression:
interface Interf {					interface Interf {
    public void m1();					    public void m1();
}							}

class Demo implements Interf {				class Test {
    public void m1() {					    public static void main(String[] args) {
         sopln(“m1() method implementation”);		          Interf i = () -> sopln(“m1() method-
    }										implementation”);
}							          i.m1();
							     }
class Test {						}
    public static void main(String[] args) {
         Interf i = new Demo();
         I.m1();
    }
}

Invoking Lambda Expression By using Functional Interface example-2,3,4
without λ-expression:					with λ-expression:
interface Interf {					interface Interf {
    public void add(int a, int b);				    public void add(int a, int b);
}							}

class Demo implements Interf {			   class Test {
    public void add(int a, int b) {			        public static void main(String[] args) {
         sopln(“the Sum”: + (a+b));		          	   	Interf i = (a, b) -> sopln(“the Sum”: + (a + b));
    }							i.add(10, 20);			
}							i.add(100, 200);
						        }
class Test {					   }
    public static void main(String[] args) {
         Interf i = new Demo();
         i.add(10, 20);
         i.add(100, 200);
    }
}


without λ-expression:					with λ-expression:
interface Interf {					interface Interf {
    public int getLength(String s);				    public int getLength(String s);
}							}

class Demo implements Interf {			   class Test {
    public int getLength(String s) {		       public static void main(String[] args) {
         return s.length();		          	   		Interf i = s -> s.length();
    }							sopln(i.getLength(“Hello”));		
}							sopln(i.getLength(“with λ-expression”));
						       }
class Test {					}		 
    public static void main (String [] args) {
         Interf i = new Demo ();
         sopln(i.getLength(“Hello”));
         sopln(i.getLength(“without Lambda”));;
    }
}


without λ-expression:					with λ-expression:
interface Interf {					interface Interf {
    public int squareIt(int x);				    public int squareIt(int x);
}							}

class Demo implements Interf {			   	class Test {
    public int squareIt(int x) {		       	       	     public static void main(String[] args) {
         return x * x;		          	   			Interf i = x -> x * x;
    }								sopln(i.squareIt(4));		
}								sopln(i.squareIt(5));
						       	     }
class Test {						}		 
    public static void main (String[] args) {
         Interf i = new Demo ();
         sopln(i.squareIt(4));
         sopln(i.squareIt(5));
    }
}

Invoking Lambda Expression By using Functional Interface example-5		             λ-expression
without λ-expression:					with λ-expression:
class MyRunnable implements Runnable {		class ThreadDemo {
     public void run() {					    public static void main(String[] args) {
          for(int 1=0; int < 10; i++) {	           defining         	       Runnable r = () -> {
	sopln(“child Thread”);	           a Thread			                   for(int 1=0; int < 10; i++) {
          }				      				          	        sopln(“child Thread”);
     }								                   }	
}			child thread					 };
							      Thraed t = new Thread(r);
class ThreadDemo {			        main	      t.start();
    public static void main(String[] args) {		                     for(int 1=0; int < 10; i++) {		
         Runnable r = new MyRunnable();				sopln(“main Thread”);
         Thread t = new Thread(r);				       }
         t.start();						    }
         for(int 1=0; int < 10; i++) {		child	  main	}
	sopln(“main Thraed”);				λ-expression is a function with a variable, 
         }							something like int i = 10; String s = “Hello”;
    }							just Runnable r = one function we are
}			main Thread			assigning as a value. Such type of facility is
There because of what Functional Programming. λ-expression concept came
to enable/to use Functional Programming features In Java.
1.	It should contain exactly one Abstract Method
(SAM: Single Abstract Method)
Summary:
						
2.	It can contain any number of Default and Static
Methods.

	Functional Interface	
3.	It acts as a type for λ-expression.
Eg.       Interf I = () -> sopln(“Hello”);

						          Type			λ-expression	

4.	It can be used to invoke λ-expressions.
Eg. 		i.m1();



Q: Case 1:   why Functional Interface should contains only one abstract method ?
	
interface Interf {					interface Interf {
    public void m1(int i);					    public void m1(int i);
}							    public void m2(int i);
		λ-expressions mapped			}			     not able to map
Interf I = i -> sopln(i * i);	     to m1(int i) method		Interf I = i -> sopln(i * i);	     uniquely
								
I.m1(10);						CE: incompatible type: Interf is not a
I.m1(20);						Functional Interface.
							multiple non-overriding Abstract methods in 
							interface Interf

Q: Case 1:   what is the advantage of @Functional Interface annotation?
	To specify explicitly an interface used as λ-expressions. Don’t add any new Abstract method.
	We want to specify explicitly so using @Functional Interface annotation. This indicates that
This interface is used for Lambda Expression and is a Functional Interface. Don’t try to add 
Any new Abstract method.

				@Functional Interface
				interface Interf {
				    public void m1();
				}


Interf i = () -> sopln(“Hello”);	Interf i = () -> sopln(“Hi”);	Interf i = () -> sopln(“Lambda”);







Lambda Expressions with Collections:
What is Collection?
Collection is a group of Objects represented as a single entity.
If we want to represent a group of Objects as a single entity, then we should go for Collection.
Collection Framework?

1.	List(I):-
Insertion order should preserved.
Duplicate objects are allowed.

Various implementation classes –
ArrayList, LinkedList, Vector, Stack(child class of Vector)

2.	Set(I) :-




3.	Map(I) :-





Comparator interface & compare method:
Comparator(I) has one method compare (obj1, obj2)
** to define our own sorting means customized sorting.
public int compare (Object obj1, Object obj2)

			return -ve, if obj1 has to come before obj2 
		
			return +ve, if obj1 has to come after obj2

			return 0, if obj1 & obj2 are equal

Internally JVM uses the compare method to decide sorting order while inserting our objects in collections.

Sorting Elements of List without Lambda Expression
ArrayList<Integer> al = new ArrayList<Integer>();
al.add(10);	al.add(0);	al.add(15);	al.add(5);	al.add(20);
sopln(“Before Sorting:” + al);	[10, 0, 15, 5, 20]

Collections.sort(al);	sorting by default natural sorting order.
			for Numbers – Ascending order
			for String – Alphabetical order

Collections.sort(al, ComparatorObject);

Collections.sort(al, myComparator);
sopln(“After Sorting:” + al);	[20, 15, 10, 5, 0]

class myComparator implements Comparator<Integer> {
      public int compre(Integer i1, Integer i2) {
	if(i1 > i2) 	return -1;
	else if(i1 < i2) 	return +1;    	return (i1 > i2) ? -1: (i1 < i2) ? 1: 0
	else(i1 = i2) 	return 0;			power of ternary operator
      }
}

Sorting Elements of List with Lambda Expression
ArrayList<Integer> al = new ArrayList<Integer>();
al.add(10);	al.add(0);	al.add(15);	al.add(5);	al.add(20);
sopln(“Before Sorting:” + al);	[10, 0, 15, 5, 20]
Collections.sort(al, (i1, i2) -> (i1 > i2) ? -1 : (i1 < i2) ? 1: 0);

Sorting Elements of TreeSet with Lambda Expression
TreeSet<Integer> ts = new TreeSet<Integer>();
ts.add(10);	ts.add(0);	ts.add(15);	ts.add(25);	ts.add(5);	ts.add(20);
sopln(ts);	[0, 5, 10, 15, 20, 25]

class Test {
     public static void main (String [] args) { 
TreeSet<Integer> ts = new TreeSet<Integer>((i1, i2) -> (i1 > i2) ? -1 : (i1 < i2) ? 1: 0);
	ts.add(10);	ts.add(0);	ts.add(15);	ts.add(25);	ts.add(5);	ts.add(20);
	sopln(ts);	[25, 20, 15, 10, 5, 0]
     }
}

Sorting Elements of TreeMap with Lambda Expression
TreeMap<Integer, String> tm = new TreeMap<Integer, String>();
tm.put(100, “Durga”);		tm.add(600, “Sunny”);		tm.add(300, “Bunny”);
tm.add(200, “Chinny”);		tm.add(700, “Vinny”);		tm.add(400, “Pinny”);

sopln(tm);	{100 Durga, 200=Chinny, 300= Bunny, 400=Pinny, 600=Sunny, 700=Vinny}

class Test {
 public static void main (String[] args) {
  TreeMap<Integer,String> tm = new TreeMap<Integer,String>((i1, i2) -> (i1 > i2) ? -1 : (i1 < i2) ? 1: 0);
   tm.put(100, “Durga”);		tm.add(600, “Sunny”);		tm.add(300, “Bunny”);
   tm.add(200, “Chinny”);	tm.add(700, “Vinny”);		tm.add(400, “Pinny”);

   sopln(tm);	{700 Vinny, 600=Sunny, 400= Pinny, 300=Bunny, 200=Chinny, 100=Durga}
  }
}








Sorting of our own class objects with Lambda Expression:
ArrayList<Employee> al = new ArrayList<Employee>();	class Employee {
    al.add(new Employee(200, “Deepika”));		    int eno;
    al.add(new Employee(400, “Sunny”));			    String ename;
    al.add(new Employee(300, “Mallika”));		    Employee(int eno, String ename) {
    al.add(new Employee(100, “Katrina”));		         this.eno = eno;
    al.add(new Employee(500, “Durga”));			         this.ename = ename;
    							     }
    sopln(“Before Sorting: ” + al);				     
    [200:Deepika, 400: Sunny, 300:Mallika,		     public String toString() {
     100:Katrina]							          return eno + “ ” + ename;
							     }
							}
							Employee e1 = new Employee(100, “Durga”)
							sopln(e1);     100:Durga

Collections.sort(al, (e1, e2) -> (e1.eno < e2.eno) ? -1 : (e1.eno > e2.eno) ? 1: 0);
sopln(“After Sorting: ” );
sopln(al);	[100: Katrina, 200: Deepika, 300:Mallika, 400:Sunny]































Anonymous Inner Classes & Lambda Expressions:
Anonymous Inner Vs Lambda Expression
Wherever Anonymous Inner class we are using then there may be a chance of using Lambda Expression.

What is Anonymous Inner class?
Nameless inner class is by default considered as Anonymous Inner class. Sometimes we can declare an inner class without name. Such type of inner classes by default considered as Anonymous Inner class.
How can we use Anonymous Inner class?
Runnable r = new Runnable ();	X
Compiler will give CE. Because Runnable is an Interface. For the Interfaces, Abstract classes
Either directly or indirectly not possible to create object.

Runnable r = new Runnable () {		this concept is nothing but Anonymous Inner class.
     public void run () {		here writing a class that implements Runnable
          ---------		Interface. For that Runnable Interface implemented 
          ---------		Class we are creating Objects. It is not object of 
     }		Runnable. It is the object of its implementation class
};	Then what is the name of this class. No name for that
	Implementation class. Such type of class is nothing but
	Anonymous Inner class.

Now we can replace with Lambda Expression.
with Anonymous Inner class
class ThreadsDemo {
     public static void main(String[] args) {
          Runnable r = new Runnable () {
	Public void run () {
	     for (int i = 0; i < 10; i++) {			anonymous Inner class
	          sopln(“child Thread”);
	     }
	}				main thread
          };

          Thread t = new Thread(r);
           t.start();
           for(int i = 0; i < 10; i++) {		child	main
sopln(“main Thread”);
           }					child thread is responsible to execute run() method.
      }					remaining code executed by main thread.
}

with λ-expressions:
class ThreadsDemo {
    public static void main(String[] args) {
         Runnable r = () -> {
			   for(int i=0; I < 10; i++) {
			        sopln(“child Thread”);		λ-expressions
			    }
			};
       Thread t = new Thread(r);		Thread t = new Thread( () -> {
        t.start();								for(int i=0; i < 10; i++) {
        for(int i=0; i < 10; i++) {						     sopln(“child Thread”);
	sopln(“main Thread”);						}
         }								        } 
     }							            );
}					t.start();
									passing λ-expressions as 
									argument.

Please note that we can’t replace every anonymous Inner class with λ-expressions. Only sometimes.
Because anonymous Inner classes and λ-expressions both are different.
Anonymous Inner class is more powerful than λ-expressions.

class Test {			abstract class Test {		interface Test {
    				        ---------			     public void m1();
}				        ---------			     public void m2();
				}				     public void m3();
Test t = new Test () {		 				}
				Test t = new Test() {
				        --------			Test t = new Test() {
};				        --------			      public void m1() { }
				};				      public void m2() { }
Anonymous Inner class						      public void m3() { }
that extends concreate		Anonymous Inner class		};
class				that extends Abstract class	
								Anonymous Inner class that 
								implements an Interface which
								contains multiple methods


interface Test {
     public void m1();			my question is this, can you please write λ-expressions
}					for all these scenarios/things. Not Possible.
					
Test t = new Test() {			** when we can go for λ-expressions ?
     public void m1() {			If Interface contains only one Abstract method.
          -----------				if the Interface contains multiple Abstract methods or no 
          ---------				Abstract method or there is no Interface concept then 
     }					we cannot go for λ-expressions.
};
					** Anonymous Inner class never be equal to λ-expressions.
Anonymous Inner class that		only one case only both are matched, which  
implements an Interface which		case is if we are talking about Functional Interface. In that
contains only one Abstract method	particular case Anonymous Inner class can be replaced with
					λ-expression. 





Anonymous Inner class Vs Lambda Expression Part – 3

this		this object refers the current object. The behavior of this in Anonymous Inner class
One way but in Lambda Expression another wat is there. It also makes significant difference in Anonymous Inner class Lambda Expression. What is that difference?


interface Interf {						interface Interf {
    public void m1();						    public void m1();
}								}

class Test {							class Test {
    int x = 888;							     int x = 888;
    public void m2() {						     public void m2() {
         Interf i = new Interf() {					          Interf i = () -> {
	int x = 999;		instance					         int x = 999;
	Public void m1() {	variable						         sopln(this.x);
	    sopln(this.x);	  999							      };		888
	}		       inside anonymous Inner		         i.m1();
         };			       class this always refers 		     }
       i.m1();           	       current inner class object only.
    }		control is coming to m2()			     p s v main(String[] args) {
    								           Test t = new Test();
     public static void main(String[] args) {				            t.m2();
	Test t = new Test();					     }
	t.m2();							}			
     }										this refers
}										outer class 
										variable only


** inside anonymous Inner class, is it possible to declare instance variables or not?   YES
     inside λ-expressions, is it possible to declare instance variables or not?   NO
     whatever variables declare inside λ-expressions, by default considered as local variable only.
** inside anonymous Inner class this always refers current inner class object only but inside
     λ-expressions this always refers current outer class object only.















Anonymous Inner Class	Lambda Expression
It is a class without name	It is a function without name (anonymous function)
anonymous inner class can extend abstract and concrete classes	λ-expression can’t extend abstract and concrete classes
anonymous inner class can implement an interface that contains any number of abstract methods 	λ-expression can implement an interface which contains single abstract method (Functional Interface)
Inside anonymous inner class, we can declare instance variables 	Inside λ-expression we can’t declare instance variables. Whatever variables declared, are considered as local variables
anonymous inner class can be instantiated	λ-expression can’t be instantiated
Inside anonymous inner class, this always refers current anonymous inner class object but not outer class object 	Inside λ-expression, this always refers current outer class object, i.e. enclosing class object
anonymous inner class is best choice if we want handle multiple methods	λ-expression is the best choice if we want to handle interface with single abstract method (Functional Interface)
For the anonymous inner class, at the time of compilation, a separate .class file will be generated 	For the λ-expression, at the time of compilation, no separate .class file will be generated
Memory will be allocated on demand whenever, we are creating object	λ-expression will reside in permanent memory of JVM(method area)

very important conclusion for λ-expression:-

interface Interf {
    public void m1();
}

class Test {
    int x = 10;
    public void m2() {		accessing enclosing
         int y = 20;			class variable directly
         Interf i = () -> { 
		          soapln(x);	     10
		          soapln(y);      20
		          x = 888;		fine to change the value
		          y = 999		CE: local variables referenced from λ-expression must be
		     };						final or effectively final.
         i.m1();			accessing enclosing 
    }				method variable directly 
    p s v main(String[] args) {
          Test t = new Test();			** from λ-expression, is it possible to access class
           t.m2();					      level variable or not? YES, we can access
     }						      enclosing class variables.
}
			** From λ-expression, is it possible to access local variables of enclosing?
			     Method or not? YES. No Problem. But local variables which are
Referenced from λ-expression must be final or effectively final. Hence within the λ-expression or outside λ-expression we can’t change the value of local variables which are referenced from λ-exp.
Advantages of λ-expression
1.	We can enable functional programming in java
2.	We can reduce length of the code so that readability will be improved
3.	We can resolve complexity of anonymous inner classes until some extent
4.	We can handle procedures/functions just like values
5.	We can pass procedure/functions as arguments
6.	Easier to use updated APIs and libraries
7.	Enable support for parallel programming

String s = “Durga”;

Interf i = () -> sopln(“Hello”);

new Thread(   () -> {
			  sopln(“child Thread”);
		            }
		);


































Default Methods and Static Methods in Interfaces
•	Until 1.7 version, we can take only abstract methods inside an interface. Every method inside present in interface is always public and abstract whether we are declaring or not.
** sometimes interface is also considered as 100% pure abstract class. Because it always
      contains only abstract methods.
** similarly, every variables present inside an interface is always public static final.
      Why?

Inside an interface it is not possible to keep any concrete method until 1.7 version

From Java 8 version onwards, we can declare concrete methods also inside an interface.
How and why?

The concrete methods which we can allow to declare inside an interface, these methods are by default considered as Default Methods. 

By default, keyword, we can declare/defined default method inside an interface.
This default method by default available to the implementation class. If the implementation class want to use, happily it can use. If the implementation class want to override, it can override. Both options are there.

interface Interf {			class Test implements Interf {
    default void m1() {			      p s v main(String[] args) {
         sopln(“default method”);			Test t = new Test();
    }						t.m1();
}					       }
					}

class Test implements Interf {
      public void m1() {				overriding m1()
	sopln(“my own implementation”);
      }
      
      p s v main(String[] args) {
	Test t = new Test();
	t.m1();
      }
}

** we can’t override Object class methods as default method.











Differences between Interface with Default methods and Abstract classes
default methods wrt multiple inheritance.
Some problem is there and how we can resolve the problem.

interface Left {				interface Left {
     default void m1() {			     default void m1() {
	soapln(“Left D M”);			soapln(“Right D M”);
      }					      }
}					}


									which m1() method?
			class Test implements Left, Right {		ambiguity problem in 
			      public void m1() {				the case of multiple
one way. 			sopln(“my own implementation”);	inheritance 
complete new 		      }
implementation	.	      
   			      public void m1() {
				// sopln(“my own implementation”);
				Left.super.m1();			
			      }				if we want Left interface default method
							m1() for the Test class.
			      public void m1() {
				// sopln(“my own implementation”);
				Right.super.m1();
			      }
			}				if we want Right interface default method
							m1() for the Test class.

What is if default methods may be a chance of ambiguity problem in case of multiple inheritance? How can we solve this problem?
Just simply override that default method in implementation class based on your requirement. While overriding if we want, we can provide completely new implementation or we can call parents interface method only. If we can call parent interface method, then we can call by…
	Left.super.m1(); 			Right.super.m1();


Difference B/W interface with default methods and Abstract classes
Interface with default method	Abstract class
Inside interface every variable is always public, static and final. We can’t declare instance variable	Inside Abstract class we can declare instance variables, which are required to the child class
Interface never talks about state of object	Abstract class can talk about state of object
Inside interface we can’t declare constructors	Inside abstract class we can declare constructors
Inside interface we can’t declare instance and static blocks	Inside abstract class we can declare instance and static blocks
Functional Interface with default methods can refer Lambda Expression	Abstract class can’t refer Lambda Expression
Inside interface we can’t override object class methods	Inside Abstract class we can override object class methods
Static Methods inside interfaces
From Java 1.8 version, we can declare static methods also inside an interface. Happily, we can declare static method inside interface.
What is the need? And how we can access?

** Static method no way related to an Object. Interface is also no way related to Object. Then what is the need of defining static method inside class.
If we want happily, we can define static method inside Interface. What is the purpose?

From 1.8 version onwards, just to define general utility methods and no way related to Object state.  Such type of general utility methods we can define inside interface in the form of static method.
These static utility methods can be called by just using by interface name.

interface interf {
    public static void m1() {
         sopln(“interface static method for utility”);
    }
}
 
class Test implements Interf {
    p s v main(String[] args) { 
          Test t = new Test();
           t.m1();
			X	interface static methods are not available 
           Test.m1();       X		in child/implementation class by default. So can’t call by
					using implementation class object reference or 
            Interf.m1();			implementation class name. get CE
     }
}

** Interface static method always can be called by using interface name only.

Interface static methods wrt overriding
Overriding concept not applicable for interface static methods but we can define exactly same static method. It is valid but not overriding.

interface Interf {			interface Interf {		interface Interf {
    public static void m1() {		     public static void m1() {	     public static void m1() {
          
     }					      }				      }
}					}				}

class Test implements Interf {	class Test implements Interf {       class Test implements Interf {
      public static void m1() {		  public void m1() {		    private static void m1() {
             
       }					      }				     }
}		correct			}		correct		}		correct

				In normal java classes gives CE because we can’t reduce scope of
				Access modifier while overriding. But in this case, it’s valid. Because 				it is not overriding concept. We can say method	hiding.		
From 1.8 version onwards, I can declare static methods inside interface. If we are allowed to declare static methods inside interface, why can’t we declare main method? If we can declare main method inside interface, why don’t we run interface directly from the command prompt?

Make sure this type of facility not available until 1.7 version. But from 1.8 version, I can declare main method inside interface, I can run interface directly from command prompt.

	interface Interf {
	     public static void main (String[] args) {
		sopln(“interface main method”);		 javac Interf.java
	     }	java Interf
	}







































Predefined Functional Interface – Predicate
There are some predefined functional interfaces (commonly used) already provided by Java peoples. We can use those predefined functional interfaces directly.

Predefined Functional Interfaces: -  defined in java.util.function package
•	Predicate
•	Function
•	Consumer
•	Supplier


Predefined Functional Interface – Predicate Part – 1
Predicate(I)	1.8 v
       means, perform some conditional check and returns true or false based on the condition.  
       In the normal mathematics, it is a mathematical function which returns Boolean value based on
       some condition. A boolean valued function is by default considered as Predicate in Mathematics.

test () is the abstract method

interface Predicate<T> {
      boolean test(T t);
}

public boolean test(Integer i) {		(Integer i) -> {			
     if(I > 10)				     if(i > 10)				
           return true;			           return true;	i -> I > 10;		
     else					     else				above λ-expression	
          return false;			           return false;		converted to Predicate
}					};			
								Predicate<Integer> p = i -> i > 10;
sopln(p.test(100));	true
sopln(p.test(5));		false

** compulsory we need to write import statement		sopln(p.test(“durga”));	CE
      java.util.finction.*;								             incompatible
										             type

							
to check 						to check ArrayList is empty or not ?
import java.util.finction.*;				Predicate<Collection> p = c -> c.isEmpty();
class Test {						ArrayList al1 = new ArrayList();
     p s v main(String[] args) {				al1.add(“A”);
          Predicate<String> p = s -> s.length() > 5;		sopln(“p.test(al1)”);		false
          sopln(“p.test(“abcdef”)”);		true		
          sopln(“p.test(“abc”)”);		false		ArrayList al2 = new ArrayList();
     }							sopln(“p.test(al2)”);		true
}


** In our programming, if anywhere conditional checking is required or conditional checking are
     There, happily we can implement by using Predicate 
Predicate joining: -
We can join multiple predicates in a single Predicate.
1.	P1 : given number greater than 10 ?
2.	P2 : is even number ?

P1.negate()		automatically condition will reverse.
			Check, whether the given number is not greater than 10 or not?
	P1.and(P2)		to check both condition
	P1. or(P2)		to check either condition

** negate(), and() and or()   these 3 methods are defined as default methods inside Predicate

public class Test {
     p s v main(String[] args) {
          	int[] x = {0, 5, 10, 15, 20, 25, 30};
	
	Predicate<Integer> p1 = i -> i > 10;
	Predicate<Integer> p2 = i -> i % 2 == 0;

	sopln(“the numbers greater than 10 are: ”);
	m1(p1, x);

sopln(“the even numbers are: ”);
m1(p2, x);

sopln(“the numbers not greater than 10: ”);
m1(p1.negate(), x);

sopln(“the numbers greater than 10 and even are: ”);
m1(p1.and(p2), x);

sopln(“the numbers greater than 10 or even are: ”);
m1(p1.or(p2), x);

p s v m1(Predicate<Integer> p, int[] x) {
      for(int x1 : x) {
          if(p.test(x1))
	sopln(x1);
      }
}
       }

Program to display names starts with ‘K’ by using Predicate

String[] names = {“Sunny”, “Kajal”, “Mallika”, “Katrina”, “Kareena”};
Predicate<String> startWithK = s -> s.chartAt(0) == ‘K’
for(String s : names) {
     if(startWithK.test(s)) {
	sopln(s);
     }
}
Predicate example to remove null values and empty String from the given List

String names = {“Durga”, “”, null, “Ravi”, “”, “Shiva”, null};

Predicate<String> p = s -> s != null && s.length() != 0;

ArrayList<String> al = new ArrayList<String>();
for(String s : names) {
     if(p.test(s))
          al.add(s);
}

sopln(“the list of valid Strings: ” + al);

Program for User Authentication by using Predicate:
import java.util.function.Predicate;
import java.util.Scanner;
class User {
  String username;
  String pwd;
  User(String username,String pwd) {
	this.username=username;
	this.pwd=pwd;
  }
}
class Test {
  public static void main(String[] args) {
	Predicate<User> p = u -> u.username.equals("durga") && u.pwd.equals("java");
	Scanner sc = new Scanner(System.in);
	System.out.println("Enter User Name:");
	String username = sc.next();
	System.out.println("Enter Password:");
	String pwd = sc.next();
	User user = new User(username,pwd);
	if(p.test(user)) {
	  System.out.println("Valid user and can avail all services");
	} else {
	  System.out.println("invalid user you cannot avail services");
	}   }   }

Program to check whether Software Engineer is allowed into pub or not by using Predicate?
import java.util.function.Predicate;
class SoftwareEngineer {
  String name;
  int age;
  boolean isHavingGf;
  SoftwareEngineer(String name,int age,boolean isHavingGf) {
	this.name = name;
	this.age = age;
	this.isHavingGf = isHavingGf;
  }
  public String toString() {
	return name;
  }
}

class Test {
  public static void main(String[] args) {
	SoftwareEngineer[] list = { new SoftwareEngineer("Durga",60,false),
		new SoftwareEngineer("Sunil",25,true),
		new SoftwareEngineer("Sayan",26,true),
		new SoftwareEngineer("Subbu",28,false),
		new SoftwareEngineer("Ravi",17,true)
	};
	Predicate<SoftwareEngineer> allowed = se -> se.age> = 18 && se.isHavingGf;
	System.out.println("The Allowed Members into Pub are:");
	for(SoftwareEngineer se : list) {
	  if(allowed.test(se)) {
		System.out.println(se);
	  }
    }
  }
}


Predicate interface isEqual() method

interface Predicate<T> {
      public boolean test(t);
      
      and()
      or()		   default methods
      negate()

      public static Predicate isEqual(T t);		static method return Predicates to check whether
					the given Object is this Object or not ? 
	this one can be used to check
	equality purpose. 

Predicate<String> p = Predicate.isEqual(“DurgaSoft”);
sopln(p.test(“DurgaSoft”));	true
sopln(p.test(“Mallika”));		false	

** where to use ? to represent a CEO, I will write one Predicate. To represent CEO object I will write
     one Predicate. Tomorrow whether this employee is CEO or not, if we want to check then
     automatically we can use this Predicate.

Predicate<Employee> p = Predicate.isEqual(new Employee (“Durga”, “CEO”, 3000, “Pune”)); 
sopln(p.test(e));	



Predefined Functional Interfaces - Function	
Function is exactly same as Predicate but only difference is it can return any type of value, not only boolean value. The Function can return Integer value, can return String value, can return Student Object, Customer Object… can return any type of value.
										input parameter
interface Predicate<T> {				interface Function<T, R> {
     boolean test(T t);				
}						     R apply(T t);			return type
			Input			}

						
						Function<String, Integer> f = s -> s.length();
sopln(f.apply(“durga”));		5
sopln(f.apply(“durgaSoft”));	9			Input		return		length of
							Type		type		string
											
Square:
Function<Integer, Integer> f = i -> i * i;

sopln(f.apply(5));	25
sopln(f.apply(10));	100


Predicate	Function
To implement conditional checks, we should got for Predicate	To perform certain operation and to return some result we should go for Function
Predicate can take one type of parameter which represents input argument type. Predicate<T>	Function can take 2 type parameters. First one represents input argument type and second one represents return type. Function<T, R>
Predicate interface defines one abstract method called test()	Function interface defines one abstract method called apply()
public boolean test(T t)	public R apply(T t)
Predicate can return only boolean value 	Function can return any type of value


















Function Chaining

f1       &      f2

f1.andThen(f2)		first f1 will applied followed by f2.
				both are default methods present inside Function interface. 
f1.compose(f2)		first f2 will applied followed by f1.

Function<String, String> f1 = s -> s.toUpperCase();

Function<String, String> f2 = s -> s.subString(0, 9);		0 to 9-1/first 9 char

sopln(f1.apply(“Aishwaryaabhi”));			AISHWARYAABHI
sopln(f2.apply(“Aishwaryaabhi”));			Aishwarya
sopln(f1.andThen(f2).apply(“Aishwaryaabhi”));		AISHWARYA
sopln(f2.compose(f1).apply(“Aishwaryaabhi”));		AISHWARYA


Demo program to demonstrate the difference between andThen() and compose()

Function<Integer, Integer> f1 = i -> i + i;

Function<Integer, Integer> f2 = i -> i * i * i;

sopln(f1.andThen(f2).apply(2));		4
					4*4*4		output: 64

sopln(f1.compose(f2).apply(2));		2*2*2
					8+8		output: 16







Function interface Static Method: identity ()

F identity () 		static method
		
What this static method going to do? Nothing. It will return a Function. What is the specialty of this Function is, whatever input we are giving, this Function is always going to provide same input as return type. 
		identity 	can you please return same thing

Very rare the people are going use.

Function<String, String> f = Function.identity();
sopln(f.apply(“Durga”));		output: Durga

static <T> Function<T, T> identity()	returns a function that always returns its input argument.
Predefined Functional Interfaces – Consumer

interface Predicate<T> {		interface Function<T, R> {
    boolean test(T t);		     R apply(T t);
}				}

Observe that, whether it is Predicate or Function, Compulsory it has to be return some value
Mandatory. boolean for Predicate. Any value type for Function.
But some time our requirement is, we will provide some value, performed some required operation but we are not expecting any return type.
Just we want a function to consume that values and perform certain operations.   

	interface Consumer<T> {			Consumer<String> c = s -> sopln(s);
 	     void accept(T t);				c.accept(“Hello”);
	              andThen()				c.accept(“Durga Soft”);
	}

Program to display Movie information by using Consumer:
Class Movie {
     String name;
     String hero;
     String heroine;

     Movie(String name, String hero, String heroine) {
	this.name = name;
	this.hero = hero;
	this.heroine = heroine;
      }
}
					Populate (AL<Movie> l) {
AL<Movie> al = new AL<Movie>();	       l.add(new Movie(“Bahubali”, “Prabhas”, “Anushka”));
populate(al); 				       l.add(new Movie(“PK”, “Aamir”, “Anushka Sharma”));
					       ………
					       ………
					}
Consumer<Movie> c = m -> {
      sopln(“Movie Name: ” + m.name);		the specialty of Consumer is, it never going to return 
      sopln(“Hero Name: ” + m.hero);		anything. It is always going to accept. Accept some
      sopln(“Heroine Name: ” + m.heroine);	item and perform certain activity.
};

for(Movie m: l) {
      c.accept(m);
}

Program to display Student information by using Predicate, Function and Consumer:
Class Student {
      String name;
      int marks;
      Student(String name, int marks) {
            this.name = name;	this.marks = marks;  }
Class Test {
     public static void main(String[] args) {
	ArrayList<Student> l = new ArrayList<Student>();
	Populate(l);
	Predicate<Student> p = s -> s.marks >= 60;
	Function<Student, String> f = s -> {		public static void populate(AL<Student> l) {
	       int marks = s.marks;				      l.add(new Student(“Sunny”, 100));
	       if(marks >= 80) {				      l.add(new Student(“Bunny”, 65));
		return “A[Distinction]”;			      l.add(new Student(“Chinny”, 55));
	       }						      l.add(new Student(“Vinny”, 45));
	       else if(marks >= 60) {			      l.add(new Student(“Pinny”, 25));
		return “B[First Class]”;
	       }
	       else if(marks >= 50) {
		return “C[Second Class]”;
	       }
	       else if(marks >= 35) {
		return “D[Third Class]”;
	       }
	       else {
		return “E[Failed]”;
	       }
	};
	Consumer<Student> c = s -> {
	       sopln(“Student Name: ” + s.name);		sopln(“Student Marks: ” + s.marks);
	       sopln(“Student Grade: ” + f.apply(s));	System.out.println();
	};
	for(Student s : l) {
	     if(p.test(s))
		c.accept(s);
	}
       }
}

Consumer Chaining:
For Consumer chaining there is one default method andThen() in Consumer Functional Interface.

c1.andThen(c2).andThen(c3).accept(m);


Notification			Result			Store in Database

assume if m is the movie object, then first c1 meant for to give notification (i.e. so and so movie ready to release). Then second Consumer i.e. c2 is responsible to provide result of that movie. i.e. Hit or flop. Then Consumer c3 is responsible to store that movie information in the database. 
All the 3 Consumers work for the same movie object m one by one. 

Class Movie {
     String name;		String result;
     Movie (String name, String result) {
           this.name = name;	this.result = result;  }  }
Consumer<Movie> c1 = m -> sopln(“Movie: ” + m.name);
Consumer<Movie> c2 = m -> sopln(“Movie: ” + m.name + “is” + m.result);
Consumer<Movie> c3 = m -> sopln(“Movie: ” + m.name + “information storing in database”);
Consumer<Movie> chainedC = c1.andThen(c2).andThen(c3);

Movie m = new Movie (“Bahubali”, “Big Hit”);
chainedC.accept(m);

Movie m2 = new Movie(“Spider”, “Flop”);
chainedC.accept(m2);

Predefined Functional Interfaces – Supplier
Supplier Introduction:
Use Supplier to get something. I don’t want to provide input.
Can you please tell system date? Can you please return Student Object?
Can you please return some random password? Can you please return some random OTP?
Means we want to get something without providing any input.
Then we should go for Supplier.
	interface  Supplier<R> {				this Supplier interface doesn’t contain 
	      R get();					any default methods and any static methods
	}			return type		that’s why chaining of supplier is not possible.

Program to get System Date by using Supplier:
public Class Test {
      public static void main(String[] args) {
	Supplier<Date> s = () -> new Date();
	System.out.println(s.get());
System.out.println(s.get());
System.out.println(s.get());
      }
}

Program to get Random Name by using Supplier:
public Class Test { 
      public static void main(String[] args) {
	Supplier<String> s = () -> {
	     String[] s1 = {“Sunny”, “Bunny”, “Chinny”, “Pinny”};
	     int x = (int)(Math.random() * 4);
	     return s1[x]; 
	};
	System.out.println(s.get());
	System.out.println(s.get());
	System.out.println(s.get());
      }
}

Program to get Random OTP by using Supplier:
public Class Test {					for(int i = 1; i <= 6; i++) {
      public static void main(String[] args) {		      otp = otp + (int)(Math.random() * 10);
	Supplier<String> otps = () -> {			      return otp;	};
	     String opt = “”;				sopln(otps.get());      }	}
Program to get Random Password by using Supplier: 
public Class Test {					     Rules:Length should be 8 characters
    public static void main (String[] args) {			   	   2,4,6,8 places only digits
        Supplier<Integer> d = () -> (int)(Math.random()*10);	   1,3,5,7 places only upper case 
        String = symbols = “ABCDEFGHIJKLMNOPQRSTUVWXYZ#$@”;    alphabet symbols + @ # $
        Supplier<Character> c = () -> symbols.charAt((int)(Math.random()*29));
        String pwd = “”;
        for(int i = 1; i <= 8; i++) {
	if(i % 2 == 0) {
	     pwd = pwd + d.get();				* this Supplier interface doesn’t contain
	}						   any default methods and any static 
	else {						   methods, that’s why chaining of supplier is 
	     pwd = pwd + c.get();				   not possible.
	}
         }
         Return pwd;
    };
    System.out.println(s.get());		System.out.println(s.get());
    System.out.println(s.get());		System.out.println(s.get());
    System.out.println(s.get());		System.out.println(s.get());
}

 






Two-Argument (Bi) Functional Interfaces:
Need of Two-Argument (Bi) Functional Interfaces:
Normal Functional Interfaces (Predicate, Function and Consumer) can accept only one input
Argument. But sometimes our programming requirement is to accept two input arguments, then
We should go for two-argument functional interfaces. The following functional interfaces can take
2 input arguments.
1.	BiPredicate
2.	BiFunction
3.	BiConsumer

BiPredicate:
Normal Predicate can take only one input argument and perform some conditional check. Sometimes our programming requirement is we have to take 2 input arguments and perform some conditional check, for this requirement we should go for BiPredicate.

BiPredicate is exactly same as Predicate except that it will take 2 input arguments.
interface BiPredicate<T1, T2>  {
public boolean test(T1 t1, T2 t2);
//remaining default methods: and(), or() , negate()
}

To check the sum of 2 given integers is even or not by using BiPredicate :
import java.util.function.*;
class Test {
public static void main(String[] args)  {
BiPredicate<Integer,Integer> p = (a,b) -> (a + b) %2 == 0;
System.out.println(p.test(10,20));
System.out.println(p.test(15,20));
}
}

Output:
true
false

BiFunction: 
Normal Function can take only one input argument and perform required operation and returns the result. The result need not be boolean type.
But sometimes our programming requirement to accept 2 input values and perform required operation and should return the result. Then we should go for Bifunction.
Bifunction is exactly same as function except that it will take 2 input arguments.
interface BiFunction<T, U, R>  {
public R apply(T t, U u);
//default method andThen()
}

To find product of 2 given integers by using BiFunction:
import java.util.function.*;
class Test  {
    public static void main(String[] args)  {
BiFunction<Integer, Integer, Integer>  f = (a, b)  -> a * b;
System.out.println(f.apply(10,20));
System.out.println(f.apply(100,200));
}
}

To create Student Object by taking name and rollno as input by using BiFunction:
import java.util.function.*;
import java.util.*;
class Student {
String name;
int rollno;
Student(String name, int rollno) {
this.name = name;
this.rollno = rollno;
}
}
class Test {
public static void main(String[] args) {
ArrayList<Student> l = new ArrayList<Student>();
BiFunction<String, Integer, Student> f = (name, rollno) -> new Student(name, rollno);

l.add(f.apply("Durga", 100));
l.add(f.apply("Ravi", 200));
l.add(f.apply("Shiva", 300));
l.add(f.apply("Pavan", 400));
for(Student s : l) {
System.out.println("Student Name:" + s.name);
System.out.println("Student Rollno:" + s.rollno);
System.out.println();
}
}
}
Output:

Student Name:Durga	   Student Name:Ravi	     Student Name:Shiva	    Student Name:Pavan	
Student Rollno:100	   Student Rollno:200	     Student Rollno:300	    Student Rollno:400

To calculate Monthly Salary with Employee and TimeSheet objects as input By using BiFunction:
import java.util.function.*;
import java.util.*;
class Employee {
int eno;
String name;
double dailyWage;
Employee(int eno, String name, double dailyWage)  {
this.eno = eno;
this.name = name;
this.dailyWage = dailyWage;
	   }
}
class TimeSheet {
int eno;
int days;
TimeSheet(int eno, int days)  {
this.eno = eno;
this.days = days;
}
}
class Test  {
public static void main(String[] args)  {
BiFunction<Employee, TimeSheet, Double> f = (e, t) -> e.dailyWage * t.days;
Employee e = new Employee(101, "Durga", 1500);
TimeSheet t = new TimeSheet(101, 25);
System.out.println("Employee Monthly Salary:" + f.apply(e,  t));
}
}
	Output: Employee Monthly Salary:37500.0

BiConsumer:
Normal Consumer can take only one input argument and perform required operation and won't return any result.
But sometimes our programming requirement to accept 2 input values and perform required operation and not required to return any result. Then we should go for BiConsumer.
BiConsumer is exactly same as Consumer except that it will take 2 input arguments.

interface BiConsumer<T, U>  {
public void accept(T t, U u);
//default method andThen()
}

Program to accept 2 String values and print result of concatenation by using BiConsumer:
import java.util.function.*;
class Test {
public static void main (String[] args) {
BiConsumer<String, String> c = (s1, s2) ->  System.out.println(s1 + s2);
c.accept("durga", "soft");
}
}
Output:     durgasoft

Demo Program to increment employee Salary by using BiConsumer:
import java.util.function.*;
import java.util.*;
class Employee {
String name;
double salary;
Employee(String name, double salary)  {
this.name = name;
this.salary = salary;
}
}
class Test  {
public static void main(String[] args)  {
ArrayList<Employee> l = new ArrayList<Employee>();
populate(l);
BiConsumer<Employee,Double> c = (e, d) -> e.salary = e.salary + d;
for(Employee e : l)  {
c.accept(e, 500.0);
}
for(Employee e : l)  {
System.out.println("Employee Name:" + e.name);
System.out.println("Employee Salary:" + e.salary);
System.out.println();
}
}
public static void populate(ArrayList<Employee> l)  {
l.add(new Employee("Durga", 1000));
l.add(new Employee("Sunny", 2000));
l.add(new Employee("Bunny", 3000));
l.add(new Employee("Chinny", 4000));
}
}

Comparison Table between One argument and Two argument Functional Interfaces:
 


Primitive Type Functional Interfaces, Unary Operator and Binary Operator
Primitive Type Functional Interfaces
What is the Need of Primitive Type Functional Interfaces?
1. Autoboxing:
Automatic conversion from primitive type to Object type by compiler is called autoboxing.
2. Auto unboxing:
Automatic conversion from object type to primitive type by compiler is called auto unboxing.

 

3.	In the case of generics, the type of parameter is always object type and no chance of passing primitive type.

ArrayList<Integer> i = new ArrayList<Integer>();	//valid ArrayList<int> i = new ArrayList<int>();	//invalid

Need of Primitive Functional Interfaces:
In the case of normal Functional interfaces (like Predicate, Function etc) input and return types are always Object types. If we pass primitive values then these primitive values will be converted to Object type (Auboxing), which creates performance problems.

import java.util.function.Predicate;
class Test  {
public static void main(String[] args)  {
 	        int[] x = {0,5,10,15,20,25};
Predicate<Integer> p = I -> i%2 == 0;
for (int x1 :  x)  {
if(p.test(x1))  {
System.out.println(x1);
}
}
}
}

In the above examples,6 times autoboxing and autounboxing happening which creates performance problems.
To overcome this problem primitive functional interfaces introduced, which can always takes primitive types as input and return primitive types. Hence autoboxing and auto unboxing won't be required, which improves performance.

Primitive Type Functional Interfaces for Predicate:
The following are various primitive Functional interfaces for Predicate.
1.	IntPredicate --> always accepts input value of int type
2.	LongPredicate --> always accepts input value of long type
3.	DoublePredicate --> always accepts input value of double type

1.
interface IntPredicate {
public boolean test(int i);
//default methods: and(),or(),negate()
}

       2.
interface LongPredicate {
public boolean test(long l);
//default methods: and(),or(),negate()
}

       3.
interface DoublePredicate {
public boolean test(double d);
//default methods: and(),or(),negate()
}

Demo Program for IntPredicate:
import java.util.function.IntPredicate;
class Test  {
public static void main(String[] args) {
int[] x = {0,5,10,15,20,25};
IntPredicate p = I -> i%2 == 0;
for (int x1 : x) {
if(p.test(x1)) {
System.out.println(x1);
}
}
}
}

In the above example, autoboxing and autounboxing won't be performed internally. Hence performance wise improvements are there.
Primitive Type Functional Interfaces for Function:
The following are various primitive Type Functional Interfaces for Function
1.	IntFunction: can take int type as input and return any type public R apply(int i);

2.	LongFunction: can take long type as input and return any type public R apply(long i);

3.	DoubleFunction: can take double type as input and return any type public R apply(double d);

4.	ToIntFunction: It can take any type as input but always returns int type public int applyAsInt(T t)
5.	ToLongFunction: It can take any type as input but always returns long type public long applyAsLong(T t)

6.	ToDoubleFunction: It can take any type as input but always returns double type public int applyAsDouble(T t)

7.	IntToLongFunction: It can take int type as input and returns long type
public long applyAsLong(int i)
8.	IntToDoubleFunction: It can take int type as input and returns long type public double applyAsDouble(int i)

9.	LongToIntFunction: It can take long type as input and returns int type public int applyAsInt(long i)

10.	LongToDoubleFunction: It can take long type as input and returns double type public int applyAsDouble(long i)

11.	DoubleToIntFunction: It can take double type as input and returns int type public int applyAsInt(double i)

12.	DoubleToLongFunction: It can take double type as input and returns long type public int applyAsLong(double i)

13.	ToIntBiFunction:return type must be int type but inputs can be anytype public int applyAsInt(T t, U u)

14.	ToLongBiFunction:return type must be long type but inputs can be anytype
public long applyAsLong(T t, U u)
15.	ToDoubleBiFunction: return type must be double type but inputs can be anytype public double applyAsDouble(T t, U u)

Demo Program to find square of given integer by using Function:
import java.util.function.*;
class Test {
public static void main(String[] args) {
IntFunction<Integer,Integer> f = I -> i*i;
System.out.println(f.apply(4));
}
}

Demo Program to find square of given integer by using IntFunction:
import java.util.function.*;
class Test {
public static void main(String[] args) {
IntFunction<Integer> f = I -> i*i;
System.out.println(f.apply(5));
}
}
Demo Program to find length of the given String by using Function:
import java.util.function.*;
class Test {
public static void main(String[] args) {
Function<String,Integer> f = s -> s.length();
System.out.println(f.apply("durga"));
}
}

Demo Program to find length of the given String by using ToIntFunction:
import java.util.function.*;
class Test {
public static void main(String[] args) {
ToIntFunction<String> f = s -> s.length();
System.out.println(f.applyAsInt("durga"));
}
}

Demo Program to find square root of given integer by using Function:
	import java.util.function.*;
class Test {
public static void main(String[] args) {
Function<Integer,Double> f = I -> Math.sqrt(i);
System.out.println(f.apply(7));
}
}
       --------------------------------------------
import java.util.function.*;
class Test {
public static void main(String[] args) {
IntToDoubleFunction f = I -> Math.sqrt(i);
System.out.println(f.applyAsDouble(9));
}
}

Primitive Version for Consumer:
The following 6 primitive versions available for Consumer:
1. IntConsumer
public void accept(int value)

2. LongConsumer
public void accept(long value)

3. DoubleConsumer
public void accept(double value)

4. ObjIntConsumer<T>
public void accept(T t,int value)

5. ObjLongConsumer<T>
public void accept(T t,long value)

6. ObjDoubleConsumer<T>
public void accept(T t,double value)


Demo Program for IntConsumer:
import java.util.function.*;
class Test {
public static void main(String[] args) {
IntConsumer c = I -> System.out.println("The Square of i:" + (i*i));
c.accept(10);
}
}		Output: The Square of i:100

Demo Program to increment employee Salary by using ObjDoubleConsumer:
import java.util.function.*;
import java.util.*;
class Employee {
String name;
double salary;
Employee(String name, double salary) {
    this.name = name;
this.salary = salary;
}
}
class Test {
public static void main(String[] args) {
ArrayList<Employee> I = new ArrayList<Employee>();
populate(l);
ObjDoubleConsumer<Employee> c = (e, d) -> e.salary = e.salary + d;
for(Employee e : l) {
c.accept(e, 500.0);
}
for(Employee e : l) {
System.out.println("Employee Name:" + e.name);
System.out.println("Employee Salary:" + e.salary);
System.out.println();
}
}
public static void populate(ArrayList<Employee> l) {
l.add(new Employee("Durga",1000));
l.add(new Employee("Sunny",2000));
l.add(new Employee("Bunny",3000));
l.add(new Employee("Chinny",4000));
}
}

Output:
Employee Name:Durga		Employee Name:Sunny
Employee Salary:1500.0	Employee Salary:2500.0

Employee Name:Bunny		Employee Name:Chinny
Employee Salary:3500.0	Employee Salary:4500.0




Primitive Versions for Supplier:
The following 4 primitive versions available for Supplier:
1. IntSupplier
public int getAsInt();

2. LongSupplier
public long getAsLong()

3. DoubleSupplier
public double getAsDouble()

4. BooleanSupplier
public boolean getAsBoolean()

Demo Program to generate 6 digit random OTP by using IntSupplier:
import java.util.function.*;
class Test {
public static void main(String[] args) {
IntSupplier s = () -> (int)(Math.random()*10);
String otp = "";
for(int i =0; I < 6; i++) {
otp = otp + s.getAsInt();
}
System.out.println("The 6 digit OTP: "+otp);
}
}		Output: The 6 digit OTP: 035716


UnaryOperator<T>:
⚽	If input and output are same type then we should go for UnaryOperator
⚽	It is child of Function<T,T>

import java.util.function.*;
class Test {
public static void main(String[] args) {
Function<Integer, Integer> f = i -> i * i;
System.out.println(f.apply(5));
}
}

import java.util.function.*;
class Test {
public static void main(String[] args) {
UnaryOperator<Integer> f = i -> i*i;
System.out.println(f.apply(6));
}
}

The primitive versions for UnaryOperator:
IntUnaryOperator:
public int applyAsInt(int)

LongUnaryOperator:
public long applyAsLong(long)

DoubleUnaryOperator:
public double applyAsDouble(double)

Demo Program-1 for IntUnaryOperator:
import java.util.function.*;
class Test {
public static void main(String[] args) {
IntUnaryOperator f = i -> i*i;
System.out.println(f.applyAsInt(6));
}
}

Demo Program-2 for IntUnaryOperator:
import java.util.function.*;
class Test {
public static void main(String[] args) {
IntUnaryOperator f1 = i -> i + 1;
System.out.println(f1.applyAsInt(4));

IntUnaryOperator f2 = i -> i*i;
System.out.println(f2.applyAsInt(4));

System.out.println(f1.andThen(f2).applyAsInt(4));

}
}		
Output:
5
16
25

BinaryOperator:
It is the child of BiFunction<T, T, T>

BinaryOperator<T> 
   public T apply(T, T)

import java.util.function.*;
class Test {
public static void main(String[] args) {
BiFunction<String,String,String> f = (s1, s2) -> s1 + s2;
System.out.println(f.apply("durga","software"));
}
}

import java.util.function.*;
class Test {
public static void main(String[] args) {
BinaryOperator<String> b = (s1, s2) -> s1 + s2;
System.out.println(b.apply("durga","software"));	}    }
The primitive versions for BinaryOperator:
1. IntBinaryOperator
public int applyAsInt(int i,int j)

2. LongBinaryOperator
public long applyAsLong(long l1,long l2)

3. DoubleBinaryOperator
    public double applyAsLong(double d1,double d2)

import java.util.function.*;
class Test {
public static void main(String[] args) {
BinaryOperator<Integer> b = (i1, i2) -> i1 + i2;
System.out.println(b.apply(10,20));
}
}

import java.util.function.*;
class Test {
public static void main(String[] args) {
IntBinaryOperator b = (i1, i2) -> i1 + i2;
System.out.println(b.applyAsInt(10,20));
}
}



























Method and Constructor Reference By Using :: Operator
What we covered…
1.	λ-expression
2.	Functional Interface
3.	Default Methods inside interface
4.	Static Methods inside interface
5.	java.util.funation

Predicate			BiPredicate
Function			BiFunction
Consumer			BiConsumer
Supplier


::	in java we can use this operator for method reference and constructor reference.

	What is the need:?
	What is the meaning of:: ?
	How we can implement and what is the advantage :: ?

interface Interf {					interface Interf {
     public void m1();					     public void m1();
}							}			can be static or
										instance method
class Test {						class Test {
     p s v main(String[] args) {				     public static void m2() {
           Interf  i = () -> {						sopln(“method refrence”);
		            sopln(“λ-expression”);			;  ;   ;   ;   ;
		              ;  ;  ;				     }
		        };					     p s v main(String[] args) {
           i.m1();						          Interf i = Test : : m2;
     }							          i.m1();
}		      provide m1() implementation	     }
		      by λ-expression			}		it will executed m2()
									internally

In Java 1.8, we have another alternative is there to provide implementation on above (alternative of λ-expression implementation). That alternative is method reference:
Instead of λ-expression, we can go for method reference:

 But if we have requirement, is we don’t want to implement m1 (). Because whatever implementation is required for m1 () is already there in m2 (). We want to use this m2 () method for m1 () purpose. What we will do is, don’t implement m1() method separately, just refer m1() to m2(). Whenever we are asking for m1(), m1() internally refer to m2(). That is method reference.
 
Observed that in λ-expression, we are providing completely new implementation but, in the method, reference we are not required to provide new implementation. Already existing method happily, we are allowed to use. 

Biggest advantage is code reusability. 
If we want to use method reference, compulsory both methods should have same argument type.
A functional interface can refer λ-expression.
A functional interface can refer method reference also.			     λ-expression	
That’s why λ-expression concept can replace with
Method reference.						FI
									      method reference
Method reference can be alternative syntax of λ-expression.

Syntax for method reference:
Static:
Classname : :  method name		e.g. 	Test : :  m2

Instance:
	Object ref::  method name		e.g. 	Test t = new Test();
							t : :  m2

by using method reference, how can we start a new thread ?
with λ-expression					    with method reference
public class Test {					    public class Test {
      p s v main(String[] args) {				          public void m1() {
            Runnable r = () -> {				              for((int i = 0; i < = 10; i++) {
			    for(int i = 0; i < = 10; i++) {		     (int i = 0; i < = 10; i++
			         sopln(“child thread”);		}
			      }				           }
			  };				           p s v main(String[] args) {
								Test t = new Test();
          Thread t = new Thread(r);					Runnable r = t : : m1;
          t.start();							Thread t = new Thread(r);
          for(int i = 0; i < = 10; i++) {					t.start();
     	sopln(“main thread”);					for(int i = 0; i < = 10; i++) {
          }								    sopln(“main thread”);
     }								}
}			compulsory we have provide	           }		but in method reference
			new implementation in case	      }		we are not providing any
			of λ-expression				       new implementation. Already
								available m1() implementation we
								are using. i.e. code reusability.

Constructor reference by double colon(: :) operator:

class sample {									method reference
     sample() {										
           sopln(“sample constructor execution & object creation”);
      }       					              				   : :
}											
interface Interf {										
     public Sample get();							        constructor reference
}

with λ-expression					with method reference
class Test {						class Test {
     p s v main(String[] args) {				     p s v main(String[] args) {
      Interf i = () -> {					     Interf I = Sample : : new;
		    Sample s = new Sample();		     Sample s = i.get();
		    return s;				   }		     
		  };					}		it refer Sample class
     Sample s1 = i.get();							constructor. Sample class
  }								      constructor will create object &
}								      returns that object.









































Streams:
Stream is the new concept which came in the 1.8 version. Most valuable concept.
Where we can use? where you can apply ?

What is Stream?
We have already   java.io Streams are there. InputStream, OutputStream, FileInputStream,
FileOutputStream like we have io stream concept.
Now we have talking about 1.8 v Streams. Then what is the difference?		write
Both concepts are different concepts. Then…
What is the purpose of java.io Streams - 
Write some data (text, binary) to the file.					    read
Want to read some data from the file.							    abc.txt
If we want to be represented a data (character or binary data) w r t File operation, then we should go for java.io Streams concepts.
Streams… A sequence of characters or binary data. 

1.8 v Streams is no way related to java.io Streams. Input stream, output stream such type of terminology is not applicable for 1.8 v Streams concept.

This 1.8 v Streams concept is applicable for Collection objects. It is related to Collections.
If we want to perform certain operations related to the Collection then we should go for Streams.

If we want to process these objects from collection then we should go for
1.8 v Streams concept.
Like suppose 1000 students are there. Then can you please list out
All the students whose marks are greater than 80%.					
So it the process on every object from collection.
		
AL<Integer> al = new AL<Integer>();
al.add(0);	al.add(10);	al.add2(0);	al.add(5);
al.add(15);	al.add(25);	
										       Collection
sopln(al);	[0, 10, 20, 5, 15, 25]

without Streams(until 1.7) style of coding…	     
List<Integer> l1 = new AL<Integer>();		     
for(Integer i1 : al) {
      If(i1 % 2 == 0 )										
	l1.add(i1);
}
sopln(l1);	[0, 10, 20]

with Streams concept(from 1.8 v)…
List<Integer> l1 = al.stream().filter(i -> i % 2 == 0).collect(Collectors.toList());
sopln(l1);	[0, 10, 20]





without Streams(until 1.7) style of coding…	     
List<Integer> l1 = new AL<Integer>();		     
for(Integer i1 : al) {
     l1.add(i1 * 2);
}
sopln(l1);	[0, 20, 40, 10, 30, 50]

with Streams concept(from 1.8 v)…
List<Integer> l1 = al.stream().map(i -> i * 2).collect(Collectors.toList());
sopln(l1);	[0, 20, 40, 10, 30, 50]


Difference between filter() and map()

Stream s = c.stream();	          This method present inside Collection
							          Interface as default method
It is an interface present in 	     any collection object
java.util.Stream package

Stream s = c.stream();
Once we create stream object, we can process Collection objects by using this Stream s object.
Then that processing contains two steps/stage…
1.	Configuration	             Filter mechanism 
            Map mechanism
2.	Processing

in the case of filter the result means, the number
of objects because of filter is less than original		  Filter only even
objects.                   					  Numbers
But by Map whatever the result what we are going
to get w r t number, original object and mapped 
object compulsory should contain same number of			for every object we want to
object. For every object a new object generated				create a mapped object
based on some new function.						based on some function
  									
Filtering						    Mapping
* if we want to filter some elements from the collection	 * if we want to create a separate new   	
   based on some boolean condition, then we should go	     object for every object present in the	
   for filtering.						     Collection, based one some function then
* if we configure Filter by using filter() method of	     we should go for mapping mechanism.
   Stream interface.					     We can implement mapping by using
   public stream Filter(Predicate<T> t)			     map() method of Stream interface.													
		it can be boolean valued		      public Stream map(Function<T, R> f)
function or λ-expression						
e.g:							       e.g:		
Stream s1 = c.stream().filter(i -> i % 2 == 0);		       Stream s1 = c.stream().map(i -> i * 2);								



Now how can we process objects by using Streams? What are various methods are there for that ?
Suppose if we configure a filter or configure a map. Once we configure filter or map, how we can do processing.
Stream interface defines several methods for our convenience purpose, based on our requirement we can use appropriate method.
1.	Processing by collect() method:
This method collects the elements from the Stream and adding to the specified Collection.
e.g.   AL<String> al = new AL<String>();
          al.add(“Pavan”);  		al.add(“RaviTeja”);	al.add(“Chiranjeevi”);
          al.add(“Venketesh”);	al.add(“Nagarjuna”);
	          sopln(al);	[Pavan, RaviTeja, Chiranjeevi, Venketesh, Nagarjuna]
	
	          List<String> l1 = al.stream().filter(s -> s.length() >= 9).collect(Collectors.toList());
	          sopln(l1);		[Chiranjeevi, Venketesh, Nagarjuna]															
	uppercase for every elements:
	           List<String> l2 = al.stream().map(s -> s.toUpperCase()).collect(Collectors.toList());	
	           sopln(l2);		[]
	
2.	Processing by count() method:
This method returns the number of elements present in Stream.
	public long count()
	e.g.
	AL<String> al = new AL<String>();
al.add(“Pavan”);  		al.add(“RaviTeja”);		al.add(“Chiranjeevi”);
al.add(“Venketesh”);		al.add(“Nagarjuna”);
	sopln(al);	[Pavan, RaviTeja, Chiranjeevi, Venketesh, Nagarjuna]

	long count = al.stream().filter(s -> s.length() >= 9).count();
	sopln(count);

3.	Processing by sorted() method:
* We can use sorted() method to sort elements inside stream.
* We can sort either based on default natural sorting order or based on our customized
    Sorting order specified by comparator object.

1.	sorted() => for default natural sorting order 
2.	sorted(Comparator c) => for customized sorting order

e.g.
AL<Integer> al = new AL<Integer>();
al.add(0);	al.add(10);	al.add(20);	al.add(5);	al.add(15);	al.add(25);
sopln(al);	[0, 10, 20, 5, 15, 25]

List<Integer> l1 = al.stream().sorted().collect(Collectors.toList());
sopln(l1);	[0, 5, 10, 15, 20, 25]

List<Integer> l2 = al.stream().sorted((i1, i2) -> - i1.compareTo(i2)).collect(Collectors.toList());
sopln(l2);	[25, 20, 15, 10, 5, 0]


Various methods of Stream:
1.	collect()
2.	count()
3.	sorted()
4.	min() 	&
max()
5.	forEach()
6.	toArray()

Processing by min() and max() methods:
1.	min(Comparator c)
returns minimum value according to specified comparator.

2.	max(Comparator c)
returns maximum value according to specified comparator.

	AL<Integer> al = new AL<Integer>();
	al.add(0);	al.add(10);	al.add(20);	al.add(5);	al.add(15);	al.add(25);
	sopln(al);	[0, 10, 20, 5, 15, 25]
	
	Integer min = al.stream().min((i1, i2) -> i1.compareTo(i2)).get();
	solpn(min);	0

	Integer max = al.stream().max((i1, i2) -> i1.compareTo(i2)).get();
	solpn(max);	25

Processing by using forEach() methods:	
* This method wouldn’t return anything.
* This method can take λ-expression as an argument and apply that λ-expression for each element
     Present in Stream.
    
     e.g.
	AL<String> al = new AL<String>();
	al.add(“A”);	al.add(“BB”);	al.add(“CCC”);

	al.stream().forEach(s -> sopln(s));
						can replace with ::			
	al.stream().forEach(System.out :: println);


Processing by toArray() method:
We can use toArray() method to copy elements present in the Stream into specified array.
e.g.
	AL<Integer> al = new AL<Integer>();
	al.add(0);	al.add(10);	al.add(20);	al.add(5);	al.add(15);	al.add(25);
	sopln(al);	[0, 10, 20, 5, 15, 25]

	Integer[] array = al.stream().toArray(Integer[] :: new);
	for(Integer x : array) {
	     sopln(x);
	}
Stream.of()  method: 
* We can also apply Stream for group of values & arrays.

						9, 99, 999, 9999, 99999		group of value

						Double[] d = {10.0, 10.1, 10.2, 10.3, 10.4, 10.5};
1.	for group of values:
Stream<Integer> s = Stream.of(9, 99, 999, 9999, 99999);
s.forEach(System.out :: println);

2.	for Arrays:
Double[] d = {10.0, 10.1, 10.2, 10.3, 10.4, 10.5};
	Stream<Double> s1 = Stream.of(d);
	s1.forEach(System.out :: println);


Summary:
1.	purpose
2.	Stream s = c.stream();
3.	filter(Predicate<T> t);
4.	map(Function<T, R> f);

1.	collect()				forEach(  )
2.	count()				toArray()
3.	sorted()
sorted(Comparator c)		
4.	min(Comparator c)			Stream.of()
5.	max(Comparator c)	
					






















Date and Time API
Date and Time API Introduction:
Why a new API came in 1.8 v ?
until 1.7 v to handle Date and Time value we have several classes are there. 
Date	  Calender	TimeStamp
	But these classes are not that much convenient to use w r t performance w r t convenience.
	Most methods are deprecated and not recommended to use.
So to handle Date and Time very effectively in Java 1.8 v a new API is introduced.
This API is defined several classes and interfaces to handle Date and Time very effectively.
This concept is very to use also.
This is also known as Joda Time API. This API is developed by joda.org

		LocalDate  date = LocalDate.now();
to handle	sopln(date);	2017-07-16
local date							present in java.timepackage
		LocalTime time = LocalTime.now();		so import above package in code.
		sopln(time);
to handle
local time

LocalDate  date = LocalDate.now();
sopln(date);
int  dd = date.getDayOfMonth();	find day value
int mm = date.getMonthValue();	find month value
int yyyy = date.getYear();		find year value

sopln(dd + “…” + mm + “…” + yyyy);			16…7…2017
System.out.printf(“%d - %d - %d”, dd, mm, yyyy);	17-7-2017


LocalTime time = LocalTime.now();							getHour()
sopln(time);										
int h = time.getHour();									getMinute()
int m = time.getMinute();						 time
int s = time.getSecond();								getSecond()
int n = time.getNano();										
											getNano()
System.out.printf(“%d : %d : %d” : %d, h, m, s, n);


LocalDateTime

LocalDateTime  dt = LocalDateTime.now();
sopln(dt);		print date and time as well

int  dd = dt.getDayOfMonth();				int h = dt.getHour();
int mm = dt.getMonthValue();				int m = dt.getMinute();
int yyyy = dt.getYear();					int s = dt.getSecond();
System.out.printf(“%d - %d - %d”, dd, mm, yyyy);	int n = dt.getNano();
						             	System.out.printf(“%d:%d:%d”:%d,h,m,s,n);

LocalDateTime  dt = LocalDateTime.of(yy, mm, dd, h, m, s, n);

LocalDateTime  dt = LocalDateTime.of(1995, 05, 28, 12, 45);	1995-05-28T12:45
sopln(dt);
							Month.MAY
sopln(“after six month : ” + dt.plusMonths(6));
			      dt.plusWeeks(5)
			      dt.plusDays(20)

sopln(“before six month : ” + dt.minusMonths(6));


Date and Time API Introduction Perod & Year:
Period:
	What is your age. To know the age between today and birthday how many days are there ?
how many years are there ? how many months are there ?

LocalDate birthday = LocalDate.of(1989, 8, 28);
	LocalDate today = LocalDate.now();

	Period p = Period.between(birthday, today);
	System.out.printf(“your age is %d Years %d Months and %d Days”, p.getYears(),
p.getMonths, p.getDays());

	LocalDate expiryday = LocalDate.of(1989+60, 8, 28);
	Period p1 = Period.between(today, expiryday);
int d = p1.getYears()*365 + p1.getMonths()*30 + p1.getDays();
System.out.printf(“\n you will be on earth only %d Days,Hurry up to do more imp things”, d);

Year:
	Write a program to check whether this year is leap year or not by using this Year class ?
	Scanner sc = Scanner(System.in);
	sopln(“Enter your year: ”);
int n = sc.nextInt();
Year y = Year.of(n);
if(y.isLeap()) 
    System.out.printf(“%d Year is Leap year”, n);
	else
	    System.out.printf(“%d Year is not a Leap year”, n);

ZoneId and ZonedDateTime
ZoneId zone = ZoneId.systemDefault();
sopln(zone);
							more readymade things are
	ZoneId la = ZoneId.of(“America/Los-Angles”);		available in Java 1.8 v
	ZonedDateTime zdt = ZonedDateTime.now(la);
	sopln(zdt);




What is intermediate operation and terminal operation in Java 8 ?
Intermediate operations act as a declarative (functional) description of how elements of the Stream should be transformed. Together, they form a pipeline through which the elements will flow. What comes out at the end of the line naturally depends on how the pipeline is designed.

Rules:
Java 8 Stream intermediate operations return another Stream which allows you to call multiple operations in the form of a query.
Stream intermediate operations do not get executed until a terminal operation is invoked.
All Intermediate operations are lazy, so they’re not executed until a result of processing is actually needed.
Traversal of the Stream does not begin until the terminal operation of the pipeline is executed.
Here is the list of all Stream intermediate operations:

•	filter()
•	map()
•	flatMap()
•	distinct()
•	sorted()
•	peek()
•	limit()
•	skip()

A terminal operation in Java is a method applied to a stream as the final step. Additional stream operations are not permitted because a terminal operation never produces a stream object. A common example of a terminal operation is the forEach method that often is used to print the stream object's elements.

Java-8 Stream terminal operations produces a non-stream, result such as primitive value, a collection or no value at all. Terminal operations are typically preceded by intermediate operations which return another Stream which allows operations to be connected in a form of a query.

Here is the list of all Stream terminal operations: 
•	toArray()
•	collect()
•	count()
•	reduce()
•	forEach()
•	forEachOrdered()
•	min()
•	max()
•	anyMatch()
•	allMatch()
•	noneMatch()
•	findAny()
•	findFirst()




What are the differences between intermediate and terminal operations in Java 8 streams?
The difference between above operations is that an intermediate operation is lazy while a terminal operation is not. When you invoke an intermediate operation on a stream, the operation is not executed immediately. It is executed only when a terminal operation is invoked on that stream.

How many terminal operations can a Stream pipeline have?
Pipeline of operations can have maximum one terminal operation, that too at the end.

What is an optional in Java?
Optional is a container object used to contain not-null objects. ... This class has various utility
methods to facilitate code to handle values as 'available' or 'not available' instead of
checking null values. It is introduced in Java 8 and is similar to what Optional is in Guava.

Where is optional used?
Use Optional Everywhere
1.	Design your classes to avoid optionality wherever feasibly possible.
2.	In all remaining cases, the default should be to use Optional instead of null.
3.	Possibly make exceptions for: local variables. Return values and arguments to private methods.

Why is default method introduced in Java 8?
The default methods were introduced to provide backward compatibility so that existing interfaces can use the lambda expressions without implementing the methods in the implementation class. Default methods are also known as defender methods or virtual extension methods.



























Completable Future:
Completable Future is used to perform possible asynchronous(non-blocking) computation and trigger dependent computations which could also be asynchronous.

 	 

In Java to perform a non-blocking operation has always been easy. We simply create a new Runnable. We run into separate thread and wants that Runnable calls the run method, and run method is completed, that thread is destroyed. It’s simple. 
If the task that we want to run also is returning a value back, we could use something called as callable. Again, create a new thread, complete the thread and return back the variable that we want in the main thread. And we typically to this by using Executive Service.				

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletableFuture {
  public static void main(String[] args) {
	ExecutorService service = Executors.newFixedThreadPool(10);
	// submit task and accept the placeholder object for return value
	Future<Integer> future = service.submit(new Task());
	try {
		// get the task return value
		Integer result = future.get(); 	// blocking
		System.out.println("result from the task is " + result);
	} catch(InterruptedException | ExecutionException e) {
		e.printStackTrace();
	}
  }
  static class Task implements Callable<Integer> {
	@Override
	public Integer call() throws Exception {
		return new Random().nextInt();
	}
  }
}					
 
Callable


 
Blocking get operation
									

 
Here is a problem !
ExecutorService service = Executors.newFixedThreadPool(10);
try {
	Future<Order> future = service.submit(getOrderTask());
	Order order = future.get();		// blocking
	Future<Order> future1 = service.submit(enrichTask(order));
	order = future1.get();		// blocking
	Future<Order> future2 = service.submit(performPaymentTask(order));
	order = future2.get();		// blocking
	Future<Order> future3 = service.submit(dispatchTask(order));
	order = future3.get();		// blocking
	Future<Order> future4 = service.submit(sendEmailTask(order));
	order = future4.get();		// blocking
} catch(InterruptedException | ExecutionException e) {
	e.printStackTrace();
}

 
Independent Operations


 
What we actually want
 
What we actually want

for(int 1 = 0; i < 100; i++) {
    CompletableFuture.supplyAsync(() -> getOrder())
	.thenApply(order -> enrich(order))
	.thenApply(o -> performPayment(o))
	.thenApply(order -> dispatch(order))
	.thenAccept(order -> sendEmail(order));
  }
}

for(int 1 = 0; i < 100; i++) {
    CompletableFuture.supplyAsync(() -> getOrder())
	.thenApplyAsync(order -> enrich(order))
	.thenApplyAsync(o -> performPayment(o))
	.thenApplyAsync(order -> dispatch(order))
	.thenAcceptAsync(order -> sendEmail(order));
  }
}

ExecutorService cpuBound = Executors.newFixedThreadPool(4);
ExecutorService ioBound = Executors.newCachedThreadPool();
for(int 1 = 0; i < 100; i++) {
    CompletableFuture.supplyAsync(() -> getOrder(), ioBound)
	.thenApplyAsync(order -> enrich(order), cpuBound)
	.thenApply(o -> performPayment(o))
	.thenApply(order -> dispatch(order))
	.thenAccept(order -> sendEmail(order));
  }
}

If we not use any ExecutorService, what happens then what is being used internally. Internally it uses the ForkJoinPool.commaonPool();
This is the default pool that if we don’t provide any then it will use this default pool to run all our tasks.

In case of exceptions, if any of the operations above this method which is like catch block will be called. And in this catch block whatever the exception, if we want to return a failed order method.
So, if it’s a failed order then please do not proceed with anything else. If it’s normal order, then proceed with dispatch and other…    

for(int 1 = 0; i < 100; i++) {
    CompletableFuture.supplyAsync(() -> getOrder())
	.thenApply(order -> enrich(order))
	.thenApply(o -> performPayment(o))
	.exceptionally(e -> new FailedOrder())
	.thenApply(order -> dispatch(order))
	.thenAccept(order -> sendEmail(order));
  }
}

Note: Reactive framework i.e. RX Java

What is Optional<?> in Java 8:
Example with stream API
As we know Java is a purely object-oriented programming language, which means in our day-to-day life while coding, we all are using object to build some logic…right. When we say object, which means either objects contain some data, or it contains null value. If objects contain data, then we can execute our code but if it contains null then definitely our code leads to NullPointerException.
That is common behaviour that we face day to day in coding life.    

public class OptionalWithStream {
  public static void main(String[] args) {
	Customer customer1 = new Customer(101, "abc");
	  customer1.getField() ----> will give data
		
	  Customer customer2 = new Customer(102, null);
	  String name = customer2.getName();  ---->  will give null
		
	  // make uppercase
	  name.toUpperCase();  ----> will give NullPointerException
	}
	
	class Customer {
	  int id;
	  String name;
	  // constructor
	  // getter & setter
	}
}

Here Customer object have only 2 fields then we can write if else null check for fields. But what if our class contains 100 object or 100 attributes. Writing 100 null check is not a good practice. So to avoid this kind of scenario or to avoid predictable NullPointerException Java 8 introduced one class that is called Optional.  

Optional is public final class and contains lots of utility methods. In which there are 3 methods which is used for Optional object creation.

Modifier and Type		   Method		    Description
static <T> Optional<T>	   empty()		    Returns an empty Optional instance.
static <T> Optional<T>	   of(T value)	  Returns an Optional describing the
     given non-null value.
static <T> Optional<T>	ofNullable(T value)Returns an Optional describing the
     given value, if non-null, otherwise
     returns an empty Optional.
// filter the tallest person in each city
        Comparator<Person> byHeight = Comparator.comparing(Person :: getHeight);
        Map<String, Optional<Person>> tallestByCity = people.stream()
                .collect(groupingBy(Person :: getCity, reducing(BinaryOperator.maxBy(byHeight))));

        for (Map.Entry<String, Optional<Person>> entry : tallestByCity.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().get());
        }

******************************************************************************************
// remove person which has same name
List<Person> unique = people.stream()
   .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(Person::getName))), ArrayList::new));
        for (Person entry : unique) {
            System.out.println(entry.getName() + ":" + entry.getCity());
        }

**********************************************************************************
// count Male and Female employee
        Map<String, Long> nameCount  =
                employeeList.stream().collect(groupingBy(Employee::getGender, counting()));
        nameCount.forEach((name, count) -> {
            System.out.println(name + ":" + count);
        });

// highest salary in each group of Male and Female
Map<String, Optional<Employee>> maxSalary  =
                employeeList.stream().collect(groupingBy(Employee::getGender, maxBy(Comparator.comparingDouble(Employee :: getSalary))));
        maxSalary.forEach((name, employee) -> {
            System.out.println(name + ":" + employee.get().getName() + ":" + employee.get().getSalary());
        });

// Count String whose length is more than three

long num = strList.stream(). filter(x -> x.length()> 3) .count();

// Count number of String which starts with "a"

long count = strList.stream(). filter(x -> x.startsWith("a")) .count();

//Remove all empty Strings from List

List<String> filtered = strList.stream() .filter(x -> !x.isEmpty()) .collect(Collectors.toList());

// Convert String to uppercase and Join them with coma
List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada"); String G7Countries = G7.stream() .map(x -> x.toUpperCase()) .collect(Collectors.joining(", "));

// Create a List of the square of all distinct numbers
List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4); List<Integer> distinct = numbers.stream() .map( i -> i*i) .distinct() .collect(Collectors.toList());

// Get count, min, max, sum, and the average for numbers

List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29); IntSummaryStatistics stats = primes.stream() .mapToInt((x) -> x) .summaryStatistics();

//

