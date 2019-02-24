package tree;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreePaths {

    /**
     * Traverse
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        pathHelper(root, String.valueOf(root.val), result);
        return result;
    }

    private void pathHelper(TreeNode root, String path, List<String> result) {
        if (root == null) { // avoid NPE
            return;
        }
        // Make sure this is the end
        if (root.left == null && root.right == null) {
            result.add(path);
            return;
        }
        if (root.left != null) {
            pathHelper(root.left, path + "->" + String.valueOf(root.left.val), result);
        }
        if (root.right != null) {
            pathHelper(root.right, path + "->" + String.valueOf(root.right.val), result);
        }
    }

    /**
     * Divide and Conquer
     */
    public List<String> binaryTreePaths2(TreeNode root) {
        List<String> result = new ArrayList<>(); // Definition <<< 1 >>>
        if (root == null) { // null node Exit <<< 3 >>>
            return result;
        }
        if (root.left == null && root.right == null) { // leaf node <<< 3 >>>
            result.add(String.valueOf(root.val)); // add new path
            return result;
        }
        // Divide <<< 2 >>>
        List<String> leftPath = binaryTreePaths2(root.left);
        List<String> rightPath = binaryTreePaths2(root.right);
        // Think what would the result be <<< 2 >>>
        // Only one situation when leftPath/rightPath would be non-empty
        // That is there exist leaf before
        // Note that result is not the same one carrying single leaf
        // But the new one initially empty
        for (String path : leftPath) {
            result.add(root.val + "->" + path);
        }
        for (String path : rightPath) {
            result.add(root.val + "->" + path);
        }
        return result;
    }
}
