package co.mobilemakers.sandwichrestaurant;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.EnumSet;


public class SandwichStudioActivity extends ActionBarActivity {

    final static private String KEY_WHICH = "KEY_WHICH";
    final static private String KEY_THIS_ONE = "KEY_THIS_ONE";
    final static private String KEY_ENTIRE_LIST = "KEY_ENTIRE_LIST";

    final static public String SANDWICH_ORDER = "SANDWICH_ORDER";

    int sandwichPos;
    int lastSandwich;
    SandwichModel thisSandwich;

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
            if (v == mRadioWheat)
                thisSandwich.setBreadType(SandwichModel.BreadEnum.WHEAT);
            else if (v == mRadioWhite)
                thisSandwich.setBreadType(SandwichModel.BreadEnum.WHITE);
            else
                thisSandwich.setBreadType(SandwichModel.BreadEnum.RYE);

            enableNextIfBread();
        }
    }

    protected class CheckToppingListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SandwichModel.ToppingEnum toppingToChange;

            if (buttonView == mCheckTomato)
                toppingToChange = SandwichModel.ToppingEnum.TOMATO;
            else if (buttonView == mCheckLattuce)
                toppingToChange = SandwichModel.ToppingEnum.LATTUCE;
            else if (buttonView == mCheckOnion)
                toppingToChange = SandwichModel.ToppingEnum.ONION;
            else if (buttonView == mCheckCarrot)
                toppingToChange = SandwichModel.ToppingEnum.CARROT;
            else if (buttonView == mCheckOlives)
                toppingToChange = SandwichModel.ToppingEnum.OLIVES;
            else if (buttonView == mCheckSesame)
                toppingToChange = SandwichModel.ToppingEnum.SESAME;
            else if (buttonView == mCheckHam)
                toppingToChange = SandwichModel.ToppingEnum.HAM;
            else //if (buttonView == mCheckCheese)
                toppingToChange = SandwichModel.ToppingEnum.CHEESE;

            if (isChecked)
                thisSandwich.addTopping(toppingToChange);
            else
                thisSandwich.removeTopping(toppingToChange);
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

        thisSandwich = new SandwichModel();
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

        CheckToppingListener checkToppingListener = new CheckToppingListener();

        mCheckTomato.setOnCheckedChangeListener(checkToppingListener);
        mCheckLattuce.setOnCheckedChangeListener(checkToppingListener);
        mCheckOnion.setOnCheckedChangeListener(checkToppingListener);
        mCheckCarrot.setOnCheckedChangeListener(checkToppingListener);
        mCheckOlives.setOnCheckedChangeListener(checkToppingListener);
        mCheckSesame.setOnCheckedChangeListener(checkToppingListener);
        mCheckHam.setOnCheckedChangeListener(checkToppingListener);
        mCheckCheese.setOnCheckedChangeListener(checkToppingListener);

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

        thisSandwich = new SandwichModel();

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_WHICH, sandwichPos);
        outState.putParcelable(KEY_THIS_ONE, thisSandwich);
        outState.putParcelableArrayList(KEY_ENTIRE_LIST, sandwichList);
    }

    protected void restoreSandwich() {
        updateNumber();

        if (thisSandwich.getBreadType() != null) {
            if (thisSandwich.getBreadType() == SandwichModel.BreadEnum.WHEAT)
                mRadioWheat.setChecked(true);
            else if (thisSandwich.getBreadType() == SandwichModel.BreadEnum.WHITE)
                mRadioWhite.setChecked(true);
            else if (thisSandwich.getBreadType() == SandwichModel.BreadEnum.RYE)
                mRadioRye.setChecked(true);
        }

        EnumSet<SandwichModel.ToppingEnum> toppingList = thisSandwich.getToppingList();
        if (toppingList.contains(SandwichModel.ToppingEnum.TOMATO))
            mCheckTomato.setChecked(true);
        if (toppingList.contains(SandwichModel.ToppingEnum.LATTUCE))
            mCheckLattuce.setChecked(true);
        if (toppingList.contains(SandwichModel.ToppingEnum.ONION))
            mCheckOnion.setChecked(true);
        if (toppingList.contains(SandwichModel.ToppingEnum.CARROT))
            mCheckCarrot.setChecked(true);
        if (toppingList.contains(SandwichModel.ToppingEnum.OLIVES))
            mCheckOlives.setChecked(true);
        if (toppingList.contains(SandwichModel.ToppingEnum.SESAME))
            mCheckSesame.setChecked(true);
        if (toppingList.contains(SandwichModel.ToppingEnum.HAM))
            mCheckHam.setChecked(true);
        if (toppingList.contains(SandwichModel.ToppingEnum.CHEESE))
            mCheckCheese.setChecked(true);

        enableNextIfBread();

        if (sandwichPos == lastSandwich) {
            changeNextToOrder();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        sandwichPos = savedInstanceState.getInt(KEY_WHICH);
        thisSandwich = savedInstanceState.getParcelable(KEY_THIS_ONE);
        sandwichList = savedInstanceState.getParcelableArrayList(KEY_ENTIRE_LIST);

        restoreSandwich();
    }
}
