package com.git.ganksquad.expressions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tinylog.Logger;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuLexer;
import com.git.ganksquad.ReimuParser;
import com.git.ganksquad.ReimuParser.ProgramContext;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.data.types.SpecialType;
import com.git.ganksquad.exceptions.compiler.CannotImportFileException;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class ImportExpression implements Expression {
	
	public String path;
	private BlockExpression importedCode = null;

	public ImportExpression(String path) {
		
		this.path = path;
	}
	
	public static ImportExpression from(String path) {
		
		ParseChecks.RequiredNotNull(path);

		return new ImportExpression(ParseChecks.getUnquotedOrFail(path));
	}
	
	private void doImport(String insideFile) throws IOException {
		
		File cur = new File(insideFile).getParentFile();
		
		if(!cur.isDirectory()) {
			throw new IOException(String.format("Cannot find directory of %s", insideFile));
		}
		
		if(this.path.startsWith("./")) {
			
			this.path = new File(cur, this.path.substring(2)).getPath();
		}
		else if (!this.path.startsWith("/")) {
			
			this.path = new File(cur, this.path).getPath();
		}
		
		Logger.debug("CWD {}", cur);
		Logger.debug("File {} is importing file {}", insideFile, path);

    	CharStream input = CharStreams.fromPath(Path.of(this.path));
    	
    	ReimuLexer pl = new ReimuLexer(input);
    	
    	CommonTokenStream tokens = new CommonTokenStream(pl);
    	
    	ReimuParser parser = new ReimuParser(tokens);
    	
    	ProgramContext result = parser.program();

    	importedCode = result.expr;
	}

	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {
		
		this.trace();
		
		try {
			doImport(resolver.currentFile());
		} catch (IOException e) {
			throw new CannotImportFileException(path, e);
		}
		
		resolver.pushFile(path);
		this.importedCode.typeCheckPartial(resolver);
		resolver.popFile();
		
		return SpecialType.VOID;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {

		this.importedCode.evalPartial(reimuRuntime);

		return NoneData.instance;
	}

}
