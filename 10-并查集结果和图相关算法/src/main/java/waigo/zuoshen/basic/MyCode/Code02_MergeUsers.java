package waigo.zuoshen.basic.MyCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-25 8:09
 */
/*面试题：
每个用户有以下字段：b站账号、qq号、手机号
如果三个字段有一个相同，则说明这是一个人，给你一个用户集合，问你有多少个人。
 */
public class Code02_MergeUsers {
    public static class User {
        private String bid;
        private String qqId;
        private String phoneNumber;

        public User(String bid, String qqId, String phoneNumber) {
            this.bid = bid;
            this.qqId = qqId;
            this.phoneNumber = phoneNumber;
        }

        @Override
        public String toString() {
            return "User{" + "bid='" + bid + '\'' + ", qqId='" + qqId + '\'' + ", phoneNumber='" + phoneNumber + '\'' + '}';
        }
    }

    public static class Node<V> {
        private V value;
        private Node<V> parent;

        public Node(V value) {
            this.value = value;
        }
    }

    public static class UnionSet<V> {
        private HashMap<V, Node<V>> nodes;
        private HashMap<Node<V>, Integer> sizeMap;

        public UnionSet(List<V> values) {
            nodes = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V value : values) {
                Node<V> node = new Node<>(value);
                nodes.put(value, node);
                node.parent = node;
                sizeMap.put(node, 1);
            }
        }

        public boolean isSameSet(V x, V y) {
            if (!nodes.containsKey(x) || !nodes.containsKey(y)) {
                return false;
            }
            return findFather(nodes.get(x)) == findFather(nodes.get(y));
        }

        public void union(V x, V y) {
            if (!nodes.containsKey(x) || !nodes.containsKey(y)) return;
            Node<V> xFather = findFather(nodes.get(x));
            Node<V> yFather = findFather(nodes.get(y));
            if (xFather == yFather) return;
            //小挂大
            Node<V> small = sizeMap.get(xFather) > sizeMap.get(yFather) ? yFather : xFather;
            small.parent = small == xFather ? yFather : xFather;
            sizeMap.remove(small);
        }

        private Node<V> findFather(Node<V> node) {
            Node<V> cur = node;
            Stack<Node<V>> stack = new Stack<>();
            while (cur != cur.parent) {
                stack.push(cur);
                cur = cur.parent;
            }
            //cur指向了代表节点
            while (!stack.isEmpty()) {
                stack.pop().parent = cur;
            }
            return cur;
        }

        public int getSetSize() {
            return sizeMap.size();
        }
    }

    public static int getUserNum(User[] users) {
        UnionSet<User> unionSet = new UnionSet<>(Arrays.asList(users));
        HashMap<String, User> bidMap = new HashMap<>();
        HashMap<String, User> qqMap = new HashMap<>();
        HashMap<String, User> phoneMap = new HashMap<>();
        for (User user : users) {
            if (!bidMap.containsKey(user.bid)) {
                bidMap.put(user.bid, user);
            } else {
                unionSet.union(user, bidMap.get(user.bid));
            }
            if (!qqMap.containsKey(user.qqId)) {
                qqMap.put(user.qqId, user);
            } else {
                unionSet.union(user, qqMap.get(user.qqId));
            }

            if (!phoneMap.containsKey(user.phoneNumber)) {
                phoneMap.put(user.phoneNumber, user);
            } else {
                unionSet.union(user, phoneMap.get(user.phoneNumber));
            }
        }
        return unionSet.getSetSize();
    }

    public static User[] getRandomUser(int maxSize) {
        if (maxSize < 0) return null;
        int size = (int) (Math.random() * (maxSize + 1));
        User[] users = new User[size];
        for (int i = 0; i < size; i++) {
            users[i] = new User(getRandomId(), getRandomId(), getRandomId());
        }
        return users;
    }

    public static String getRandomId() {
        char[] idChars = new char[2];
        for (int i = 0; i < idChars.length; i++) {
            idChars[i] = (char) ((int) (Math.random() * 3) + 'a');
        }
        return String.valueOf(idChars);
    }

    public static void main(String[] args) {
        User[] randomUser = getRandomUser(5);
        Logger.getGlobal().info(Arrays.toString(randomUser));
        Logger.getGlobal().info("user sum:" + getUserNum(randomUser));
    }
}
