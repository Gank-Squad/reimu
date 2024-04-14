package com.git.ganksquad.data.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.git.ganksquad.data.BooleanEvaluable;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.types.AggregateType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.data.types.UserDefinedType;
import com.git.ganksquad.exceptions.runtime.CannotCastException;
import com.git.ganksquad.exceptions.runtime.CannotIndexException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class UserDefinedData implements Data, BooleanEvaluable {
	
	public UserDefinedType type;
	public String name;

	public UserDefinedData(UserDefinedType type, String name) {
		this.type = type;
		this.name = name;
	}
	private Map<String, Data> members = new HashMap<>();

	@Override
	public int getClassKey() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Data getMember(String index) throws CannotIndexException {
		return this.members.get(index);
	}

	public void setMember(String index, Data value) throws CannotIndexException {
		this.members.put(index, value);
	}

	@Override
	public boolean evalAsBool() {
		return true;
	}
	
	@Override
	public String toString() {
		
		return 
				this.name + "[" +

				members.entrySet()
					.stream()
					.map(x -> {
						
						if(x.getValue() instanceof UserDefinedData) {

							String y = ((UserDefinedData)x.getValue()).name;

							return x.getKey() + ":" + y;
						}
						
						return x.getKey() + ":" + x.getValue();
					})
					.collect(Collectors.joining(", "))
					+ ']'
				;
	}

	@Override
	public UserDefinedType getType() {
		return this.type;
	}

	@Override
	public Data castTo(ReimuType newType) throws ReimuRuntimeException {
		if(newType.isEqualType(AggregateType.STRING_TYPE)) {
			return new StringData(this.toString());
		}
		if(this.getType().isAssignableFrom(newType)) {
			return this;
		}
		throw new CannotCastException(this.getType(), newType);
	}
}
