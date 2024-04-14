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
		return this.value.doubleValue() != 0.0;
	}
	
	public int asChar() {
		return this.value.intValue() & 0xFFFF;
	}
	
	public static PrimitiveType getLargerType(PrimitiveType a, PrimitiveType b) {
		// want the left type to take precedence in case of equal sizes, so >=
		if (a.getBitSize() >= b.getBitSize()) {
			return a;
		}
		return b;
	}
	
	public boolean eq(Data other) throws CannotCompareException {
		
		if (!(other instanceof PrimitiveData)) {
			throw new CannotCompareException(this, other);
		}
		
		switch (((PrimitiveData)other).type) {
		case BOOLEAN:
			return asBool() == ((PrimitiveData)other).asBool();
		case BYTE:
			return this.value.byteValue() == ((PrimitiveData)other).value.byteValue();
		case CHAR:
			return asChar() == ((PrimitiveData)other).asChar();
		case DOUBLE:
			return this.value.doubleValue() == ((PrimitiveData)other).value.doubleValue();
		case FLOAT:
			return this.value.floatValue() == ((PrimitiveData)other).value.floatValue();
		case INT:
			return this.value.intValue() == ((PrimitiveData)other).value.intValue();
		case LONG:
			return this.value.longValue() == ((PrimitiveData)other).value.longValue();
		case SHORT:
			return this.value.shortValue() == ((PrimitiveData)other).value.shortValue();
		default:
			throw new CannotCompareException(this, other);
		
		}
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
		
		return this.value.doubleValue() < ((PrimitiveData)other).value.doubleValue();
	}
	
	@Override
	public boolean gt(Data other) throws CannotCompareException {
		if (!(other instanceof PrimitiveData)) {
			throw new CannotCompareException(this, other);
		}
		
		return this.value.doubleValue() > ((PrimitiveData)other).value.doubleValue();
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
			return new PrimitiveData(this.value.longValue() + other.value.longValue(), other.type);
		
		case DOUBLE:
		case FLOAT:
			return new PrimitiveData(Double.doubleToLongBits(this.value.doubleValue() + other.value.longValue()), this.type);
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
			return new PrimitiveData(this.value.longValue() + other.value.longValue(), getLargerType(this.type,  other.type));
		
		case DOUBLE:
		case FLOAT:
			return new PrimitiveData(Double.doubleToLongBits(this.value.doubleValue() + other.value.longValue()), getLargerType(this.type,  other.type));
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
			return new PrimitiveData(Double.doubleToLongBits(this.value.longValue() + other.value.doubleValue()), getLargerType(this.type,  other.type));
		
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
				((PrimitiveData)other).value = -1 * ((PrimitiveData)other).value.longValue();
				return addBool((PrimitiveData)other);
			case BYTE:
			case CHAR:
			case INT:
			case LONG:
			case SHORT:
				((PrimitiveData)other).value = -1 * ((PrimitiveData)other).value.longValue();
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
			return new PrimitiveData(this.value.longValue() * other.value.longValue(), getLargerType(this.type,  other.type));
		
		case DOUBLE:
		case FLOAT:
			return new PrimitiveData(Double.doubleToLongBits(this.value.doubleValue() * other.value.longValue()), getLargerType(this.type,  other.type));
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
			return new PrimitiveData(Double.doubleToLongBits(this.value.longValue() * other.value.doubleValue()), getLargerType(this.type,  other.type));
		
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
					new PrimitiveData(1 / ((PrimitiveData)other).value.longValue(), ((PrimitiveData)other).type) :
					new PrimitiveData(0, ((PrimitiveData)other).type);
		case BYTE:
		case CHAR:
		case INT:
		case LONG:
		case SHORT:
			return new PrimitiveData(this.value.longValue() / other.value.longValue(), getLargerType(this.type,  other.type));
		
		case DOUBLE:
		case FLOAT:
			return new PrimitiveData(Double.doubleToLongBits(this.value.doubleValue() / other.value.longValue()), getLargerType(this.type,  other.type));
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
			return new PrimitiveData(Double.doubleToLongBits(this.value.longValue() / other.value.doubleValue()), getLargerType(this.type,  other.type));
		
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
					new PrimitiveData(1 % ((PrimitiveData)other).value.longValue(), ((PrimitiveData)other).type) :
					new PrimitiveData(0, ((PrimitiveData)other).type);
		case BYTE:
		case CHAR:
		case INT:
		case LONG:
		case SHORT:
			return new PrimitiveData(this.value.longValue() % other.value.longValue(), getLargerType(this.type,  other.type));
		
		case DOUBLE:
		case FLOAT:
			return new PrimitiveData(Double.doubleToLongBits(this.value.doubleValue() % other.value.longValue()), getLargerType(this.type,  other.type));
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
			return new PrimitiveData(Double.doubleToLongBits(this.value.longValue() % other.value.doubleValue()), getLargerType(this.type,  other.type));
		
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
		return new PrimitiveData(this.value.longValue() ^ ((PrimitiveData)other).value.longValue(), getLargerType(this.type, ((PrimitiveData)other).type));
	}
	@Override
	public Data or(Data other) throws CannotORException {
		return new PrimitiveData(this.value.longValue() | ((PrimitiveData)other).value.longValue(), getLargerType(this.type, ((PrimitiveData)other).type));
	}
	@Override
	public Data and(Data other) throws CannotANDException {
		return new PrimitiveData(this.value.longValue() & ((PrimitiveData)other).value.longValue(), getLargerType(this.type, ((PrimitiveData)other).type));
	}
	@Override
	public int getClassKey() {
		return 0;
	}
	
	@Override
	public String toString() {
		return this.value.toString();
	}
}
