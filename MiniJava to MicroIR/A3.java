import syntaxtree.*;
import visitor.*;

public class A3 {
   public static void main(String [] args) {
      try {
         Node root = new MiniJavaParser(System.in).Goal();
         Object symbolTable=root.accept(new Pass1(), null);
         root.accept(new Pass2(), symbolTable);
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 

