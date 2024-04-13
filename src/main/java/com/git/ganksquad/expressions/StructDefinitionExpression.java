package com.git.ganksquad.expressions;

import java.util.List;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.data.types.ResolvingType;
import com.git.ganksquad.data.types.SpecialType;
import com.git.ganksquad.data.types.UserDefinedType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class StructDefinitionExpression implements Expression {
	
	public String name;
	public List<String> members;
	public List<ReimuType> memberTypes;
	
	public StructDefinitionExpression(String name, List<String> mem, List<ReimuType> typ) {
		this.name = name;
		this.members = mem;
		this.memberTypes = typ;
	}

	public static StructDefinitionExpression from( String name, List<String> argNames, List<ReimuType> argTypes) {
		
		ParseChecks.RequiredNotNull( name, argNames, argTypes);

		return new StructDefinitionExpression(name, argNames, argTypes);
	}

	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {
		
		this.trace();

		UserDefinedType type = new UserDefinedType(name);

		for(int i = 0; i < this.memberTypes.size(); i++) {

			if(memberTypes.get(i) instanceof ResolvingType) {

				ResolvingType t = (ResolvingType) memberTypes.get(i);

				if(t.getResolveName().equals(this.name)) {
					
					memberTypes.set(i, type);
				}
				else {
				
					memberTypes.set(i, resolver.resolve(t.getResolveName()));
				}
			}
		}

		resolver.declare(name, type);

		type.populateTypes(members, memberTypes);
		
		return SpecialType.VOID;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		return NoneData.instance;
	}
	
	@Override
	public String toString() {
		return this.formatToString(
				this.name, 
				this.members, 
				this.memberTypes);
	}

}
