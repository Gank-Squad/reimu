package com.git.ganksquad;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import com.git.ganksquad.exceptions.ReimuException;
import com.git.ganksquad.exceptions.compiler.RedeclarationException;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.SymbolNotFoundException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class ScopTest {

    @Test
    public void testForLoopScopes() throws IOException, ReimuRuntimeException, ReimuCompileException
    {
//    	App.IS_DEBUG = true;
    	try {
    		// using i after the loop should throw an error
    		App.eval("for(i32 i = 0; i < 10; i = i + 1) {"
    				+ "    i;"
    				+ "}"
    				+ "i;"
    				+ "");
    		fail("Should throw exception because i is used outside the scope it was defined");
    	} catch (ReimuException e) {
    		if( e instanceof SymbolNotFoundException ) {
    			
    		}
    		else{
    			throw e;
    		}
    	}
    	

    	// if we declare i above the loop, we can use it after
    	App.eval("i32 i;" 
    			+ "for(i = 0; i < 10; i = i + 1) {"
    			+ "    i;"
    			+ "}"
    			+ "i;"
    			+ "");
    }

    @Test
    public void testBlockExpressionScopes() throws IOException, ReimuRuntimeException, ReimuCompileException
    {
//    	App.IS_DEBUG = true;
    	try {
    		// cannot declare a twice
    		App.eval("i32 a; i32 a;");
    		fail("Should thow exception because variable is already declared");
    	} catch (ReimuException e) {

    		if(!(e instanceof RedeclarationException)) {
    			throw e;
    		}
    	}

    	// shadowing is valid
    	App.eval("i32 a; { i32 a; }");

    	// shadowing is valid
    	App.eval("i32 a; { i32 a; { i32 a;} }");

    	// a should be contained in the block
    	App.eval("{ i32 a; } i32 a;");
    }

}
