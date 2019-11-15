package string;

public class StringBasics {

    public static void main(String[] args) {
        String input = "hello world";
        String target = "bba";
        int res = strStr(target, input);
        System.out.println(res);
    }

    private static int strStr(String needle, String haystack) {
        if (haystack == null || needle == null) {
            return -1;
        }
        if (haystack.equals(needle) || needle.isEmpty()) {
            return 0;
        }

        for (int i = 0; i < haystack.length(); i++) {
            for (int j = 0; j < needle.length(); j++) {
                if (i + j >= haystack.length()) {
                    return -1;
                }
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    break;
                }
                if (j + 1 == needle.length()) {
                    return i;
                }
            }
        }

        return -1;
    }
}
