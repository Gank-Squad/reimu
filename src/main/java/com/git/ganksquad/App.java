package com.git.ganksquad;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tinylog.Logger;

import com.git.ganksquad.ReimuParser.ProgramContext;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.NativeMethod;
import com.git.ganksquad.data.impl.FunctionData;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;
import com.git.ganksquad.expressions.BlockExpression;
import com.git.ganksquad.expressions.FunctionDefinitionExpression;
import com.git.ganksquad.expressions.InvokeNativeExpression;

public class App 
{
	private static class TinylogHandler implements UncaughtExceptionHandler
	{
		@Override
		public void uncaughtException(Thread thread, Throwable ex)
		{
			Logger.error("! === Unhandled Exception === !");
			Logger.error(ex);
		}
	}	
	
	public static ReimuRuntime getGlobalRuntime() throws ReimuRuntimeException {

		ReimuRuntime rt = new ReimuRuntime();

		return rt;
	}

	public static BlockExpression injectGlobal() throws ReimuRuntimeException {
		
		return BlockExpression.fromList(Arrays.asList(
				FunctionDefinitionExpression.from(
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
								},  new String[]{"a"})
								)
								)
						),
				FunctionDefinitionExpression.from(
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
										return NoneData.instance;
									}
								},  new String[]{"a"})
								)
								)
						)
				));
		
	}

    public static void eval(String srcCode) throws ReimuRuntimeException, IOException, ReimuCompileException {

    	CharStream input = CharStreams.fromString(srcCode);
    	
    	ReimuLexer pl = new ReimuLexer(input);
    	
    	CommonTokenStream tokens = new CommonTokenStream(pl);
    	
    	ReimuParser parser = new ReimuParser(tokens);
    	
    	ProgramContext result = parser.program();


    	ReimuTypeResolver r = new ReimuTypeResolver();
    	ReimuRuntime rt = getGlobalRuntime();

    	BlockExpression globalScope = injectGlobal();
    	
    	globalScope.typeCheckPartial(r);
    	result.expr.typeCheck(r);
    	globalScope.evalPartial(rt);
    	result.expr.eval(rt);
    	
    }
    public static void eval(Path sourceFile) throws ReimuRuntimeException, IOException, ReimuCompileException {

    	CharStream input = CharStreams.fromPath(sourceFile);
    	
    	ReimuLexer pl = new ReimuLexer(input);
    	
    	CommonTokenStream tokens = new CommonTokenStream(pl);
    	
    	ReimuParser parser = new ReimuParser(tokens);
    	
    	ProgramContext result = parser.program();

//    	tokens.getTokens().forEach(x->System.out.println(x));
    	Logger.debug(result.expr);


    	ReimuTypeResolver r = new ReimuTypeResolver();
    	ReimuRuntime rt = getGlobalRuntime();

    	BlockExpression globalScope = injectGlobal();
    	
    	globalScope.typeCheckPartial(r);
    	Logger.info(r);
    	result.expr.typeCheck(r);
    	globalScope.evalPartial(rt);
    	Logger.info("--------- output -----------");
    	result.expr.eval(rt);
    	
//    	for(ReimuRuntime r : ReimuRuntime.runtimes) {
//    		
//    		System.out.println(r);
//    	}
    }


    public static void main( String[] args ) throws ReimuRuntimeException, IOException, ReimuCompileException
    {
		Thread.setDefaultUncaughtExceptionHandler(new TinylogHandler());

    	if(args.length < 1) {

    		System.err.println("Provide source files...");

    		return;
    	}

    	for(String fpath : args) {
    		
    		eval( Path.of(fpath) );
    		
    	}
    }
}
