[![Build Status](https://travis-ci.org/RutledgePaulV/a-maze-ing.svg)](https://travis-ci.org/RutledgePaulV/a-maze-ing)

##a-maze-ing
This project is an implementation of prim's algorithm applied to maze generation. It uses a generic graph
structure along with two custom types (one for the data associated with vertices and one for the data 
associated with edges) in order to track the state of the algorithm as it progresses.

Something else that is kind of neat is that the way I designed it you don't necessarily need to render to
a rectangular grid. So, I should be able to seed the nodes in any shape and still create a working maze.


## Why scala?
I've been spending a lot of time learning scala lately and it has some features that I thought could make 
working with graphs pretty interesting. Namely case classes, pattern matching, and some simple filters allow 
me to track edges in a reasonably efficient way without a lot of boilerplate code and since I let them all 
reference the graph instance to which they're assigned you can have some nice convenient methods on the vertices
and edges themselves (something which is sorely lacking in some popular graph databases).

## Basic (rectangular)
![Image of rectangular at 10000 nodes](https://lh3.googleusercontent.com/E01TYIOy6URW2JFlppDKDnf75SKl1uqCvJgvYHuiv2w=w970-h951-no)

## Intermediate (multilevel)
![Floor 1 of 3](https://lh3.googleusercontent.com/kfX5J2a7bWSmI0dj9KYDfNG-_ZlLcM3-KfOTc1I-TpU=w911-h907-no)
![Floor 2 of 3](https://lh3.googleusercontent.com/z7k_44r1s7uqysEpCF2WnCIMqAdBispqPe9-oG-PFOQ=w911-h907-no)
![Floor 3 of 3](https://lh3.googleusercontent.com/DDD_zLjdyvSEtvxbuGRYZ7ahnBgTUcam5DODGoUV2SA=w904-h907-no)
