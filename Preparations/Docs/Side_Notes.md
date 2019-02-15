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
    * $$ T(n) = T(n/2) + O(n) \\ = T(n/4) + O(n/2) + O(n) \\ = O(n) + O(n/2) + O(n/4) + T(n/8) \\ = O(n + n/2 + n/4 + n/8) \\ $$