package binary.search;

public class SearchInASortedArrayOfUnknownSize {

    private class ArrayReader {
        private int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};


        public int get(int index) {
            // if exist at that index return element
            if (index < arr.length && index >= 0) {
                return arr[index];
            }
            // out of bounds
            // return 2147483647; // same as below
            return Integer.MAX_VALUE;
        }
    }

    /*
     * Second try with just Binary Search
     */
    public int search2(ArrayReader reader, int target) {
        int start = 0, end = Integer.MAX_VALUE;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            int curr = reader.get(mid);

            if (curr == target) {
                return mid;
            } else if (target < curr) {
                end = mid;
            } else {
                start = mid;
            }
        }
        if (reader.get(start) == target) {
            return start;
        }
        if (reader.get(end) == target) {
            return end;
        }
        return -1;
    }

    /**
     * First try with JiuZhang Example
     */
    public int search(ArrayReader reader, int target) {
        if (reader == null || reader.get(0) == Integer.MAX_VALUE) {
            return -1;
        }
        // Key is doubling and find rightBound
        int baseIndex = 1;
        while (reader.get(baseIndex - 1) < target) {
            baseIndex *= 2; // Doubling most efficient
        } // Exponential Backoff

        int start = baseIndex / 2 - 1, end = baseIndex - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            int curr = reader.get(mid);

            if (target == curr) {
                return mid;
            } else if (target > curr) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (reader.get(start) == target) {
            return start;
        }
        if (reader.get(end) == target) {
            return end;
        }

        return -1;
    }


}
