/**
 * 
 */
import java.util.HashMap;
import java.util.Map;
/**
 * @author Frankie Fasola
 *
 */

public class LinkedList {

	private Node head;
	
	public static void main(String[] args) {
		LinkedList list = new LinkedList(new Node(1));
		list.add(8);
		list.add(4);
		list.add(18);
		list.add(17);
		list.add(99);
		list.add(2);
		
		System.out.println("Original List:");
		list.print(list.head);
		System.out.println("After Merge Sort: ");
		list.head = list.sort(list.head, true, true);
		list.print(list.head);		
	}
	
	public LinkedList() {
		this.head = null;
	}
	
	public LinkedList(Node head) {
		this.head = head;
	}
	
	public void push(int data) {
		Node node = new Node(data);
		node.next = this.head;
		this.head = node;
	}
	
	public void push (Node head, int data) {
		Node node = new Node(data);
		node.next = head;
		head = node;
	}
	
	public Node addAtFront(Node head, Node node) {
		if (head == null)
			return node;
		node.next = head;
		return node;
	}
	
	public void add(int data) {
		add(this.head, data);
	}
	
	private void add(Node head, int data) {
		Node node = new Node(data);
		Node current = head;
		if (current != null) {
			while (current.next != null) {
				current = current.next;
			}
			current.next = node;
		} else
			head = node;
	}
	
	public void print(Node node) {
		while (node != null) {
			Node random = node.random;
			int data = random == null ? -1 : random.data;
			System.out.println("The data in the node is: " + node.data + 
								", The random node data is: " + data);
			node = node.next;
			System.out.println();
		}
	}
	
	public void deleteNthNodeFromEnd(int n) {
		if (n < 1)
			return;
		Node fast = head, slow = head;
		while (n-- > 0)
			fast = fast.next;
		if (fast == null) {
			head = head.next;
			return;
		}
		while (fast.next != null) {
			fast = fast.next;
			slow = slow.next;
		}
		slow.next = slow.next.next;
//		int count = 0;
//		while (count < n) {
//			if (fast == null) {
//				System.err.println("N is greater then length of the list.");
//				return;
//			}
//			count++;
//			fast = fast.next;
//		}
//		if (fast == null) {
//			head = head.next;
//			return;
//		}
//		while (fast.next != null) {
//			fast = fast.next;
//			slow = slow.next;
//		}
//		slow.next = slow.next.next;
	}
	
	public LinkedList clone() {
		Node original = this.head, clone = null;
		Map<Node,Node> map = new HashMap<Node,Node>();
		while (original != null) {
			clone = new Node(original.data);
			map.put(original, clone);
			original = original.next;
		}
		original = this.head;
		
		while (original != null) {
			clone = map.get(original);
			clone.next = map.get(original.next);
			clone.random = map.get(original.random);
			original = original.next;
		}	
		return new LinkedList(map.get(this.head));
	}
	
	public Node cloneO1() {
		Node current = this.head, temp;
		while (current != null) {
			temp = current.next;
			current.next = new Node(current.data);
			current.next.next = temp;
			current = temp;
		}
		current = this.head;
		while (current != null) {
			current.next.random = current.random.next;
			current = current.next == null ? current.next : current.next.next;
		}
		Node original = this.head, clone = original.next;
		temp = clone;
		while (original != null && clone != null) {
			original.next = original.next != null ? original.next.next : original.next;
			clone.next = clone.next != null ? clone.next.next : clone.next;
			original = original.next;
			clone = clone.next;
		}
		return temp;
	}
	
	public Node addList(Node h1, Node h2, boolean isExtraSpace) {
		Node result = new Node();
		if (isExtraSpace)
			result = addListExtraSpace(h1,h2);
		else
			result = addList(h1,h2);
		return result;
	}
	
	private Node addListExtraSpace(Node h1, Node h2) {
		Node result = null, temp = null, prev = null;
		int sum = 0, carry = 0;
		while (h1 != null || h2 != null) {
			sum = carry + (h1 != null ? h1.data : 0) + (h2 != null ? h2.data : 0);
			
			carry = sum > 10 ? sum / 10 : 0;
			sum = sum % 10;
			temp = new Node(sum);
			if (result == null)
				result = temp;
			else
				prev.next = temp;
			prev = temp;
			
			if (h1 != null) h1 = h1.next;
			if (h2 != null) h2 = h2.next;
		}
		if (carry > 0)
			temp.next = new Node(carry);
		return result;
	}
	
	private Node addList(Node h1, Node h2) {
		Node current;
		Node result = new Node();
		if (h2 == null) {
			result = h1;
			return result;
		}
		if (h1 == null) {
			result = h2;
			return result;
		}
		
		int length1 = size(h1);
		int length2 = size(h2);
		Integer carry = 0;
		
		if (length1 == length2)
			result = addSameSize(h1,h2,carry);
		else {
			int difference = Math.abs(length1 - length2);
			if (length2 > length1)
				swapNodes(h1,h2);
			
			for (current = h1; (difference--) > 0; current = current.next);
			result = addSameSize(current,h2,carry);
			result = addCarryToRemaining(h1, current, result, carry);			
		}
		if (carry > 0)
			push(result, carry);
		return result;
		
	}
	
