
/**
 * 树节点
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/20 18:36
 */
public class TreeNode {

    /** 左孩子 */
    private TreeNode leftChild;

    /** 右孩子 */
    private TreeNode rightChild;

    /** 父节点 */
    private TreeNode father;

    /** 节点颜色，默认红色 */
    private NodeColor color = NodeColor.RED;

    /** 储存值 */
    private int data;

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }

    public TreeNode getFather() {
        return father;
    }

    public void setFather(TreeNode father) {
        this.father = father;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public NodeColor getColor() {
        return color;
    }

    public void setColor(NodeColor color) {
        this.color = color;
    }

    /**
     * 获得祖父节点
     * @return: TreeNode
     * @date: 2019/5/20 20:56
     */
    public TreeNode getGrandFather() {
        return father.father;
    }

    /**
     * 获得兄弟节点
     * @return: TreeNode
     * @date: 2019/5/20 20:55
     */
    public TreeNode getBrother() {
        if (this == father.leftChild) {
            // 是父亲的左孩子
            return father.rightChild;
        }
        return father.leftChild;
    }

    /**
     * 获得叔叔节点
     * @return: TreeNode
     * @date: 2019/5/20 20:55
     */
    public TreeNode getUncle() {
        TreeNode grandFather = father.father;
        if (father == grandFather.leftChild) {
            // 父亲是祖父的左孩子
            return grandFather.rightChild;
        }
        return grandFather.leftChild;
    }

    /**
     * 左旋节点
     * @return: void
     * @date: 2019/5/21 11:46
     */
    public void leftRotate() {
        if (rightChild == null) {
            // 没有右孩子无法左旋
            return;
        }
        // 保存右孩子的左孩子
        TreeNode tmpChild = rightChild.leftChild;
        if (father != null) {
            if (this == father.leftChild) {
                // 是父亲的左孩子
                father.leftChild = rightChild;
            } else {
                // 是父亲的右孩子
                father.rightChild = rightChild;
            }
        }
        rightChild.father = father;
        father = rightChild;
        rightChild.leftChild = this;
        this.rightChild = tmpChild;
        if (tmpChild != null) {
            tmpChild.father = this;
        }
    }

    /**
     * 右旋节点
     * @return: void
     * @date: 2019/5/21 11:47
     */
    public void rightRotate() {
        if (leftChild == null) {
            // 没有左孩子不能右旋
            return;
        }
        // 保存左孩子的右孩子
        TreeNode tmpChild = leftChild.rightChild;
        if (father != null) {
            if (this == father.leftChild) {
                // 是父亲的左孩子
                father.leftChild = leftChild;
            } else {
                // 是父亲的右孩子
                father.rightChild = leftChild;
            }
        }
        leftChild.father = father;
        father = leftChild;
        leftChild.rightChild = this;
        this.leftChild = tmpChild;
        if (tmpChild != null) {
            tmpChild.father = this;
        }
    }
}
