# beggars_neighbour

This was a simple project to find out what the average number of turns the card game
beggars neighbour would take to complete with the rules that my roommates and I 
played with.

This isn't an accurate way of working this out as it simply simulates several games 
and calculates an average from those, as apposed to doing it mathematically.
However I chose to do it this way because its simpler and I only wanted an 
approximation.

The answer for those who are wondering is betwenn 380 and 390 turns, which with an 
average turn time of 5 seconds would take roughly 30 minutes.

It should be noted that this code was written late at night with little regard for 
quality and so many optimizations can be made, as well as planty of hackish and 
poor code.

## Usage

(use 'beggers-neighbour.core)
(game-loop x)

where x is the number of iterations you wish to run

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
