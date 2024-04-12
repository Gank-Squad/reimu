(identifier) @variable

((identifier) @constant
              (#match? @constant "^[A-Z][A-Z\\d_]*$"))

[
 (boolean)
 ]
@constant

[
 "var"
 ; "func"
 "in"
 ] @keyword

[
 "if"
 "el"
 ] @keyword.conditional

[
 "for"
 "while"
 ] @keyword.repeat

[
 "!"
 "~"
 "--"
 "-"
 ; "-="
 ; "->"
 "="
 "!="
 "*"
 "&"
 ; "&&"
 "+"
 "++"
 ; "+="
 "<"
 "<="
 ; "=="
 ">"
 ">="
 ; "||"
 ] @operator

"," @delimiter
; ";" @delimiter

(string) @string

(integer) @number
(hex_integer) @number
(bin_integer) @number

(type
    array_type: (type) @type)

(primitive_type) @type.builtin


(function_definition
  name: (identifier) @function)

(call_expression
  function: (identifier) @function.call)

(comment) @comment

