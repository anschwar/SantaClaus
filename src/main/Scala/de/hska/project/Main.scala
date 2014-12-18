package de.hska.project

/**
 * Execution and measurement of the different version
 */
object Main {

  def main(args: Array[String]): Unit = {
    val t0 = System.currentTimeMillis()

    println("Starting STM Test Case 1")
    val app1 = new SantaClausForeverTC1
    var d1: Long = 0
    var elvesHelped1: Int = 0
    for (i <- 1 to 20) {
      app1.start() match {
        case (duration, elvesHelped) =>
          d1 += duration
          elvesHelped1 += elvesHelped
      }
    }
    println( elvesHelped1 + " elf groups helped")
    println(d1 / 20 + " ms")

    println("Starting STM Test Case 2")
    val app2 = new SantaClausForeverTC2
    var d2: Long = 0
    var elvesHelped2: Int = 0
    for (i <- 1 to 20) {
      app2.start() match {
        case (duration, elvesHelped) =>
          d2 += duration
          elvesHelped2 += elvesHelped
      }
    }
    println( elvesHelped2 + " elf groups helped")
    println(d2 / 20 + " ms")

    println("Starting STM Test Case 3")
    val app3 = new SantaClausForeverTC3
    var d3: Long = 0
    var elvesHelped3: Int = 0
    for (i <- 1 to 20) {
      app3.start() match {
        case (duration, elvesHelped) =>
          d3 += duration
          elvesHelped3 += elvesHelped
      }
    }
    println( elvesHelped3 + " elf groups helped")
    println(d3 / 20 + " ms")

    println("Starting STM Test Case 4")
    val app4 = new SantaClausForeverTC4
    var d4: Long = 0
    var elvesHelped4: Int = 0
    for (i <- 1 to 20) {
      app4.start() match {
        case (duration, elvesHelped) =>
          d4 += duration
          elvesHelped4 += elvesHelped
      }
    }
    println( elvesHelped4 + " elf groups helped")
    println(d4 / 20 + " ms")

    println("Starting STM Test Case 5")
    val app5 = new SantaClausForeverTC5
    var d5: Long = 0
    var elvesHelped5: Int = 0
    for (i <- 1 to 20) {
      app5.start() match {
        case (duration, elvesHelped) =>
          d5 += duration
          elvesHelped5 += elvesHelped
      }
    }
    println( elvesHelped5 + " elf groups helped")
    println(d5 / 20 + " ms")

    println("The whole process took " + (System.currentTimeMillis() - t0) + " ms")
  }
}
