import java.util.PriorityQueue;
import java.util.Random;

/*
 * @lc app=leetcode id=215 lang=java
 *
 * [215] Kth Largest Element in an Array
 *
 * https://leetcode.com/problems/kth-largest-element-in-an-array/description/
 *
 * algorithms
 * Medium (50.88%)
 * Likes:    2642
 * Dislikes: 199
 * Total Accepted:    478.7K
 * Total Submissions: 937.3K
 * Testcase Example:  '[3,2,1,5,6,4]\n2'
 *
 * Find the kth largest element in an unsorted array. Note that it is the kth
 * largest element in the sorted order, not the kth distinct element.
 * 
 * Example 1:
 * 
 * 
 * Input: [3,2,1,5,6,4] and k = 2
 * Output: 5
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: [3,2,3,1,2,4,5,5,6] and k = 4
 * Output: 4
 * 
 * Note: 
 * You may assume k is always valid, 1 ≤ k ≤ array's length.
 * 
 */

// @lc code=start
class Solution {

    public int findKthLargest2(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(
            (n1, n2) -> n1 - n2
        );
        for (int num : nums) {
            heap.add(num);
            if (heap.size() > k) {
                heap.poll();
            }
        }

        return heap.peek();
    }

    public int findKthLargest(int[] nums, int k) {
        int size = nums.length;
        int number = quickSelect(nums, 0, size - 1, size - k);
        return number;
    }

    private int quickSelect(int[] nums, int left, int right, int kthIndex) {
        if (left == right) { // one element in range
            return nums[left];
        }

        Random random = new Random(); // choose a random pivot_index
        int pivotIndex = left + random.nextInt(right - left);
        pivotIndex = partition(nums, left, right, pivotIndex);

        if (pivotIndex == kthIndex) { // if the pivot is on (size - k)
            return nums[kthIndex];
        }
        if (pivotIndex > kthIndex) { // go left side
            return quickSelect(nums, left, pivotIndex - 1, kthIndex);
        } // else go right side
        return quickSelect(nums, pivotIndex + 1, right, kthIndex);
    }

    private int partition(int[] nums, int left, int right, int pivotIndex) {
        int pivot = nums[pivotIndex];
        int index = left;
        // 1. move pivot to end
        swap(nums, pivotIndex, right);

        // 2. move all smaller elements to the left
        for (int i = left; i <= right; i++) {
            if (nums[i] < pivot) {
                // swap the current nums[i] with initial left
                swap(nums, index, i);
                index++;
            }
        }

        // 3. move pivot to its final place
        swap(nums, index, right);
        return index; // maybe index + 1?
    }

    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
// @lc code=end

