package string;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Subsets {

    /**
     * Subset Optimal with Backtracking (same as dfs recursion)
     */
    public List<List<Integer>> subsets2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 1) {
            return result;
        }
        List<Integer> subset = new ArrayList<>();

        for (int subsetSize = 0, N = nums.length; subsetSize <= N; subsetSize++) {
            backtracking(0, subsetSize, nums, subset, result);
        }
        return result;
    }

    private void backtracking(int startIndex, final int subsetSize, final int[] nums,
                              List<Integer> subset, List<List<Integer>> result) {
        if (subset.size() == subsetSize) {
            result.add(new ArrayList<>(subset));
            return;
        }

        for (int N = nums.length; startIndex < N; startIndex++) {
            subset.add(nums[startIndex]);
            backtracking(startIndex + 1, subsetSize, nums, subset, result);
            subset.remove(subset.size() - 1);
        }
    }

    /**
     * Subset Optimal with DFS, 34.7mb, 100%
     */
    public List<List<Integer>> subsets(int[] nums) {
        // NP, can only be solved by searching
        // 1. eliminate duplicate by
        // 1.1 find leader like the ordered one [1, 2, 3] not [1, 3, 2]
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        // Arrays.sort(nums); // optional
        // nums, startIndex, subset, result
        // move all set start with [] into result
        dfsHelper(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private void dfsHelper(int[] nums, int startIndex,
                           List<Integer> subset,
                           List<List<Integer>> result) {
        // Recursive
        // 1. Aim: move all subsets into result
        // nums = [1, 2, 3], startIndex = 0, subset = [], results = []
        // result.add(subset) => reference, pointer
        // need to new; variable object
        // 2. break down the recursion
        result.add(new ArrayList<>(subset)); // 1. clone, deep copy

        for (int N = nums.length; startIndex < N; startIndex++) {
            subset.add(nums[startIndex]); // [1]
            dfsHelper(nums, startIndex + 1, subset, result);
            // change it back, go back graphically
            subset.remove(subset.size() - 1);
        }
        // 3. recursion exit
    }

}
