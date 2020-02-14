/**
 * 
 */

/**
 * @author Frankie Fasola
 *
 */
public class AVLTree {
	private Node leftRotate (Node root) {
		Node newRoot = root.next;
		root.next = root.next.prev;
		newRoot.prev = root;
		root.height = setHeight(root);
		root.size = setSize(root);
		newRoot.height = setHeight(newRoot);
		newRoot.size = setSize(newRoot);
		return newRoot;
	}
	
	private Node rightRotate (Node root) {
		Node newRoot = root.prev;
		root.prev= root.prev.next;
		newRoot.next = root;
		root.height = setHeight(root);
		root.size = setSize(root);
		newRoot.height = setHeight(newRoot);
		newRoot.size = setSize(newRoot);
		return newRoot;
	}
	
	private int setHeight (Node root) {
		if (root == null)
			return 0;
		return 1 + Math.max((root.prev != null ? root.prev.height : 0), (root.next != null ? root.next.height : 0));
	}
	
	private int height (Node root) {
		if (root == null)
			return 0;
		else
			return root.height;
	}
	
	private int setSize (Node root) {
		if (root == null)
			return 0;
		return 1 + Math.max((root.prev != null ? root.prev.size : 0), (root.next != null ? root.next.size : 0));
	}
	
	public Node insert (Node root, int data) {
		if (root == null)
			return new Node(data);
		if (root.data <= data) 
			root.next = insert(root.next, data);
		else
			root.prev = insert(root.prev, data);
		int balance = balance(root.prev, root.next);
		if (balance > 1) {
			if (height(root.prev.prev) >= height(root.prev.next))
				root = rightRotate(root);
			else {
				root.prev = leftRotate(root);
				root = rightRotate(root);
			} 
		} else if (balance < -1) {
			if (height(root.next.next) >= height(root.next.prev))
				root = leftRotate(root);
			else {
				root = rightRotate(root);
				root = leftRotate(root);
			}
		} else {
			root.height = setHeight(root);
			root.size = setSize(root);
		}
		return root;
	}
	
	private int balance (Node left, Node right) {
		return height(left) - height(right);
	}
}











