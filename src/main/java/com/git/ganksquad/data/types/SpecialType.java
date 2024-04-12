package com.git.ganksquad.data.types;

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
	};

}
