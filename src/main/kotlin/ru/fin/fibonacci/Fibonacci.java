package ru.fin.fibonacci;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public final class Fibonacci<T> {
    public static final class Entry<T> {
        private int mDegree = 0;       // Number of children
        private boolean mIsMarked = false; // Whether this node is marked
        private Entry<T> mNext;   // Next and previous elements in the list
        private Entry<T> mPrev;
        private Entry<T> mParent; // Parent in the tree, if any.
        private Entry<T> mChild;  // Child node, if any.
        private T mElem;     // Element being stored here
        private double mPriority; // Its priority

        public T getValue() {
            return mElem;
        }

        public int getDegree() {
            return mDegree;
        }

        private Entry(T elem, double priority) {
            mNext = mPrev = this;
            mElem = elem;
            mPriority = priority;
        }
    }

    private Entry<T> mMin = null;
    private int mSize = 0;

    public Entry<T> enqueue(T value, double priority) {
        Entry<T> result = new Entry<T>(value, priority);
        mMin = mergeLists(mMin, result);
        ++mSize;
        return result;
    }

    public Entry<T> min() {
        if (isEmpty())
            throw new NoSuchElementException("Heap is empty.");
        return mMin;
    }

    public boolean isEmpty() {
        return mMin == null;
    }

    public int size() {
        return mSize;
    }

    public Entry<T> pop() {
        if (isEmpty())
            throw new NoSuchElementException("Heap is empty.");
        --mSize;
        Entry<T> minElem = mMin;
        if (mMin.mNext == mMin) { // Case one
            mMin = null;
        } else { // Case two
            mMin.mPrev.mNext = mMin.mNext;
            mMin.mNext.mPrev = mMin.mPrev;
            mMin = mMin.mNext; // Arbitrary element of the root list.
        }
        if (minElem.mChild != null) {
            Entry<?> curr = minElem.mChild;
            do {
                curr.mParent = null;
                curr = curr.mNext;
            } while (curr != minElem.mChild);
        }
        mMin = mergeLists(mMin, minElem.mChild);
        if (mMin == null) return minElem;
        List<Entry<T>> treeTable = new ArrayList<Entry<T>>();
        List<Entry<T>> toVisit = new ArrayList<Entry<T>>();
        for (Entry<T> curr = mMin; toVisit.isEmpty() || toVisit.get(0) != curr; curr = curr.mNext)
            toVisit.add(curr);
        for (Entry<T> curr : toVisit) {
            while (true) {
                while (curr.mDegree >= treeTable.size())
                    treeTable.add(null);
                if (treeTable.get(curr.mDegree) == null) {
                    treeTable.set(curr.mDegree, curr);
                    break;
                }
                Entry<T> other = treeTable.get(curr.mDegree);
                treeTable.set(curr.mDegree, null); // Clear the slot
                Entry<T> min = (other.mPriority < curr.mPriority) ? other : curr;
                Entry<T> max = (other.mPriority < curr.mPriority) ? curr : other;
                max.mNext.mPrev = max.mPrev;
                max.mPrev.mNext = max.mNext;
                max.mNext = max.mPrev = max;
                min.mChild = mergeLists(min.mChild, max);
                max.mParent = min;
                max.mIsMarked = false;
                ++min.mDegree;
                curr = min;
            }
            if (curr.mPriority <= mMin.mPriority) mMin = curr;
        }
        return minElem;
    }

    public void delete(Entry<T> entry) {
        decreaseKeyUnchecked(entry, Double.NEGATIVE_INFINITY);
        pop();
    }

    private static <T> Entry<T> mergeLists(Entry<T> one, Entry<T> two) {
        if (one == null && two == null) { // Both null, resulting list is null.
            return null;
        } else if (one != null && two == null) { // Two is null, result is one.
            return one;
        } else if (one == null) { // One is null, result is two.
            return two;
        } else { // Both non-null; actually do the splice.
            Entry<T> oneNext = one.mNext; // Cache this since we're about to overwrite it.
            one.mNext = two.mNext;
            one.mNext.mPrev = one;
            two.mNext = oneNext;
            two.mNext.mPrev = two;
            return one.mPriority < two.mPriority ? one : two;
        }
    }

    private void decreaseKeyUnchecked(Entry<T> entry, double priority) {
        entry.mPriority = priority;
        if (entry.mParent != null && entry.mPriority <= entry.mParent.mPriority)
            cutNode(entry);
        if (entry.mPriority <= mMin.mPriority)
            mMin = entry;
    }

    private void cutNode(Entry<T> entry) {
        entry.mIsMarked = false;
        if (entry.mParent == null) return;
        if (entry.mNext != entry) { // Has siblings
            entry.mNext.mPrev = entry.mPrev;
            entry.mPrev.mNext = entry.mNext;
        }
        if (entry.mParent.mChild == entry) {
            if (entry.mNext != entry) {
                entry.mParent.mChild = entry.mNext;
            } else {
                entry.mParent.mChild = null;
            }
        }
        --entry.mParent.mDegree;
        entry.mPrev = entry.mNext = entry;
        mMin = mergeLists(mMin, entry);
        if (entry.mParent.mIsMarked)
            cutNode(entry.mParent);
        else
            entry.mParent.mIsMarked = true;
        entry.mParent = null;
    }
}
