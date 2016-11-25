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
  */
import scala.util.matching.Regex
import scala.util.parsing.combinator._


/**
  * We really only need these 3 patterns since everything builds off
  * of them.
  *
  * We need a Lambda pattern to declare our Lambda function
  * The Variable pattern identifies singletons
  * The Application pattern manages the individual Symbols
  */
sealed trait Expression {
    // def eval: String
    def freeVars: Set[Variable]
    def boundVars: Set[Variable]
    def pretty(color:String): String
}

// Variable patterns
case class Variable(name:String) extends Expression {
    // def eval = name
    def freeVars: Set[Variable] = Set(this)
    def boundVars: Set[Variable] = Set()
    def pretty(color:String):String = color + name + Console.WHITE
    override def toString = s"$name"
}


// Lambda pattern
case class Lambda(arg: Variable, body: Expression) extends Expression {
    // def eval = s"$arg ${body.eval}"

    def freeVars: Set[Variable] =  body.freeVars - arg
    def boundVars: Set[Variable] = body.boundVars + arg
    def pretty(color:String):String = color + arg.name + Console.WHITE
    override def toString = s"λ$arg.$body"
}


// Application pattern
case class Application(funcExpr: Expression, argument: Expression) extends Expression {
    // def eval = "_"+funcExpr.eval + argument.eval + "_"
    def freeVars: Set[Variable] = funcExpr.freeVars ++ argument.freeVars
    def boundVars: Set[Variable] = funcExpr.boundVars ++ argument.boundVars
    def pretty(color:String):String = color + toString + Console.WHITE

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


case class Assignment(identifier: Variable, expr:Expression) extends Expression {
    def freeVars: Set[Variable] = identifier.freeVars ++ expr.freeVars
    def boundVars: Set[Variable] = identifier.boundVars ++ expr.boundVars
    def pretty(color:String):String = color + toString + Console.WHITE

    override def toString = s"$identifier = $expr"
}

/**
  * Effectively can be almsot anything here
  * As with any parser, it should take some input and return an output.
  *     In our case we are returning Expression type objects
  * It is the parsers job to convert the input to the correct output
  */
class LambdaParser extends RegexParsers with PackratParsers{
    // This should help up

    /** The set of reserved identifiers: these will be returned as `Keyword`s. */
    lazy val expression:PackratParser[Expression] = defined | application | abstraction
    lazy val abstraction:PackratParser[Expression] = function | name | "(" ~> expression <~ ")"


    // A single variable, can be one to many characters, starting with an alpha-char
    lazy val name:PackratParser[Variable] = """[a-zA-Z'\w]+""".r ^^ (v => Variable(v))

    // lambda/λ/\<name>. <expression>   expression is a simple abstraction over body, saves us from typing anything // Here we have a regex to capture the lambda symbol
    lazy val function:PackratParser[Expression] = """(lambda\s|λ|\\)""".r ~> rep1(name) ~ "." ~ expression ^^ { case arg~"."~expr => (arg :\ expr) { Lambda } }

//    lazy val delimiters:Regex = """[+-*\(\)]""".r
    lazy val application:PackratParser[Expression] = abstraction ~ rep1(abstraction) ^^ { case exp1 ~ exp2 => ( exp1 /: exp2) { (app, e) => Application(app, e) } }

    // This will give us a basic mapping
    lazy val defined:PackratParser[Assignment]  = name ~ "=" ~ expression ^^ { case id ~ "=" ~ value => Assignment(id, value) }


    def parse(str: String): Expression = parseAll(expression, str) match {
        case Success(result: Expression, _) => result
        case err:NoSuccess => println(s"Malformed input: " + err )
            Variable("")
    }

//    lazy val definitions:Parser[Map[Variable, Expression]] = repsep(defined, ";")  ^^ (definedVariables ++= _) //repsep(declared, ";") ^^ {case l => val ahh = Map()[Variable, Expression](l(0)._1, l(0)._2); ahh }

//    def apply(str:String): Expression = parseAll(expression, str) match {
//        case Success(result: Expression, _) => result
//        case err:NoSuccess => println(s"Malformed input: " + err )
//            Variable("")
//    }
//
}



//(λx.x (λx.λy.x)) ( (λc.λd.λe.e c d) a b )