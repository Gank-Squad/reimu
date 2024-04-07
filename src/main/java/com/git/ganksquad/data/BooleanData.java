package com.git.ganksquad.data;

import com.git.ganksquad.exceptions.CannotAddException;
import com.git.ganksquad.exceptions.CannotCompareException;
import com.git.ganksquad.exceptions.CannotDivideException;
import com.git.ganksquad.exceptions.CannotMultiplyException;
import com.git.ganksquad.exceptions.CannotSubtractException;

public class BooleanData implements Data, ArithmeticData, ComparableData {
	
	public boolean value = false;
	
	public BooleanData() {}

	public BooleanData(boolean value) {

		this.value = value;
	}
	
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
	public int getClassKey() {
		
		return ClassKeys.BOOLEAN_DATA;
	}
}
