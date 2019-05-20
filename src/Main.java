import sun.reflect.generics.tree.Tree;

import java.io.Reader;
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
                tree.insert(newNode);
            } catch (Exception e) {
                // 输入非数字，退出
                break;
            }
        }
        // 打印树的情况, 层次遍历
        tree.visit();
    }
}
