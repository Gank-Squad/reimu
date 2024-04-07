package com.git.ganksquad.data;

import com.git.ganksquad.exceptions.CannotCompareException;

public interface ComparableData {

	abstract boolean eq(Data other) throws CannotCompareException;
	abstract boolean lt(Data other) throws CannotCompareException;
	abstract boolean gt(Data other) throws CannotCompareException;

	public default boolean neq(Data other) throws CannotCompareException{
		return !this.eq(other);
	}

	public default boolean gteq(Data other) throws CannotCompareException{
		return !this.lt(other);
	}

	public default boolean lteq(Data other) throws CannotCompareException{
		return !this.gt(other);
	}
	
	public static boolean eq(Data left, Data right) throws CannotCompareException {
		
		if(left instanceof ComparableData)
			return ((ComparableData)left).eq(right);
		
		throw new CannotCompareException(left, right);
	}

	public static boolean lt(Data left, Data right) throws CannotCompareException {
		
		if(left instanceof ComparableData)
			return ((ComparableData)left).lt(right);
		
		throw new CannotCompareException(left, right);
	}

	public static boolean gt(Data left, Data right) throws CannotCompareException {
		
		if(left instanceof ComparableData)
			return ((ComparableData)left).gt(right);
		
		throw new CannotCompareException(left, right);
	}

	public static boolean neq(Data left, Data right) throws CannotCompareException {
		
		if(left instanceof ComparableData)
			return ((ComparableData)left).neq(right);
		
		throw new CannotCompareException(left, right);
	}

	public static boolean lteq(Data left, Data right) throws CannotCompareException {
		
		if(left instanceof ComparableData)
			return ((ComparableData)left).lteq(right);
		
		throw new CannotCompareException(left, right);
	}

	public static boolean gteq(Data left, Data right) throws CannotCompareException {
		
		if(left instanceof ComparableData)
			return ((ComparableData)left).gteq(right);
		
		throw new CannotCompareException(left, right);
	}
}
