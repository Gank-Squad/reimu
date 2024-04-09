/**
 * @file Reimu grammar for tree-sitter
 * @author Minno
 */

// https://tree-sitter.github.io/tree-sitter/creating-parsers#the-first-few-rules

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

    rules: {
        source_file: $ => repeat(choice(
            $.function_definition,
            $.statement
            // TODO: other kinds of definitions
        )),


        function_definition: $ => seq(
            'func',
            $.identifier,
            seq( '(', commaSep($.identifier), ')'),
            $.block
        ),

        statement: $ => choice(
            $.statement_end,
            seq($.expression, $.statement_end),
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

        while: $ => seq(
            'while', '(',
            $.expression,
            ')',
            $.block
        ),
        for: $ => seq(
            'for', '(',
            $.expression,
            $.statement_end,
            $.expression,
            $.statement_end,
            $.expression,
            ')',
            $.block
        ),

        foreach: $ => seq(
            'for', '(',
            $.identifier,
            'in',
            $.expression,
            ')',
            $.block
        ),


        ifelse: $ => seq(
            'if', '(',
            $.expression,
            ')',
            $.block,
            optional(
                repeat(
                    seq(
                        'el', '(',
                        $.expression,
                        ')',
                        $.block))),
            optional(
                seq(
                    'el',
                    $.block)),
        ),

        unary_expression: $ => prec.left(PREC.UNARY,
            seq(
                choice(
                    '!',
                    '-',
                    '--',
                    '++',
                    '~',
                ),
                $.expression)
        ),
        expression: $ => choice(
            $.unary_expression,
            prec.left(PREC.PAREN_DECLARATOR, seq('(', $.expression ,')')),
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
            prec.left(PREC.ASSIGNMENT, seq($.identifier, '=', $.expression)),
            prec.left(PREC.ASSIGNMENT, seq('var', $.identifier, '=', $.expression)),
            prec.left(PREC.CALL, seq($.identifier, '(', commaSep($.expression), ')')),
            $.integer,
            $.hex_integer,
            $.bin_integer,
            $.string,
            $.boolean,
            $.identifier,
            seq($.integer, '..', $.integer),
        ),

        string: $ => /"(\\(u[0-9a-fA-F]{4}|.)|[^\\\"])*"/,

        integer: $ => /(0|[1-9])[0-9_]*/,
        hex_integer: $ => /0x[0-9a-fA-F][0-9a-fA-F_]*/,
        bin_integer: $ => /0b[01][01_]*/,

        boolean: $=> /(true|false)/,

        statement_end: $ => ';',

        identifier: $ => /[a-zA-Z_][a-zA-Z0-9_]*/
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
