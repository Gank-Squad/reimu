package com.git.ganksquad;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
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
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.NativeMethod;
import com.git.ganksquad.data.impl.FunctionData;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.exceptions.ReimuRuntimeException;
import com.git.ganksquad.expressions.BlockExpression;
import com.git.ganksquad.expressions.InvokeNativeExpression;

public class App 
{
	public static ReimuRuntime getGlobalRuntime() throws ReimuRuntimeException {

		ReimuRuntime rt = new ReimuRuntime();
		
		populateRuntime(rt);

		return rt;
	}

	public static void populateRuntime(ReimuRuntime rt) throws ReimuRuntimeException {
		
		rt.declareFunction(
				new FunctionData(
						rt,
						"print", 
						Arrays.asList("a"), 
						new BlockExpression(Arrays.asList(
								new InvokeNativeExpression(new NativeMethod<Data>() {
									
									@Override
									public Data call(ReimuRuntime runtime, String... args) throws ReimuRuntimeException {
										System.out.print(runtime.deref(args[0]));
										return NoneData.instance;
									}
									
									@Override
									public Data call(ReimuRuntime runtime) throws ReimuRuntimeException {
										return NoneData.instance;
									}
								},  new String[]{"a"})))));
		rt.declareFunction(
				new FunctionData(
						rt,
						"println", 
						Arrays.asList("a"), 
						new BlockExpression(Arrays.asList(
								new InvokeNativeExpression(new NativeMethod<Data>() {
									
									@Override
									public Data call(ReimuRuntime runtime, String... args) throws ReimuRuntimeException {
										System.out.println(runtime.deref(args[0]));
										return NoneData.instance;
									}
									
									@Override
									public Data call(ReimuRuntime runtime) throws ReimuRuntimeException {
										System.out.println();
										return NoneData.instance;
									}
								},  new String[]{"a"})))));
		
		
		
	}

    public static void eval(String srcCode) throws ReimuRuntimeException, IOException {

    	CharStream input = CharStreams.fromString(srcCode);
    	
    	ReimuLexer pl = new ReimuLexer(input);
    	
    	CommonTokenStream tokens = new CommonTokenStream(pl);
    	
    	ReimuParser parser = new ReimuParser(tokens);
    	
    	ProgramContext result = parser.program();

    	ReimuRuntime rt = getGlobalRuntime();

    	result.expr.eval(rt);
    	
    }
    public static void eval(Path sourceFile) throws ReimuRuntimeException, IOException {

    	CharStream input = CharStreams.fromPath(sourceFile);
    	
    	ReimuLexer pl = new ReimuLexer(input);
    	
    	CommonTokenStream tokens = new CommonTokenStream(pl);
    	
    	ReimuParser parser = new ReimuParser(tokens);
    	
    	ProgramContext result = parser.program();

//    	tokens.getTokens().forEach(x->System.out.println(x));
    	System.out.println(result.expr);

    	ReimuRuntime rt = getGlobalRuntime();

    	try {
			
    		result.expr.eval(rt);

		} catch (ReimuRuntimeException e) {

			System.err.println(e);
		}
    	
//    	for(ReimuRuntime r : ReimuRuntime.runtimes) {
//    		
//    		System.out.println(r);
//    	}
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
