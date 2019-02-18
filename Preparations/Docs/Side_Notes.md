# Side Note

## Chapter 1

* Coding Style
  * Meaningful names
  * spacing
  * braces
* Code Hobby, Bug Free
  * Error checking
  * Boundary conditions
  * Self debug on write page
* Communication
  * Let interviewer understand you
  * write and talk
* Testing
  * Offer test cases
  * Cover all cases
* General
  * Understand the questions you did
  * Look back a few days later, do question again (For Core Algorithms)
  * If don't know, show your ideas and ask for hints
  * Follow Up: Understand questions' conditions
* Algorithm
  * Can't remember
  * Dynamic Programing
  * Seen it, but don't know how to do it
  * Fail on new questions
  * Which solution is right
  * Must O(n)? Can I do with O(nlogn)?
  * How many questions do I need before interview
    1. Know Basic Points:
       1. Intro to Algorithm Interview & Coding Style
       2. **Binary Search**
       3. **Binary Tree & Divide Conquer**
       4. **Breadth First Search**
       5. **Depth First Search**
       6. **Linked List & Array**
       7. **Hash & Heap**
       8. Two Pointers
       9. Dynamic Programming
    2. Quantity: Around 100 - 200
    3. Quality: avg upload times. Try to AC in (1-3) submit.
  * Conclude similar questions
  * Find model code
* Interview is not exam, talk and solve the question but don't judge interviewer
* dfs: O(answer numbers * time build each answer)

### Some leetcode numbers

I Feel I can get questions from leetcode directly.

Chapter 1: ~~13~~ / 18, 17, 594
Chapter 2: 458, 141, 14, 447, 183, 159, 75, 74, 62, 437 / 462, 459, 254, 617, 585, 460, 414, 61, 38 / 457, 160, 63

### Questions

* **78 - Subsets - Medium**
  * O(2^n * n)
* **90 - Subsets II - Medium** (unsolved)

## Chapter 2

* Binary Search
    * Time complexity
    * recursion or non-recursion
    * **704 - Leetcode**
    * T(n) = T(n/2) + O(1) = O(log n)
    * A question can use O(1) time to shrink N problems into N/2 problems
    * A question use O(n) time to shrink N problems into N/2 problems
    * T(n) = T(n/2) + O(n) = T(n/4) + O(n/2) + O(n) = O(n) + O(n/2) + O(n/4) + T(n/8) = O(n + n/2 + n/4 + n/8 + ...) + T(1) &#8776; O(2n - 1) &#8776; O(n)
* Time Complexity in Coding Interview (**Use O() to find the algorithm needed**)
    * O(1)  very rare
    * O(log n)  almost all **binary search**
    * O(&radic;n)   Prime factor decomposition (360=2*2*2*3*3*5)
    * O(n log n)  **Need sorting** or **heap-like data structure**
    * O(n<sup>2</sup>)  array, brute force, **DP**
    * O(n<sup>3</sup>)  array, brute force, **DP**
    * O(2<sup>n</sup>)  Search relates to *Combination*
    * O(n!)  Search relates to *Arrangement*
* The Algorithm better than O(n) almost always O(log n) binary search
    * recursion or while loop? R: recursion, W: while loop, B: both work
        1. Interviewer requires to not use recursion
        2. non-recursion might make it to more complex
        3. depth too high?
        4. is it the point of this interview to use recursion
        5. while loop is better than recursion 
            * stack memory is small
            * recursion might leads to stack overflow
        *. **Ask Interviewer**
* Things need to note for binary search
    * Dead Loop
    * where shall it end
    * where the pointer shall change to
* Level of Understanding:
    1. OOXX: OOOOO...OOXX...XXXXX, find OX.
        * 704, 278, 34, 702
    2. half-half: find the part with solution and cut
        * 153, 33, 81, 162, 852
    3. BS on Answer: find the max and min satisfy certain condition
        * 69, 183 - wood cut, 437 - copy books
    * Or:
        1. Binary Search on Index
            1. OOXX
            2. Half Half
        2. Binary Search on Result