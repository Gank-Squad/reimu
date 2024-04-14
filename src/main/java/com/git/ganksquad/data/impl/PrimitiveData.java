package com.git.ganksquad.data.impl;

import com.git.ganksquad.data.ArithmeticData;
import com.git.ganksquad.data.BooleanEvaluable;
import com.git.ganksquad.data.ComparableData;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.IndexKeyData;

public abstract class PrimitiveData implements Data, ArithmeticData, ComparableData, BooleanEvaluable, IndexKeyData {

	public abstract Number getValue();
	
	
}
