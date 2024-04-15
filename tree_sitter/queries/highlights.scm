(identifier) @variable

((identifier) @constant
              (#match? @constant "^[A-Z][A-Z\\d_]*$"))

[
 (boolean)
 ]
@constant

[
 "var"
 "struct"
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
(char) @character

[
 (integer)
 (hex_integer)
 (bin_integer)
 (short)
 (hex_short)
 (bin_short)
 (long)
 (hex_long)
 (bin_long)
 (float)
 (double)

 ] @number

[
 (type
   aggregate: (aggregate_type))
 ]@type

 (struct_definition
   type_name: (type) @type)

(primitive_type) @type.builtin


(function_definition
  name: (identifier) @function)

(call_expression
  function: (identifier) @function.call)

(comment) @comment

