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
public class Pass<R,A> implements GJVisitor<R,A> {

Boolean is_reg=false;
Boolean is_lit=false;
Boolean is_label=false;

Boolean is_spill_status=false;

String dest_reg=null;
Boolean is_simple_exp=false;

String method_name=null;
Integer arg_count=null;
Integer stack_slots=null;
Integer max_call_args=null;

public R visit(NodeList n, A argu) {
    R _ret=null;
    int _count=0;
    for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
        e.nextElement().accept(this,argu);
        _count++;
    }
    return _ret;
}

public R visit(NodeListOptional n, A argu) {
    if ( n.present() ) {
        R _ret=null;
        int _count=0;
        for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
        e.nextElement().accept(this,argu);
        _count++;
        }
        return _ret;
    }
    else
        return null;
}

public R visit(NodeOptional n, A argu) {
    if ( n.present() ) {
        String label=(String)n.node.accept(this, argu);
        if(!is_spill_status)
            System.out.println(label + ":\tnop");
        is_spill_status=false;
        return (R)label;
    } else {
        return null;
    }
}

public R visit(NodeSequence n, A argu) {
    R _ret=null;
    int _count=0;
    for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
        e.nextElement().accept(this,argu);
        _count++;
    }
    return _ret;
}

public R visit(NodeToken n, A argu) { return (R) n.tokenImage; }

//
// User-generated visitor methods below
//

/**
* f0 -> "MAIN"
* f1 -> "["
* f2 -> IntegerLiteral()
* f3 -> "]"
* f4 -> "["
* f5 -> IntegerLiteral()
* f6 -> "]"
* f7 -> "["
* f8 -> IntegerLiteral()
* f9 -> "]"
* f10 -> StmtList()
* f11 -> "END"
* f12 -> ( SpillInfo() )?
* f13 -> ( Procedure() )*
* f14 -> <EOF>
*/
/*
* n1: number of args
* n2: number of stack slots
* n3: max number of args in function call
*/
public R visit(Goal n, A argu) {
    R _ret=null;
    method_name=(String)n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    arg_count=Integer.parseInt((String)n.f2.accept(this, argu));
    n.f3.accept(this, argu);
    n.f4.accept(this, argu);
    stack_slots=Integer.parseInt((String)n.f5.accept(this, argu));
    n.f6.accept(this, argu);
    n.f7.accept(this, argu);
    max_call_args=Integer.parseInt((String)n.f8.accept(this, argu));
    n.f9.accept(this, argu);

    System.out.println("\t.text");
    System.out.println("\t.globl main");
    System.out.println("main:");

    System.out.println("\tsw $ra, -4($sp)");
    System.out.println("\tmove $fp, $sp");
    System.out.println("\tsubu $sp, $sp, " + 4*(2+stack_slots));

    n.f10.accept(this, argu);
    n.f11.accept(this, argu);
    n.f12.accept(this, argu);

    System.out.println("\tlw $ra, -4($fp)");
    System.out.println("\taddu $sp, $sp, " + 4*(2+stack_slots));
    System.out.println("\tjal _exitret\n\n");

    n.f13.accept(this, argu);
    n.f14.accept(this, argu);

    System.out.println("\t.text");
    System.out.println("\t.globl _halloc");
    System.out.println("_halloc:");
    System.out.println("\tli $v0, 9");
    System.out.println("\tsyscall");
    System.out.println("\tjr $ra");
    System.out.println();
    System.out.println("\t.text"); 
    System.out.println("\t.globl _error");
    System.out.println("_error:");
    System.out.println("\tli $v0, 4");
    System.out.println("\tsyscall");
    System.out.println("\tli $v0, 10");
    System.out.println("\tsyscall");
    System.out.println();
    System.out.println("\t.text");
    System.out.println("\t.globl _print"); 
    System.out.println("_print:");
    System.out.println("\tli $v0, 1");
    System.out.println("\tsyscall");
    System.out.println("\tla $a0, newline"); 
    System.out.println("\tli $v0, 4");
    System.out.println("\tsyscall");
    System.out.println("\tjr $ra");
    System.out.println();
    System.out.println("\t.text ");
    System.out.println("\t.globl _exitret");
    System.out.println("_exitret: ");
    System.out.println("\tli $v0, 10");
    System.out.println("\tsyscall ");
    System.out.println("\t.data");
    System.out.println("\t.align 0");
    System.out.println();
    System.out.println("newline: 	.asciiz \"\\n\"");
    System.out.println();
    System.out.println("\t.data");
    System.out.println("\t.align 0");
    System.out.println("error_msg: .asciiz \" ERROR : Abnormally terminated!\n ");


    return _ret;
}

