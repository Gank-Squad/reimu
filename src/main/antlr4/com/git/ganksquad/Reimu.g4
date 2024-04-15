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
        | g_struct     { $value.add($g_struct.value); }
      )+
    ;
    
g_funcdef returns [FunctionDefinitionExpression value]
    : g_type s1=SYMBOL '(' s2=g_typed_symbol_list ')' s3=g_block 
      { $value = FunctionDefinitionExpression.from($g_type.value, $s1.text, $s2.valueNames, $s2.valueTypes, $s3.value); }
    ;

g_struct returns [StructDefinitionExpression value]
    : 'struct' s1=SYMBOL '(' s2=g_typed_symbol_list ')'  ENDL
      { $value = StructDefinitionExpression.from($s1.text, $s2.valueNames, $s2.valueTypes); }
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
    | 'return' g_expr ENDL               { $value = new ReturnExpression($g_expr.value); }
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
    : e1=g_expr '.' SYMBOL          { $value = new MemberDerefExpression($e1.value, $SYMBOL.text); }
    | e1=g_expr '[' e2=g_expr ']'   { $value = ArrayIndexExpression.from($e1.value, $e2.value); }
    | '(' g_expr ')'                { $value = $g_expr.value; }
    | '(' g_type ')' e2=g_expr      { $value = CastExpression.from($g_type.value, $e2.value);}
    | '-' e2=g_expr                 { $value = ArithmeticExpression.sub(IntegerLiteral.zero(), $e2.value);}
    | '!' e2=g_expr                 { $value = BooleanArithmetic.not($e2.value);}
    | '++' e2=g_expr                { $value = AssignmentExpression.assign(SpecialType.UNKNOWN, $e2.value, ArithmeticExpression.add($e2.value, new IntegerLiteral(1)));}
    | '--' e2=g_expr                { $value = AssignmentExpression.assign(SpecialType.UNKNOWN, $e2.value, ArithmeticExpression.sub($e2.value, new IntegerLiteral(1)));}
    | e1=g_expr '/' e2=g_expr       { $value = ArithmeticExpression.div($e1.value, $e2.value); }
    | e1=g_expr DIVCAST e2=g_expr   { $value = CastExpression.from(PrimitiveType.getTypeFromString($DIVCAST.text.substring($DIVCAST.text.length() - 1)), 
        PrimitiveType.getTypeFromString($DIVCAST.text.substring(0,1)) == null ? ArithmeticExpression.div($e1.value, $e2.value) : 
        ArithmeticExpression.div(
            CastExpression.from(PrimitiveType.getTypeFromString($DIVCAST.text.substring(0,1)), $e1.value),
            CastExpression.from(PrimitiveType.getTypeFromString($DIVCAST.text.substring(0,1)), $e2.value))); }

    | e1=g_expr '*' e2=g_expr       { $value = ArithmeticExpression.mul($e1.value, $e2.value); }
    | e1=g_expr MULCAST e2=g_expr   { $value = CastExpression.from(PrimitiveType.getTypeFromString($MULCAST.text.substring($MULCAST.text.length() - 1)), 
        PrimitiveType.getTypeFromString($MULCAST.text.substring(0,1)) == null ? ArithmeticExpression.mul($e1.value, $e2.value) : 
        ArithmeticExpression.mul(
            CastExpression.from(PrimitiveType.getTypeFromString($MULCAST.text.substring(0,1)), $e1.value),
            CastExpression.from(PrimitiveType.getTypeFromString($MULCAST.text.substring(0,1)), $e2.value))); }

    | e1=g_expr '%' e2=g_expr       { $value = ArithmeticExpression.mod($e1.value, $e2.value); }
    | e1=g_expr MODCAST e2=g_expr   { $value = CastExpression.from(PrimitiveType.getTypeFromString($MODCAST.text.substring($MODCAST.text.length() - 1)), 
        PrimitiveType.getTypeFromString($MODCAST.text.substring(0,1)) == null ? ArithmeticExpression.mod($e1.value, $e2.value) : 
        ArithmeticExpression.mod(
            CastExpression.from(PrimitiveType.getTypeFromString($MODCAST.text.substring(0,1)), $e1.value),
            CastExpression.from(PrimitiveType.getTypeFromString($MODCAST.text.substring(0,1)), $e2.value))); }

    | e1=g_expr '+' e2=g_expr     { $value = ArithmeticExpression.add($e1.value, $e2.value); }
    | e1=g_expr ADDCAST e2=g_expr   { $value = CastExpression.from(PrimitiveType.getTypeFromString($ADDCAST.text.substring($ADDCAST.text.length() - 1)), 
                                        PrimitiveType.getTypeFromString($ADDCAST.text.substring(0,1)) == null ? ArithmeticExpression.add($e1.value, $e2.value) : 
                                        ArithmeticExpression.add(
                                            CastExpression.from(PrimitiveType.getTypeFromString($ADDCAST.text.substring(0,1)), $e1.value),
                                            CastExpression.from(PrimitiveType.getTypeFromString($ADDCAST.text.substring(0,1)), $e2.value))); }

    | e1=g_expr '-' e2=g_expr       { $value = ArithmeticExpression.sub($e1.value, $e2.value); }
    | e1=g_expr SUBCAST e2=g_expr   { $value = CastExpression.from(PrimitiveType.getTypeFromString($SUBCAST.text.substring($SUBCAST.text.length() - 1)), 
        PrimitiveType.getTypeFromString($SUBCAST.text.substring(0,1)) == null ? ArithmeticExpression.sub($e1.value, $e2.value) : 
        ArithmeticExpression.sub(
            CastExpression.from(PrimitiveType.getTypeFromString($SUBCAST.text.substring(0,1)), $e1.value),
            CastExpression.from(PrimitiveType.getTypeFromString($SUBCAST.text.substring(0,1)), $e2.value))); }

    | e1=g_expr '&' e2=g_expr       { $value = ArithmeticExpression.and($e1.value, $e2.value); }
    | e1=g_expr '^' e2=g_expr       { $value = ArithmeticExpression.xor($e1.value, $e2.value); }
    | e1=g_expr '|' e2=g_expr       { $value = ArithmeticExpression.or($e1.value, $e2.value); }
    | e1=g_expr '==' e2=g_expr      { $value = CompareExpression.eq($e1.value, $e2.value); }
    | e1=g_expr '!=' e2=g_expr      { $value = CompareExpression.neq($e1.value, $e2.value); }
    | e1=g_expr '<' e2=g_expr       { $value = CompareExpression.lt($e1.value, $e2.value); }
    | e1=g_expr '<=' e2=g_expr      { $value = CompareExpression.lteq($e1.value, $e2.value); }
    | e1=g_expr '>' e2=g_expr       { $value = CompareExpression.gt($e1.value, $e2.value); }
    | e1=g_expr '>=' e2=g_expr      { $value = CompareExpression.gteq($e1.value, $e2.value); }
    | e1=g_expr '=' e2=g_expr       { $value = AssignmentExpression.assign(SpecialType.UNKNOWN, $e1.value, $e2.value); }
    | e1=g_expr '+=' e2=g_expr      { $value = AssignmentExpression.assign(SpecialType.UNKNOWN, $e1.value, ArithmeticExpression.add($e1.value, $e2.value)); }
    | e1=g_expr '-=' e2=g_expr      { $value = AssignmentExpression.assign(SpecialType.UNKNOWN, $e1.value, ArithmeticExpression.sub($e1.value, $e2.value)); }
    | e1=g_expr '*=' e2=g_expr      { $value = AssignmentExpression.assign(SpecialType.UNKNOWN, $e1.value, ArithmeticExpression.mul($e1.value, $e2.value)); }
    | e1=g_expr '/=' e2=g_expr      { $value = AssignmentExpression.assign(SpecialType.UNKNOWN, $e1.value, ArithmeticExpression.div($e1.value, $e2.value)); }
    | e1=g_expr '%=' e2=g_expr      { $value = AssignmentExpression.assign(SpecialType.UNKNOWN, $e1.value, ArithmeticExpression.mod($e1.value, $e2.value)); }
    | e1=g_expr '^=' e2=g_expr      { $value = AssignmentExpression.assign(SpecialType.UNKNOWN, $e1.value, ArithmeticExpression.xor($e1.value, $e2.value)); }
    | e1=g_expr '|=' e2=g_expr      { $value = AssignmentExpression.assign(SpecialType.UNKNOWN, $e1.value, ArithmeticExpression.or($e1.value, $e2.value)); }
    | e1=g_expr '&=' e2=g_expr      { $value = AssignmentExpression.assign(SpecialType.UNKNOWN, $e1.value, ArithmeticExpression.and($e1.value, $e2.value)); }
    | e1=g_expr '||' e2=g_expr      { $value = BooleanArithmetic.or($e1.value, $e2.value); }
    | e1=g_expr '&&' e2=g_expr      { $value = BooleanArithmetic.and($e1.value, $e2.value); }
    | SYMBOL '(' g_expr_list ')'    { $value = InvokeFunctionExpression.from($SYMBOL.text, $g_expr_list.value); }
    | g_value                       { $value = $g_value.value; }
    ;

