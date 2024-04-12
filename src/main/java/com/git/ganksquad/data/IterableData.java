package com.git.ganksquad.data;

import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.exceptions.runtime.InvalidIterStateException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

/**
 * Represents a generic data which can be iterated over,
 * 
 * IE. using a for loop
 */
public interface IterableData {
	
	/**
	 * The state for an iterable data.
	 * 
	 * The state is created by an expression and handles
	 * getting a value and keeping a counter.
	 */
	public static abstract class IterState {
		
		public Data value;
		
		public boolean hasData;
		
		public abstract void markFinished();
		
		public void mustBe(Class<?> c) throws InvalidIterStateException {
			
			if(!c.isInstance(this)) {
				
				throw InvalidIterStateException.expectedWas(c, this.getClass());
			}
		}
	}
	
	/**
	 * Return the next item
	 * @return the next {@link Data} or {@link NoneData} if the end has been reached
	 */
	public void update(IterState state) throws ReimuRuntimeException;

	/**
	 * Creates a new iter state. This is for beginning iteration.
	 * @return
	 */
	public IterState newState();
}
