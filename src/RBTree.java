import sun.reflect.generics.tree.Tree;

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
        if (node == null) {
            // 容错
            return;
        }
        // 1.形成二叉排序树
        insertInTree(node);
        // 2.着色与旋转
        colorAndRotateAfterInsert(node);
    }

    /**
     * 删除某个节点
     * @param node
     * @return: void
     * @date: 2019/5/21 16:02
     */
    public void delete(TreeNode node) {
        // 1.从排序树中删除
        Tuple<TreeNode, TreeNode> tuple = deleteInSortTree(node);
        if (tuple == null) {
            return;
        }
        TreeNode deleteNode = tuple.left;
        TreeNode newNode = tuple.right;
        if (newNode == null) {
            // 替换节点是叶子
            return;
        }
        // 2.着色与旋转
        if (deleteNode.getColor() == NodeColor.RED) {
            // 前提1：被删除节点是红色，不用继续处理
            return;
        }
        if (newNode.getColor() == NodeColor.RED) {
            // 前提2：替换节点是红色，被删节点也是黑色，变色成黑色即可
            newNode.setColor(NodeColor.BLACK);
            return;
        }
        colorAndRotateAfterDelete(newNode);
    }

    /**
     * 检查节点是否在树内
     * @param root
     * @param node
     * @return: void
     * @date: 2019/5/21 16:10
     */
    public boolean checkNodeInTree(TreeNode root, TreeNode node) {
        if (root == null || node == null) {
            return false;
        }
        if (root == node) {
            // 找到
            return true;
        }
        if (root.getData() >= node.getData()) {
            // 找左子树
            return checkNodeInTree(root.getLeftChild(), node);
        } else {
            // 找右子树
            return checkNodeInTree(root.getRightChild(), node);
        }
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

    /**
     * 从排序树中删除节点，被删节点至多只有一个儿子
     * @param node
     * @return: Tuple<TreeNode,TreeNode>  左元素 - 真正被删除的节点；右元素 - 替代的节点
     * @date: 2019/5/21 16:31
     */
    public Tuple<TreeNode, TreeNode> deleteInSortTree(TreeNode node) {
        if (!checkNodeInTree(root, node)) {
            // 节点不在树内
            return null;
        }
        if (node.getRightChild() != null && node.getLeftChild() != null) {
            // 左右孩子都存在，将删除点改为左子树的最右孩子, 原本删除点Data被替换
            // 注意，这里暗含一个逻辑：最右子树肯定不会有两个儿子
            TreeNode biggestChild = node.getLeftChild();
            while (biggestChild.getRightChild() != null) {
                biggestChild = biggestChild.getRightChild();
            }
            node.setData(biggestChild.getData());
            node = biggestChild;
        }
        TreeNode child = node.getLeftChild() != null ? node.getLeftChild() : node.getRightChild();
        if (node == node.getFather().getLeftChild()) {
            // 是父亲的左孩子
            node.getFather().setLeftChild(child);
            if (child != null) {
                child.setFather(node.getFather());
            }
        } else {
            // 是父亲的右孩子
            node.getFather().setRightChild(child);
            if (child != null) {
                child.setFather(node.getFather());
            }
        }
        return new Tuple<>(node, child);
    }

    /**
     * 删除节点后着色与旋转
     * @param newNode 被替换成的节点
     * @return: void
     * @date: 2019/5/21 17:28
     */
    public void colorAndRotateAfterDelete(TreeNode newNode) {
        // 以下情况前提：替换节点和被删节点都是黑色
        if (newNode.getFather() == null) {
            // case1: 替换节点是根
            return;
        }
        if (newNode.getBrother().getColor() == NodeColor.RED) {
            // case2：兄弟节点是红色，对父亲左旋或右旋
            if (newNode == newNode.getFather().getLeftChild()) {
                newNode.getFather().leftRotate();
            } else {
                newNode.getFather().rightRotate();
            }
            newNode.getFather().setColor(NodeColor.RED);
            // 将原来的兄弟，现在的祖父置为黑色
            newNode.getGrandFather().setColor(NodeColor.BLACK);
        }
        if (newNode.getFather().getColor() == NodeColor.BLACK
                && newNode.getBrother().getColor() == NodeColor.BLACK
                && (newNode.getBrother().getRightChild() == null || newNode.getBrother().getRightChild().getColor() == NodeColor.BLACK)
                && (newNode.getBrother().getLeftChild() == null || newNode.getBrother().getLeftChild().getColor() == NodeColor.BLACK)) {
            // case3：父亲，兄弟，兄弟的儿子都是黑色
            newNode.getBrother().setColor(NodeColor.RED);
            colorAndRotateAfterDelete(newNode.getFather());
        } else if (newNode.getFather().getColor() == NodeColor.RED
                && newNode.getBrother().getColor() == NodeColor.BLACK
                && (newNode.getBrother().getRightChild() == null || newNode.getBrother().getRightChild().getColor() == NodeColor.BLACK)
                && (newNode.getBrother().getLeftChild() == null || newNode.getBrother().getLeftChild().getColor() == NodeColor.BLACK)) {
            // case4：父亲是红色，兄弟和兄弟的儿子都是黑色
            newNode.getBrother().setColor(NodeColor.RED);
            newNode.getFather().setColor(NodeColor.BLACK);
        } else if (newNode.getBrother().getColor() == NodeColor.BLACK) {
            // case5：兄弟是黑色，一个儿子是红色一个儿子是黑色
            if ((newNode.getBrother().getRightChild() == null || newNode.getBrother().getRightChild().getColor() == NodeColor.BLACK)
                    && (newNode.getBrother().getLeftChild() != null && newNode.getBrother().getLeftChild().getColor() == NodeColor.RED)
                        && newNode == newNode.getFather().getLeftChild()) {
                // 自己是左儿子, 兄弟有红左儿子，黑右儿子
                newNode.getBrother().setColor(NodeColor.RED);
                newNode.getBrother().getLeftChild().setColor(NodeColor.BLACK);
                newNode.getBrother().rightRotate();
            } else if ((newNode.getBrother().getRightChild() == null || newNode.getBrother().getRightChild().getColor() == NodeColor.RED)
                    && (newNode.getBrother().getLeftChild() != null && newNode.getBrother().getLeftChild().getColor() == NodeColor.BLACK)
                    && newNode == newNode.getFather().getRightChild()){
                // 自己是右儿子，兄弟有黑左儿子，红右儿子
                newNode.getBrother().setColor(NodeColor.RED);
                newNode.getBrother().getRightChild().setColor(NodeColor.BLACK);
                newNode.getBrother().leftRotate();
            }
            // case6：兄弟是黑色，兄弟右儿子是红色，自己是父亲的左儿子（反之也有）
            newNode.getBrother().setColor(newNode.getFather().getColor());
            newNode.getFather().setColor(NodeColor.BLACK);
            if (newNode == newNode.getFather().getLeftChild()) {
                newNode.getBrother().getRightChild().setColor(NodeColor.BLACK);
                newNode.getFather().leftRotate();
            } else {
                newNode.getBrother().getLeftChild().setColor(NodeColor.BLACK);
                newNode.getFather().rightRotate();
            }
        }
    }

}
