#include "parser.h"
#include"regexbuilder.h"
Parser::Parser(string file_name){
    rgx_builder.read_regx(file_name);
    //cout << rgx_builder.get_format2_regx().size()<<endl;
}

bool Parser::is_valid_label(string x){
    return !rgx_builder.is_reserved_word(x);
}


bool Parser::check_adressing_mode(Instruction &inst , string label,string op,string op1,string op2,int format){
    if(!is_valid_label(label) ){
        inst.set_error(true);
        return false;
    }
    if( format != 2){
        /// format 3 or 4 , aly (single)
        if(op[0] == '+' ){
            op = op.substr(1);
            inst.set_e(true);
            format = 4;
        }

        if(op1.size() && op1[0] == '@' ){
            op1 = op1.substr(1);
            inst.set_n(true);
        }else if( op1.size() && op1[0] == '#' ){
            op1 = op1.substr(1);
            inst.set_i(true);
        }else if(op1.size() && op1.find(",x") != string::npos ){
            op1 = op1.substr(0,op1.size()-2);
            inst.set_i(true);
            inst.set_x(true);
            inst.set_n(true);
        }else{
            inst.set_n(true);
            inst.set_i(true);
        }
        if(!is_valid_label(op1)){
            inst.set_error(true);
            return false;
        }

    }

    inst.set_label(label);
    inst.set_op_label(op);
    inst.set_op_code(rgx_builder.get_obj_code(op));
    inst.set_op1(op1);
    inst.set_op2(op2);
    inst.set_format(format);

    return true;

}

bool Parser::process_directive(Instruction &inst, string label, string directive,string op){

    if(!is_valid_label  (label) ){
        inst.set_error(true);
        return false;
    }

    inst.set_label(label);
    inst.set_op_label(directive);
    inst.set_op1(op);

    if(directive == "resw"){
       if(op.size() > 4){
           inst.set_error(false);
           return false;
       }
       inst.set_format(  -1 * atoi(op.c_str()) * 3);
    }

    if(directive == "resb"){
          if(op.size() > 4){
                inst.set_error(true);
               return false;
          }
          inst.set_format(  -1 * atoi(op.c_str()) );
    }

    if(directive == "word"){
           int max_len = 5;
           if(op[0] == '-'){
               max_len = 4;
           }
           if(op.size() > max_len){
               inst.set_error(true);
               return false;
           }
           inst.set_format(-3);
    }

    if(directive == "byte"){

        if(op[0] == 'x'&& (op.size() <= 17) && ( (op.size()-3) % 2 == 0)){

            inst.set_format(- ((op.size()-3) / 2));

        }else if(op[0] == 'c'&& (op.size() <= 18)){

            inst.set_format(- (op.size()-3));

        }else{

            inst.set_error(true);
            return false;

        }

    }

    if(directive == "start"){

        if(op.size() > 4){
            inst.set_error(false);
            return false;
        }

    }

    return true;

}


Instruction Parser::read_line(string line){
    transform(line.begin(),line.end(),line.begin(),::tolower);
    Instruction inst;
    smatch sm;
    bool found = false;
    regex rgx("^"+rgx_builder.get_comment_regx());
    if( regex_match(line,rgx) ){
        regex_search(line,sm,rgx);
        inst.set_is_comment(true);
        found = true;
    }
    rgx = regex(rgx_builder.get_ret_sub_regx());
    if( regex_match(line,rgx) ){
        regex_search(line,sm,rgx);
        check_adressing_mode(inst,sm[1],sm[2],"","",3);
        found = true;
    }
    for(int i=0;!found && i<5;i++){
        rgx = regex(rgx_builder.get_dir_regx(i));
        if( regex_match(line,rgx) ){
            regex_search(line,sm,rgx);
            process_directive(inst,sm[1],sm[2],sm[3]);
            found = true;
        }
    }
    map<string,string>mp = rgx_builder.get_format2_regx();
    map<string,string>::iterator it =  mp.begin();
    while(!found && it != mp.end()){
        rgx = regex("\\s*("+rgx_builder.get_label_regx()+"\\s+){0,1}"+it->second);

        if( regex_match(line,rgx) ){

            regex_search(line,sm,rgx);
            string last = "";
            if( sm.size() >= 4)last = sm[4];
            check_adressing_mode(inst,sm[1] , sm[2], sm[3], last,2);
            found = true;
            break;
        }
        it++;
    }
    mp = rgx_builder.get_format3_4_regx();
    it =  mp.begin();
    while(!found && it != mp.end()){
        rgx = regex("\\s*("+rgx_builder.get_label_regx()+"\\s+){0,1}"+it->second);
        if( regex_match(line,rgx) ){
            cout << line << " " << 123<<endl;
            regex_search(line,sm,rgx);
            check_adressing_mode(inst,sm[1] , sm[2], sm[3], "",3);
            found = true;
            break;

        }
        it++;
    }
    if(!found)
           inst.set_error(true);
    return inst;
}

//ctor
