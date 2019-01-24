package greedy;

import java.util.Arrays;
import java.util.Comparator;

@SuppressWarnings("unused")
public class MinimumNumberOfArrowsToBurstBalloons {

    /**
     * Only Compare End Point, 24ms, 99.86% O(n log n)
     */
    private int findMinArrowShots2(int[][] points) {
        if (points.length < 1)
            return 0;
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                // comparing end point
                return o1[1] - o2[1];
            }
        });
        int currentEnd = points[0][1], count = 1;

        for (int[] range : points) {
            if (currentEnd >= range[0])
                continue;
            currentEnd = range[1];
            count++;
        } // End of loop

        return count;
    }

    /**
     * First try with Greedy, 35ms, 76.22%, O(n log n)
     */
    private int findMinArrowShots(int[][] points) {
        int N = points.length;
        if (N < 2)
            return N;
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                // comparing range
                return o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1];
            }
        });
        int currentEnd = Integer.MIN_VALUE, count = 0;

        for (int[] range : points) {
            if (currentEnd < range[0]) { // -INF to start
                currentEnd = range[1];
            } else if (currentEnd <= range[1]) { // start to end
                count++; //overlapped
            } else { // end to +INF
                currentEnd = range[1];
                count++;
            }
        } // End of loop

        return N - count;
    }
}
