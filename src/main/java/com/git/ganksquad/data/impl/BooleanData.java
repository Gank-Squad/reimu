package com.git.ganksquad.data.impl;

import com.git.ganksquad.data.ArithmeticData;
import com.git.ganksquad.data.BooleanEvaluable;
import com.git.ganksquad.data.ClassKeys;
import com.git.ganksquad.data.ComparableData;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.exceptions.runtime.CannotCompareException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotANDException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotAddException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotDivideException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotModulusException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotMultiplyException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotORException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotSubtractException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotXORException;

/**
 * Represents boolean data, which can hold true or false.
 * 
 * Boolean's can be treated like integers, in most cases.
 */
public class BooleanData implements Data, ArithmeticData, ComparableData, BooleanEvaluable {
	
	/**
	 * The value of this data
	 */
	public boolean value = false;
	
	public BooleanData() {}

	public BooleanData(boolean value) {

		this.value = value;
	}
	
	/**
	 * Returns this value as an integer
	 * @return
	 */
	public int valueAsInt() {
		
		if(this.value) 
			return 1;

		return 0;
	}

	@Override
	public boolean eq(Data other) throws CannotCompareException {
		
		switch (other.getClassKey()) {

		case ClassKeys.BOOLEAN_DATA:
			return this.value == ((BooleanData)other).value;

		case ClassKeys.INTEGER_DATA:
			return this.valueAsInt() == ((IntegerData)other).value;

		default:
			throw new CannotCompareException(this, other);
		}
	}

	@Override
	public boolean lt(Data other) throws CannotCompareException {

		switch (other.getClassKey()) {

		case ClassKeys.BOOLEAN_DATA:
			return this.valueAsInt() < ((BooleanData)other).valueAsInt();

		case ClassKeys.INTEGER_DATA:
			return this.valueAsInt() < ((IntegerData)other).value;

		default:
			throw new CannotCompareException(this, other);
		}
	}

	@Override
	public boolean gt(Data other) throws CannotCompareException {

		switch (other.getClassKey()) {

		case ClassKeys.BOOLEAN_DATA:
			return this.valueAsInt() > ((BooleanData)other).valueAsInt();

		case ClassKeys.INTEGER_DATA:
			return this.valueAsInt() > ((IntegerData)other).value;

		default:
			throw new CannotCompareException(this, other);
		}
	}

	@Override
	public Data add(Data other) throws CannotAddException {

		switch (other.getClassKey()) {

		case ClassKeys.BOOLEAN_DATA:
			return new IntegerData(this.valueAsInt() + ((BooleanData)other).valueAsInt());

		case ClassKeys.INTEGER_DATA:
			return new IntegerData(this.valueAsInt() + ((IntegerData)other).value);

		default:
			throw new CannotAddException(this, other);
		}
	}

	@Override
	public Data sub(Data other) throws CannotSubtractException {

		switch (other.getClassKey()) {

		case ClassKeys.BOOLEAN_DATA:
			return new IntegerData(this.valueAsInt() - ((BooleanData)other).valueAsInt());

		case ClassKeys.INTEGER_DATA:
			return new IntegerData(this.valueAsInt() - ((IntegerData)other).value);

		default:
			throw new CannotSubtractException(this, other);
		}
	}

	@Override
	public Data mul(Data other) throws CannotMultiplyException {

		switch (other.getClassKey()) {

		case ClassKeys.BOOLEAN_DATA:
			return new IntegerData(this.valueAsInt() * ((BooleanData)other).valueAsInt());

		case ClassKeys.INTEGER_DATA:
			return new IntegerData(this.valueAsInt() * ((IntegerData)other).value);

		default:
			throw new CannotMultiplyException(this, other);
		}
	}

	@Override
	public Data div(Data other) throws CannotDivideException {

		switch (other.getClassKey()) {

		case ClassKeys.BOOLEAN_DATA:
			return new IntegerData(this.valueAsInt() / ((BooleanData)other).valueAsInt());

		case ClassKeys.INTEGER_DATA:
			return new IntegerData(this.valueAsInt() / ((IntegerData)other).value);

		default:
			throw new CannotDivideException(this, other);
		}
	}


	@Override
	public Data mod(Data other) throws CannotModulusException{

		switch (other.getClassKey()) {

		case ClassKeys.BOOLEAN_DATA:
			return new IntegerData(this.valueAsInt() % ((BooleanData)other).valueAsInt());

		case ClassKeys.INTEGER_DATA:
			return new IntegerData(this.valueAsInt() % ((IntegerData)other).value);

		default:
			throw new CannotModulusException(this, other);
		}
	}

	@Override
	public Data xor(Data other) throws CannotXORException {

		switch (other.getClassKey()) {

		case ClassKeys.BOOLEAN_DATA:
			return new IntegerData(this.valueAsInt() ^ ((BooleanData)other).valueAsInt());

		case ClassKeys.INTEGER_DATA:
			return new IntegerData(this.valueAsInt() ^ ((IntegerData)other).value);

		default:
			throw new CannotXORException(this, other);
		}
	}

	@Override
	public Data or(Data other) throws CannotORException {

		switch (other.getClassKey()) {

		case ClassKeys.BOOLEAN_DATA:
			return new IntegerData(this.valueAsInt() | ((BooleanData)other).valueAsInt());

		case ClassKeys.INTEGER_DATA:
			return new IntegerData(this.valueAsInt() | ((IntegerData)other).value);

		default:
			throw new CannotORException(this, other);
		}
	}

	@Override
	public Data and(Data other) throws CannotANDException {

		switch (other.getClassKey()) {

		case ClassKeys.BOOLEAN_DATA:
			return new IntegerData(this.valueAsInt() & ((BooleanData)other).valueAsInt());

		case ClassKeys.INTEGER_DATA:
			return new IntegerData(this.valueAsInt() & ((IntegerData)other).value);

		default:
			throw new CannotANDException(this, other);
		}
	}

	@Override
	public boolean evalAsBool() {
		return this.value;
	}

	@Override
	public int getClassKey() {
		
		return ClassKeys.BOOLEAN_DATA;
	}
	
	@Override
	public String toString() {
		return Boolean.toString(this.value);
	}
}
