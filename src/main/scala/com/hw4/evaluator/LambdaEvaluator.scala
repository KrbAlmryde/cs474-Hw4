package com.hw4.evaluator

import com.hw4.parser._


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
case class Substitution(orig: Variable, sub: Expression ) {
    val evaluator = new LambdaEvaluator()

    def apply(term: Expression): Expression = term match {

        case Application(funcExpr, argument) =>
//            println(s"=> ($funcExpr, $argument)  => $orig  $sub")
            Application( Substitution(orig, sub)(funcExpr),  Substitution(orig, sub)(argument) )

        case lambda:Lambda => {
            if (orig != lambda.arg )
                if (sub.freeVars contains lambda.arg)
                    Substitution(orig, sub)( evaluator.α(lambda, sub.freeVars) )
                else
                    Lambda(lambda.arg, Substitution(orig, sub)(lambda.body) )
            else
                lambda
        }

        case name:Variable => {
            if (orig == name) {
                println(s" ${Console.BOLD}=> ${Console.BLUE} $sub ${Console.WHITE}for $name")
                sub
            } else {
                name
            }
        }
    }
}

class LambdaEvaluator() {
    var eta = false
    var alpha = false

    def apply(expr: Expression):Expression = {
        print(expr)
        val beta = β(expr)
        if (beta != expr)
            apply(beta)
        else {
            val eta = η(expr)
            if (eta != expr) print(eta)
            eta
        }
    }

    def α(expr: Expression, conflicting: Set[Variable]): Expression = {
//        println(s"α $expr")
        expr match {
            case Variable(_) => expr

            case Lambda(arg, body) =>
                if (conflicting contains arg) {
                    val newArg = _rename(arg, conflicting ++ expr.boundVars)
                    Lambda(newArg, Substitution(arg, newArg)(body))
                } else
                    Lambda(arg, α(body, conflicting))

            case Application(funcExpr, argExpr) =>
                Application(α(funcExpr, conflicting), α(argExpr, conflicting))
        }
    }

    // More of a helper method than not
    def _rename(name:Variable, conflicts:Set[Variable]): Variable = {
//        println(s"Renaming $name")
        val alpha = Variable(name + "'")
        if (conflicts contains alpha)
            _rename(alpha, conflicts)
        else
            alpha
    }

    def β(expr: Expression): Expression = {
//        println(s"β: $expr")
        expr match {

            case Application( Lambda(name, body), argExpr) => Substitution(name, argExpr)(body)


            case Application(a, b) => {
                val left = Application(β(a), b)
                if (left != expr)
                    left
                else
                    Application(a, β(b))
            }

            case Lambda(arg, body) => Lambda(arg, β(body))
            case _ => expr
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
    def evaluate(expr: Expression): Expression = {
        println(expr)
        val beta = β(expr)
        if (beta != expr)
            evaluate(beta)
        else {
            val eta = η(expr)
            if (eta != expr) println(eta)
            eta
        }
    }

}
