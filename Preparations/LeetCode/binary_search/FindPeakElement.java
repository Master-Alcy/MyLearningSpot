package binary_search;

public class FindPeakElement {


    public int findPeakElement(int[] nums) {
        // nums[-1] = nums[n] = - INF
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        int start = 0, end = nums.length - 1;

        while (start + 1 < end) {
            // no length 1, so mid + 1 will do
            int mid = start + (end - start) / 2;

            if (nums[mid] < nums[mid + 1]) {
                // / * /
                start = mid + 1;
            } else {
                // \ * \ or \ * / or / * \
                end = mid; // would not exclude peak
            }
        }
        // first half exclude the exceeding case
        // Note start only changes with mid + 1
        if (start == nums.length - 1 || nums[start] > nums[start + 1]) {
            return start;
        } else {
            return end;
        }
    }
}
