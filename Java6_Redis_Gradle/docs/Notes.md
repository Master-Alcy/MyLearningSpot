# Notes

## On Multi-Threading

Three key rules: ***Atomic***, ***Memory Visibility***, ***Ordering***  

> **synchronized** and **lock** guaranteed one thread one time on this block of code (Atomic)  
> **volatile** provides Memory Visibility  
> About three keys words and **happens-before rule** makes sure the Ordering

## volatile

1. If one thread wrote a field, and one thread read this field, then write 
happens before read!
2. Once this field is changed, it's visible to all threads
3. No re-Ordering
4. Cannot guaranteed Atomic
    1. synchronized
    2. lock
    3. AtomicInteger
5. Guaranteed the lines before it is done before volatile
6. Guaranteed the lines after it is executed after volatile
7. Though the ordering of these lines are not guaranteed

### volatile application

1. Write to field is not depend on current value
2. This field is not included in other field's functions

In conclusion, volatile field is independent of any status, and use 
it when Atomic is secured.