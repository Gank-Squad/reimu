package com.git.ganksquad.expressions;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.StringData;
import com.git.ganksquad.data.types.AggregateType;
import com.git.ganksquad.data.types.ArrayType;
import com.git.ganksquad.data.types.PrimitiveType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;

public class StringLiteral implements Expression {

	public String value = "";
	
	public StringLiteral() {}

	public StringLiteral(String value) {

		this.value = value;
	}
	
	public static StringLiteral fromString(String str) {
		
		ParseChecks.RequiredNotNull(str);

		return new StringLiteral(str);
	}

	public static StringLiteral fromQuotedString(String str) {
		
		ParseChecks.RequiredNotNull(str);

		return new StringLiteral(ParseChecks.getUnquotedOrFail(str));
	}

	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {
		this.trace();
		return AggregateType.STRING_TYPE;
	}

	@Override
	public Data eval(ReimuRuntime runtime) {

		return new StringData(this.value);
	}

	@Override
	public String toString() {
		return this.formatToString('"' + this.value + '"');
	}
}
