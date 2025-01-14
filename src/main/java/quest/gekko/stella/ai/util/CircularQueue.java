package quest.gekko.stella.ai.util;

import java.util.LinkedList;
import java.util.List;

public class CircularQueue<T> {
    private final LinkedList<T> list;
    private final int maxSize;

    public CircularQueue(final int maxSize) {
        this.list = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public void add(final T item) {
        if (list.size() >= maxSize) {
            list.poll();
        }

        list.add(item);
    }

    public List<T> getAll() {
        return new LinkedList<>(list);
    }
}
