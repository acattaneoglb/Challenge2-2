package co.mobilemakers.sandwichrestaurant;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;


public class SandwichStudioActivity extends ActionBarActivity {

    final static public String SANDWICH_ORDER = "SANDWICH_ORDER";

    int sandwichPos;
    int lastSandwich;

    ArrayList<SandwichModel> sandwichList = new ArrayList<>();

    TextView mTextSandwichNumber;
    RadioButton mRadioWheat, mRadioWhite, mRadioRye;
    CheckBox mCheckTomato, mCheckLattuce, mCheckOnion, mCheckCarrot,
            mCheckSesame, mCheckOlives, mCheckHam, mCheckCheese;
    Button mButtonNext;

    private void controlsToVars() {
        mTextSandwichNumber = (TextView)findViewById(R.id.text_sandwich_number);

        mRadioWheat = (RadioButton)findViewById(R.id.radio_bread_wheat);
        mRadioWhite = (RadioButton)findViewById(R.id.radio_bread_white);
        mRadioRye = (RadioButton)findViewById(R.id.radio_bread_rye);

        mCheckTomato = (CheckBox)findViewById(R.id.check_topping_tomato);
        mCheckLattuce = (CheckBox)findViewById(R.id.check_topping_lattuce);
        mCheckOnion = (CheckBox)findViewById(R.id.check_topping_onion);
        mCheckCarrot = (CheckBox)findViewById(R.id.check_topping_carrot);
        mCheckOlives = (CheckBox)findViewById(R.id.check_topping_olives);
        mCheckSesame = (CheckBox)findViewById(R.id.check_topping_sesame);
        mCheckHam = (CheckBox)findViewById(R.id.check_topping_ham);
        mCheckCheese = (CheckBox)findViewById(R.id.check_topping_cheese);

        mButtonNext = (Button)findViewById(R.id.button_next);
    }

    protected void updateNumber() {
        mTextSandwichNumber.setText(String.format(
                getResources().getString(R.string.sandwich_number), sandwichPos, lastSandwich));
    }

    protected void enableNextIfBread() {
        mButtonNext.setEnabled(mRadioWheat.isChecked() ||
                mRadioWhite.isChecked() || mRadioRye.isChecked());
    }

    protected class RadioBreadListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            enableNextIfBread();
        }
    }

    protected void clearOptions() {
        mRadioWheat.setChecked(false);
        mRadioWhite.setChecked(false);
        mRadioRye.setChecked(false);

        mCheckTomato.setChecked(false);
        mCheckLattuce.setChecked(false);
        mCheckOnion.setChecked(false);
        mCheckCarrot.setChecked(false);
        mCheckOlives.setChecked(false);
        mCheckSesame.setChecked(false);
        mCheckHam.setChecked(false);
        mCheckCheese.setChecked(false);
    }

    protected class ButtonOrderListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            addScreenSandwich();

            Intent intent = new Intent(SandwichStudioActivity.this, ResultsActivity.class);
            intent.putParcelableArrayListExtra(SANDWICH_ORDER, sandwichList);
            startActivity(intent);
        }
    }

    protected void changeNextToOrder() {
        mButtonNext.setText(R.string.place_order);
        mButtonNext.setOnClickListener(new ButtonOrderListener());
    }

    protected class ButtonNextListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            addScreenSandwich();

            sandwichPos++;
            updateNumber();
            clearOptions();
            enableNextIfBread();

            if (sandwichPos == lastSandwich) {
                changeNextToOrder();
            }
        }
    }

    private void addScreenSandwich() {
        SandwichModel newSandwich = new SandwichModel();

        if (mRadioWheat.isChecked()) {
            newSandwich.setBreadType(SandwichModel.BreadEnum.WHEAT);
        }
        else if (mRadioWhite.isChecked()) {
            newSandwich.setBreadType(SandwichModel.BreadEnum.WHITE);
        }
        else if (mRadioRye.isChecked()) {
            newSandwich.setBreadType(SandwichModel.BreadEnum.RYE);
        }

        if (mCheckTomato.isChecked()) {
            newSandwich.addTopping(SandwichModel.ToppingEnum.TOMATO);
        }
        if (mCheckLattuce.isChecked()) {
            newSandwich.addTopping(SandwichModel.ToppingEnum.LATTUCE);
        }
        if (mCheckOnion.isChecked()) {
            newSandwich.addTopping(SandwichModel.ToppingEnum.ONION);
        }
        if (mCheckCarrot.isChecked()) {
            newSandwich.addTopping(SandwichModel.ToppingEnum.CARROT);
        }
        if (mCheckOlives.isChecked()) {
            newSandwich.addTopping(SandwichModel.ToppingEnum.OLIVES);
        }
        if (mCheckSesame.isChecked()) {
            newSandwich.addTopping(SandwichModel.ToppingEnum.SESAME);
        }
        if (mCheckHam.isChecked()) {
            newSandwich.addTopping(SandwichModel.ToppingEnum.HAM);
        }
        if (mCheckCheese.isChecked()) {
            newSandwich.addTopping(SandwichModel.ToppingEnum.CHEESE);
        }

        sandwichList.add(newSandwich);
    }

    private void setListeners() {
        RadioBreadListener radioListener = new RadioBreadListener();

        mRadioWheat.setOnClickListener(radioListener);
        mRadioWhite.setOnClickListener(radioListener);
        mRadioRye.setOnClickListener(radioListener);

        if (sandwichPos < lastSandwich) {
            mButtonNext.setOnClickListener(new ButtonNextListener());
        }
        else {
            changeNextToOrder();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwich_studio);

        sandwichPos = 1;
        lastSandwich = getIntent().getIntExtra(SandwichModel.QUANTITY_KEY, -1);

        sandwichList = new ArrayList<>();

        controlsToVars();

        setListeners();

        updateNumber();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sandwich_studio, menu);
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
