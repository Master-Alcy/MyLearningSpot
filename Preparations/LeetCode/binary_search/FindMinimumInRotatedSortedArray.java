package binary_search;

public class FindMinimumInRotatedSortedArray {

    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int start = 0, end = nums.length - 1, base = nums[end] ;
        // 4 5 6 7 0 1 2 3
        // Find First Position <= Last Number
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            int curr = nums[mid];

            if (curr <= base) {
                // this would keep push end
                end = mid;
            } else {
                start = mid;
            }
        }
        if (nums[start] <= base) {
            return nums[start];
        }
        if (nums[end] <= base) {
            return nums[end];
        }
        return -1;
    }
}
