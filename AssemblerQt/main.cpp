#include <iostream>
#include"parser.h"
#include"instruction.h"
#include"pass1.h"
using namespace std;


int main(int argc, char *argv[]){
    if(argc != 2){
        cout<<"USAGE:\n pass1 <filename>"<<endl;
        return 0;
    }
    Pass1 p(argv[1]);
    return 0;
}


