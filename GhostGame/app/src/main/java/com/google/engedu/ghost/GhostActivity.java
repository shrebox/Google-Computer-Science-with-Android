/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private final String TAG = "GhostActivity";
    //private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    private SimpleDictionary dictionary;

    private String WordFragment;
    private TextView GameStatus;
    private TextView GhostText;
    private TextView ValidWordText;
    private boolean endgame = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        try {
            dictionary = new SimpleDictionary(getAssets().open("words.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        onStart(null);

    }

    public void challengeFunc(View view)
    {
        if(endgame) {

        }
        if(!endgame) {
            if (WordFragment.length() >= 4 && dictionary.isWord(WordFragment)) {
                GameStatus.setText("You won!");
                Toast.makeText(getApplicationContext(), "You Won", Toast.LENGTH_SHORT).show();
                endgame = true;
            } else if (dictionary.getAnyWordStartingWith(WordFragment) != null) {
                String PossibleLongerWord = dictionary.getAnyWordStartingWith(WordFragment);
                GameStatus.setText("You lose!");
                ValidWordText.setText("Possible word: " + PossibleLongerWord);
            } else {
                GameStatus.setText("You won!");
                Toast.makeText(getApplicationContext(), "You Won", Toast.LENGTH_SHORT).show();
                endgame = true;
            }
        }


    }

    public void restartFunc(View view)
    {
        endgame = false;
        onStart(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
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

    public String startText()
    {
        Random rand = new Random();

        int n = rand.nextInt(10000);

        String s = dictionary.send_text(n);

        while(s.length()<4 || dictionary.isWord(s.substring(0,4)))
        {
            n= rand.nextInt(10000);
            s = dictionary.send_text(n);
        }

        return s.substring(0,4);

    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {

        userTurn = random.nextBoolean();
        WordFragment = startText();

        GhostText = (TextView) findViewById(R.id.ghostText);
        GhostText.setText(WordFragment);

//        TextView text = (TextView) findViewById(R.id.ghostText);
//        text.setText("");
//        TextView label = (TextView) findViewById(R.id.gameStatus);

        ValidWordText = (TextView)findViewById(R.id.valid_word_text);
        ValidWordText.setText("No word yet!");

        GameStatus = (TextView) findViewById(R.id.gameStatus);

        if (userTurn) {
            GameStatus.setText(USER_TURN);
        } else {
            GameStatus.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {

        if(dictionary.isWord(WordFragment))
        {
            //System.out.println("here");
            ValidWordText.setText("This is a valid word.");
        }

        if(WordFragment.length() >= 4 && dictionary.isWord(WordFragment))
        {
            GameStatus.setText("Computer won!");
            Toast.makeText(getApplicationContext(),"Computer Won",Toast.LENGTH_SHORT).show();
            endgame = true;
            return;
        }

        else if(dictionary.getAnyWordStartingWith(WordFragment) != null)
        {
            String mPossibleLongerWord = dictionary.getAnyWordStartingWith(WordFragment);
            WordFragment += mPossibleLongerWord.charAt(WordFragment.length());
            GhostText.setText(WordFragment);
        }

        else
        {
            GameStatus.setText("No longer word exists - Computer won!");
            Toast.makeText(getApplicationContext(),"Computer Won",Toast.LENGTH_SHORT).show();
            endgame = true;
        }

        if(dictionary.isWord(WordFragment)){
            ValidWordText.setText("This is a valid word. - You won!");
        }

        if(endgame==false) {
            userTurn = true;
            GameStatus.setText(USER_TURN);
        }
    }

    /**
     * Handler for user key presses.
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
//        TextView text = (TextView) findViewById(R.id.ghostText);
//        TextView label = (TextView) findViewById(R.id.gameStatus);

        //endgame = true;

        char c = (char) event.getUnicodeChar();

        //if(c=='a' || c=='b' || c=='c'|| c=='d'|| c=='e'|| c=='f'|| c=='g'|| c=='h'|| c=='i'|| c=='j'|| c=='k'|| c=='l'|| c=='m'|| c=='n'|| c=='o'|| c=='p'|| c=='q'|| c=='r'|| c=='s'|| c=='t'|| c=='u'|| c=='v'|| c=='w'|| c=='x'|| c=='y'|| c=='z' )
        if(c >= 'a' && c<= 'z' && !endgame)
        {
            //text.setText(text.getText() + Character.toString(c));

            WordFragment += c;
            GhostText.setText(WordFragment);

            if(dictionary.isWord(WordFragment))
            {
                GameStatus.setText("Valid Word!");
            }

            else

            {
                GameStatus.setText("Invalid Word");
            }

            Log.d(TAG,"test_before_computer's_turn");
            GameStatus.setText(COMPUTER_TURN);
            computerTurn();

        }

        else
        {
            Toast.makeText(getApplicationContext(),"Wrong Input",Toast.LENGTH_SHORT).show();
        }

        return super.onKeyUp(keyCode, event);
    }
}
