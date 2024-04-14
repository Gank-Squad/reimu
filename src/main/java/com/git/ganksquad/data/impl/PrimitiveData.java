package com.git.ganksquad.data.impl;

import com.git.ganksquad.data.ArithmeticData;
import com.git.ganksquad.data.BooleanEvaluable;
import com.git.ganksquad.data.ClassKeys;
import com.git.ganksquad.data.ComparableData;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.IndexKeyData;
import com.git.ganksquad.data.types.PrimitiveType;
import com.git.ganksquad.exceptions.runtime.CannotCompareException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotANDException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotAddException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotDivideException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotModulusException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotMultiplyException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotORException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotSubtractException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotXORException;

public class PrimitiveData implements Data, ArithmeticData, ComparableData, BooleanEvaluable, IndexKeyData  {
	public PrimitiveType type;
	public Number value = 0;
//	public double dvalue = 0;
	
	public PrimitiveData(long value, PrimitiveType type) {
		this.type = type;
		
		switch (type) {
		case BOOLEAN:
			this.value = value != 0 ? 1 : 0;
			break;
		case BYTE:
			this.value = (byte)value;
			break;
		case CHAR:
		case SHORT:
			this.value = (short)value;
			break;
		case DOUBLE:
			this.value = Double.longBitsToDouble(value);
			break;
		case FLOAT:
			this.value = (float)Double.longBitsToDouble(value);
			break;
		case INT:
			this.value = (int)value;
			break;
		case LONG:
			this.value = value;
			break;
		}
	}
	
	public PrimitiveData(double value, PrimitiveType type) {
		this(Double.doubleToLongBits(value), type);
	}
	
	public PrimitiveData(Number value, PrimitiveType type) {
		this.value = value;
		this.type = type;
	}
	
	public PrimitiveData(byte value) {
		this.type = PrimitiveType.BYTE;
		this.value = value;
	}
	public PrimitiveData(short value) {
		this.type = PrimitiveType.SHORT;
		this.value = value;
	}
	public PrimitiveData(int value) {
		this.type = PrimitiveType.INT;
		this.value = value;
	}
	public PrimitiveData(long value) {
		this.type = PrimitiveType.LONG;
		this.value = value;
	}
	public PrimitiveData(float value) {
		this.type = PrimitiveType.FLOAT;
		this.value = value;
	}
	public PrimitiveData(double value) {
		this.type = PrimitiveType.DOUBLE;
		this.value = value;
	}
	public PrimitiveData(boolean value) {
		this.type = PrimitiveType.BOOLEAN;
		this.value = value ? 1 : 0;
	}
	public PrimitiveData(char value) {
		this.type = PrimitiveType.CHAR;
		this.value = (int)value & 0xFFFF;
	}
	
	
	public boolean asBool() {
		// basically just checking if there is any bits that are 1
		return this.value.longValue() != 0;
	}
	
	public int asChar() {
		// shorts and chars are same number of bits
		return this.value.shortValue();
	}
	
	public static PrimitiveType getLargerType(PrimitiveType a, PrimitiveType b) {
		// want the left type to take precedence in case of equal sizes, so >=
		if (a.getBitSize() >= b.getBitSize()) {
			return a;
		}
		return b;
	}
	
	public static long getBitPreservedLongValue(PrimitiveData data) {
		switch(data.type) {
		case BOOLEAN:
			return data.asBool() ? (long)1 : (long)0;
		case BYTE:
			return data.value.longValue() & 0xFF;
		case CHAR:
		case SHORT:
			return data.value.longValue() & 0xFFFF;
		case LONG:
			return data.value.longValue();
		case INT:
			return data.value.longValue() & 0xFFFFFFFF;
		case DOUBLE:
			return Double.doubleToLongBits(data.value.doubleValue());
		case FLOAT:
			return Double.doubleToLongBits((double)data.value.floatValue());
		}
		// this should never be reached
		return data.value.longValue();
	}
	
