package com.git.ganksquad.data.types;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.data.impl.UserDefinedData;
import com.git.ganksquad.exceptions.runtime.CannotIndexException;

public class UserDefinedType implements ReimuType {
	
	private String name;
	public LinkedHashMap<String, ReimuType> members;

	public UserDefinedType(String name) {

		this.name = name;
		this.members = new LinkedHashMap<>();
	}
	
	public void populateTypes(List<String> members, List<ReimuType> memberTypes) {
		
		if(members.size() != memberTypes.size()) {
			
			throw new IllegalArgumentException("list size missmatch");
		}

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

		if(other instanceof ResolvingType) {

			ResolvingType t = ((ResolvingType)other);

			if(t.getResolved() != null) {
				return  this.isAssignableFrom(t.getResolved());
			}

			return t.getResolveName().equals(this.name);
		}

		if(!(other instanceof UserDefinedType)) {
			return false;
		}
		
		UserDefinedType t = (UserDefinedType)other;
		
		if(t.name.equals(this.name)) {
			return true;
		}

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
		
		if(other instanceof ResolvingType) {

			ResolvingType t = ((ResolvingType)other);

			if(t.getResolved() != null) {
				return  this.isEqualType(t.getResolved());
			}

			return t.getResolveName().equals(this.name);
		}

		if(!(other instanceof UserDefinedType)) {
			return false;
		}
		
		UserDefinedType t = (UserDefinedType)other;

		if(t.name.equals(this.name)) {
			return true;
		}
		
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


	@Override
	public String toString() {
		return this.formatToString(
				this.name, 
				members.entrySet()
					.stream()
					.map(x -> {
						
						if(x.getValue() instanceof UserDefinedType) {

							String y = ((UserDefinedType)x.getValue()).name;

							return y + ":" + x.getKey();
						}
						
						return x.getValue() + ":" + x.getKey();
					})
					.collect(Collectors.joining(", "))
				);
	}
	
	@Override
	public Data getDefaultEmptyValue() {

		UserDefinedData d = new UserDefinedData(this, name);
		
		try {
			for(Entry<String, ReimuType> e : this.members.entrySet()) {

				if(!(e.getValue() instanceof PrimitiveType)) {

					d.setMember(e.getKey(), NoneData.instance);
				}
				else {
					d.setMember(e.getKey(), e.getValue().getDefaultEmptyValue());
				}
			}

		} catch (CannotIndexException e1) {
			throw new RuntimeException(e1);
		}

		return d;

	}
	
	@Override
	public boolean canBeBool() {
		return true;
	}
	
	@Override
	public String getLookupName() {
		return this.toString();
	}

	@Override
	public void resolve(ReimuTypeResolver resolver) {
	}
}
