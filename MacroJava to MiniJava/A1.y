%{
    #include <stdio.h>
    #include <string.h>
    #include <stdlib.h>

    int yylex();
    int yyerror(const char *s);

    char* merge(int p, char* a[]) {
        if(p==0) {
            char* temp=(char*) malloc(1*sizeof(char));
            temp[0]='\0';
            return temp;
        }

        int len= strlen(a[0]);
        for(int i=1; i<p; i++)
            len+= strlen(a[i]) + 1;
        
        char* result=(char*) malloc((len+1)*sizeof(char));
        result[0]='\0';

        strcat(result, a[0]);
        for(int i=1; i<p; i++) {
            strcat(result, " ");
            strcat(result, a[i]);
        }
        
        return result;
    }

    char* free_merge(int p, char* a[]) {
        char* result=merge(p, a);
        for(int i=0; i<p; i++)
            free(a[i]);

        return result;
    }

    int CEA_STAR=1;
    char* ELIST[100]={NULL};

    struct macro {
        char* identifier;
        char* arguments;
        char* expression;
    };
    int macro_count=0;
    struct macro macros[100];
    char buffer[30000];
%}

%union {
	char* text;
}

/* Tokens */
%token NEW THIS IF ELSE WHILE RETURN CLASS PUBLIC STATIC VOID MAIN EXTENDS
%token SYSTEM OUT PRINTLN LENGTH
%token INT_ARRAY INT BOOL STRING
%token INT_LIT TRUE FALSE
%token EQ LE NE OR AND ADD SUB MUL DIV NOT DOT
%token RND_L RND_R SQR_L SQR_R CRLY_L CRLY_R SEMICOLON COMMA
%token DSTAT DSTAT0 DSTAT1 DSTAT2 DEXP DEXP0 DEXP1 DEXP2
%token ID
%token EF

/* Types */
%type<text> NEW THIS IF ELSE WHILE RETURN CLASS PUBLIC STATIC VOID MAIN EXTENDS SYSTEM OUT PRINTLN LENGTH INT_ARRAY INT BOOL STRING INT_LIT TRUE FALSE EQ LE NE OR AND ADD SUB MUL DIV NOT DOT RND_L RND_R SQR_L SQR_R CRLY_L CRLY_R SEMICOLON COMMA DSTAT DSTAT0 DSTAT1 DSTAT2 DEXP DEXP0 DEXP1 DEXP2 ID
%type<text> GOAL MAIN_CLASS TYPE_DECLARATION METHOD_DECLARATION TYPE STATEMENT EXPRESSION PRIMARY_EXPRESSION MACRO_DEFINITION MACRO_DEF_STATEMENT MACRO_DEF_EXPRESSION IDENTIFIER INTEGER_LITERAL
%type<text> MACRO_DEFINITION_ASTERATE TYPE_DECLARATION_ASTERATE TYPE_IDENTIFIER_SEMICOLON_ASTERATE METHOD_DECLARATION_ASTERATE COMMA_TYPE_IDENTIFIER_ASTERATE STATEMENT_ASTERATE COMMA_EXPRESSION_ASTERATE COMMA_IDENTIFIER_ASTERATE METHOD_BODY NON_TRIVIAL_METHOD_BODY

%%

GOAL : MACRO_DEFINITION_ASTERATE MAIN_CLASS TYPE_DECLARATION_ASTERATE EF
{
    free($1);
    char* a[2]={$2, $3};
    $$= free_merge(2, a);
    printf("%s\n", $$);
    return 0;
};

MAIN_CLASS : CLASS IDENTIFIER CRLY_L PUBLIC STATIC VOID MAIN RND_L STRING SQR_L SQR_R IDENTIFIER RND_R CRLY_L SYSTEM DOT OUT DOT PRINTLN RND_L EXPRESSION RND_R SEMICOLON CRLY_R CRLY_R
{
    char* a[25]={$1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13, $14, $15, $16, $17, $18, $19, $20, $21, $22, $23, $24, $25};
    $$= free_merge(25, a);
};

TYPE_DECLARATION : CLASS IDENTIFIER CRLY_L TYPE_IDENTIFIER_SEMICOLON_ASTERATE METHOD_DECLARATION_ASTERATE CRLY_R
{
    char* a[6]={$1, $2, $3, $4, $5, $6};
    $$= free_merge(6, a);
}
    | CLASS IDENTIFIER EXTENDS IDENTIFIER CRLY_L TYPE_IDENTIFIER_SEMICOLON_ASTERATE METHOD_DECLARATION_ASTERATE CRLY_R
{
    char* a[8]={$1, $2, $3, $4, $5, $6, $7, $8};
    $$= free_merge(8, a);
};

