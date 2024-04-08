package com.git.ganksquad.data.impl;

import com.git.ganksquad.data.ClassKeys;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.IterableData;
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
	public Data next() {
		
		if(step < 0) {
			
			if(start <= stop) {
				return NoneData.instance;
			}
		}
		else {
			
			if(start >= stop) {
				return NoneData.instance;
			}
		}
		
		IntegerData r = new IntegerData( start );
		
		this.start += this.step;

		return r;
	}

	@Override
	public int getClassKey() {

		return ClassKeys.RANGE_DATA;
	}

}
