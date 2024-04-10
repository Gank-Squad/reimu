package com.git.ganksquad.data.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.git.ganksquad.data.ClassKeys;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.IterableData;
import com.git.ganksquad.data.impl.iterable.CounterIterState;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public class ArrayData<T extends Data> implements Data, IterableData {
	
	protected ArrayList<T> arr;
	
	public ArrayData(T[] arr){
		
		this.arr = new ArrayList<T>(Arrays.asList(arr));
	}

	public ArrayData(List<T> lis){
		
		this.arr = new ArrayList<T>(lis);
	}

	public void set(int index, T value) {
		
		this.arr.set(index, value);
	}

	public T get(int index) {
		
		return this.arr.get(index);
	}

	public int size() {
		
		return this.arr.size();
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
	
	@Override
	public String toString() {
		return this.arr.toString();
	}
}
