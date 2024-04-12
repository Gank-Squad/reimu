

import "function_def.rei";

i32[] a = [1, 2, 3, 4, 5];

bool iseven(i32 i) {

    if(i % 2 == 0) {
        true;
    }
    el {
        false;
    }
}


for(i32 c = 1, var b = 4; c < b; c = c+1, b = b + (b%2)) {

    print("hello");
}

for(i in a) {

    if(iseven(i)) {
        println("is even");
    }
    el {
        println("not even");
    }
}

println(square(1));
