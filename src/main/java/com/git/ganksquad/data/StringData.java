package com.git.ganksquad.data;

import com.git.ganksquad.exceptions.CannotCompareException;

public class StringData implements Data, ComparableData {
	
	public String value = "";
	
	public StringData() {}

	public StringData(String value) {

		this.value = value;
	}
	
	@Override
	public boolean eq(Data other) throws CannotCompareException {
		
		switch (other.getClassKey()) {

		case ClassKeys.STRING_DATA:
			return this.value.equals(((StringData)other).value);

		default:
			throw new CannotCompareException(this, other);
		}
	}

	@Override
	public boolean lt(Data other) throws CannotCompareException {
		
		switch (other.getClassKey()) {

		case ClassKeys.STRING_DATA:
			return this.value.compareTo(((StringData)other).value) < 0;

		default:
			throw new CannotCompareException(this, other);
		}
	}

	@Override
	public boolean gt(Data other) throws CannotCompareException {
		
		switch (other.getClassKey()) {

		case ClassKeys.STRING_DATA:
			return this.value.compareTo(((StringData)other).value) > 0;

		default:
			throw new CannotCompareException(this, other);
		}
	}

	@Override
	public int getClassKey() {

		return ClassKeys.STRING_DATA;
	}

}
