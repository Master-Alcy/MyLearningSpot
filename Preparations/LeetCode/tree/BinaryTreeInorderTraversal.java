package tree;

import java.util.*;

public class BinaryTreeInorderTraversal {

    /**
     * 1st try pass with rec method (dfs)
     * Time O(n), Space abg(O(log n), worst O(n)
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        inorderRec(root, result);
        return result;
    }

    private static void inorderRec (TreeNode curr, List<Integer> result) {
        if (curr == null) {
            return;
        }

        inorderRec(curr.left, result);
        result.add(curr.val);
        inorderRec(curr.right, result);
    }

    /**
     * Iterator (dfs), Time O(n), Space O(n)
     */
    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        TreeNode curr = root; // not necessary
        Deque<TreeNode> stack = new ArrayDeque<>();

        while (true) {
            // inorder: left, root, right
            // add all left nodes into stack
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            } // curr == null
            // if stack is empty, then finished
            if (stack.isEmpty()) {
                break;
            }
            // all left nodes in stack
            // if right node empty, pop, else add right
            curr = stack.pop();
            // add left and root
            result.add(curr.val);
            // if right is null, then next round pop stack
            // else next round add right.left first ...
            curr = curr.right;
        }
        // stack is empty
        return result;
    }
}
