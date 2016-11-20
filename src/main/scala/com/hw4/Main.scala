package com.hw4

import scala.io.StdIn
import Utils.instructions
import com.hw4.parser.LambdaExprParser

/**
  * Created by krbalmryde on 11/18/16.
  */

object Main extends App {

    val exprParser = new LambdaExprParser()

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

            // Something silly
            case "hello" => {
                println("Hello, HW4!")

                println("Things to look for in Combinators")
                for ( wrd <- List("StdTokenParsers", "StdLexical", "PackratParsers"))
                    println(s"\t$wrd")

                println("You should extend StdTokenParsers")
                println("Implement all of its abstract methods")
                println("Specify which characters in the input string are delimiters")
            }

            // Acknowledge the lambda symbols
            case "λ" => println("ooh the Greek letter, lambda! Fancy")
            case "lambda" => println("did you know the Greek letter is λ ?")

            case input => {
                val result = new LambdaExprParser()(input)
                println(result)
            }
        }






    }

}
