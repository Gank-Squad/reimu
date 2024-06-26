/**
 * @file Reimu grammar for tree-sitter
 * @author Minno
 *
 * Lots of stuff taken from https://github.com/tree-sitter/tree-sitter-c
 *
 * https://tree-sitter.github.io/tree-sitter/creating-parsers#the-first-few-rules
 */


/// <reference types="tree-sitter-cli/dsl" />
// @ts-check

const PREC = {
  PAREN_DECLARATOR: -10,
  ASSIGNMENT: -2,
  CONDITIONAL: -1,
  DEFAULT: 0,
  LOGICAL_OR: 1,
  LOGICAL_AND: 2,
  INCLUSIVE_OR: 3,
  EXCLUSIVE_OR: 4,
  BITWISE_AND: 5,
  EQUAL: 6,
  RELATIONAL: 7,
  OFFSETOF: 8,
  SHIFT: 9,
  ADD: 10,
  MULTIPLY: 11,
  CAST: 12,
  SIZEOF: 13,
  UNARY: 14,
  CALL: 15,
  FIELD: 16,
  SUBSCRIPT: 17,
};

module.exports = grammar({
    name: 'reimu',

    extras: $ => [
        /\s|\\\r?\n/,
        $.comment,
    ],

    rules: {
        source_file: $ => repeat(choice(
            $.function_definition,
            $.struct_definition,
            $.statement
            // TODO: other kinds of definitions
        )),

        struct_definition: $ => seq(
            'struct',
            field("type_name", $.type),
            seq( '(', commaSep(seq($.type, $.identifier)), ')'),
            $.statement_end
        ),

        function_definition: $ => seq(
            field("return_type", $.type),
            field("name", $.identifier),
            seq( '(', commaSep(seq($.type, $.identifier)), ')'),
            field("body", $.block)
        ),

        statement: $ => choice(
            $.statement_end,
            seq($.expression, $.statement_end),
            seq($.declare, $.statement_end),
            $.block,
            $.ifelse,
            $.while,
            $.for,
            $.foreach
        ),

        block: $ => seq(
            '{',
            repeat($.statement),
            '}'
        ),

        while: $ => choice(
            seq(
                'while', '(',
                $.expression,
                ')',
                $.block
            ),
            seq(
                'while',
                $.expression,
                $.block
            )
        ),

        for: $ => seq(
            'for', '(',
            commaSep($.declare_or_expression),
            $.statement_end,
            $.expression,
            $.statement_end,
            commaSep($.expression),
            ')',
            $.block
        ),

        foreach: $ => choice(
            seq(
                'for', '(',
                $.identifier,
                'in',
                $.expression,
                ')',
                $.block
            ),
            seq(
                'for',
                $.identifier,
                'in',
                $.expression,
                $.block
            )
        ),


        ifelse: $ => seq(
            choice(
                seq('if', '(', $.expression, ')'),
                seq('if', $.expression),
            ),
            $.block,
            optional(
                repeat(
                    seq(
                        choice(
                            seq('el', '(', $.expression, ')'),
                            seq('el', $.expression)
                        ),
                        $.block))),
            optional(
                seq(
                    'el',
                    $.block)),
        ),

        declare: $=> choice(
            seq($.type, $.identifier, optional(seq('=', $.expression))),
        ),

        declare_or_expression: $=> choice(
            $.expression,
            $.declare,
        ),

        unary_expression: $ => prec.left(PREC.UNARY,
            seq(
                choice(
                    '!',
                    '-',
                    '--',
                    '++',
                    '~',
                    seq('(', $.type, ')'),
                ),
                $.expression)
        ),

        call_expression: $ => prec.left(PREC.CALL, seq(
            field("function", $.identifier),
            '(',
            field("arguments", commaSep($.expression)),
            ')'
        )),

        expression: $ => choice(
            $.unary_expression,
            prec.left(PREC.PAREN_DECLARATOR, seq('(', $.expression ,')')),
            prec.left(PREC.PAREN_DECLARATOR, seq('[', commaSep($.expression) ,']')),
            prec.left(PREC.PAREN_DECLARATOR, seq($.expression, '[', commaSep($.expression) ,']')),
            prec.left(PREC.PAREN_DECLARATOR, seq($.expression, '{', commaSep($.expression) ,'}')),
            prec.left(PREC.MULTIPLY, seq($.expression, '/', $.expression)),
            prec.left(PREC.MULTIPLY, seq($.expression, '*', $.expression)),
            prec.left(PREC.MULTIPLY, seq($.expression, '%', $.expression)),
            prec.left(PREC.ADD, seq($.expression, '+', $.expression)),
            prec.left(PREC.ADD, seq($.expression, '-', $.expression)),
            prec.left(PREC.EXCLUSIVE_OR, seq($.expression, '^', $.expression)),
            prec.left(PREC.INCLUSIVE_OR, seq($.expression, '|', $.expression)),
            prec.left(PREC.BITWISE_AND, seq($.expression, '&', $.expression)),
            prec.left(PREC.EQUAL, seq($.expression, '==', $.expression)),
            prec.left(PREC.EQUAL, seq($.expression, '!=', $.expression)),
            prec.left(PREC.RELATIONAL, seq($.expression, '<', $.expression)),
            prec.left(PREC.RELATIONAL, seq($.expression, '<=', $.expression)),
            prec.left(PREC.RELATIONAL, seq($.expression, '>', $.expression)),
            prec.left(PREC.RELATIONAL, seq($.expression, '>=', $.expression)),
            prec.left(PREC.ASSIGNMENT, seq($.expression, '=', $.expression)),
            $.call_expression,
            $.integer,
            $.hex_integer,
            $.bin_integer,
            $.short,
            $.hex_short,
            $.bin_short,
            $.long,
            $.hex_long,
            $.bin_long,
            $.float,
            $.double,
            $.string,
            $.char,
            $.boolean,
            $.identifier,
            seq($.integer, '..', $.integer),
        ),

        type: $ => choice(
            seq(field("array_type", $.type), '[]'),
            field("aggregate", $.aggregate_type),
            $.primitive_type,
            'var'
        ),

        aggregate_type: $=> prec.left(PREC.SUBSCRIPT,
            choice(
                'string',
                $.identifier,
                seq( 'iter', '[', $.type, ']'),
            ),
        ),

        primitive_type: $ => choice(
            'i64',
            'long',
            'i32'  ,
            'int'  ,
            'i16'  ,
            'short'  ,
            'i8'   ,
            'byte'  ,
            'f64'  ,
            'double'  ,
            'f32'  ,
            'float'  ,
            'bool' ,
            'char' ,
            'void'
        ),

        string: $ => /"(\\(u[0-9a-fA-F]{4}|.)|[^\\\"])*"/,

        integer: $ => /(0|[1-9])[0-9_]*/,
        hex_integer: $ => /0x[0-9a-fA-F][0-9a-fA-F_]*/,
        bin_integer: $ => /0b[01][01_]*/,

        char        : $=> /['].[']/,

        short        : $=> seq($.integer, 's'),
        hex_short    : $=> seq($.hex_integer,'s'),
        bin_short    : $=> seq($.bin_integer, 's'),

        long         : $=> seq($.integer,'L'),
        hex_long     : $=> seq($.hex_integer,'L'),
        bin_long     : $=> seq($.bin_integer,'L'),

        float  : $=> seq($.integer, '.', $.integer,'f'),
        double : $=> seq($.integer ,'.', $.integer),

        boolean: $=> /(true|false)/,

        statement_end: $ => ';',

        identifier: $ => /[a-zA-Z_][a-zA-Z0-9_]*/,

        // http://stackoverflow.com/questions/13014947/regex-to-match-a-c-style-multiline-comment/36328890#36328890
        comment: _ => token(choice(
            seq('//', /(\\+(.|\r?\n)|[^\\\n])*/),
            seq(
                '/*',
                /[^*]*\*+([^/*][^*]*\*+)*/,
                '/',
            ),
        )),
    }
});

module.exports.PREC = PREC;

/**
 * Creates a rule to optionally match one or more of the rules separated by a comma
 *
 * @param {Rule} rule
 *
 * @return {ChoiceRule}
 *
 */
function commaSep(rule) {
    return optional(commaSep1(rule));
}

/**
 * Creates a rule to match one or more of the rules separated by a comma
 *
 * @param {Rule} rule
 *
 * @return {SeqRule}
 *
 */
function commaSep1(rule) {
    return seq(rule, repeat(seq(',', rule)));
}