g_value returns [Expression value]
    : INTEGER      { $value = PrimitiveLiteral.fromString(PrimitiveType.INT, $INTEGER.text); } 
    | HEX_INTEGER  { $value = PrimitiveLiteral.fromString(PrimitiveType.INT, $HEX_INTEGER.text, 16); } 
    | BIN_INTEGER  { $value = PrimitiveLiteral.fromString(PrimitiveType.INT, $BIN_INTEGER.text, 2); } 
    | SHORT        { $value = PrimitiveLiteral.fromString(PrimitiveType.SHORT, $SHORT.text); }
    | HEX_SHORT    { $value = PrimitiveLiteral.fromString(PrimitiveType.SHORT, $HEX_SHORT.text, 16); }
    | BIN_SHORT    { $value = PrimitiveLiteral.fromString(PrimitiveType.SHORT, $BIN_SHORT.text, 2); }
    | LONG         { $value = PrimitiveLiteral.fromString(PrimitiveType.LONG, $LONG.text); }
    | HEX_LONG     { $value = PrimitiveLiteral.fromString(PrimitiveType.LONG, $HEX_LONG.text, 16); }
    | BIN_LONG     { $value = PrimitiveLiteral.fromString(PrimitiveType.LONG, $BIN_LONG.text, 2); }
    | FLOAT        { $value = PrimitiveLiteral.fromString(PrimitiveType.FLOAT, $FLOAT.text); } 
    | DOUBLE       { $value = PrimitiveLiteral.fromString(PrimitiveType.DOUBLE, $DOUBLE.text); } 
    | BOOLEAN      { $value = PrimitiveLiteral.fromString(PrimitiveType.BOOLEAN, $BOOLEAN.text); }
    | CHAR         { $value = PrimitiveLiteral.fromString(PrimitiveType.CHAR, $CHAR.text); }
    | STRING       { $value = StringLiteral.fromQuotedString($STRING.text); }
    | SYMBOL       { $value = DerefExpression.fromString($SYMBOL.text); }
    | e1=INTEGER '..' e2=INTEGER        { $value = RangeLiteral.fromString($e1.text, $e2.text); }
    | '[' e=g_expr_list ']'             { $value = ArrayLiteral.from($g_expr_list.value); }
    | SYMBOL '{' e=g_expr_list '}'      { $value = CreateUserDefinedDataExpression.from($SYMBOL.text, $g_expr_list.value); }
    ;


