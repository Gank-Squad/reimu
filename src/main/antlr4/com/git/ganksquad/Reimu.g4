grammar Reimu;

@header {
import com.git.ganksquad.expressions.*;
import com.git.ganksquad.exceptions.*;
import com.git.ganksquad.data.types.*;
import com.git.ganksquad.data.*;
}

program returns [BlockExpression expr]
    :  g_program EOF { $expr = BlockExpression.fromList($g_program.value); }
    ;
    
g_program
    returns [List<Expression> value]
    @init { $value = new ArrayList<Expression>(); }
    : (   g_statement  { $value.add($g_statement.value); }
        | g_funcdef    { $value.add($g_funcdef.value); } 
        | g_import     { $value.add($g_import.value); }
      )+
    ;
    
g_funcdef returns [FunctionDefinitionExpression value]
    : g_type s1=SYMBOL '(' s2=g_typed_symbol_list ')' s3=g_block 
      { $value = FunctionDefinitionExpression.from($g_type.value, $s1.text, $s2.valueNames, $s2.valueTypes, $s3.value); }
    ;

g_import returns [ImportExpression value]
    : 'import' STRING ENDL
      { $value = ImportExpression.from($STRING.text); }
    ;

g_statement_list returns [List<Expression> value] 
    @init { $value = new ArrayList<Expression>(); }
    : ( el+=g_statement )*   { for(var e : $el) $value.add(e.value); }
    ;
    
g_statement returns [Expression value]
    : ENDL                               { $value = NoneExpression.get(); }
    | g_expr ENDL                        { $value = $g_expr.value; }
    | g_declare ENDL                     { $value = $g_declare.value; }
    | g_block                            { $value = $g_block.value; }
    | g_ifelse                           { $value = $g_ifelse.value; }
    | g_while                            { $value = $g_while.value; }
    | g_for                              { $value = $g_for.value; }
    | g_foreach                          { $value = $g_foreach.value; }
    ;

g_expr_list returns [List<Expression> value] 
    @init { $value = new ArrayList<Expression>(); }
    : ( el+=g_expr ( ',' el+=g_expr )* )?   { for(var e : $el) $value.add(e.value); }
    ;

g_expr returns [Expression value]
    : '(' g_expr ')'                { $value = $g_expr.value; }
    | '-' e2=g_expr                 { $value = ArithmeticExpression.sub(IntegerLiteral.zero(), $e2.value);}
    | e1=g_expr '[' e2=g_expr ']'   { $value = ArrayIndexExpression.from($e1.value, $e2.value); }
    | e1=g_expr '/' e2=g_expr       { $value = ArithmeticExpression.div($e1.value, $e2.value); }
    | e1=g_expr '*' e2=g_expr       { $value = ArithmeticExpression.mul($e1.value, $e2.value); }
    | e1=g_expr '%' e2=g_expr       { $value = ArithmeticExpression.mod($e1.value, $e2.value); }
    | e1=g_expr '+' e2=g_expr       { $value = ArithmeticExpression.add($e1.value, $e2.value); }
    | e1=g_expr '-' e2=g_expr       { $value = ArithmeticExpression.sub($e1.value, $e2.value); }
    | e1=g_expr '&' e2=g_expr       { $value = ArithmeticExpression.and($e1.value, $e2.value); }
    | e1=g_expr '^' e2=g_expr       { $value = ArithmeticExpression.xor($e1.value, $e2.value); }
    | e1=g_expr '|' e2=g_expr       { $value = ArithmeticExpression.or($e1.value, $e2.value); }
    | e1=g_expr '==' e2=g_expr      { $value = CompareExpression.eq($e1.value, $e2.value); }
    | e1=g_expr '!=' e2=g_expr      { $value = CompareExpression.neq($e1.value, $e2.value); }
    | e1=g_expr '<' e2=g_expr       { $value = CompareExpression.lt($e1.value, $e2.value); }
    | e1=g_expr '<=' e2=g_expr      { $value = CompareExpression.lteq($e1.value, $e2.value); }
    | e1=g_expr '>' e2=g_expr       { $value = CompareExpression.gt($e1.value, $e2.value); }
    | e1=g_expr '>=' e2=g_expr      { $value = CompareExpression.gteq($e1.value, $e2.value); }
    | SYMBOL '=' e1=g_expr          { $value = AssignmentExpression.assign(SpecialType.UNKNOWN, $SYMBOL.text, $e1.value); }
    | SYMBOL '(' g_expr_list ')'    { $value = InvokeFunctionExpression.from($SYMBOL.text, $g_expr_list.value); }
    | g_value                       { $value = $g_value.value; }
    ;

g_value returns [Expression value]
    : INTEGER      { $value = IntegerLiteral.fromString($INTEGER.text); } 
    | HEX_INTEGER  { $value = IntegerLiteral.fromHexString($HEX_INTEGER.text); } 
    | BIN_INTEGER  { $value = IntegerLiteral.fromBinString($BIN_INTEGER.text); } 
    | STRING       { $value = StringLiteral.fromQuotedString($STRING.text); }
    | BOOLEAN      { $value = BooleanLiteral.fromString($BOOLEAN.text); }
    | SYMBOL       { $value = DerefExpression.fromString($SYMBOL.text); }
    | e1=INTEGER '..' e2=INTEGER      { $value = RangeLiteral.fromString($e1.text, $e2.text); }
    | '[' e=g_expr_list ']'      { $value = ArrayLiteral.from($g_expr_list.value); }
    ;


g_type returns [ReimuType value]
    : e=g_type '[]'              { $value = new ArrayType($e.value); }
    | 'var'                    { $value = SpecialType.UNKNOWN; }
    | g_primative_type         { $value = $g_primative_type.value; }
    ;

