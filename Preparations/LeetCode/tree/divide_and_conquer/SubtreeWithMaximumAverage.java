package tree.divide_and_conquer;

import tree.TreeNode;

/**
 * Given a binary tree, find the subtree with maximum average.
 * Return the root of the subtree.
 * <p>
 * Example 1
 * Input：
 * 1
 * /   \
 * -5     11
 * / \   /  \
 * 1   2 4    -2
 * Output：11(it's a TreeNode)
 * <p>
 * Example 2
 * Input：
 * 1
 * /   \
 * -5     11
 * Output：11(it's a TreeNode)
 * <p>
 * Notice
 * LintCode will print the subtree which root is your return node.
 * It's guaranteed that there is only one subtree with maximum average.
 */
public class SubtreeWithMaximumAverage {

    public TreeNode findSubtree2(TreeNode root) {
        ResultType result = dcHelper(root);
        return result.maxNode;
    }

    // Think about that we need to get the result
    // 1. number of nodes in subtree rooted in node
    // 2. current average
    // 3. subtree node with maximum average
    private class ResultType {
        TreeNode maxNode;
        double maxAvg;
        int sum, size;

        public ResultType(TreeNode maxNode, double maxAvg, int sum, int size) {
            this.maxNode = maxNode;
            this.maxAvg = maxAvg;
            this.sum = sum;
            this.size = size;
        }
    }

    // Think about that we need in this helper
    // We want to find the things defined in ResultType
    private ResultType dcHelper(TreeNode node) {
        if (node == null) { // These three values are important! Leaf Node
            return new ResultType(node, -Double.MAX_VALUE, 0, 0);
        } // Double.MIN_VALUE is another thing
        // Divide
        ResultType left = dcHelper(node.left);
        ResultType right = dcHelper(node.right);

        int currSize = left.size + right.size + 1;
        int currSum = left.sum + right.sum + node.val;
        double currAvg = (double) currSum / currSize; // cast is a must
        ResultType currResult = new ResultType(node, currAvg, currSum, currSize);

        // renew ResultType, Note must call currResult.maxAvg and not currAvg!!!
        if (left.maxAvg > currResult.maxAvg) {
            currResult.maxNode = left.maxNode;
            currResult.maxAvg = left.maxAvg;
        }
        if (right.maxAvg > currResult.maxAvg) {
            currResult.maxNode = right.maxNode;
            currResult.maxAvg = right.maxAvg;
        }
        // else currAvg >= left/right maxAvg
        return currResult;
    }

    /**
     * traversal + Divide and Conquer
     */
    private TreeNode maxNode = null;
    private ResultType2 maxNodeData = null;

    private class ResultType2 {
        public int sum, size;

        public ResultType2(int sum, int size) {
            this.sum = sum;
            this.size = size;
        }
    }

    public TreeNode findSubTree22(TreeNode root) {
        tradcHelper(root);
        return maxNode;
    }

    private ResultType2 tradcHelper(TreeNode node) {
        if (node == null) {
            // sum is being carried, set to Integer.MIN_VALUE may carry to later calculation
            return new ResultType2(0, 0);
        }

        ResultType2 left = tradcHelper(node.left);
        ResultType2 right = tradcHelper(node.right);

        ResultType2 result = new ResultType2(left.sum + right.sum + node.val,
                left.size + right.size + 1);
        // avoid /0 situation
        if (maxNode == null || maxNodeData.sum * result.size < result.sum * maxNodeData.size) {
            maxNode = node;
            maxNodeData = result;
        }
        // result un-changed
        return result;
    }
}
