package com.git.ganksquad.expressions;

import com.git.ganksquad.Operator;
import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.ComparableData;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.BooleanData;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public class CompareExpression implements Expression {

	public Expression left;
	public Expression right;
	public byte operator;

	public CompareExpression(byte op, Expression left, Expression right) {
		
		this.operator = op;
		this.left = left;
		this.right = right;
	}
	
	public static CompareExpression eq(Expression left, Expression right) {
		
		ParseChecks.RequiredNotNull(left, right);

		return new CompareExpression(Operator.EQ, left, right);
	}

	public static CompareExpression neq(Expression left, Expression right) {
		
		ParseChecks.RequiredNotNull(left, right);

		return new CompareExpression(Operator.NEQ, left, right);
	}

	public static CompareExpression lt(Expression left, Expression right) {
		
		ParseChecks.RequiredNotNull(left, right);

		return new CompareExpression(Operator.LT, left, right);
	}

	public static CompareExpression lteq(Expression left, Expression right) {
		
		ParseChecks.RequiredNotNull(left, right);

		return new CompareExpression(Operator.LTEQ, left, right);
	}

	public static CompareExpression gt(Expression left, Expression right) {
		
		ParseChecks.RequiredNotNull(left, right);

		return new CompareExpression(Operator.GT, left, right);
	}

	public static CompareExpression gteq(Expression left, Expression right) {
		
		ParseChecks.RequiredNotNull(left, right);

		return new CompareExpression(Operator.GTEQ, left, right);
	}

	@Override
	public Data eval(ReimuRuntime runtime) throws ReimuRuntimeException {
		
		Data lData = left.eval(runtime);
		Data rData = right.eval(runtime);
		
		boolean result;
		
		switch (this.operator) {

		case Operator.EQ:
			result = ComparableData.eq(lData, rData);
			break;

		case Operator.NEQ:
			result =  ComparableData.neq(lData, rData);
			break;

		case Operator.LT:
			result =   ComparableData.lt(lData, rData);
			break;

		case Operator.LTEQ:
			result =   ComparableData.lteq(lData, rData);
			break;

		case Operator.GT:
			result =   ComparableData.gt(lData, rData);
			break;

		case Operator.GTEQ:
			result =   ComparableData.gteq(lData, rData);
			break;

		default:
			throw new RuntimeException(String.format("Invalid operator passed to %s", this.getClass().getSimpleName()));
		}
		
		return new BooleanData(result);
	}

	@Override
	public String toString() {
		return this.formatToString(
				Operator.opString(this.operator),
				this.left,
				this.right);
	}
}
