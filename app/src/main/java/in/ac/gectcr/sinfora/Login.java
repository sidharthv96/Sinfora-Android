package in.ac.gectcr.sinfora;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private void newPIN(){
        Button save = (Button) findViewById(R.id.save);
        final EditText pin = (EditText) findViewById(R.id.pin);
        save.setVisibility(View.VISIBLE);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pin.getEditableText().toString().length()<4) {
                    Toast.makeText(getApplicationContext(), "Min length 4 required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                editor.putString(getString(R.string.pin), pin.getEditableText().toString());
                pin.getEditableText().clear();
                editor.commit();
                Toast.makeText(getApplicationContext(),"PIN updated",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),Web.class);
                i.putExtra(getString(R.string.login),true);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button save = (Button) findViewById(R.id.save);
        EditText pin = (EditText) findViewById(R.id.pin);
        save.setVisibility(View.INVISIBLE);
        pin.getEditableText().clear();
        pin.requestFocus();
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(pin, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
//        final SharedPreferences.Editor editor = sharedPref.edit();

        Button forgot = (Button) findViewById(R.id.forgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                CookieManager cookieManager = CookieManager.getInstance();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    CookieSyncManager.createInstance(getApplicationContext());
                    cookieManager.removeAllCookie();

                }
                cookieManager.removeAllCookies(null);
                newPIN();
            }
        });
        EditText pin = (EditText) findViewById(R.id.pin);
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(pin, InputMethodManager.SHOW_IMPLICIT);
//        pin.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
//        pin.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0));
        pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() < 4)
                    return;

                if(s.toString().equals(sharedPref.getString(getString(R.string.pin),"0000000000"))){
                    s.clear();
                    Intent i = new Intent(getApplicationContext(),Web.class);
                    i.putExtra(getString(R.string.login),true);
                    startActivity(i);
                }

            }
        });
        pin.requestFocus();
    }
}
