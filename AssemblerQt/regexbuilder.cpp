#include "regexbuilder.h"
#include<bits/stdc++.h>
using namespace std;
RegexBuilder::RegexBuilder(){
    comment_regx = "\\s*(?:\\..*)*$";
    registers_regx = "[axlbstf]";
    label_regx = "(?:[a-zA-Z_](?:[0-9a-zA-Z_])*)";
    char_regx = "(?:c'(?:(?:\\')|(?:[^']))+')";
    byte_regx = "(?:x'[0-9A-Fa-f]+')";
    imd_regx = "(?:#\\d+)";
    ind_regx = "(?:"+label_regx+",x"+")";
    ret_sub_regx = "^\\s*("+label_regx+"\\s+){0,1}"+"(rsub)"+comment_regx;
    dir_regx[0] = "^\\s*("+label_regx+"\\s+){0,1}"+"(resb|resw)\\s+(\\d+)"+comment_regx;
    dir_regx[1] = "^\\s*("+label_regx+"\\s+){0,1}"+"(word)\\s+(\\-{0,1}\\d+)"+comment_regx;
    dir_regx[2] =  "^\\s*("+label_regx+"\\s+){0,1}"+"(byte)\\s+("+byte_regx+"|"+char_regx+")"+comment_regx;
    dir_regx[3] = "^\\s*("+label_regx+"\\s+){0,1}"+"(start)\\s+([0-9a-fA-F]+)"+comment_regx;
    dir_regx[4] = "^\\s*()(end)\\s+("+label_regx+"){0,1}"+comment_regx;
}
void RegexBuilder::add_op(string x,map<string,string>&mp,string y){
    if( mp.find(y) == mp.end() )mp[y] = "(?:"+x;
    else    mp[y] += "|"+x+"";
}

void RegexBuilder::read_regx(string file_name){
    ifstream inp;
    inp.open(file_name,ios::in);
    while(!inp.eof()){
        string x,y,z;
        int o;
        inp >> x >> y >> z ;
        inp >> hex >> o;
        if(x == "")break;
        transform(x.begin(),x.end(),x.begin(),::tolower);
        transform(y.begin(),y.end(),y.begin(),::tolower);
        transform(z.begin(),z.end(),z.begin(),::tolower);
        obj_code[x] = o;
        if(z == "2")
            add_op(x,format2_regx,y);
        else
            add_op(x,format3_4_regx,y);
    }

    map<string,string> :: iterator it = format2_regx.begin();
    while(it != format2_regx.end()){
        it->second = "("+it->second+"))";
        if(it->first != "_"){
            it->second += "\\s+";
            for(int i=0;i<(int)it->first.size();i++){
                if(it->first[i] == 'r'){
                    it->second += "("+registers_regx+")";
                }else
                if(it->first[i] == 'n'){
                    it->second += "(\\d+)";
                }else
                    it->second += it->first[i];
            }
        }
        it->second += comment_regx;
       // cout<< it->second;
        it++;
    }

    it = format3_4_regx.begin();
    while(it != format3_4_regx.end()){
        it->second = "(\\+{0,1}"+it->second+"))\\s+";
        for(int i=0;i<(int)it->first.size();i++){
            if(it->first[i] == 'm'){
                it->second += "(\\\*|\\d+|"+ind_regx+"|"+"(?:[@#]{0,1}"+label_regx+")|"+imd_regx+")";
            }else
                it->second += it->first[i];
        }

        it->second += comment_regx;
       // cout<< it->second;
        it++;
    }

}

bool RegexBuilder:: is_reserved_word(string x){
    if( x == "x" || x == "a" || x == "l" || x == "s" || x == "t" || x == "f" || x == "start" || x == "end" || x == "resb" || x == "resw" || x == "word" || x == "byte" || x == "equ"  )
        return true;
    return obj_code.find(x) != obj_code.end();
}

int RegexBuilder:: get_obj_code(string x){
    return obj_code[x];
}


map<string,string> RegexBuilder::get_format2_regx(){
    return format2_regx;
}
map<string,string> RegexBuilder::get_format3_4_regx(){
    return format3_4_regx;
}
string RegexBuilder::get_label_regx(){
    return label_regx;
}
string RegexBuilder::get_imd_regx(){
    return imd_regx;
}
string RegexBuilder::get_ind_regx(){
    return ind_regx;
}
string RegexBuilder::get_comment_regx(){
    return comment_regx;
}
string RegexBuilder::get_dir_regx(int i){
    return dir_regx[i];
}

string RegexBuilder::get_ret_sub_regx(){
    return ret_sub_regx;
}
