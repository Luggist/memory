package com.example.android.roomwordssample;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.example.android.roomwordssample.ResultView;

import static com.example.android.roomwordssample.ResultView.NEW_WORD_ACTIVITY_REQUEST_CODE;


public class MemorieActivity extends AppCompatActivity implements View.OnClickListener {

    int mediaLenght;
    int counter;
    int colorLightBlue1;
    int colorDarkGreen1;
    int colorLightBlack2;
    int colorDarkWhite2;
    Intent intent;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private WordViewModel mWordViewModel;

    SharedPreferences sharedPreferences;
    Vibrator vibrator;

    MediaPlayer click, click2, gameMusic, crowd;
    TextView textViewPlayer1, textViewPlayer2;

    ImageView[] imageArray = new ImageView[12];

    Integer[] cardArray = {101, 102, 103, 104, 105, 106, 201, 202, 203, 204, 205, 206};

    long[] pattern = {0, 200, 200, 200};

    int p_101, p_102, p_103, p_104, p_105, p_106, p_201, p_202, p_203, p_204, p_205, p_206;
    int[] drawableArray = {p_101, p_102, p_103, p_104, p_105, p_106, p_201, p_202, p_203, p_204, p_205, p_206};

    int[] idArray = {R.id._11, R.id._12, R.id._13, R.id._14, R.id._21, R.id._22, R.id._23, R.id._24, R.id._31, R.id._32, R.id._33, R.id._34};

    int firstCard, secondCard;
    int clickedFirst, clickedSecond;

    int cardNumber = 1;
    int turn = 1;

    int playerPoints = 0, player2Points = 0;
    boolean check2, check1;

