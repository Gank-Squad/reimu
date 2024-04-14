package com.git.ganksquad.data.impl;

import com.git.ganksquad.data.BooleanEvaluable;
import com.git.ganksquad.data.ClassKeys;
import com.git.ganksquad.data.ComparableData;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.types.AggregateType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.runtime.CannotCastException;
import com.git.ganksquad.exceptions.runtime.CannotCompareException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

/**
 * Represents a string value.
 */
public class StringData implements Data, ComparableData, BooleanEvaluable {
	
	public String value = "";
	
	public StringData() {}

	public StringData(String value) {

		this.value = value;
	}
	
	@Override
	public boolean eq(Data other) throws CannotCompareException {
		
		switch (other.getClassKey()) {

		case ClassKeys.STRING_DATA:
			return this.value.equals(((StringData)other).value);

		default:
			throw new CannotCompareException(this, other);
		}
	}

	@Override
	public boolean lt(Data other) throws CannotCompareException {
		
		switch (other.getClassKey()) {

		case ClassKeys.STRING_DATA:
			return this.value.compareTo(((StringData)other).value) < 0;

		default:
			throw new CannotCompareException(this, other);
		}
	}

	@Override
	public boolean gt(Data other) throws CannotCompareException {
		
		switch (other.getClassKey()) {

		case ClassKeys.STRING_DATA:
			return this.value.compareTo(((StringData)other).value) > 0;

		default:
			throw new CannotCompareException(this, other);
		}
	}

	@Override
	public boolean evalAsBool() {
		return !this.value.isEmpty();
	}

	@Override
	public int getClassKey() {

		return ClassKeys.STRING_DATA;
	}

	@Override
	public String toString() {
		return this.value;
	}

	@Override
	public ReimuType getType() {
		return AggregateType.STRING_TYPE;
	}

	@Override
	public Data castTo(ReimuType newType) throws ReimuRuntimeException {
		if(this.getType().isAssignableFrom(newType)) {
			return this;
		}
		throw new CannotCastException(this.getType(), newType);
	}

}
