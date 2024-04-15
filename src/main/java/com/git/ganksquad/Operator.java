package com.git.ganksquad;

public interface Operator {

	public static final byte ADD = 1;
	public static final byte SUB = 2;
	public static final byte MUL = 3;
	public static final byte DIV = 4;
	public static final byte MOD = 5;
	public static final byte XOR = 6;
	public static final byte OR = 7;
	public static final byte AND = 8;

	public static final byte EQ = 9;
	public static final byte NEQ = 10;
	public static final byte LT = 11;
	public static final byte LTEQ = 12;
	public static final byte GT = 13;
	public static final byte GTEQ = 14;

	public static final byte BOOL_OR = 15;
	public static final byte BOOL_AND = 16;

	public static final byte BOOL_NOT = 17;

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
		case MOD:
			return "op.MOD";
		case XOR:
			return "op.XOR";
		case OR:
			return "op.OR";
		case AND:
			return "op.AND";
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
		case BOOL_AND:
			return "op.BOOL_AND";
		case BOOL_OR:
			return "op.BOOL_OR";
		case BOOL_NOT:
			return "op.BOOL_NOT";
		}

		return "op.UNKNOWN";
	}
}