/**
* f0 -> ( ( Label() )? Stmt() )*
*/
public R visit(StmtList n, A argu) {
    R _ret=null;
    n.f0.accept(this, argu);
    return _ret;
}

/**
* f0 -> Label()
* f1 -> "["
* f2 -> IntegerLiteral()
* f3 -> "]"
* f4 -> "["
* f5 -> IntegerLiteral()
* f6 -> "]"
* f7 -> "["
* f8 -> IntegerLiteral()
* f9 -> "]"
* f10 -> StmtList()
* f11 -> "END"
* f12 -> ( SpillInfo() )?
*/
public R visit(Procedure n, A argu) {
    R _ret=null;
    method_name=(String)n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    arg_count=Integer.parseInt((String)n.f2.accept(this, argu));
    n.f3.accept(this, argu);
    n.f4.accept(this, argu);
    stack_slots=Integer.parseInt((String)n.f5.accept(this, argu));
    n.f6.accept(this, argu);
    n.f7.accept(this, argu);
    max_call_args=Integer.parseInt((String)n.f8.accept(this, argu));
    n.f9.accept(this, argu);

    System.out.println("\t.text");
    System.out.println("\t.globl " + method_name);
    System.out.println(method_name + ":");

    System.out.println("\tsw $ra, -4($sp)");
    System.out.println("\tsw $fp, -8($sp)");
    System.out.println("\tmove $fp, $sp");
    System.out.println("\tsubu $sp, $sp, " + 4*(2+stack_slots));

    n.f10.accept(this, argu);
    n.f11.accept(this, argu);
    n.f12.accept(this, argu);

    System.out.println("\tlw $ra, -4($fp)");
    System.out.println("\tlw $fp, -8($fp)");
    System.out.println("\taddu $sp, $sp, " + 4*(2+stack_slots));
    System.out.println("\tjr $ra\n\n");

    return _ret;
}

/**
* f0 -> NoOpStmt()
*       | ErrorStmt()
*       | CJumpStmt()
*       | JumpStmt()
*       | HStoreStmt()
*       | HLoadStmt()
*       | MoveStmt()
*       | PrintStmt()
*       | ALoadStmt()
*       | AStoreStmt()
*       | PassArgStmt()
*       | CallStmt()
*/
public R visit(Stmt n, A argu) {
    R _ret=null;
    n.f0.accept(this, argu);
    return _ret;
}

/**
* f0 -> "NOOP"
*/
public R visit(NoOpStmt n, A argu) {
    R _ret=null;
    n.f0.accept(this, argu);
    System.out.println("\tnop");
    return _ret;
}

/**
* f0 -> "ERROR"
*/
public R visit(ErrorStmt n, A argu) {
    R _ret=null;
    n.f0.accept(this, argu);
    return _ret;
}

/**
* f0 -> "CJUMP"
* f1 -> Reg()
* f2 -> Label()
*/
public R visit(CJumpStmt n, A argu) {
    R _ret=null;
    n.f0.accept(this, argu);
    String reg=(String)n.f1.accept(this, argu);
    String label=(String)n.f2.accept(this, argu);
    System.out.println("\tbeqz $" + reg + ", " + label);
    return _ret;
}

/**
* f0 -> "JUMP"
* f1 -> Label()
*/
public R visit(JumpStmt n, A argu) {
    R _ret=null;
    n.f0.accept(this, argu);
    String label=(String)n.f1.accept(this, argu);
    System.out.println("\tj " + label);
    return _ret;
}

/**
* f0 -> "HSTORE"
* f1 -> Reg()
* f2 -> IntegerLiteral()
* f3 -> Reg()
*/
public R visit(HStoreStmt n, A argu) {
    R _ret=null;
    n.f0.accept(this, argu);
    String addr=(String)n.f1.accept(this, argu);
    Integer offset=Integer.parseInt((String)n.f2.accept(this, argu));
    String val=(String)n.f3.accept(this, argu);
    System.out.println("\tsw $" + val + ", " + offset + "($" + addr + ")");
    return _ret;
}

