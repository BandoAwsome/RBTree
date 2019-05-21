/**
 * 二元组，用于方法解耦
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/21 17:15
 */
public class Tuple<T, K> {

    /** 左元素 */
    public T left;

    /** 右元素 */
    public K right;

    public Tuple(T left, K right) {
        this.left = left;
        this.right = right;
    }
}
