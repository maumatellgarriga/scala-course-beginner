package lectures.part3fp

object AnonymousFunctions extends App {

  // anonymous function or LAMBDA
  val doubler: Int => Int = (x: Int) => x * 2

  // multiple paramaters in a Lambda
  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b

  // any parameters in a Lambda
  val justDoSomething: () => Int = () => 3

  println(justDoSomething) // function itself
  println(justDoSomething()) // call

  // curly braces with lambdas
  val stringToInt = { (str: String) =>
    str.toInt
  }

  // MOAR syntactic sugar
  val niceIncrementer: Int => Int = (int: Int) => int + 1
  //equivalent to
  val niceIncrementer2: Int => Int = _ + 1

  val niceAdder: (Int, Int) => Int = _ + _ // (a, b) => a + b  .  mandatory to add types after name

  val superAdder: Int => Int => Int = (x: Int) => (y: Int) => x + y
  println(superAdder(3)(4))
}
