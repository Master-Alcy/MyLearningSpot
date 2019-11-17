package binary.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Give you an integer array (index from 0 to n-1, where n is
 * the size of this array, value from 0 to 10000) and an query list.
 * <p>
 * For each query, give you an integer, return the number of element
 * in the array that are smaller than the given integer.
 * <p>
 * Example
 * For array [1,2,7,8,5], and queries [1,8,5], return [0,4,2]
 * <p>
 * Challenge
 * Could you use three ways to do it:
 * 1. Just loop
 * 2. Sort and binary search
 * 3. Build Segment Tree and Search.
 */
public class CountOfSmallerNumber {
    /**
     * @param A: An integer array
     * @param queries: The query list
     * @return: The number of element in the array that are smaller that the given integer
     */
    public List<Integer> countOfSmallerNumber(int[] A, int[] queries) {
        List<Integer> result = new ArrayList<>();
        for (int j = 0; j < queries.length; j++) {
            result.add(0);
        }
        if (A == null || queries == null || A.length == 0 || queries.length == 0) {
            return result;
        }
        result.clear();
        Arrays.sort(A);

        for (int i = 0; i < queries.length; i++) {
            result.add(searchTarget(A, queries[i]));
        }
        return result;
    }

    private int searchTarget(int[] nums, int target) {
        int start = 0, len = nums.length, end = len - 1;

        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            int curr = nums[mid];
            // want to find the number that is the same as target
            // or smaller than target with largest index
            if (curr < target) {
                start = mid;
            } else { // curr >= target
                end = mid;
            }
        }
        // Note target == nums[start] don't count as smaller
        if (target <= nums[start]) { // smaller ones + 1 = start
            return start;
        }
        if (target <= nums[end]) {  // smaller ones + 1 = end
            return end;
        }
        return end + 1; // target > nums[end] (larger than tail)
    }
}
