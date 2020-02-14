/**
 * 
 */

/**
 * @author Frankie Fasola
 *
 */
public class ArrayPractice {
	
	public static void main(String[] args) {
		ArrayPractice a = new ArrayPractice();
		int[] arr = {1,2,3,4,5};
		a.reverse(arr, 0, arr.length-1);
		a.print(arr);
		
	}
	
	public int findRotateCount(int[] arr) {
		int low = 0, high = arr.length - 1;
		while(arr[low] > arr[high]) {
			int mid = low + (high - low) /2;
			if (arr[mid] > arr[high])
				low = mid + 1;
			else
				high = mid;
		}
		return low;
	}
	
	public void printRepeating(int[] arr, int n) {
		for (int i = 0; i < arr.length; i++) {
			int index = arr[i] % n;
			arr[index]++;
		}
		
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 1)
				System.out.println(i);
		}
	}
	
	public void leftRotate(int[] arr, int n, int d) {
		int i, j, k, temp;
		for (i = 0; i < gcd(d,n); i++) {
			temp = arr[i];
			j = i;
			while (true) {
				k = j + d;
				if (k >= n) k -= n;
				if (k == i) break;
				arr[j] = arr[k];
				j = k;
			}
			arr[j] = temp;
		}
	}	
	
	public void rRotate(int[] arr, int k) {
		reverse(arr, 0, k-1);
		print(arr);
		reverse(arr, k, arr.length-1);
		print(arr);
		reverse(arr, 0, arr.length-1);
		
	}
	
	public void reverse(int[] arr, int i, int k) {
		for (; i < k; i++) {
			int temp = arr[i];
			arr[i] = arr[k-i];
			arr[k-i] = temp;
		}
	}
	
	public void rightRotate(int[] arr, int n, int d) {
		int i, j, k, temp;
		for (i = n-1; i > gcd(d,n); i--) {
			temp = arr[i];
			j = i;
			while (true) {
				k = j - d;
				if (k < 0) k += n;
				if (k == i) break;
				arr[j] = arr[k];
				j = k;
			}
			arr[k] = temp;
		}
	}
	
	public void print(int[] arr) {
		for (int i = 0; i < arr.length; i++) 
			System.out.print(arr[i] + " ");
		System.out.println();
	}
	
	public int gcd(int a, int b) {
		if (b == 0) return a;
		else return gcd (b, a%b);
	}
	
	public void generateNextPalindrome(int[] arr) {
		if (isAll9s(arr)) {
			System.out.println("1 ");
			for (int i = 0; i < arr.length; i++) 
				System.out.println("0 ");
			System.out.println("1");
		} else {
			generateNextPalindromeUtil(arr);
			print(arr);
		}
	}
	
	private void generateNextPalindromeUtil(int[] arr) {
		int mid = arr.length / 2;
		boolean leftSmaller = false;
		int i = mid - 1;
		int j = (arr.length % 2) == 1 ? mid + 1 : mid;
		//ignore middle digits if they are the same
		while (i >= 0 && arr[i] == arr[j]) {
			i--; j++;
		}
		if (i < 0 || arr[i] < arr[j])
			leftSmaller = true;
		
		while (i >= 0) {
			arr[j] = arr[i];
			j++;
			i--;
		}
		
		if (leftSmaller) {
			int carry = 1;
			i = mid - 1;
			// Check if the length is odd and increment middle digit
			if (arr.length % 2 == 1) {
				arr[mid] += carry;
				carry = arr[mid] / 10;
				arr[mid] %= 10;
				j = mid + 1;
			} else
				j = mid;
			// Add one to the most right number on the left side, then
			// continue until the most left number, copying to the right side
			while (i >= 0) {
				arr[i] += carry;
				carry = arr[i] / 10;
				arr[i] %= 10;
				arr[j++] = arr[i--];
			}
		}
	}
	
	public boolean isAll9s(int[] arr) {
		for (int i = 0; i < arr.length; i++)
			if (arr[i] != 9)
				return false;
		return true;
	}
	
	public int maxSubArraySum (int[] arr) {
		int max = arr[0], current = arr[0];
		for (int i = 1; 1 < arr.length; i++){
			current = Math.max(arr[i], arr[i] + current);
			max = Math.max(max,  current);
		}
		return max;
	}
	
	public int findMaxZeroCount (int[] arr) {
		int original = 0, current = 0, max = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == 0)
				original++;
			int value = arr[i] == 1 ? 1 : -1;
			current = Math.max(value, current + value);
			max = Math.max(current, max);
		}
		max = Math.max(max, 0);
		return original + max;
	}
}









