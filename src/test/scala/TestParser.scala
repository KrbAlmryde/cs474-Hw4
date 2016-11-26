/**
  * Created by krbalmryde on 11/18/16.
  */
import com.hw4.evaluator.LambdaEvaluator
import com.hw4.parser.{Application, Lambda, LambdaParser, Variable}
import org.scalatest._


class TestParser extends FunSuite with Matchers {
    val evaluator = new LambdaEvaluator(false, false)
    val parser = new LambdaParser()

    test("no distinction between lambda x \\x λx") {
        assert( evaluator(parser.parse("\\x.x")).toString == "λx.x")
        assert( evaluator(parser.parse("lambda x.x")).toString == "λx.x")
        assert( evaluator(parser.parse("λx.x")).toString == "λx.x")
    }

    test ("Free variable: Y") {
        evaluator(parser.parse("\\f.f(f y) (\\x.x)(\\x.x)"))
        assert( evaluator.freeVariables contains Variable("y") )
        evaluator.freeVariables.clear() // so we dont muck up the scope
    }

    test("Free variables: square 3"){
        evaluator(parser.parse("(lambda x.(lambda z.(x z)) 3) square"))
        assert(evaluator.freeVariables == Set(Variable("square"), Variable("3")))
        evaluator.freeVariables.clear() // so we dont muck up the scope
    }

    test("Bound variables: n, y, y', x") {
        evaluator.boundVariables.clear() // juuuust in case

        evaluator(parser.parse("(λn.λx.λy.x(n x y)) (λn.λx.λy.x(n x y)) (λx.λy.x y)"))
        assert(evaluator.boundVariables == Set(Variable("n"), Variable("y"), Variable("y'"), Variable("x")))
    }

    test("beta reduction: (\\a.\\b.b a) c") {
        val result = evaluator(parser.parse("(\\a.\\b.b a) c"))
        assert(result.toString == "λb.b c")
    }

    test("Test eta conversion") {
        assert( evaluator(parser.parse("\\x.abs x")).toString == "abs")
    }


}
