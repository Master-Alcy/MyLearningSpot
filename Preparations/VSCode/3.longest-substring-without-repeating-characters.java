import java.util.Set;

/*
 * @lc app=leetcode id=3 lang=java
 *
 * [3] Longest Substring Without Repeating Characters
 *
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
 *
 * algorithms
 * Medium (29.18%)
 * Likes:    7000
 * Dislikes: 414
 * Total Accepted:    1.2M
 * Total Submissions: 4.1M
 * Testcase Example:  '"abcabcbb"'
 *
 * Given a string, find the length of the longest substring without repeating
 * characters.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: "abcabcbb"
 * Output: 3 
 * Explanation: The answer is "abc", with the length of 3. 
 * 
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 * 
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3. 
 * ⁠            Note that the answer must be a substring, "pwke" is a
 * subsequence and not a substring.
 */

// @lc code=start
class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        char[] chArr = s.toCharArray();
        Set<Character> chSet = new HashSet<>();
        int maxLen = 1;
        int i = 0;
        int j = 0;

        while(i < chArr.length && j < chArr.length) {
            if (chSet.contains(chArr[j])) {
                chSet.remove(chArr[i]);
                i++;
            } else {
                chSet.add(chArr[j]);
                j++;
                maxLen = Math.max(maxLen, j - i);
            }
        }

        return maxLen;
    }
}
// @lc code=end

