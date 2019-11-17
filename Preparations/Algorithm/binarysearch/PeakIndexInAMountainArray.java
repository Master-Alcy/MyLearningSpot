package binarysearch;

public class PeakIndexInAMountainArray {

    public int peakIndexInMountainArray(int[] nums) {
        if (nums == null || nums.length < 3) {
            return -1;
        }
        int start = 1, end = nums.length - 2;

        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            int curr = nums[mid],
                    next = nums[mid + 1];
            if (curr < next) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }

        if (nums[start - 1] < nums[start] && nums[start] > nums[start + 1]) {
            return start;
        }
        if (nums[end - 1] < nums[end] && nums[end] > nums[end + 1]) {
            return end;
        }
        return -1;
    }
}
