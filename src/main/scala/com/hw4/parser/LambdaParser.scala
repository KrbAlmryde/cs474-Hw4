package com.hw4.parser

/**
  * Created by krbalmryde on 11/19/16.
  *
  * Description:
  *     We are building a Inheritance chain.
  *
  *     Each object Extends the other, adding more functionality,
  *     while still maintaining commonality.
  *
  * Notes:
  *     It does necessitate having a well thought out design here.
  *
  *
  *
  * KYLE WHEN YOU WAKE UP:
  *
  *     Construct your Parser, it need not be terribly complex or clever.
  *         Borrow from Samples for now, and modify as the need arises
  *
  *     Write Test code!
  *
  *     Write Recursive evaluation function
  *         Remember to include Substitution
  *                             Renaming
  *
  *
  *
  */

import scala.util.parsing.combinator._
import scala.util.parsing.combinator.lexical.StdLexical
import scala.util.parsing.combinator.syntactical.StdTokenParsers

/**
  * We really only need these 3 patterns since everything builds off
  * of them.
  *
  * We need a Lambda pattern to declare our Lambda function
  * The Variable pattern identifies singletons
  *
  * The Application pattern manages the individual Symbols
  *
  *  A simple example would look something like the following
  *         λ(  V(x),
  *             λ(
  *                 V(y) ,
  *                 A( V(x), V(y)
  *              )
  *          )
  * */
sealed trait Expression {
//    def substitue(parent:Variable, child:Expression): Expression
    def eval: String
    def freeVars: Set[Variable]
    def boundVars: Set[Variable]
}

// Variable patterns
case class Variable(name:String) extends Expression {
    def eval = name
    def freeVars: Set[Variable] = Set(this)
    def boundVars: Set[Variable] = Set()
    override def toString = name

}


// Lambda pattern
case class Lambda(arg: Variable, body: Expression) extends Expression {
    def eval = s"$arg ${body.eval}"
    def freeVars: Set[Variable] =  body.freeVars - arg
    def boundVars: Set[Variable] = body.boundVars + arg
    override def toString = s"λ$arg.$body"
}

// Application pattern
case class Application(funcExpr: Expression, argument: Expression) extends Expression {
    def eval = "_"+funcExpr.eval + argument.eval + "_"
    def freeVars: Set[Variable] = funcExpr.freeVars ++ argument.freeVars
    def boundVars: Set[Variable] = funcExpr.boundVars ++ argument.boundVars

    override def toString = {
        // Left side
        (funcExpr match {
            case Lambda(_, _) => "(" + funcExpr + ")"
            case _ => funcExpr

        // Right side
        }) + " " + (argument match {
            case Variable(_) => argument
            case _ => "(" + argument + ")"

        })
    }
}


/**
  * Effectively can be almsot anything here
  * As with any parser, it should take some input and return an output.
  *     In our case we are returning Expression type objects
  * It is the parsers job to convert the input to the correct output
  */
class LambdaParser extends RegexParsers with PackratParsers{
    // This should help up

    val lexical = new StdLexical
    lexical.delimiters ++= Seq("lambda", "λ", "\\", ".", "(", ")", ";")

//    lazy val expr:PackratParser[Expression] = application | other
//    lazy val other:PackratParser[Expression] = func | name | parens
//    lazy val expr:PackratParser[Expression] = application | terms
//    lazy val terms:PackratParser[Expression] = function | parens | name

    lazy val expression:PackratParser[Expression] = application | remainder
    lazy val remainder:PackratParser[Expression] = function | name | "(" ~> expression <~ ")"



    // A single variable, can be one to many characters, starting with an alpha-char
    lazy val name:PackratParser[Variable] = """[a-zA-Z]+\w*""".r ^^ (v => Variable(v))

    // lambda/λ/\<name>. <expression>   expression is a simple abstraction over body, saves us from typing anything // Here we have a regex to capture the lambda symbol
    lazy val function:Parser[Expression] = """(lambda\s|λ|\\)""".r ~> rep1(name) ~ "." ~ expression ^^ { case arg~"."~expr => (arg :\ expr) { Lambda } }

    lazy val application:PackratParser[Expression] = remainder ~ rep1(remainder) ^^ { case exp1 ~ exp2 => ( exp1 /: exp2) { (app, e) => Application(app, e) } }


//    lazy val constant: Parser[Expression] = [^a-z\\λ\(\)\s\.']+""".r ^^ { Expression }


//    def expr: Parser[Expression] = ("""(lambda\s|λ|\\)""".r ~> """[a-zA-Z]+\w*""".r <~ ".") ~ expr ^^ { case param ~ body => Lambda(param, body) }  | term
//    def term: Parser[Expression] = factor ~ rep(factor) ^^ { case a ~ lst => {
//        println(s"A( $a /: $lst )") ; (a /: lst) { Application }
//    } } //
//    def factor: Parser[Expression] = """[a-zA-Z]+\w*""".r ^^ { Variable } | "(" ~> expr <~ ")"
//

    def parse(str: String): Expression = parseAll(expression, str) match {
        case Success(result: Expression, _) => result
        case err:NoSuccess => println(s"Malformed input: " + err )
            Variable("")
    }

    def apply(str:String): Expression = parseAll(expression, str) match {
        case Success(result: Expression, _) => result
        case err:NoSuccess => println(s"Malformed input: " + err )
            Variable("")
    }


}


