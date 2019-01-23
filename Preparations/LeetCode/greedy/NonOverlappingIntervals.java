package greedy;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;

public class NonOverlappingIntervals {

    private class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

    /**
     * Optimal 2ms 100%, Greedy, O(nlogn) + O(n) => O(nlogn)
     */
    private int eraseOverlapIntervals2(@NotNull Interval[] intervals) {
        if (intervals.length < 2)
            return 0;

        Arrays.sort(intervals, new Comparator<Interval>() {
            @Override
            // If start point is different, then the smaller start point is listed earlier
            // If start point is the same, then the one with smaller end point listed earlier
            public int compare(Interval o1, Interval o2) {
                return o1.start != o2.start ? o1.start - o2.start : o1.end - o2.end;
            }
        });
        int currentEnd = Integer.MIN_VALUE, count = 0;

        for (Interval interval : intervals) {
            if (currentEnd <= interval.start) {
                // At first the smallest start point
                // Then currentEnd is the none overlapping interval's end
                currentEnd = interval.end;
            } else if (currentEnd >= interval.end) {
                // Note the currentEnd > interval.start
                // If it also >= interval.end, renew the currentEnd
                currentEnd = interval.end;
                count++;
            } else {
                // This is currentEnd > interval.start && currentEnd < interval.end
                // Then this is included already
                count++;
            }
        } // End of loop

        return count;
    }

    /**
     * 5ms 72.76% Time: O(nlogn)
     */
    private int eraseOverlapIntervals(@NotNull Interval[] intervals) {
        if (intervals.length < 2)
            return 0;
//        Arrays.sort(intervals,
//                (a, b) -> (a.end != b.end) ? a.end - b.end : b.start - a.start
//        ); // lamda expression is too slow
        Arrays.sort(intervals, new Comparator<Interval>() {
            @Override
            // If end point is different, then the one with smaller end point listed earlier
            // If end point is the same, then the one with bigger start point listed earlier
            public int compare(Interval o1, Interval o2) {
                return o1.end != o2.end ? o1.end - o2.end : o2.start - o1.start;
            }
        });
        int end = Integer.MIN_VALUE, count = 0;

        for (Interval interval : intervals) {
            if (interval.start >= end)
                end = interval.end;
            else
                count++;
        }

        return count;
    }
}
