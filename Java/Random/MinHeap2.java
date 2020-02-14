import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Frankie Fasola
 *
 */
public class MinHeap2 {
	private ArrayList<Comparable> elements;
	
	public MinHeap2() {
		elements = new ArrayList<Comparable>();
		elements.add(null);
	}
	
	public void add(Comparable element) {
		elements.add(null);
		int index = elements.size() - 1;
		
		while (index > 1 && getParent(index).compareTo(element) > 0) {
			elements.set(index, getParent(index));
			index = getParentIndex(index);
		}
		elements.set(index, element);
	}
	
	public Comparable peek() {
		return elements.get(1);
	}
	
	public Comparable remove() {
		Comparable min = elements.get(1);
		int last = elements.size() - 1;
		if (last > 1) {
			elements.set(1, last);
			fixHeap();
		}
		return min;
	}
	
	private void fixHeap() {
		Comparable root = elements.get(1);
		int lastIndex = elements.size() - 1;
		int index = 1;
		boolean more = true;
		while (more) {
			int childIndex = getLeftChildIndex(index);
			if (childIndex <= lastIndex) {
					Comparable child = getLeftChild(index);
					if (getRightChildIndex(index) <= lastIndex && getRightChild(index).compareTo(child) < 0)
						childIndex = getRightChildIndex(index);
					child = getRightChild(index);
				if (child.compareTo(root) < 0) {
					elements.set(index, child);
					index = childIndex;
				} else
					more = false;
			} else
				more = false;
		}
		elements.set(index, root);
	}
	
	public int size() {
		return elements.size() - 1;
	}
	
	private static int getLeftChildIndex(int index) {
		return 2 * index;
	}
	
	private static int getRightChildIndex(int index) {
		return 2 * index + 1;
	}
	
	private static int getParentIndex(int index) {
		return index / 2;
	}
	
	private Comparable getLeftChild(int index) {
		return elements.get(2*index);
	}
	
	private Comparable getRightChild(int index) {
		return elements.get(2*index+1);
	}
	
	private Comparable getParent(int index) {
		return elements.get(index/2);
	}
	
	
	
}
