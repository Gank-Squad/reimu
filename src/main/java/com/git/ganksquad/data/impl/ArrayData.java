package com.git.ganksquad.data.impl;

import com.git.ganksquad.data.ClassKeys;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.IterableData;
import com.git.ganksquad.data.impl.iterable.CounterIterState;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public class ArrayData<T extends Data> implements Data, IterableData {
	
	public T[] arr;

	public void set(int index, T value) {
		
		this.arr[index] = value;
	}

	public T get(int index) {
		
		return this.arr[index];
	}

	public int size() {
		
		return this.arr.length;
	}

	@Override
	public int getClassKey() {

		return ClassKeys.ARRAY_DATA;
	}

	@Override
	public void update(IterState state) throws ReimuRuntimeException {
		
		state.mustBe(CounterIterState.class);
		
		CounterIterState s = (CounterIterState)state;
		
		if(s.counter < 0 || s.counter >= this.size()) {
			
			s.markFinished();

			return;
		}
		
		s.value = this.get(s.counter);
		s.counter += 1;
		
	}

	@Override
	public IterState newState() {
		return new CounterIterState(0);
	}
}
