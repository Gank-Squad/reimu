package com.git.ganksquad.data.types;

import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.BooleanData;
import com.git.ganksquad.data.impl.ByteData;
import com.git.ganksquad.data.impl.CharData;
import com.git.ganksquad.data.impl.DoubleData;
import com.git.ganksquad.data.impl.FloatData;
import com.git.ganksquad.data.impl.IntegerData;
import com.git.ganksquad.data.impl.LongData;
import com.git.ganksquad.data.impl.ShortData;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;

public enum PrimitiveType implements ReimuType { 

//	https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
	
	BYTE{
		
		@Override
		public Object getValueFromString(String value) {
			return Byte.parseByte(value);
		}
		
		@Override
		public Object getValueFromString(String value, int radix) {
			return Byte.parseByte(value, radix);
		}

		@Override
		public Data getDefaultEmptyValue() {
			return new ByteData((byte)0);
		}
	},

	SHORT{
		
		@Override
		public Data getDefaultEmptyValue() {
			return new ShortData((byte)0);
		}
		@Override
		public Object getValueFromString(String value) {
			return Short.parseShort(value);
		}
		
		@Override
		public Object getValueFromString(String value, int radix) {
			return Short.parseShort(value, radix);
		}
	},

	INT{
		
		@Override
		public Data getDefaultEmptyValue() {
			return new IntegerData((byte)0);
		}
		@Override
		public Object getValueFromString(String value) {
			return Integer.parseInt(value);
		}
		
		@Override
		public Object getValueFromString(String value, int radix) {
			return Integer.parseInt(value, radix);
		}
	},

	LONG{
		
		@Override
		public Data getDefaultEmptyValue() {
			return new LongData(0);
		}

		@Override
		public Object getValueFromString(String value) {
			return Long.parseLong(value);
		}
		
		@Override
		public Object getValueFromString(String value, int ordinal) {
			return Long.parseLong(value, ordinal);
		}
	},

	FLOAT{
		
		@Override
		public Data getDefaultEmptyValue() {
			return new FloatData(0f);
		}

		@Override
		public Object getValueFromString(String value) {
			return Float.parseFloat(value);
		}
		
		@Override
		public Object getValueFromString(String value, int ordinal) {
			return getValueFromString(value);
		}
	},

	DOUBLE{
		
		@Override
		public Data getDefaultEmptyValue() {
			return new DoubleData(0.0);
		}

		@Override
		public Object getValueFromString(String value, int ordinal) {
			return getValueFromString(value);
		}

		@Override
		public Object getValueFromString(String value) {
			return Double.parseDouble(value);
		}
	},

	BOOLEAN{
		
		@Override
		public Data getDefaultEmptyValue() {
			return new BooleanData(false);
		}

		@Override
		public Object getValueFromString(String value, int ordinal) {
			return getValueFromString(value);
		}

		@Override
		public Object getValueFromString(String value) {
			return Boolean.parseBoolean(value);
		}
	},

	CHAR{
		
		@Override
		public Data getDefaultEmptyValue() {
			return new CharData((char)0);
		}

		@Override
		public Object getValueFromString(String value, int ordinal) {
			return getValueFromString(value);
		}

		@Override
		public Object getValueFromString(String value) {
			if(value.length()==3)
				return value.charAt(1);
			return value.charAt(0);
		}
	};
	
	@Override
	public boolean canBeBool() {
		return true;
	}

	public boolean isEqualType(ReimuType other) {
		return this == other;
	}
	
	@Override
	public void resolve(ReimuTypeResolver resolver) throws ReimuCompileException{
	}
	@Override
	public String getLookupName() {
		return this.toString();
	}

	@Override
	public boolean isAssignableFrom(ReimuType other) {

		if(!(other instanceof PrimitiveType)) {
			return false;
		}
		return true;
		}

	public abstract Object getValueFromString(String value);
	public abstract Object getValueFromString(String value, int radix);
}
