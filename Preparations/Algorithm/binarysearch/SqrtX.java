package binarysearch;

public class SqrtX {

    public int mySqrt(int x) {
        if (x <= 0) {
            return x;
        }
        int start = 1, end = x;

        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (mid == x / mid) {
                return mid;
            }
            if (mid > x / mid) {
                end = mid;
            } else if (mid < x / mid) {
                start = mid;
            }
        }
        if (end <= x / end) {
            return end;
        }
        if (start <= x / start) {
            return start;
        }
        return -1;
    }
}
