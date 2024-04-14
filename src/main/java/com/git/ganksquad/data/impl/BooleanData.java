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
 * Represents boolean data, which can hold true or false.
 * 
 * Boolean's can be treated like integers, in most cases.
 */
public class BooleanData extends PrimitiveData {
	
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

		if(!(other.getType() instanceof PrimitiveType)) {
			throw new CannotCompareException(this, other);
		}

		switch ((PrimitiveType)other.getType()) {
		case BOOLEAN:
			return this.valueAsInt() == ((PrimitiveData)other).getValue().intValue();
		case CHAR:
			return this.valueAsInt()  == ((PrimitiveData)other).getValue().intValue();
		case BYTE:
			return this.valueAsInt()  == ((PrimitiveData)other).getValue().byteValue();
		case INT:
			return this.valueAsInt() == ((PrimitiveData)other).getValue().intValue();
		case SHORT:
			return this.valueAsInt() == ((PrimitiveData)other).getValue().shortValue();
		case LONG:
			return this.valueAsInt() == ((PrimitiveData)other).getValue().longValue();
		case DOUBLE:
			return this.valueAsInt() == ((PrimitiveData)other).getValue().doubleValue();
		case FLOAT:
			return this.valueAsInt() == ((PrimitiveData)other).getValue().floatValue();
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
			return this.valueAsInt() < ((PrimitiveData)other).getValue().intValue();
		case CHAR:
			return this.valueAsInt() < ((CharData)other).value;
		case BYTE:
			return this.valueAsInt() < ((PrimitiveData)other).getValue().byteValue();
		case INT:
			return this.valueAsInt() < ((PrimitiveData)other).getValue().intValue();
		case SHORT:
			return this.valueAsInt() < ((PrimitiveData)other).getValue().shortValue();
		case LONG:
			return this.valueAsInt() < ((PrimitiveData)other).getValue().longValue();
		case DOUBLE:
			return this.valueAsInt() < ((PrimitiveData)other).getValue().doubleValue();
		case FLOAT:
			return this.valueAsInt() < ((PrimitiveData)other).getValue().floatValue();
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
			return this.valueAsInt() > ((PrimitiveData)other).getValue().intValue();
		case CHAR:
			return this.valueAsInt() > ((CharData)other).value;
		case BYTE:
			return this.valueAsInt() > ((PrimitiveData)other).getValue().byteValue();
		case INT:
			return this.valueAsInt() > ((PrimitiveData)other).getValue().intValue();
		case SHORT:
			return this.valueAsInt() > ((PrimitiveData)other).getValue().shortValue();
		case LONG:
			return this.valueAsInt() > ((PrimitiveData)other).getValue().longValue();
		case DOUBLE:
			return this.valueAsInt() > ((PrimitiveData)other).getValue().doubleValue();
		case FLOAT:
			return this.valueAsInt() > ((PrimitiveData)other).getValue().floatValue();
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
			return new LongData(this.valueAsInt() + ((PrimitiveData)other).getValue().intValue());
		case BYTE:
			return new LongData(this.valueAsInt() + ((PrimitiveData)other).getValue().byteValue());
		case INT:
			return new LongData(this.valueAsInt() + ((PrimitiveData)other).getValue().intValue());
		case SHORT:
			return new LongData(this.valueAsInt() + ((PrimitiveData)other).getValue().shortValue());
		case LONG:
			return new LongData(this.valueAsInt() + ((PrimitiveData)other).getValue().longValue());
		case DOUBLE:
		case FLOAT:
			return new DoubleData(this.valueAsInt() + ((PrimitiveData)other).getValue().doubleValue());
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
			return new LongData(this.valueAsInt() - ((PrimitiveData)other).getValue().intValue());
		case BYTE:
			return new LongData(this.valueAsInt() - ((PrimitiveData)other).getValue().byteValue());
		case INT:
			return new LongData(this.valueAsInt() - ((PrimitiveData)other).getValue().intValue());
		case SHORT:
			return new LongData(this.valueAsInt() - ((PrimitiveData)other).getValue().shortValue());
		case LONG:
			return new LongData(this.valueAsInt() - ((PrimitiveData)other).getValue().longValue());
		case DOUBLE:
		case FLOAT:
			return new DoubleData(this.valueAsInt() - ((PrimitiveData)other).getValue().longValue());
		case CHAR:
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
			return new LongData(this.valueAsInt() * ((PrimitiveData)other).getValue().intValue());
		case BYTE:
			return new LongData(this.valueAsInt() * ((PrimitiveData)other).getValue().byteValue());
		case INT:
			return new LongData(this.valueAsInt() * ((PrimitiveData)other).getValue().intValue());
		case SHORT:
			return new LongData(this.valueAsInt() * ((PrimitiveData)other).getValue().shortValue());
		case LONG:
			return new LongData(this.valueAsInt() * ((PrimitiveData)other).getValue().longValue());
		case DOUBLE:
		case FLOAT:
			return new DoubleData(this.valueAsInt() * ((PrimitiveData)other).getValue().longValue());
		case CHAR:
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
		case BYTE:
		case INT:
		case SHORT:
		case LONG:
		case FLOAT:
		case DOUBLE:
			return new DoubleData((double)this.valueAsInt() / ((PrimitiveData)other).getValue().doubleValue());
		case CHAR:
		case BOOLEAN:
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
		case BYTE:
		case INT:
		case SHORT:
		case LONG:
		case DOUBLE:
		case FLOAT:
			return new DoubleData((double)this.valueAsInt() % ((PrimitiveData)other).getValue().doubleValue());
		case CHAR:
		case BOOLEAN:
		default:
			throw new CannotModulusException(this, other);
		}
	}

	@Override
	public Data xor(Data other) throws CannotXORException {

		if(!(other.getType() instanceof PrimitiveType)) {
			throw new CannotXORException(this, other);
		}

		switch ((PrimitiveType)other.getType()) {
		case BOOLEAN:
		case BYTE:
		case INT:
		case SHORT:
		case LONG:
			return new LongData(this.valueAsInt() ^ ((PrimitiveData)other).getValue().longValue());
		case DOUBLE:
		case FLOAT:
		case CHAR:
		default:
			throw new CannotXORException(this, other);
		}
	}

	@Override
	public Data or(Data other) throws CannotORException {

		if(!(other.getType() instanceof PrimitiveType)) {
			throw new CannotORException(this, other);
		}

		switch ((PrimitiveType)other.getType()) {
		case BOOLEAN:
		case BYTE:
		case INT:
		case SHORT:
		case LONG:
			return new LongData(this.valueAsInt() | ((PrimitiveData)other).getValue().longValue());
		case DOUBLE:
		case FLOAT:
		case CHAR:
		default:
			throw new CannotORException(this, other);
		}
	}

	@Override
	public Data and(Data other) throws CannotANDException {

		if(!(other.getType() instanceof PrimitiveType)) {
			throw new CannotANDException(this, other);
		}

		switch ((PrimitiveType)other.getType()) {
		case BOOLEAN:
		case BYTE:
		case INT:
		case SHORT:
		case LONG:
			return new LongData(this.valueAsInt() & ((PrimitiveData)other).getValue().longValue());
		case CHAR:
		case DOUBLE:
		case FLOAT:
		default:
			throw new CannotANDException(this, other);
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
			return new BooleanData(this.value);
		case BYTE:
			return new ByteData((byte)this.valueAsInt());
		case CHAR:
			return new CharData((char)this.valueAsInt());
		case DOUBLE:
			return new DoubleData((double)this.valueAsInt());
		case FLOAT:
			return new FloatData((float)this.valueAsInt());
		case INT:
			return new IntegerData((int)this.valueAsInt());
		case LONG:
			return new LongData((long)this.valueAsInt());
		case SHORT:
			return new ShortData((short)this.valueAsInt());
		default:
			throw new CannotCastException(this.getType(), newType);
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

	@Override
	public ReimuType getType() {
		return PrimitiveType.BOOLEAN;
	}

	@Override
	public Number getValue() {
		return this.valueAsInt();
	}
}
