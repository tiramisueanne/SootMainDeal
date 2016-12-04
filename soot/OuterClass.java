import java.util.*;
import java.io.*;

public class OuterClass {
    public void ourMethod(Object obj) {
        //does nothing!! Wow!
        return;
    }

    //covering primitives
    public void ourMethod(int var){
        return;
    }
}
