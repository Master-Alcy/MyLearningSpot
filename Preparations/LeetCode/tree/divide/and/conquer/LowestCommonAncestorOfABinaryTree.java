package tree.divide.and.conquer;

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

    /**
     * Lowest Common Ancestor III
     */
    private class ResultType {
        public boolean a_exist, b_exist;
        public TreeNode node;

        public ResultType(boolean a, boolean b, TreeNode node) {
            this.a_exist = a;
            this.b_exist = b;
            this.node = node;
        }
    }

    public TreeNode lowestCommonAncestorIII(TreeNode root, TreeNode A, TreeNode B) {
        ResultType rt = helper(root, A, B);
        if (rt.a_exist && rt.b_exist) {
            return rt.node;
        }
        return null;
    }

    private ResultType helper(TreeNode root, TreeNode A, TreeNode B) {
        if (root == null) {
            return new ResultType(false, false, null);
        }

        ResultType left = helper(root.left, A, B);
        ResultType right = helper(root.right, A, B);

        boolean hasA = left.a_exist || right.a_exist || root == A;
        boolean hasB = left.b_exist || right.b_exist || root == B;
        ResultType result = new ResultType(hasA, hasB, root);
        // renew
        if (root == A || root == B) { // If found we transfer it up
            return result;
        }
        if (left.node != null && right.node != null) { // this is it
            return result;
        }
        if (left.node != null) { // right node == null
            result.node = left.node;
            return result;
        }
        if (right.node != null) { // left node == null
            result.node = right.node;
            return result;
        }
        result.node = null;
        return result;
    }
}
