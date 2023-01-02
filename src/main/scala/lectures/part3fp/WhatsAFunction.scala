package lectures.part3fp

object WhatsAFunction extends App {

  // use functions as first class elements

  val doubler = new MyFunction[Int, Int] {
    override def apply(element:  Int): Int = element * 2
  }

  val concatenator: (String, String) => String = new Function2[String, String, String] {
    override def apply(v1: String, v2: String): String = v1 + v2
  }

  // Function1[Int, Function1[Int, Int]]
  val superAdder: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
    override def apply(x: Int): Int => Int = new Function1[Int, Int] {
      override def apply(y: Int): Int = x + y
    }
  }

  val adder3 = superAdder(3)

  println(doubler(3))
  println(adder3(4))
  println(superAdder(3)(4))  // curried function

  println(concatenator("Hello ", "Scala"))
}

trait MyFunction[A, B] {
  def apply(element: A): B
}