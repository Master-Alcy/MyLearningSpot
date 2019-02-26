package tree.DivideAndConquer;

import tree.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class FlattenBinaryTreeToLinkedList {

    /**
     * Try with Divide and Conquer, to be honest I don't really understand this
     */
    public void flatten(TreeNode root) {
        dcHelper(root);
    }

    // tail of left points to right head, root.right point to left head
    // we want to return a flattened node
    private TreeNode dcHelper(TreeNode node) {
        // exit
        if (node == null) {
            return node;
        }
        // Divide
        TreeNode left = dcHelper(node.left); // assume left and right
        TreeNode right = dcHelper(node.right); // are already flattened

        if (left != null) { // consider this node is the last node of left
            left.right = node.right; // connect it's right tail
            node.right = node.left;
            node.left = null;
        }
        // return right and left and not node b/c node is not flattened yet
        if (right != null) { // means right is not empty
            return right; // if flatten succeed, right is non-empty
        }
        if (left != null) {
            return left;
        }
        // leaf node
        return node;
    }

    /**
     * Traverse
     */
    public void flatten2(TreeNode node) { // root is misleading name, changed to node
        if (node == null) {
            return;
        }

        flatten(node.left);
        flatten(node.right);

        // save current right for concat
        TreeNode rightCopy = node.right;

        if (node.left != null) {
            // 1. concat node with left flatten subtree
            node.right = node.left;
            node.left = null;
            // move node to the end of newly added flatten subtree
            while (node.right != null) {
                node = node.right;
            }
            // attach old right flatten subtree to tail of newly added subtree
            node.right = rightCopy;
        }
    }

    /**
     * Iterative
     */
    public void flatten3(TreeNode node) {
        if (node == null) {
            return;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            if (curr.right != null) {
                stack.push(curr.right);
            }
            if (curr.left != null) {
                stack.push(curr.left);
            }
            // connect
            if (!stack.isEmpty()) {
                curr.right = stack.peek();
            }
            curr.left = null;
        }
    }
}
