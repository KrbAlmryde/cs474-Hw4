package com.hw4.evaluator

import com.hw4.parser.{Expression, _}

import scala.collection.mutable

/**
  * Created by krbalmryde on 11/20/16.
  * Lambda Evaluator performs the heavy lifting in regards to actually evaluating
  * the lambda expression.
  *
  * Can perform implicit Alpha conversion
  *                      Beta Reduction
  *                      Eta conversion
  *
  */
class LambdaEvaluator(var verbose:Boolean, var debug:Boolean) {
    val freeVariables:mutable.Set[Variable] = mutable.Set[Variable]()
    val boundVariables:mutable.Set[Variable] = mutable.Set[Variable]()
    val definitions:mutable.Map[Variable,Expression] = mutable.Map[Variable,Expression]()


    def apply(expr: Expression):Expression = {
        printVerb(s"   ${Console.BOLD}$expr\n")
        boundVariables ++= expr.boundVars
        freeVariables ++= expr.freeVars

        val beta = β(expr)
        if (beta != expr) {
            printDebug(s"   $beta ${Console.RED}*-β reduction-*${Console.WHITE}\n")
            val alpha = α(expr, freeVariables.toSet)
            if (alpha != expr) {
                printDebug(s"   $alpha ${Console.RED}*-α conversion-*${Console.WHITE}\n")
                apply(alpha)
            } else
                apply(beta)
        }
        else {
            val eta = η(expr)
            if( eta != expr) printDebug(s"   $eta ${Console.YELLOW}*-η conversion-* ${Console.WHITE}\n")
            eta
        }
    }

    // Checks to see if the variable is defined, and if so, replace it with its definition
    def hasDefinition(name:Variable):Expression = {
        if (definitions contains name) {
            val defs = definitions(name)
            println(s"\t\t$name == $defs")
            substitute(Assignment(name, defs), name, defs)
        }
        else
            name
    }

    // Renames our variables. Helper method to α-conversion below
    def rename(name:Variable, conflicts:Set[Variable]): Variable = {
        val alpha = Variable(name + "'")
        if (conflicts contains alpha)
            rename(alpha, conflicts)
        else
            alpha
    }

    def α(expr: Expression, conflicting: Set[Variable]): Expression = {
        printDebug(s"\tα($expr) ${conflicting.mkString(", ")}\n")
        expr match {
            case name:Variable => hasDefinition(name)

            case Lambda(arg, body) =>
                if (conflicting contains arg) {
                    val newArg = rename(arg, conflicting ++ expr.boundVars)
                    Lambda(newArg, substitute(body, arg, newArg))
                } else
                    Lambda(arg, α(body, conflicting))

            case Application(funcExpr, argExpr) =>
                Application(α(funcExpr, conflicting), α(argExpr, conflicting))

            case _ =>
//                println(s" α $expr")
                expr
        }
    }

    // Beta Reduction
    def β(expr: Expression): Expression = {
        expr match {
            case Application( Lambda(name, body), argExpr) =>
                substitute(body, name, argExpr)

            case Application(a, b) =>
                val left = Application(β(a), b)
                if (left != expr)
                    left
                else
                    Application(a, β(b))

            case Lambda(arg, body) => Lambda(arg, β(body))
            case _ =>
                expr

        }
    }


    def η(expr: Expression): Expression = {
        printDebug(s"η($expr)\n")
        expr match {
            case Lambda(x, Application(f, y)) if x == y =>
                if (f.freeVars contains x) expr else f
            case _ => expr
        }
    }

    def substitute(term: Expression, orig: Variable, sub: Expression ):Expression = {
        term match {
            // Should an Application be matched, Substitute
            case Application(funcExpr, argument) =>
                Application(
                    substitute(funcExpr, orig, sub), //
                    substitute(argument, orig, sub)  //
                )

            case lambda @ Lambda(name, body) =>
                if (orig != name ) {
                    // If the sub expression contains the lambdas name, substitute it
                    if (sub.freeVars contains name)
                        substitute(
                            α(lambda, sub.freeVars),
                            orig,
                            sub
                        )
                    else
                        Lambda(name, substitute(body, orig, sub))
                } else
                    lambda

            case name:Variable =>
                if (definitions contains name) {
                    val defs = definitions(name)
                    substitute(Assignment(name, defs), name, defs)

                } else if (orig == name) {
                    printVerb(s" ${Console.BOLD}=> ${Console.BLUE} $sub ${Console.WHITE}for $name\n")
                    sub
                } else {
                    name
                }

            case declared:Assignment =>
                if (orig == declared.identifier) {
                    printVerb(s" ${Console.BOLD}=> ${Console.BLUE} ${declared.expr} ${Console.WHITE}for ${declared.identifier}\n")
                    declared.expr
                } else {
                    declared.identifier
                }
        }

    }

    /**
      * Handy functions that manages the logic of determining if the application should print output
      * That is, if isVerbose is true, print the supplied string, otherwise dont.
      * @param expr String: The string to be printed. String should include \n characters if needed
      */
    def printDebug(expr:String): Unit = {
        if(debug) print(expr)
    }

    def printVerb(expr:String): Unit = {
        if(verbose) print(expr)
    }

    def displayDefined(): Unit = {
        println("Defined Variables are:")
        definitions.foreach(d => println(s"${Console.BOLD}\t${d._1} = ${d._2}"))
    }

    def report(): Unit = {
        displayDefined()
        println(s"${Console.WHITE}Free Variables are: ${Console.BOLD}" + freeVariables.mkString(", "))
        println(s"${Console.WHITE}Bound Variables are: ${Console.BOLD}" + boundVariables.mkString(", "))
        freeVariables.clear
        boundVariables.clear
    }


}
