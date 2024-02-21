import java.util.NoSuchElementException;

public class MyStack<Item> {

    Item[] items;
    int N ;

    public MyStack() {
        items = (Item[]) new Object[1];
        N = 0;
    }

    public Item pop() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        Item target = items[--N];
        items[N] = null;
        if(N > 0 && N == items.length/4) resize(items.length / 2);
        return target;
    }

    public void push(Item item) {

        if(item == null) {
            throw new IllegalArgumentException();
        }
        if(N == items.length) resize(items.length * 2);
        items[N++] = item;
    }

    private void resize(int capacity) {
        Item[] updated = (Item[]) new Object[2*capacity];

        System.arraycopy(items, 0, updated, 0, items.length);
        items = updated;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

}
