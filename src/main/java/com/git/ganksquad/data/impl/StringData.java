package com.git.ganksquad.data.impl;

import com.git.ganksquad.data.ArithmeticData;
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
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotANDException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotAddException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotDivideException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotModulusException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotMultiplyException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotORException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotSubtractException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotXORException;

/**
 * Represents a string value.
 */
public class StringData implements Data, ComparableData, BooleanEvaluable, IndexableData<CharData>, IterableData, ArithmeticData {
	
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

	@Override
	public Data add(Data other) throws CannotAddException {
		if (other instanceof StringData) {
			return new StringData(this.value + ((StringData)other).value);
		}
		if (other instanceof PrimitiveData) {
			return new StringData(this.value + ((PrimitiveData)other).toString());
		}
		throw new CannotAddException(this, other);
	}

	@Override
	public Data sub(Data other) throws CannotSubtractException {
		throw new CannotSubtractException(this, other);
	}

	@Override
	public Data mul(Data other) throws CannotMultiplyException {
		if (!(other instanceof PrimitiveData)) {
			throw new CannotMultiplyException(this, other);
		}
		if (other instanceof DoubleData || other instanceof FloatData) {
			throw new CannotMultiplyException(this, other);
		}
		if (((PrimitiveData)other).getValue().longValue() < 0) {
			throw new CannotMultiplyException(this, other);
		}
		if (((PrimitiveData)other).getValue().longValue()  == 0) {
			return new StringData("");
		}
		if (((PrimitiveData)other).getValue().longValue() > Integer.MAX_VALUE) {
			throw new CannotMultiplyException(this, other);
		}
		return new StringData(
					this.value.repeat(((PrimitiveData)other).getValue().intValue())
				);
	}

	@Override
	public Data div(Data other) throws CannotDivideException {
		 throw new CannotDivideException(this, other);
	}

	@Override
	public Data mod(Data other) throws CannotModulusException {
		throw new CannotModulusException(this, other);
	}

	@Override
	public Data xor(Data other) throws CannotXORException {
		throw new CannotXORException(this, other);
	}

	@Override
	public Data or(Data other) throws CannotORException {
		throw new CannotORException(this, other);
	}

	@Override
	public Data and(Data other) throws CannotANDException {
		throw new CannotANDException(this, other);
	}
}
