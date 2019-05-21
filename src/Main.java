import java.util.InputMismatchException;
import java.util.Scanner;

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
        while (true) {
            try {
                int data = scanner.nextInt();
                TreeNode newNode = new TreeNode();
                newNode.setData(data);
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
    }
}
