package com.git.ganksquad.data.impl;

import com.git.ganksquad.data.ClassKeys;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.IterableData;
import com.git.ganksquad.data.impl.iterable.CounterIterState;
import com.git.ganksquad.data.types.AggregateType;
import com.git.ganksquad.data.types.IterableType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.runtime.CannotCastException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class RangeData implements Data, IterableData {
	
	private IterableType type;
	public int start;
	public int stop;
	public int step;
	
	public RangeData(IterableType type, int start, int stop) {
		
		this.type = type;
		this.start = start;
		this.stop = stop;
		this.step = 1;
	}

	public RangeData(IterableType type, int start, int stop, int step) {
		
		this(type, start, stop);

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
		
		s.value = new LongData(s.counter);
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

	@Override
	public ReimuType getType() {
		return this.type;
	}

	@Override
	public Data castTo(ReimuType newType) throws ReimuRuntimeException {
		if(newType.isEqualType(AggregateType.STRING_TYPE)) {
			return new StringData(this.toString());
		}
		if(this.getType().isAssignableFrom(newType)) {
			return this;
		}
		throw new CannotCastException(this.getType(), newType);
	}
}
