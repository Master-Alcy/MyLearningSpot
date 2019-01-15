package heapsort.quickselect;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class KthLargestElementInAnArray {

    public static void main(String[] args) {
        KthLargestElementInAnArray kth = new KthLargestElementInAnArray();
        int[] test = {3, 2, 1, 5, 6, 4};
        int res = kth.findKthLargest2(test, 2);
        System.out.println(res);
    }

    /**
     * Quick Select. Time: avg(almost guaranteed): O(n) Space: O(1)
     * 9ms, 71% Not Optimal but should remember
     */
    private int findKthLargest3(int[] nums, int k) {
        shuffle(nums);
        k = nums.length - k;
        int lo = 0, hi = nums.length - 1;

        while (lo < hi) {
            int j = partition(nums, lo, hi);

            if (j == k)
                break;
            else if (j < k)
                lo = j + 1;
            else
                hi = j - 1;
        }
        return nums[k];
    }

    private void shuffle(int a[]) {
        Random random = new Random();

        for (int ind = 1; ind < a.length; ind++) {
            final int r = random.nextInt(ind + 1);
            exch(a, ind, r);
        }
    }

    private int partition(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;

        while (true) {
            while (a[++i] < a[lo] && i < hi) ;
            while (a[--j] > a[lo] && j > lo) ;
            if (i >= j)
                break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private void exch(int[] a, int i, int j) {
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
