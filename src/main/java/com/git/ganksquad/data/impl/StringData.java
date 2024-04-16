package com.git.ganksquad.data.impl;

import com.git.ganksquad.data.BooleanEvaluable;
import com.git.ganksquad.data.ClassKeys;
import com.git.ganksquad.data.ComparableData;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.IndexKeyData;
import com.git.ganksquad.data.IndexableData;
import com.git.ganksquad.data.IterableData;
import com.git.ganksquad.data.impl.iterable.CounterIterState;
import com.git.ganksquad.data.types.AggregateType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.runtime.CannotCompareException;
import com.git.ganksquad.exceptions.runtime.CannotIndexException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

/**
 * Represents a string value.
 */
public class StringData implements Data, ComparableData, BooleanEvaluable, IndexableData<CharData>, IterableData {
	
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
		return Data.commonCast(this, newType);
	}


	@Override
	public CharData get(IndexKeyData index) throws CannotIndexException {
		if(index instanceof PrimitiveData) {
			
			return new CharData(this.value.charAt(((PrimitiveData)index).getValue().intValue()));
		}

		throw CannotIndexException.typeNotIndexableBy(getClass(), index.getClass());
	}

	@Override
	public void set(IndexKeyData index, CharData value) throws CannotIndexException {
		throw new CannotIndexException("Strings cannot have their values set");
	}

	@Override
	public void update(IterState state) throws ReimuRuntimeException {
		
		state.mustBe(CounterIterState.class);
		
		CounterIterState s = (CounterIterState)state;
		
		if(s.counter < 0 || s.counter >= this.value.length()) {
			
			s.markFinished();

			return;
		}
		
		s.value = new CharData(this.value.charAt(s.counter));
		s.counter += 1;
		
	}

	@Override
	public IterState newState() {
		return new CounterIterState(0);
	}
}