g_primative_type returns [ReimuType value]
    : 'i64'    { $value = PrimitiveType.LONG; }
    | 'i32'    { $value = PrimitiveType.INT; }
    | 'i16'    { $value = PrimitiveType.SHORT; }
    | 'i8'     { $value = PrimitiveType.BYTE; }
    | 'f64'    { $value = PrimitiveType.DOUBLE; }
    | 'f32'    { $value = PrimitiveType.FLOAT; }
    | 'bool'   { $value = PrimitiveType.BOOLEAN; }
    | 'char'   { $value = PrimitiveType.CHAR; }
    | 'void'   { $value = SpecialType.VOID; }
    ;

g_declare
    returns [AssignmentExpression value]
    : g_type SYMBOL              { $value = AssignmentExpression.declare($g_type.value, $SYMBOL.text, NoneExpression.get()); }
    | g_type SYMBOL '=' g_expr   { $value = AssignmentExpression.declare($g_type.value, $SYMBOL.text, $g_expr.value); }
    ;

g_block
    returns [BlockExpression value]
    : '{' g_statement_list '}'   { $value = BlockExpression.fromList($g_statement_list.value); }
    ;

g_ifelse
    returns [Expression value]
    @init {
        ArrayList<IfExpression> ifs = new ArrayList<>();
    }
    : ( 
          'if' '(' g_expr ')' g_block    { ifs.add(IfExpression.from($g_expr.value, $g_block.value)); }
        | 'if'     g_expr     g_block    { ifs.add(IfExpression.from($g_expr.value, $g_block.value)); } 
      )
      ( 
          'el' '(' g_expr ')' g_block   { ifs.add(IfExpression.from($g_expr.value, $g_block.value)); }
        | 'el'     g_expr     g_block   { ifs.add(IfExpression.from($g_expr.value, $g_block.value)); }
      )*
      ( 
        'el' e=g_block 
      )?    
      { $value = IfElseExpression.fromAndOptimize(ifs, ($e.ctx == null) ? BlockExpression.empty() : $e.value); }
    ;

g_while
    returns [Expression value]
    : 'while' '(' g_expr ')' g_block  { $value = WhileExpression.from($g_expr.value, $g_block.value); }
    | 'while'     g_expr     g_block  { $value = WhileExpression.from($g_expr.value, $g_block.value); }
    ;
    
g_foreach
    returns [Expression value]
    : 'for' '(' SYMBOL 'in' g_expr ')' g_block
        { $value = ForeachLoopExpression.from($SYMBOL.text, $g_expr.value, $g_block.value); }

    | 'for' SYMBOL 'in' g_expr g_block
        { $value = ForeachLoopExpression.from($SYMBOL.text, $g_expr.value, $g_block.value); }
    ;

g_for
    returns [Expression value]
    : 'for' '(' e1=g_for_def_expr ENDL e2=g_expr ENDL e3=g_expr_list ')' g_block
        { $value = ForLoopExpression.from($e1.value, $e2.value, $e3.value, $g_block.value); }

    | 'for' e1=g_for_def_expr ENDL e2=g_expr ENDL e3=g_expr_list g_block
        { $value = ForLoopExpression.from($e1.value, $e2.value, $e3.value, $g_block.value); }
    ;

g_for_def_expr
    returns [List<Expression> value]
    @init  { $value = new ArrayList<Expression>(); }
    : ( el+=g_expr_declare ( ',' el+=g_expr_declare )* )?  { for(var e : $el) $value.add(e.value); }
    ;

g_expr_declare
    returns [Expression value]
    : g_expr     { $value = $g_expr.value; }
    | g_declare  { $value = $g_declare.value; }
    ;
    
g_symbol_list
    returns [List<String> value]
    @init  { $value = new ArrayList<String>(); }
    : ( el+=SYMBOL ( ',' el+=SYMBOL )* )?          { for(var e : $el) $value.add(e.getText()); }
    ;

g_typed_symbol_list
    returns [List<String> valueNames, List<ReimuType> valueTypes]
    @init  { 
        $valueNames = new ArrayList<String>(); 
        $valueTypes = new ArrayList<ReimuType>(); 
        }
    : ( et+=g_type el+=SYMBOL ( ',' et+=g_type el+=SYMBOL )* )?          
      
      { for(var e : $et) $valueTypes.add(e.value); 
        for(var e : $el) $valueNames.add(e.getText()); 
      }
    ;


ENDL : ( ';' )+ ;


STRING                : '"' ( ESCAPED_CHAR | ~["\\] )* '"' ;
fragment ESCAPED_CHAR : '\\' ( UNICODE | . ) ;
fragment UNICODE      : 'u' HEX HEX HEX HEX ;
fragment HEX          : [0-9a-fA-F] ;

INTEGER      : '0' | [1-9] [0-9_]*    { setText(getText().replace("_","")); };
HEX_INTEGER  : '0x' HEX (HEX | '_')*  { setText(getText().replace("_","")); };
BIN_INTEGER  : '0b' [01] [01_]*       { setText(getText().replace("_","")); };

BOOLEAN: ( 'true' | 'false' ) ;


// MUST COME AFTER KEYWORDS
SYMBOL          : [a-zA-Z_][0-9a-zA-Z_]* ;

LINE_COMMENT: '//' ~( '\r' | '\n' )*  -> skip;
BLOCK_COMMENT: '/*' ( ~'*' )* '*'+ ( ~[/*] ( ~'*' )* '*' )* '/' -> skip;
WHITESPACE : [ \t\r\n]+ -> skip;

