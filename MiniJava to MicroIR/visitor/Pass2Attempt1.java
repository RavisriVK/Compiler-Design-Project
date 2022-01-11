//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class Pass2<R,A> implements GJVisitor<R,A> {
   void display(Object s) {
      System.out.println(s);
   }

   boolean debugging=true;
   void debug(Object s) {
      if(debugging)
         System.out.println(s);
   }

   static Hashtable<String, class_details> classes;
   static Hashtable<Integer, String> class_record_name_map=new Hashtable<>();

   boolean is_local_variable(String identifier_name) {
      return classes.get(class_name).methods.get(method_name).local_variables.containsKey(identifier_name);
   }
   Integer get_local_variable_offset(String identifier_name) {
      return classes.get(class_name).methods.get(method_name).local_variables.get(identifier_name);
   }
   String get_local_variable_type(String identifier_name) {
      return classes.get(class_name).methods.get(method_name).local_variable_types.get(identifier_name);
   }

   boolean is_argument(String identifier_name) {
      return classes.get(class_name).methods.get(method_name).arguments.containsKey(identifier_name);
   }
   Integer get_argument_offset(String identifier_name) {
      return classes.get(class_name).methods.get(method_name).arguments.get(identifier_name);
   }
   String get_argument_type(String identifier_name) {
      return classes.get(class_name).methods.get(method_name).argument_types.get(identifier_name);
   }

   boolean is_class_variable(String identifier_name) {
      String current_class=class_name;
      while(current_class.compareTo("")!=0) {
         class_details selected_class=classes.get(current_class);
         if(selected_class.class_variables.containsKey(identifier_name))
            return true;
         current_class=classes.get(current_class).parent_class;
      }
      return false;
   }

   Integer get_class_variable_offset(String identifier_name) {
      Integer offset=0;
      String current_class=class_name;
      while(current_class.compareTo("")!=0) {
         class_details selected_class=classes.get(current_class);
         if(selected_class.class_variables.containsKey(identifier_name))
            return offset + selected_class.class_variables.get(identifier_name);
         offset+=selected_class.class_variables.size();
         current_class=classes.get(current_class).parent_class;
      }
      return null;
   }
   String get_class_variable_type(String identifier_name) {
      String current_class=class_name;
      while(current_class.compareTo("")!=0) {
         class_details selected_class=classes.get(current_class);
         if(selected_class.class_variables.containsKey(identifier_name))
            return selected_class.class_variable_types.get(identifier_name);
         current_class=classes.get(current_class).parent_class;
      }
      return null;
   }

   Integer get_class_record_size(String identifier_name) {
      Integer size=0;
      String current_class=identifier_name;
      while(current_class.compareTo("")!=0) {
         size+=classes.get(current_class).class_variables.size();
         current_class=classes.get(current_class).parent_class;
      }
      return size;
   }

   Integer get_function_offset(String unique_method) {
      String[] parts=unique_method.split("#");
      String start_class=parts[0];
      String current_class=start_class;
      Integer offset=0;
      while(current_class.compareTo("")!=0) {
         class_details selected_class=classes.get(current_class);
         for(int i=0; i<selected_class.method_list.size(); i++) {
            offset++;
            if(selected_class.method_list.get(i).compareTo(unique_method)==0)
               return offset;
         }
         current_class=classes.get(current_class).parent_class;
      }
      return null;
   }

   String get_return_type(String unique_method) {
      String[] parts=unique_method.split("#");
      String start_class=parts[0];
      String function_name=parts[1];
      String current_class=start_class;
      while(current_class.compareTo("")!=0) {
         class_details selected_class=classes.get(current_class);
         if(selected_class.methods.containsKey(function_name))
            return selected_class.methods.get(function_name).return_type;
         current_class=classes.get(current_class).parent_class;
      }
      return null;
   }

   /* Counters */
   Integer label_counter=1;
   Integer generate_fresh_label() {
      return ++label_counter;
   }

   Integer temp_counter=0;
   Integer generate_fresh_temp() {
      return ++temp_counter;
   }

   /* Context for Node */
   String class_name=null;
   String method_name=null;
   Object last_expression_result=null;

   public R visit(NodeList n, A argu) {
      R _ret=null;
      int _count=0;
      ArrayList<Object> node_list=new ArrayList<>();
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         Object item=e.nextElement().accept(this,argu);
         node_list.add(item);
         _count++;
      }
      _ret=(R)node_list;
      return _ret;
   }

   public R visit(NodeListOptional n, A argu) {
      ArrayList<Object> node_list=new ArrayList<>();
      if ( n.present() ) {
         R _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            node_list.add(last_expression_result);
            _count++;
         }
         _ret=(R)node_list;
         return _ret;
      }
      else
         return (R) node_list;
   }

   public R visit(NodeOptional n, A argu) {
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return null;
   }

   public R visit(NodeSequence n, A argu) {
      R _ret=null;
      int _count=0;
      ArrayList<Object> node_list=new ArrayList<>();
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         Object item=e.nextElement().accept(this,argu);
         node_list.add(item);
         _count++;
      }
      _ret=(R)node_list;
      return _ret;
   }

   public R visit(NodeToken n, A argu) { return (R)n.tokenImage; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
   public R visit(Goal n, A argu) {
      classes=(Hashtable<String, class_details>)argu;

      argu=null;
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> PrintStatement()
    * f15 -> "}"
    * f16 -> "}"
    */
   public R visit(MainClass n, A argu) {
      R _ret=null;

      display("MAIN");

      n.f0.accept(this, argu);
      class_name=(String)n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      method_name=(String)n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      n.f13.accept(this, argu);
      n.f14.accept(this, argu);
      n.f15.accept(this, argu);
      n.f16.accept(this, argu);

      display("END");

      return _ret;
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
   public R visit(TypeDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
   public R visit(ClassDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      class_name=(String)n.f1.accept(this, argu);
      method_name=null;

      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
   public R visit(ClassExtendsDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      class_name=(String)n.f1.accept(this, argu);
      method_name=null;

      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public R visit(VarDeclaration n, A argu) {
      R _ret=null;
      String data_type=(String)n.f0.accept(this, argu);
      String variable_name=(String)n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      if(method_name!=null) {
         method_details selected_method=classes.get(class_name).methods.get(method_name);
         Integer local_variable_offset=generate_fresh_temp();
         selected_method.local_variables.put(variable_name, local_variable_offset);
      }

      return _ret;
   }

   /**
    * f0 -> AccessType()
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
   public R visit(MethodDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      method_name=(String)n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);

      Integer arg_count=classes.get(class_name).methods.get(method_name).arguments.size();
      temp_counter=Math.max(temp_counter, arg_count + 1);
      display(class_name + "_" + method_name + " [" + (arg_count+1) + "]");
      display("BEGIN");

      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);

      R eresult=n.f10.accept(this, argu);
      Integer expr_result=(Integer)eresult;
      display("RETURN TEMP " + expr_result);

      n.f11.accept(this, argu);
      n.f12.accept(this, argu);

      display("END");

      return _ret;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public R visit(FormalParameterList n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   public R visit(FormalParameter n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public R visit(FormalParameterRest n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
   public R visit(Type n, A argu) {
      R _ret=null;
      _ret=n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PublicType()
    *       | PrivateType()
    *       | ProtectedType()
    */
   public R visit(AccessType n, A argu) {
      R _ret=null;
      _ret=n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   public R visit(ArrayType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      _ret=(R)INT_ARRAY_TYPE;
      return _ret;
   }

   /**
    * f0 -> "boolean"
    */
   public R visit(BooleanType n, A argu) {
      R _ret=null;
      _ret=n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "int"
    */
   public R visit(IntegerType n, A argu) {
      R _ret=null;
      _ret=n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "public"
    */
   public R visit(PublicType n, A argu) {
      R _ret=null;
      _ret=n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "private"
    */
   public R visit(PrivateType n, A argu) {
      R _ret=null;
      _ret=n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "protected"
    */
   public R visit(ProtectedType n, A argu) {
      R _ret=null;
      _ret=n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
   public R visit(Statement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public R visit(Block n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public R visit(AssignmentStatement n, A argu) {
      R _ret=null;
      String identifier_name=(String)n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      R eresult=n.f2.accept(this, argu);
      n.f3.accept(this, argu);

      Integer expr_result=(Integer)eresult;
      if(is_local_variable(identifier_name)) {
         Integer offset=get_local_variable_offset(identifier_name);
         display("MOVE TEMP " + offset + " TEMP " + expr_result);
      } else if(is_argument(identifier_name)) {
         Integer offset=get_argument_offset(identifier_name);
         display("MOVE TEMP " + offset + " TEMP " + expr_result);
      } else {
         Integer offset=get_class_variable_offset(identifier_name);
         display("HSTORE TEMP 0 " + 4*offset + " TEMP " + expr_result);
      }

      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
   public R visit(ArrayAssignmentStatement n, A argu) {
      R _ret=null;
      String identifier_name=(String)n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      R eresult1=n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      R eresult2=n.f5.accept(this, argu);
      n.f6.accept(this, argu);

      Integer expr1_result=(Integer)eresult1;
      Integer expr2_result=(Integer)eresult2;
      Integer array_base=generate_fresh_temp();

      if(is_local_variable(identifier_name)) {
         Integer offset=get_local_variable_offset(identifier_name);
         display("MOVE TEMP " + array_base + " TEMP " + offset);
      } else if(is_argument(identifier_name)) {
         Integer offset=get_argument_offset(identifier_name);
         display("MOVE TEMP " + array_base + " TEMP " + offset);
      } else {
         Integer offset=get_class_variable_offset(identifier_name);
         display("HLOAD TEMP " + array_base + " TEMP 0 " + 4*offset);
      }

      Integer expr1_result_upd=generate_fresh_temp();
      Integer number_one=generate_fresh_temp();
      Integer number_four=generate_fresh_temp();
      Integer offset_address=generate_fresh_temp();
      Integer offset_temp=generate_fresh_temp();
      display("MOVE TEMP " + number_one + " 1");
      display("MOVE TEMP " + expr1_result_upd + " PLUS TEMP " + expr1_result + " TEMP " + number_one);
      display("MOVE TEMP " + number_four + " 4");
      display("MOVE TEMP " + offset_temp + " TIMES TEMP " + expr1_result_upd + " TEMP " + number_four);
      display("MOVE TEMP " + offset_address + " PLUS TEMP " + array_base + " TEMP " + offset_temp);

      display("HSTORE TEMP " + offset_address + " 0 TEMP " + expr2_result);

      return _ret;
   }

   /**
    * f0 -> IfthenElseStatement()
    *       | IfthenStatement()
    */
   public R visit(IfStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public R visit(IfthenStatement n, A argu) {
      R _ret=null;

      Integer end_of_if_statement=generate_fresh_label();

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      R eresult=n.f2.accept(this, argu);

      Integer expr_result=(Integer)eresult;
      display("CJUMP TEMP " + expr_result + " L" + end_of_if_statement);

      n.f3.accept(this, argu);
      n.f4.accept(this, argu);

      display("L"+end_of_if_statement);
      display("NOOP");

      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   public R visit(IfthenElseStatement n, A argu) {
      R _ret=null;

      Integer start_of_else_statement=generate_fresh_label();
      Integer end_of_if_else_statement=generate_fresh_label();

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);

      R eresult=n.f2.accept(this, argu);
      Integer expr_result=(Integer)eresult;
      display("CJUMP TEMP " + expr_result + " L" + start_of_else_statement);

      n.f3.accept(this, argu);
      n.f4.accept(this, argu);

      display("JUMP L" + end_of_if_else_statement);
      display("L"+start_of_else_statement);

      n.f5.accept(this, argu);
      n.f6.accept(this, argu);

      display("L"+end_of_if_else_statement);
      display("NOOP");

      return _ret;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public R visit(WhileStatement n, A argu) {
      R _ret=null;

      Integer start_of_loop_iteration=generate_fresh_label();
      Integer end_of_while_statement=generate_fresh_label();

      display("L" + start_of_loop_iteration);

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      R eresult=n.f2.accept(this, argu);
      Integer expr_result=(Integer)eresult;

      display("CJUMP TEMP " + expr_result + " L" + end_of_while_statement);

      n.f3.accept(this, argu);
      n.f4.accept(this, argu);

      display("JUMP L" + start_of_loop_iteration);
      display("L"+end_of_while_statement);
      display("NOOP");

      return _ret;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
   public R visit(PrintStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      R eresult=n.f2.accept(this, argu);
      Integer expr_result=(Integer)eresult;

      display("PRINT TEMP " + expr_result);

      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> OrExpression()
    *       | AndExpression()
    *       | CompareExpression()
    *       | neqExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | DivExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | TernaryExpression()
    *       | PrimaryExpression()
    */
   public R visit(Expression n, A argu) {
      R _ret=null;
      _ret=n.f0.accept(this, null);

      last_expression_result=_ret;

      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
   public R visit(AndExpression n, A argu) {
      R _ret=null;

      Integer start_of_second_condition=generate_fresh_label();
      Integer false_result=generate_fresh_label();
      Integer end_of_and_expression=generate_fresh_label();

      Integer fresh_temp=generate_fresh_temp();

      R eresult1=n.f0.accept(this, argu);
      Integer expr1_result=(Integer)eresult1;

      display("CJUMP TEMP " + expr1_result + " L" + false_result);
      display("L" + start_of_second_condition);

      n.f1.accept(this, argu);
      R eresult2=n.f2.accept(this, argu);
      Integer expr2_result=(Integer)eresult2;

      display("CJUMP TEMP " + expr2_result + " L" + false_result);
      display("MOVE TEMP " + fresh_temp + " 1");
      display("JUMP L"+end_of_and_expression);
      display("L" + false_result);
      display("MOVE TEMP " + fresh_temp + " 0");
      display("L" + end_of_and_expression);
      display("NOOP");

      _ret=(R)fresh_temp;
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "||"
    * f2 -> PrimaryExpression()
    */
   public R visit(OrExpression n, A argu) {
      R _ret=null;

      Integer start_of_second_condition=generate_fresh_label();
      Integer true_result=generate_fresh_label();
      Integer end_of_and_expression=generate_fresh_label();

      Integer fresh_temp=generate_fresh_temp();

      R eresult1=n.f0.accept(this, argu);
      Integer expr1_result=(Integer)eresult1;

      display("CJUMP TEMP " + expr1_result + " L" + true_result);
      display("L" + start_of_second_condition);

      n.f1.accept(this, argu);
      R eresult2=n.f2.accept(this, argu);
      Integer expr2_result=(Integer)eresult2;

      display("CJUMP TEMP " + expr2_result + " L" + true_result);
      display("MOVE TEMP " + fresh_temp + " 0");
      display("JUMP L" + end_of_and_expression);
      display("L" + true_result);
      display("MOVE TEMP " + fresh_temp + " 1");
      display("L" + end_of_and_expression);

      _ret=(R)fresh_temp;
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<="
    * f2 -> PrimaryExpression()
    */
   public R visit(CompareExpression n, A argu) {
      R _ret=null;

      Integer fresh_temp=generate_fresh_temp();

      R eresult1=n.f0.accept(this, argu);
      Integer expr1_result=(Integer)eresult1;

      n.f1.accept(this, argu);

      R eresult2=n.f2.accept(this, argu);
      Integer expr2_result=(Integer)eresult2;

      display("MOVE TEMP " +fresh_temp + " LE TEMP " + expr1_result + " TEMP " + expr2_result);
      _ret=(R)fresh_temp;

      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "!="
    * f2 -> PrimaryExpression()
    */
   public R visit(neqExpression n, A argu) {
      R _ret=null;
      Integer fresh_temp=generate_fresh_temp();

      R eresult1=n.f0.accept(this, argu);
      Integer expr1_eresult=(Integer)eresult1;

      n.f1.accept(this, argu);
      
      R eresult2=n.f2.accept(this, argu);
      Integer expr2_eresult=(Integer)eresult2;

      display("MOVE TEMP " + fresh_temp + " NE TEMP " + expr1_eresult + " TEMP " + expr2_eresult);
      _ret=(R)fresh_temp;
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public R visit(PlusExpression n, A argu) {
      R _ret=null;

      Integer fresh_temp=generate_fresh_temp();

      R eresult1=n.f0.accept(this, argu);
      Integer expr1_result=(Integer)eresult1;

      n.f1.accept(this, argu);

      R eresult2=n.f2.accept(this, argu);
      Integer expr2_result=(Integer)eresult2;

      display("MOVE TEMP " + fresh_temp + " PLUS TEMP " + expr1_result + " TEMP " + expr2_result);
      _ret=(R)fresh_temp;
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public R visit(MinusExpression n, A argu) {
      R _ret=null;

      Integer fresh_temp=generate_fresh_temp();

      R eresult1=n.f0.accept(this, argu);
      Integer expr1_result=(Integer)eresult1;

      n.f1.accept(this, argu);

      R eresult2=n.f2.accept(this, argu);
      Integer expr2_result=(Integer)eresult2;

      display("MOVE TEMP " + fresh_temp + " MINUS TEMP " + expr1_result + " TEMP " + expr2_result);
      _ret=(R)fresh_temp;
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public R visit(TimesExpression n, A argu) {
      R _ret=null;

      Integer fresh_temp=generate_fresh_temp();

      R eresult1=n.f0.accept(this, argu);
      Integer expr1_result=(Integer)eresult1;

      n.f1.accept(this, argu);

      R eresult2=n.f2.accept(this, argu);
      Integer expr2_result=(Integer)eresult2;

      display("MOVE TEMP " + fresh_temp + " TIMES TEMP " + expr1_result + " TEMP " + expr2_result);
      _ret=(R)fresh_temp;
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "/"
    * f2 -> PrimaryExpression()
    */
   public R visit(DivExpression n, A argu) {
      R _ret=null;

      Integer fresh_temp=generate_fresh_temp();

      R eresult1=n.f0.accept(this, argu);
      Integer expr1_result=(Integer)eresult1;

      n.f1.accept(this, argu);

      R eresult2=n.f2.accept(this, argu);
      Integer expr2_result=(Integer)eresult2;

      display("MOVE TEMP " + fresh_temp + " DIV TEMP " + expr1_result + " TEMP " + expr2_result);
      _ret=(R)fresh_temp;
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   public R visit(ArrayLookup n, A argu) {
      R _ret=null;
      R eresult1=n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      R eresult2=n.f2.accept(this, argu);
      n.f3.accept(this, argu);

      Integer array_base=(Integer)eresult1;
      Integer index=(Integer)eresult2;

      Integer result=generate_fresh_temp();
      Integer number_one=generate_fresh_temp();
      Integer number_four=generate_fresh_temp();
      Integer index_upd=generate_fresh_temp();
      Integer offset_address=generate_fresh_temp();
      Integer offset_temp=generate_fresh_temp();
      display("MOVE TEMP " + number_one + " 1");
      display("MOVE TEMP " + index_upd + " PLUS TEMP " + index + " TEMP " + number_one);
      display("MOVE TEMP " + number_four + " 4");
      display("MOVE TEMP " + offset_temp + " TIMES TEMP " + index_upd + " TEMP " + number_four);
      display("MOVE TEMP " + offset_address + " PLUS TEMP " + array_base + " TEMP " + offset_temp);
      display("HLOAD TEMP " + result + " TEMP " + offset_address + " " + 0);
      _ret=(R)result;

      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   public R visit(ArrayLength n, A argu) {
      R _ret=null;
      R eresult=n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      Integer array_base=(Integer)eresult;
      Integer result=generate_fresh_temp();
      display("HLOAD TEMP " + result + " TEMP " + array_base + " " + 0);
      _ret=(R)result;

      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
   public R visit(MessageSend n, A argu) {
      R _ret=null;
      Integer expr_result=(Integer)n.f0.accept(this, (A)"message_send");
      n.f1.accept(this, argu);
      String function_name=(String)n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      R elist=n.f4.accept(this, argu);
      n.f5.accept(this, argu);

      ArrayList<Object> expression_list=elist==null ? new ArrayList<Object>() : (ArrayList<Object>) elist;
      Integer function_result=generate_fresh_temp();
      String object_class=class_record_name_map.get(expr_result);
      String unique_method=object_class + "#" + function_name;

      String[] parts=unique_method.split("#");
      // if(function_name.compareTo("Remove") == 0) {
      //    debug("++++++++");
      //    debug(parts[0] + " " + parts[1]);
      //    debug(classes.get(object_class).methods.get(parts[1]).arguments.size());
      //    debug(expression_list.size());
      // }
      
      Integer function_offset=get_function_offset(unique_method);
      Integer function_table=generate_fresh_temp();
      Integer function_pointer=generate_fresh_temp();
      display("HLOAD TEMP " + function_table + " TEMP " + expr_result + " 0");
      display("HLOAD TEMP " + function_pointer + " TEMP " + function_table + " " + 4*(function_offset-1));
      System.out.print("MOVE TEMP " + function_result + " CALL TEMP " + function_pointer + " ( TEMP " + expr_result + " ");

      for(int i=0; i<expression_list.size(); i++)
         System.out.print(" TEMP " + expression_list.get(i));
      display(")");

      String return_type=get_return_type(unique_method);
      class_record_name_map.put(function_result, return_type);

      _ret=(R)function_result;
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "?"
    * f2 -> PrimaryExpression()
    * f3 -> ":"
    * f4 -> PrimaryExpression()
    */
   public R visit(TernaryExpression n, A argu) {
      R _ret=null;

      Integer start_of_second_option=generate_fresh_label();
      Integer end_of_ternary_expression=generate_fresh_label();

      Integer fresh_temp=generate_fresh_temp();

      R eresult1=n.f0.accept(this, argu);
      Integer expr1_result=(Integer)eresult1;

      n.f1.accept(this, argu);

      display("CJUMP TEMP " + expr1_result + " L" + start_of_second_option);

      R eresult2=n.f2.accept(this, argu);
      Integer expr2_result=(Integer)eresult2;

      n.f3.accept(this, argu);

      display("MOVE TEMP " + fresh_temp + " TEMP " + expr2_result);
      display("JUMP L" + end_of_ternary_expression);
      display("L" + start_of_second_option);
      
      R eresult3=n.f4.accept(this, argu);
      Integer expr3_result=(Integer)eresult3;

      display("MOVE TEMP " + fresh_temp + " TEMP " + expr3_result);
      display("L" + end_of_ternary_expression);
      display("NOOP");
      _ret=(R)fresh_temp;

      return _ret;
   }

   /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
   public R visit(ExpressionList n, A argu) {
      R _ret=null;

      ArrayList<Integer> expression_list=new ArrayList<>();

      Object eresult=n.f0.accept(this, argu);
      ArrayList<Object> rest=(ArrayList<Object>)n.f1.accept(this, argu);
      rest.add(0, eresult);

      _ret=(R)rest;
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
   public R visit(ExpressionRest n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */
   public R visit(PrimaryExpression n, A argu) {
      R _ret=null;

      if(argu!=null && ((String)argu).compareTo("message_send")==0)
         _ret=n.f0.accept(this, (A)"message_send");
      else
         _ret=n.f0.accept(this, (A)"primary_expression");
      
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n, A argu) {
      R _ret=null;

      R lit=n.f0.accept(this, argu);
      Integer fresh_temp=generate_fresh_temp();
      Integer literal=Integer.parseInt((String)lit);
      display("MOVE TEMP " + fresh_temp + " " + literal);
      _ret=(R)fresh_temp;

      return _ret;
   }

   /**
    * f0 -> "true"
    */
   public R visit(TrueLiteral n, A argu) {
      R _ret=null;

      Integer fresh_temp=generate_fresh_temp();
      n.f0.accept(this, argu);

      display("MOVE TEMP " + fresh_temp + " " + 1);
      _ret=(R)fresh_temp;

      return _ret;
   }

   /**
    * f0 -> "false"
    */
   public R visit(FalseLiteral n, A argu) {
      R _ret=null;
      Integer fresh_temp=generate_fresh_temp();
      n.f0.accept(this, argu);

      display("MOVE TEMP " + fresh_temp + " " + 0);
      _ret=(R)fresh_temp;

      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Identifier n, A argu) {
      R _ret=null;

      _ret=n.f0.accept(this, argu);

      if(argu!=null && (((String)argu).compareTo("primary_expression")==0 || ((String)argu).compareTo("message_send")==0)) {
         String identifier_name=(String) _ret;
         Integer fresh_temp=generate_fresh_temp();
         String identifier_class;

         if(is_local_variable(identifier_name)) {
            identifier_class=get_local_variable_type(identifier_name);
            Integer offset=get_local_variable_offset(identifier_name);
            display("MOVE TEMP " + fresh_temp + " TEMP " + offset);
         } else if(is_argument(identifier_name)) {
            identifier_class=get_argument_type(identifier_name);
            Integer offset=get_argument_offset(identifier_name);
            display("MOVE TEMP " + fresh_temp + " TEMP " + offset);
         } else {
            identifier_class=get_class_variable_type(identifier_name);
            Integer offset=get_class_variable_offset(identifier_name);
            display("HLOAD TEMP " + fresh_temp + " TEMP 0 " + 4*offset);
         }

         if(((String)argu).compareTo("message_send")==0) {
            class_record_name_map.put(fresh_temp, identifier_class);
         }

         _ret=(R) fresh_temp;
         argu=null;
      }

      return _ret;
   }

   /**
    * f0 -> "this"
    */
   public R visit(ThisExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);

      Integer zero=0;
      _ret=(R) zero;
      class_record_name_map.put(zero, class_name);
      
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
   public R visit(ArrayAllocationExpression n, A argu) {
      R _ret=null;
      argu=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      R eresult=n.f3.accept(this, argu);
      n.f4.accept(this, argu);

      Integer length_variable=(Integer)eresult;

      Integer number_one=generate_fresh_temp();
      Integer number_four=generate_fresh_temp();
      Integer slots_to_allocate=generate_fresh_temp();
      Integer size_bytes=generate_fresh_temp();
      Integer array_variable=generate_fresh_temp();

      display("MOVE TEMP " + number_one + " 1");
      display("MOVE TEMP " + slots_to_allocate + " PLUS TEMP " + length_variable + " TEMP " + number_one);
      display("MOVE TEMP " + number_four + " 4");
      display("MOVE TEMP " + size_bytes + " TIMES TEMP " + slots_to_allocate + " TEMP " + number_four);
      display("MOVE TEMP " + array_variable + " HALLOCATE TEMP " + size_bytes);
      display("HSTORE TEMP " + array_variable + " 0 TEMP " + length_variable);

      _ret=(R)array_variable;
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public R visit(AllocationExpression n, A argu) {
      R _ret=null;
      argu=null;
      n.f0.accept(this, argu);
      String object_class=(String)n.f1.accept(this, argu);

      Integer class_record_size=generate_fresh_temp();
      Integer class_record=generate_fresh_temp();

      Integer class_record_size_bytes=4*(1+get_class_record_size(object_class));
      display("MOVE TEMP " + class_record_size + " " + class_record_size_bytes);
      display("MOVE TEMP " + class_record + " HALLOCATE TEMP " + class_record_size);

      ArrayList<String> functions=new ArrayList<>();
      String current_class=object_class;
      while(current_class.compareTo("")!=0) {
         class_details selected_class=classes.get(current_class);
         functions.addAll(selected_class.method_list);
         current_class=classes.get(current_class).parent_class;
      }

      Integer function_table_size=generate_fresh_temp();
      Integer function_table=generate_fresh_temp();
      Integer function_table_size_bytes=4*functions.size();

      display("MOVE TEMP " + function_table_size + " " + function_table_size_bytes);
      display("MOVE TEMP " + function_table + " HALLOCATE TEMP " + function_table_size);

      for(int i=0; i<functions.size(); i++) {
         Integer function_label=generate_fresh_temp();

         String[] parts=functions.get(i).split("#");
         String unique_function=parts[0]+"_"+parts[1];

         display("MOVE TEMP " + function_label + " " + unique_function);
         display("HSTORE TEMP " + function_table + " " + 4*i + " TEMP " + function_label);
      }

      display("HSTORE TEMP " + class_record + " " + 0 + " TEMP " + function_table);

      class_record_name_map.put(class_record, object_class);
      _ret=(R) class_record;

      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "!"
    * f1 -> Expression()
    */
   public R visit(NotExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      R eresult=n.f1.accept(this, null);
      Integer expr_result=(Integer)eresult;

      Integer false_result=generate_fresh_label();
      Integer end_of_not_expression=generate_fresh_label();

      Integer fresh_temp=generate_fresh_temp();

      display("CJUMP TEMP " + expr_result + " L" + false_result);
      display("MOVE TEMP " + fresh_temp + " " + 0);
      display("JUMP L" + end_of_not_expression);
      display("L" + false_result);
      display("MOVE TEMP " + fresh_temp + " " + 1);
      display("L" + end_of_not_expression);

      _ret=(R)fresh_temp;

      return _ret;
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   public R visit(BracketExpression n, A argu) {
      R _ret=null;
      argu=null;
      n.f0.accept(this, argu);
      R temp=n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      _ret=temp;

      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> ( IdentifierRest() )*
    */
   public R visit(IdentifierList n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Identifier()
    */
   public R visit(IdentifierRest n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

}
