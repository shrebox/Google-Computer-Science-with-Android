package com.example.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class
MainActivity extends AppCompatActivity {

    int finalScoreUser =0;
    int currentScoreUser=0;
    int finalScoreCom =0;
    int currentScoreCom=0;
    Random rand;
    boolean endgame = false;
   // int randVal = 0;
    //int currentScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Button hold = (Button)findViewById(R.id.button3);
        hold.setOnClickListener(new OnClickListener(){

            public void onClick(View v){
                Toast.makeText(getApplicationContext(),"Roll",Toast.LENGTH_SHORT).show();

                //rolldice();
            }
        });

        Button reset = (Button)findViewById(R.id.button4);
        reset.setOnClickListener(new OnClickListener(){

            public void onClick(View v){
                Toast.makeText(getApplicationContext(),"Reset",Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    public void resetFunc(View view){
        finalScoreCom=0;
        finalScoreUser=0;
        currentScoreUser=0;
        currentScoreCom=0;
        ImageView image = (ImageView) findViewById(R.id.imageView2);
        image.setImageResource(R.drawable.dice1);
        changeScoresUser(0);

    }

    public void holdFunc(View view){

        if(!endgame) {
            finalScoreUser += currentScoreUser;
            if (finalScoreUser >= 100) {
                endgame = true;
                Toast.makeText(getApplicationContext(), "You Won", Toast.LENGTH_SHORT).show();
            } else {
                currentScoreUser = 0;
                //changeScoresUser(0);
                finalScoreCom += computerTurn();
                if (finalScoreCom >= 100) {
                    endgame = true;
                    Toast.makeText(getApplicationContext(), "Computer Won", Toast.LENGTH_SHORT).show();
                }
            }
                currentScoreCom = 0;
                changeScoresUser(0);


        }
    }

    public int computerTurn(){

        rand = new Random();
        int randVal = rand.nextInt(6) + 1;

        while(randVal!=1 && currentScoreCom<20) {
            currentScoreCom += randVal;
            randVal = rand.nextInt(6) + 1;
        }

        if(randVal==1){
            return 0;
        }

        else
        {
            return currentScoreCom;
        }
    }

    public void rolldice(View view){

        rand = new Random();
        int randVal = rand.nextInt(6) + 1;

        ImageView image = (ImageView) findViewById(R.id.imageView2);

        if(!endgame) {

            if (randVal == 1) {
                image.setImageResource(R.drawable.dice1);
                currentScoreUser = 0;
                finalScoreCom += computerTurn();
                if (finalScoreCom >= 100) {
                    endgame = true;
                    Toast.makeText(getApplicationContext(), "Computer Won", Toast.LENGTH_SHORT).show();
                }
                currentScoreCom = 0;
                changeScoresUser();

            } else if (randVal == 2) {
                image.setImageResource(R.drawable.dice2);
                changeScoresUser(randVal);
            } else if (randVal == 3) {
                image.setImageResource(R.drawable.dice3);
                changeScoresUser(randVal);
            } else if (randVal == 4) {
                image.setImageResource(R.drawable.dice4);
                changeScoresUser(randVal);
            } else if (randVal == 5) {
                image.setImageResource(R.drawable.dice5);
                changeScoresUser(randVal);
            } else {
                image.setImageResource(R.drawable.dice6);
                changeScoresUser(randVal);
            }
        }

    }


    public void changeScoresUser() {
        TextView label = (TextView)findViewById(R.id.textView2);
        label.setText("Final Score: " + finalScoreUser + " Current Score: " + currentScoreUser + " Your turn Score: 1 "+ " Computer Score: " + finalScoreCom );
    }

    public void changeScoresUser(int randVal) {
        currentScoreUser += randVal;
        TextView label = (TextView)findViewById(R.id.textView2);
        label.setText("Final Score: " + finalScoreUser + " Current Score: " + currentScoreUser + " Your turn Score: "+ randVal + " Computer Score: " + finalScoreCom );
    }
}