METHOD_DECLARATION : PUBLIC TYPE IDENTIFIER RND_L RND_R CRLY_L METHOD_BODY RETURN EXPRESSION SEMICOLON CRLY_R
{
    char* a[11]={$1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11};
    $$= free_merge(11, a);
}
    | PUBLIC TYPE IDENTIFIER RND_L TYPE IDENTIFIER COMMA_TYPE_IDENTIFIER_ASTERATE RND_R CRLY_L METHOD_BODY RETURN EXPRESSION SEMICOLON CRLY_R
{
    char* a[14]={$1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13, $14};
    $$= free_merge(14, a);
};

TYPE : INT_ARRAY
{
    char* a[1]={$1};
    $$= free_merge(1, a);
}
	| BOOL
{
    char* a[1]={$1};
    $$= free_merge(1, a);
}
    | INT
{
    char* a[1]={$1};
    $$= free_merge(1, a);
}
    | IDENTIFIER
{
    char* a[1]={$1};
    $$= free_merge(1, a);
};

STATEMENT : CRLY_L STATEMENT_ASTERATE CRLY_R
{
    char* a[3]={$1, $2, $3};
    $$= free_merge(3, a);
}
    | SYSTEM DOT OUT DOT PRINTLN RND_L EXPRESSION RND_R SEMICOLON
{
    char* a[9]={$1, $2, $3, $4, $5, $6, $7, $8, $9};
    $$= free_merge(9, a);
}
    | IDENTIFIER EQ EXPRESSION SEMICOLON
{
    char* a[4]={$1, $2, $3, $4};
    $$= free_merge(4, a);
}
    | IDENTIFIER SQR_L EXPRESSION SQR_R EQ EXPRESSION SEMICOLON
{
    char* a[7]={$1, $2, $3, $4, $5, $6, $7};
    $$= free_merge(7, a);
}
    | IF RND_L EXPRESSION RND_R STATEMENT
{
    char* a[5]={$1, $2, $3, $4, $5};
    $$= free_merge(5, a);
}
    | IF RND_L EXPRESSION RND_R STATEMENT ELSE STATEMENT
{
    char* a[7]={$1, $2, $3, $4, $5, $6, $7};
    $$= free_merge(7, a);
}
    | WHILE RND_L EXPRESSION RND_R STATEMENT
{
    char* a[5]={$1, $2, $3, $4, $5};
    $$= free_merge(5, a);
}
    | IDENTIFIER RND_L EXPRESSION COMMA_EXPRESSION_ASTERATE RND_R SEMICOLON
{
    int loc;
    for(loc=0; loc<macro_count; loc++) {
        if(strcmp($1, macros[loc].identifier)==0)
            break;
    }

    if(loc<macro_count)
    {
        char* inputs=(char*)malloc((strlen($3)+strlen($4)+1)*sizeof(char));
        inputs[0]='\0';
        strcat(inputs, $3);
        strcat(inputs, $4);

        ELIST[0]=(char*)malloc((strlen($3)+1)*sizeof(char));
        ELIST[0][0]='\0';
        strcat(ELIST[0], $3);

        char* macros_expression=(char*)malloc((strlen(macros[loc].expression)+1)*sizeof(char));
        macros_expression[0]='\0';
        strcat(macros_expression, macros[loc].expression);

        char* macros_arguments=(char*)malloc((strlen(macros[loc].arguments)+1)*sizeof(char));
        macros_arguments[0]='\0';
        strcat(macros_arguments, macros[loc].arguments);

        int p=0;
        char* x1[100];
        char* argument= strtok(macros_arguments, " ,");
        while(argument!=NULL) {
            x1[p++]= argument;
            argument= strtok(NULL, " ,");
        }

        int q=CEA_STAR;
        char** x2=ELIST;

        if(p!=q)
            return yyerror(NULL);

        buffer[0]='\0';
        char* token= strtok(macros_expression, " ");
        while(token!=NULL) {
            int found;
            for(found=p-1; found>=0; found--) {
                if(strcmp(x1[found], token)==0) {
                    strcat(buffer, x2[found]);
                    break;
                }
            }
            if(found==-1) {
                strcat(buffer, token);
            }
            token= strtok(NULL, " ");
        }
        int len=strlen(buffer);
        $$= (char*) malloc((len+1)*sizeof(char)); $$[0]=$$[len]='\0';
        strcpy($$, buffer);

        free(inputs); free(macros_expression); free(macros_arguments);
        free($1); free($2); free($3); free($4); free($5); free($6);
    } 
    else
    {
        char* a[6]={$1, $2, $3, $4, $5, $6};
        $$= free_merge(6, a);
    }
}
    | IDENTIFIER RND_L RND_R SEMICOLON
{
    int loc;
    for(loc=0; loc<macro_count; loc++) {
        if(strcmp($1, macros[loc].identifier)==0)
            break;
    }

    if(loc<macro_count)
    {
        buffer[0]='\0';
        strcat(buffer, macros[loc].expression);
        int len=strlen(buffer);
        $$= (char*) malloc((len+1)*sizeof(char)); $$[0]=$$[len]='\0';
        strcpy($$, buffer);
    }
    else
    {
        char* a[4]={$1, $2, $3, $4};
        $$ = free_merge(4, a);
    }
};

