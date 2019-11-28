/*
 * @lc app=leetcode id=557 lang=java
 *
 * [557] Reverse Words in a String III
 *
 * https://leetcode.com/problems/reverse-words-in-a-string-iii/description/
 *
 * algorithms
 * Easy (66.41%)
 * Likes:    745
 * Dislikes: 75
 * Total Accepted:    159.6K
 * Total Submissions: 240.3K
 * Testcase Example:  `"Let's take LeetCode contest"`
 *
 * Given a string, you need to reverse the order of characters in each word
 * within a sentence while still preserving whitespace and initial word order.
 * 
 * Example 1:
 * 
 * Input: "Let's take LeetCode contest"
 * Output: "s'teL ekat edoCteeL tsetnoc"
 * 
 * 
 * 
 * Note:
 * In the string, each word is separated by single space and there will not be
 * any extra space in the string.
 * 
 */

// @lc code=start
class Solution {
    public String reverseWords(String s) {
        if (s == null || s.isEmpty() || s.length() == 1) {
            return s;
        }
        
        char[] chArr = s.toCharArray();

        int slow = 0;
        for (int i = 1; i < chArr.length; i++) {
            if (chArr[i] == ' ') {
                reverse(chArr, slow, i - 1);
                slow = i + 1;
            }
        }

        reverse(chArr, slow, chArr.length - 1);

        return String.valueOf(chArr);
    }

    private void reverse(char[] chArr, int head, int tail) {
        while (head < tail) {
            char temp = chArr[head];
            chArr[head] = chArr[tail];
            chArr[tail] = temp;
            head++;
            tail--;
        }
    }
}
// @lc code=end

