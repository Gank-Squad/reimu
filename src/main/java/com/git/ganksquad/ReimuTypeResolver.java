package com.git.ganksquad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinylog.Logger;

import com.git.ganksquad.data.types.SpecialType;
import com.git.ganksquad.data.types.FunctionType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.ReimuException;
import com.git.ganksquad.exceptions.compiler.NoneTypeException;
import com.git.ganksquad.exceptions.compiler.RedeclarationException;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.SymbolNotFoundException;

public class ReimuTypeResolver {

	
	/**
	 * Runtime history, used for debugging
	 */
	public List<ReimuTypeResolver> children = new ArrayList<>();

	/**
	 * The parent resolver used by sub-scopes
	 */
	private ReimuTypeResolver parent = null;

	/**
	 * The symbol types 
	 */
    private Map<String, ReimuType> symbolTable = new HashMap<>();
    
    public ReimuTypeResolver() {
    	this(null);	
    }

    public ReimuTypeResolver(ReimuTypeResolver p) {

    	this.parent = p;
    }
    
    /**
     * Creates a new sub-scope from the current runtime
     * @return The new scope
     */
    public ReimuTypeResolver subScope() {
    	
    	ReimuTypeResolver r = new ReimuTypeResolver(this);
    	
    	this.children.add(r);
    	
    	return r;
    }

    public void declareFunction(String name, FunctionType t) throws ReimuCompileException {
    	declare(ReimuNameResolver.getFunctionName(name, t.argumentTypes), t);
    }
    public void declare(String name, ReimuType type) throws ReimuCompileException {
    	
    	if(type == null) {

    		throw ReimuException.nullPtrFromVariableDeclaration(name);
    	}
    	
    	if(type == SpecialType.VOID) {
    		
    		throw new NoneTypeException(String.format("Tried to declare variable %s with None type", name));
    	}
    	
    	if(this.symbolTable.containsKey(name)) { 
    		
    		throw RedeclarationException.fromVariableDeclaration(name);
    	}
    
    	Logger.debug("Declared variable {} with type {}", name, type);

    	this.symbolTable.put(name, type);
    }


    public ReimuType  resolveFunction(String name, List<ReimuType> args) throws ReimuCompileException {
    	return resolve(ReimuNameResolver.getFunctionName(name, args));
    }

    public ReimuType  resolve(String name) throws ReimuCompileException {
    	
    	if(this.symbolTable.containsKey(name)) { 
    		
    		return this.symbolTable.get(name);
    	}
    	
    	if(this.parent == null) {
    		
    		Logger.debug(this.symbolTable);
    		throw new SymbolNotFoundException(String.format("Cannot resolve type from variable %s because it has not been declared", name) );
    	}
    
    	return parent.resolve(name);
    }
    
    
    @Override
    public String toString() {
    	
    	String t = "CompileTime" + Arrays.asList(this.symbolTable).toString();

    	return t;
    }

    public String toTreeString(int depth) {
    	
    	StringBuilder sb = new StringBuilder();
    	
    	for(int i = 0; i < depth; i++) 
    		sb.append("\t");

    	sb.append(this.toString()).append('\n');
    	
    	for(ReimuTypeResolver rt : this.children) {
    		
    		sb.append(rt.toTreeString(depth+1)).append('\n');
    	}

    	return sb.toString();
    }
}
