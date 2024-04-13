package com.git.ganksquad.data.impl;

import java.util.HashMap;
import java.util.Map;

import com.git.ganksquad.data.Data;
import com.git.ganksquad.exceptions.runtime.CannotIndexException;

public class UserDefinedData implements Data {
	
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
	public String toString() {
		return this.name +  this.members.toString();
	}
}
