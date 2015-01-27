package co.mobilemakers.sandwichrestaurant;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    final static int MAX_SANDWICH_COUNT = 5;

    EditText mEditSandwichCount;
    Button mButtonNext;

    protected class SandwichCountListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            boolean enableButton = !s.toString().isEmpty();
            if (enableButton) {
                enableButton = Integer.valueOf(s.toString()) <= MAX_SANDWICH_COUNT;
            }
            mButtonNext.setEnabled(enableButton);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controlsToVars();
        setListeners();
    }

    private void controlsToVars() {
        mEditSandwichCount = (EditText)findViewById(R.id.edit_sandwich_count);
        mButtonNext = (Button)findViewById(R.id.button_next);
    }

    private void setListeners() {
        mEditSandwichCount.addTextChangedListener(new SandwichCountListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
