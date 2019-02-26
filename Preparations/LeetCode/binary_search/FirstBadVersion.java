package binary_search;

@SuppressWarnings("unused")
public class FirstBadVersion {

    private boolean isBadVersion(int x) {
        // for 1 - 100, bad: 77
        if (x >= 77) {
            return true;
        }
        return false;
    }

    public int firstBadVersion(int n) {
        int start = 1, end = n;
        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;

            if (isBadVersion(mid)) {
                end = mid; // after this all bad
            } else {
                start = mid;
            }
        }

        if (isBadVersion(start)) {
            return start;
        }
        if (isBadVersion(end)) {
            return end;
        }

        return -1;
    }
}
