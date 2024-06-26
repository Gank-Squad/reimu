package com.git.ganksquad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Thread.UncaughtExceptionHandler;
import java.nio.file.Path;
import java.util.Arrays;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tinylog.Logger;
import org.tinylog.configuration.Configuration;

import com.git.ganksquad.ReimuParser.ProgramContext;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.NativeMethod;
import com.git.ganksquad.data.impl.ArrayData;
import com.git.ganksquad.data.impl.IntegerData;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.data.impl.StringData;
import com.git.ganksquad.data.types.PrimitiveType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.data.types.SpecialType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;
import com.git.ganksquad.expressions.BlockExpression;
import com.git.ganksquad.expressions.FunctionDefinitionExpression;
import com.git.ganksquad.expressions.InvokeNativeExpression;
import com.git.ganksquad.expressions.ReturnExpression;

public class App 
{
	
	public static boolean IS_DEBUG = false;
	
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
				FunctionDefinitionExpression.from(SpecialType.VOID,
						"print",
						Arrays.asList("a"), 
						Arrays.asList(SpecialType.ANY), 
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

									@Override
									public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {
										return SpecialType.VOID;
									}
								},  new String[]{"a"})
								)
								)
						),
				FunctionDefinitionExpression.from(SpecialType.VOID,
						"println",
						Arrays.asList("a"), 
						Arrays.asList(SpecialType.ANY), 
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

									@Override
									public ReimuType typeCheck(ReimuTypeResolver resolver)
											throws ReimuCompileException {
										return SpecialType.VOID;
									}
								},  new String[]{"a"})
								)
								)
						),
				FunctionDefinitionExpression.from(PrimitiveType.INT,
						"len",
						Arrays.asList("a"), 
						Arrays.asList(SpecialType.ANY), 
						new BlockExpression(Arrays.asList(
								new ReturnExpression(
								new InvokeNativeExpression(new NativeMethod<Data>() {

									@Override
									public Data call(ReimuRuntime runtime, String... args) throws ReimuRuntimeException {
										Data d = runtime.deref(args[0]);
										if(d instanceof ArrayData) {
											
											return new IntegerData(((ArrayData)d).size());
										}
										if(d instanceof StringData) {
											
											return new IntegerData(((StringData)d).value.length());
										}

										return new IntegerData(0);
									}

									@Override
									public Data call(ReimuRuntime runtime) throws ReimuRuntimeException {
										return new IntegerData(0);
									}

									@Override
									public ReimuType typeCheck(ReimuTypeResolver resolver)
											throws ReimuCompileException {
										return PrimitiveType.INT;
									}
								},  new String[]{"a"})
								)
								)
								)
						),
				FunctionDefinitionExpression.from(SpecialType.VOID,
						"printa",
						Arrays.asList("a"), 
						Arrays.asList(SpecialType.ANY), 
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

									@Override
									public ReimuType typeCheck(ReimuTypeResolver resolver)
											throws ReimuCompileException {
										return SpecialType.VOID;
									}
								},  new String[]{"a"})
								)
								)
						)
				)
				);
		
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
    
    public static void eval(String srcCode, ReimuTypeResolver r, ReimuRuntime rt, BlockExpression globalScope) throws ReimuRuntimeException, IOException, ReimuCompileException {

    	CharStream input = CharStreams.fromString(srcCode);
    	
    	ReimuLexer pl = new ReimuLexer(input);
    	
    	CommonTokenStream tokens = new CommonTokenStream(pl);
    	
    	ReimuParser parser = new ReimuParser(tokens);
    	
    	ProgramContext result = parser.program();


//    	ReimuTypeResolver r = new ReimuTypeResolver();
//    	ReimuRuntime rt = getGlobalRuntime();

//    	BlockExpression globalScope = injectGlobal();
    	
    	
    	result.expr.typeCheckPartial(r);
    	
    	System.err.println( result.expr.evalPartial(rt));
//    	System.out.println(r);
//    	System.out.println(rt);
    	
    }
    
    public static void eval(Path sourceFile) throws ReimuRuntimeException, IOException, ReimuCompileException {

    	CharStream input = CharStreams.fromPath(sourceFile);
    	
    	ReimuLexer pl = new ReimuLexer(input);
    	
    	CommonTokenStream tokens = new CommonTokenStream(pl);
    	
    	ReimuParser parser = new ReimuParser(tokens);
    	
    	ProgramContext result = parser.program();

//    	tokens.getTokens().forEach(x->System.out.println(x));

    	Logger.debug("--------- raw parsed tree -----------");
    	Logger.debug(result.expr);

    	ReimuTypeResolver r = new ReimuTypeResolver();
    	r.pushFile(sourceFile.toString());

    	ReimuRuntime rt = getGlobalRuntime();

    	BlockExpression globalScope = injectGlobal();
    	
    	globalScope.typeCheckPartial(r);
    	result.expr.typeCheck(r);

    	Logger.debug("--------- type checked tree -----------");
    	Logger.debug(result.expr);

    	Logger.debug("--------- global runtime -----------");
    	globalScope.evalPartial(rt);
    	Logger.debug(rt.toString());

    	Logger.debug("--------- output -----------");
    	result.expr.evalPartial(rt);

    	Logger.debug("--------- runtime scopes after tree -----------");
    	Logger.debug(rt.toTreeString(0));
//    	for(ReimuRuntime r : ReimuRuntime.runtimes) {
//    		
//    		System.out.println(r);
//    	}
    }

    public static void readSourceFiles(String[] args) throws ReimuRuntimeException, IOException, ReimuCompileException {
    	
    	for(String fpath : args) {
    		
    		eval( Path.of(fpath) );
    	}
    }
    
    public static void main( String[] args ) throws ReimuRuntimeException, IOException, ReimuCompileException
    {
		Thread.setDefaultUncaughtExceptionHandler(new TinylogHandler());
		
		IS_DEBUG = Boolean.parseBoolean(System.getenv("DEBUG"));

		 if(IS_DEBUG)
         {
             Configuration.set("writer.format", "{class}.{method}({file}:{line})\t{message}");
             Configuration.set("writer.level", "trace");
         }
         else
         {
             Configuration.set("writer.level", "error");
         }


    	if(args.length < 1) {
    		
    		System.out.println("Entering shell:");

    		BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
    		
    		String line = "";
    		
    		ReimuRuntime rt = getGlobalRuntime();
    		ReimuTypeResolver r = new ReimuTypeResolver();
    		BlockExpression globalScope = injectGlobal();
    		globalScope.typeCheckPartial(r);
    		globalScope.evalPartial(rt);
    		
    		while (true) {
    			
    			line = reader.readLine().trim();
    			
    			if (line.equals("exit()")) {
    				break;
    			}
    			if (line.equals("rt")) {
    				System.out.println(rt);
    				continue;
    			}
    			if (line.equals("tc")) {
    				System.out.println(r);
    				continue;
    			}
    			if (line.isEmpty()) {
    				continue;
    			}
    			
    			eval(line, r, rt, globalScope);
    			
    		}
    	}
    	else {
    		
        	for(String fpath : args) {

        		eval( Path.of(fpath) );
        	}
    	}

    	
    }
}
