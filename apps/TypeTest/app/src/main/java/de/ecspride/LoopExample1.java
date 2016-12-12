package de.ecspride;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

public class LoopExample1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_example1);
        
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        SmsManager sm = SmsManager.getDefault();

        /* Sources */
        String int_id   = telephonyManager.getDeviceId();
        String doub_id  = telephonyManager.getDeviceId();
        String float_id = telephonyManager.getDeviceId();
        String short_id = telephonyManager.getDeviceId();
        String long_id  = telephonyManager.getDeviceId();
        String char_id  = telephonyManager.getDeviceId();
        String bool_id  = telephonyManager.getDeviceId();
        String byte_id  = telephonyManager.getDeviceId();

        /* Sinks */

        // Int Sinks
        for (int i = 0; i < int_id.length(); i++)
            System.out.println("I'm a for-loop sink!");
        int j = 0;
        while (j < int_id.length()) {
            System.out.println("I'm a while-loop sink!");
            j++;
        }
        int k = 0;
        do {
            System.out.println("I'm a do-while-loop sink!");
            k++;
        } while (k < int_id.length());


        // Boolean + Object Sinks
        String tainted = bool_id;
        for (; !tainted.isEmpty(); tainted = tainted.substring(0, tainted.length() - 1))
            System.out.println("I'm a for-loop sink!");
        while (!tainted.isEmpty()) {
            System.out.println("I'm a while-loop sink!");
            tainted = tainted.substring(0, tainted.length() - 1);
        }
        do {
            System.out.println("I'm a do-while-loop sink!");
            tainted = tainted.substring(0, tainted.length() - 1);
        } while (!tainted.isEmpty());


        // Byte Sinks
        byte tainted_byte = (byte) byte_id.length();
        for (byte i = 0; i < tainted_byte; i++)
            System.out.println("I'm a for-loop sink!");
        byte l = 0;
        while (l < tainted_byte) {
            System.out.println("I'm a while-loop sink!");
            l++;
        }
        byte m = 0;
        do {
            System.out.println("I'm a do-while-loop sink!");
            m++;
        } while (m < tainted_byte);


        // Char Sinks
        char tainted_char = (char) char_id.length();
        for (char i = 0; i < tainted_char; i++)
            System.out.println("I'm a for-loop sink!");
        char n = 0;
        while (n < tainted_char) {
            System.out.println("I'm a while-loop sink!");
            n++;
        }
        char o = 0;
        do {
            System.out.println("I'm a do-while-loop sink!");
            o++;
        } while (o < tainted_char);


        // Float Sinks
        float tainted_float = (float) float_id.length();
        for (float i = 0; i < tainted_float; i++)
            System.out.println("I'm a for-loop sink!");
        float p = 0;
        while (p < tainted_float) {
            System.out.println("I'm a while-loop sink!");
            p++;
        }
        float q = 0;
        do {
            System.out.println("I'm a do-while-loop sink!");
            q++;
        } while (q < tainted_float);


        // Double Sinks
        double tainted_double = (double) doub_id.length();
        for (double i = 0; i < tainted_double; i++)
            System.out.println("I'm a for-loop sink!");
        double r = 0;
        while (r < tainted_double) {
            System.out.println("I'm a while-loop sink!");
            r++;
        }
        double s = 0;
        do {
            System.out.println("I'm a do-while-loop sink!");
            s++;
        } while (s < tainted_double);


        // Long Sinks
        long tainted_long = (long) long_id.length();
        for (long i = 0; i < tainted_long; i++)
            System.out.println("I'm a for-loop sink!");
        long t = 0;
        while (t < tainted_long) {
            System.out.println("I'm a while-loop sink!");
            t++;
        }
        long u = 0;
        do {
            System.out.println("I'm a do-while-loop sink!");
            u++;
        } while (u < tainted_long);


        // Short Sinks
        short tainted_short = (short) short_id.length();
        for (short i = 0; i < tainted_short; i++)
            System.out.println("I'm a for-loop sink!");
        short v = 0;
        while (v < tainted_short) {
            System.out.println("I'm a while-loop sink!");
            v++;
        }
        short w = 0;
        do {
            System.out.println("I'm a do-while-loop sink!");
            w++;
        } while (w < tainted_short);

    }    
}
