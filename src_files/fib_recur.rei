
int fib(int i){

    if(i <= 0){
        return 0;
    } 
    el(i == 1 || i == 2){
        return 1;
    }

    return fib(i - 1) + fib(i - 2);
}

println((string)fib(10));


