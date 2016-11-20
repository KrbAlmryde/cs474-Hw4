# CS 474: HW4
Author: **Kyle R. Almryde**


## Preamble

This README documents the installation and usage for the cs474 HW4 tasked with developing an untyped lambda-calculus interpreter in scala. Read on!

### What is this repository for?


### Libraries

---

## How do I get set up?

####Setting up from IntelliJ ####

1) If no project is currently open in IntelliJ IDEA, click **Import Project** on the Welcome screen. Otherwise, select **File | New | Project from Existing Sources**.

2) In the dialog that opens, select the directory that contains the project to be imported, or a file that contains an appropriate project description. Click **OK**.

3) On the first page of the **Import Project** wizard, select SBT, and click **Next**. (This page is not shown if IntelliJ IDEA has guessed what you are importing.)

4) On the next page of the wizard, specify SBT project settings and global SBT settings, click **Finish**.

---

## Development Testing

#### Unit Testing
For Unit-testing I utilized Akka's Actor Testkit[ScalaTest](http://www.scalatest.orge) using the [FunSuite](http://doc.scalatest.org/3.0.0/#org.scalatest.FunSuite). Its fun and was surprisingly simple to get it up and running. The Akka Testkit was no walk in the park though, so forgive the stupid simple tests.

Go figure.

To run the tests, in Intellij simplly select the **SBT** task "Test" and youll be up and running! Que sera

---

## Discussion:

---


## References and Sources
I could not have gotten far on this assignment without the wonderful tutorials and example code provided by these lovely people
  * Erkki Lindpere's ["Parsing Lambda Calculus in Scala"](http://zeroturnaround.com/rebellabs/parsing-lambda-calculus-in-scala/) blog and associated[Github](https://github.com/Villane/lambdacalculus)
  * François Sarradin's ["Playing with Scala Parser Combinator"](https://kerflyn.wordpress.com/2012/08/25/playing-with-scala-parser-combinator/) blog post
  * [Dr. Cay Horstmann](http://horstmann.com/)'s `cs256 lectures` [1](http://horstmann.com/sjsu/fall2009/cs252/lambda1/)[2](http://horstmann.com/sjsu/fall2009/cs252/lambda2/)and [3](http://horstmann.com/sjsu/fall2009/cs252/lambda3/) and [Sample Code](http://horstmann.com/sjsu/fall2009/cs252/lambda.scala) (He had some nice practice problems)
  * Christoph Henkelmann’s `"An Introduction To Scala Parser Combinators"` Part [1](http://henkelmann.eu/2011/01/13/an_introduction_to_scala_parser_combinators) [2](http://henkelmann.eu/2011/01/28/an_introduction_to_scala_parser_combinators-part_2_literal_expressions) and [3](http://henkelmann.eu/2011/01/29/an_introduction_to_scala_parser_combinators-part_3_unit_tests)Blog

---


## Who do I talk to?

* If you have any specific questions contact me via [kyle.almryde@gmail.com](mailto:kyle.almryde@gmail.com)
* If you have any complaints, please direct them to this [Handsome devil](mailto:drmark@uic.edu) ![drmark@uic.edu](https://www.cs.uic.edu/~drmark/index_htm_files/3017.jpg)




