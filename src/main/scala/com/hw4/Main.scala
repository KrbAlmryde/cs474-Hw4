package com.hw4

import scala.io.StdIn
import Utils.instructions
import com.hw4.evaluator.LambdaEvaluator
import com.hw4.parser.{Expression, LambdaParser, Variable}

/**
  * Created by krbalmryde on 11/18/16.
  */

object ExprPrs extends LambdaParser {

    def main(args: Array[String]): Unit = {
        val evaluator = new LambdaEvaluator()
        println(instructions)

        while(true) {
            StdIn.readLine(s"${Console.BOLD}>: ${Console.WHITE}") match {

                // Quit the Interpreter
                case "q"|"quit" => {
                    println(s"${Console.YELLOW}Thanks for playing!")
                    System.exit(0)
                }

                // Display the Intructions
                case "h" | "help"  => println(instructions)


                // Parse the actual input!
                case input:String => {
                    parseAll(expression, input) match {
                        case Success(result: Expression, _) => {
                            println(s"\nParsed: ${evaluator(result)}")
                        }
                        case err:NoSuccess => println(s"Malformed input: " + err )
                    }
                }
            }
        }

    }
}
