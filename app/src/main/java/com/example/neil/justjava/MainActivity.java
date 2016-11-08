/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

package com.example.neil.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.name;
import static android.R.id.message;
import static java.net.Proxy.Type.HTTP;

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
        if (quantity == 100) {
            Toast.makeText(this, getString(R.string.too_many_coffees_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method decrements the amount of coffees
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, getString(R.string.too_little_coffees_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
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

        String orderSummary = createOrderSummary(price, isWhipped, chocolate, userName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject) + " " + userName);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int amount) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + amount);
    }

    /**
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
        String orderSummary = getString(R.string.order_summary_name) + name;
        orderSummary += "\n" + getString(R.string.order_summary_whipped_cream) + isWhipped;
        orderSummary += "\n" + getString(R.string.order_summary_chocolate) + chocolate;
        orderSummary += "\n" + getString(R.string.order_summary_quantity) + quantity;
        orderSummary += "\n" + getString(R.string.order_summary_total) + price;
        orderSummary += "\n" + getString(R.string.order_summary_thank_you);

        return orderSummary;
    }
}