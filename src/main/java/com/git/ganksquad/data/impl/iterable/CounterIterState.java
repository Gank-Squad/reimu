package com.git.ganksquad.data.impl.iterable;

import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.IterableData.IterState;
import com.git.ganksquad.data.impl.NoneData;

public class CounterIterState extends IterState {

	public int counter;
	
	public CounterIterState(int counter) {
		this(counter, true, NoneData.instance);
	}

	public CounterIterState(int counter, Data d) {
		this(counter, true, d);
	}

	public CounterIterState(int counter, boolean hasNext, Data d) {
		
		this.hasData = hasNext;
		this.counter = counter;
		this.value = d;
	}
	
	public void markFinished() {
		
		this.hasData = false;
	}
}