EXPRESSION : PRIMARY_EXPRESSION AND PRIMARY_EXPRESSION
{
    char* a[3]={$1, $2, $3};
    $$= free_merge(3, a);
}
    | PRIMARY_EXPRESSION OR PRIMARY_EXPRESSION
{
    char* a[3]={$1, $2, $3};
    $$= free_merge(3, a);
}
    | PRIMARY_EXPRESSION NE PRIMARY_EXPRESSION
{
    char* a[3]={$1, $2, $3};
    $$= free_merge(3, a);
}
    | PRIMARY_EXPRESSION LE PRIMARY_EXPRESSION
{
    char* a[3]={$1, $2, $3};
    $$= free_merge(3, a);
}
    | PRIMARY_EXPRESSION ADD PRIMARY_EXPRESSION
{
    char* a[3]={$1, $2, $3};
    $$= free_merge(3, a);
}
    | PRIMARY_EXPRESSION SUB PRIMARY_EXPRESSION
{
    char* a[3]={$1, $2, $3};
    $$= free_merge(3, a);
}
    | PRIMARY_EXPRESSION MUL PRIMARY_EXPRESSION
{
    char* a[3]={$1, $2, $3};
    $$= free_merge(3, a);
}
    | PRIMARY_EXPRESSION DIV PRIMARY_EXPRESSION
{
    char* a[3]={$1, $2, $3};
    $$= free_merge(3, a);
}
    | PRIMARY_EXPRESSION SQR_L PRIMARY_EXPRESSION SQR_R
{
    char* a[4]={$1, $2, $3, $4};
    $$= free_merge(4, a);
}
    | PRIMARY_EXPRESSION DOT LENGTH
{
    char* a[3]={$1, $2, $3};
    $$= free_merge(3, a);
}
    | PRIMARY_EXPRESSION
{
    char* a[1]={$1};
    $$= free_merge(1, a);
}
    | PRIMARY_EXPRESSION DOT IDENTIFIER RND_L EXPRESSION COMMA_EXPRESSION_ASTERATE RND_R
{
    char* a[7]={$1, $2, $3, $4, $5, $6, $7};
    $$= free_merge(7, a);
}
    | PRIMARY_EXPRESSION DOT IDENTIFIER RND_L RND_R
{
    char* a[5]={$1, $2, $3, $4, $5};
    $$= free_merge(5, a);
}
    | IDENTIFIER RND_L EXPRESSION COMMA_EXPRESSION_ASTERATE RND_R
{
    int loc;
    for(loc=0; loc<macro_count; loc++) {
        if(strcmp($1, macros[loc].identifier)==0)
            break;
    }

    if(loc<macro_count)
    {
        char* inputs=(char*)malloc((strlen($3)+strlen($4)+1)*sizeof(char));
        inputs[0]='\0';
        strcat(inputs, $3);
        strcat(inputs, $4);

        ELIST[0]=(char*)malloc((strlen($3)+1)*sizeof(char));
        ELIST[0][0]='\0';
        strcat(ELIST[0], $3);

        char* macros_expression=(char*)malloc((strlen(macros[loc].expression)+1)*sizeof(char));
        macros_expression[0]='\0';
        strcat(macros_expression, macros[loc].expression);

        char* macros_arguments=(char*)malloc((strlen(macros[loc].arguments)+1)*sizeof(char));
        macros_arguments[0]='\0';
        strcat(macros_arguments, macros[loc].arguments);

        int p=0;
        char* x1[100];
        char* argument= strtok(macros_arguments, " ,");
        while(argument!=NULL) {
            x1[p++]= argument;
            argument= strtok(NULL, " ,");
        }

        int q=CEA_STAR;
        char** x2=ELIST;

        if(p!=q)
            return yyerror(NULL);

        buffer[0]='\0';
        char* token= strtok(macros_expression, " ");
        while(token!=NULL) {
            int found;
            for(found=p-1; found>=0; found--) {
                if(strcmp(x1[found], token)==0) {
                    strcat(buffer, x2[found]);
                    break;
                }
            }
            if(found==-1) {
                strcat(buffer, token);
            }
            token= strtok(NULL, " ");
        }
        int len=strlen(buffer);
        $$= (char*) malloc((len+1)*sizeof(char)); $$[0]=$$[len]='\0';
        strcpy($$, buffer);

        free(inputs); free(macros_expression); free(macros_arguments);
        free($1); free($2); free($3); free($4); free($5);
    } 
    else
    {
        char* a[5]={$1, $2, $3, $4, $5};
        $$= free_merge(5, a);
    }
}
    | IDENTIFIER RND_L RND_R
{
    int loc;
    for(loc=0; loc<macro_count; loc++) {
        if(strcmp($1, macros[loc].identifier)==0)
            break;
    }

    if(loc<macro_count)
    {
        buffer[0]='\0';
        strcat(buffer, macros[loc].expression);
        int len=strlen(buffer);
        $$= (char*) malloc((len+1)*sizeof(char)); $$[0]=$$[len]='\0';
        strcpy($$, buffer);
    }
    else
    {
        char* a[3]={$1, $2, $3};
        $$ = free_merge(3, a);
    }
};

