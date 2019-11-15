package tree.traversal;

import tree.TreeNode;

public class BinaryTreeLongestConsecutiveSequence {

    /**
     * Traverse and DC
     */
    private int longest = 0;

    public int longestConsecutive(TreeNode root) {
        helper(root);
        return longest;
    }

    private int helper(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftLen = helper(root.left);
        int rightLen = helper(root.right);

        int subtreeLongest = 1; // For this node, base max len is 1
        if (root.left != null && root.val + 1 == root.left.val) {
            subtreeLongest = Math.max(subtreeLongest, leftLen + 1);
        }
        if (root.right != null && root.val + 1 == root.right.val) {
            subtreeLongest = Math.max(subtreeLongest, rightLen + 1);
        }
        if (subtreeLongest > longest) {
            longest = subtreeLongest;
        }
        return subtreeLongest;
    }

    /**
     * DC with ResultType is also ok
     * Which is also about to record longestascending and longestdecending
     */
}
