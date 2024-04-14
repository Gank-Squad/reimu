
int fib(int i){

    if(i <= 0){
        println("alskdj alsdlakjd lkasjdk l");
        return 0;
    } 
    el(i == 1){
        return 1;
    }
    el(i == 2){
       return 1;
    }

    return fib(i - 1) + fib(i -2);
}

println((string)fib(10));


