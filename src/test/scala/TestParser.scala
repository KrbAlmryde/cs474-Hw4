/**
  * Created by krbalmryde on 11/18/16.
  */
import com.hw4.parser.LambdaParser
import org.scalatest._


class TestParser extends FunSuite with Matchers {
    test("This test should fail because it has nothing with which to pass") {
        val itsFalse = false
        assert(itsFalse)
    }

//    test ("parse free variables") {
//        val parser = new LambdaParser()
//        parser.parse("x y z")
//    }

}
