package tree;

public class MaximumDepthOfBinaryTree {

    /**
     * Divide and Conquer, preferred
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // Divide
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        // Conquer (Merge)
        return Math.max(left, right) + 1;
    }

    /**
     * Global Depth, traverse
     */
    private int depth;

    public int maxDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        depth = 1;
        maxDepthRec(root, 1);
        return depth;
    }

    private void maxDepthRec(TreeNode node, int currDepth) {
        if (node == null) {
            return;
        }
        if (currDepth > depth) {
            depth = currDepth;
        }
        maxDepthRec(node.left, currDepth + 1);
        maxDepthRec(node.right, currDepth + 1);
    }

}
