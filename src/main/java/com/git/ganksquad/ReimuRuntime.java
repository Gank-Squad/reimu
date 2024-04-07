package com.git.ganksquad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.exceptions.ReimuRuntimeException;
import com.git.ganksquad.exceptions.SymbolNotFoundException;

public class ReimuRuntime {
	
	public static List<ReimuRuntime> runtimes = new ArrayList<>();
    
	private ReimuRuntime parent = null;

    private Map<String, Data> symbolTable = new HashMap<>();

    public ReimuRuntime() {
    	
    	runtimes.add(this);
    }

    public ReimuRuntime(ReimuRuntime parent) {

    	this();

    	this.parent = parent;
    }
    
    public ReimuRuntime subScope(Map<String, Data> bindings) {
    	
    	ReimuRuntime r = this.subScope();

    	r.symbolTable.putAll(bindings);
    	
    	return r;
    }

    public ReimuRuntime subScope() {
    	
    	ReimuRuntime r = new ReimuRuntime(this);
    	
    	return r;
    }
    
    public void declare(String name, Data value) throws ReimuRuntimeException {
    	
    	if(value == null) {

    		throw new ReimuRuntimeException("Tried to assign value as null!");
    	}
    
    	this.symbolTable.put(name, value);
    }

    public void declare(String name) throws ReimuRuntimeException {
    
    	this.declare(name, NoneData.instance);
    }

    public void assign(String name, Data value) throws ReimuRuntimeException {
    
    	if(value == null) {

    		throw new ReimuRuntimeException("Tried to assign value as null!");
    	}

    	if(this.symbolTable.containsKey(name)) {
    		
    		this.symbolTable.put(name, value);
    		
    		return;
    	}
    	
    	if(this.parent == null) {
    		
    		throw new SymbolNotFoundException(String.format("Cannot assgin to symbol with name %s, since it does not exist", name));
    	}

    	this.parent.assign(name, value);
    }

    public Data deref(String name) throws SymbolNotFoundException {
    	
    	if(this.symbolTable.containsKey(name)) {
    		
    		return this.symbolTable.get(name);
    	}
    	
    	if(this.parent == null) {
    		
    		throw new SymbolNotFoundException(String.format("Cannot deref symbol with name %s, since it does not exist", name));
    	}
    	
    	return parent.deref(name);
    }

    @Override
    public String toString() {
    	
    	String t = "Runtime" + Arrays.asList(this.symbolTable).toString();

    	return t;
    }
}


