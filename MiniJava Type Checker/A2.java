import syntaxtree.*;
import visitor.*;

public class A2 {
   public static void main(String [] args) {
      try {
         Node root = new MiniJavaParser(System.in).Goal();
         GJDepthFirst obj=new GJDepthFirst();
         root.accept(obj, "construct_symbol_table");
         root.accept(obj, "parse_and_type_check");
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 

