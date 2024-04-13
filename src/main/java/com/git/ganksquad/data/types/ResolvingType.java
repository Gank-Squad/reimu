package com.git.ganksquad.data.types;

import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;

public class ResolvingType implements ReimuType {

	private ReimuType resolved = null;
	private String resolvesTo;
	
	public ResolvingType(String rt) {
		
		this.resolvesTo = rt;
	}
	
	public String getResolveName() {
		return this.resolvesTo;
	}
	
	public ReimuType getResolved() {
		return this.resolved;
	}

	public void resolve(ReimuTypeResolver resoler) throws ReimuCompileException {
		
		this.resolved = resoler.resolve(resolvesTo);
	}

	@Override
	public boolean isAssignableFrom(ReimuType other) {

		if(other instanceof ResolvingType) {
			return this.resolvesTo.equals(((ResolvingType)other).resolvesTo);
		}
		
		if(other instanceof UserDefinedType) {

			UserDefinedType t = (UserDefinedType)other;

			if(t.getName().equals(this.resolvesTo)) {
				this.resolved = t;

				return true;
			}

			return false;
		}

		if(this.resolved != null) {
			return this.resolved.isAssignableFrom(other);
		}

		return false;
	}

	@Override
	public boolean isEqualType(ReimuType other) {

		if(other instanceof ResolvingType) {
			return this.resolvesTo.equals(((ResolvingType)other).resolvesTo);
		}

		if(other instanceof UserDefinedType) {

			UserDefinedType t = (UserDefinedType)other;

			if(t.getName().equals(this.resolvesTo)) {
				this.resolved = t;

				return true;
			}

			return false;
		}

		if(this.resolved != null) {
			return this.resolved.isEqualType(other);
		}
		return false;
	}

	@Override
	public String toString() {
		if(this.resolved!=null) {
			return this.resolved.toString();
		}
		return this.formatToString(this.resolvesTo);
	}
}
