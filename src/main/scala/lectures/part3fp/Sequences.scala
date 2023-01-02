package lectures.part3fp

import scala.util.Random

object Sequences extends App {

  //Seq
  val aSequence: Seq[Int] = Seq(1,3,2,4)
  println((aSequence))
  println(aSequence.reverse)
  println(aSequence(2))
  println(aSequence ++ Seq(7,6,5))
  println(aSequence.sorted)

  // Ranges
  val aRange: Seq[Int] = 1 until 10
  aRange.foreach(println)
  (1 to 10).map(x => println("Hello"))

  // Lists
  val aList = List(1,2,3,4)
  val prepended = 42 +: aList :+ 89
  println(prepended)

  val apples5 = List.fill(5)("apple")
  println(apples5)
  println(aList.mkString("-|-"))

  // arrays
  val numbers = Array(1,2,3,4)
  val threeElements = Array.ofDim[String](3)
  println(threeElements)
  threeElements.foreach(println)

  // mutation
  numbers(2) = 0 // syntax sugar for numbers.update(2,0)
  println(numbers.mkString(" "))

  val numbersSeq: Seq[Int] = numbers
  println(numbersSeq)

  // vectors
  val vector: Vector[Int] = Vector(1,2,3)
  println(vector)

  // vectors vs lists
  val maxRuns = 1000
  val maxCapacity = 1000000

  def getWriteTime(collection: Seq[Int]): Double = {
    val r = new Random
    val times = for {
      it <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      collection.updated(r.nextInt(maxCapacity), 0)
      System.nanoTime() - currentTime
    }
    times.sum * 1.0 / maxRuns
  }

  val numbersList = (1 to maxCapacity).toList
  val numbersVector = (1 to maxCapacity).toVector

  // advantage of list: keep reference to tails
  // disadvantage: updating an element in the middle takes long time
  println(getWriteTime(numbersList))
  // advantage of vectors: depth of the tree is small
  // disadvantage: needs to replace an entire 32-elemnt chunk
  println(getWriteTime(numbersVector))


}
