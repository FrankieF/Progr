/**
 * 
 */
import java.util.ArrayList;
import java.util.*;
import java.util.List;
/**
 * @author Frankie Fasola
 *
 */
public class BinaryTree {

	public static void main (String[] args) {
		 	Node root  = new Node(1);
		 	root.prev = new Node(2);
		 	root.next = new Node(3);
		 	root.prev.prev = new Node(7);
		 	root.prev.next = new Node(6);
		 	root.next.prev = new Node(5);
		 	root.next.next = new Node(4);
		 
		    BinaryTree bt = new BinaryTree();
		    int sum = 23;
		    System.out.print("Sprial traversal: ");
		    bt.printSpiral(root);
	}
	
	public void printPath(Node node, int sum) {
		List<Integer> list = new ArrayList<Integer>();
		printPath(node,sum,0,list);
	}
	
	private void printPath(Node node, int sum, int total, List<Integer> list) {
		if (node == null)
			return;
		total += node.data;
		list.add(node.data);
		if (total == sum) {
			System.out.println("Path found:");
			for (int i = 0; i < list.size(); i++)
				System.out.println(list.get(i));
		}
		if (node.prev != null)
			printPath(node.prev, sum, total, list);
		if (node.next != null)
			printPath(node.next, sum, total, list);
		list.remove(list.size()-1);
	}
	
	public void morrisTraversal (Node root) {
		Node current = null, pre = null;
		if (root == null)
			return;
		current = root;
		while (current != null) {
			if (current.prev == null) {
				System.out.print(current.data + " ");
				current = current.next;
			} else {
				pre = current.prev;
				while (pre.next != null && pre.next != current) 
					pre = pre.next;
					// Make current the right child of predecessor
					if (pre.next == null) {
						pre.next = current;
						current = current.prev;
					} else {
						pre.next = null;
						System.out.print(current.data + " ");
						current = current.next;
					}
			}
		}
	}
	
	public boolean isBalanced(Node root, Integer height) {
		if (root == null) {
			height = 0;
			return true;
		}
		int h1 = 0, h2 = 0;
		// Calculate the height of both sub trees
		boolean l = isBalanced(root.prev, h1);
		boolean r = isBalanced(root, h2);
		int lh = h1, rh = h2;
		// The height of this node is the max of subtrees
		height = (lh > rh ? lh : rh) + 1;
		if (Math.abs(lh-rh) > 1)
			return false;
		else return l&&r;
	}
	
	public boolean hasPathSum (Node root, int sum) {
		if (root == null)
			return sum == 0;
		else {
			boolean result = false;
			int remaining = sum - root.data;
			if (remaining == 0 && root.prev == null && root.next == null)
				return true;
			if (root.prev != null)
				result = result || hasPathSum(root.prev, remaining);
			if (root.next != null)
				result = result || hasPathSum(root.next, remaining);
			return result;
		}
	}
	
	public void printSpiral (Node node) {
		if (node == null)
			return;
		Stack<Node> s1 = new Stack<Node>();
		Stack<Node> s2 = new Stack<Node>();
		
		s1.push(node);
		while (!s1.isEmpty() || !s2.isEmpty()) {
			while (!s1.isEmpty()) {
				Node temp = s1.peek();
				s1.pop();
				System.out.print(temp.data + " ");
				if (temp.next != null)
					s2.push(temp.next);
				if (temp.prev != null)
					s2.push(temp.prev);
			}
			while (!s2.isEmpty()) {
				Node temp = s2.peek();
				s2.pop();
				System.out.print(temp.data + " ");
				if (temp.prev != null)
					s1.push(temp.prev);
				if (temp.next != null)
					s1.push(temp.next);
			}
		}
	}
}







