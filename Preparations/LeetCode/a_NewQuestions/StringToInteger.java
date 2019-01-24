package a_NewQuestions;

import java.math.BigInteger;
import java.util.Arrays;

public class StringToInteger {

    public static void main(String[] args) {
        StringToInteger si = new StringToInteger();
        int res = si.myAtoi2("--2");
        System.out.println(res);
    }

    /**
     * Other's code. 18ms, 87%
     */
    public int myAtoi2(String str) {
        str = str.trim();
        if (str.isEmpty())
            return 0;

        int i = 0, ans = 0, sign = 1, len = str.length();
        char c = str.charAt(i);

        if (c == '-' || c == '+') {
            sign = c == '+' ? 1 : -1;
            i++;
        }

        for (; i < len; i++) {
            int tmp = str.charAt(i) - '0';
            // This means that tmp is not a number
            if (tmp < 0 || tmp > 9) // nice way of testing char
                break;
            if (ans > Integer.MAX_VALUE / 10 ||
                    (ans == Integer.MAX_VALUE / 10 && Integer.MAX_VALUE % 10 < tmp))
                // check max_value and its last digit
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            else
                ans = ans * 10 + tmp;
        }

        return sign * ans;
    }

    /**
     * First try fail at "--1"
     */
    private int myAtoi(String str) {
        str = str.replaceAll("[^0-9\\-]+", " ");
        str = str.replaceAll("\\s+", " ");
        str = str.trim();
        if (str.length() < 1)
            return 0;
        String[] words = str.split(" ");

        System.out.println(Arrays.toString(words));

        int result = 0;
        for (String word : words) {
            if (isNum(word.charAt(0))) {
                result = turnChar(word);
                break;
            }
        }

        return result;
    }

    private int turnChar(String word) {
        System.out.println(word);
        int N = word.length();
        if (N == 1 && word.equals("-"))
            return 0;

        for (int i = 0; i < N; i++) {
            char c = word.charAt(i);
            if (c == '.' || (i > 0 && c == '-')) {
                word = word.substring(0, i);
                break;
            }
        }

        BigInteger big = new BigInteger(word);
        BigInteger min = new BigInteger(String.valueOf(Integer.MIN_VALUE));
        BigInteger max = new BigInteger(String.valueOf(Integer.MAX_VALUE));
        if (big.compareTo(min) == -1)
            return Integer.MIN_VALUE;
        if (big.compareTo(max) == 1)
            return Integer.MAX_VALUE;
        return Integer.parseInt(word);
    }

    private boolean isNum(char c) {
        if (c == '+' || c == '-' || c == '0' || c == '1' || c == '2' ||
                c == '3' || c == '4' || c == '5' || c == '6' ||
                c == '7' || c == '8' || c == '9' || c == '0')
            return true;
        return false;
    }
}
