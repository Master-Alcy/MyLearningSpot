package tree;

import java.util.*;

public class BinaryTreePostorderTraversal {

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        postorderRec(root, result);
        return result;
    }

    /**
     * Recursive method, dfs
     */
    private void postorderRec(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        postorderRec(root.left, result);
        postorderRec(root.right, result);
        result.add(root.val);
    }

    /**
     * Iterating, dfs, Time O(n), Space O(n)
     */
    public List<Integer> postorderTraversal2(TreeNode curr) {
        LinkedList<Integer> result = new LinkedList<>(); // addFirst O(1)
        if (curr == null) {
            return result;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(curr);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.addFirst(node.val); // addFirst is key

            if (node.left != null) { // when pop, this goes later but add before right
                stack.push(node.left);
            }
            if (node.right != null) { // when pop, this goes first but add after left
                stack.push(node.right);
            }
        }
        return result;
    }
}
