/**
 * 
 */

/**
 * @author Frankie Fasola
 *
 */
public class Node {
	int data, lis, height, size;
	Node next, prev, random;
	
	public Node(int data) {
		this.data = data;
		this.lis = -1;
		this.height = this.size = 1;
		this.next = this.random = prev = null;
	}
	
	public Node () {
		
	}
}
