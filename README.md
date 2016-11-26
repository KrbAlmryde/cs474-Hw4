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
To run the application, navigate to the project directory: ```kyle_almryde_hw3/``` then in the command-line enter ```sbt run```.
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
     d | def => Display user defined variables
     v | verb => Toggle LOTS of information about the processing steps.

λ -- Starting the Lambda calculus Interpreter -- λ

>:
```


####Setting up from IntelliJ ####

1) If no project is currently open in IntelliJ IDEA, click **Import Project** on the Welcome screen. Otherwise, select **File | New | Project from Existing Sources**.

2) In the dialog that opens, select the directory that contains the project to be imported, or a file that contains an appropriate project description. Click **OK**.

3) On the first page of the **Import Project** wizard, select SBT, and click **Next**. (This page is not shown if IntelliJ IDEA has guessed what you are importing.)

4) On the next page of the wizard, specify SBT project settings and global SBT settings, click **Finish**.

---

## Development Testing

Some Example inputs and their evaluation steps
```
>: (λc.λd.λe.c d e) (λx.λy.x) a b
 = (λc.λd.λe.c d e) (λx.λy.x) a b
 = (λd.λe.(λx.λy.x) d e) a b
 = (λe.(λx.λy.x) a e) b
 = (λx.λy.x) a b
 = (λy.a) b
 = a
```

```
(λn.λx.λy.x(n x y)) (λn.λx.λy.x(n x y)) (λx.λy.x y)
(λn.λx.λy.x (n x y)) (λn.λx.λy.x (n x y)) (λx.λy.x y)
(λx.λy.x ((λn.λx.λy.x (n x y)) x y)) (λx.λy.x y)
λy.(λx.λy.x y) ((λn.λx.λy.x (n x y)) (λx.λy.x y) y)
λy.λy'.(λn.λx.λy.x (n x y)) (λx.λy.x y) y y'
λy.λy'.(λx.λy.x ((λx.λy.x y) x y)) y y'
λy.λy'.(λy'.y ((λx.λy.x y) y y')) y'
λy.λy'.y ((λx.λy.x y) y y')
λy.λy'.y ((λy'.y y') y')
λy.λy'.y (y y')
```
```
(λn.λx.λy.x(n x y)) ((λn.λx.λy.x(n x y)) ((λn.λx.λy.x(n x y)) (λx.λy.y)))
(λn.λx.λy.x (n x y)) ((λn.λx.λy.x (n x y)) ((λn.λx.λy.x (n x y)) (λx.λy.y)))
λx.λy.x ((λn.λx.λy.x (n x y)) ((λn.λx.λy.x (n x y)) (λx.λy.y)) x y)
λx.λy.x ((λx.λy.x ((λn.λx.λy.x (n x y)) (λx.λy.y) x y)) x y)
λx.λy.x ((λy.x ((λn.λx.λy.x (n x y)) (λx.λy.y) x y)) y)
λx.λy.x (x ((λn.λx.λy.x (n x y)) (λx.λy.y) x y))
λx.λy.x (x ((λx.λy.x ((λx.λy.y) x y)) x y))
λx.λy.x (x ((λy.x ((λx.λy.y) x y)) y))
λx.λy.x (x (x ((λx.λy.y) x y)))
λx.λy.x (x (x ((λy.y) y)))
λx.λy.x (x (x y))
```

```
(\m.\n.\f.\x.m f (n f x)) (\f.\x.f x) (\f.\x.f (f x))
(λm.λn.λf.λx.m f (n f x)) (λf.λx.f x) (λf.λx.f (f x))
(λn.λf.λx.(λf.λx.f x) f (n f x)) (λf.λx.f (f x))
λf.λx.(λf.λx.f x) f ((λf.λx.f (f x)) f x)
λf.λx.(λx.f x) ((λf.λx.f (f x)) f x)
λf.λx.f ((λf.λx.f (f x)) f x)
λf.λx.f ((λx.f (f x)) x)
λf.λx.f (f (f x))
```
#### Unit Testing
For Unit-testing I utilized Akka's Actor Testkit[ScalaTest](http://www.scalatest.orge) using the [FunSuite](http://doc.scalatest.org/3.0.0/#org.scalatest.FunSuite). Its fun and was surprisingly simple to get it up and running. The Akka Testkit was no walk in the park though, so forgive the stupid simple tests.

Go figure.

To run the tests, in Intellij simplly select the **SBT** task "Test" and youll be up and running! Que sera

---

## Discussion:

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




