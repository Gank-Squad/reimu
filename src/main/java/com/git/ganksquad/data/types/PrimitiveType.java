package com.git.ganksquad.data.types;

public enum PrimitiveType implements TestReimuType {

//	https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
	
	BYTE{

		@Override
		public boolean isCompatibleWith(ReimuTypeEnumValue other) {
			switch (other) {
			case NUMERIC:
			case BOOLEAN:
				return true;
			default:
				return false;
			}
		}
	},

	SHORT{
		@Override
		public boolean isCompatibleWith(ReimuTypeEnumValue other) {
			switch (other) {
			case NUMERIC:
			case BOOLEAN:
				return true;
			default:
				return false;
			}
		}
	},

	INT{
		@Override
		public boolean isCompatibleWith(ReimuTypeEnumValue other) {
			switch (other) {
			case NUMERIC:
			case BOOLEAN:
				return true;
			default:
				return false;
			}
		}
	},

	LONG{
		@Override
		public boolean isCompatibleWith(ReimuTypeEnumValue other) {
			switch (other) {
			case NUMERIC:
			case BOOLEAN:
				return true;
			default:
				return false;
			}
		}
	},

	FLOAT{
		@Override
		public boolean isCompatibleWith(ReimuTypeEnumValue other) {
			switch (other) {
			case NUMERIC:
			case BOOLEAN:
				return true;
			default:
				return false;
			}
		}
	},
	
	DOUBLE{
		@Override
		public boolean isCompatibleWith(ReimuTypeEnumValue other) {
			switch (other) {
			case NUMERIC:
			case BOOLEAN:
				return true;
			default:
				return false;
			}
		}
	},
	
	BOOLEAN{
		@Override
		public boolean isCompatibleWith(ReimuTypeEnumValue other) {
			switch (other) {
			case NUMERIC:
			case BOOLEAN:
				return true;
			default:
				return false;
			}
		}
	},
	
	CHAR{
		@Override
		public boolean isCompatibleWith(ReimuTypeEnumValue other) {
			switch (other) {
			case NUMERIC:
			case BOOLEAN:
			case STRING:
				return true;
			default:
				return false;
			}
		}
	},
}
