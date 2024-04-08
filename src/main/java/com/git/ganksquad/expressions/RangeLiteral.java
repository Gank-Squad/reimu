package com.git.ganksquad.expressions;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.RangeData;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public class RangeLiteral implements Expression {
	
	public RangeData range ;
	
	public RangeLiteral(RangeData d) {
		
		this.range = d;
	}
	
	public static RangeLiteral from(int start, int stop, int step) {
		
		return new RangeLiteral(new RangeData(start, stop, step));
	}

	public static RangeLiteral from(int start, int stop) {
		
		return new RangeLiteral(new RangeData(start, stop));
	}

	public static RangeLiteral fromString(String start, String  stop) {
		
		ParseChecks.RequiredNotNull(start, stop);

		return new RangeLiteral(
				new RangeData(
						Integer.parseInt(start),
						Integer.parseInt(stop)));
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {

		return this.range;
	}

}
