package uoc.ds.pr.collections;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.Queue;

public class LinkedQueue<E> extends LinkedList<E> implements Queue<E> {

    protected LinkedNode<E> first;

    public LinkedQueue() {
        super();
        this.first = null;
    }

    @Override
    public void add(E e) {
        LinkedNode<E> newNode = new LinkedNode<>(e);
        if (first == null) {
            first = newNode;
        } else {
            last.setNext(newNode);
        }
        last = newNode;
        n++;
    }

    @Override
    public E poll() {
        LinkedNode<E> aux = first;
        first = first.getNext();
        if (first == null) {
            last = null;
        }
        n--;
        return aux.getElem();
    }

    @Override
    public E peek() {
        return first.getElem();
    }
}