	public boolean eq(Data other) throws CannotCompareException {
		
		if (!(other instanceof PrimitiveData)) {
			throw new CannotCompareException(this, other);
		}
		
		return getBitPreservedLongValue(this) == getBitPreservedLongValue((PrimitiveData)other);
	}
	@Override
	public boolean evalAsBool() {
		return asBool();
	}
	
	@Override
	public boolean lt(Data other) throws CannotCompareException {
		if (!(other instanceof PrimitiveData)) {
			throw new CannotCompareException(this, other);
		}
		
		switch(((PrimitiveData)other).type) {
		case DOUBLE:
		case FLOAT:
			switch (this.type) {
			case DOUBLE:
			case FLOAT:
				return this.value.doubleValue() < ((PrimitiveData)other).value.doubleValue();
			default:
				return getBitPreservedLongValue(this) < ((PrimitiveData)other).value.doubleValue();
			}
		// if other is numeric or bool
		default:
			switch (this.type) {
			case DOUBLE:
			case FLOAT:
				return this.value.doubleValue() < getBitPreservedLongValue((PrimitiveData)other);
			default:
				return getBitPreservedLongValue(this) < getBitPreservedLongValue((PrimitiveData)other);
			}	
		}
	}
	
	@Override
	public boolean gt(Data other) throws CannotCompareException {
		if (!(other instanceof PrimitiveData)) {
			throw new CannotCompareException(this, other);
		}
		
		switch(((PrimitiveData)other).type) {
		case DOUBLE:
		case FLOAT:
			switch (this.type) {
			case DOUBLE:
			case FLOAT:
				return this.value.doubleValue() > ((PrimitiveData)other).value.doubleValue();
			default:
				return getBitPreservedLongValue(this) > ((PrimitiveData)other).value.doubleValue();
			}
		// if other is numeric or bool
		default:
			switch (this.type) {
			case DOUBLE:
			case FLOAT:
				return this.value.doubleValue() > getBitPreservedLongValue((PrimitiveData)other);
			default:
				return getBitPreservedLongValue(this) > getBitPreservedLongValue((PrimitiveData)other);
			}	
		}
	}
	
	// assume other is a boolean primitive to simplify code
	private Data addBool(PrimitiveData other) throws CannotAddException {
		if (other.type != PrimitiveType.BOOLEAN) {
			throw new CannotAddException(this, other);
		}
		
		switch (this.type) {
		case BOOLEAN:
			return new PrimitiveData(asBool() | other.asBool());
			
		case BYTE:
		case CHAR:
		case INT:
		case LONG:
		case SHORT:
			return new PrimitiveData(getBitPreservedLongValue(this) + getBitPreservedLongValue(other), getLargerType(type, other.type));
		
		case DOUBLE:
		case FLOAT:
			return new PrimitiveData(Double.doubleToLongBits(this.value.doubleValue() + getBitPreservedLongValue(other)), this.type);
		}
		// this should never be reached
		throw new CannotAddException(this, other);
	}
	
	// assume other is numeric (whole numbers)
	private Data addNumeric(PrimitiveData other) throws CannotAddException {
		if (other.type != PrimitiveType.BYTE || other.type != PrimitiveType.CHAR || other.type != PrimitiveType.INT || other.type != PrimitiveType.LONG
				|| other.type != PrimitiveType.SHORT) {
			throw new CannotAddException(this, other);
		}
		
		switch (this.type) {
		case BOOLEAN:		
		case BYTE:
		case CHAR:
		case INT:
		case LONG:
		case SHORT:
			return new PrimitiveData(getBitPreservedLongValue(this) + getBitPreservedLongValue(other), getLargerType(this.type,  other.type));
		
		case DOUBLE:
		case FLOAT:
			return new PrimitiveData(Double.doubleToLongBits(this.value.doubleValue() + getBitPreservedLongValue(other)), getLargerType(this.type,  other.type));
		}
		// this should never be reached
		throw new CannotAddException(this, other);
	}
	
