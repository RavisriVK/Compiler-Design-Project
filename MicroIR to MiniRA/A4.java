import syntaxtree.*;
import visitor.*;

public class A4 {
    public static void main(String [] args) {
        try {
            Node root = new microIRParser(System.in).Goal();

            Object labelInfo=root.accept(new Pass1(), null);
            Object allocationInfo=root.accept(new Pass2(), labelInfo);
            root.accept(new Pass3(), allocationInfo);

        }
        catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
} 