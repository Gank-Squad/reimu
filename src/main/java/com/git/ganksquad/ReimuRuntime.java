package com.git.ganksquad;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.crypto.Data;

public class ReimuRuntime {
    
    public Map<String, Data> symbolTable = new HashMap<>();

    public ReimuRuntime() {
    	
    }
    
    public ReimuRuntime subScope(Map<String, Data> bindings) {
    	
    	ReimuRuntime r = this.subScope();

    	r.symbolTable.putAll(bindings);
    	
    	return r;
    }

    public ReimuRuntime subScope() {
    	
    	ReimuRuntime r = new ReimuRuntime();
    	
    	r.symbolTable.putAll(this.symbolTable);
    	
    	return r;
    }

    @Override
    public String toString() {
        return this.symbolTable.entrySet().stream()
                .map(entry -> entry.getKey() + " = " + entry.getValue().toString())
                .collect(Collectors.joining("; "));
    }
}


