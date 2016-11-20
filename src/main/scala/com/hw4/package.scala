package com.hw4

import java.io.File

import scala.util.parsing.combinator.{Parsers, RegexParsers}

/**
  * Created by krbalmryde on 11/19/16.
  */

package object Utils {

    val instructions =
        s"""
          |${Console.YELLOW}
          |λ -- Starting the Lambda calculus Interpreter -- λ
          |${Console.WHITE}
          |To use this tool type an expression such as the following:
          |${Console.BLUE}
          |      lambda x.(lambda y.(x y)) 3) square)
          |            λx.(λy.(x y)) 3) square)
          |            \\x.(\\y.(x y)) 3) square)
          |${Console.WHITE}
          |Available options include:
          |     -v  For additional (re: verbose) information about the expression
          |
          |      Note: Options should follow an expression i.e.,
          |${Console.BLUE}
          |        lambda x.(lambda y.(x y)) 3) square)${Console.RED} -v${Console.BLUE}
          |            λx.(λy.(x y)) 3) square)${Console.RED} -v${Console.BLUE}
          |            \\x.(\\y.(x y)) 3) square)${Console.RED} -v
          |${Console.WHITE}
          |Available commands include:
          |${Console.GREEN}
          |     q | quit ${Console.WHITE}=> Quit the application${Console.GREEN}
          |     h | help ${Console.WHITE}=> Display this help message again${Console.GREEN}
          |     r | rules ${Console.WHITE}=> Display naming conventions and rules${Console.GREEN}
          |     d | def ${Console.WHITE}=> Display user defined variables
          |${Console.YELLOW}
          |λ -- Starting the Lambda calculus Interpreter -- λ
          |
        """.stripMargin
    /**
      * Convenience function which gives me the current working directory
      * @return
      */
    def pwd:String = System.getProperty("user.dir")

    /**
      * Name:
      *     ParseFilesInDir
      *
      * Description:
      *     Recursively parses Files in the local project Resources/ directory producing
      *     an array of Strings containing file paths to each of the source files
      *     found.
      *
      * Source:
      *     This function was adapted from the accepted answer of this StackOverflow question
      *     http://stackoverflow.com/questions/2637643/how-do-i-list-all-files-in-a-subdirectory-in-scala
      *
      * @param dir: a Java File object containing the source to the directory
      * @return Array[String]
      */
    def parseFilesInDir(dir: File): Array[File] = {
        val files = dir.listFiles
        val allFiles = files ++ files.filter(_.isDirectory).flatMap(parseFilesInDir)
        allFiles.filter( f => """.*\.java$""".r.findFirstIn(f.getName).isDefined)
    }


}
