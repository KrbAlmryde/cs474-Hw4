# CS 474: HW4
Author: **Kyle R. Almryde**


## Preamble

This README documents the installation and usage for the cs474 HW4 tasked with developing an untyped lambda-calculus interpreter in scala. Read on!

### What is this repository for?
Using the Scala Language, and the [Parsing Combinator Library](https://github.com/scala/scala-parser-combinators), this application parses untyped 
lambda calculus of the form ```λx.x y (x z)```. It performs basic parsing under the rules of **Normal Order Reduction**, and can perform such steps 
as **```α conversion```**, **```β reduction```**, and **```η converstion```**. Mind you, it doesnt do them all that great, but it does it, and thats 
what counts here. 


### Libraries
+ [Parsing Combinator Library](https://github.com/scala/scala-parser-combinators) was used to perform all the heavy-lifting of parsing the input string
+ No joke, thats it!

---

## How do I get set up?
To run the application, navigate to the project directory: 
```
cd kyle_almryde_hw3/
sbt run
# OR 
sbt test
```
 From there, you should see the following (assuming there were no exceptions)
 
```
λ -- Starting the Lambda calculus Interpreter -- λ

To use this tool type an expression such as the following:

      (lambda x.(lambda y.(x y)) 3) square
            (λx.(λy.(x y)) 3) square
            (\x.(\y.(x y)) 3) square

Available commands include:

     q | quit => Quit the application
     h | help => Display this help message again
     v | verb => Toggle each step in the evaluation.
     d | debug => Toggle LOTS of information about the processing steps. Do this at your own risk!
     def => Display user defined variables

λ ---------------------------------------------- λ

```
This will be followed immediately by a prompt
```
>:
```
Type your equation there! Note, It will continue to prompt you until you submit **```q | quit```**  

For example
```
>: (λc.λd.λe.c d e) (λx.λy.x) a b
```
Sample output, assuming you do NOT set verbose mode would look like the following:
```
>: (λc.λd.λe.c d e) (λx.λy.x) a b
 ~  a  ~
```
Noting that ```a``` is the result of the expression

#####Verbose Mode
```
>: v
Verbose mode set to: true
>: (λc.λd.λe.c d e) (λx.λy.x) a b
=> (λc.λd.λe.c d e) (λx.λy.x) a b
=> (λd.λe.(λx.λy.x) d e) a b
=> (λe.(λx.λy.x) a e) b
=> (λx.λy.x) a b
=> (λy.a) b
=> a

 ~  a  ~
Defined Variables are:
Free Variables are: b, a
Bound Variables are: c, y, d, x, e 
```
#####Debug Mode
```
>: d
Debug mode set to: true
>: (λc.λd.λe.c d e) (λx.λy.x) a b
=>  λx.λy.x for c
=> *-β reduction-*
=>  a for d
=> *-β reduction-*
=>  b for e
=> *-β reduction-*
=>  a for x
=> *-β reduction-*
=> *-β reduction-*
~  a  ~
>:
```

#####Debug Mode AND Verbose Mode
```
>: (λc.λd.λe.c d e) (λx.λy.x) a b
=> (λc.λd.λe.c d e) (λx.λy.x) a b
=>  λx.λy.x for c
=> *-β reduction-*
=> (λd.λe.(λx.λy.x) d e) a b
=>  a for d
=> *-β reduction-*
=> (λe.(λx.λy.x) a e) b
=>  b for e
=> *-β reduction-*
=> (λx.λy.x) a b
=>  a for x
=> *-β reduction-*
=> (λy.a) b
=> *-β reduction-*
=> a
η(a)

~  a  ~
Defined Variables are:
Free Variables are: b, a
Bound Variables are: c, y, d, x, e
```
####Setting up from IntelliJ ####

1) If no project is currently open in IntelliJ IDEA, click **Import Project** on the Welcome screen. Otherwise, select **File | New | Project from Existing Sources**.

2) In the dialog that opens, select the directory that contains the project to be imported, or a file that contains an appropriate project description. Click **OK**.

3) On the first page of the **Import Project** wizard, select SBT, and click **Next**. (This page is not shown if IntelliJ IDEA has guessed what you are importing.)

4) On the next page of the wizard, specify SBT project settings and global SBT settings, click **Finish**.

---

### Discussion
Unfortunately I had some trouble getting the parse to recognize symbols outside of Alpha-Numeric, so try not to input anything fancy like ````@ # $ % ^ & * + _ ```
I had some semblance of assignments and definitions but it broke the rest of the parser, so it had to be sacrificed! It operates on Normal Order reduction, and I would 
REALLY appreciate it if you tried to give it "proper input" See above if you arent sure what I mean by that. The parser will parse of course, but might not do as expected..
Feel free to check out some of the unit tests for fancy operations.

---


#### Unit Testing
For Unit-testing I utilized [ScalaTest](http://www.scalatest.orge) using the [FunSuite](http://doc.scalatest.org/3.0.0/#org.scalatest.FunSuite). Its fun and was surprisingly simple to get it up and running. There are some half decent tests for once!

Go figure.

To run the tests, in Intellij simply type in the command line **```sbt test```** youll be up and running! Que sera

---


## References and Sources
I could not have gotten as far on this assignment as I did without the wonderful tutorials and example code provided by these lovely people (In no particular order)

+ Erkki Lindpere's ["Parsing Lambda Calculus in Scala"](http://zeroturnaround.com/rebellabs/parsing-lambda-calculus-in-scala/) blog and associated[Github](https://github.com/Villane/lambdacalculus)
+ François Sarradin's ["Playing with Scala Parser Combinator"](https://kerflyn.wordpress.com/2012/08/25/playing-with-scala-parser-combinator/) blog post
+ [Dr. Cay Horstmann](http://horstmann.com/)'s `cs256 lectures` [1](http://horstmann.com/sjsu/fall2009/cs252/lambda1/), [2](http://horstmann.com/sjsu/fall2009/cs252/lambda2/), [3](http://horstmann.com/sjsu/fall2009/cs252/lambda3/), and [Sample Code](http://horstmann.com/sjsu/fall2009/cs252/lambda.scala) (He had some nice practice problems)
+ Christoph Henkelmann’s `"An Introduction To Scala Parser Combinators"` Blog, Parts [1](http://henkelmann.eu/2011/01/13/an_introduction_to_scala_parser_combinators), [2](http://henkelmann.eu/2011/01/28/an_introduction_to_scala_parser_combinators-part_2_literal_expressions), and [3](http://henkelmann.eu/2011/01/29/an_introduction_to_scala_parser_combinators-part_3_unit_tests)
+ Jesse Hallet's [implementation](https://github.com/hallettj/LambdaCalculus) was also a very helpful resource.

---

## Who do I talk to?

* If you have any specific questions contact me via [kyle.almryde@gmail.com](mailto:kyle.almryde@gmail.com)
* If you have any complaints, please direct them to this [Handsome devil](mailto:drmark@uic.edu) ![drmark@uic.edu](https://www.cs.uic.edu/~drmark/index_htm_files/3017.jpg)




