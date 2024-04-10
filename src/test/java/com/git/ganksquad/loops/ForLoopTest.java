package com.git.ganksquad.loops;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.git.ganksquad.App;
import com.git.ganksquad.exceptions.ReimuRuntimeException;
import com.git.ganksquad.exceptions.SymbolNotFoundException;

public class ForLoopTest {

    @Test
    public void testForLoop() throws IOException
    {

    	try {
    		// basic c for loop
    		App.eval("for(var i = 0; i < 10; i = i + 1) {"
    				+ "    i;"
    				+ "}"
    				+ "");
    	} catch (ReimuRuntimeException e) {
    		assertTrue(false);
    	}
    	

    	try {
    		// missing assignment part
    		App.eval( "var i = 0;"
    				+ "for(; i < 10; i = i + 1) {"
    				+ "    i;"
    				+ "}"
    				+ "");
    	} catch (ReimuRuntimeException e) {
    		assertTrue(false);
    	}
    	
    	
    	try {
    		// missing both parts
    		App.eval( "var i = 0;"
    				+ "for(; i < 10;) {"
    				+ "    i = i + 1;"
    				+ "}"
    				+ "");
    	} catch (ReimuRuntimeException e) {
    		assertTrue(false);
    	}
    }
}