PRIMARY_EXPRESSION : INTEGER_LITERAL
{
    char* a[1]={$1};
    $$= free_merge(1, a);
}
    | TRUE
{
    char* a[1]={$1};
    $$= free_merge(1, a);
}
    | FALSE
{
    char* a[1]={$1};
    $$= free_merge(1, a);
}
    | IDENTIFIER
{
    char* a[1]={$1};
    $$= free_merge(1, a);
}
    | THIS
{
    char* a[1]={$1};
    $$= free_merge(1, a);
}
    | NEW INT SQR_L EXPRESSION SQR_R
{
    char* a[5]={$1, $2, $3, $4, $5};
    $$= free_merge(5, a);
}
    | NEW IDENTIFIER RND_L RND_R
{
    char* a[4]={$1, $2, $3, $4};
    $$= free_merge(4, a);
}
    | NOT EXPRESSION
{
    char* a[2]={$1, $2};
    $$= free_merge(2, a);
}
    | RND_L EXPRESSION RND_R
{
    char* a[3]={$1, $2, $3};
    $$= free_merge(3, a);
};

MACRO_DEFINITION : MACRO_DEF_STATEMENT
{
    char* a[1]={$1};
    $$= free_merge(1, a);
}
    | MACRO_DEF_EXPRESSION
{
    char* a[1]={$1};
    $$= free_merge(1, a);
};

