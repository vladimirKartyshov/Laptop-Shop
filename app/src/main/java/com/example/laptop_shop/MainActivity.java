package com.example.laptop_shop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int quantity = 0;
    Spinner spinner;
    ArrayList spinnerArrayList;
    ArrayAdapter spinnerAdapter;

    HashMap goodsMap;
    String goodsName;
    double price;
    EditText userNameEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText = findViewById(R.id.nameEditText);

        createSpinner();

        createMap();
    }


    void createSpinner(){

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        spinnerArrayList = new ArrayList();


        spinnerArrayList.add("DELL");
        spinnerArrayList.add("MSI");
        spinnerArrayList.add("HP");

        //связываем данные со spiner
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerArrayList);
        //выставляем выпадающий список для spiner
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //устанавливаем в spiner spinerAdapter
        spinner.setAdapter(spinnerAdapter);

    }

    void createMap(){

        goodsMap = new HashMap();
        goodsMap.put("DELL", 90000.0);
        goodsMap.put("MSI", 95000.0);
        goodsMap.put("HP", 85000.0);
    }


    public void increaseQuantity(View view) {
        quantity = quantity + 1;
        TextView quantityTextView = findViewById(R.id.quantityTextView);
        quantityTextView.setText(" " + quantity);
        TextView priceTextView = findViewById(R.id.priceTextView);
        priceTextView.setText("" + quantity * price);
    }

    public void decreaseQuantity(View view) {
        quantity = quantity - 1;
        if (quantity < 0){
            quantity = 0;
        }
        TextView quantityTextView = findViewById(R.id.quantityTextView);
        quantityTextView.setText(" " + quantity);
        TextView priceTextView = findViewById(R.id.priceTextView);
        priceTextView.setText("" + quantity * price);

    }

    @Override// в этом методе пишем код, когда выбирается элемент
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        goodsName = spinner.getSelectedItem().toString();//присваиваем значения которые сейчас в spinner
        price = (double)goodsMap.get(goodsName);//присваиваем цене значение товара по ключу
        TextView priceTextView = findViewById(R.id.priceTextView);
        priceTextView.setText("" + quantity * price);

        ImageView goodsImageView = findViewById(R.id.goodsImageView);

        switch (goodsName){
            case "MSI":
                goodsImageView.setImageResource(R.drawable.msi_iap);
                break;
            case "DELL":
                goodsImageView.setImageResource(R.drawable.DELL);
                break;
            case "HP":
                goodsImageView.setImageResource(R.drawable.OMEN);
                break;
            default:
                goodsImageView.setImageResource(R.drawable.msi_iap);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void addToCart(View view) {

        Order order = new Order();

        order.userName = userNameEditText.getText().toString();
        Log.d("userName", order.userName);// выводим в Logcat

        order.goodsName = goodsName;// присваиваем полю класса order значение одноименной перем MainActivity
        Log.d("goodsName", order.goodsName);

        order.quantity = quantity;
        Log.d("quantity", "" + order.quantity);//конкетенация int в String, т к в Logcat только String

        order.orderPrice = quantity * price;
        Log.d("orderPrice", "" + order.orderPrice);
        // после проверки строки Log можно удалять

        // запускаем вторую activity
        Intent orderIntent = new Intent(MainActivity.this, OrderActivity.class);
        //передаем данные во втору activity
        orderIntent.putExtra("userNameForIntent", order.userName);// ключ, значение
        orderIntent.putExtra("goodsName", order.goodsName);
        orderIntent.putExtra("quantity", order.quantity);
        orderIntent.putExtra("orderPrice", order.orderPrice);

        startActivity(orderIntent);
    }

}