	private Node addSameSize(Node a, Node b, Integer carry) {
		if (a == null)
			return null;
		int sum = 0;
		Node result = new Node();
		result.next = addSameSize(a.next,b.next,carry);
		sum = a.data + b.data + carry;
		carry = sum / 10;
		sum %= 10;
		result.data = sum;
		return result;
	}
	
	public Node addCarryToRemaining(Node h1, Node current, Node result, Integer carry) {
		if (h1 == current)
			return result;
		int sum = 0;
		Node node = new Node();
		node.next = addCarryToRemaining(h1.next, current, result, carry);
		sum = h1.data + carry;
		carry = sum / 10;
		sum %= 10;
		node.data = sum;
		return node;
	}
	
	private void swapNodes(Node a, Node b) {
		Node x = new Node();
		x.next = a.next;
		x.data = a.data;
		a.next = b.next;
		a.data = b.data;
		b.next = x.next;
		b.data = x.data;
	}
	
	private int size(Node node) {
		int count = 0;
		while (node != null) {
			count++;
			node = node.next;
		}
		return count;
	}
	
	public Node reverse(Node head) {
		Node prev = null, current = head, next = null;
		while (current != null) {
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
		return prev;
	}
	
	public Node reverseKNodes(Node head, int k) {
		Node current = head, next = null, previous = null;
		int count = 0;
		while (count < k && current != null) {
			next = current.next;
			current.next = previous;
			previous = current;
			current = next;			
			count++;
		}
		if (next != null)
			head.next = reverseKNodes(next, k);
		return previous;		
	}
	
	public boolean isPalindrome(Node head) {
		Node slow = head, fast = head;
		Node secondHalf, prevSlow = head, mid = null;
		boolean result = true;
		
		if (head != null && head.next != null) {
			while (fast != null && fast.next != null) {
				fast = fast.next.next;
				prevSlow = slow;
				slow = slow.next;
			}
			if (fast != null) {
				mid = slow;
				slow = slow.next;
			}
			secondHalf = slow;
			prevSlow.next = null;
			secondHalf = reverse(secondHalf);
			result = compareLists(head, secondHalf);
			secondHalf = reverse(secondHalf);
			if (mid != null) {
				prevSlow.next = mid;
				mid.next = secondHalf;
			} else
				prevSlow.next = secondHalf;
		}
		return result;
	}
	
	public boolean compareLists(Node h1, Node h2) {
		Node a = h1, b = h2;
		while (a != null && b != null) {
			if (a.data == b.data) {
				a = a.next;
				b = b.next;
			} else
				return false;
		}
		if (a == null && b == null)
			return true;
		return false;
	}
	
	public Node sort(Node head, boolean quickSort, boolean isAscending) {
		return quickSort ? quickSort(head) : mergeSort(head, isAscending);
	}
	
	private Node quickSort(Node head) {
		if (head == null || head.next == null)
			return head;
		Node smaller = null, larger = null, pivot = head, temp = head.next;
		LinkedList list = new LinkedList();
		while (temp != null) {
			Node next = temp.next;
			temp.next = null;
			if (temp.data < pivot.data) 
				smaller = list.addAtFront(smaller, temp);
			else
				larger = list.addAtFront(larger, temp);
			temp = next;
		}
		
		smaller = quickSort(smaller);
		larger = quickSort(larger);
		Node tail1 = smaller;
		while (tail1 != null && tail1.next != null)
			tail1 = tail1.next;
		if (smaller != null) {
			tail1.next = pivot;
			pivot.next = larger;
			return smaller;
		} else {
			pivot.next = larger;
			return pivot;
		}
	}
	
	public Node mergeSort(Node head, boolean isAscending) {
		if (head == null|| head.next == null)
			return head;
		Node head1 = frontBackSplit(head);
		head = mergeSort(head, isAscending);
		head1 = mergeSort(head1, isAscending);
		return merge(head, head1, isAscending);
	}
	
	public Node frontBackSplit(Node head) {
		if (head == null)
		return null;
		Node fast = head.next, slow = head;
		while (fast != null && fast.next != null) {
			fast = fast.next.next;
			slow = slow.next;
		}
		Node newHead = slow.next;
		slow.next = null;
		return newHead;
	}
	
	public Node merge(Node head1, Node head2, boolean isAscending) {
		if (head1 == null)
			return head2;
		if (head2 == null)
			return head1;
		if (isAscending) {
			if (head1.data <= head2.data) {
				head1.next = merge(head1.next, head2, isAscending);
				return head1;
			} else {
				head2.next = merge(head1, head2.next, isAscending);
				return head2;
			}
		} else {
			if (head1.data >= head2.data) {
				head1.next = merge(head1.next, head2, isAscending);
				return head1;
			} else {
				head2.next = merge(head1, head2.next, isAscending);
				return head2;
			}
		}
	}
}