MACRO_DEF_STATEMENT : DSTAT0 IDENTIFIER RND_L RND_R CRLY_L STATEMENT_ASTERATE CRLY_R
{
    char* expression_components[3]={$5, $6, $7};

    struct macro hash_define;
    hash_define.identifier= (char*)malloc((strlen($2)+1)*sizeof(char));
    hash_define.identifier[0]='\0';
    strcpy(hash_define.identifier, $2);
    hash_define.arguments=NULL;
    hash_define.expression=merge(3, expression_components);
    macros[macro_count++]=hash_define;

    char* a[7]={$1, $2, $3, $4, $5, $6, $7};
    $$= free_merge(7, a);
}
    | DSTAT1 IDENTIFIER RND_L IDENTIFIER RND_R CRLY_L STATEMENT_ASTERATE CRLY_R
{
    char* argument_components[1]={$4};
    char* expression_components[3]={$6, $7, $8};

    struct macro hash_define;
    hash_define.identifier= (char*)malloc((strlen($2)+1)*sizeof(char));
    hash_define.identifier[0]='\0';
    strcpy(hash_define.identifier, $2);
    hash_define.arguments=merge(1, argument_components);
    hash_define.expression=merge(3, expression_components);
    macros[macro_count++]=hash_define;

    char* a[8]={$1, $2, $3, $4, $5, $6, $7, $8};
    $$= free_merge(8, a);
}
    | DSTAT2 IDENTIFIER RND_L IDENTIFIER COMMA IDENTIFIER RND_R CRLY_L STATEMENT_ASTERATE CRLY_R
{
    char* argument_components[3]={$4, $5, $6};
    char* expression_components[3]={$8, $9, $10};

    struct macro hash_define;
    hash_define.identifier= (char*)malloc((strlen($2)+1)*sizeof(char));
    hash_define.identifier[0]='\0';
    strcpy(hash_define.identifier, $2);
    hash_define.arguments=merge(3, argument_components);
    hash_define.expression=merge(3, expression_components);
    macros[macro_count++]=hash_define;

    char* a[10]={$1, $2, $3, $4, $5, $6, $7, $8, $9, $10};
    $$= free_merge(10, a);
}
    | DSTAT IDENTIFIER RND_L IDENTIFIER COMMA IDENTIFIER COMMA IDENTIFIER COMMA_IDENTIFIER_ASTERATE RND_R CRLY_L STATEMENT_ASTERATE CRLY_R
{
    char* argument_components[6]={$4, $5, $6, $7, $8, $9};
    char* expression_components[3]={$11, $12, $13};

    struct macro hash_define;
    hash_define.identifier= (char*)malloc((strlen($2)+1)*sizeof(char));
    hash_define.identifier[0]='\0';
    strcpy(hash_define.identifier, $2);
    hash_define.arguments=merge(6, argument_components);
    hash_define.expression=merge(3, expression_components);
    macros[macro_count++]=hash_define;

    char* a[13]={$1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13};
    $$= free_merge(13, a);
};

MACRO_DEF_EXPRESSION : DEXP0 IDENTIFIER RND_L RND_R RND_L EXPRESSION RND_R
{
    char* expression_components[3]={$5, $6, $7};

    struct macro hash_define;
    hash_define.identifier= (char*)malloc((strlen($2)+1)*sizeof(char));
    hash_define.identifier[0]='\0';
    strcpy(hash_define.identifier, $2);
    hash_define.arguments=NULL;
    hash_define.expression=merge(3, expression_components);
    macros[macro_count++]=hash_define;

    char* a[7]={$1, $2, $3, $4, $5, $6, $7};
    $$= free_merge(7, a);
}
    | DEXP1 IDENTIFIER RND_L IDENTIFIER RND_R RND_L EXPRESSION RND_R
{
    char* argument_components[1]={$4};
    char* expression_components[3]={$6, $7, $8};

    struct macro hash_define;
    hash_define.identifier= (char*)malloc((strlen($2)+1)*sizeof(char));
    hash_define.identifier[0]='\0';
    strcpy(hash_define.identifier, $2);
    hash_define.arguments=merge(1, argument_components);
    hash_define.expression=merge(3, expression_components);
    macros[macro_count++]=hash_define;

    char* a[8]={$1, $2, $3, $4, $5, $6, $7, $8};
    $$= free_merge(8, a);
}
    | DEXP2 IDENTIFIER RND_L IDENTIFIER COMMA IDENTIFIER RND_R RND_L EXPRESSION RND_R
{
    char* argument_components[3]={$4, $5, $6};
    char* expression_components[3]={$8, $9, $10};

    struct macro hash_define;
    hash_define.identifier= (char*)malloc((strlen($2)+1)*sizeof(char));
    hash_define.identifier[0]='\0';
    strcpy(hash_define.identifier, $2);
    hash_define.arguments=merge(3, argument_components);
    hash_define.expression=merge(3, expression_components);
    macros[macro_count++]=hash_define;

    char* a[10]={$1, $2, $3, $4, $5, $6, $7, $8, $9, $10};
    $$= free_merge(10, a);
}
    | DEXP IDENTIFIER RND_L IDENTIFIER COMMA IDENTIFIER COMMA IDENTIFIER COMMA_IDENTIFIER_ASTERATE RND_R RND_L EXPRESSION RND_R
{
    char* argument_components[6]={$4, $5, $6, $7, $8, $9};
    char* expression_components[3]={$11, $12, $13};

    struct macro hash_define;
    hash_define.identifier= (char*)malloc((strlen($2)+1)*sizeof(char));
    hash_define.identifier[0]='\0';
    strcpy(hash_define.identifier, $2);
    hash_define.arguments=merge(6, argument_components);
    hash_define.expression=merge(3, expression_components);
    macros[macro_count++]=hash_define;

    char* a[13]={$1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13};
    $$= free_merge(13, a);
};

