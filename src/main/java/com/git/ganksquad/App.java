package com.git.ganksquad;

import java.io.IOException;
import java.nio.file.Path;
import java.util.BitSet;
import java.util.Iterator;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import com.git.ganksquad.ReimuParser.ProgramContext;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public class App 
{

    public static void eval(String srcCode) throws ReimuRuntimeException, IOException {

    	CharStream input = CharStreams.fromString(srcCode);
    	
    	ReimuLexer pl = new ReimuLexer(input);
    	
    	CommonTokenStream tokens = new CommonTokenStream(pl);
    	
    	ReimuParser parser = new ReimuParser(tokens);
    	
    	ProgramContext result = parser.program();

    	ReimuRuntime rt = new ReimuRuntime();

    	try {
			
    		result.expr.eval(rt);

		} catch (ReimuRuntimeException e) {

			System.err.println(e);
		}
    	
    }
    public static void eval(Path sourceFile) throws ReimuRuntimeException, IOException {

    	CharStream input = CharStreams.fromPath(sourceFile);
    	
    	ReimuLexer pl = new ReimuLexer(input);
    	
    	CommonTokenStream tokens = new CommonTokenStream(pl);
    	
    	ReimuParser parser = new ReimuParser(tokens);
    	
    	ProgramContext result = parser.program();

    	tokens.getTokens().forEach(x->System.out.println(x));
    	System.out.println(result.expr);

    	ReimuRuntime rt = new ReimuRuntime();

    	try {
			
    		result.expr.eval(rt);

		} catch (ReimuRuntimeException e) {

			System.err.println(e);
		}
    	
    	for(ReimuRuntime r : ReimuRuntime.runtimes) {
    		
    		System.out.println(r);
    	}
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
