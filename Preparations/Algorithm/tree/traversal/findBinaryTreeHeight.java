package tree.traversal;

import tree.TreeNode;

public class findBinaryTreeHeight {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(0);
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);
        root.left = n1;
        root.right = n2;
        n1.left = n3;
        n3.left = n4;
        n2.left = n5;

        int height = find(root);
        System.out.println(height);
    }

    private static int find(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftHeight = find(root.left);
        int rightHeight = find(root.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }
}