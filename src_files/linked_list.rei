


struct Node(Node next, Node prev, int data);

Node head = Node{};



for(Node h = head, int i = 0; i < 10; h = h.next, ++i){

    h.data = i;
    h.next = Node{};
    h.next.prev = h;
    println((string)i);
}


for(Node h = head; h; h = h.next){
    println((string)h);
}
