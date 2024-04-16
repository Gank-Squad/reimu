package com.git.ganksquad.data;

import com.git.ganksquad.exceptions.runtime.arithmetic.CannotANDException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotAddException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotDivideException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotModulusException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotMultiplyException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotORException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotSubtractException;
import com.git.ganksquad.exceptions.runtime.arithmetic.CannotXORException;

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
	 * Modulus this data to other data
	 * @param other The right side of the operation
	 * @return The result of the operation
	 * @throws CannotDivideException if this data or other data cannot be divided
	 */
	abstract Data mod(Data other) throws CannotModulusException;

	/**
	 * XOR this data to other data
	 * @param other The right side of the operation
	 * @return The result of the operation
	 * @throws CannotDivideException if this data or other data cannot be divided
	 */
	abstract Data xor(Data other) throws CannotXORException;

	/**
	 * OR this data to other data
	 * @param other The right side of the operation
	 * @return The result of the operation
	 * @throws CannotDivideException if this data or other data cannot be divided
	 */
	abstract Data or(Data other) throws CannotORException;

	/**
	 * and this data to other data
	 * @param other The right side of the operation
	 * @return The result of the operation
	 * @throws CannotDivideException if this data or other data cannot be divided
	 */
	abstract Data and(Data other) throws CannotANDException;

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
	}

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
	}

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
	}

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
	}

	/**
	 * modulus the two datas together
	 * @param left The left data, which should be {@link ArithmeticData}
	 * @param right The right data
	 * @return The sum of left * right
	 * @throws CannotDivideException If they cannot be divided
	 */
	public static Data mod(Data left, Data right) throws CannotModulusException {
		
		if (left instanceof ArithmeticData) {
			
			return ((ArithmeticData)left).mod(right);
		}

		throw new CannotModulusException(left, right);
	}

	/**
	 * xor the two datas together
	 * @param left The left data, which should be {@link ArithmeticData}
	 * @param right The right data
	 * @return The sum of left * right
	 * @throws CannotDivideException If they cannot be divided
	 */
	public static Data xor(Data left, Data right) throws CannotXORException {
		
		if (left instanceof ArithmeticData) {
			
			return ((ArithmeticData)left).xor(right);
		}

		throw new CannotXORException(left, right);
	}

	/**
	 * or the two datas together
	 * @param left The left data, which should be {@link ArithmeticData}
	 * @param right The right data
	 * @return The sum of left * right
	 * @throws CannotDivideException If they cannot be divided
	 */
	public static Data or(Data left, Data right) throws CannotORException {
		
		if (left instanceof ArithmeticData) {
			
			return ((ArithmeticData)left).or(right);
		}

		throw new CannotORException(left, right);
	}

	/**
	 * and the two datas together
	 * @param left The left data, which should be {@link ArithmeticData}
	 * @param right The right data
	 * @return The sum of left * right
	 * @throws CannotDivideException If they cannot be divided
	 */
	public static Data and(Data left, Data right) throws CannotANDException {
		
		if (left instanceof ArithmeticData) {
			
			return ((ArithmeticData)left).and(right);
		}

		throw new CannotANDException(left, right);
	}
}
