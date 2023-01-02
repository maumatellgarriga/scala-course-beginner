package lectures.part2oop

object Generics extends App {

  class MyList[+A] {

    def add[B >: A](element: B): MyList[B] = ???
    /*
    A = Cat
    B = Dog = Animal
    adding a Dog into a list of cats will return a list of Animals
     */
  }
  class MyMap[Key, Value]

  val listOfIntegers = new MyList[Int]
  val listOfStrings = new MyList[String]

  // generic methods
  object MyList {
    def empty[A]: MyList[A] = ???

  }
  //val emptyListOfIntegers = MyList.empty[Int]

  // variance problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // 1. yes, List[Cat] extends List[Animal] = COVARIANCE
  class CovariantList[+A]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  // animalList.add(new Dog) ??? => we return a list o f animals as seen in line 7

  // 2. No = INVARIANCE
  class InvariantList[A]
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal]

  // 3. Hell, no! = CONTRAVARIANCE
  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]

  // bounded types
  class Cage[A <: Animal](animal: A) // class Cage only accepts type parameters A that are subclasses of Animal
  val cage = new Cage(new Dog)

  class Car
  //val newCage = new Cage(new Car) // this will not work because Car is not a subtype of Animal


}
