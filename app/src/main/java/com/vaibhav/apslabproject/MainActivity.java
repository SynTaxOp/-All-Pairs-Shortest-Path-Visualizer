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
import android.widget.LinearLayout;
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
    RecyclerView recyclerView, recyclerResult;
    ArrayList<Model> arrayList;
    ArrayList<Integer> numbers;
    ArrayList<String> uniquePairs;
    boolean directed = false;
    String algo = "";
    Button calculate, restart;
    TextView message;
    ArrayList<Integer>[][] paths;
    int[][] answer;
    boolean negCycle = false;
    LinearLayout workingLayout;
    LinearLayout resultLayout;
    int alreadyMarked = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.distances);
        calculate = findViewById(R.id.calculate);
        restart = findViewById(R.id.restart);
        workingLayout = findViewById(R.id.workingLayout);
        resultLayout = findViewById(R.id.resultLayout);
        recyclerResult = findViewById(R.id.recyclerResults);
        message = findViewById(R.id.message);
        numbers = new ArrayList<>();
        arrayList = new ArrayList<>();
        uniquePairs = new ArrayList<>();

        Intent intent = getIntent();
        directed = intent.getBooleanExtra("directed", false);
        n = intent.getIntExtra("numberOfNodes", 0);
        algo = intent.getStringExtra("algo");
        adjacencyMatrix = new int[n][n];
        message.setText("Mark " + n + " nodes");
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
                if(alreadyMarked != n) {
                    message.setText("You cannot proceed without marking " + n + " node(s)");
                    return;
                }
                Algorithms algos = new Algorithms();
                algos.setN(n);
                ArrayList<Edge> edges = new ArrayList<>();
                ArrayList<Pair>[] pairs = new ArrayList[n];
                for(int i = 0; i < n; i++) {
                    pairs[i] = new ArrayList<>();
                }
                for(int i = 0; i < n; i++) {
                    for(int j = 0; j < n; j++) {
                        //Log.d("AdjMatrix", Arrays.toString(adjacencyMatrix[i]));
                        if(adjacencyMatrix[i][j] != -1) {
                            Edge e = new Edge(i, j, adjacencyMatrix[i][j]);
                            Pair p = new Pair(j, adjacencyMatrix[i][j]);
                            pairs[i].add(p);
                            edges.add(e);
                        }
                    }
                }

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
                negCycle = algos.isNegcycle();
                if(!negCycle) {
                    workingLayout.setVisibility(View.GONE);
                    resultLayout.setVisibility(View.VISIBLE);
                    ArrayList<Model> answerArrayList = new ArrayList<>();
                    for (int k = 0; k < n; k++) {
                        for (int j = 0; j < n; j++) {
                            if(answer[k][j] == 100000000) {
                                answerArrayList.add(new Model((k + 1) + "", (j + 1) + "", "∞", null));
                            } else {
                                answerArrayList.add(new Model((k + 1) + "", (j + 1) + "", answer[k][j] + "", paths[k][j]));
                            }
                        }
                    }
                    setAdapterResult(answerArrayList);
                } else {
                    message.setText("Negative Cycle detected.\nShortest Path does not exist.");
                    resultLayout.setVisibility(View.VISIBLE);
                    recyclerResult.setVisibility(View.GONE);
                }
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backHome = new Intent(MainActivity.this, Details.class);
                startActivity(backHome);
            }
        });
    }

    public void cell(View view) {
        TextView v = (TextView)view;
        Log.d("Image No", view.getTag().toString());
        int number = Integer.parseInt(view.getTag().toString());
        if(!marked[number] && k < n) {
            v.setText((k + 1) + "");
            marked[number] = true;
            numbers.add(number);
            k++;
            num++;
            alreadyMarked++;
            if(n - alreadyMarked == 0) {
                message.setText("Select 2 nodes one after other to mark distance between them.");
            } else {
                message.setText("Mark " + (n - alreadyMarked) + " more node(s)");
            }
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
                    message.setText(firstPoint + " selected.\nSelect one more to mark distance between two nodes.");
                } else if(marked[number] && oneSelected) {
                    secondPoint = numbers.indexOf(number) + 1;
                    oneSelected = false;
                    message.setText("");
                    //operations
                    if(uniquePairs.indexOf(firstPoint + " " + secondPoint) == -1) {
                        if(firstPoint == secondPoint) {
                            Toast.makeText(MainActivity.this, "Choose two different nodes. Restart by selecting a new node", Toast.LENGTH_LONG).show();
                            Toast.makeText(MainActivity.this, "Now again select the first node", Toast.LENGTH_SHORT).show();
                        } else {
                            openDialog();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Can't use different values for same path. Restart by selecting a new node", Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.this, "Now again select the first node", Toast.LENGTH_SHORT).show();
                    }
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
            uniquePairs.add(firstPoint + " " + secondPoint);
            adjacencyMatrix[firstPoint - 1][secondPoint - 1] = Integer.parseInt(distance);
            arrayList.add(new Model(firstPoint + "", secondPoint + "", distance));
            setAdapter(arrayList);
        } else {
            uniquePairs.add(firstPoint + " " + secondPoint);
            uniquePairs.add(secondPoint + " " + firstPoint);
            adjacencyMatrix[firstPoint - 1][secondPoint - 1] = Integer.parseInt(distance);
            adjacencyMatrix[secondPoint - 1][firstPoint - 1] = Integer.parseInt(distance);
            arrayList.add(new Model(firstPoint + "", secondPoint + "", distance));
            setAdapter(arrayList);
        }
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

    void setAdapterResult(ArrayList<Model> arrayList) {
        AdapterResult adapterResult;
        adapterResult = new AdapterResult(MainActivity.this, R.layout.card2, arrayList);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(MainActivity.this);
        try {
            recyclerResult.setHasFixedSize(true);
            recyclerResult.setLayoutManager(layoutManager1);
            recyclerResult.setAdapter(adapterResult);
        } catch (Exception e) {

        }
    }
}