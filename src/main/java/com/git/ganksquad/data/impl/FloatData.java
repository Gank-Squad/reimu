package com.git.ganksquad.data.impl;

import com.git.ganksquad.data.ArithmeticData;
import com.git.ganksquad.data.BooleanEvaluable;
import com.git.ganksquad.data.ClassKeys;
import com.git.ganksquad.data.ComparableData;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.IndexKeyData;
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
 * Represents integer data, which can hold a 32bit integer.
 */
public class FloatData implements Data, ArithmeticData, ComparableData, BooleanEvaluable, IndexKeyData {
	
	public double value = 0;
	
	public FloatData(double value) {
		
		this.value = value;
	}

	@Override
	public boolean eq(Data other) throws CannotCompareException {
		
		switch (other.getClassKey()) {

		case ClassKeys.INTEGER_DATA:
			return this.value == ((FloatData)other).value;

		case ClassKeys.BOOLEAN_DATA:
			return this.value == ((BooleanData)other).valueAsInt();

		default:
			throw new CannotCompareException(this, other);
		}
	}

	@Override
	public boolean lt(Data other) throws CannotCompareException {
		
		switch (other.getClassKey()) {

		case ClassKeys.INTEGER_DATA:
			return this.value < ((FloatData)other).value;

		case ClassKeys.BOOLEAN_DATA:
			return this.value < ((BooleanData)other).valueAsInt();

		default:
			throw new CannotCompareException(this, other);
		}
	}

	@Override
	public boolean gt(Data other) throws CannotCompareException {
		
		switch (other.getClassKey()) {

		case ClassKeys.INTEGER_DATA:
			return this.value > ((FloatData)other).value;

		case ClassKeys.BOOLEAN_DATA:
			return this.value > ((BooleanData)other).valueAsInt();

		default:
			throw new CannotCompareException(this, other);
		}
	}

	@Override
	public Data add(Data other) throws CannotAddException {

		switch (other.getClassKey()) {

		case ClassKeys.INTEGER_DATA:
			return new FloatData(this.value + ((FloatData)other).value);

		case ClassKeys.BOOLEAN_DATA:
			return new FloatData(this.value + ((BooleanData)other).valueAsInt());

		default:
			throw new CannotAddException(this, other);
		}
	}

	@Override
	public Data sub(Data other) throws CannotSubtractException {

		switch (other.getClassKey()) {

		case ClassKeys.INTEGER_DATA:
			return new FloatData(this.value - ((FloatData)other).value);

		case ClassKeys.BOOLEAN_DATA:
			return new FloatData(this.value - ((BooleanData)other).valueAsInt());

		default:
			throw new CannotSubtractException(this, other);
		}
	}

	@Override
	public Data mul(Data other) throws CannotMultiplyException {

		switch (other.getClassKey()) {

		case ClassKeys.INTEGER_DATA:
			return new FloatData(this.value * ((FloatData)other).value);

		case ClassKeys.BOOLEAN_DATA:
			return new FloatData(this.value * ((BooleanData)other).valueAsInt());

		default:
			throw new CannotMultiplyException(this, other);
		}
	}

	@Override
	public Data div(Data other) throws CannotDivideException {

		switch (other.getClassKey()) {

		case ClassKeys.INTEGER_DATA:
			return new FloatData(this.value / ((FloatData)other).value);

		case ClassKeys.BOOLEAN_DATA:
			return new FloatData(this.value / ((BooleanData)other).valueAsInt());

		default:
			throw new CannotDivideException(this, other);
		}
	}

	@Override
	public Data mod(Data other) throws CannotModulusException{

		switch (other.getClassKey()) {

		case ClassKeys.INTEGER_DATA:
			return new FloatData(this.value % ((FloatData)other).value);

		case ClassKeys.BOOLEAN_DATA:
			return new FloatData(this.value % ((BooleanData)other).valueAsInt());

		default:
			throw new CannotModulusException(this, other);
		}
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

	@Override
	public boolean evalAsBool() {
		return this.value != 0;
	}

	@Override
	public int getClassKey() {
		
		return ClassKeys.FLOAT_DATA;
	}
	
	@Override
	public String toString() {
		return Double.toString(this.value);
	}

}
