import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import javax.swing.tree.TreeNode;

/*
 * @lc app=leetcode id=94 lang=java
 *
 * [94] Binary Tree Inorder Traversal
 *
 * https://leetcode.com/problems/binary-tree-inorder-traversal/description/
 *
 * algorithms
 * Medium (59.44%)
 * Likes:    2154
 * Dislikes: 94
 * Total Accepted:    570.7K
 * Total Submissions: 959K
 * Testcase Example:  '[1,null,2,3]'
 *
 * Given a binary tree, return the inorder traversal of its nodes' values.
 * 
 * Example:
 * 
 * 
 * Input: [1,null,2,3]
 * ⁠  1
 * ⁠   \
 * ⁠    2
 * ⁠   /
 * ⁠  3
 * 
 * Output: [1,3,2]
 * 
 * Follow up: Recursive solution is trivial, could you do it iteratively?
 * 
 */

// @lc code=start
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    // public List<Integer> inorderTraversal(TreeNode root) {
    //     List<Integer> result = new ArrayList<>();
    //     if (root == null) {
    //         return result;
    //     }

    //     traverse(result, root);
    //     return result;
    // }

    // private void traverse(List<Integer> result, TreeNode root) {
    //     if (root == null) {
    //         return;
    //     }

    //     traverse(result, root.left);
    //     result.add(root.val);
    //     traverse(result, root.right);
    // }

    // public List<Integer> inorderTraversal(TreeNode root) {
    //     List<Integer> result = new ArrayList<>();
    //     if (root == null) {
    //         return result;
    //     }

    //     // Divide
    //     List<Integer> left = inorderTraversal(root.left);
    //     List<Integer> right = inorderTraversal(root.right);
    //     // Conquer
    //     result.addAll(left);
    //     result.add(root.val);
    //     result.addAll(right);

    //     return result;
    // }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode node = root;

        while(true) {

            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            
            if (stack.isEmpty()) {
                break;
            }

            node = stack.pop();
            result.add(node.val);
            node = node.right;
        }
        
        return result;
    }
}
// @lc code=end

