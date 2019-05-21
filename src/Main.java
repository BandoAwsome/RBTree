import java.util.*;
import java.util.stream.Collectors;

/**
 * 主方法
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/20 18:36
 */
public class Main {
    public static void main(String[] args) {
        RBTree tree = new RBTree();
        // 标准输入输出流
        Scanner scanner = new Scanner(System.in);
        Set<TreeNode> nodeSet = new HashSet<>();
        // 加入节点
        while (true) {
            try {
                int data = scanner.nextInt();
                TreeNode newNode = new TreeNode();
                newNode.setData(data);
                nodeSet.add(newNode);
                // 插入树中
                tree.add(newNode);
            } catch (Exception e) {
                // 输入非数字，退出
                if (!(e instanceof InputMismatchException)) {
                    e.printStackTrace();
                }
                break;
            }
        }
        // 打印树的情况, 层次遍历
        tree.visit();
        scanner = new Scanner(System.in);
        // 删除节点
        while (true) {
            try {
                int data = scanner.nextInt();
                // 取出指定节点缓存
                List<TreeNode> resultList = nodeSet.stream().filter(treeNode -> treeNode.getData() == data).collect(Collectors.toList());
                Optional<TreeNode> optional = resultList.stream().findFirst();
                TreeNode deleteNode = optional.orElse(null);
                // 删除节点
                tree.delete(deleteNode);
            } catch (Exception e) {
                // 输入非数字，退出
                if (!(e instanceof InputMismatchException)) {
                    e.printStackTrace();
                }
                break;
            }
        }
        // 打印树的情况, 层次遍历
        tree.visit();
    }
}