	private Data addFloat(PrimitiveData other) throws CannotAddException {
		if (other.type != PrimitiveType.FLOAT || other.type != PrimitiveType.DOUBLE ) {
			throw new CannotAddException(this, other);
		}
		
		switch (this.type) {
		case BOOLEAN:		
		case BYTE:
		case CHAR:
		case INT:
		case LONG:
		case SHORT:
			return new PrimitiveData(Double.doubleToLongBits(getBitPreservedLongValue(this) + other.value.doubleValue()), getLargerType(this.type,  other.type));
		
		case DOUBLE:
		case FLOAT:
			return new PrimitiveData(Double.doubleToLongBits(this.value.doubleValue() + other.value.doubleValue()), getLargerType(this.type,  other.type));
		}
		// this should never be reached
		throw new CannotAddException(this, other);
	}
	
	@Override
	public Data add(Data other) throws CannotAddException {
		
		if (!(other instanceof PrimitiveData)) {
			throw new CannotAddException(this, other);
		}
		
		switch (((PrimitiveData)other).type) {
		case BOOLEAN:
			return addBool((PrimitiveData)other);
		case BYTE:
		case CHAR:
		case INT:
		case LONG:
		case SHORT:
			return addNumeric((PrimitiveData)other);
			
		case DOUBLE:
		case FLOAT:
			return addFloat((PrimitiveData)other);
		}
		// this should never be reached
		throw new CannotAddException(this, other);
	}
	@Override
	public Data sub(Data other) throws CannotSubtractException {
		
		if (!(other instanceof PrimitiveData)) {
			throw new CannotSubtractException(this, other);
		}
		try {
			
			switch (((PrimitiveData)other).type) {
			case BOOLEAN:
				((PrimitiveData)other).value = -1 * getBitPreservedLongValue((PrimitiveData)other);
				return addBool((PrimitiveData)other);
			case BYTE:
			case CHAR:
			case INT:
			case LONG:
			case SHORT:
				((PrimitiveData)other).value = -1 * getBitPreservedLongValue((PrimitiveData)other);
				return addNumeric((PrimitiveData)other);
				
			case DOUBLE:
				((PrimitiveData)other).value = -1 * ((PrimitiveData)other).value.doubleValue();
				return addFloat((PrimitiveData)other);
			case FLOAT:
				((PrimitiveData)other).value = -1 * ((PrimitiveData)other).value.floatValue();
				return addFloat((PrimitiveData)other);
			}
		} catch (CannotAddException e) {
			throw new CannotSubtractException(this, other);
		}
		
		// this should never be reached
		throw new CannotSubtractException(this, other);
	}
	
	// assume other is Numeric
	private Data mulNumeric(PrimitiveData other) throws CannotMultiplyException {
		if (other.type != PrimitiveType.BYTE || other.type != PrimitiveType.CHAR || other.type != PrimitiveType.INT || other.type != PrimitiveType.LONG
				|| other.type != PrimitiveType.SHORT) {
			throw new CannotMultiplyException(this, other);
		}
		
		switch (this.type) {
		case BOOLEAN:
			return ((PrimitiveData)other).asBool() ? new PrimitiveData(((PrimitiveData)other).value, ((PrimitiveData)other).type) : new PrimitiveData(0, ((PrimitiveData)other).type);
		case BYTE:
		case CHAR:
		case INT:
		case LONG:
		case SHORT:
			return new PrimitiveData(getBitPreservedLongValue(this) * getBitPreservedLongValue(other), getLargerType(this.type,  other.type));
			
		case DOUBLE:
		case FLOAT:
			return new PrimitiveData(Double.doubleToLongBits(this.value.doubleValue() * getBitPreservedLongValue(other)), getLargerType(this.type,  other.type));
		}
		
		throw new CannotMultiplyException(this, other);
	}
	
