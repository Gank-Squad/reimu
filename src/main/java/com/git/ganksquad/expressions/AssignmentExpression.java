package com.git.ganksquad.expressions;

import org.tinylog.Logger;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.ArrayData;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.data.types.SpecialType;
import com.git.ganksquad.data.types.UserDefinedType;
import com.git.ganksquad.data.types.ArrayType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.data.types.ResolvingType;
import com.git.ganksquad.exceptions.compiler.NoneTypeException;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.TypeException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class AssignmentExpression implements Expression {
	
	public ReimuType type;
	public String symbolName;
	public Expression symbolExpr;
	public Expression value;
	public boolean declare;

	public AssignmentExpression(ReimuType t, String name, Expression symbolExpr, Expression value, boolean declare) {
		
		this.type = t;
		this.symbolExpr = symbolExpr;
		this.symbolName = name;
		this.value = value;
		this.declare = declare;
	}
	
	public static AssignmentExpression declare(ReimuType t, String name, Expression value) {
		
		ParseChecks.RequiredNotNull(t, name, value);
		
		return new AssignmentExpression(t, name, null, value, true);
	}

	public static AssignmentExpression assign(ReimuType t, Expression sym, Expression value) {
		
		ParseChecks.RequiredNotNull(t, sym, value);

		return new AssignmentExpression(t, null, sym, value, false);
	}

	public ReimuType typeCheckWhenLeftIsExpr(ReimuTypeResolver resolver) throws ReimuCompileException {

		ReimuType rightT = this.value.typeCheck(resolver);

		if(this.symbolExpr instanceof AssignableExpression) {
			
			ReimuType leftT = this.symbolExpr.typeCheck(resolver);

			this.type = leftT;
		}
		else {
			
			throw new TypeException(String.format("Left side of assignment is not an assignable expression since it is %s", this.symbolExpr));
		}

		if(!this.type.isAssignableFrom(rightT)) {

			throw new TypeException(String.format("Cannot assign type %s from type %s", this.type, rightT));
		}

		return this.type;
	}


	public ReimuType typeCheckWhenLeftIsSymbol(ReimuTypeResolver resolver) throws ReimuCompileException {

		ReimuType t = this.value.typeCheck(resolver);

		if(this.type instanceof ResolvingType) {

			this.type = resolver.resolve(((ResolvingType)this.type).getResolveName());

			Logger.debug("Resolved type {}", this.type);
		}

		if(this.type == SpecialType.UNKNOWN) {

			if(t == SpecialType.UNKNOWN) {
				throw new TypeException(String.format("Could not resolve the type of variable %s", this.symbolName));
			}

			if(t == SpecialType.VOID) {

				throw new NoneTypeException(String.format("Tried to declare variable %s with None type",  this.symbolName));
			} 

			this.type = t;
		}
		else if(this.type instanceof ArrayType && t instanceof ArrayType) {
			
			ArrayType aType    = (ArrayType)t;
			ArrayType thisType = (ArrayType)this.type;

			thisType.inferSizes(aType);
		}

		if(!(this.value instanceof NoneExpression) && !this.type.isAssignableFrom(t)) {

			throw new TypeException(String.format("Cannot assign type %s to type %s", t, this.type));
		}

		resolver.declare(this.symbolName, this.type);

		return SpecialType.VOID;
	}

	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {

		this.trace();

		
		if(this.declare) {

			return this.typeCheckWhenLeftIsSymbol(resolver);
		}
		else {
			
			return this.typeCheckWhenLeftIsExpr(resolver);
		}
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		Data r = this.value.eval(reimuRuntime);
		
		if(!this.declare) {

			if(this.symbolExpr instanceof AssignableExpression){

				return ((AssignableExpression)this.symbolExpr).evalAssign(reimuRuntime, r);
			}

				throw new ReimuRuntimeException(String.format("THIS SHOUUD BE UNREACHABLE. Left side of assignment is not an assignable expression"));
		}
			

		if(r instanceof NoneData) {

			reimuRuntime.declare(this.symbolName, this.type.getDefaultEmptyValue());	
		}

		else if(this.type instanceof ArrayType && r instanceof ArrayData) {

			ArrayType atype = (ArrayType)this.type;
			ArrayData<Data> ad = (ArrayData<Data>)r;

			if(atype.size == -1) {
				
				reimuRuntime.declare(this.symbolName, ad);	

				return NoneData.instance;
			}
			if(ad.size() > atype.size) {

				throw new ReimuRuntimeException("Cannot assign array size larger than the type says");
			}

			ArrayData<Data> fillwith = atype.getDefaultEmptyValue();

			for(int i = 0; i < ad.size(); i++) {

				fillwith.set(i, ad.get(i));
			}

			reimuRuntime.declare(this.symbolName, fillwith);	
		}
		else {
			reimuRuntime.declare(this.symbolName, r.castTo(this.type));	
		}

		return NoneData.instance;
	}

	@Override
	public String toString() {
		
		if(this.symbolName != null)
		return this.formatToString( 
				this.type,
				this.symbolName,
				this.value,
				"declaration: " + this.declare
			);
		return this.formatToString( 
				this.type,
				this.symbolExpr,
				this.value,
				"declaration: " + this.declare
			);
	}
}
