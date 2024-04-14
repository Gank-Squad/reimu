

struct Node(int a, int b);


void add(Node a, Node b) {

    a.a += b.a;
    a.b += b.b;
}


Node a = Node{5, 10};
Node b = Node{5, 10};

println((string)a);
println((string)b);

add(a, b);

println((string)a);

