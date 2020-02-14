/**
 * @author Frankie Fasola
 *
 */
public class ImmutableStack<T> implements Stack<T> {

	private final T head;
	private final Stack<T> tail;
	
	private ImmutableStack(T head, Stack<T> tail) {
		this.head = head;
		this.tail = tail;
	}
	
	public final Stack<T> push(T t) {
		return new ImmutableStack<T>(t, this);
	}

	public final Stack<T> pop() {
		return tail;
	}
	
	public final T head() {
		return head;
	}
	
	public final boolean isEmpty() {
		return false;
	}

	public final static Stack GetEmptyStack() {
		return EmptyStack.getEmptyStack();
	}
	
	private static final class EmptyStack<T> implements Stack<T> {
		
		private final static EmptyStack emptyStack = new EmptyStack();
		
		public final static EmptyStack getEmptyStack() {
			return emptyStack;
		}
		
		public final Stack push(T t)
		{
			return new ImmutableStack<T>(t, this);
		}
		
		public final Stack<T> pop() {
			return emptyStack;
		}
		
		/**
		 * Always returns null because the Stack is empty.
		 * @return null
		 */
		public final T head() {
			return null;
		}
		
		public final boolean isEmpty() {
			return true;
		}
	}
}
