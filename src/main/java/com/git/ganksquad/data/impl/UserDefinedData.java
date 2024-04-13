package com.git.ganksquad.data.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.git.ganksquad.data.BooleanEvaluable;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.exceptions.runtime.CannotIndexException;

public class UserDefinedData implements Data, BooleanEvaluable {
	
	public String name;
	public UserDefinedData(String name) {
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
}
