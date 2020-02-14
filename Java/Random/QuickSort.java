
public class QuickSort
{
	public static <T extends Comparable<? super T>> void quickSort(T[] t, int a, int b) {
		if(a < b) {
			int i = a, j = b;
			T x = t[(i + j) / 2];
			do {
				while(t[i].compareTo(x) < 0) i++;
				while(x.compareTo(t[j]) < 0) j--;
				
				if(i <= j) {
					T temp = t[i];
					t[i] = t[j];
					t[j] = temp;
					i++;
					j--;					
				}
			} while (i <= j);
			quickSort(t, a, j);
			quickSort(t, i, b);
		}
	}
	
	public static <T extends Comparable <? super T>> void qSort (T [] t, int a, int b) {
		if (a<b) {
			int i = a, j = b;
			T x = t[(a+b)/2];
			do {
				if (i<=j) {
					while (t[i].compareTo(x) < 0) i++;
					while (x.compareTo(t[j]) < 0) j--;
					if (i<=j) {
						T temp = t[i];
						t[j] = t[i];
						t[i] = temp;
						i++;
						j--;
					}
				}
			} while (i <= j);
			qSort (t, a, j);
			qSort (t, i, b);
		}
	}
}
