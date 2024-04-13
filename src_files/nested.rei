

import "./function_def.rei";

char[][][] a = [
    ["hello"], 
    ["world"]
];

iter[int] range = 0..10;


for(i in range) {

    for(list1 in a) {

        for(str in list1){

            print(str);
            print(", ");
        }

        if(i % 5 == 0){
            print("something divisible by 5^2 = ");
            println(square(i));
        } el(i % 3 == 0) {
            print("something divisible by 3^2 = ");
            println(square(i));
        } el {
            println((string)i);
        }
    }
}

println((string)a);
println((string)range);



