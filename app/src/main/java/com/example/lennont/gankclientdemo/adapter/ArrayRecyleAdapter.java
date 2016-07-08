package com.example.lennont.gankclientdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by LennonT on 2016/7/7.
 */
public abstract class ArrayRecyleAdapter<E, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH>
        implements List<E>
{

    private final List<E> list;

    public ArrayRecyleAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public void add(int location, E object) {

    }

    @Override
    public boolean add(E object) {
        return false;
    }

    @Override
    public boolean addAll(int location, Collection<? extends E> collection) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean contains(Object object) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public E get(int location) {
        return list.get(location);
    }

    @Override
    public int indexOf(Object object) {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @NonNull
    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int lastIndexOf(Object object) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<E> listIterator(int location) {
        return null;
    }

    @Override
    public E remove(int location) {
        return null;
    }

    @Override
    public boolean remove(Object object) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public E set(int location, E object) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @NonNull
    @Override
    public List<E> subList(int start, int end) {
        return null;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] array) {
        return null;
    }

    public void replaceWith(List<E> data) {
        if (list.isEmpty() && data.isEmpty()) {
            return;
        }

        if (list.isEmpty()) {
            addAll(data);
            return;
        }

        if (data.isEmpty()) {
            clear();
            return;
        }

        if (list.equals(data)) {
            return;
        }

        // 首先将旧列表有、新列表没有的从旧列表去除
        retainAll(data);

        // 如果列表被完全清空了，那就直接全部插入好了
        if (list.isEmpty()) {
            addAll(data);
            return;
        }

        // 然后遍历新列表，对旧列表的数据更新、移动、增加
        for (int indexNew = 0; indexNew < data.size(); indexNew++) {
            E item = data.get(indexNew);

            int indexOld = indexOf(item);

            if (indexOld == -1) {
                add(indexNew, item);
            } else if (indexOld == indexNew) {
                set(indexNew, item);
            } else {
                list.remove(indexOld);
                list.add(indexNew, item);
                notifyItemMoved(indexOld, indexNew);
            }
        }
    }

}
