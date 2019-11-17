package binarysearch;

public class SearchInRotatedSortedArray {

    public static void main(String[] args) {
        SearchInRotatedSortedArray si = new SearchInRotatedSortedArray();
        int res = si.search2(new int[]{1, 3}, 0);
        System.out.println(res);
    }

    /**
     * Method without start ++
     */
    public int search3(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int start = 0, end = nums.length - 1;

        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            int head = nums[start], curr = nums[mid];
            if (target == curr) {
                return mid;
            }

            if (curr >= head) {
                if (target >= head && target < curr) {
                    end = mid - 1;
                } else {
                    start = mid;
                }
            } else {
                // curr < head
                if (target > curr && target < head) {
                    start = mid + 1;
                } else {
                    end = mid;
                }
            }
        }

        if (nums[start] == target) {
            return start;
        }
        if (nums[end] == target) {
            return end;
        }
        return -1;
    }

    /**
     *  Method from SRSA II
     */
    public int search2(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int start = 0, end = nums.length - 1;

        while (start + 1 < end) {
            // The idea is find which part is sorted
            // And if target is on sorted array
            int mid = start + (end - start) / 2;
            if (target == nums[mid]) {
                return mid;
            }
            // [start...mid] is sorted
            if (nums[start] < nums[mid]) {
                if (target >= nums[start] && target < nums[mid]) {
                    // end = mid - 1
                    end = mid;
                } else {
                    start = mid;
                }
            } else if (nums[mid] < nums[start]) {
                // or target > nums[mid] && target < nums[start]
                if (target > nums[mid] && target <= nums[end]) {
                    // start = mid + 1
                    start = mid;
                } else {
                    end = mid;
                }
            } else { // This is not needed for array with unique elements
                // Note, we only increase start,
                // so we need to only check with start
                start ++;
            }
        }

        if (nums[start] == target) {
            return start;
        }
        if (nums[end] == target) {
            return end;
        }
        return -1;
    }

    /**
     * First try, first loop to find pivot,
     * then adjust carefully to set new start and end
     * Other methods too hard to remember
     * 6 ms 95% 39mb, optimal.
     */
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        // Find pivot first
        int start = 0, end = nums.length - 1, base = nums[end];

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

        // Set pivot to first number after "cliff"
        int pivot = 0;
        if (nums[start] <= base) {
            pivot = start;
        } else if (nums[end] <= base) {
            pivot = end;
        }

        // Set new start and end.
        start = target <= base ? pivot : 0;
        // Note decide end point is tricky
        if (target <= base) { // easy
            end = nums.length - 1;
        } else if (target > base && pivot == 0) { // one "straight line"
            return -1;
        } else { // at peak
            end = pivot - 1;
        }

        // new BS for answer
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            int curr = nums[mid];
            if (curr == target) {
                return mid;
            } else if (target < curr) {
                end = mid;
            } else {
                start = mid;
            }
        }

        if (nums[start] == target) {
            return start;
        }
        if (nums[end] == target) {
            return end;
        }
        return -1;
    }
}
