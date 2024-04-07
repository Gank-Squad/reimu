package com.git.ganksquad;

import java.io.IOException;
import java.nio.file.Path;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import com.git.ganksquad.ReimuParser.ProgramContext;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public class App 
{

    public static void eval(Path sourceFile) throws ReimuRuntimeException, IOException {

    	CharStream input = CharStreams.fromPath(sourceFile);
    	
    	ReimuLexer pl = new ReimuLexer(input);
    	
    	CommonTokenStream tokens = new CommonTokenStream(pl);
    	
    	ReimuParser parser = new ReimuParser(tokens);
    	
    	ProgramContext result = parser.program();

    	System.out.println(result.expr);

    	result.expr.eval(new ReimuRuntime());
    }


    public static void main( String[] args ) throws ReimuRuntimeException, IOException
    {
    	if(args.length < 1) {

    		System.err.println("Provide source files...");

    		return;
    	}

    	for(String fpath : args) {
    		
    		eval( Path.of(fpath) );
    		
    	}
    }
}