	private Data mulFloat(PrimitiveData other) throws CannotMultiplyException {
		if (other.type != PrimitiveType.BYTE || other.type != PrimitiveType.CHAR || other.type != PrimitiveType.INT || other.type != PrimitiveType.LONG
				|| other.type != PrimitiveType.SHORT) {
			throw new CannotMultiplyException(this, other);
		}
		
		switch (this.type) {
		case BOOLEAN:
			return ((PrimitiveData)other).asBool() ? new PrimitiveData(((PrimitiveData)other).value, ((PrimitiveData)other).type) : new PrimitiveData(0, ((PrimitiveData)other).type);
		case BYTE:
		case CHAR:
		case INT:
		case LONG:
		case SHORT:
			return new PrimitiveData(Double.doubleToLongBits(getBitPreservedLongValue(this) * other.value.doubleValue()), getLargerType(this.type,  other.type));
		
		case DOUBLE:
		case FLOAT:
			return new PrimitiveData(Double.doubleToLongBits(this.value.doubleValue() * other.value.doubleValue()), getLargerType(this.type,  other.type));
		}
		
		throw new CannotMultiplyException(this, other);
	}
	
	@Override
	public Data mul(Data other) throws CannotMultiplyException {
		if (!(other instanceof PrimitiveData)) {
			throw new CannotMultiplyException(this, other);
		}
		
		switch (((PrimitiveData)other).type) {
		case BOOLEAN:
			return ((PrimitiveData)other).asBool() ? new PrimitiveData(((PrimitiveData)other).value, ((PrimitiveData)other).type) : new PrimitiveData(0, ((PrimitiveData)other).type);
		case BYTE:
		case CHAR:
		case INT:
		case LONG:
		case SHORT:
			return mulNumeric((PrimitiveData)other);
			
		case DOUBLE:
		case FLOAT:
			return mulFloat((PrimitiveData)other);
		}
		// this should never be reached
		throw new CannotMultiplyException(this, other);
	}
	
	private Data divNumeric(PrimitiveData other) throws CannotDivideException {
		if (other.type != PrimitiveType.BYTE || other.type != PrimitiveType.CHAR || other.type != PrimitiveType.INT || other.type != PrimitiveType.LONG
				|| other.type != PrimitiveType.SHORT) {
			throw new CannotDivideException(this, other);
		}
		
		switch (this.type) {
		case BOOLEAN:
			return ((PrimitiveData)other).asBool() ? 
					new PrimitiveData(1 / getBitPreservedLongValue(other), ((PrimitiveData)other).type) :
					new PrimitiveData(0, ((PrimitiveData)other).type);
		case BYTE:
		case CHAR:
		case INT:
		case LONG:
		case SHORT:
			return new PrimitiveData(getBitPreservedLongValue(this) / getBitPreservedLongValue(other), getLargerType(this.type,  other.type));
		
		case DOUBLE:
		case FLOAT:
			return new PrimitiveData(Double.doubleToLongBits(this.value.doubleValue() / getBitPreservedLongValue(other)), getLargerType(this.type,  other.type));
		}
		
		throw new CannotDivideException(this, other);
	}
	
	private Data divFloat(PrimitiveData other) throws CannotDivideException {
		if (other.type != PrimitiveType.BYTE || other.type != PrimitiveType.CHAR || other.type != PrimitiveType.INT || other.type != PrimitiveType.LONG
				|| other.type != PrimitiveType.SHORT) {
			throw new CannotDivideException(this, other);
		}
		
		switch (this.type) {
		case BOOLEAN:
			return ((PrimitiveData)other).asBool() ? 
					new PrimitiveData(1 / ((PrimitiveData)other).value.doubleValue(), ((PrimitiveData)other).type) :
					new PrimitiveData(0, ((PrimitiveData)other).type);
		case BYTE:
		case CHAR:
		case INT:
		case LONG:
		case SHORT:
			return new PrimitiveData(Double.doubleToLongBits(getBitPreservedLongValue(this) / other.value.doubleValue()), getLargerType(this.type,  other.type));
		
		case DOUBLE:
		case FLOAT:
			return new PrimitiveData(Double.doubleToLongBits(this.value.doubleValue() / other.value.doubleValue()), getLargerType(this.type,  other.type));
		}
		
		throw new CannotDivideException(this, other);
	}
	
	
	@Override
	public Data div(Data other) throws CannotDivideException {
		if (!(other instanceof PrimitiveData)) {
			throw new CannotDivideException(this, other);
		}
		
		switch (((PrimitiveData)other).type) {
		case BOOLEAN:
			throw new CannotDivideException(this, other);
		case BYTE:
		case CHAR:
		case INT:
		case LONG:
		case SHORT:
			return divNumeric((PrimitiveData)other);
			
		case DOUBLE:
		case FLOAT:
			return divFloat((PrimitiveData)other);
		}
		// this should never be reached
		throw new CannotDivideException(this, other);
	}
	
	
	private Data modNumeric(PrimitiveData other) throws CannotModulusException {
		if (other.type != PrimitiveType.BYTE || other.type != PrimitiveType.CHAR || other.type != PrimitiveType.INT || other.type != PrimitiveType.LONG
				|| other.type != PrimitiveType.SHORT) {
			throw new CannotModulusException(this, other);
		}
		
		switch (this.type) {
		case BOOLEAN:
			return ((PrimitiveData)other).asBool() ? 
					new PrimitiveData(1 % getBitPreservedLongValue(other), ((PrimitiveData)other).type) :
					new PrimitiveData(0, ((PrimitiveData)other).type);
		case BYTE:
		case CHAR:
		case INT:
		case LONG:
		case SHORT:
			return new PrimitiveData(getBitPreservedLongValue(this) % getBitPreservedLongValue(other), getLargerType(this.type,  other.type));
		
		case DOUBLE:
		case FLOAT:
			return new PrimitiveData(Double.doubleToLongBits(this.value.doubleValue() % getBitPreservedLongValue(other)), getLargerType(this.type,  other.type));
		}
		
		throw new CannotModulusException(this, other);
	}
	
