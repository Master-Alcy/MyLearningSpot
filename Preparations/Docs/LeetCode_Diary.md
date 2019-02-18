# Start this Diary on 2019/1/2

## 2019/1/2

* **1 - Two Sum - Easy**
  * HashMap's .containsKey
  * .get and .put
  * Rest is just iterating through
* **2 - Add Two Numbers - Medium**
  * Achieved first acceptance, though slow
  * Implemented with StringBuilder, BigInteger and String[]

## 2019/1/3

* **2 - Add Two Numbers - Medium**
  * Declare root node with dummy
    * `ListNode root = new ListNode(0)`
  * Use `ListNode tail = root` to hold value and act like 'temp'
  * Use Divide and Conquer to reduce the Sum-BigInteger to Sum-At-Each-Stage-Value:
    1. `stageSum = num1 + num2 + thisRound;`
    2. `thisRound = stageSum / 10;`
  * boolean_expression ? if_yes_return_this : else_return_this
* **146 - LRU Cache - Hard**
  * Cannot finish myself, due to lack of the knowledge of doubly linked list
  * I think I now understand doubly linked list's structure
  * Key Points:
    * Use HashMap to keep references
    * DoublyLinkedList to achieve O(1) get and put because the node can remove itself without other references
    * DoublyLinkedList need to care its resource leak
  * Need to redo this with paper sometime later
* **20 - Valid Parentheses - Easy**
  * First try is slow, trying to speeding up

## 2019/1/4

* **20 - Valid Parentheses - Easy**
  * My Own idea runs in 8ms, Optimal one from discussion board makes it to 5ms
  * Key Points:
    * use switch(char) and push ')' directely.
    * `string.toCharArray()` is a bit slower than using `charAt(index)`
    * Since the String is implemented with an array, the charAt() method is a constant time operation.
* ~~**200 - Number of Islands - Medium - *SKIP***~~
  * No idea, go see help
    * Go learn DFS
    * Go learn BFS
    * Go learn Disjoint Set (Union Find)
* **104 - Maximum Depth of Binary Tree - Easy**
  * Some Knowledge
    * Breadth First Traversal (Or Level Order Traversal)
      * O(w), w is maximum width
    * Depth First Traversals
      * O(h), h is maximum height
        * Inorder Traversal (Left-Root-Right)
        * Preorder Traversal (Root-Left-Right)
        * Postorder Traversal (Left-Right-Root)
    * In terms of Space Complexity:
      * Less Balanced: BFS uses less space
      * More Balanced: DFS uses less space
    * DFS are typically recursive, which requires to call overheads.
    * If we search something closer to root, we would prefer BFS. And if it's closer to a leaf, we would prefer DFS.
  * Key Points:
    * TreeNode is implemented just like doubly linked list, except that instead of .prev and .next, we use .left and .right here
    * For recursion, just increase depth for each node
    * `Deque<Node> queue = new ArrayDeque<>()` This is a better way for both Queue and Stack
    * The non-recursive Iterable maxDepth is nice to keep in mind

## 2019/1/7 (5 & 6 are weekends)

* **684 - Redundant Connection - Medium**
  * UF types: (No.5 has minimal Tree Height and is Thus the optimal one)
    1. Quick-Find
    2. Quick-Union
    3. Weighted Quick-Union
    4. Weighted Quick-Union with path compression
    5. Weighted Quick-Union by rank with path compression
    * Constructor: O(N), Union: O(near 1), Find O(near 1)
  * Techniques:
    * path compression: `parent[node] = parent[parent[node]];`
    * use rank as size (weighted)
* **200 - Number of Islands - Medium - *CONTINUE***
  * First accept with UF, 13.46%. Need to check DFS and BFS.
    * Need to think about `[]` and out of boundary for array
    * UF only modified constructor, where:
      1. Make the count to the number of lands
      2. Map the matrix to array for `parent[current]` and `rank[current]`
    * Method: `numIslands(char[][] grid)` union the point to the right and down
  * Now here is the DFS and BFS:
    * DFS (Flood Fill Algorithm)
      * `private void DFS(char[][] grid, int i, int j, int row, int col)`
      * recursively call until return on boundary
      * mark current land to water
    * BFS:
      * Instead of Recursion, use queue and while loop to go over lands

## 2019/1/8 (Low Efficiency)

* **3 - Longest Substring Without Repeating Characters - Medium**
  * Learning KMP...
    * Understand KMP and its implementation
    * Understand BM, but implementation too long.
    * Due to low efficiency today, need to continue on this question tomorrow

