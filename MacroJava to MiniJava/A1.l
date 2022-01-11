%{
    #include "A1.tab.h"
%}

%%

"//"(.*)[\n] {}
"/*"(.*)"*/" {}
[ \t\n] {}

"new" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return NEW;
}
"this" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return THIS;
}
"if" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return IF;
}
"else" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return ELSE;
}
"while" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return WHILE;
}
"return" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return RETURN;
}
"class" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return CLASS;
}
"public" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return PUBLIC;
}
"static" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return STATIC;
}
"void" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return VOID;
}
"main" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return MAIN;
}
"extends" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return EXTENDS;
}

"System" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return SYSTEM;
}
"out" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return OUT;
}
"println" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return PRINTLN;
}
"length" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return LENGTH;
}

"int[]" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return INT_ARRAY;
}
"int" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return INT;
}
"boolean" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return BOOL;
}
"String" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return STRING;
}

0|[1-9][0-9]* { 
	int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return INT_LIT;
}
"true" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return TRUE;
}
"false" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return FALSE;
}

"=" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return EQ;
}
"<=" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return LE;
}
"!=" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return NE;
}
"&&" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return AND;
}
"||" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return OR;
}
"+" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return ADD;
}
"-" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return SUB;
}
"*" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return MUL;
}
"/" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return DIV;
}
"!" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return NOT;
}
"." {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return DOT;
}
"," {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return COMMA;
}

"(" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return RND_L;
}
")" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return RND_R;
}
"[" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return SQR_L;
}
"]" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return SQR_R;
}
"{" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return CRLY_L;
}
"}" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return CRLY_R;
}
";" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return SEMICOLON;
}

"#defineStmt" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return DSTAT;
}
"#defineStmt0" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return DSTAT0;
}
"#defineStmt1" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return DSTAT1;
}
"#defineStmt2" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return DSTAT2;
}
"#defineExpr" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return DEXP;
}
"#defineExpr0" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return DEXP0;
}
"#defineExpr1" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return DEXP1;
}
"#defineExpr2" {
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text,yytext);
    return DEXP2;
}

[a-zA-Z_][a-zA-Z_0-9]* { 
    int len = strlen(yytext);
	yylval.text = (char*) malloc((len + 1) * sizeof(char));
	yylval.text[0] = '\0';
	strcat(yylval.text, yytext);
    return ID;
}

<<EOF>> {return EF;}

%%

// int main() {
//     int what;
//     while((what=yylex())!=EF) {
//         printf("%d %s\n", what, yytext);
//     }
// 	return 0;
// }

/*
#defineExpr add(x, y, z, w) (((x+y)+z)+w)
class test {
    public static void main(String[] args) {
        System.out.println(1);
    }
}

*/