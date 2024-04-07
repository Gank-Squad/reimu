package com.git.ganksquad.data;

import com.git.ganksquad.exceptions.Arithmetic.CannotAddException;
import com.git.ganksquad.exceptions.Arithmetic.CannotDivideException;
import com.git.ganksquad.exceptions.Arithmetic.CannotMultiplyException;
import com.git.ganksquad.exceptions.Arithmetic.CannotSubtractException;

/**
 * Represents a generic data which arithmetic can be performed with another data.
 * 
 * Only the left-hand-side needs to be this type.
 */
public interface ArithmeticData {

	/**
	 * Add this data to other data
	 * @param other The right side of the operation
	 * @return The result of the operation
	 * @throws CannotAddException if this data or other data cannot be added together
	 */
	abstract Data add(Data other) throws CannotAddException;

	/**
	 * Subtract this data to other data
	 * @param other The right side of the operation
	 * @return The result of the operation
	 * @throws CannotSubtractException if this data or other data cannot be subtracted
	 */
	abstract Data sub(Data other) throws CannotSubtractException;

	/**
	 * Subtract this data to other data
	 * @param other The right side of the operation
	 * @return The result of the operation
	 * @throws CannotMultiplyException if this data or other data cannot be multiplied
	 */
	abstract Data mul(Data other) throws CannotMultiplyException;

	/**
	 * Subtract this data to other data
	 * @param other The right side of the operation
	 * @return The result of the operation
	 * @throws CannotDivideException if this data or other data cannot be divided
	 */
	abstract Data div(Data other) throws CannotDivideException;


	/**
	 * Add the two datas together
	 * @param left The left data, which should be {@link ArithmeticData}
	 * @param right The right data
	 * @return The sum of left + right
	 * @throws CannotAddException If they cannot be added together
	 */
	public static Data add(Data left, Data right) throws CannotAddException {
		
		if (left instanceof ArithmeticData) {
			
			return ((ArithmeticData)left).add(right);
		}

		throw new CannotAddException(left, right);
	};

	/**
	 * Subtract the two datas together
	 * @param left The left data, which should be {@link ArithmeticData}
	 * @param right The right data
	 * @return The sum of left - right
	 * @throws CannotSubtractException If they cannot be subtracted
	 */
	public static Data sub(Data left, Data right) throws CannotSubtractException {
		
		if (left instanceof ArithmeticData) {
			
			return ((ArithmeticData)left).sub(right);
		}

		throw new CannotSubtractException(left, right);
	};

	/**
	 * Multiply the two datas together
	 * @param left The left data, which should be {@link ArithmeticData}
	 * @param right The right data
	 * @return The sum of left * right
	 * @throws CannotMultiplyException If they cannot be multiplied
	 */
	public static Data mul(Data left, Data right) throws CannotMultiplyException {
		
		if (left instanceof ArithmeticData) {
			
			return ((ArithmeticData)left).mul(right);
		}

		throw new CannotMultiplyException(left, right);
	};

	/**
	 * Multiply the two datas together
	 * @param left The left data, which should be {@link ArithmeticData}
	 * @param right The right data
	 * @return The sum of left * right
	 * @throws CannotDivideException If they cannot be divided
	 */
	public static Data div(Data left, Data right) throws CannotDivideException {
		
		if (left instanceof ArithmeticData) {
			
			return ((ArithmeticData)left).div(right);
		}

		throw new CannotDivideException(left, right);
	};
}
