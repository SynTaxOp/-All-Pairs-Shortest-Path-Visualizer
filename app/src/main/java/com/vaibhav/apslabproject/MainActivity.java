package com.vaibhav.apslabproject;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements Dialog.DialogListener {

    int n = 0;
    int k = 0;
    int num = 0;
    boolean oneSelected = false;
    int firstPoint = 0;
    int secondPoint = 0;
    int[][] adjacencyMatrix;
    //int[] numbers = new int[n];
    boolean[] marked = new boolean[48];
    RecyclerView recyclerView;
    ArrayList<Model> arrayList;
    ArrayList<Integer> numbers;
    ArrayList<String> uniquePairs;
    boolean directed = false;
    String algo = "";
    Button calculate;
    ArrayList<Integer>[][] paths;
    int[][] answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.distances);
        calculate = findViewById(R.id.calculate);
        numbers = new ArrayList<>();
        arrayList = new ArrayList<>();
        uniquePairs = new ArrayList<>();

        Intent intent = getIntent();
        directed = intent.getBooleanExtra("directed", false);
        n = intent.getIntExtra("numberOfNodes", 0);
        algo = intent.getStringExtra("algo");
        adjacencyMatrix = new int[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                adjacencyMatrix[i][j] = -1;
            }
        }
        for(int i = 0; i < 48; i++) {
            marked[i] = false;
        }

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Algorithms algos = new Algorithms();
                algos.setN(n);
                ArrayList<Edge> edges = new ArrayList<>();
                ArrayList<Pair>[] pairs = new ArrayList[n];
                for(int i = 0; i < n; i++) {
                    pairs[i] = new ArrayList<>();
                }
                for(int i = 0; i < n; i++) {
                    for(int j = 0; j < n; j++) {
                        Log.d("AdjMatrix", Arrays.toString(adjacencyMatrix[i]));
                        Edge e = new Edge(i, j, adjacencyMatrix[i][j]);
                        Pair p = new Pair(j, adjacencyMatrix[i][j]);
                        pairs[i].add(p);
                        edges.add(e);
                    }
                }
                Log.d("Path", Arrays.toString(answer) + " " + Arrays.toString(paths));
                if(algo.equals("Johnson")) {
                    answer = algos.Johnson(pairs, edges);
                    paths = algos.paths;
                } else if(algo.equals("Dijkstra")) {
                    answer = algos.MDijkstra(pairs);
                    paths = algos.paths;
                } else if(algo.equals("Bellman Ford")) {
                    answer = algos.MBellman(edges);
                    paths = algos.paths;
                } else {
                    answer = algos.Floyd_Warshall(pairs);
                    paths = algos.paths;
                }
                Log.d("Path", Arrays.toString(answer) + " " + Arrays.toString(paths));
            }
        });

    }

    public void cell(View view) {
        TextView v = (TextView)view;
        Log.d("Image No", view.getTag().toString());
        int number = Integer.parseInt(view.getTag().toString());
        //Toast.makeText(this, Arrays.toString(marked), Toast.LENGTH_LONG).show();
        if(!marked[number] && k < n) {
            v.setText((k + 1) + "");
            marked[number] = true;
            numbers.add(number);
            k++;
            num++;
            Toast.makeText(this, k + " " + oneSelected + " " + firstPoint + " " + secondPoint, Toast.LENGTH_SHORT).show();
        } else {
            if(k < n) {
                if(numbers.indexOf(number) == numbers.size() - 1) {
                    marked[number] = false;
                    k--;
                    v.setText("");
                }
            } else {
                if(marked[number] && !oneSelected) {
                    firstPoint = numbers.indexOf(number) + 1;
                    oneSelected = true;
                    Toast.makeText(this, k + " " + oneSelected + " " + firstPoint + " " + secondPoint, Toast.LENGTH_SHORT).show();
                } else if(marked[number] && oneSelected) {
                    secondPoint = numbers.indexOf(number) + 1;
                    oneSelected = false;
                    Toast.makeText(this, k + " " + oneSelected + " " + firstPoint + " " + secondPoint, Toast.LENGTH_SHORT).show();
                    //operations
                    if(uniquePairs.indexOf(firstPoint + " " + secondPoint) == -1) {
                       openDialog();
                    } else {
                        Toast.makeText(MainActivity.this, "Can't use different values for same path. Restart by selecting a new node", Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.this, "Now again selet the first node", Toast.LENGTH_SHORT).show();
                    }
                    //openDialog();
                }
            }
        }
    }

    public void openDialog() {
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void apply(String distance) {
        //got distance here
        if(directed) {
            adjacencyMatrix[firstPoint - 1][secondPoint - 1] = Integer.parseInt(distance);
            arrayList.add(new Model(firstPoint + "", secondPoint + "", distance));
            setAdapter(arrayList);
        } else {
            /*if(uniquePairs.indexOf(firstPoint + " " + secondPoint) == -1) {
                uniquePairs.add(firstPoint + " " + secondPoint);
                uniquePairs.add(secondPoint + " " + firstPoint);
                adjacencyMatrix[firstPoint - 1][secondPoint - 1] = Integer.parseInt(distance);
                adjacencyMatrix[secondPoint - 1][firstPoint - 1] = Integer.parseInt(distance);
                arrayList.add(new Model(firstPoint + "", secondPoint + "", distance));
            } else {
                Toast.makeText(MainActivity.this, "Can't use different values for same path", Toast.LENGTH_LONG).show();
            }*/
            uniquePairs.add(firstPoint + " " + secondPoint);
            uniquePairs.add(secondPoint + " " + firstPoint);
            adjacencyMatrix[firstPoint - 1][secondPoint - 1] = Integer.parseInt(distance);
            adjacencyMatrix[secondPoint - 1][firstPoint - 1] = Integer.parseInt(distance);
            arrayList.add(new Model(firstPoint + "", secondPoint + "", distance));
            setAdapter(arrayList);
        }
        Log.d("AdjMatrix", Arrays.toString(adjacencyMatrix[0]));
        Log.d("AdjMatrix", Arrays.toString(adjacencyMatrix[1]));
        Log.d("AdjMatrix", Arrays.toString(adjacencyMatrix[2]));
        Log.d("AdjMatrix", Arrays.toString(adjacencyMatrix[3]));

    }

    void setAdapter(ArrayList<Model> arrayList) {
        Adapter adapter;
        if(directed) {
            adapter = new Adapter(MainActivity.this, R.layout.card, arrayList);
        } else {
            adapter = new Adapter(MainActivity.this, R.layout.card1, arrayList);
        }
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(MainActivity.this);
        try {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager1);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {

        }
    }
}