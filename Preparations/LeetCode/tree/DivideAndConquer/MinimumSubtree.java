package tree.DivideAndConquer;

import tree.TreeNode;

/**
 * Given a binary tree, find the subtree with minimum sum.
 * Return the root of the subtree.
 * <p>
 * Example 1:
 * Input:
 * 1
 * /   \
 * -5     2
 * / \   /  \
 * 0   2 -4  -5
 * Output:1
 * <p>
 * Example 2:
 * Input:
 * 1
 * Output:1
 * <p>
 * Notice
 * LintCode will print the subtree which root is your return node.
 * It's guaranteed that there is only one subtree with minimum sum
 * and the given binary tree is not an empty tree.
 */
public class MinimumSubtree {

    /**
     * Method 1: Traverse + Divide and Conquer
     */
    private TreeNode subtree = null;
    private int subtreeSum = Integer.MAX_VALUE;

    /**
     * @param root: the root of binary tree
     * @return: the root of the minimum subtree
     */
    public TreeNode findSubtree(TreeNode root) {
        findNode(root);
        return subtree;
    }

    private int findNode(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int sum = findNode(root.left) + findNode(root.right) + root.val;
        if (sum <= subtreeSum) { // '=' means at least one node would be returned
            subtreeSum = sum; // renew new minimum sum
            subtree = root; // record new minimum tree node
        }
        return sum;
    }

    /**
     * Trying pure divide and conquer
     */
    public TreeNode findSubtree2(TreeNode root) {
        ResultType result = helper(root);
        return result.minSubtree;
    }

    private class ResultType {
        public TreeNode minSubtree;
        public int currSum, minSubtreeValue;

        public ResultType(TreeNode minSubtree, int minSubtreeSum, int currSum) {
            this.minSubtree = minSubtree;
            this.currSum = currSum;
            this.minSubtreeValue = minSubtreeSum;
        }
    }

    // 1. Definition:
    // for subtree rooted at node, find its minimum subtree node
    // find its current sum, compare with minimum subtree sum
    // return the renewed ResultType
    private ResultType helper(TreeNode node) {
        // 3. Exit
        if (node == null) { // It's deadly important to return max_value here!
            return new ResultType(node, Integer.MAX_VALUE, 0);
        }
        // 2. Divide
        ResultType left = helper(node.left);
        ResultType right = helper(node.right);
        int currSum = left.currSum + right.currSum + node.val;

        // want to renew minSubtree node and minSubtree value
        if (currSum > left.minSubtreeValue || currSum > right.minSubtreeValue) {
            if (left.minSubtreeValue > right.minSubtreeValue) {
                // left subtree got bigger value, return right
                return new ResultType(right.minSubtree, right.minSubtreeValue, currSum);
            } else {
                return new ResultType(left.minSubtree, left.minSubtreeValue, currSum);
            }
        } // current sum is smaller than or equals to both subtree
        return new ResultType(node, currSum, currSum);
    }

    public static void main(String[] args) {
        MinimumSubtree ms = new MinimumSubtree();
        TreeNode root = new TreeNode(0);
        TreeNode one = new TreeNode(1);
        TreeNode two = new TreeNode(2);
        one.right = two;
        TreeNode res = ms.findSubtree2(one);
        System.out.println(one.val);
        System.out.println(one.right.val);
        System.out.println(res.val);
    }
}
