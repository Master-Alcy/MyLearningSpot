package binary_search;

/**
 * Given n books and the ith book has A[i] pages.
 * You are given k people to copy the n books.
 * <p>
 * n books list in a row and each person can claim a continuous
 * range of the n books. For example one copier can copy the books
 * from ith to jth continuously, but he can not copy the 1st book,
 * 2nd book and 4th book (without 3rd book).
 * <p>
 * They start copying books at the same time and they all cost 1 minute
 * to copy 1 page of a book. What's the best strategy to assign books
 * so that the slowest copier can finish at earliest time?
 * <p>
 * Example
 * Given array A = [3,2,4], k = 2.
 * Return 5( First person spends 5 minutes to copy book 1
 * and book 2 and second person spends 4 minutes to copy book 3. )
 * <p>
 * Challenge
 * Time Complexity: O(nk)
 */
public class CopyBooks {

    /**
     * Could be DP, but BS better
     * @param pages numbers of pages, n books
     * @param people number of people
     * @return earliest time finish copying
     */
    public int copyBooks(int[] pages, int people) {
        if (pages == null || pages.length == 0) {
            return 0;
        }

        int totalTime = 0, maxTimePerPage = 0;
        for (int i = 0; i < pages.length; i++) {
            totalTime += pages[i];
            if (pages[i] > maxTimePerPage) {
                maxTimePerPage = pages[i];
            }
        }

        int start = maxTimePerPage, end = totalTime;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;

            if (countPeople(mid, pages) <= people) {
                end = mid;
            } else {
                start = mid;
            }
        }
        // as small as possible thus start first then end
        if (countPeople(start, pages) <= people) {
            return start;
        }
        if (countPeople(end, pages) <= people) {
            return end;
        }
        return 0;
    }

    private int countPeople(int maxTime, int[] arr) {
        int headCount = 1;
        int sumTime = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (sumTime + arr[i] > maxTime) {
                headCount ++;
                sumTime = 0;
            }
            sumTime += arr[i];
        }
        return headCount;
    }
}
