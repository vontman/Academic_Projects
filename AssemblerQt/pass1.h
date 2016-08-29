#include <bits/stdc++.h>
#ifndef PASS1_H
#define PASS1_H
#endif // PARSER_H
#include "parser.h"
#include"instruction.h"
using namespace std;
class Pass1
{
    public:
        Pass1(string);
        void read_file();
        bool is_completed();
    private:
        bool completed = true;
        bool start = false;
        bool endf = false;
        string path;
        int location_counter=0;
        vector <string> commands;
        unordered_map<string, int> sym_table;
};
