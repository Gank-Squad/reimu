package com.git.ganksquad.data.types;

import com.git.ganksquad.ReimuTypeResolver;

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
	};

	@Override
	public String getLookupName() {
		return this.toString();
	}
}
