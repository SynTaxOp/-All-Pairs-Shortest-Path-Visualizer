package com.vaibhav.apslabproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Details extends AppCompatActivity {
    Spinner spinner, spinner2;
    Button draw;
    EditText numberOfNodes;
    boolean directed = false;
    int n = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        final String[] algo = {""};

        spinner = findViewById(R.id.algo);
        spinner2 = findViewById(R.id.directed);
        numberOfNodes = findViewById(R.id.numberOfNodes);
        draw = findViewById(R.id.draw);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.algos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                algo[0] = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                algo[0] = "Dijkstra";
            }
        });

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.directed, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter1);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("Directed")) {
                    directed = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                directed = false;
            }
        });

        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Details.this, MainActivity.class);
                if(numberOfNodes.getText().toString() != null) {
                    n = Integer.parseInt(numberOfNodes.getText().toString());
                }
                intent.putExtra("numberOfNodes", n);
                intent.putExtra("algo", algo[0]);
                intent.putExtra("directed", directed);
                startActivity(intent);
            }
        });

    }
}