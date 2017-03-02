import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AlgorithmExample {

    private static List<Node> nodeList = null;
    private static List<Integer> numbers = new ArrayList<>();

    /**
     * 内部类：节点
     *
     * @author ocaicai@yeah.net @date: 2011-5-17
     */
    private static class Node {
        Node leftChild;
        Node rightChild;
        String data;

        Node(String newData) {
            leftChild = null;
            rightChild = null;
            data = newData;
        }
    }

    public void createBinTree() {
        nodeList = new LinkedList<Node>();

        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");
        Node E = new Node("E");
        Node F = new Node("F");
        Node G = new Node("G");
        Node H = new Node("H");

        A.leftChild = B;
        A.rightChild = E;
        B.leftChild = C;
        B.rightChild = D;
        E.rightChild = F;
        E.leftChild = H;
        F.leftChild = G;

        nodeList.add(A);
        nodeList.add(B);
        nodeList.add(C);
        nodeList.add(D);
        nodeList.add(E);
        nodeList.add(F);
        nodeList.add(G);
        nodeList.add(H);

        /*
        // 将一个数组的值依次转换为Node节点
        for (int nodeIndex = 0; nodeIndex < array.length; nodeIndex++) {
            nodeList.add(new Node(array[nodeIndex]));
        }
        // 对前lastParentIndex-1个父节点按照父节点与孩子节点的数字关系建立二叉树
        for (int parentIndex = 0; parentIndex < array.length / 2 - 1; parentIndex++) {
            // 左孩子
            nodeList.get(parentIndex).leftChild = nodeList
                    .get(parentIndex * 2 + 1);
            // 右孩子
            nodeList.get(parentIndex).rightChild = nodeList
                    .get(parentIndex * 2 + 2);
        }
        // 最后一个父节点:因为最后一个父节点可能没有右孩子，所以单独拿出来处理
        int lastParentIndex = array.length / 2 - 1;
        // 左孩子
        nodeList.get(lastParentIndex).leftChild = nodeList
                .get(lastParentIndex * 2 + 1);
        // 右孩子,如果数组的长度为奇数才建立右孩子
        if (array.length % 2 == 1) {
            nodeList.get(lastParentIndex).rightChild = nodeList
                    .get(lastParentIndex * 2 + 2);
        }
        */
    }

    /**
     * 先序遍历
     * <p>
     * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
     *
     * @param node 遍历的节点
     */
    public static void preOrderTraverse(Node node) {
        if (node == null)
            return;
        System.out.print(node.data + " ");
        preOrderTraverse(node.leftChild);
        preOrderTraverse(node.rightChild);
    }

    /**
     * 中序遍历
     * <p>
     * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
     *
     * @param node 遍历的节点
     */
    public static void inOrderTraverse(Node node) {
        if (node == null)
            return;
        inOrderTraverse(node.leftChild);
        System.out.print(node.data + " ");
        inOrderTraverse(node.rightChild);
    }

    /**
     * 后序遍历
     * <p>
     * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
     *
     * @param node 遍历的节点
     */
    public static void postOrderTraverse(Node node) {
        if (node == null)
            return;
        postOrderTraverse(node.leftChild);
        postOrderTraverse(node.rightChild);
        System.out.print(node.data + " ");
    }

    public static void main(String[] args) {
        AlgorithmExample binTree = new AlgorithmExample();
        binTree.createBinTree();
        // nodeList中第0个索引处的值即为根节点
        Node root = nodeList.get(0);

        System.out.println("先序遍历：");
        preOrderTraverse(root);
        System.out.println();

        System.out.println("中序遍历：");
        inOrderTraverse(root);
        System.out.println();

        System.out.println("后序遍历：");
        postOrderTraverse(root);

        for (int i = 1; i <= 10; i++) {
            numbers.add(fibonacci(i));
        }

        System.out.println();
        System.out.println("斐波拉契数列: ");

        System.out.println(numbers);

        System.out.println();
        System.out.println("汉诺塔: ");

        move(3, 'A', 'B', 'C');

        System.out.println();
        System.out.println("47月前存款: ");

        System.out.println(balance1(1));

        System.out.println();
        System.out.println("47月后存款: ");

        System.out.println(balance2(48));
    }

    static int fibonacci(int n) {

        if (n <= 2) return 1;

        return fibonacci(n - 1) + fibonacci(n - 2);

    }

    static void move(int n, char a, char b, char c) {
        if (n == 1) {
            System.out.println("盘 " + n + " 由 " + a + " 移至 " + c);
        }else {
            move(n - 1, a, c, b);
            System.out.println("盘 " + n + " 由 " + a + " 移至 " + c);
            move(n - 1, b, a, c);
        }
    }

    static double balance1(int n){
        if (n == 48) return 3000;

        return balance1(n + 1)/(1 + 0.0171/12) + 3000;
    }

    static double balance2(int n){

        if (n ==1) return 139288.0661922625;

        return (balance2(n-1) - 3000) * (1 + 0.0171/12);
    }
}