    RelativeLayout relativeLayout1;
    LinearLayout linearLayoutImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorie);

        final WordListAdapter adapter = new WordListAdapter(this);

        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
                    @Override
                    public void onChanged(@Nullable final List<Word> words) {
                        // Update the cached copy of the words in the adapter.
                        adapter.setWords(words);
                    }
                });

        counter = 0;

        relativeLayout1 = (RelativeLayout) findViewById(R.id.relativLayout1);
        linearLayoutImage = (LinearLayout) findViewById(R.id.linearimage);

        colorLightBlue1 = Color.BLUE;
        colorLightBlack2 = Color.BLACK;
        colorDarkGreen1 = Color.GREEN;
        colorDarkWhite2 = Color.WHITE;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        gameMusic = MediaPlayer.create(MemorieActivity.this, R.raw.relax);
        crowd = MediaPlayer.create(MemorieActivity.this, R.raw.crowd);
        click = MediaPlayer.create(MemorieActivity.this, R.raw.click2);
        click2 = MediaPlayer.create(MemorieActivity.this, R.raw.clickik);

        textViewPlayer1 = findViewById(R.id.textView1);
        textViewPlayer2 = findViewById(R.id.textView2);

        for (int i = 0; i < 12; i++) {

            imageArray[i] = (ImageView) findViewById(idArray[i]);
            imageArray[i].setTag(Integer.toString(i));
            imageArray[i].setOnClickListener(this);

        }

        //load images frontOfCardsResources();
        frontOfCardsResources();
        Collections.shuffle(Arrays.asList(cardArray));

    }


    @Override
    public void onClick(View v) {

        for (int z = 0; z < 12; z++) {
            if (v.getId() == imageArray[z].getId()) {

                click.start();
                vibrator.vibrate(50);
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(imageArray[z], theCard);
            }

        }
    }

    public void onResume() {
        super.onResume();

        gameMusic.seekTo(mediaLenght);

        check2 = sharedPreferences.getBoolean("DARKMODE", false);
        check1 = sharedPreferences.getBoolean("MUSIC", false);

        if (check1) {
            gameMusic.start();
        }
        //change color of player
        if (!check2) {
            textViewPlayer1.setTextColor(colorLightBlue1);
            textViewPlayer2.setTextColor(colorLightBlack2);
        } else {
            textViewPlayer1.setTextColor(colorDarkGreen1);
            textViewPlayer2.setTextColor(colorDarkWhite2);
        }

        if (!check2) {
            relativeLayout1.setBackgroundResource(R.drawable.trinangle_white);
            // linearLayoutImage.setBackgroundResource(R.drawable.andi2);

        } else {
            relativeLayout1.setBackgroundResource(R.drawable.triangle_black);
            //  linearLayoutImage.setBackgroundResource(R.drawable.circle);
        }
    }

    public void onPause() {
        super.onPause();

        gameMusic.pause();
        mediaLenght = gameMusic.getCurrentPosition();
    }

    private void doStuff(ImageView iv, int card) {
        // set correct image to image view

        if (cardArray[card] == 101) {
            iv.setImageResource(drawableArray[0]);
        } else if (cardArray[card] == 102) {
            iv.setImageResource(drawableArray[1]);
        } else if (cardArray[card] == 103) {
            iv.setImageResource(drawableArray[2]);
        } else if (cardArray[card] == 104) {
            iv.setImageResource(drawableArray[3]);
        } else if (cardArray[card] == 105) {
            iv.setImageResource(drawableArray[4]);
        } else if (cardArray[card] == 106) {
            iv.setImageResource(drawableArray[5]);
        } else if (cardArray[card] == 201) {
            iv.setImageResource(drawableArray[6]);
        } else if (cardArray[card] == 202) {
            iv.setImageResource(drawableArray[7]);
        } else if (cardArray[card] == 203) {
            iv.setImageResource(drawableArray[8]);
        } else if (cardArray[card] == 204) {
            iv.setImageResource(drawableArray[9]);
        } else if (cardArray[card] == 205) {
            iv.setImageResource(drawableArray[10]);
        } else if (cardArray[card] == 206) {
            iv.setImageResource(drawableArray[11]);
        }

        // check which image is selected and save it temporary

        if (cardNumber == 1) {

            firstCard = cardArray[card];

            if (firstCard > 200) {

                firstCard = firstCard - 100;

            }
            cardNumber = 2;
            clickedFirst = card;

            iv.setEnabled(false);

        } else if (cardNumber == 2) {

            secondCard = cardArray[card];

            if (secondCard > 200) {

                secondCard = secondCard - 100;

            }
            cardNumber = 1;
            clickedSecond = card;


            for (int k = 0; k < 12; k++) {
                imageArray[k].setEnabled(false);
            }


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    calculate();

                }
            }, 700);
        }
    }

    private void calculate() {
        //if images are equal remove them and add point

        if (firstCard == secondCard) {

            vibrator.vibrate(pattern, -1);
            click2.start();

            for (int y = 0; y < 12; y++) {

                if (clickedFirst == y) {

                    imageArray[y].setVisibility(View.INVISIBLE);

                } else if (clickedSecond == y) {

                    imageArray[y].setVisibility(View.INVISIBLE);
                }
            }

            //add points to the correct player
            if (turn == 1) {

                playerPoints++;
                textViewPlayer1.setText("Player 1: " + playerPoints);

            } else if (turn == 2) {

                player2Points++;
                textViewPlayer2.setText("Player 2: " + player2Points);

            }
            counter++;


        } else {
            for (int t = 0; t < 12; t++) {

                imageArray[t].setImageResource(R.drawable.fz12);


            }
            //change player turn
            if (turn == 1) {

                if (!check2) {
                    textViewPlayer2.setTextColor(colorLightBlue1);
                    textViewPlayer1.setTextColor(colorLightBlack2);
                } else {
                    textViewPlayer2.setTextColor(colorDarkGreen1);
                    textViewPlayer1.setTextColor(colorDarkWhite2);
                }
                turn = 2;

            } else if (turn == 2) {

                if (!check2) {
                    textViewPlayer1.setTextColor(colorLightBlue1);
                    textViewPlayer2.setTextColor(colorLightBlack2);
                } else {
                    textViewPlayer1.setTextColor(colorDarkGreen1);
                    textViewPlayer2.setTextColor(colorDarkWhite2);
                }
                turn = 1;
            }

        }


        for (int w = 0; w < 12; w++) {
            imageArray[w].setEnabled(true);
        }

        checkEnd();

    }


    private void checkEnd() {


        if (counter == 6) {

            crowd.start();

            AlertDialog.Builder alertDiologBuilder = new AlertDialog.Builder(MemorieActivity.this);

            if (playerPoints > player2Points) {
                alertDiologBuilder.setMessage("Game Over! Spieler 1 hat gewonnen\nP1: " + playerPoints + "\nP2: " + player2Points);
            }
            if (playerPoints < player2Points) {
                alertDiologBuilder.setMessage("Game Over! Spieler 2 hat gewonnen\nP1: " + playerPoints + "\nP2: " + player2Points);
            }
            if (playerPoints == player2Points) {
                alertDiologBuilder.setMessage("Game Over! Unentschieden\nP1: " + playerPoints + "\nP2: " + player2Points);
            }
            alertDiologBuilder.setCancelable(false);

            alertDiologBuilder.setPositiveButton("Enter winner and exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    intent = new Intent(MemorieActivity.this, NewWordActivity.class);
                    startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
                }
            });
            AlertDialog alertDialog = alertDiologBuilder.create();
            alertDialog.show();
        }
    }



    public void onActivityResult ( int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word result = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
             mWordViewModel.insert(result);
             intent = new Intent(MemorieActivity.this, MainActivity.class);
             startActivity(intent);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();

        }
    }
        private void frontOfCardsResources () {

            drawableArray[0] = R.drawable.i_101;
            drawableArray[1] = R.drawable.i_102;
            drawableArray[2] = R.drawable.i_103;
            drawableArray[3] = R.drawable.i_104;
            drawableArray[4] = R.drawable.i_105;
            drawableArray[5] = R.drawable.i_106;
            drawableArray[6] = R.drawable.i_201;
            drawableArray[7] = R.drawable.i_202;
            drawableArray[8] = R.drawable.i_203;
            drawableArray[9] = R.drawable.i_204;
            drawableArray[10] = R.drawable.i_205;
            drawableArray[11] = R.drawable.i_206;

        }
}
