package com.git.ganksquad.expressions;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.BooleanData;
import com.git.ganksquad.data.impl.FloatData;
import com.git.ganksquad.data.impl.IntegerData;
import com.git.ganksquad.data.types.PrimitiveType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class PrimitiveLiteral<T> implements Expression {
	
	public T value;
	public PrimitiveType type;
	
	public PrimitiveLiteral(T value, PrimitiveType t){
		
		this.value = value;
		this.type = t;
	}
	
	public static PrimitiveLiteral<?> fromString(PrimitiveType t, String value){
		return fromString(t, value, 10);
	}

	public static PrimitiveLiteral<?> fromString(PrimitiveType t, String value, int radix){
		
		ParseChecks.RequiredNotNull(t, value);
		
		switch (t) {
		case BOOLEAN:
			return new PrimitiveLiteral<Boolean>((Boolean)t.getValueFromString(value, radix), t);
		case BYTE:
			return new PrimitiveLiteral<Byte>((Byte)t.getValueFromString(value, radix), t);
		case CHAR:
			return new PrimitiveLiteral<Character>((Character)t.getValueFromString(value, radix), t);
		case DOUBLE:
			return new PrimitiveLiteral<Double>((Double)t.getValueFromString(value, radix), t);
		case FLOAT:
			return new PrimitiveLiteral<Float>((Float)t.getValueFromString(value, radix), t);
		case SHORT:
			return new PrimitiveLiteral<Short>((Short)t.getValueFromString(value, radix), t);
		case INT:
			return new PrimitiveLiteral<Integer>((Integer)t.getValueFromString(value, radix), t);
		case LONG:
			return new PrimitiveLiteral<Long>((Long)t.getValueFromString(value, radix), t);
		default:
			throw new IllegalArgumentException(String.format("value %s passed was not in the switch case", t));
		}
	}
	

	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {

		return this.type;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {

		switch (this.type) {
		case BOOLEAN:
			return new BooleanData(((Boolean)this.value).booleanValue());
		case CHAR:
			return new IntegerData(((Character)this.value).charValue());
		case LONG:
		case BYTE:
		case SHORT:
		case INT:
			return new IntegerData(((Number)this.value).intValue());
		case DOUBLE:
		case FLOAT:
			return new FloatData(((Number)this.value).doubleValue());
		default:
			throw new IllegalArgumentException("value passed was not in the switch case");
		}
	}

	@Override
	public String toString() {
		return this.formatToString(this.type, this.value);
	}
}
