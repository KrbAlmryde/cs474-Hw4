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
    def eval: String
}

// Lambda pattern(s)
case class Lambda(name: String, body: Expression) extends Expression {
    def eval = name + body.eval
    override def toString = s"λ$name.$body"
}

// Variable patterns(s)
case class Variable(name:String) extends Expression {
    def eval = name
    override def toString = name
}

// Application pattern
case class Application(funcExpr: Expression, argument: Expression) extends Expression {
    def eval = funcExpr.eval + argument.eval

    override def toString = {
        (funcExpr match {
            case Lambda(_, _) => "(" + funcExpr + ")"
            case _ => funcExpr
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
class LambdaExprParser extends RegexParsers with PackratParsers{
    // This should help up

    val lexical = new StdLexical
    lexical.delimiters ++= Seq("lambda", "λ", "\\", ".", "(", ")", ";")

    lazy val expr:PackratParser[Expression] = application | other
    lazy val other:PackratParser[Expression] = name | parens | func

    // A single variable, can be one to many characters, starting with an alpha-char
    lazy val name:PackratParser[Variable] = """[a-zA-Z]+\w*""".r ^^ { case v => Variable(v) }

    // lambda/λ/\<name>. <expression>   expression is a simple abstraction over body, saves us from typing anything
    // Here we have a regex to capture the lambda symbol
    lazy val func:PackratParser[Lambda] =  """(lambda\s|λ|\\)""".r ~> name ~ """\.""".r ~ expr ^^ { case l~"."~b => Lambda(l.eval, b) }
    lazy val application:PackratParser[Application] = expr ~ expr ^^ { case args: ~[Expression, Expression] => Application(args._1, args._2) }
    lazy val parens:PackratParser[Expression] = "(" ~> expr <~ ")"

    def parse(str: String): ParseResult[Expression] = {
        val tokens = new lexical.Scanner(str)
//        phrase(expr)(str)
        parseAll(expr, str)
    }
    def apply(str:String): Expression = parseAll(expr, str) match {
        case Success(result: Expression, _) => result
        case err:NoSuccess => println(s"Malformed input: " + err )
            Variable("")
    }


}


