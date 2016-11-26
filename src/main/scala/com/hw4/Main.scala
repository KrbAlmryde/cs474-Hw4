package com.hw4

import scala.io.StdIn
import Utils.instructions
import com.hw4.evaluator.LambdaEvaluator
import com.hw4.parser.LambdaParser

/**
  * Created by krbalmryde on 11/18/16.
  */

object Main extends LambdaParser {


    def main(args: Array[String]): Unit = {

        val evaluator = new LambdaEvaluator(false, false)
        println(instructions)

        while (true) {
            StdIn.readLine(s"${Console.BOLD}>: ${Console.WHITE}") match {

                // Quit the Interpreter
                case "q" | "quit" =>
                    println(s"${Console.YELLOW}Thanks for playing!")
                    return

                // Display the Intructions
                case "h" | "help" =>
                    println(instructions)

                case "v" | "verbose" =>
                    evaluator.verbose = !evaluator.verbose
                    println(s"Verbose mode set to: ${evaluator.verbose}")

                case "d" | "debug" =>
                    evaluator.debug = !evaluator.debug
                    println(s"Debug mode set to: ${evaluator.debug}")

                case "def" | "defs" =>
                    println("definitions are:")
                    evaluator.displayDefined()

                // Parse the actual input!
                case input: String =>
                    val parsed =  Main.parse(input)
                    val result = evaluator(parsed)

                    println(s"\n>: ${Console.BOLD}$result${Console.WHITE} :<")
                    if (evaluator.verbose) {
                        evaluator.report()

                    }


            }
        }

    }
}