package binary_search;

import java.util.Arrays;

@SuppressWarnings("unused")
public class FindFirstAndLastPositionOfElementInSortedArray {

    public static void main(String[] args) {
        FindFirstAndLastPositionOfElementInSortedArray ff = new FindFirstAndLastPositionOfElementInSortedArray();
        int[] res = ff.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8);
        System.out.println(Arrays.toString(res));
    }

    /**
     * First try with BS Template. 3ms, 100%, 42.2 MB. Optimal.
     */
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length < 1) {
            return new int[]{-1, -1};
        }

        int[] result = new int[2];
        int start1 = 0, end1 = nums.length - 1;
        int start2 = start1, end2 = end1;

        while (start1 + 1 < end1 || start2 + 1 < end2) {

            if (start1 + 1 < end1) {
                int mid1 = start1 + (end1 - start1) / 2;
                int curr1 = nums[mid1];

                if (curr1 == target) {
                    end1 = mid1;
                } else if (curr1 > target) {
                    end1 = mid1;
                } else {
                    start1 = mid1;
                }
            }

            if (start2 + 1 < end2) {
                int mid2 = start2 + (end2 - start2) / 2;
                int curr2 = nums[mid2];

                if (curr2 == target) {
                    start2 = mid2;
                } else if (curr2 > target) {
                    end2 = mid2;
                } else {
                    start2 = mid2;
                }
            }
        }

        if (nums[start1] == target) {
            result[0] = start1;
        } else if (nums[end1] == target) {
            result[0] = end1;
        }

        if (nums[end2] == target) {
            result[1] = end2;
            return result;
        } else if (nums[start2] == target) {
            result[1] = start2;
            return result;
        }

        return new int[]{-1, -1};
    }
}