INTEGER_LITERAL : INT_LIT
{
    char* a[1]={$1};
    $$= free_merge(1, a);
}

IDENTIFIER : ID
{
    char* a[1]={$1};
    $$= free_merge(1, a);
}

MACRO_DEFINITION_ASTERATE : 
{
    $$ = free_merge(0, NULL);
}
    | MACRO_DEFINITION MACRO_DEFINITION_ASTERATE
{
    char* a[2]={$1, $2};
    $$= free_merge(2, a);
};

TYPE_DECLARATION_ASTERATE : 
{
    $$ = free_merge(0, NULL);
}
    | TYPE_DECLARATION TYPE_DECLARATION_ASTERATE
{
    char* a[2]={$1, $2};
    $$= free_merge(2, a);
};

TYPE_IDENTIFIER_SEMICOLON_ASTERATE : 
{
    $$ = free_merge(0, NULL);
}
    | TYPE IDENTIFIER SEMICOLON TYPE_IDENTIFIER_SEMICOLON_ASTERATE
{
    char* a[4]={$1, $2, $3, $4};
    $$= free_merge(4, a);
};

METHOD_DECLARATION_ASTERATE : 
{
    $$ = free_merge(0, NULL);
}
    | METHOD_DECLARATION METHOD_DECLARATION_ASTERATE
{
    char* a[2]={$1, $2};
    $$= free_merge(2, a);
};

COMMA_TYPE_IDENTIFIER_ASTERATE : 
{
    $$ = free_merge(0, NULL);
}
    | COMMA TYPE IDENTIFIER COMMA_TYPE_IDENTIFIER_ASTERATE
{
    char* a[4]={$1, $2, $3, $4};
    $$= free_merge(4, a);
};

STATEMENT_ASTERATE : 
{
    $$ = free_merge(0, NULL);
}
    | STATEMENT STATEMENT_ASTERATE
{
    char* a[2]={$1, $2};
    $$= free_merge(2, a);
};

COMMA_EXPRESSION_ASTERATE : 
{
    while(CEA_STAR>1) {
        free(ELIST[--CEA_STAR]);
        ELIST[CEA_STAR]=NULL;
    }
    $$ = free_merge(0, NULL);
}
    | COMMA EXPRESSION COMMA_EXPRESSION_ASTERATE
{
    ELIST[CEA_STAR]=(char*)malloc((strlen($2)+1)*sizeof(char));
    ELIST[CEA_STAR][0]='\0';
    strcat(ELIST[CEA_STAR], $2);
    CEA_STAR++;

    char* a[3]={$1, $2, $3};
    $$= free_merge(3, a);
};

COMMA_IDENTIFIER_ASTERATE : 
{
    $$ = free_merge(0, NULL);
}
    | COMMA IDENTIFIER COMMA_IDENTIFIER_ASTERATE
{
    char* a[3]={$1, $2, $3};
    $$= free_merge(3, a);
};

METHOD_BODY : TYPE_IDENTIFIER_SEMICOLON_ASTERATE
{
    char* a[1]={$1};
    $$= free_merge(1, a);
}
    | STATEMENT_ASTERATE
{
    char* a[1]={$1};
    $$= free_merge(1, a);
}
    | NON_TRIVIAL_METHOD_BODY
{
    char* a[1]={$1};
    $$= free_merge(1, a);
};

NON_TRIVIAL_METHOD_BODY : TYPE IDENTIFIER SEMICOLON STATEMENT
{
    char* a[4]={$1, $2, $3, $4};
    $$= free_merge(4, a);
}
    | NON_TRIVIAL_METHOD_BODY STATEMENT 
{
    char* a[2]={$1, $2};
    $$= free_merge(2, a);
}
    | TYPE IDENTIFIER SEMICOLON NON_TRIVIAL_METHOD_BODY
{
    char* a[4]={$1, $2, $3, $4};
    $$= free_merge(4, a);
};


%% 

int yyerror(const char *s)
{
	printf("//Failed to parse input code\n");
	return 0;
}


int main(int argc, char **argv)
{
	yyparse();
	printf("\n");
	return 0;
}