SantaClaus
==========

Solving the Santa Claus Problem with Java and Scala

This project was used to compare 2 implementations of the santa claus problem

The Java Version is based on the implementation which can be retrieved here:
http://pesche.schlau.ch/files/santa/SantaClaus.java.html
It was slightly modified to match the testcases

The Scala implementation was ported from an existing haskell solution which can be found here:
https://www.fpcomplete.com/school/advanced-haskell/beautiful-concurrency/4-the-santa-claus-problem

The comparision of both versions had two criterias:
1. how long is the average runtime of any implementation. Whereby the time was stopped after Santa handled 100 reindeer requests. 
2. How many elf groups were processed by santa in this time.
