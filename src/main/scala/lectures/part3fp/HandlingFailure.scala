package lectures.part3fp

import scala.util.{Success, Failure, Try}

import java.util.Random

object HandlingFailure extends App {

  // create success and failure
  val aSuccess = Success(3)
  val aFailure = Failure(new RuntimeException("Super failure"))

  println(aSuccess)
  println(aFailure)

  def unsafeMethod(): String = throw new RuntimeException("No string for yoU!!")

    // Try objects via the apply method
  val potencialFailure = Try(unsafeMethod())
  println(potencialFailure)

  // syntax sugar
  val anotherPotencialFailure = Try {
    //code that might throw
  }

  // utilities
  println(potencialFailure.isSuccess)

  // orElse
  def backupMethod(): String = "A valid result"
  val fallbackTry = Try(unsafeMethod()).orElse(Try(backupMethod()))
  println(fallbackTry)

  // IF you design the API
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException)
  def betterBackupMethod(): Try[String] = Success("A valid result")
  val betterFallbackTry = betterUnsafeMethod() orElse betterBackupMethod()

  //map, flatMap, filter
  println(aSuccess.map(_ * 2))
  println(aSuccess.flatMap(x => Success(x * 10)))
  println(aSuccess.filter(x => x > 10))
  // it means we can use for-comprehensions

  // exercise
  val hostname = "localhost"
  val port = "8080"
  def renderHTML(page: String) = println(page)

  class Connection {
    def get(url: String): String = {
      val random = new Random(System.nanoTime())
      if (random.nextBoolean()) "<html>...</html>"
      else throw new RuntimeException("Connection interrupted")
    }

    def getSafe(url: String): Try[String] = Try(get(url))
  }

  object HttpService {
    val random = new Random(System.nanoTime())
    def getConnection(host: String, port: String): Connection = {
      if (random.nextBoolean()) new Connection
      else throw new RuntimeException("Connection interrupted")
    }

    def getSafeConnection(host: String, port: String): Try[Connection] = Try(getConnection(host, port))
  }

  // if you get the html from the connection, print it to the console i.e. call renderHTML

  val possibleConnection = HttpService.getSafeConnection(host = hostname, port = port)
  val possibleHTML = possibleConnection.flatMap(connection => connection.getSafe("/home"))
  possibleHTML.foreach(renderHTML)

  // shorthand version
  HttpService.getSafeConnection(hostname, port)
    .flatMap(connection => connection.getSafe("/home"))
    .foreach(renderHTML)

  // for-comprehension version
  for {
    connection <- HttpService.getSafeConnection(hostname, port)
    html <- connection.getSafe("/home")
  } renderHTML(html)

  /* not readable and not scalable
    try {
      connection = HttpService.getSafeConnection(hostname, port)
      try {
        page = connection.getSafe("/home")
        renderHTML(page)
      } catch (another exception)
    } catch (exception)
  */
}
