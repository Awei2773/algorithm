package waigo.zuoshen.basic.MyCode;

import java.util.PriorityQueue;

/**
 * author waigo
 * create 2021-02-17 22:36
 */
public class Test {
    public static class Pair<T> {
        private T first;
        private T second;

        public boolean hasNulls(Pair<?> p) {
            p.setFirst(null);
            return p.getFirst() == null || p.getSecond() == null;
        }

        public T getFirst() {
            return first;
        }

        public void setFirst(T first) {
            this.first = first;
        }

        public T getSecond() {
            return second;
        }

        public void setSecond(T second) {
            this.second = second;
        }
    }


}
