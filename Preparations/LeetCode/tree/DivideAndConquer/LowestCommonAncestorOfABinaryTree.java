package tree.DivideAndConquer;

import tree.TreeNode;

public class LowestCommonAncestorOfABinaryTree {

    /**
     * Divide and Conquer
     * if only found p, return p
     * if only found q, return q
     * neither return null
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        } //
        // Divide
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        // Conquer, Merge
        // Note, not null means there is root == p or root == q happened!
        if (left != null && right != null) {
            return root;
        }
        if (left != null) { // right == null
            return left;
        }
        if (right != null) { // left == null
            return right;
        }
        return null;
    }
}
