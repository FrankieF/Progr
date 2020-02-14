/**
 * @author Frankie Fasola
 * This ImmutableQueue implementation is okay but I would have preferred to use Optionals if, but was unsure if I was able to change your API.
 * I also was unsure if I could change the API to throw exceptions.
 */
public class ImmutableQueue<T> implements Queue<T> {

	private final Stack<T> backwards;
	private final Stack<T> forwards;

	private ImmutableQueue(Stack<T> forwards, Stack<T> backwards) {
		this.forwards = forwards;
		this.backwards = backwards;
	}
     
    public final static Queue getEmptyQueue() {
    	return EmptyQueue.getEmptyQueue();
    }
    
    public final Queue<T> enQueue(T t) {
    	return new ImmutableQueue<T> (forwards, backwards.push(t));
    }
    
    public final Queue<T> deQueue() {
    	Stack<T> forward = forwards.pop();
    	if (!forward.isEmpty()) {
    		return new ImmutableQueue<T>(forward, this.backwards);
    	}
    	else if (this.backwards.isEmpty()) {
    		return ImmutableQueue.getEmptyQueue();
    	}
    	else {
    		return new ImmutableQueue<T>(ImmutableQueue.reverseStack(backwards), ImmutableStack.GetEmptyStack());
    	}
    }

    public final T head() {
    	return this.forwards.head();
    }
    
    public final boolean isEmpty() {
    	return false;
    }
    
    public final static Stack reverseStack(Stack stack) {
    	Stack _stack = ImmutableStack.GetEmptyStack();
    	while (!stack.isEmpty()) {
    		_stack = _stack.push(stack.head());
    		stack = stack.pop();
    	}
    	return _stack;
    }

	private static final class EmptyQueue<T> implements Queue<T> {
		private final static EmptyQueue emptyQueue = new EmptyQueue();
		
		private final static EmptyQueue getEmptyQueue() {
			return emptyQueue;
		}
		
		public final Queue<T> enQueue(T t) {
			return new ImmutableQueue<T> (ImmutableStack.GetEmptyStack().push(t), ImmutableStack.GetEmptyStack());
		}
		
		public final Queue<T> deQueue() {
			return emptyQueue;
		}
		
		/**
		 * This is an empty Queue so the head will always be null.
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
