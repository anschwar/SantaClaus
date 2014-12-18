package de.hska.project;

/* Solving the Santa Claus Problem (John Trono, 1994) with Java J2SE 5.0
 *
 * (c) 2006 Peter Steiner <peter.steiner@schlau.ch> http://pesche.schlau.ch
 */


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;


public class SantaClausTC2 {

    // helper variables for program termination and output
    private volatile boolean kidsStillBelieveInSanta = true;
    private final Semaphore disbelief = new Semaphore(0);
    private final static int END = 100;
    private AtomicInteger elfCounter = new AtomicInteger(1);
    private AtomicInteger counter = new AtomicInteger(1);
    private static Random generator = new Random();

    // problem dimensions
    private final static int NUMBER_OF_REINDEER = 18;
    private final static int NUMBER_OF_ELVES = 20;
    private final static int ELVES_NEEDED_TO_WAKE_SANTA = 3;

    // synchronisation variables
    private final Semaphore queueElves;
    private final CyclicBarrier threeElves;
    private final CyclicBarrier elvesAreInspired;
    private final CyclicBarrier allReindeers;
    private final Semaphore santasAttention;
    private final static int LAST_REINDEER = 0;                      // compares to CyclicBarrier.await()
    private final static int THIRD_ELF = 0;                      // compares to CyclicBarrier.await()

    private long duration = 0;

    class Reindeer implements Runnable {

        int id;

        Reindeer(int id) {
            this.id = id;
        }

        public void run() {
            while (kidsStillBelieveInSanta) {
                try {
                    // wait until christmas comes
                    Thread.sleep(generator.nextInt(50));

                    // only all reindeers together can wake Santa
                    int reindeer = allReindeers.await();
                    // the last reindeer to return to North Pole must get Santa
                    if (reindeer == LAST_REINDEER) {
                        santasAttention.acquire();
                        if (counter.incrementAndGet() == END) {
                            kidsStillBelieveInSanta = false;
                            disbelief.release();
                        }
                        santasAttention.release();
                    }
                } catch (InterruptedException e) {
                    // thread interrupted for program cleanup
                } catch (BrokenBarrierException e) {
                    // another thread in the barrier was interrupted
                }
            }
        }
    }

    class Elf implements Runnable {

        int id;

        Elf(int id) {
            this.id = id;
        }

        public void run() {
            while (kidsStillBelieveInSanta) {
                try {
                    Thread.sleep(generator.nextInt(50));
                    // no more than three elves fit into Santa's office
                    queueElves.acquire();

                    // wait until three elves have a problem
                    int elf = threeElves.await();

                    // the third elf acts for all three
                    if (elf == THIRD_ELF) santasAttention.acquire();
                    if (elf == THIRD_ELF) elfCounter.incrementAndGet();
                    elvesAreInspired.await();
                    if (elf == THIRD_ELF) santasAttention.release();

                    // other elves that ran out of ideas in the meantime
                    // may now gather and wake santa again
                    queueElves.release();
                } catch (InterruptedException e) {
                    // thread interrupted for program cleanup
                } catch (BrokenBarrierException e) {
                    // another thread in the barrier was interrupted
                }
            }
        }
    }

    public SantaClausTC2() {
        long start = System.currentTimeMillis();
        // use a fair semaphore for Santa to prevent that a second group
        // of elves might get Santas attention first if the reindeer are
        // waiting and Santa is consulting with a first group of elves.
        santasAttention = new Semaphore(1, true);
        queueElves = new Semaphore(ELVES_NEEDED_TO_WAKE_SANTA, true); // use a fair semaphore
        threeElves = new CyclicBarrier(ELVES_NEEDED_TO_WAKE_SANTA);
        elvesAreInspired = new CyclicBarrier(ELVES_NEEDED_TO_WAKE_SANTA);
        allReindeers = new CyclicBarrier(NUMBER_OF_REINDEER);

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_ELVES; ++i)
            threads.add(new Thread(new Elf(i)));
        for (int i = 0; i < NUMBER_OF_REINDEER; ++i)
            threads.add(new Thread(new Reindeer(i)));
        for (Thread t : threads)
            t.start();

        try {
            // wait until !kidsStillBelieveInSanta
            disbelief.acquire();
            duration = System.currentTimeMillis() - start;
            for (Thread t : threads)
                t.interrupt();
            for (Thread t : threads)
                t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public long getDuration() {
        return duration;
    }

    public int getElvesHelped() { return elfCounter.intValue(); }
}