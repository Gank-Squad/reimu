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


/**
 * The main runtime for Reimu
 * 
 * This tracks symbols and handles scoping
 */
public class ReimuRuntime {
	
	/**
	 * Runtime history, used for debugging
	 */
	public static List<ReimuRuntime> runtimes = new ArrayList<>();
    
	/**
	 * The parent runtime used by sub-scopes
	 */
	private ReimuRuntime parent = null;

	/**
	 * The runtime symbols
	 */
    private Map<String, Data> symbolTable = new HashMap<>();

    public ReimuRuntime() {
    	
    	runtimes.add(this);
    }

    public ReimuRuntime(ReimuRuntime parent) {

    	this();

    	this.parent = parent;
    }
    
    /**
     * Creates a new sub-scope from the current runtime
     * @param bindings Variables which should be put into the new scope
     * @return The new scope
     */
    public ReimuRuntime subScope(Map<String, Data> bindings) {
    	
    	ReimuRuntime r = this.subScope();

    	r.symbolTable.putAll(bindings);
    	
    	return r;
    }

    /**
     * Creates a new sub-scope from the current runtime
     * @return The new scope
     */
    public ReimuRuntime subScope() {
    	
    	ReimuRuntime r = new ReimuRuntime(this);
    	
    	return r;
    }
    
    /**
     * Declare a symbol with the given value
     * @param name The symbol name 
     * @param value The value
     * @throws ReimuRuntimeException thrown if the value is Null
     */
    public void declare(String name, Data value) throws ReimuRuntimeException {
    	
    	if(value == null) {

    		throw new ReimuRuntimeException("Tried to assign value as null!");
    	}
    
    	this.symbolTable.put(name, value);
    }

    /**
     * Declare a symbol with the value of {@link NoneData}
     * @param name The symbol name 
     */
    public void declare(String name) throws ReimuRuntimeException {
    
    	this.declare(name, NoneData.instance);
    }

    /**
     * Assign data to the symbol in the parent or current scope.
     * 
     * This should only be used if the variable has already been declared using {@link ReimuRuntime#declare}
     * 
     * @param name The symbol name
     * @param value The value to assign
     * @throws ReimuRuntimeException thrown if value is null or the symbol cannot be found
     */
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

    /**
     * Get the value for the given symbol, from the current or parent scope
     * @param name The symbol name
     * @return The data held by the symbol
     * @throws SymbolNotFoundException thrown if the symbol cannot be found
     */
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


