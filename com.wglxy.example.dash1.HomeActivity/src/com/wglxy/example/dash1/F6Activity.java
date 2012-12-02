

package com.wglxy.example.dash1;

import android.content.Intent;
import android.os.Bundle;

/**
 * This is the activity for feature 6 in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */

public class F6Activity extends HomeActivity 
{

/**
 * onCreate
 *
 * Called when the activity is first created. 
 * This is where you should do all of your normal static set up: create views, bind data to lists, etc. 
 * This method also provides you with a Bundle containing the activity's previously frozen state, if there was one.
 * 
 * Always followed by onStart().
 *
 * @param savedInstanceState Bundle
 */

protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_HOME);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    finish();
}
    
} // end class
