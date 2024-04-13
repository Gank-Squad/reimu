



struct test(int a, int b);
struct test2(test a, int b);



test2 a = new test2{ new test {0, 1}, 5 };

a.a.a = 5;

println((string)a.a.a);

var b = [1 , 2, 3, 4, 5];

b[0] = 100;

println((string)b);
/*
 BlockExpression[
    com.git.ganksquad.expressions.StructDefinitionExpression@5034c75a, 

    AssignmentExpression[
    com.git.ganksquad.data.types.ResolvingType@7dc7cbad, a, ArrayLiteral[[PrimitiveLiteral[INT, 1], PrimitiveLiteral[INT, 2]]], declaration: true]]





 */


