package com.git.ganksquad.data.impl;

import com.git.ganksquad.data.ClassKeys;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.IterableData;
import com.git.ganksquad.data.impl.iterable.CounterIterState;
import com.git.ganksquad.exceptions.runtime.InvalidIterStateException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;
import com.git.ganksquad.expressions.IntegerLiteral;

public class RangeData implements Data, IterableData {
	
	public int start;
	public int stop;
	public int step;
	
	public RangeData(int start, int stop) {
		
		this.start = start;
		this.stop = stop;
		this.step = 1;
	}

	public RangeData(int start, int stop, int step) {
		
		this(start, stop);

		this.step = step;
	}
	
	@Override
	public IterState newState() {
		
		return new CounterIterState(this.start);
	}

	@Override
	public void update(IterState state) throws ReimuRuntimeException {
		
		state.mustBe(CounterIterState.class);
		
		CounterIterState s = (CounterIterState)state;
		
		if(step < 0) {
			
			if(s.counter <= stop) {
				
				s.markFinished();

				return;
			}
		}
		else {
			
			if(s.counter >= stop) {

				s.markFinished();

				return;
			}
		}
		
		s.value = new IntegerData(s.counter);
		s.counter += this.step;
	}

	@Override
	public int getClassKey() {

		return ClassKeys.RANGE_DATA;
	}

	@Override
	public String toString() {
		return this.start + ".." + this.stop + ":" + this.step;
	}
}
