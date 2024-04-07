package com.git.ganksquad;

public interface Operator {

	public static final byte ADD = 1;
	public static final byte SUB = 2;
	public static final byte MUL = 3;
	public static final byte DIV = 4;

	public static final byte EQ = 5;
	public static final byte NEQ = 6;
	public static final byte LT = 7;
	public static final byte LTEQ = 8;
	public static final byte GT = 9;
	public static final byte GTEQ = 10;

	public static String opString(byte op) {
		
		switch (op) {
		case ADD:
			return "op.ADD";
		case SUB: 
			return "op.SUB";
		case MUL:
			return "op.MUL";
		case DIV:
			return "op.DIV";
		case EQ:
			return "op.EQ";
		case NEQ: 
			return "op.NEQ";
		case LT: 
			return "op.LT";
		case LTEQ:
			return "op.LTEQ";
		case GT:
			return "op.GT";
		case GTEQ:
			return "op.GTEQ";
		}

		return "op.UNKNOWN";
	}
}
