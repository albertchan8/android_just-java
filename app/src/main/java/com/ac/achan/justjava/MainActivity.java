package com.ac.achan.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Just Java Created by Albert Chan
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {

    int quantity;
    int baseCoffeePrice = 5;
    int whippedCreamPrice = 1;
    int chocolatePrice = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayMenu();
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //Figure out the name of the user
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name =  nameField.getText().toString();

        //Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        //Create order summary
        String priceMessage = createOrderSummary(price, name, hasWhippedCream, hasChocolate);

        //Display order summary
        displayMessage(priceMessage);

        //Open and populate email app with order
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * This method calculates the price of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int tempPrice = baseCoffeePrice;

        //Add whippedCreamPrice if the user wants whipped cream
        if (addWhippedCream) {
            tempPrice = tempPrice + whippedCreamPrice;
        }

        //Add chocolatePrice if the user wants chocolate
        if (addChocolate) {
            tempPrice = tempPrice + chocolatePrice;
        }

        //Calculate the total order price by multiplying by quantity
        return quantity * tempPrice;
    }

    /**
     * This method creates the summary of the order.
     *
     * @param price of the order
     * @param name of the user
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @return text summary
     */
    private String createOrderSummary(int price, String name, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }

    /**
     * This method displays the menu on the screen.
     */
    private void displayMenu() {
        TextView coffeePriceMenu = (TextView) findViewById(R.id.coffee_price_menu);
        TextView whippedCreamPriceMenu = (TextView) findViewById(R.id.whipped_cream_price_menu);
        TextView chocolatePriceMenu = (TextView) findViewById(R.id.chocolate_price_menu);

        coffeePriceMenu.setText("Coffee: $" + baseCoffeePrice + " each");
        whippedCreamPriceMenu.setText("Whipped Cream: $" + whippedCreamPrice + " each");
        chocolatePriceMenu.setText("Chocolate: $" + chocolatePrice + " each");

    }
}
