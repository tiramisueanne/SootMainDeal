package de.ecspride;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

public class LoopExampleInt extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_example1);
        
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId(); // Source
		
		String obfuscated = "";
		for (char c : imei.toCharArray())
			obfuscated += c + "_";
		
		SmsManager sm = SmsManager.getDefault();

		sm.sendTextMessage("+49 1234", null, obfuscated, null, null); // Default sink

        /*  Sinks */

        int tainted = obfuscated.length();

        for (int i = 0; i < tainted ; i++)
            System.out.println("I'm a for-loop sink!");

        int j = 0;
        while (j < tainted) {
            System.out.println("I'm a while-loop sink!");
            j++;
        }

        int k = 0;
        do {
            System.out.println("I'm a do-while-loop sink!");
            k++;
        } while (k < tainted);

        if (obfuscated.charAt(0) == 'a')
            System.out.println("I start with an 'a'!");
        else
            System.out.println("I don't start with an 'a'!");

    }    
}