g_type returns [ReimuType value]

    : e=g_type { List<Integer> il = new ArrayList<>(); }  
        (
            ( '[]'            { il.add(-1);                              } ) |
            ( '[' INTEGER ']' { il.add(Integer.parseInt($INTEGER.text)); } )
        )+
        { $value = ArrayType.fromNbrackets($e.value, il); }

    | 'iter' '[' e=g_type  ']' { $value = new IterableType($e.value); }
    | 'var'                    { $value = SpecialType.UNKNOWN; }
    | 'string'                 { $value = AggregateType.STRING_TYPE; }
    | SYMBOL                   { $value = new ResolvingType($SYMBOL.text); }
    | g_primative_type         { $value = $g_primative_type.value; }
    ;

g_primative_type returns [ReimuType value]
    : 'i64'    { $value = PrimitiveType.LONG; }
    | 'long'   { $value = PrimitiveType.LONG; }
    | 'i32'    { $value = PrimitiveType.INT; }
    | 'int'    { $value = PrimitiveType.INT; }
    | 'i16'    { $value = PrimitiveType.SHORT; }
    | 'short'  { $value = PrimitiveType.SHORT; }
    | 'i8'     { $value = PrimitiveType.BYTE; }
    | 'byte'   { $value = PrimitiveType.BYTE; }
    | 'f64'    { $value = PrimitiveType.DOUBLE; }
    | 'double' { $value = PrimitiveType.DOUBLE; }
    | 'f32'    { $value = PrimitiveType.FLOAT; }
    | 'float'  { $value = PrimitiveType.FLOAT; }
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
CHAR                  : '\'' . '\'';
fragment ESCAPED_CHAR : '\\' ( . ) ;
fragment HEX          : [0-9a-fA-F] ;

