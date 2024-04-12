package com.git.ganksquad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.FunctionData;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.compiler.SymbolNotFoundException;
import com.git.ganksquad.exceptions.runtime.NullAssignmentExpression;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;


/**
 * The main runtime for Reimu
 * 
 * This tracks symbols and handles scoping
 */
public class ReimuRuntime {
	
	/**
	 * Runtime history, used for debugging
	 */
	public List<ReimuRuntime> children = new ArrayList<>();
    
	/**
	 * The parent runtime used by sub-scopes
	 */
	private ReimuRuntime parent = null;

	/**
	 * The runtime symbols
	 */
    private Map<String, Data> symbolTable = new HashMap<>();

//    private Map<String, TypeSignature> typeSignatures = new HashMap<>();
//    
//    public static class TypeSignature {
//    	
//    	String type;
//
//    	public VariableData() {
//    		// TODO Auto-generated constructor stub
//    	}
//    	}

    public ReimuRuntime() {
    	
    }

    public ReimuRuntime(ReimuRuntime parent) {

    	this();

    	this.parent = parent;
    }
    

    /**
     * Creates a new sub-scope from the current runtime
     * @return The new scope
     */
    public ReimuRuntime subScope() {
    	
    	ReimuRuntime r = new ReimuRuntime(this);
    	
    	this.children.add(r);
    	
    	return r;
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
     * Creates a new sub-scope from the current runtime and populates the symbol table with the given bindings
     * @param names The names to bind to the value
     * @param values  The values to bind to the names
     * @throws ReimuRuntimeException if there is a different number of names as values
     * @return The new scope
     */
    public ReimuRuntime subScope(Iterator<String> names, Iterator<Data> values) throws ReimuRuntimeException {

    	ReimuRuntime r = this.subScope();

    	while(names.hasNext() && values.hasNext()){
    		
    		r.symbolTable.put(names.next(), values.next());
    	}

    	if(names.hasNext() || values.hasNext()) {
    		
    		throw new ReimuRuntimeException("Cannot bind variables to scope because they missmatch the values");
    	}
    	
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

    		throw NullAssignmentExpression.fromVariableDeclaration(name);
    	}
    	
    	if(this.symbolTable.containsKey(name)) { 
    		
    		throw new ReimuRuntimeException(String.format("%s is already defined, this should be impossible!!!", this.symbolTable));
//    		throw RedeclarationException.fromVariableDeclaration(name);
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
     * Declare a function, this allows for basic function overloading. 
     * All functions must have a different number of params.
     * @param value The {@link FunctionData} to declare
     * @throws ReimuRuntimeException if the value is null
     */
    public void declareFunction(FunctionData value) throws ReimuRuntimeException {
    	
    	if(value == null) {

    		throw NullAssignmentExpression.fromFuncDef();
    	}
    
    	this.symbolTable.put(value.getName(), value);
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

    		throw NullAssignmentExpression.fromVariableAssignment(name);
    	}

    	if(this.symbolTable.containsKey(name)) {
    		
    		this.symbolTable.put(name, value);
    		
    		return;
    	}
    	
    	if(this.parent == null) {
    		
    		throw new ReimuRuntimeException(String.format("Symbol %s was not defined, this should be impossible!!!", name));
    	}

    	this.parent.assign(name, value);
    }

    /**
     * Get the value for the given symbol, from the current or parent scope
     * @param name The symbol name
     * @return The data held by the symbol
     * @throws SymbolNotFoundException thrown if the symbol cannot be found
     */
    public Data deref(String name) throws ReimuRuntimeException {
    	
    	if(this.symbolTable.containsKey(name)) {
    		
    		return this.symbolTable.get(name);
    	}
    	
    	if(this.parent == null) {
    		
    		throw new ReimuRuntimeException(String.format("Cannot deref Symbol %s because it was not defined, this should be impossible!!!", name));
    	}
    	
    	return parent.deref(name);
    }
    
    
    /**
     * Get a function. This requires the number of arguments to allow for function overloading.
     * @param name The function name
     * @param paramCount The param count
     * @return The function data
     * @throws SymbolNotFoundException If the function was not found
     */
    public FunctionData derefFunction(String name, List<ReimuType> argTypes) throws ReimuRuntimeException {
    	
    	Object d = this.symbolTable.get(ReimuNameResolver.getFunctionName(name, argTypes));
    	
    	if(d != null) {
    		
    		return (FunctionData)d;
    	}
    	
    	if(this.parent == null) {
    		
    		throw new ReimuRuntimeException(String.format("Symbol %s was not defined, this should be impossible!!!", name));
    	}
    	
    	return (FunctionData)parent.derefFunction(name, argTypes);
    }

    @Override
    public String toString() {
    	
    	String t = "Runtime" + Arrays.asList(this.symbolTable).toString();

    	return t;
    }

    public String toTreeString(int depth) {
    	
    	StringBuilder sb = new StringBuilder();
    	
    	for(int i = 0; i < depth; i++) 
    		sb.append("\t");

    	sb.append(this.toString()).append('\n');
    	
    	for(ReimuRuntime rt : this.children) {
    		
    		sb.append(rt.toTreeString(depth+1)).append('\n');
    	}

    	return sb.toString();
    }
}