## 2019/1/9 (Low Efficiency DAMMMMIT)

* **3 - Longest Substring Without Repeating Characters - Medium - *CONTINUE***
  * Dynamic Programming Problem:
    * Brute Force in O(n^3). Idea: Check Unique with HashSet();
    * `Set<Character> set = new HashSet<>();`. Iterate once and dynamic check. For char exists in set, move head pointer ahead. For char don't exist, move tail pointer ahead.
    * **Key is to make sure there are all unique and *new* chars between two pointers.**
    * `Map<Character, Integer> map = new HashMap<>();` Save char with it's max unique length
    * `int[] index = new int[256];` ASCII range 256. Optimized
    * Kadane's Algorithm:
      * `current = max(newElement, current + newElement)`
      * if newEle + oldMax smaller than newEle, then dump oldMax and replace with newEle
      * `maxSoFar = max(maxSoFar, current)`
      * try if new current is larger than old sum
      * (need to practice)

## 2019/1/10 (Low Efficiency DAMMMMIT)

* **4 - Median of Two Sorted Arrays - Hard**
  * <https://www.youtube.com/watch?v=LPFhl65R7ww>
  * KeyPoints:
    * Understand how to allocate cut1 and cut2
    * Start from shorter array, define cut1 and calculate cut2, key is balance the number of left and right
    * Fullfull condition: `maxL1 <= minR2 && maxL2 <= minR1` else move the cut
    * use `Integer.MIN_VALUE` and `Integer.MAX_VALUE` for boundary condition
    * (need to practice)

## 2019/1/11

This is where to find the order of doing leetcode:  
***<https://github.com/CyC2018/CS-Notes/blob/master/docs/notes/Leetcode%20%E9%A2%98%E8%A7%A3.md>***
Following this guide from now on!

### Dual Pointer (1)

* **167 - Two Sum II - Input array is sorted - Medium**
  * easy. O(n)
* **663 - Sum of Square Numbers - Easy**
  * easy, O(n), much faster than hashset method

## 2019/1/14 (12 & 13 are weekends)

### Dual Pointer (2)

* **345 - Reverse Vowels of a String - Easy**
  * `return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';` a nice way to check characters
  * This is a better way of incrementation

    ```
      while (i < j && !isVowel(lo))
          lo = cArr[++i];
    ```

  * `new String(char[])`
* **680 - Valid Palindrome II - Easy**
  * first try too slow, string build approch
  * second try instead of stringbuilder, check the string from two end, Key is:
    * `return isPalindrome2(s, i, j - 1) || isPalindrome2(s, i + 1, j);`
  * This can 'delete' the head and tail of remaining string
* **88 - Merge Sorted Array - Easy**
  * two while loops's approch is a bit slower
  * Note that when nums2[] runs out, the rest of nums1[] elements is smaller, thus, no need to further sort

## 2019/1/15

### Dual Pointer (3)

* **141 - Linked List Cycle - Easy**
  * Succeed with two pointer, but many errors in boundary
    * need to consider how to avoid NullPointerException
  * HashSet proceed is much slower
* **524 - Longest Word in Dictionary through Deleting - Medium**
  * `(String).compareTo((String))` compare in lexicographically order
  * count number of same char to see if longer string contains shorter one

### Sorting (1): HeapSort

* **215 - Kth Largest Element in an Array - Medium**
  * Arrays.sort is the Optimal solution with Time: O(n log n) and Space: O(1)
  * HeapSort with PriorityQueue gives Time: O(n log k), Space O(k)
  * QuickSelect, Time: O(n) (almost guaranteed) Space: O(1)
    * shuffle to randomize the array
    * partition and exch for quick select
    * Will dig deeper tomorrow

## 2019/1/16 (Low Efficiency: News and Phone)

### Sorting (2): QuickSelect

* **215 - Kth Largest Element in an Array - Medium**
  * QuickSelect:
    * randomly choose (or shuffle array first) a pivot
    * move everything smaller to left with partition method
    * judge this pivot index, see if it's kth largest
      * same => return this
      * larger than k's index, quickSelect on left side
      * smaller than k's index, quickSelct on right side
  * QuickSelect is avg O(n) and in place O(1) method. random to prevent worst case

## 2019/1/17 (Got Other Things To Do)

### Sorting (3): BucketSort (1)

* **347 - Top K Frequent Elements - Medium**
  * First try with minHeap in O(n log k)
    * `PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>((a, b) -> Integer.compare(a.getValue(), b.getValue()));`
  * Second try with bucket sort, O(n)
  * `List<Integer>[] bucket = new ArrayList[N + 1];`
  * key is to use array of list as bucket to store frequency
    * return last k valid array value
  * (need to redo this later)

