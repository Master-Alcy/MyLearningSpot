package heapsort.quickselect;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class KthLargestElementInAnArray {

    public static void main(String[] args) {
        KthLargestElementInAnArray kth = new KthLargestElementInAnArray();
        int[] test = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        int[] test2 = {3,3,3,3,3,3,3,3,3};
        int res = kth.findKthLargest3(test2, 1);
        System.out.println(res);
    }

    /**
     * Quick Select. Time: avg(almost guaranteed): O(n) Space: O(1)
     * 9ms, 71% Not Optimal but should remember
     */
    private int findKthLargest3(int[] nums, int k) {
        int N = nums.length;
        int index = quickSelect(nums, k);
        return nums[index];
    }

    private int quickSelect(int[] nums, int k) {

        return -1;
    }

    private int partition(int[] nums, int left, int right, int pivot_index) {
        int pivot = nums[pivot_index];
        int store_index = left;
        // 1. move pivot to end
        swap(nums, pivot_index, right);

        // 2. move all smaller elements to the left
        for (int i = left; i <= right; i++) {
            if (nums[i] < pivot) {
                swap(nums, store_index, i);
                store_index++;
            }
        }

        // 3. move pivot to its final place
        swap(nums, store_index, right);
        return store_index;
    }

    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * HeapSort. Time: O(n log k) Space: O(k)
     * 16ms, 51%
     */
    private int findKthLargest2(int[] nums, int k) {
        // This heap is the least element
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int val : nums) {
            pq.offer(val);
            if (pq.size() > k)
                pq.poll();
        }
        return pq.peek();
    }

    /**
     * First Try, sort, Optimal
     * Time O(n log n) Space: O(1)
     * 8ms, 75% for parallelSort
     * 6ms, 86.63% for sort
     */
    private int findKthLargest1(int[] nums, int k) {
        // Arrays.parallelSort(nums);
        Arrays.sort(nums);
        return nums[nums.length - k];
    }
}
