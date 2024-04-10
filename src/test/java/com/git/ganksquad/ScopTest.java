package com.git.ganksquad;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.git.ganksquad.exceptions.RedeclarationException;
import com.git.ganksquad.exceptions.ReimuRuntimeException;
import com.git.ganksquad.exceptions.SymbolNotFoundException;

public class ScopTest {

    @Test
    public void testForLoopScopes() throws IOException
    {

    	try {
    		// using i after the loop should throw an error
    		App.eval("for(var i = 0; i < 10; i = i + 1) {"
    				+ "    i;"
    				+ "}"
    				+ "i;"
    				+ "");
    		assertTrue(false);
    	} catch (ReimuRuntimeException e) {
    		assertTrue(e instanceof SymbolNotFoundException);
    	}
    	

    	try {
    		// if we declare i above the loop, we can use it after
    		App.eval("var i;" 
    				+ "for(i = 0; i < 10; i = i + 1) {"
    				+ "    i;"
    				+ "}"
    				+ "i;"
    				+ "");
    	} catch (ReimuRuntimeException e) {
    		assertTrue(false);
    	}
    }

    @Test
    public void testBlockExpressionScopes() throws IOException
    {
    	try {
    		// cannot declare a twice
    		App.eval("var a; var a;");
    		assertTrue(false);
    	} catch (ReimuRuntimeException e) {
    		assertTrue(e instanceof RedeclarationException);
    	}

    	try {
    		// shadowing is valid
    		App.eval("var a; { var a; }");
    	} catch (ReimuRuntimeException e) {
    		assertTrue(false);
    	}

    	try {
    		// shadowing is valid
    		App.eval("var a; { var a; { var a;} }");
    	} catch (ReimuRuntimeException e) {
    		assertTrue(false);
    	}

    	try {
    		// a should be contained in the block
    		App.eval("{ var a; } var a;");
    	} catch (ReimuRuntimeException e) {
    		assertTrue(false);
    	}
    }

}
