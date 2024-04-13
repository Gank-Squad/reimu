package com.git.ganksquad.expressions;

import javax.xml.crypto.AlgorithmMethod;

import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.UserDefinedData;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.data.types.UserDefinedType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.TypeException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class MemberDerefExpression implements Expression, AssignableExpression {
	
	public Expression object;
	public String member;
	
	public MemberDerefExpression(Expression obj, String member) {
		
		this.member = member;
		this.object = obj;
	}

	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {
		
		ReimuType t = this.object.typeCheck(resolver);
		
		if(!(t instanceof UserDefinedType)) {
			
			throw new TypeException("Expression does not have any members since it is type %s", t);
		}

		UserDefinedType d = ((UserDefinedType)t);
		
		return d.members.get(this.member);
	}

	@Override
	public Data evalAssign(ReimuRuntime reimuRuntime, Data data) throws ReimuRuntimeException {

		((UserDefinedData)this.object.eval(reimuRuntime)).setMember(member, data);

		return data;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		return ((UserDefinedData)this.object.eval(reimuRuntime)).getMember(member);
	}

}