	private Data modFloat(PrimitiveData other) throws CannotModulusException {
		if (other.type != PrimitiveType.BYTE || other.type != PrimitiveType.CHAR || other.type != PrimitiveType.INT || other.type != PrimitiveType.LONG
				|| other.type != PrimitiveType.SHORT) {
			throw new CannotModulusException(this, other);
		}
		
		switch (this.type) {
		case BOOLEAN:
			return ((PrimitiveData)other).asBool() ? 
					new PrimitiveData(1 % ((PrimitiveData)other).value.doubleValue(), ((PrimitiveData)other).type) :
					new PrimitiveData(0, ((PrimitiveData)other).type);
		case BYTE:
		case CHAR:
		case INT:
		case LONG:
		case SHORT:
			return new PrimitiveData(Double.doubleToLongBits(getBitPreservedLongValue(this) % other.value.doubleValue()), getLargerType(this.type,  other.type));
		
		case DOUBLE:
		case FLOAT:
			return new PrimitiveData(Double.doubleToLongBits(this.value.doubleValue() % other.value.doubleValue()), getLargerType(this.type,  other.type));
		}
		
		throw new CannotModulusException(this, other);
	}
	
	
	@Override
	public Data mod(Data other) throws CannotModulusException {
		if (!(other instanceof PrimitiveData)) {
			throw new CannotModulusException(this, other);
		}
		
		switch (((PrimitiveData)other).type) {
		case BOOLEAN:
			throw new CannotModulusException(this, other);
		case BYTE:
		case CHAR:
		case INT:
		case LONG:
		case SHORT:
			return modNumeric((PrimitiveData)other);
			
		case DOUBLE:
		case FLOAT:
			return modFloat((PrimitiveData)other);
		}
		// this should never be reached
		throw new CannotModulusException(this, other);
	}
	@Override
	public Data xor(Data other) throws CannotXORException {
		return new PrimitiveData(getBitPreservedLongValue(this) ^ getBitPreservedLongValue((PrimitiveData)other), getLargerType(this.type, ((PrimitiveData)other).type));
	}
	@Override
	public Data or(Data other) throws CannotORException {
		return new PrimitiveData(getBitPreservedLongValue(this) | getBitPreservedLongValue((PrimitiveData)other), getLargerType(this.type, ((PrimitiveData)other).type));
	}
	@Override
	public Data and(Data other) throws CannotANDException {
		return new PrimitiveData(getBitPreservedLongValue(this) & getBitPreservedLongValue((PrimitiveData)other), getLargerType(this.type, ((PrimitiveData)other).type));
	}
	@Override
	public int getClassKey() {
		return 0;
	}
}
