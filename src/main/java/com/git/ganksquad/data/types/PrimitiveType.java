package com.git.ganksquad.data.types;

public enum PrimitiveType implements TestReimuType {

//	https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
	
	BYTE{
		@Override
		public boolean isBooleanEvaluable() { return true; }
	},

	SHORT{
		@Override
		public boolean isBooleanEvaluable() { return true; }
	},

	INT{
		@Override
		public boolean isBooleanEvaluable() { return true; }
	},

	LONG{
		@Override
		public boolean isBooleanEvaluable() { return true; }
	},

	FLOAT{
		@Override
		public boolean isBooleanEvaluable() { return true; }
	},
	
	DOUBLE{
		@Override
		public boolean isBooleanEvaluable() { return true; }
	},
	
	BOOLEAN{
		@Override
		public boolean isBooleanEvaluable() { return true; }
	},
	
	CHAR{
		@Override
		public boolean isBooleanEvaluable() { return true; }
	},
}
