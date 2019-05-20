import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 一整颗红黑树
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/20 18:49
 */
public class RBTree {

    /**  树根 */
    private TreeNode root;

    public RBTree(){}

    /**
     * 加入节点
     * @param node
     * @return: void
     * @date: 2019/5/20 18:55
     */
    public void insert(TreeNode node) {
        // 1.形成二叉排序树
        transferToSortTree(node);
        // 2.着色与旋转

    }

    /**
     * 变成排序树
     * @return: void
     * @date: 2019/5/20 18:57
     */
    public void transferToSortTree(TreeNode newNode) {
        if (root == null) {
            // 初始化树根
            root = newNode;
            return;
        }
        // 插入树
        transfer(root, newNode);
    }

    /**
     * 找到简单排序树中的位置
     * @param node
     * @param newNode
     * @return: void
     * @date: 2019/5/20 19:27
     */
    public void transfer(TreeNode node, TreeNode newNode) {
        if (node == null) {
            node = newNode;
            return;
        }
        TreeNode nextNode = null;
        if (node.getData() >= newNode.getData()) {
            if (node.getLeftChild() == null) {
                // 置为左孩子
                node.setLeftChild(newNode);
                newNode.setFather(node);
                return;
            }
            nextNode = node.getLeftChild();
        } else {
            if (node.getRightChild() == null) {
                // 置为右孩子
                node.setRightChild(newNode);
                newNode.setFather(node);
                return;
            }
            nextNode = node.getRightChild();
        }
        transfer(nextNode, newNode);
    }

    /**
     * 层次遍历
     * @return: void
     * @date: 2019/5/20 19:03
     */
    public void visit() {
        Queue<TreeNode> queue = new LinkedBlockingDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            // 取出队首
            TreeNode node = queue.poll();
            System.out.println(node.getData() + " ");
            // 左右孩子进队
            if (node.getLeftChild() != null) {
                queue.add(node.getLeftChild());
            }
            if (node.getRightChild() != null) {
                queue.add(node.getRightChild());
            }
        }
    }

}
