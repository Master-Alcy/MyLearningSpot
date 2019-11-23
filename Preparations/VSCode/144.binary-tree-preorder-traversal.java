import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import javax.swing.tree.TreeNode;

/*
 * @lc app=leetcode id=144 lang=java
 *
 * [144] Binary Tree Preorder Traversal
 *
 * https://leetcode.com/problems/binary-tree-preorder-traversal/description/
 *
 * algorithms
 * Medium (53.14%)
 * Likes:    1032
 * Dislikes: 46
 * Total Accepted:    399.8K
 * Total Submissions: 751.6K
 * Testcase Example:  '[1,null,2,3]'
 *
 * Given a binary tree, return the preorder traversal of its nodes' values.
 * 
 * Example:
 * 
 * 
 * Input: [1,null,2,3]
 * ⁠  1
 * ⁠   \
 * ⁠    2
 * ⁠   /
 * ⁠  3
 * 
 * Output: [1,2,3]
 * 
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
    // (a) Inorder (Left, Root, Right)
    // (b) Preorder (Root, Left, Right)
    // (c) Postorder (Left, Right, Root)
    
    // public List<Integer> preorderTraversal(TreeNode root) {
    //     List<Integer> result = new ArrayList<>();
    //     traverse(result, root);
    //     return result;
    // }

    // private void traverse(List result, TreeNode root) {
    //     if (root == null) {
    //         return;
    //     }
    //     result.add(root.val);
    //     traverse(result, root.left);
    //     traverse(result, root.right);
    // }

    // public List<Integer> preorderTraversal(TreeNode root) {
    //     List<Integer> result = new ArrayList<>();
    //     if (root == null) {
    //         return result;
    //     }
    //     // Divide
    //     List<Integer> left = preorderTraversal(root.left);
    //     List<Integer> right = preorderTraversal(root.right);
    //     // Conquer
    //     result.add(root.val);
    //     result.addAll(left);
    //     result.addAll(right);
    //     return result;
    // }

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while(!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);

            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }

        return result;
    }
}
// @lc code=end