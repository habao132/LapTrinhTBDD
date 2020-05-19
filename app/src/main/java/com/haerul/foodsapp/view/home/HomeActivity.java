/*-----------------------------------------------------------------------------
 - Developed by Haerul Muttaqin                                               -
 - Last modified 3/17/19 5:24 AM                                              -
 - Subscribe : https://www.youtube.com/haerulmuttaqin                         -
 - Copyright (c) 2019. All rights reserved                                    -
 -----------------------------------------------------------------------------*/
package com.haerul.foodsapp.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.haerul.foodsapp.R;


public class HomeActivity extends AppCompatActivity {
    Button btnGo1 , btnGo2 ,btnGo3,  btnGo4  ;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnGo1 = (Button)findViewById(R.id.btnGo1);
        btnGo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Act1 = new Intent(HomeActivity.this, ActivityOne.class);
                startActivity(Act1);
            }
        });

        btnGo2 = (Button)findViewById(R.id.btnGo2);
        btnGo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Act1 = new Intent(HomeActivity.this, ActivityTwo.class);
                startActivity(Act1);
            }
        });

        btnGo3 = (Button)findViewById(R.id.btnGo3);
        btnGo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Act1 = new Intent(HomeActivity.this, ActivityThree.class);
                startActivity(Act1);
            }
        });

        btnGo4 = (Button)findViewById(R.id.btnGo4);
        btnGo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Act1 = new Intent(HomeActivity.this, ActivityFour.class);
                startActivity(Act1);
            }
        });


    }
}




