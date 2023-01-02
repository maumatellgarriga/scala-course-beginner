package playground

import math.Fractional.Implicits.infixFractionalOps
import math.Integral.Implicits.infixIntegralOps
import math.Numeric.Implicits.infixNumericOps


abstract class MyList[+A] {

  /*
    head = first element of the list
    tail = remainder of the list
    isEmpty =
    add(int) => new list with element added
    toString => string representation of the list
    */
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  def printElements: String
  override def toString: String = "[" + printElements + "]"

  //higher-order functions
  def map[B](transformer: A => B): MyList[B]
  def flatMap[B](transformer: A => MyList[B]): MyList[B]
  def filter(predicate: A => Boolean): MyList[A]

  // concatenation
  def ++[B >: A](list: MyList[B]): MyList[B]

  //hofs
  def forEach(f: A => Unit): Unit
  def sort(compare: (A, A) => Int): MyList[A]
  def zipWith[B, C](list: MyList[B], f: (A, B) => C): MyList[C]
  def fold[B](start: B)(operator:(B, A) => B): B
}

case object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] =  throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)
  def printElements: String = ""
  def map[B](transformer: Nothing => B): MyList[B] = Empty
  def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty
  def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = Empty

  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list

  def forEach(f: Nothing => Unit): Unit = ()
  def sort(compare: (Nothing, Nothing) => Int): MyList[Nothing] = Empty
  def zipWith[B, C](list: MyList[B], f: (Nothing, B) => C): MyList[C] =
    if (!list.isEmpty) throw new RuntimeException("Lists do not have the same lengths.")
    else Empty

  def fold[B](start: B)(operator: (B, Nothing) => B): B = start
}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyList[B] = new Cons(element, this)
  def printElements: String = {
    if(t.isEmpty) "" + h
    else s"$h ${t.printElements}"
  }
  def map[B](transformer: A => B): MyList[B] = {
    new Cons(transformer(h), t.map(transformer))
  }
  def filter(predicate: A => Boolean): MyList[A] = {
    if(predicate(h)) new Cons(h, t.filter(predicate))
    else t.filter(predicate)
  }
  /*
  [1,2] ++ [3,4,5]
  = new Cons(1, [2] ++ [3,4,5])
  = new Cons(1, new Cons(2, Empty ++ [3,4,5]))
  = new Cons(1, new Cons(2, [3,4,5]))

 */
  def ++[B >: A](list: MyList[B]): MyList[B] =  new Cons(h, t ++ list)

  def flatMap[B](transformer: A => MyList[B]): MyList[B] =
    transformer(h) ++ t.flatMap(transformer)

  def forEach(f: A => Unit): Unit = {
    f(h)
    t.forEach(f)
  }

  def sort(compare: (A, A) => Int): MyList[A] = {
    def insert(x: A, sortedList: MyList[A]): MyList[A] =
      if (sortedList.isEmpty) new Cons(x, Empty)
      else if (compare(x, sortedList.head) <= 0) new Cons(x, sortedList)
      else new Cons(sortedList.head, insert(x, sortedList.tail))
    val sortedTail = t.sort(compare)
    insert(h, sortedTail)
  }

  def zipWith[B, C](list: MyList[B], f: (A, B) => C): MyList[C] = {
    if (list.isEmpty) throw new RuntimeException("Lists do not have the same lengths.")
    else new Cons(f(h, list.head), t.zipWith(list.tail, f))
  }
  /*
  [1,2,3].fold(0)(+) =
  [2,3].fold(1)(+) =
  [3].fold(3)(+) =
  [].fold(6)(+) =
  6
  */
  def fold[B](start: B)(operator: (B, A) => B): B = {
    t.fold(operator(start, h))(operator)
  }
}

/*
trait MyPredicate[-T] {
  def test(elem: T): Boolean
}

trait MyTransformer[-A,B] {
  def transform(elem: A): B
}
*/

object ListTest extends App {
  val listOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val listOfStrings: MyList[String] = new Cons("Hello", new Cons("scala", Empty))
  val anotherListOfIntegers: MyList[Int] = new Cons(4, new Cons(5, Empty))

  println(listOfIntegers.toString)
  println(listOfStrings.toString)

  println(listOfIntegers.map((element: Int) => element * 2).toString)
  println(listOfIntegers.map(_ * 2).toString) //equal to previous line

  println(listOfIntegers.filter((element: Int) => element % 2 == 0).toString)
  println(listOfIntegers.filter(_ % 2 == 0).toString) //equal to previous line

  println((listOfIntegers ++ anotherListOfIntegers).toString)
  println(listOfIntegers.flatMap((element: Int) => new Cons(element, new Cons(element + 1, Empty))
  ).toString)
  listOfIntegers.forEach(println)

  println(listOfIntegers.sort((x,y)=> y-x))

  println(anotherListOfIntegers.zipWith[String, String](listOfStrings, _ + "-" + _))

  println(listOfIntegers.fold(0)(_ + _))

  // for comprehension
  val combinations = for {
    n <- listOfIntegers
    s <- listOfStrings
  } yield n + "-" + s
  println(combinations)
}