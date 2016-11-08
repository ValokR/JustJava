/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

package com.example.neil.justjava;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {


    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method increments the amount of coffees
     */
    public void increment(View view) {
        quantity = quantity + 1;

        if (quantity > 100) {
            quantity = 100;
            Context context = getApplicationContext();
            CharSequence text = "You can't order more than 100 coffees";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method decrements the amount of coffees
     */
    public void decrement(View view) {
        quantity = quantity - 1;

        if (quantity < 1) {
            quantity = 1;
            Context context = getApplicationContext();
            CharSequence text = "You can't order less than one coffee";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // whether or not whipped cream is selected
        CheckBox isWhippedCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        boolean isWhipped = isWhippedCheckBox.isChecked();

        // whether or not chocolate is selected
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        boolean chocolate = chocolateCheckBox.isChecked();

        // get user name from input field
        EditText nameTextField = (EditText) findViewById(R.id.name_input_field);
        String userName = nameTextField.getText().toString();

        int price = calculatePrice(isWhipped, chocolate);

        String priceMessage = createOrderSummary(price, isWhipped, chocolate, userName);
        displayMessage(priceMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int amount) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + amount);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     *
     *
     * @param isWhipped did the user want whipped cream?
     * @param chocolate did the user want chocolate
     * @return price total based on toppings
     */
    private int calculatePrice(boolean isWhipped, boolean chocolate) {
        int pricePerCup = 5;

        if (isWhipped) {
            pricePerCup += 1;
        }

        if (chocolate) {
            pricePerCup += 2;
        }
        int price = quantity * pricePerCup;
        return price;
    }

    /**
     * Creates a text summary of an order
     *
     * @param price     of order
     * @param chocolate whether or not chocolate is added
     * @param isWhipped whether or not whipped cream is added
     * @return Text bases summary of order
     */
    public String createOrderSummary(int price, boolean isWhipped, boolean chocolate, String name) {
        String orderSummary = "Name: " + name;
        orderSummary += "\nAdd Whipped Cream? " + isWhipped;
        orderSummary += "\nAdd Chocolate? " + chocolate;
        orderSummary += "\nQuantity: " + quantity;
        orderSummary += "\nTotal: " + price;
        orderSummary += "\nThank you!";
        return orderSummary;
    }
}