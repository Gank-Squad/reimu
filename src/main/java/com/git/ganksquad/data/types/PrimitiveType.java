package com.git.ganksquad.data.types;

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
		public Class<?> getJavaPrimitiveClass() {
			return Byte.class;
		}

		@Override
		public boolean isAssignableFrom(ReimuType other) {
			
			if(!(other instanceof PrimitiveType)) {
				return false;
			}

			switch ((PrimitiveType)other) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
				return true;
			default:
				return  false;
			}
		}
	},

	SHORT{
		
		@Override
		public Object getValueFromString(String value) {
			return Short.parseShort(value);
		}
		
		@Override
		public Object getValueFromString(String value, int radix) {
			return Short.parseShort(value, radix);
		}
		
		@Override
		public Class<?> getJavaPrimitiveClass() {
			return Short.class;
		}
		
		@Override
		public boolean isAssignableFrom(ReimuType other) {
			
			if(!(other instanceof PrimitiveType)) {
				return false;
			}

			switch ((PrimitiveType)other) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case SHORT:
				return true;
			default:
				return  false;
			}
		}
	},

	INT{
		
		@Override
		public Object getValueFromString(String value) {
			return Integer.parseInt(value);
		}
		
		@Override
		public Object getValueFromString(String value, int radix) {
			return Integer.parseInt(value, radix);
		}
		
		@Override
		public Class<?> getJavaPrimitiveClass() {
			return Integer.class;
		}
		
		@Override
		public boolean isAssignableFrom(ReimuType other) {
			if(!(other instanceof PrimitiveType)) {
				return false;
			}

			switch ((PrimitiveType)other) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case FLOAT:
			case INT:
			case SHORT:
				return true;
			default:
				return  false;
			}
		}
	},

	LONG{
		
		@Override
		public Object getValueFromString(String value) {
			return Long.parseLong(value);
		}
		
		@Override
		public Object getValueFromString(String value, int ordinal) {
			return Long.parseLong(value, ordinal);
		}
		@Override
		public Class<?> getJavaPrimitiveClass() {
			return Long.class;
		}

		@Override
		public boolean isAssignableFrom(ReimuType other) {
			if(!(other instanceof PrimitiveType)) {
				return false;
			}

			switch ((PrimitiveType)other) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case FLOAT:
			case INT:
			case SHORT:
			case LONG:
			case DOUBLE:
				return true;
			default:
				return  false;
			}
		}
	},

	FLOAT{
		
		@Override
		public Object getValueFromString(String value) {
			return Float.parseFloat(value);
		}
		
		@Override
		public Object getValueFromString(String value, int ordinal) {
			return getValueFromString(value);
		}
		
		@Override
		public Class<?> getJavaPrimitiveClass() {
			return Float.class;
		}
		
		@Override
		public boolean isAssignableFrom(ReimuType other) {
			if(!(other instanceof PrimitiveType)) {
				return false;
			}

			switch ((PrimitiveType)other) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case FLOAT:
			case INT:
			case SHORT:
				return true;
			default:
				return  false;
			}
		}
	},

	DOUBLE{
		
		@Override
		public Object getValueFromString(String value, int ordinal) {
			return getValueFromString(value);
		}

		@Override
		public Object getValueFromString(String value) {
			return Double.parseDouble(value);
		}
		@Override
		public Class<?> getJavaPrimitiveClass() {
			return Double.class;
		}
		
		@Override
		public boolean isAssignableFrom(ReimuType other) {
			if(!(other instanceof PrimitiveType)) {
				return false;
			}

			switch ((PrimitiveType)other) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case FLOAT:
			case INT:
			case SHORT:
			case LONG:
			case DOUBLE:
				return true;
			default:
				return  false;
			}
		}
	},

	BOOLEAN{
		
		@Override
		public Object getValueFromString(String value, int ordinal) {
			return getValueFromString(value);
		}
		@Override
		public Object getValueFromString(String value) {
			return Boolean.parseBoolean(value);
		}
		
		@Override
		public Class<?> getJavaPrimitiveClass() {
			return Boolean.class;
		}
		
		@Override
		public boolean isAssignableFrom(ReimuType other) {
			if(!(other instanceof PrimitiveType)) {
				return false;
			}

			switch ((PrimitiveType)other) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case FLOAT:
			case INT:
			case SHORT:
			case LONG:
			case DOUBLE:
				return true;
			default:
				return  false;
			}
		}
	},

	CHAR{
		
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

		@Override
		public Class<?> getJavaPrimitiveClass() {
			return Character.class;
		}
		
		@Override
		public boolean isAssignableFrom(ReimuType other) {

			if(!(other instanceof PrimitiveType)) {
				return false;
			}

			switch ((PrimitiveType)other) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
				return true;
			default:
				return  false;
			}
		}
	};

	public boolean isEqualType(ReimuType other) {
		return this == other;
	}

	public abstract Class<?> getJavaPrimitiveClass();
	public abstract Object getValueFromString(String value);
	public abstract Object getValueFromString(String value, int radix);
}
