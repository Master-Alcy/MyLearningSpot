package tree.traversal;

import tree.TreeNode;

import java.util.*;

public class BinaryTreePreorderTraversal {

    /**
     * Rec
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        preorderRec(root, result);
        return result;
    }

    /**
     * put preorder nodes rooted in root into result
     */
    private void preorderRec(TreeNode curr, List<Integer> result) {
        if (curr == null) {
            return;
        }
        result.add(curr.val);
        preorderRec(curr.left, result);
        preorderRec(curr.right, result);
    }

    /**
     * Iterate, Time O(n), Space O(n)
     */
    public List<Integer> preorderTraversal2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            // if this is left, then if there exist next left
            // we can make sure that's the next one gona be popped
            // and added to the tail
            result.add(node.val);

            if (node.right != null) { // when pop, this goes after left
                stack.push(node.right);
            }
            if (node.left != null) { // when pop, this goes first
                stack.push(node.left);
            }
        }

        return result;
    }

    /**
     * Divide & Conquer
     */
    public List<Integer> preorderTraversal3(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        // Divide
        List<Integer> left = preorderTraversal3(root.left);
        List<Integer> right = preorderTraversal3(root.right);
        // Conquer (Merge)
        result.add(root.val);
        result.addAll(left);
        result.addAll(right);
        return result;
    }
}
