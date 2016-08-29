#include <bits/stdc++.h>
#include "pass1.h"
#include "parser.h"
using namespace std;

Pass1::Pass1(string path) {
    this->path = path;
    read_file();
}
bool Pass1::is_completed() {
    return completed;
}
void Pass1::read_file(){
    Parser parser("optable.txt");
    ifstream file(path);
    ofstream ofile("LISA.txt");
    if(!file){
        cout<<"not found";
        return;
    }
    string word_temp;
    string word;
    while(getline(file, word_temp)) {
        word = "";
        for(int i=0;i<word_temp.size();i++){
            if(word_temp[i] != 13)word += word_temp[i];
        }
        Instruction instruct = parser.read_line(word);
        if(instruct.get_is_comment()){
            ofile<< word<<"\n";
            continue;
        }
        int temp = 0;
        string error = "";
        if (endf) error = "statement should not follow END statement\n";
        if (!instruct.get_error()){
            if(instruct.get_op_label() == "start" && !start) {
                start  = true;
                std::stringstream ss;
                ss << std::hex << instruct.get_op1();
                ss >> location_counter;
            }
            else if(instruct.get_op_label() == "start"){
                completed = false;
                error =  "duplicate or misplaced START statement\n";
            }

            if(!start) {
                start = true;
                location_counter = 0;
            }

            temp  = location_counter;
            if(instruct.get_op_label() == "end" && !endf){
                endf = true;
            }
            else if(instruct.get_op_label() == "end"){

                error = "Duplicate END instrtuction\n";
            }


            if(instruct.get_label().size() > 0){
                if(sym_table.find(instruct.get_label()) == sym_table.end()){
                    sym_table[instruct.get_label()] = location_counter;
                    commands.push_back(instruct.get_label());
                    instruct.set_address(location_counter);
                }
                else {
                    error = "Error: DUBLICATE SYMBOL\n";
                    completed = false;
                    if(instruct.get_format() < 0)
                        location_counter += instruct.get_format();
                    else
                        location_counter -= instruct.get_format();
                }
            }

            if(instruct.get_format() < 0)
                location_counter -= instruct.get_format();
            else
                location_counter += instruct.get_format();
        }
        else {
            error = "Error: Invalid Instruction\n";
            completed = false;
        }
        ofile <<hex << temp <<"\t"<<word<<"\n";
        if(error.size()>0)ofile<<error<<"\n";
    }
    ofile<<"\n***symbol table:\n";
    for(int i = 0; i < commands.size(); i++) {
        ofile << hex << sym_table[commands[i]] <<"\t" << commands[i] << "\n";
    }

}
