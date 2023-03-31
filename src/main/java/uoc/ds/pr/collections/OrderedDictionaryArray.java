package uoc.ds.pr.collections;

import edu.uoc.ds.adt.helpers.KeyValue;
import edu.uoc.ds.adt.sequential.DictionaryArrayImpl;
import edu.uoc.ds.algorithms.MergeSort;
import edu.uoc.ds.algorithms.SortingAlgorithm;
import edu.uoc.ds.exceptions.FullContainerException;

public class OrderedDictionaryArray<K, V> extends DictionaryArrayImpl<K, V> {

    private SortingAlgorithm<KeyValue<K, V>> mergeSortDictionary;

    public OrderedDictionaryArray(int n) {
        super(n);
        mergeSortDictionary = new MergeSort<>();
    }

    @Override
    public void put(K key, V value) {
        if (this.isFull()) {
            throw new FullContainerException();
        } else {
            int i = binarySearchByKey((Comparable<K>) key, 0, this.n - 1);
            this.dictionary[i] = new KeyValue<>(key, value);
            if (i == this.n) {
                this.n++;
                mergeSortDictionary.sort(this.dictionary, this.n);
            }
        }
    }

    @Override
    public V delete(K key) {
        V elem = null;
        int i = binarySearchByKey((Comparable<K>) key, 0, this.n - 1);
        if (i < this.n) {
            elem = this.dictionary[i].getValue();
            this.dictionary[i] = this.dictionary[this.n - 1];
            this.dictionary[this.n - 1] = null;
            --this.n;
        }
        return elem;
    }

    @Override
    public V get(K key) {
        int i = binarySearchByKey((Comparable<K>) key, 0, this.n - 1);
        if (i < this.n) {
            return this.dictionary[i].getValue();
        }
        return null;
    }

    protected int binarySearchByKey(Comparable<K> key, int low, int high) {
        int middle = low + ((high - low) / 2);
        if (low > high) {
            return this.n;
        }
        if (key.equals(this.dictionary[middle].getKey())) {
            return middle;
        } else if (key.compareTo(this.dictionary[middle].getKey()) < 0) {
            return binarySearchByKey(key, low, middle - 1);
        } else {
            return binarySearchByKey(key, middle + 1, high);
        }
    }

}
