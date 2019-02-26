package binary_search;

@SuppressWarnings("unused")
public class BinarySearch {

    /**
     *  Template for Binary Search
     *
     */
    public int search1(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int start = 0, end = nums.length - 1;
        // break when adjacent (not at the exact position)
        // avoid dead loop when start < end and [2, 2] to find last pos
        // mid calculation is bias to start
        // safe to break when start = 1, end = 2
        while (start + 1 < end) { // while loop only narrows the range
            // int mid = (start + end) / 2 (start, end ~ 2^31)
            // Edge case
            int mid = start + (end - start) / 2;

            if (nums[mid] == target) { // <= or >=, concat later
                return mid;
            } else if (nums[mid] < target) {
                // start = mid + 1; // Not easy to be certain
                start = mid;
            } else {
                // end = mid - 1; // Not easy to be certain
                end = mid;
            }
        }
        // [start .......... mid .......... end]
        //                  target

        // double check
        if (nums[start] == target) {
            return start;
        }
        if (nums[end] == target) {
            return end;
        }
        return -1;
    }

    public int findFirstTarget(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int start = 0, end = nums.length - 1;
        // break when adjacent (not at the trigger)
        // break when start = 1, end = 2
        while (start + 1 < end) {
            // int mid = (start + end) / 2 (start, end ~ 2^31)
            // Edge case
            int mid = start + (end - start) / 2;

            if (nums[mid] == target) {
                end = mid;
            } else if (nums[mid] < target) {
                start = mid;
            } else {
                // same as end = mid - 1
                end = mid;
            }
        }
        // [start .......... mid .......... end]
        //                  target

        // double check
        if (nums[start] == target) {
            return start;
        }
        if (nums[end] == target) {
            return end;
        }
        return -1;
    }
}