/**
* f0 -> "HLOAD"
* f1 -> Reg()
* f2 -> Reg()
* f3 -> IntegerLiteral()
*/
public R visit(HLoadStmt n, A argu) {
    R _ret=null;
    n.f0.accept(this, argu);
    String val=(String)n.f1.accept(this, argu);
    String addr=(String)n.f2.accept(this, argu);
    Integer offset=Integer.parseInt((String)n.f3.accept(this, argu));
    System.out.println("\tlw $" + val + ", " + offset + "($" + addr + ")");
    return _ret;
}

/**
* f0 -> "MOVE"
* f1 -> Reg()
* f2 -> Exp()
*/
public R visit(MoveStmt n, A argu) {
    R _ret=null;
    n.f0.accept(this, argu);
    dest_reg=(String)n.f1.accept(this, argu);
    
    n.f2.accept(this, argu);

    return _ret;
}

/**
* f0 -> "PRINT"
* f1 -> SimpleExp()
*/
public R visit(PrintStmt n, A argu) {
    R _ret=null;
    n.f0.accept(this, argu);
    String simple_exp=(String)n.f1.accept(this, argu);
    if(is_reg)
        System.out.println("\tmove $a0, $" + simple_exp);
    else
        System.out.println("\tli $a0, " + simple_exp);
    System.out.println("\tjal _print");
    return _ret;
}

/**
* f0 -> "ALOAD"
* f1 -> Reg()
* f2 -> SpilledArg()
*/
public R visit(ALoadStmt n, A argu) {
    R _ret=null;
    n.f0.accept(this, argu);
    String reg=(String)n.f1.accept(this, argu);
    Integer spilled_arg=Integer.parseInt((String)n.f2.accept(this, argu));

    Integer number_of_stack_args =  Math.max(0, arg_count - 4);
    if(spilled_arg < number_of_stack_args)
        System.out.println("\tlw $" + reg + ", -" + 4*(spilled_arg+3) + "($fp)");
    else 
        System.out.println("\tlw $" + reg + ", " + 4*(spilled_arg-number_of_stack_args) + "($sp)");

    return _ret;
}

/**
* f0 -> "ASTORE"
* f1 -> SpilledArg()
* f2 -> Reg()
*/
public R visit(AStoreStmt n, A argu) {
    R _ret=null;
    n.f0.accept(this, argu);
    Integer spilled_arg=Integer.parseInt((String)n.f1.accept(this, argu));
    String reg=(String)n.f2.accept(this, argu);

    Integer number_of_stack_args =  Math.max(0, arg_count - 4);
    if(spilled_arg < number_of_stack_args)
        System.out.println("\tsw $" + reg + ", -" + 4*(spilled_arg+3) + "($fp)");
    else 
        System.out.println("\tsw $" + reg + ", " + 4*(spilled_arg-number_of_stack_args) + "($sp)");

    return _ret;
}

/**
* f0 -> "PASSARG"
* f1 -> IntegerLiteral()
* f2 -> Reg()
*/
public R visit(PassArgStmt n, A argu) {
    R _ret=null;
    n.f0.accept(this, argu);
    Integer passarg=Integer.parseInt((String)n.f1.accept(this, argu));
    String reg=(String)n.f2.accept(this, argu);

    System.out.println("\tsw $" + reg + ", -" + 4*(passarg+2) + "($sp)");

    return _ret;
}

/**
* f0 -> "CALL"
* f1 -> SimpleExp()
*/
public R visit(CallStmt n, A argu) {
    R _ret=null;
    n.f0.accept(this, argu);
    String simple_exp=(String)n.f1.accept(this, argu);
    if(is_reg)
        System.out.println("\tjalr $" + simple_exp);
    else
        System.out.println("\tjal " + simple_exp);
    return _ret;
}

/**
* f0 -> HAllocate()
*       | BinOp()
*       | SimpleExp()
*/
public R visit(Exp n, A argu) {
    R _ret=null;
    is_simple_exp=false;
    String exp=(String)n.f0.accept(this, argu);

    if(is_simple_exp) {
        if(is_reg)
            System.out.println("\tmove $" + dest_reg + ", $" + exp);
        else if(is_lit)
            System.out.println("\tli $" + dest_reg + ", " + exp);
        else
            System.out.println("\tla $" + dest_reg + ", " + exp);
    }

    return _ret;
}

