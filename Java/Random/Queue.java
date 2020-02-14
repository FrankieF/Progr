/**
 * @author Frankie Fasola
 */
public interface Queue<T> {
	public Queue<T> enQueue(T t);
	// Removes the element at the beginning of the immutable queue, and returns a new queue.
	public Queue<T> deQueue();
	public T head();
	public boolean isEmpty();
}