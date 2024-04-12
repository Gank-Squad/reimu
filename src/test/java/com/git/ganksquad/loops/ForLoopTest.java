package com.git.ganksquad.loops;

import java.io.IOException;

import org.junit.Test;

import com.git.ganksquad.App;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class ForLoopTest {

    @Test
    public void testForLoop() throws IOException, ReimuRuntimeException, ReimuCompileException
    {

    	// basic c for loop
    	App.eval("for(var i = 0; i < 10; i = i + 1) {"
    			+ "    i;"
    			+ "}"
    			+ "");


    	// missing assignment part
    	App.eval( "var i = 0;"
    			+ "for(; i < 10; i = i + 1) {"
    			+ "    i;"
    			+ "}"
    			+ "");


    	// missing both parts
    	App.eval( "var i = 0;"
    			+ "for(; i < 10;) {"
    			+ "    i = i + 1;"
    			+ "}"
    			+ "");
    }
}
