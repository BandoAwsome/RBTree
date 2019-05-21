import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 一整颗红黑树，满足以下性质:
 *  1.节点是红色或黑色。
 *  2.根是黑色。
 *  3.所有叶子都是黑色（叶子是NULL节点）。
 *  4.每个红色节点必须有两个黑色的子节点。（从每个叶子到根的所有路径上不能有两个连续的红色节点。）
 *  5.从任一节点到其每个叶子的所有简单路径都包含相同数目的黑色节点。
 *  详细Case分析可以参照Google wiki
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/20 18:49
 */
public class RBTree {

    /**  树根 */
    private TreeNode root;

    /**
     * 加入节点
     * @param node
     * @return: void
     * @date: 2019/5/20 18:55
     */
    public void add(TreeNode node) {
        // 1.形成二叉排序树
        insertInTree(node);
        // 2.着色与旋转
        colorAndRotateAfterInsert(node);
    }

    /**
     * 变成排序树
     * @return: void
     * @date: 2019/5/20 18:57
     */
    public void insertInTree(TreeNode newNode) {
        if (root == null) {
            // 初始化树根
            root = newNode;
            return;
        }
        // 插入树
        insertInSortTree(root, newNode);
    }

    /**
     * 找到简单排序树中的位置
     * @param node
     * @param newNode
     * @return: void
     * @date: 2019/5/20 19:27
     */
    public void insertInSortTree(TreeNode node, TreeNode newNode) {
        if (node == null) {
            // 容错
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
        insertInSortTree(nextNode, newNode);
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
            System.out.println(node.getData() + " " + node.getColor().getStr());
            // 左右孩子进队
            if (node.getLeftChild() != null) {
                queue.add(node.getLeftChild());
            }
            if (node.getRightChild() != null) {
                queue.add(node.getRightChild());
            }
        }
    }

    /**
     * 对新节点进行着色和旋转
     * @param newNode
     * @return: void
     * @date: 2019/5/21 10:25
     */
    public void colorAndRotateAfterInsert(TreeNode newNode) {
        if (newNode.getFather() == null) {
            // case1：新节点是根
            newNode.setColor(NodeColor.BLACK);
        } else if (newNode.getFather().getColor() == NodeColor.BLACK) {
            // case2：新节点的父亲是黑色
        } else if (newNode.getFather().getColor() == NodeColor.RED) {
            if (newNode.getUncle() != null && newNode.getUncle().getColor() == NodeColor.RED) {
                // case3：父亲和叔叔节点都是红色
                newNode.getUncle().setColor(NodeColor.BLACK);
                newNode.getFather().setColor(NodeColor.BLACK);
                newNode.getGrandFather().setColor(NodeColor.RED);
                // 把祖父节点当做新插入节点，递归处理
                colorAndRotateAfterInsert(newNode.getGrandFather());
            } else {
                if (newNode.getFather() == newNode.getGrandFather().getLeftChild() && newNode == newNode.getFather().getRightChild()) {
                    // case4：父节点是红色而叔父节点是黑色或缺少，父节是左孩子，自己是右孩子。先父亲左旋
                    newNode.getFather().leftRotate();
                    // 新插入节点指向旋转后的左孩子，也就是旋转前的父亲
                    newNode = newNode.getLeftChild();
                } else if (newNode.getFather() == newNode.getGrandFather().getRightChild() && newNode == newNode.getFather().getLeftChild()) {
                    // case4：父节点是红色而叔父节点是黑色或缺少，父亲是右孩子，自己是左孩子。先父亲右旋
                    newNode.getFather().rightRotate();
                    // 新插入节点指向旋转后的右孩子，也就是旋转前的父亲
                    newNode = newNode.getRightChild();
                }
                boolean rootChange = false;
                if (newNode.getGrandFather() == root) {
                    // 之后需要处理树根指向
                    rootChange = true;
                }
                if (newNode.getFather() == newNode.getGrandFather().getLeftChild()) {
                    // case5：父节点是红色而叔父节点是黑色或缺少，自己和父亲都是左孩子。祖父节点的一次右旋转
                    newNode.getGrandFather().rightRotate();
                } else {
                    // case5：父节点是红色而叔父节点是黑色或缺少，自己和父亲都是右孩子。祖父节点的一次左旋转
                    newNode.getGrandFather().leftRotate();
                }
                if (rootChange) {
                    // 可能树根改变
                    root = newNode.getFather();
                }
                // 父亲变成黑色
                newNode.getFather().setColor(NodeColor.BLACK);
                // 原来的祖父变成红色
                newNode.getBrother().setColor(NodeColor.RED);
            }
        }
    }

}
