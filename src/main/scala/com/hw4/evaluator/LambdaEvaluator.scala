package com.hw4.evaluator

import com.hw4.parser._
import com.hw4.Utils.printDebug
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
class LambdaEvaluator(var verbose:Boolean) {
    val definitions:mutable.Map[Variable,Expression] = mutable.Map[Variable,Expression]()


    def apply(expr: Expression):Expression = {
        println(s"apply: $expr")

        evaluate(expr) match {
            case declared:Assignment =>
                println(s"\tAssn: $declared" )
                // append defined variables so we can keep track of them
                definitions++=mutable.Map[Variable,Expression](declared.identifier -> declared.expr)
                // evaluate(declared, verbose)
                declared // testing to see if this has an effect

            case single:Variable =>

                if (definitions contains single) {
                    val defs = definitions(single)
                    evaluate(substitution(Assignment(single, defs), single, defs))
                }
                else evaluate(single)

            case app@Application(fn, arg)=>
                println(s"\t $app")
                app match {
                    case Application(fn:Variable, arg:Variable) =>
                        println(s"\t\t App(V, V) => $fn | $arg")
                        apply(
                            Application(
                                hasDefinition(fn),
                                hasDefinition(arg)
                            )
                        )

                    case Application(fn:Variable, _) =>
                        println(s"\t\t App(V, _) => $fn {$arg}")
                        evaluate(
                            Application(
                                hasDefinition(fn),
                                arg
                            )
                        )
                        // fn is anything, arg is Variable
                    case Application(_, arg:Variable) =>
                        println(s"\t\t App(_, V) => {$fn} $arg")
                        evaluate(
                            Application(
                                fn,
                                hasDefinition(arg)
                            )
                        )
                    // if its anything else, go strait to evaluation
                    case _ =>
                        println(s"\t\t App(_, _)")
                        evaluate(app)
                }

            case any => evaluate(any)
        }
    }

    def evaluate(expr: Expression): Expression = {
        printDebug(verbose, s"   $expr")
        val beta = β(expr)
        if (beta != expr) {
            printDebug(verbose, s"   $expr ${Console.BOLD}=> ${Console.RED}*-β*- ${Console.WHITE}")
            apply(beta)
        }
        else {
            val eta = η(expr)
            printDebug(verbose, s"   $eta ${Console.BOLD}=> ${Console.YELLOW}*-η-* ${Console.WHITE}")
            eta
        }
    }


    // Checks to see if the variable is defined, and if so, replace it with its definition
    def hasDefinition(name:Variable):Expression = {
        if (definitions contains name) {
            val defs = definitions(name)
            println(s"\t\t$name == $defs")
            substitution(Assignment(name, defs), name, defs)
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
        expr match {
            case name:Variable => hasDefinition(name)

            case Lambda(arg, body) =>
                if (conflicting contains arg) {
                    val newArg = rename(arg, conflicting ++ expr.boundVars)
                    Lambda(newArg, substitution(body, arg, newArg))
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
                substitution(body, name, argExpr)

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
//        println(s"η: $expr")
        expr match {
            case Lambda(x, Application(f, y)) if x == y =>
                if (f.freeVars contains x) expr else f
            case _ => expr
        }
    }

    def substitution(term: Expression, orig: Variable, sub: Expression ):Expression = {
        term match {
            // Should an Application be matched, Substitute
            case Application(funcExpr, argument) =>
                Application( substitution(funcExpr, orig, sub),  substitution(argument, orig, sub) )

            case lambda @ Lambda(name, body) =>
                if (orig != name ) {
                    // If the sub expression contains the lambdas name, substitute it
                    if (sub.freeVars contains name)
                        substitution(
                            α(lambda, sub.freeVars),
                            orig,
                            sub
                        )
                    else
                        Lambda(name, substitution(body, orig, sub))
                } else
                    lambda

            case name:Variable =>
                if (definitions contains name) {
                    val defs = definitions(name)
                    substitution(Assignment(name, defs), name, defs)

                } else if (orig == name) {
                    sub
                } else {
                    name
                }

            case declared:Assignment =>
                if (orig == declared.identifier) {
                    declared.expr
                } else {
                    declared.identifier
                }
        }

    }


}
