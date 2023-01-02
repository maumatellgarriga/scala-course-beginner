package lectures.part3fp

import java.util.Random

object Options extends App {

  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None

  println(myFirstOption)
  println(noOption)

  //unsafe APIs
  def unsafeMethod(): String = null
  // val result = Some(null) //WRONG
  val result = Option(unsafeMethod()) //Some or None
  println(result)

  // chained methods
  def backupMethod(): String = "A valid result"
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))

  // Design unsafe APIs
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("A valid result")

  val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()
  println(betterChainedResult)
  // functions on Options
  println(myFirstOption.isEmpty)
  println(myFirstOption.get) //UNSAFE - DO NOT USE IT

  // map, flatMap, filter
  println(myFirstOption.map(_*2))
  println(myFirstOption.filter(_ > 10))
  println(myFirstOption.flatMap(x => Option(x * 10)))

  // for-comprehensions



  val config: Map[String, String] = Map(
    // fetched from elsewhere
    "host" -> "234.54.64",
    "port" -> "80"
  )
  class ConnectionClass {
    def connect = "Connected" //connect to some server
  }
  object ConnectionObject {
    val random = new Random(System.nanoTime())
    def apply(host: String, port: String): Option[ConnectionClass] =
      if (random.nextBoolean()) Some(new ConnectionClass)
      else None
  }

  // establish a connection and call connect method
  val host = config.get("host")
  val port = config.get("port")
  /* equal to connection val
  if (h != null)
    if (p != null)
      return ConnectionObject.apply(h, p))
  return null
  */
  val connection = host.flatMap(h => port.flatMap(p => ConnectionObject.apply(h, p)))
  /* equal connectionStatus val
  if (c != null)
    return c.connect
  return null
  */
  val connectionStatus = connection.map(c => c.connect)
  // if (connectionStatus == null) println(None) else println(Some(ConnectionStatus.get))
  println(connectionStatus)
  /* equals to foreach print
  if (status != null)
    println(status)
  */
  connectionStatus.foreach(println)

  // another solution, chained calls solution
  config.get("host")
    .flatMap(host => config.get("port")
      .flatMap(port => ConnectionObject(host, port))
      .map(connection => connection.connect))
    .foreach(println)

  // another solution, for-comprehensions
  val forConnectionStatus = for {
    host <- config.get("host")
    port <- config.get("port")
    connection <- ConnectionObject(host, port)
  } yield connection.connect
  forConnectionStatus.foreach(println)


}
