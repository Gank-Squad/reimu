



int fib(int i){

    int[] hist = [0, 1, 1];

    for(int j = 0; j < i; ++j) {

        println((string)hist[2]);

        hist[0] = hist[1];
        hist[1] = hist[2];
        hist[2] = hist[0] + hist[1];
    }

    hist[2];
}


fib(10);



