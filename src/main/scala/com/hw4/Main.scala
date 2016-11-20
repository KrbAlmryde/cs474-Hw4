package com.hw4

import scala.io.StdIn
import Utils.instructions
import com.hw4.parser.LambdaParser

/**
  * Created by krbalmryde on 11/18/16.
  */

object Main extends App {

    val exprParser = new LambdaParser()

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
                val result = new LambdaParser()(input)
                println(s"Parsed: $result")
            }
        }






    }

}
