package com.git.ganksquad;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.tinylog.Logger;

import com.git.ganksquad.data.impl.FunctionData;
import com.git.ganksquad.exceptions.ReimuException;
import com.git.ganksquad.exceptions.compiler.NoneTypeException;
import com.git.ganksquad.exceptions.compiler.RedeclarationException;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.SymbolNotFoundException;
import com.git.ganksquad.expressions.Expression.ReimuType;

public class ReimuTypeResolver {

	/**
	 * The parent runtime used by sub-scopes
	 */
	private ReimuTypeResolver parent = null;

	/**
	 * The runtime symbols
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
    	
    	return r;
    }

    public void declare(String name, ReimuType type) throws ReimuCompileException {
    	
    	if(type == null) {

    		throw ReimuException.nullPtrFromVariableDeclaration(name);
    	}
    	
    	if(type == ReimuType.NONE) {
    		
    		throw new NoneTypeException(String.format("Tried to declare variable %s with None type", name));
    	}
    	
    	if(this.symbolTable.containsKey(name)) { 
    		
    		throw RedeclarationException.fromVariableDeclaration(name);
    	}
    
    	Logger.debug("Declared variable {} with type {}", name, type);

    	this.symbolTable.put(name, type);
    }


    public ReimuType  resolveFunction(String name, int args) throws ReimuCompileException {
    	return resolve(FunctionData.resolveName(name, args));
    }

    public ReimuType  resolve(String name) throws ReimuCompileException {
    	
    	if(this.symbolTable.containsKey(name)) { 
    		
    		return this.symbolTable.get(name);
    	}
    	
    	if(this.parent == null) {
    		throw new SymbolNotFoundException(String.format("Cannot resolve type from variable %s because it has not been declared", name) );
    	}
    
    	return parent.resolve(name);
    }
    
    
    @Override
    public String toString() {
    	
    	String t = "CompileTime" + Arrays.asList(this.symbolTable).toString();

    	return t;
    }
}
