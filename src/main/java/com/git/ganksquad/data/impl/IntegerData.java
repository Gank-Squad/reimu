package com.git.ganksquad.data.impl;

import com.git.ganksquad.data.ArithmeticData;
import com.git.ganksquad.data.BooleanEvaluable;
import com.git.ganksquad.data.ClassKeys;
import com.git.ganksquad.data.ComparableData;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.IndexKeyData;
import com.git.ganksquad.exceptions.CannotCompareException;
import com.git.ganksquad.exceptions.Arithmetic.CannotANDException;
import com.git.ganksquad.exceptions.Arithmetic.CannotAddException;
import com.git.ganksquad.exceptions.Arithmetic.CannotDivideException;
import com.git.ganksquad.exceptions.Arithmetic.CannotModulusException;
import com.git.ganksquad.exceptions.Arithmetic.CannotMultiplyException;
import com.git.ganksquad.exceptions.Arithmetic.CannotORException;
import com.git.ganksquad.exceptions.Arithmetic.CannotSubtractException;
import com.git.ganksquad.exceptions.Arithmetic.CannotXORException;

/**
 * Represents integer data, which can hold a 32bit integer.
 */
public class IntegerData implements Data, ArithmeticData, ComparableData, BooleanEvaluable, IndexKeyData {
	
	public int value = 0;
	
	public IntegerData(int value) {
		
		this.value = value;
	}

	@Override
	public boolean eq(Data other) throws CannotCompareException {
		
		switch (other.getClassKey()) {

		case ClassKeys.INTEGER_DATA:
			return this.value == ((IntegerData)other).value;

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
			return this.value < ((IntegerData)other).value;

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
			return this.value > ((IntegerData)other).value;

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
			return new IntegerData(this.value + ((IntegerData)other).value);

		case ClassKeys.BOOLEAN_DATA:
			return new IntegerData(this.value + ((BooleanData)other).valueAsInt());

		default:
			throw new CannotAddException(this, other);
		}
	}

	@Override
	public Data sub(Data other) throws CannotSubtractException {

		switch (other.getClassKey()) {

		case ClassKeys.INTEGER_DATA:
			return new IntegerData(this.value - ((IntegerData)other).value);

		case ClassKeys.BOOLEAN_DATA:
			return new IntegerData(this.value - ((BooleanData)other).valueAsInt());

		default:
			throw new CannotSubtractException(this, other);
		}
	}

	@Override
	public Data mul(Data other) throws CannotMultiplyException {

		switch (other.getClassKey()) {

		case ClassKeys.INTEGER_DATA:
			return new IntegerData(this.value * ((IntegerData)other).value);

		case ClassKeys.BOOLEAN_DATA:
			return new IntegerData(this.value * ((BooleanData)other).valueAsInt());

		default:
			throw new CannotMultiplyException(this, other);
		}
	}

	@Override
	public Data div(Data other) throws CannotDivideException {

		switch (other.getClassKey()) {

		case ClassKeys.INTEGER_DATA:
			return new IntegerData(this.value / ((IntegerData)other).value);

		case ClassKeys.BOOLEAN_DATA:
			return new IntegerData(this.value / ((BooleanData)other).valueAsInt());

		default:
			throw new CannotDivideException(this, other);
		}
	}

	@Override
	public Data mod(Data other) throws CannotModulusException{

		switch (other.getClassKey()) {

		case ClassKeys.INTEGER_DATA:
			return new IntegerData(this.value % ((IntegerData)other).value);

		case ClassKeys.BOOLEAN_DATA:
			return new IntegerData(this.value % ((BooleanData)other).valueAsInt());

		default:
			throw new CannotModulusException(this, other);
		}
	}

	@Override
	public Data xor(Data other) throws CannotXORException {

		switch (other.getClassKey()) {

		case ClassKeys.INTEGER_DATA:
			return new IntegerData(this.value ^ ((IntegerData)other).value);

		case ClassKeys.BOOLEAN_DATA:
			return new IntegerData(this.value ^ ((BooleanData)other).valueAsInt());

		default:
			throw new CannotXORException(this, other);
		}
	}

	@Override
	public Data or(Data other) throws CannotORException {

		switch (other.getClassKey()) {

		case ClassKeys.INTEGER_DATA:
			return new IntegerData(this.value | ((IntegerData)other).value);

		case ClassKeys.BOOLEAN_DATA:
			return new IntegerData(this.value | ((BooleanData)other).valueAsInt());

		default:
			throw new CannotORException(this, other);
		}
	}

	@Override
	public Data and(Data other) throws CannotANDException {

		switch (other.getClassKey()) {

		case ClassKeys.INTEGER_DATA:
			return new IntegerData(this.value & ((IntegerData)other).value);

		case ClassKeys.BOOLEAN_DATA:
			return new IntegerData(this.value & ((BooleanData)other).valueAsInt());

		default:
			throw new CannotANDException(this, other);
		}
	}

	@Override
	public boolean evalAsBool() {
		return this.value != 0;
	}

	@Override
	public int getClassKey() {
		
		return ClassKeys.INTEGER_DATA;
	}
	
	@Override
	public String toString() {
		return Integer.toString(this.value);
	}

}
