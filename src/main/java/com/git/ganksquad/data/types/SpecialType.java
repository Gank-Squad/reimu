package com.git.ganksquad.data.types;

import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;

public enum SpecialType implements ReimuType{

	VOID{

		@Override
		public boolean isEqualType(ReimuType other) {
			return other == VOID;
		}

		@Override
		public boolean isAssignableFrom(ReimuType other) {
			return other == VOID;
		}

		@Override
		public void resolve(ReimuTypeResolver resolver) {
		}
		
		@Override
		public boolean canBeBool() {
			return false;
		}
	},

	UNKNOWN {

		@Override
		public boolean isEqualType(ReimuType other) {
			return other == UNKNOWN;
		}

		@Override
		public boolean isAssignableFrom(ReimuType other) {
			return true;
		}

		@Override
		public void resolve(ReimuTypeResolver resolver) {
		}
		
		@Override
		public boolean canBeBool() {
			return true;
		}
	};

	@Override
	public Data getDefaultEmptyValue() {
		return NoneData.instance;
	}
	@Override
	public String getLookupName() {
		return this.toString();
	}
}
