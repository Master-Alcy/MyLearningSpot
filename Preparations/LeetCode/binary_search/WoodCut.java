package binary_search;

/**
 * Given n pieces of wood with length L[i] (integer array).
 * Cut them into small pieces to guarantee you could have equal or
 * more than k pieces with the same length. What is the longest length
 * you can get from the n pieces of wood? Given L & k,
 * return the maximum length of the small pieces.
 * <p>
 * You couldn't cut wood into float length.
 * If you couldn't get >= k pieces, return 0.
 * <p>
 * Example:
 * For L=[232, 124, 456], k=7, return 114.
 * <p>
 * Challenge:
 * O(n log Len), where Len is the longest length of the wood.
 */
public class WoodCut {

    /**
     * O(n + n log maxLen) = O(n log max)
     * @param lens arrays of lengths of wood
     * @param pieces number of pieces with same length
     * @return maximum length of the small pieces
     */
    public int cut(int[] lens, int pieces) {
        /**
         * Length: 1 2 3 4 ... 114 115 ...
         *                     7   6
         * >= 7    O O O O ... O   X
         */
        if (lens == null || lens.length == 0) {
            return 0;
        }
        /**
         * First thing to decide start and end
         */
        int start = 1, end = 0;
        for (int i = 0; i < lens.length; i++) {
            if (lens[i] > end) {
                end = lens[i];
            }
        }

        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            int currSum = count(lens, mid);

            // Note it could increase start while currSum == pieces
            if (currSum >= pieces) {
                start = mid;
            } else {
                end = mid;
            }
        }
        // end first for longer length
        if (count(lens, end) >= pieces) {
            return start;
        }
        if (count(lens, start) >= pieces) {
            return end;
        }
        return 0;
    }

    private int count(int[] lens, int len) {
        int sum = 0;
        for (int i = 0; i < lens.length; i++) {
            sum += lens[i] / len;
        }
        return sum;
    }
}
