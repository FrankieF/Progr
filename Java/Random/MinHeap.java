/**
 * 
 */
import java.lang.Comparable;
/**
 * @author Frankie Fasola
 *
 */
public class MinHeap<E extends Comparable<? super E>> {
	private E[] heap;
	private int size;
	private int n;
	
	public MinHeap (E[] heap, int num, int max) {
		this.heap = heap;
		this.n = num;
		this.size = max;
		heapify();
	}
	
	public int heapSize() {
		return n;
	}
	
	public boolean isLeaf(int position) {
		return (position >= n/2) && (position < n);
	}
	
	public int leftChild(int position) {
		return position < (n/2) ? 2*position + 1 : -1;
	}
	
	public int rightChild(int position) {
		return position < (n/2) ? 2*position + 2 : -1;
	}
	
	public int parent (int position) {
		return position > 0 ? (position-1)/2 : -1;
	}
	
	public void heapify () {
		for (int i = n/2-1; i >= 0; i--)
			siftdown(i);
	}
	
	public void insert (E value) {
		if (n < size) {
			int current = n++;
			heap[current] = value;
			while (current != 0 && (heap[current].compareTo(heap[parent(current)])) < 0) {
				swap(heap, current, parent(current));
				current = parent(current);
			}
		} else
			System.out.println("Heap is full");
	}
	
	
	private void siftdown(int position) {
		if (position < 0 || position > n)
			throw new IndexOutOfBoundsException();
		while (!isLeaf(position)) {
			int i = leftChild(position);
			if ((i <(n-1)) && (heap[i].compareTo(heap[i+1]) > 0)) 
				i++;
			if ((heap[position].compareTo(heap[i]) <= 0))
				return;
			swap(heap,position,i);
			position = i;
		}
	}
	
	public E extractMin() {
		if (n < 1)
			return null;
		swap(heap, 0, --n);
		if (n != 0)
			siftdown(0);
		return heap[n];
	}
	
	public E remove (int position) {
		if (position < 0 || position >= n)
			throw new IndexOutOfBoundsException();
		if (position == (n-1)) 
			n--;
		else {
			swap(heap,position,--n);
			while((position > 0) && (heap[position].compareTo(heap[parent(position)]) > 0)) {
				swap(heap,position,parent(position));
				position = parent(position);
			}
			if (n != 0)
				siftdown(position);
		}
		return heap[n];
	}
	
	private void swap(E[] heap, int a, int b) {
		E t = heap[a];
		heap[a] = heap[b];
		heap[b] = t;
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < heap.length; i++)
			s += heap[i] + " ";
		return s;
	}
}
