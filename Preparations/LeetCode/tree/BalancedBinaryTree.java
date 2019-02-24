package tree;

public class BalancedBinaryTree {
    /**
     * Divide and Conquer:
     * Find if left balanced, then right, then whole thing
     */
    public boolean isBalanced(TreeNode root) {
        return helper(root).isBalanced;
    }

    private class ResultType {
        public boolean isBalanced;
        public int maxDepth;

        public ResultType(boolean isBalanced, int maxDepth) {
            this.isBalanced = isBalanced;
            this.maxDepth = maxDepth;
        }
    }

    // 1. Definition: 得到root为根的二叉树是否平衡，以及他的高度是多少
    // See if the binary tree rooted at node balanced, and its height
    private ResultType helper(TreeNode node) {
        // 3. exit, what's the meaning of being empty
        if (node == null) {
            return new ResultType(true, 0);
        }

        // 2. split, find left tree and right tree
        ResultType left = helper(node.left);
        ResultType right = helper(node.right);

        // subtree not balanced
        if (!left.isBalanced || !right.isBalanced) {
            // height not important
            return new ResultType(false, -1);
        } // both left and right is balanced

        if (Math.abs(left.maxDepth - right.maxDepth) > 1) {
            return new ResultType(false, -1);
        }
        // both is balanced and their maxDepth differ is
        // less than or equal to 1
        // Calc new depth for this node, renew maxDepth
        // Noted here, a node's left and right must be
        // one level lower. This is why we + 1 here
        int depth = Math.max(left.maxDepth, right.maxDepth) + 1;
        return new ResultType(true, depth);
    }
}