INTEGER      : '0' | [1-9] [0-9_]*    { setText(getText().replace("_","")); };
HEX_INTEGER  : '0x' HEX (HEX | '_')*  { setText(getText().replace("_","")); };
BIN_INTEGER  : '0b' [01] [01_]*       { setText(getText().replace("_","")); };

SHORT        : INTEGER 's'            { setText(getText().substring(0, getText().length() - 1)); };
HEX_SHORT    : HEX_INTEGER 's'        { setText(getText().substring(0, getText().length() - 1)); };
BIN_SHORT    : BIN_INTEGER 's'        { setText(getText().substring(0, getText().length() - 1)); };

LONG         : INTEGER 'L'            { setText(getText().substring(0, getText().length() - 1)); };
HEX_LONG     : HEX_INTEGER 'L'        { setText(getText().substring(0, getText().length() - 1)); };
BIN_LONG     : BIN_INTEGER 'L'        { setText(getText().substring(0, getText().length() - 1)); };

FLOAT : INTEGER '.' INTEGER 'f'       { setText(getText().substring(0, getText().length() - 1)); };
DOUBLE: INTEGER '.' INTEGER    ;

BOOLEAN: ( 'true' | 'false' ) ;

fragment PRIMITIVES: [bsilfdcB];
ADDCAST : PRIMITIVES? '+' PRIMITIVES;
SUBCAST : PRIMITIVES? '-' PRIMITIVES;
MULCAST : PRIMITIVES? '*' PRIMITIVES;
DIVCAST : PRIMITIVES? '/' PRIMITIVES;
MODCAST : PRIMITIVES? '%' PRIMITIVES;

// MUST COME AFTER KEYWORDS
SYMBOL          : [a-zA-Z_][0-9a-zA-Z_]* ;

LINE_COMMENT: '//' ~( '\r' | '\n' )*  -> skip;
BLOCK_COMMENT: '/*' ( ~'*' )* '*'+ ( ~[/*] ( ~'*' )* '*' )* '/' -> skip;
WHITESPACE : [ \t\r\n]+ -> skip;

