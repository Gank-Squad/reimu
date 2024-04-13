package com.git.ganksquad.data.types;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class UserDefinedType implements ReimuType {
	
	private String name;
	public LinkedHashMap<String, ReimuType> members;

	public UserDefinedType(String name) {

		this.name = name;
	}
	
	public void populateTypes(List<String> members, List<ReimuType> memberTypes) {
		
		if(members.size() != memberTypes.size()) {
			
			throw new IllegalArgumentException("list size missmatch");
		}

		this.members = new LinkedHashMap<>();

		Iterator<String> s1 = members.iterator();
		Iterator<ReimuType> s2 = memberTypes.iterator();

		while (s1.hasNext()) {
			this.members.put(s1.next(), s2.next());
		}
	}

	
	public String getName() {
		return this.name;
	}
	

	@Override
	public boolean isAssignableFrom(ReimuType other) {

		if(!(other instanceof UserDefinedType)) {
			return false;
		}
		
		UserDefinedType t = (UserDefinedType)other;
		
		if(this.members.size() != t.members.size()) {
			return false;
		}
		
		Iterator<ReimuType> t1 = this.members.values().iterator();
		Iterator<ReimuType> t2 = t.members.values().iterator();
		
		while(t1.hasNext()) {
			
			if(!t1.next().isAssignableFrom(t2.next())) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean isEqualType(ReimuType other) {

		if(!(other instanceof UserDefinedType)) {
			return false;
		}
		
		UserDefinedType t = (UserDefinedType)other;
		
		if(this.members.size() != t.members.size()) {
			return false;
		}
		
		Iterator<ReimuType> t1 = this.members.values().iterator();
		Iterator<ReimuType> t2 = t.members.values().iterator();
		
		while(t1.hasNext()) {
			
			if(!t1.next().isEqualType(t2.next())) {
				return false;
			}
		}

		return true;
	}


	private static boolean preventOverflow = false;
	@Override
	public String toString() {
		if(preventOverflow) {
			return this.getClass().getName();
		}
		preventOverflow = true;
		String s = this.formatToString(this.name, members);
		preventOverflow = false;
		return s;
	}
}
