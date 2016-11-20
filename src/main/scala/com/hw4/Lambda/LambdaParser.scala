package com.hw4.Lambda

/**
  * Created by krbalmryde on 11/19/16.
  */

import scala.util.parsing.combinator._
import scala.util.parsing.combinator.lexical.StdLexical
import scala.util.parsing.combinator.syntactical.StdTokenParsers
import scala.util.parsing.input.CharSequenceReader


trait LambdaParser extends StdTokenParsers with PackratParsers {
    type Tokens = StdLexical
//    val lexical = new StdLexical
    lexical.delimiters ++= Seq("lambda ", "λ", ".", "(", ")", "=")  // the '=' is for assignment...eventually

}

trait Ab01Parser extends Parsers {
    type Elem = Char

    abstract class Parser[T] extends super.Parser[T] {
        def or(right:Parser[T]):Parser[T] = {
            val left = this
            new Parser[T] {
                def apply(in:Input) =
                    left(in) match {
                        case s:Success[T] => s
                        case _ => right(in)
                    }
            }
        }
    }

    def repeat[T](p:Parser[T]) = new Parser[List[T]] {
        def apply(in:Input):Success[List[T]] = {
            p (in) match {
                case Success(t,next) => {
                    val s = apply(next)
                    Success(t::s.get, s.next)
                }
                case _ => Success(Nil, in)
            }
        }
    }

    def charParser(expected:Char) = new Parser[Char] {
        def apply(in:Input):ParseResult[Char] = {
            val c = in.first

            if (c == expected) Success(c, in.rest)
            else Failure(s"Expected $expected got $c ", in)

        }
    }

    def run(s:String):Option[List[Char]] = {
        val input = new CharSequenceReader(s)
        myParser(input) match {
            case Success(list,next) if (next.atEnd) => Some(list)
            case _ => None
        }
    }
    val ab01 = charParser('a') or charParser('b') or charParser('0') or charParser('1')
    val myParser = repeat(ab01)


}
