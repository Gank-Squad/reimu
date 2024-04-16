package com.git.ganksquad.data.impl;

import com.git.ganksquad.data.ArithmeticData;
import com.git.ganksquad.data.BooleanEvaluable;
import com.git.ganksquad.data.ComparableData;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.IndexKeyData;
import com.git.ganksquad.data.types.AggregateType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.data.types.SpecialType;
import com.git.ganksquad.exceptions.runtime.CannotCastException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public abstract class PrimitiveData implements Data, ArithmeticData, ComparableData, BooleanEvaluable, IndexKeyData {

	public abstract Number getValue();
	
	@Override
	public Data castTo(ReimuType newType) throws ReimuRuntimeException {
		return Data.commonCast(this, newType);
	}
	
}
