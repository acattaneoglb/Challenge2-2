package co.mobilemakers.sandwichrestaurant;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.EnumSet;


public class ResultsActivity extends ActionBarActivity {

    ArrayList<SandwichModel> sandwichList;

    TextView mTextTitle, mTextResult;

    protected String stringFromEnum(SandwichModel.BreadEnum bread) {
        if (bread == SandwichModel.BreadEnum.WHEAT)
            return getResources().getString(R.string.wheat_option);
        if (bread == SandwichModel.BreadEnum.WHITE)
            return getResources().getString(R.string.white_option);
        //if (bread == SandwichModel.BreadEnum.RYE)
            return getResources().getString(R.string.rye_option);
    }

    protected String stringFromEnum(SandwichModel.ToppingEnum topping) {
        if (topping == SandwichModel.ToppingEnum.TOMATO)
            return getResources().getString(R.string.tomato_option);
        if (topping == SandwichModel.ToppingEnum.LATTUCE)
            return getResources().getString(R.string.lattuce_option);
        if (topping == SandwichModel.ToppingEnum.ONION)
            return getResources().getString(R.string.onion_option);
        if (topping == SandwichModel.ToppingEnum.CARROT)
            return getResources().getString(R.string.grated_carrot_option);
        if (topping == SandwichModel.ToppingEnum.OLIVES)
            return getResources().getString(R.string.olives_option);
        if (topping == SandwichModel.ToppingEnum.SESAME)
            return getResources().getString(R.string.sesame_seeds_option);
        if (topping == SandwichModel.ToppingEnum.HAM)
            return getResources().getString(R.string.ham_option);
        if (topping == SandwichModel.ToppingEnum.CHEESE)
            return getResources().getString(R.string.cheese_option);

        return "";
    }

    protected String getToppingsStringList(EnumSet<SandwichModel.ToppingEnum> toppingsEnum) {
        String result = "";

        boolean first = true;
        for (SandwichModel.ToppingEnum topping : toppingsEnum) {
            if (!first)
                result += ", ";
            result += stringFromEnum(topping);

            first = false;
        }
        if (first)
            result = getResources().getString(R.string.nothing);

        return result;
    }

    protected void showResults() {
        int quantity = sandwichList.size();
        mTextTitle.setText(String.format(getResources().getString(R.string.text_sandwich_total), quantity));

        String result = "";
        int whichSandwich = 1;
        for (SandwichModel sandwich : sandwichList) {
            result += String.format(getResources().getString(R.string.sandwich_each), whichSandwich);
            result += "\n";
            result += getResources().getString(R.string.bread_options);
            result += " ";
            result += stringFromEnum(sandwich.getBreadType());
            result += "\n";
            result += getResources().getString(R.string.toppings_options);
            result += " ";
            result += getToppingsStringList(sandwich.getToppingList());
            result += "\n\n";
            whichSandwich++;
        }

        mTextResult.setText(result);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        sandwichList = getIntent().getParcelableArrayListExtra(SandwichStudioActivity.SANDWICH_ORDER);

        controlsToVars();

        showResults();
    }

    private void controlsToVars() {
        mTextTitle = (TextView)findViewById(R.id.text_sandwich_total);
        mTextResult = (TextView)findViewById(R.id.text_sandwich_result);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
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
