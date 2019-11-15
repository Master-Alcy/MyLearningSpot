package string;

public class RotateString {

    public boolean rotateString(String message, String pattern) {
        if (pattern.length() != message.length()) {
            return false;
        }
        if (message.equals(pattern)) {
            return true;
        }

        for (int i = 0; i < message.length(); i++) {
            if (message.equals(pattern)) {
                return true;
            }
            char temp = message.charAt(0);
            message = message.substring(1) + temp;
        }

        return false;
    }

    public boolean rotateString2(String A, String B) {
        // A+A include all rotations
        return A.length() == B.length() && (A + A).contains(B);
    }

    /**
     * Given a string(Given in the way of char array) and an offset,
     * rotate the string by offset in place. (rotate from left to right)
     * <p>
     * Example
     * Example 1:
     * Input: str="abcdefg", offset = 3
     * Output:"efgabcd"
     * <p>
     * Explanation:
     * Given a string and an offset, rotate string by offset.
     * (rotate from left to right)
     * <p>
     * Example 2:
     * Input: str="abcdefg", offset = 0
     * Output: "abcdefg"
     * <p>
     * Explanation:
     * Given a string and an offset, rotate string by offset.
     * (rotate from left to right)
     * <p>
     * Example 3:
     * Input: str="abcdefg", offset = 1
     * Output: "gabcdef"
     * <p>
     * Explanation:
     * Given a string and an offset, rotate string by offset.
     * (rotate from left to right)
     * <p>
     * Example 4
     * Input: str="abcdefg", offset =2
     * Output:"fgabcde"
     * <p>
     * Explanation:
     * Given a string and an offset, rotate string by offset.
     * (rotate from left to right)
     * <p>
     * Challenge
     * Rotate in-place with O(1) extra memory.
     *
     * @param str:    An array of char
     * @param offset: An integer
     * @return: nothing
     */
    public void rotateString(char[] str, int offset) {
        int N = str.length;
        if (str == null || N <= 1) {
            return;
        }
        offset = offset % N; // some strange offsets
        reverse(str, 0, N - offset - 1);
        reverse(str, N - offset, N - 1);
        reverse(str, 0, N - 1);
    }

    private void reverse(char[] cArr, int start, int end) {
        for (; start < end; start++, end--) {
            char temp = cArr[start];
            cArr[start] = cArr[end];
            cArr[end] = temp;
        }
    }
}
