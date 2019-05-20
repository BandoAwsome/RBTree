
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

    public TreeNode getGrandFather() {
        return father.father;
    }

    public TreeNode getBrother() {
        if (this == father.leftChild) {
            // 是父亲的左孩子
            return father.rightChild;
        }
        return father.leftChild;
    }

    public TreeNode getUncle() {
        TreeNode grandFather = father.father;
        if (father == grandFather.leftChild) {
            // 父亲是祖父的左孩子
            return grandFather.rightChild;
        }
        return grandFather.leftChild;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
