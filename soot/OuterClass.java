import java.util.*;
import java.io.*;


public class OuterClass {

    // Method for all java Objects
    public void ourMethod(Object obj){}

    /* Since we're instrumenting at the IR level, Java autoboxing doesn't work.
    These methods cover the primitive cases. */

    public void ourMethod(int var){}
    public void ourMethod(boolean b){}
    public void ourMethod(char c){}
    public void ourMethod(byte b){}
    public void ourMethod(short s){}
    public void ourMethod(long l){}
    public void ourMethod(float f){}
    public void ourMethod(double d){}
 
}