/**
* f0 -> "HALLOCATE"
* f1 -> SimpleExp()
*/
public R visit(HAllocate n, A argu) {
    R _ret=null;
    n.f0.accept(this, argu);
    String simple_exp=(String)n.f1.accept(this, argu);

    if(is_reg)
        System.out.println("\tmove $a0, $" + simple_exp);
    else
        System.out.println("\tli $a0, " + simple_exp);
    System.out.println("\tjal _halloc");
    System.out.println("\tmove $" + dest_reg + ", $v0");
    is_simple_exp=false;
    return _ret;
}

/**
* f0 -> Operator()
* f1 -> Reg()
* f2 -> SimpleExp()
*/
public R visit(BinOp n, A argu) {
    R _ret=null;
    String op=(String)n.f0.accept(this, argu);
    String reg=(String)n.f1.accept(this, argu);
    String simple_exp=(String)n.f2.accept(this, argu);

    if(is_reg)
        System.out.println("\t" + op + " $" + dest_reg + ", $" + reg + ", $" + simple_exp);
    else
        System.out.println("\t" + op + " $" + dest_reg + ", $" + reg + ", " + simple_exp);
    is_simple_exp=false;
    return _ret;
}

/**
* f0 -> "LE"
*       | "NE"
*       | "PLUS"
*       | "MINUS"
*       | "TIMES"
*       | "DIV"
*/
public R visit(Operator n, A argu) {
    R _ret=null;
    String op=(String)n.f0.accept(this, argu);
    if(op.compareTo("LE") == 0)
        _ret=(R)"sle";
    else if(op.compareTo("NE") == 0)
        _ret=(R)"sne";
    else if(op.compareTo("PLUS") == 0)
        _ret=(R)"add";
    else if(op.compareTo("MINUS") == 0)
        _ret=(R)"sub";
    else if(op.compareTo("TIMES") == 0)
        _ret=(R)"mul";
    else if(op.compareTo("DIV") == 0)
        _ret=(R)"div";
    return _ret;
}

/**
* f0 -> "SPILLEDARG"
* f1 -> IntegerLiteral()
*/
public R visit(SpilledArg n, A argu) {
    R _ret=null;
    n.f0.accept(this, argu);
    _ret=n.f1.accept(this, argu);
    return _ret;
}

/**
* f0 -> Reg()
*       | IntegerLiteral()
*       | Label()
*/
public R visit(SimpleExp n, A argu) {
    R _ret=null;
    is_simple_exp=true;
    is_reg = is_lit = is_label = false;
    _ret=n.f0.accept(this, argu);
    return _ret;
}

/**
* f0 -> "a0"
*       | "a1"
*       | "a2"
*       | "a3"
*       | "t0"
*       | "t1"
*       | "t2"
*       | "t3"
*       | "t4"
*       | "t5"
*       | "t6"
*       | "t7"
*       | "s0"
*       | "s1"
*       | "s2"
*       | "s3"
*       | "s4"
*       | "s5"
*       | "s6"
*       | "s7"
*       | "t8"
*       | "t9"
*       | "v0"
*       | "v1"
*/
public R visit(Reg n, A argu) {
    R _ret=null;
    is_reg = true;
    _ret=n.f0.accept(this, argu);
    return _ret;
}

/**
* f0 -> <INTEGER_LITERAL>
*/
public R visit(IntegerLiteral n, A argu) {
    R _ret=null;
    is_lit = true;
    _ret=n.f0.accept(this, argu);
    return _ret;
}

/**
* f0 -> <IDENTIFIER>
*/
public R visit(Label n, A argu) {
    R _ret=null;
    is_label = true;
    _ret=n.f0.accept(this, argu);
    return _ret;
}

/**
* f0 -> "//"
* f1 -> SpillStatus()
*/
public R visit(SpillInfo n, A argu) {
    R _ret=null;
    is_spill_status=true;
    n.f0.accept(this, argu);
    _ret=n.f1.accept(this, argu);
    return _ret;
}

/**
* f0 -> <SPILLED>
*       | <NOTSPILLED>
*/
public R visit(SpillStatus n, A argu) {
    R _ret=null;
    _ret=n.f0.accept(this, argu);
    return _ret;
}

}
