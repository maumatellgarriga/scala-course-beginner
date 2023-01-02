package lectures.part3fp

import java.awt.print.Book

object TuplesAndMaps extends App {
  // tuples = finite ordered "lists"
  val aTuple = new Tuple2(2, "Hello, Scala") // Tuple2[Int, String] = (Int, String)
  val aTuple2 = (2, "Hello, Scala")

  println(aTuple._1)
  println(aTuple._2)
  println(aTuple.copy(_2 = "goodbye Java"))
  println(aTuple.swap) // ("Hello, Scala", 2)

  //maps = associate keys to values
  val aMap: Map[String, Int] = Map()

  val phoneBook = Map(("Jim", 555), "Daniel" -> 789).withDefaultValue(-1)
  // a -> b syntactic sugar for the tuple (a,b)
  println(phoneBook)

  // map ops
  println(phoneBook.contains("Jim"))
  println(phoneBook.apply("Jim")) // phoneBook("Jim")
  println(phoneBook("Mary"))

  // add pairing
  val newPairing = "Mary" -> 567
  val newPhoneBook = phoneBook + newPairing
  println(newPhoneBook)
  //val phoneBook = newPhoneBook

  // functionals on maps
  // map, flatMap, filter

  println(phoneBook.map(pair => pair._1.toLowerCase() -> pair._2))

  // filterKeys -- deprecated since 2.13
  println(phoneBook.filterKeys(x => x.startsWith("J"))) //lambda sugar to: x => x.startsWith("J")
  println(phoneBook.filter((x, v) => x.startsWith("J")))
  //mapValues -- deprecated since 2.13
  println(phoneBook.mapValues(number => number * 10))
  println(phoneBook.map((k, number) => (k, number * 10)))
  println(phoneBook.map((k, number) => k + number))

  // conversions
  println(phoneBook.toList)
  println(List(("Daniel", 555)).toMap)

  val names = List("Anton", "Jim", "Angela", "Mary", "James")
  println(names.groupBy(name => name.charAt(0)))

  val phoneBook2 = Map(("Jim", 123), ("JIM", 456))
  println(phoneBook2.map(pair => pair._1.toLowerCase() -> pair._2))


  //def addPerson(network: Map[String, String], personName:String) = network[personName] == "someone"
  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] =
    network + (person -> Set())

  def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)
    network + (a -> (friendsA + b)) + (b -> (friendsB + a))
  }

  def unfriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)
    network + (a -> (friendsA - b)) + (b -> (friendsB - a))
  }

  def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] =
      if (friends.isEmpty) networkAcc
      else removeAux(friends.tail, unfriend(networkAcc, person, friends.head))

    val unfriended = removeAux(network(person), network)
    unfriended - person
  }

  val empty: Map[String, Set[String]] = Map()
  val network: Map[String, Set[String]] = add(add(empty, "Bob"), "Mary")
  println(network)
  println(friend(network, "Bob", "Mary"))
  println(unfriend(friend(network, "Bob", "Mary"), "Bob", "Mary"))
  println(remove(friend(network, "Bob", "Mary"), "Bob"))

  val people = add(add(add(network,"Mary"), "Bob"), "Jim")
  println(people)
  val jimBob = friend(people, "Jim", "Bob")
  println(jimBob)
  val testNet = friend(jimBob, "Mary", "Bob")
  println(testNet)

  def nFriends(network: Map[String, Set[String]], person: String): Int =
    if (!network.contains(person)) 0
    else network(person).size

  println(nFriends(testNet, "Bob"))

  def mostFriends(network: Map[String, Set[String]]): String =
    network.maxBy(pair => pair._2.size)._1

  println(mostFriends(testNet))

  def nPeopleWithNoFriends(network: Map[String, Set[String]]): Int =
    network.filter((k,v) => network(k).isEmpty).size // same: network.count(pair => pair._2.isEmpty) === network.count(_._2.isEmpty)

  println(nPeopleWithNoFriends(testNet))

  def socialConnection(network: Map[String, Set[String]], a:String, b:String): Boolean = {
    def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean =
      if (discoveredPeople.isEmpty) false
      else {
        val person = discoveredPeople.head
        if (person == target) true
        else if (consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail)
        else bfs(target, consideredPeople + person, discoveredPeople ++ network(person))
      }

    bfs(b, Set(), network(a) + a)

  }

  println(socialConnection(testNet, "Mary", "Jim"))
  println(socialConnection(network, "Mary", "Bob"))
}
