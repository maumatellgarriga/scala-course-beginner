package lectures.part2oop

object MethodNotations extends App {
  class Person(val name: String, favoriteMovie: String, val age: Int = 0) {
    def likes(movie: String): Boolean = movie == favoriteMovie
    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}"
    def unary_! : String = s"${name}, what the heck?!"
    def isAlive: Boolean = true
    def apply(): String = s"Hi, my name is $name and I like $favoriteMovie."
    def +(nickName: String): Person = new Person(s"$name ($nickName)", this.favoriteMovie)
    def unary_+ : Person = new Person(name, favoriteMovie, age + 1)
    def learns(something: String): String = s"$name learns $something"
    def learnsScala : String = this.learns("Scala")
    def apply(times: Int): String = s"$name watched $favoriteMovie $times times."
  }

  val mary = new Person("Mary", "Inception")
  println(mary.likes("Inception"))
  println(mary likes "Inception")

  val tom = new Person("Tom", "Fight Club")
  println(mary + tom)
  print(mary.+(tom))

  println(1 + 2)
  println(1.+(2))

  println(!mary)
  println(mary.unary_!)

  println(mary.isAlive)

  println(mary.apply())
  println(mary())

  // 1. Overload the + operator with a nickname
  println((mary + "the one").name)

  // 2. Add an age to the Person class.
  //    Add a unary + operator => new person with the age +1
  val marc = new Person("Marc", "Star Wars", 29)
  println((+marc).age)

  // 3. Add a "learns" method in the Person class => "Mary learns Scala"
  //    Add a learnsScala method, calls learns method with "Scala".
  println(marc.learnsScala)

  // 4. Overload the apply method
  //    mary.apply(2) => "Mary watched Inveption 2 times"
  println(mary.apply(2))
}
