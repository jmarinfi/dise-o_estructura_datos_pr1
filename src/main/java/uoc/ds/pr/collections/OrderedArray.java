package uoc.ds.pr.collections;

import edu.uoc.ds.adt.sequential.FiniteContainer;
import edu.uoc.ds.algorithms.MergeSort;
import edu.uoc.ds.algorithms.SortingAlgorithm;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.IteratorArrayImpl;

public class OrderedArray<E> implements FiniteContainer<E> {

    private E[] array;
    private int n;
    private SortingAlgorithm<E> mergeSortArray;

    public OrderedArray(int max) {
        this.array = (E[]) new Object[max];
        this.n = 0;
        mergeSortArray = new MergeSort<>();
    }

    public void insert(E elem) {
        this.array[n] = elem;
        this.n++;
        this.mergeSortArray.sort(this.array, this.n);
    }

    public int delete(E elem) {
        for (int i = 0; i < this.n; i++) {
            if (this.array[i].equals(elem)) {
                this.array[i] = this.array[this.n - 1];
                this.array[this.n - 1] = null;
                this.n--;
                this.mergeSortArray.sort(this.array, this.n);
                return i;
            }
        }
        return -1;
    }

    public void sort() {
        mergeSortArray.sort(this.array, this.n);
    }

    public E getLast() {
        return this.array[this.n - 1];
    }

    @Override
    public boolean isFull() {
        return n == array.length;
    }

    @Override
    public boolean isEmpty() {
        return n == 0;
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public Iterator<E> values() {
        return new IteratorArrayImpl<>(array, n, 0);
    }

}
