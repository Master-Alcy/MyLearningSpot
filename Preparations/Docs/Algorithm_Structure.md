# Algorithm Structure

* 算法思想
  * 双指针
  * 排序
    * 快速选择
    * 堆排序
    * 桶排序
    * 荷兰国旗问题
  * 贪心思想
  * 二分查找
  * 分治
  * 搜索
    * BFS
    * DFS
    * Backtracking
  * 动态规划
    * 斐波那契数列
    * 矩阵路径
    * 数组区间
    * 分割整数
    * 最长递增子序列
    * 最长公共子序列
    * 0-1 背包
    * 股票交易
    * 字符串编辑
  * 数学
    * 素数
    * 最大公约数
    * 进制转换
    * 阶乘
    * 字符串加法减法
    * 相遇问题
    * 多数投票问题
    * 其它
* 数据结构相关
  * 链表
  * 树
    * 递归
    * 层次遍历
    * 前中后序遍历
    * BST
    * Trie
  * 栈和队列
  * 哈希表
  * 字符串
  * 数组与矩阵
  * 图
    * 二分图
    * 拓扑排序
    * 并查集
  * 位运算

## **算法思想**

## *Dual Pointer*

双指针主要用于遍历数组，两个指针指向不同的元素，从而协同完成任务。

Ex: 有序数组的 Two Sum
Leetcode：167. Two Sum II - Input array is sorted (Easy)

~~~
Input: numbers={2, 7, 11, 15}, target=9
Output: index1=1, index2=2
~~~

题目描述：在有序数组中找出两个数，使它们的和为 target。

使用双指针，一个指针指向值较小的元素，一个指针指向值较大的元素。指向较小元素的指针从头向尾遍历，指向较大元素的指针从尾向头遍历。

如果两个指针指向元素的和 sum == target，那么得到要求的结果；
如果 sum > target，移动较大的元素，使 sum 变小一些；
如果 sum < target，移动较小的元素，使 sum 变大一些。

* **167 - 有序数组的 Two Sum**
* **633 - 两数平方和**
* **345 - 反转字符串中的元音字符**
* **680 - 回文字符串**
* **88 - 归并两个有序数组**
* **141 - 判断链表是否存在环**
* **524 - 最长子序列**

## *Sorting*

### Quick Select

用于求解  **Kth Element**  问题，使用快速排序的 partition() 进行实现。  
需要先打乱数组，否则最坏情况下时间复杂度为 O(N^2)

* **215 - Kth Largest Element in an Array**

### Heap Sort

用于求解 TopK Elements 问题，通过维护一个大小为 K 的堆，堆中的元素就是 TopK Elements。

堆排序也可以用于求解 Kth Element 问题，堆顶元素就是 Kth Element。

快速选择也可以求解 TopK Elements 问题，因为找到 Kth Element 之后，再遍历一次数组，所有小于等于 Kth Element 的元素都是 TopK Elements。

可以看到，快速选择和堆排序都可以求解 Kth Element 和 TopK Elements 问题

* **215 - Kth Largest Element in an Array**

### Bucket Sort

设置若干个桶，每个桶存储出现频率相同的数，并且桶的下标代表桶中数出现的频率，即第 i 个桶中存储的数出现的频率为 i。

把数都放到桶之后，从后向前遍历桶，最先得到的 k 个数就是出现频率最多的的 k 个数。

* **347 - Top K Frequent Elements - Medium**
* **451 - Sort Characters By Frequency - Medium**

### Dutch Flag

荷兰国旗包含三种颜色：红、白、蓝。

有三种颜色的球，算法的目标是将这三种球按颜色顺序正确地排列。

它其实是三向切分快速排序的一种变种，在三向切分快速排序中，每次切分都将数组分成三个区间：小于切分元素、等于切分元素、大于切分元素，而该算法是将数组分成三个区间：等于红色、等于白色、等于蓝色。

* **75 - Sort Colors - Medium**

## *Greedy*

保证每次操作都是局部最优的，并且最后得到的结果是全局最优的。

* **455 - Assign Cookies - Easy**
* **435 - Non-overlapping Intervals - Medium**
* **452 - Minimum Number of Arrows to Burst Balloons - Medium**