package a_QuestionLobby;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Subsets {

    public static void main(String[] args) {
        Subsets ss = new Subsets();

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
        dfsHelper(nums, 0, new ArrayList<Integer>(), result);
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
        result.add(new ArrayList<Integer>(subset)); // 1. clone, deep copy

        for (int i = startIndex; i < nums.length; i++) {
            subset.add(nums[i]); // [1]
            dfsHelper(nums, i + 1, subset, result);
            // change it back, go back graphically
            subset.remove(subset.size() - 1);
        }
        // 3. recursion exit
    }

}
