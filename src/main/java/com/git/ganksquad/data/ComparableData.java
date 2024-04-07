package com.git.ganksquad.data;

import com.git.ganksquad.exceptions.CannotCompareException;

/**
 * Represents a generic data which can be compared to other data.
 * 
 * This is only required on the left-hand-side data.
 */
public interface ComparableData {

	/**
	 * Checks if this data is equal to other data
	 * @param other The data to compare
	 * @return true or false if both datas can be compared
	 * @throws CannotCompareException thrown if the datas cannot be compared
	 */
	abstract boolean eq(Data other) throws CannotCompareException;

	/**
	 * Checks if this data is less than other data
	 * @param other The data to compare
	 * @return true or false if both datas can be compared
	 * @throws CannotCompareException thrown if the datas cannot be compared
	 */

	abstract boolean lt(Data other) throws CannotCompareException;

	/**
	 * Checks if this data is greater than other data
	 * @param other The data to compare
	 * @return true or false if both datas can be compared
	 * @throws CannotCompareException thrown if the datas cannot be compared
	 */
	abstract boolean gt(Data other) throws CannotCompareException;

	/**
	 * Checks if this data is not equal to other data
	 * @param other The data to compare
	 * @return true or false if both datas can be compared
	 * @throws CannotCompareException thrown if the datas cannot be compared
	 */
	public default boolean neq(Data other) throws CannotCompareException{
		return !this.eq(other);
	}

	/**
	 * Checks if this data is greater than or equal to other data
	 * @param other The data to compare
	 * @return true or false if both datas can be compared
	 * @throws CannotCompareException thrown if the datas cannot be compared
	 */
	public default boolean gteq(Data other) throws CannotCompareException{
		return !this.lt(other);
	}

	/**
	 * Checks if this data is less than or equal to other data
	 * @param other The data to compare
	 * @return true or false if both datas can be compared
	 * @throws CannotCompareException thrown if the datas cannot be compared
	 */
	public default boolean lteq(Data other) throws CannotCompareException{
		return !this.gt(other);
	}

	/**
	 * Check if the left is equal to the right data
	 * @param left The left data, which should be {@link CompareableData}
	 * @param right The right data
	 * @return true or false if both datas can be compared
	 * @throws CannotCompareException
	 */
	public static boolean eq(Data left, Data right) throws CannotCompareException {
		
		if(left instanceof ComparableData)
			return ((ComparableData)left).eq(right);
		
		throw new CannotCompareException(left, right);
	}

	/**
	 * Check if the left is less than the right data
	 * @param left The left data, which should be {@link CompareableData}
	 * @param right The right data
	 * @return true or false if both datas can be compared
	 * @throws CannotCompareException
	 */
	public static boolean lt(Data left, Data right) throws CannotCompareException {
		
		if(left instanceof ComparableData)
			return ((ComparableData)left).lt(right);
		
		throw new CannotCompareException(left, right);
	}

	/**
	 * Check if the left is greater than the right data
	 * @param left The left data, which should be {@link CompareableData}
	 * @param right The right data
	 * @return true or false if both datas can be compared
	 * @throws CannotCompareException
	 */
	public static boolean gt(Data left, Data right) throws CannotCompareException {
		
		if(left instanceof ComparableData)
			return ((ComparableData)left).gt(right);
		
		throw new CannotCompareException(left, right);
	}

	/**
	 * Check if the left is not equal to the right data
	 * @param left The left data, which should be {@link CompareableData}
	 * @param right The right data
	 * @return true or false if both datas can be compared
	 * @throws CannotCompareException
	 */
	public static boolean neq(Data left, Data right) throws CannotCompareException {
		
		if(left instanceof ComparableData)
			return ((ComparableData)left).neq(right);
		
		throw new CannotCompareException(left, right);
	}

	/**
	 * Check if the left is less than or equal to the right data
	 * @param left The left data, which should be {@link CompareableData}
	 * @param right The right data
	 * @return true or false if both datas can be compared
	 * @throws CannotCompareException
	 */
	public static boolean lteq(Data left, Data right) throws CannotCompareException {
		
		if(left instanceof ComparableData)
			return ((ComparableData)left).lteq(right);
		
		throw new CannotCompareException(left, right);
	}

	/**
	 * Check if the left is greater than or equal to the right data
	 * @param left The left data, which should be {@link CompareableData}
	 * @param right The right data
	 * @return true or false if both datas can be compared
	 * @throws CannotCompareException
	 */
	public static boolean gteq(Data left, Data right) throws CannotCompareException {
		
		if(left instanceof ComparableData)
			return ((ComparableData)left).gteq(right);
		
		throw new CannotCompareException(left, right);
	}
}
