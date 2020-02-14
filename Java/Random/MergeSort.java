import java.util.*;
public class MergeSort<T extends Comparable<T>>
{	
	public static void main(String[] args)
	{
//		Integer[] a = {2, 6, 3, 5, 1};
//		MergeSort m = new MergeSort();
//		m.sort(a);
//		System.out.println(Arrays.toString(a));
		add(7,4);
	}
	
	public static int add(int x, int y) {
	    while(x != 0) {	    	
	        int t = (x & y) <<1;
	        System.out.println("Top of function: x: " + x + " y: " + y + " t: " + t);
	        y ^= x;
	        x = t;
	        System.out.println("Bottom of function: x: " + x + " y: " + y + " t: " + t);
	    }
	    return y;
	}
	
	public void sort(Comparable [] a) {
		Comparable[] b = new Comparable[a.length];
		mergeSort(a, b, 0, a.length - 1);
	}
	
	private void mergeSort(Comparable [] a, Comparable [] b, int left, int right) {
		if (left < right) {
			int middle = (left + right) / 2;
			mergeSort(a, b, left, middle);
			mergeSort(a, b, middle + 1, right);
			merge(a, b, left, middle + 1, right);
		}
	}
	
	private static void merge(Comparable [] a, Comparable [] b, int left, int right, int rightEnd ) {
		int leftEnd = right - 1;
        int k = left;
        int num = rightEnd - left + 1;
		
		while (left <= leftEnd && right <= rightEnd) 
			if (a[left].compareTo(a[right]) <= 0)
				b[k++] = a[left++];
			else
				b[k++] = a[right++];
		
		while (left <= leftEnd)
			b[k++] = a[left++];
		while (right <= rightEnd)
			b[k++] = a[right++];
		
		for (int i = 0; i < num; i++, rightEnd--)
			a[rightEnd] = b[rightEnd];
	}	
	
	public void sort2 (Comparable [] a) {
		Comparable [] b = new Comparable [a.length];
		mSort (a, b, 0, a.length - 1);
	}
	
	private void mSort (Comparable [] a, Comparable [] b, int left, int right) {
		if (left < right) {
			int middle = (left + right) / 2;
			mSort (a, b, left, middle);
			mSort (a, b, middle + 1, right);
			merge2 (a, b, left, middle + 1, right);
		}
	}
	
	private void merge2 (Comparable [] a, Comparable [] b, int left, int right, int rightEnd) {
		int leftEnd = right - 1;
		int num = rightEnd - left + 1;
		int k = left;
		
		while (left <= leftEnd && right <= rightEnd) {
			if (a[left].compareTo(a[right]) <= 0)
				b[k++] = a[left++];
			else
				b[k++] = a[right++];
		}
		while (left <= leftEnd) b[k++] = a[left++];
		while (right <= rightEnd) b[k++] = a[right++];
		
		for (int i = 0; i < num; i++, rightEnd--)
			a[rightEnd] = b[rightEnd];
	}

}