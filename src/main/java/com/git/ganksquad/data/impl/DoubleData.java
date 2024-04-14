package com.git.ganksquad.data.impl;

import com.git.ganksquad.data.ClassKeys;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.types.AggregateType;
import com.git.ganksquad.data.types.PrimitiveType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.runtime.CannotCastException;
import com.git.ganksquad.exceptions.runtime.CannotCompareException;
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
 * Represents integer data, which can hold a 32bit integer.
 */
public class DoubleData extends PrimitiveData {
	
	public double value = 0;
	
	public DoubleData(double value) {
		
		this.value = value;
	}

	public DoubleData(float value) {
		
		this.value = value;
	}

	@Override
	public boolean eq(Data other) throws CannotCompareException {

		if(!(other.getType() instanceof PrimitiveType)) {
			throw new CannotCompareException(this, other);
		}

		switch ((PrimitiveType)other.getType()) {
		case BOOLEAN:
			return this.value == ((PrimitiveData)other).getValue().intValue();
		case CHAR:
			return this.value == ((PrimitiveData)other).getValue().intValue();
		case BYTE:
			return this.value == ((PrimitiveData)other).getValue().byteValue();
		case INT:
			return this.value == ((PrimitiveData)other).getValue().intValue();
		case SHORT:
			return this.value == ((PrimitiveData)other).getValue().shortValue();
		case LONG:
			return this.value == ((PrimitiveData)other).getValue().longValue();
		case DOUBLE:
			return this.value == ((PrimitiveData)other).getValue().doubleValue();
		case FLOAT:
			return this.value == ((PrimitiveData)other).getValue().floatValue();
		default:
			throw new CannotCompareException(this, other);
		}
	}

	@Override
	public boolean lt(Data other) throws CannotCompareException {

		if(!(other.getType() instanceof PrimitiveType)) {
			throw new CannotCompareException(this, other);
		}

		switch ((PrimitiveType)other.getType()) {
		case BOOLEAN:
			return this.value < ((PrimitiveData)other).getValue().intValue();
		case CHAR:
			return this.value < ((PrimitiveData)other).getValue().intValue();
		case BYTE:
			return this.value < ((PrimitiveData)other).getValue().byteValue();
		case INT:
			return this.value < ((PrimitiveData)other).getValue().intValue();
		case SHORT:
			return this.value < ((PrimitiveData)other).getValue().shortValue();
		case LONG:
			return this.value < ((PrimitiveData)other).getValue().longValue();
		case DOUBLE:
			return this.value < ((PrimitiveData)other).getValue().doubleValue();
		case FLOAT:
			return this.value < ((PrimitiveData)other).getValue().floatValue();
		default:
			throw new CannotCompareException(this, other);
		}
	}

	@Override
	public boolean gt(Data other) throws CannotCompareException {
		
		if(!(other.getType() instanceof PrimitiveType)) {
			throw new CannotCompareException(this, other);
		}

		switch ((PrimitiveType)other.getType()) {
		case BOOLEAN:
			return this.value > ((PrimitiveData)other).getValue().intValue();
		case CHAR:
			return this.value > ((PrimitiveData)other).getValue().intValue();
		case BYTE:
			return this.value > ((PrimitiveData)other).getValue().byteValue();
		case INT:
			return this.value > ((PrimitiveData)other).getValue().intValue();
		case SHORT:
			return this.value > ((PrimitiveData)other).getValue().shortValue();
		case LONG:
			return this.value > ((PrimitiveData)other).getValue().longValue();
		case DOUBLE:
			return this.value > ((PrimitiveData)other).getValue().doubleValue();
		case FLOAT:
			return this.value > ((PrimitiveData)other).getValue().floatValue();
		default:
			throw new CannotCompareException(this, other);
		}
	}

	@Override
	public Data add(Data other) throws CannotAddException {

		if(!(other.getType() instanceof PrimitiveType)) {
			throw new CannotAddException(this, other);
		}

		switch ((PrimitiveType)other.getType()) {
		case BOOLEAN:
		case BYTE:
		case INT:
		case SHORT:
		case LONG:
		case DOUBLE:
		case FLOAT:
			return new DoubleData(this.value + ((PrimitiveData)other).getValue().doubleValue());
		case CHAR:
		default:
			throw new CannotAddException(this, other);
		}
	}

	@Override
	public Data sub(Data other) throws CannotSubtractException {

		if(!(other.getType() instanceof PrimitiveType)) {
			throw new CannotSubtractException(this, other);
		}

		switch ((PrimitiveType)other.getType()) {
		case BOOLEAN:
		case CHAR:
		case BYTE:
		case INT:
		case SHORT:
		case LONG:
		case DOUBLE:
		case FLOAT:
			return new DoubleData(this.value - ((PrimitiveData)other).getValue().doubleValue());
		default:
			throw new CannotSubtractException(this, other);
		}
	}

	@Override
	public Data mul(Data other) throws CannotMultiplyException {

		if(!(other.getType() instanceof PrimitiveType)) {
			throw new CannotMultiplyException(this, other);
		}

		switch ((PrimitiveType)other.getType()) {
		case BOOLEAN:
		case CHAR:
		case BYTE:
		case INT:
		case SHORT:
		case LONG:
		case DOUBLE:
		case FLOAT:
			return new DoubleData(this.value * ((PrimitiveData)other).getValue().doubleValue());
		default:
			throw new CannotMultiplyException(this, other);
		}
	}

	@Override
	public Data div(Data other) throws CannotDivideException {

		if(!(other.getType() instanceof PrimitiveType)) {
			throw new CannotDivideException(this, other);
		}

		switch ((PrimitiveType)other.getType()) {
		case BOOLEAN:
		case BYTE:
		case CHAR:
		case INT:
		case SHORT:
		case LONG:
		case FLOAT:
		case DOUBLE:
			return new DoubleData((double)this.value / ((PrimitiveData)other).getValue().doubleValue());
		default:
			throw new CannotDivideException(this, other);
		}
	}

	@Override
	public Data mod(Data other) throws CannotModulusException{

		if(!(other.getType() instanceof PrimitiveType)) {
			throw new CannotModulusException(this, other);
		}

		switch ((PrimitiveType)other.getType()) {
		case CHAR:
		case BOOLEAN:
		case BYTE:
		case INT:
		case SHORT:
		case LONG:
		case DOUBLE:
		case FLOAT:
			return new DoubleData((double)this.value % ((PrimitiveData)other).getValue().doubleValue());
		default:
			throw new CannotModulusException(this, other);
		}
	}


	@Override
	public Data castTo(ReimuType newType) throws ReimuRuntimeException {
		if(newType.isEqualType(AggregateType.STRING_TYPE)) {
			return new StringData(this.toString());
		}
		if(!(newType instanceof PrimitiveType)) {
			throw new CannotCastException(this.getType(), newType);
		}
		switch ((PrimitiveType)newType) {
		case BOOLEAN:
			return new BooleanData(this.value != 0);
		case BYTE:
			return new ByteData((byte)this.value);
		case CHAR:
			return new CharData((char)this.value);
		case DOUBLE:
			return new DoubleData((double)this.value);
		case FLOAT:
			return new DoubleData((float)this.value);
		case INT:
			return new IntegerData((int)this.value);
		case LONG:
			return new LongData((long)this.value);
		case SHORT:
			return new ShortData((short)this.value);
		default:
			throw new CannotCastException(this.getType(), newType);
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

	@Override
	public ReimuType getType() {
		return PrimitiveType.FLOAT;
	}

	@Override
	public Number getValue() {
		return this.value;
	}
}
