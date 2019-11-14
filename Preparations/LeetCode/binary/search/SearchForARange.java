package binary.search;

/**
 * Given a sorted array of n integers, find the starting and
 * ending position of a given target value.
 * <p>
 * If the target is not found in the array, return [-1, -1].
 * <p>
 * Example 1:
 * Input:
 * []
 * 9
 * Output:
 * [-1,-1]
 * <p>
 * Example 2:
 * Input:
 * [5, 7, 7, 8, 8, 10]
 * 8
 * Output:
 * [3, 4]
 * <p>
 * Challenge
 * O(log n) time.
 */
public class SearchForARange {

    /**
     * @param A:      an integer sorted array
     * @param target: an integer to be inserted
     * @return: a list of length 2, [index1, index2]
     */
    public int[] searchRange(int[] A, int target) {
        if (A == null || A.length == 0) {
            return new int[]{-1, -1};
        }
        int[] result = new int[2];
        // Find first index
        int start = 0, end = A.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            int curr = A[mid];
            // 1,2,3,4,5,6,7,8,8,8,8,8,8,9,10,11 for 8
            // end = mid will push right bound to left
            if (curr >= target) {
                end = mid;
            } else { // curr < target, stops at 7
                start = mid;
            }
        }

        if (A[start] == target) {
            result[0] = start;
        } else if (A[end] == target) {
            result[0] = end;
        } else {
            return new int[]{-1, -1};
        }
        // find last index
        start = 0;
        end = A.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            int curr = A[mid];
            // 1,2,3,4,5,6,7,8,8,8,8,8,8,9,10,11 for 8
            // start = mid will push right bound to left
            if (curr <= target) {
                start = mid;
            } else { // curr > target, stops at 9
                end = mid;
            }
        }

        if (A[end] == target) {
            result[1] = end;
        } else if (A[start] == target) {
            result[1] = start;
        } else {
            return new int[]{-1, -1};
        }
        return result;
    }
}
