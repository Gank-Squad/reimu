grammar Reimu;

@header {
import com.git.ganksquad.expressions.*;
import com.git.ganksquad.data.*;
import com.git.ganksquad.exceptions.*;
}

program returns [Expression expr]
    :  g_program { $expr = BlockExpression.fromList($g_program.value); }
    ;
    
g_program
    returns [List<Expression> value]
    @init { $value = new ArrayList<Expression>(); }
    : (   g_statement  { $value.add($g_statement.value); }
        /* | g_funcdef    { $value.add($g_funcdef.value); } */
      )+
    ;
    
// g_funcdef returns [FuncDefExpr value]
//     : 'function' s1=SYMBOL '(' s2=g_symbol_list ')' '{' s3=g_statement_list '}' 
//       { $value = FuncDefExpr.from($s1.text, $s2.value, $s3.value); }
//     ;

g_statement_list returns [List<Expression> value] 
    @init { $value = new ArrayList<Expression>(); }
    : ( el+=g_statement )*   { for(var e : $el) $value.add(e.value); }
    ;
    
g_statement returns [Expression value]
    : STATEMENT_END         { $value = NoneExpression.get(); }
    | g_expr STATEMENT_END  { $value = $g_expr.value; }
//    | g_ifelse              { $value = $g_ifelse.value; }
//    | g_foreach             { $value = $g_foreach.value; }
    ;

g_expr_list returns [List<Expression> value] 
    @init { $value = new ArrayList<Expression>(); }
    : ( el+=g_expr ( ',' el+=g_expr )* )?   { for(var e : $el) $value.add(e.value); }
    ;

g_expr returns [Expression value]
    : '(' g_expr ')'                { $value = $g_expr.value; }
    | e1=g_expr '+' e2=g_expr       { $value = ArithmeticExpression.add($e1.value, $e2.value); }
    | e1=g_expr '++' e2=g_expr      { $value = ArithmeticExpression.add($e1.value, $e2.value); }
    | e1=g_expr '-' e2=g_expr       { $value = ArithmeticExpression.sub($e1.value, $e2.value); }
    | e1=g_expr '*' e2=g_expr       { $value = ArithmeticExpression.mul($e1.value, $e2.value); }
    | e1=g_expr '/' e2=g_expr       { $value = ArithmeticExpression.div($e1.value, $e2.value); }
    | '-' e2=g_expr                 { $value = ArithmeticExpression.sub(IntegerLiteral.zero(), $e2.value);}
    | e1=g_expr '==' e2=g_expr      { $value = CompareExpression.eq($e1.value, $e2.value); }
    | e1=g_expr '!=' e2=g_expr      { $value = CompareExpression.neq($e1.value, $e2.value); }
    | e1=g_expr '<' e2=g_expr       { $value = CompareExpression.lt($e1.value, $e2.value); }
    | e1=g_expr '<=' e2=g_expr      { $value = CompareExpression.lteq($e1.value, $e2.value); }
    | e1=g_expr '>' e2=g_expr       { $value = CompareExpression.gt($e1.value, $e2.value); }
    | e1=g_expr '>=' e2=g_expr      { $value = CompareExpression.gteq($e1.value, $e2.value); }
//    | SYMBOL '=' e1=g_expr          { $value = AssignExpr.from($SYMBOL.text, $e1.value); }
//    | SYMBOL '(' g_expr_list ')'    { $value = InvokeFunc.from($SYMBOL.text, $g_expr_list.value); }
    | g_value                       { $value = $g_value.value; }
    ;

g_value returns [Expression value]
    : INTEGER { $value = IntegerLiteral.fromString($INTEGER.text); } 
    | STRING  { $value = StringLiteral.fromQuotedString($STRING.text); }
    | BOOLEAN { $value = BooleanLiteral.fromString($BOOLEAN.text); }
//    | SYMBOL  { $value = DerefExpr.fromString($SYMBOL.text); }
//    | e1=INTEGER '..' e2=INTEGER      { $value = RangeLiteral.fromString($e1.text, $e2.text); }
    ;

// g_ifelse
//     returns [Expression value]
//     : 'if' '(' g_expr ')' '{' g_statement_list '}'          
//         { $value = IfElseExpr.from($g_expr.value, $g_statement_list.value); }
//     | 'if' '(' g_expr ')' '{' e1=g_statement_list '}' 'else' '{' e2=g_statement_list '}'
//         { $value = IfElseExpr.from($g_expr.value, $e1.value, $e2.value); }
//     
//     ;
    
// g_foreach
//     returns [Expression value]
//     : 'for' '(' SYMBOL 'in' g_expr ')' '{' g_statement_list '}'
//         { $value = ForeachLoopExpr.from($SYMBOL.text, $g_expr.value, $g_statement_list.value); }
//     ;
    
g_symbol_list
    returns [List<String> value]
    @init  { $value = new ArrayList<String>(); }
    : ( el+=SYMBOL ( ',' el+=SYMBOL )* )?          { for(var e : $el) $value.add(e.getText()); }
    ;


STATEMENT_END : ( ';' | '\n' )+ ;


STRING                : '"' ( ESCAPED_CHAR | ~["\\] )* '"' ;
fragment ESCAPED_CHAR : '\\' ( UNICODE | . ) ;
fragment UNICODE      : 'u' HEX HEX HEX HEX ;
fragment HEX          : [0-9a-fA-F] ;

INTEGER      : '0' | [1-9] [0-9]* ;
fragment EXP : [Ee] [+\-]? INTEGER    ;

BOOLEAN: ( 'true' | 'false' ) ;

// MUST COME AFTER KEYWORDS
SYMBOL          : [a-zA-Z_][0-9a-zA-Z_]* ;

WHITESPACE : [ \t\r\n]+ -> skip;
