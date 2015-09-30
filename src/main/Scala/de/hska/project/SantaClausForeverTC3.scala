package de.hska.project

import scala.collection.mutable.ListBuffer
import scala.concurrent.stm._
import scala.language.postfixOps
import scala.util.Random

/**
 * Solving the Santa Claus Problem (John Trono, 1994) with Scala ported from
 * Haskell (see https://www.fpcomplete.com/school/advanced-haskell/beautiful-concurrency/4-the-santa-claus-problem)
 *
 */
class SantaClausForeverTC3() {

  var elfGroupsHelped: Int = 0
  var executionTime: Long = 0

  object Task extends Enumeration {
    type Task = Value
    val Deliver = Value("deliver Toys")
    val Study = Value("meet in my study")
  }

  case class Group(capacity: Int) {

    private val gates = Ref((capacity, Gate(capacity), Gate(capacity)))

    def join() = {
      atomic { implicit transaction =>
        gates() match {
          // in haskell (n_left, g1, g2) <- readTVar tv is a case match!!!
          case (remainingCapacity, entrance, exit) =>
            // queue is full
            if (remainingCapacity == 0) retry
            gates() = (remainingCapacity - 1, entrance, exit)
            (entrance, exit)
        }
      }
    }

    def await() = {
      atomic { implicit transaction =>
        gates() match {
          case (remainingCapacity, entrance, exit) =>
            if (remainingCapacity == 0)
              gates() = (capacity, Gate(capacity), Gate(capacity))
            else
              retry
            (entrance, exit)
        }
      }
    }
  }

  // the class definition is simultaneously the constructor
  case class Gate(capacity: Int, capacityLeft: Ref[Int] = Ref(0)) {

    def passGate(): Unit = {
      atomic { implicit transaction =>
        val size = capacityLeft()
        if (size == 0) retry
        capacityLeft() = size - 1
      }
    }

    def operateGate(): Unit = {
      // Reset the left capacity of the gate
      capacityLeft.single.set(capacity)
      atomic { implicit transaction =>
        val size = capacityLeft()
        if (size > 0) retry
      }
    }
  }

  abstract class Helper() extends Thread {
    val id: Int
    val group: Group

    def doTask()

    def work(): Boolean = {
      try {
        group join() match {
          case (entrance, exit) =>
            Thread sleep randomDelay
            entrance passGate()
            exit passGate()
        }
      } catch {
        // santa retired, all Helper can stop working and leave the queues
        case ie: InterruptedException => Thread.currentThread().interrupt()
          return false
      }
      true
    }

    override def run() = {
      forever(work())
    }
  }

  case class Elf(group: Group, id: Int) extends Helper {
    override def doTask() = println("Elf " + id + " meeting in the study")
  }

  case class Reindeer(group: Group, id: Int) extends Helper {
    override def doTask() = println("Reindeer " + id + " delivering toys")
  }

  case class Santa(elfGroup: Group, reindeerGroup: Group) extends Thread {

    val counter: Ref[Int] = Ref(1)
    val elfCounter: Ref[Int] = Ref(1)

    def waitForHelpers(): Boolean = {
      // lookup if the reindeers are ready
      atomic { implicit transaction =>
        (reindeerGroup await(), Task.Deliver)
        // if not see if there are enough elves
      } orAtomic { implicit transaction =>
        (elfGroup await(), Task.Study)
      } match {
        // if one group is ready get the gates and the task and operate them
        case ((entrance, exit), task) =>
          entrance operateGate()
          exit operateGate()
          if (task == Task.Deliver) {
            val count = counter.single.get
            counter.single.set(count + 1)
            // after certain iterations santa has done his job
            if (counter.single.get >= 100) {
              return false
            }
          } else {
            val elfCount = elfCounter.single.get
            elfCounter.single.set(elfCount + 1)
          }
      }
      true
    }

    override def run(): Unit = {
      executionTime = time(forever(waitForHelpers()))
      elfGroupsHelped = elfCounter.single.get
    }
  }

  // returns a Long between 0 and 1000. This represents the milliseconds a thread should sleep
  def randomDelay(): Long = {
    Random nextInt 50
  }

  // recursively call yourself. But how can we interrupt
  def forever[A](function: => A): Unit = {
    function match {
      case false => // if
      case _ => forever(function)
    }
  }

  // this could be used with a list to store several results. Improve this later
  def time[A](function: => Unit): Long = {
    val start = System.currentTimeMillis
    function
    System.currentTimeMillis - start
  }

  def start(): (Long, Int) = {
    val threads = new ListBuffer[Helper]
    val elfGroup = new Group(3)
    val reindeerGroup = new Group(9)

    val santa = new Santa(elfGroup, reindeerGroup)
    santa start()

    // start the elves
    for (i <- 1 to 20)
      threads += Elf(elfGroup, i)
    // and the reindeers
    for (i <- 1 to 18)
      threads += Reindeer(reindeerGroup, i)

    for (thread <- threads)
      thread start()

    santa join()

    for (thread <- threads)
      thread interrupt()

    for (thread <- threads)
      thread join()

    (executionTime, elfGroupsHelped)
  }
}