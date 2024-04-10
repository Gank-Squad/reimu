(identifier) @variable

((identifier) @constant
              (#match? @constant "^[A-Z][A-Z\\d_]*$"))

[
 "var"
 "func"
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

(call_expression
  function: (identifier) @function)


(comment) @comment
