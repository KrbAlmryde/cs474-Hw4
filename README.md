# CS 474: HW4

#### Preamble

This README documents the installation and usage for the cs474 HW4 tasked with developing an untyped lambda-calculus interpreter in scala. Read on!


### What is this repository for? ###


### Libraries


### How do I get set up?
To run the application, (assuming you are using Intellij) simply execute the SBT task **'Main'**. You will then be prompted
to enter the name of one of the languages presented to you. If you have trouble entering a valid language name, the program
will simply ask you again until you do (or kill the process by hitting enter)

 **<start the application>**

 **<JVM does some stuff>**

 **----------------------------------------------------------------------------**
 
 
 **Hello! From the following list:**
 
 **ada, assembly, c, c++, c#, fortran, java, jovial, delphi, pascal, pl, m, vhdl, cobol, php, html, css, javascript, python**
 
 **Please enter Language: \>** *java*

**NOTE: Quotation is not necessary. The application assumes you are passing a string.**

###Full Disclosure
If you have a poor internet connection, you are going to have a bad time. Dont let ComCast get you down!
Joking aside, this app requires a solid internet connection in order to do its job. The app may not shutdown in some cases...



####Setting up from IntelliJ ####

1) If no project is currently open in IntelliJ IDEA, click **Import Project** on the Welcome screen. Otherwise, select **File | New | Project from Existing Sources**.

2) In the dialog that opens, select the directory that contains the project to be imported, or a file that contains an appropriate project description. Click **OK**.

3) On the first page of the **Import Project** wizard, select SBT, and click **Next**. (This page is not shown if IntelliJ IDEA has guessed what you are importing.)

4) On the next page of the wizard, specify SBT project settings and global SBT settings, click **Finish**.


#### Development Testing
For testing purposes, I tried to keep the size of the repositories relatively small, if only to make downloading and parse etc faster. To that end the repository search string limits search results to 3000kb

*Be aware, despite these measures, some of these files can get large and will take some time to parse.


#### Unit Testing
For Unit-testing I utilized Akka's Actor Testkit[ScalaTest](http://www.scalatest.orge) using the [FunSuite](http://doc.scalatest.org/3.0.0/#org.scalatest.FunSuite). Its fun and was surprisingly simple to get it up and running. The Akka Testkit was no walk in the park though, so forgive the stupid simple tests.

Go figure.

To run the tests, in Intellij simplly select the **SBT** task "Test" and youll be up and running! Que sera


## Discussion:
### How to tell something is happening
Keep an eye on the resources directory under **<this_project_source_dir>/src/main/resources** a folder named repositories will appear and be populated with cloned repos.

As things progress, you should expect to see a couple of things:
- 1) repository directories are by their **ID/repoName**
-


### References and Sources
I could not have gotten far on this assignment without the wonderful tutorials and example code provided by these lovely people
* Erkki Lindpere's ["Parsing Lambda Calculus in Scala"](http://zeroturnaround.com/rebellabs/parsing-lambda-calculus-in-scala/) blog and associated[Github](https://github.com/Villane/lambdacalculus)
* François Sarradin's ["Playing with Scala Parser Combinator"](https://kerflyn.wordpress.com/2012/08/25/playing-with-scala-parser-combinator/) blog post
* [Dr. Cay Horstmann](http://horstmann.com/)'s `cs256 lectures` [1](http://horstmann.com/sjsu/fall2009/cs252/lambda1/)[2](http://horstmann.com/sjsu/fall2009/cs252/lambda2/)and [3](http://horstmann.com/sjsu/fall2009/cs252/lambda3/) and [Sample Code](http://horstmann.com/sjsu/fall2009/cs252/lambda.scala) (He had some nice practice problems)
* Christoph Henkelmann’s `"An Introduction To Scala Parser Combinators"` Part [1](http://henkelmann.eu/2011/01/13/an_introduction_to_scala_parser_combinators) [2](http://henkelmann.eu/2011/01/28/an_introduction_to_scala_parser_combinators-part_2_literal_expressions) and [3](http://henkelmann.eu/2011/01/29/an_introduction_to_scala_parser_combinators-part_3_unit_tests)Blog

### Who do I talk to? ###

* If you have any specific questions contact me via [kyle.almryde@gmail.com](mailto:kyle.almryde@gmail.com)
* If you have any complaints, please direct them to this [Handsome devil](mailto:drmark@uic.edu) ![drmark@uic.edu](https://www.cs.uic.edu/~drmark/index_htm_files/3017.jpg)




