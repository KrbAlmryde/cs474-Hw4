package com.hw4

import scala.io.StdIn
import Utils.instructions
import com.hw4.evaluator.LambdaEvaluator
import com.hw4.parser.LambdaParser

/**
  * Created by krbalmryde on 11/18/16.
  */

object ExprPrs extends LambdaParser {

    def main(args: Array[String]): Unit = {

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
                case input => {
//                    val result = exprParser.parse(input)

                    // In case we need it
                    // val result = new LambdaParser()(input)
                    // val evaluator = new LambdaEvaluator(result)
                    println(s"Parsed: ${ExprPrs.parse(input)} ")
                }
            }
        }

    }
    val exprParser = new LambdaParser()


}
