package lwjgui.collections;

import java.util.ArrayList;

public class StateStack<E> {
	private final int maxStates;
	private ArrayList<E> states = new ArrayList<E>();
	private int current;
	
	public StateStack(int maxStates) {
		this.maxStates = maxStates;
	}
	
	public StateStack() {
		this(512);
	}
	
	/**
	 * Push a editorState onto the stack. If the current editorState is not fully forwarded, all future states are lost.
	 * @param state
	 */
	public void Push(E state) {
		// Remove any states that exist after the current editorState. (Delete future)
		while ( states.size() > current ) {
			states.remove(states.size()-1);
		}
		
		// Delete history if we've exceeded maxStates
		while ( states.size() >= maxStates ) {
			states.remove(0);
		}
		
		// Add to states
		states.add(state);
		
		// Set current editorState
		current = states.size();
	}
	
	/**
	 * Returns if the current editorState is the most recent editorState.
	 * @return
	 */
	public boolean isCurrent() {
		return current == states.size();
	}

	/**
	 * Rewind one editorState on the stack.
	 * @return
	 */
	public E Rewind() {
		current--;
		if ( current < 0 ) {
			current = 0;
		}
		if ( states.size() == 0 )
			return null;
		
		return states.get(current);
	}
	
	/**
	 * Move forward one editorState on the stack
	 * @return
	 */
	public E Forward() {
		current++;
		if ( current >= states.size() ) {
			current = states.size()-1;
			//return states.get(current);
		}
		return states.get(current);
	}
}