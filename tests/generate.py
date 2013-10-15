#!/usr/bin/env python
# Put this file in a directory to make all your test cases, then run it.
 
import sys
import random
from functools import wraps
 
def save_to(filename_format):
    """Makes print statements go to a file with the given name
 
    Parameters passed to the function are formatted into the filename.
    """
    def decorator(f):
        @wraps(f)
        def inner(*args, **kwargs):
            real_stdout = sys.stdout
            filename = filename_format.format(*args, **kwargs)
            with open(filename, 'w') as sys.stdout:
                ret = f(*args, **kwargs)
            sys.stdout = real_stdout
            return ret
        return inner
    return decorator
 
@save_to('minimal.in')
def minimal():
    print 2
    print "Waypoints "
    print "Path 0 1"
 
@save_to('one-waypoint.in')
def onewaypoint():
    """A small graph where going through the waypoint is preferable."""
    print 3
    print "Waypoints 1"
    print "Path 0 1 2"
    print "Path 0 2"
    print "Weight 0 1 6"
    print "Weight 1 2 6"
    print "Weight 0 2 11"
 
@save_to('no-path.in')
def nopath():
    print 4
    print "Waypoints 1 2"
    print "Path 0 1"
    print "Path 2 3"
 
@save_to('simple-2.in')
def simple2():
    """A graph where going through two of the waypoints is preferable.
              6
    *--*--*--*--*
     \         / 8
      \--*-*--/
       \     / 12
        --*--
    """
    print 8
    print "Waypoints 1 2 3 4 5 6"
    print "Path 0 2 3 4 7"
    print "Path 0 1 5 7"
    print "Path 0 6 7"
    print "Weight 4 7 6"
    print "Weight 5 7 8"
    print "Weight 6 7 12"
 
@save_to('simple-1.in')
def simple1():
    """A graph where going through one of the waypoints is preferable.
              6
    *--*--*--*--*
     \         / 9
      \--*-*--/
       \     / 12
        --*--
    """
    print 8
    print "Waypoints 1 2 3 4 5 6"
    print "Path 0 2 3 4 7"
    print "Path 0 1 5 7"
    print "Path 0 6 7"
    print "Weight 4 7 6"
    print "Weight 5 7 9"
    print "Weight 6 7 12"
 
@save_to('simple-3.in')
def simple3():
    """A graph where going through three of the waypoints is preferable.
 
    *--*--*--*--*
     \         / 9
      \--*-*--/
       \     / 12
        --*--
    """
    print 8
    print "Waypoints 1 2 3 4 5 6"
    print "Path 0 2 3 4 7"
    print "Path 0 1 5 7"
    print "Path 0 6 7"
    print "Weight 5 7 9"
    print "Weight 6 7 12"
 
@save_to('complete-{0}.in')
def maximal(n):
    """Generate a graph on n vertices where every available edge exists and
    every vertex is a waypoint"""
    print n
    print "Waypoints " + " ".join(map(str, range(1,n-1)))
    # create all edges
    for jump in range(1,n):
        for start in range(min(jump,n-jump)):
            path = range(start, n, jump)
            print "Path", " ".join(map(str, path))
 
if __name__ == '__main__':
    minimal()
    onewaypoint()
    simple1()
    simple2()
    simple3()
    nopath()
    maximal(900)
    #maximal(35)
    #maximal(36)
    #maximal(800)
	
