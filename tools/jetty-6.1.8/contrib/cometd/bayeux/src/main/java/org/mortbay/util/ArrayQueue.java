package org.mortbay.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/* ------------------------------------------------------------ */
/** Queue backed by circular array.
 * 
 * This partial Queue implementation (also with {@link #pop()} for stack operation)
 * is backed by a growable circular array.
 * 
 * @author gregw
 *
 * @param <E>
 */
public class ArrayQueue<E> implements Queue<E>
{
    private Object[] _elements;
    private int _nextE;
    private int _nextSlot;
    private int _size;
    private int _growCapacity;
    
    public ArrayQueue()
    {
        _elements=new Object[64];
        _growCapacity=32;
    }
    
    public ArrayQueue(int capacity)
    {
        _elements=new Object[capacity];
        _growCapacity=-1;
    }
    
    public ArrayQueue(int initCapacity,int growBy)
    {
        _elements=new Object[initCapacity];
        _growCapacity=growBy;
    }
    
    
    public synchronized boolean add(E e)
    {
        _size++;
        _elements[_nextSlot++]=e;
        if (_nextSlot==_elements.length)
            _nextSlot=0;
        if (_nextSlot==_nextE)
        {
            if (_growCapacity<=0)
                throw new IllegalStateException("Full");
           
            Object[] elements=new Object[_elements.length+_growCapacity];
                
            int split=_elements.length-_nextE;
            if (split>0)
                System.arraycopy(_elements,_nextE,elements,0,split);
            if (_nextE!=0)
                System.arraycopy(_elements,0,elements,split,_nextSlot);
            
            _elements=elements;
            _nextE=0;
            _nextSlot=_size;
        }
        
        return true;
    }

    public synchronized E element()
    {
        if (_nextSlot==_nextE)
            throw new NoSuchElementException();
        return (E)_elements[_nextE];
    }

    public synchronized boolean offer(E e)
    {
        _size++;
        _elements[_nextSlot++]=e;
        if (_nextSlot==_elements.length)
            _nextSlot=0;
        if (_nextSlot==_nextE)
        {
            if (_growCapacity<=0)
                return false;
            
            Object[] elements=new Object[_elements.length+_growCapacity];
                
            int split=_elements.length-_nextE;
            if (split>0)
                System.arraycopy(_elements,_nextE,elements,0,split);
            if (_nextE!=0)
                System.arraycopy(_elements,0,elements,split,_nextSlot);
            
            _elements=elements;
            _nextE=0;
            _nextSlot=_size;
        }
        
        return true;
    }

    public synchronized E peek()
    {
        if (_nextSlot==_nextE)
            return null;
        return (E)_elements[_nextE];
    }

    public synchronized E poll()
    {
        if (_nextSlot==_nextE)
            return null;
        E e = (E)_elements[_nextE];
        _elements[_nextE]=null;
        if (++_nextE==_elements.length)
            _nextE=0;
        return e;
    }

    public synchronized E pop()
    {
        if (_nextSlot==_nextE)
            return null;
        _nextSlot=(_nextSlot==0?_elements.length:_nextSlot)-1;
        E e = (E)_elements[_nextSlot];
        _elements[_nextSlot]=null;
        return e;
    }

    public synchronized E remove()
    {
        if (_nextSlot==_nextE)
            throw new NoSuchElementException();
        E e = (E)_elements[_nextE++];
        if (_nextE==_elements.length)
            _nextE=0;
        return e;
    }

    public synchronized boolean addAll(Collection<? extends E> c)
    {
        throw new UnsupportedOperationException();
    }

    public synchronized void clear()
    {
        _size=0;
        _nextE=0;
        _nextSlot=0;
    }

    public boolean contains(Object o)
    {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection<?> c)
    {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty()
    {
        return _size==0;
    }

    public synchronized Iterator<E> iterator()
    {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object o)
    {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> c)
    {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> c)
    {
        throw new UnsupportedOperationException();
    }

    public int size()
    {
        return _size;
    }

    public Object[] toArray()
    {
        throw new UnsupportedOperationException();
    }

    public <T> T[] toArray(T[] a)
    {
        throw new UnsupportedOperationException();
    }

}
