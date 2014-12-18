SantaClaus
==========

Solving the Santa Claus Problem with Java and Scala

This project was used to compare 2 implementations of the santa claus problem

The Java Version is based on the implementation which can be retrieved here:
http://pesche.schlau.ch/files/santa/SantaClaus.java.html
It was slightly modified to match the test cases

The Scala implementation was ported from an existing haskell solution which can be found here:
https://www.fpcomplete.com/school/advanced-haskell/beautiful-concurrency/4-the-santa-claus-problem

The comparison of both versions had two criteria:
1. how long is the average runtime of any implementation. Whereby the time was stopped after Santa handled 100 reindeer requests. 
2. How many elf groups were processed by santa in this time.

This project only contains the source files and the sbt files needed if you use sbt. Otherwise u have to download the
smt library manually.