## 2019/1/18

### Sorting (4): BucketSort (2)

* **451 - Sort Characters By Frequency - Medium**
  * Standard bucket for this question is HashMap + ArrayList
  * Note that continue is faster in some case
  * heap is with priority queue

### Sorting (4): Dutch Flag

* **75 - Sort Colors - Medium**
  * Two Pointer Solution

### Greedy (1)

* **455 - Assign Cookies - Easy**
  * Sort first then greedy with two pointers

## 1/23 (Travel back to the US)

### Greedy (2)

* **435 - Non-overlapping Intervals - Medium**
  * The comparator needs some care
  * Optimal way is compare start point
  * Discuss the three range of one interval:
    * -INF to start
    * start to end
    * end to +INF
* **452 - Minimum Number of Arrows to Burst Balloons - Medium**
  * Same Idea as 435, except for boundary condition
  * Taking care of end points is enough
* **406 - Queue Reconstruction by Height - Medium**
  * Use list to insert shorter guys which can't be seen by taller guys
  * Sort first with height then k
  * Inserted right at where it should be

## 1/24 (Other Works)

* **8 - String to Integer (atoi) - Medium**
  * check sign first then next digit with `tmp < 0 || tmp > 9` to check if the char is a number or not
  * then check MAX_VALUE / 10 and MAX_VALUE % 10 with tmp for last digit

## 2/14

School stuffs. Watching Videos for Algorithms. Today is Chapter 1.

* **78 - Subsets - Medium**
  * Looking at the question: range should be small
  * Time complexity is more than O(n^2), for ex: (2^n, n!, n^n), then should be a polynomial time problem
  * NP problem => search problem (can only be solved by searching)
  * recursion and dfs
  
## 2/15

* **13 - Roman to Integer - Easy**
    * Note `indexof` can find
    * Get used to `exp1 ? res1 : res2`
* **704 - Binary Search - Easy**
    * error check first, then check head and tail
    * use mid = start + (end - start) / 2 to be safe with 2^31
    * use adjacent break and double check answer to be safe
* **278 - First Bad Version - Easy**
    * Typical Binary Search Question

## 2/16

* **34 - Find First and Last Position of Element in Sorted Array - Medium**
    * Use Binary Search Template, easily done optimal
* **702 - Search in a Sorted Array of Unknown Size- Medium**
    * Find right bound with doubling end index (method 1)
    * Or just set end index to Integer.MAX_VALUE (method 2)
* **153 - Find Minimum in Rotated Sorted Array- Medium**
    * set target to last element, then whenever current <= target, end = mid
    * This way to push end index to last possible index
* **33 - Search in Rotated Sorted Array - Medium**
    * Very Tricky One. The Smart methods are hard to remember and make sure the correctness
    * Just use the JiuZhang Template twice => get pivot point for rotated array first
    * Then set the start and end point carefully => do BS => result
* **81 - Search in Rotated Sorted Array II - Medium**
    * First try with template, I over thinking about all cases
    * I should foresee that there is a case need O(n) to check
        1. nums[mid] > nums[start]  => decide which part is sorted
        2. nums[mid] < nums[start]  => decide which part is sorted
        3. nums[mid] == nums[start] => start ++ (O(n))
    * Brute-Force is also nice
    
## 2/17

* **162 - Find Peak Element - Medium**
    * First try with template failed for array out of bound => need less checking
    * Second try with Integer.MIN_VALUE failed for test case [-Integer.MIN_VALUE, ..]
    * Third try with carefully checking succeed. Need to think about this for more similar questions.
* **852 - Peak Index in a Mountain Array - Easy**
    * First try with 162's third syntax, passed easily. Keep this in mind:
        1. if (curr < next) start = mid + 1
        2. else end = mid;
        3. check start and end when out of loop    
* **33 & 81 - REDO**
    * with duplicates, need to isolate current == head, curr > head, curr < head
    * else the same. Using half-half to find sorted part.
* **69 - Sqrt(x) - Easy**
    * use mid == x / mid instead of mid * mid = x
    * else just normal binary search with bound [1, x]
    * The things you want to get, is the things you gona BS
* **LintCode - 183 - Wood Cut - Hard**
    * Decide start and end, end with max length
    * don't just return when pieces match, min-length can still grow
* **LintCode - 437 - Copy Books - Hard**
    * start is max page, end is total time.
    * aim to get smallest time needed, thus BS on smallest time needed
    * set mid a cut to time, when return remember to return the smallest time
    