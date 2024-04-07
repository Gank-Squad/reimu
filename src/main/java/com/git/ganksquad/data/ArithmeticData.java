package com.git.ganksquad.data;

import com.git.ganksquad.exceptions.CannotAddException;
import com.git.ganksquad.exceptions.CannotDivideException;
import com.git.ganksquad.exceptions.CannotMultiplyException;
import com.git.ganksquad.exceptions.CannotSubtractException;

public interface ArithmeticData {

	abstract Data add(Data other) throws CannotAddException;

	abstract Data sub(Data other) throws CannotSubtractException;

	abstract Data mul(Data other) throws CannotMultiplyException;

	abstract Data div(Data other) throws CannotDivideException;

	public static Data add(Data left, Data right) throws CannotAddException {
		
		if (left instanceof ArithmeticData) {
			
			return ((ArithmeticData)left).add(right);
		}

		throw new CannotAddException(left, right);
	};

	public static Data sub(Data left, Data right) throws CannotSubtractException {
		
		if (left instanceof ArithmeticData) {
			
			return ((ArithmeticData)left).sub(right);
		}

		throw new CannotSubtractException(left, right);
	};

	public static Data mul(Data left, Data right) throws CannotMultiplyException {
		
		if (left instanceof ArithmeticData) {
			
			return ((ArithmeticData)left).mul(right);
		}

		throw new CannotMultiplyException(left, right);
	};

	public static Data div(Data left, Data right) throws CannotDivideException {
		
		if (left instanceof ArithmeticData) {
			
			return ((ArithmeticData)left).div(right);
		}

		throw new CannotDivideException(left, right);
	};
}
