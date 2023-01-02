package lectures.part2oop

object OOBasics extends App {
  val person = new Person("Miriam", 27)
  println(person)
  println(person.age)
  person.getName()
  person.greet("Daniel")
}

class Person(name: String, val age: Int) {
  val x = 2
  val personName: String = name

  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")

  def getName(): Unit = println(personName)
}

class Writer(firstName: String, surname: String, val year: Int) {
  def fullName: String = s"$firstName $surname"

}

class Novel(name: String, releaseYear: Int, author: Writer) {
  def authorAge: Int = releaseYear - author.year
  //def isWrittenBy(author: String): Boolean = this.author == author
  def copy(newReleaseYear: Int): Novel = new Novel(name, newReleaseYear, author)
}

class Counter(val count: Int) {
  def incrementCounter: Counter = new Counter(this.count + 1)
  def decrementCounter: Counter = new Counter(this.count - 1)
  def incrementCounter(n: Int): Counter = {
    if (n<=0) this
    else incrementCounter.incrementCounter(n-1)
  }
  def decrementCounter(n: Int): Counter = {
    if (n<=0) this
    else decrementCounter.decrementCounter(n-1)
  }
}

