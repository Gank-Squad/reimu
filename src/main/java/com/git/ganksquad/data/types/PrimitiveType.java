package com.git.ganksquad.data.types;

public enum PrimitiveType implements ReimuType {

//	https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
	
	BYTE{

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
		
		@Override
		public boolean isEqualType(ReimuType other) {
			return other == BYTE;
		}
	},

	SHORT{
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

}
