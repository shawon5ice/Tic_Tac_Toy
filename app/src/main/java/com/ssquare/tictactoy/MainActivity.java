package com.ssquare.tictactoy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //vars
    // 0: red, 1: yellow, 2: empty
    int activePlayer = 0;
    boolean gameActive = true;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    //Widgets
    TextView player1,player2;
    Button resetBoard,resetGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        resetBoard = findViewById(R.id.resetGame);
        resetGame = findViewById(R.id.resetGame);
    }

    public void dropIn(View view){
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter]==2 && gameActive){
            counter.setTranslationY(-1500);
            gameState[tappedCounter] = activePlayer;
            if(activePlayer==0){
                counter.setImageResource(R.drawable.red);
                activePlayer = 1;
            }else{
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);
            checkWin();
        }

    }

    public void checkWin(){
        for(int[] winningPosition:winningPositions){
            if(gameState[winningPosition[0]]==gameState[winningPosition[1]] && gameState[winningPosition[1]]==gameState[winningPosition[2]]
                && gameState[winningPosition[0]]!=2){
                gameActive = false;
                int p1 = Integer.parseInt(player1.getText().toString());
                int p2 = Integer.parseInt(player2.getText().toString());

                int who = gameState[winningPosition[0]];
                if(who==0){
                    p1++;
                    player1.setText(String.valueOf(p1));
                }else {
                    p2++;
                    player2.setText(String.valueOf(p2));
                }
            }
        }
    }
    public void playAgain(View view) {

        GridLayout gridLayout = findViewById(R.id.grid_layout);

        for(int i=0; i<gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);

        }

        for (int i=0; i<gameState.length; i++) {
            gameState[i] = 2;
        }
        gameActive = true;
    }
    public void clearGame(View view){
        playAgain(view);
        player1.setText("0");
        player2.setText("0");
        activePlayer = 0;
        Toast.makeText(this,"Game cleared successfully",Toast.LENGTH_SHORT).show();
    }

    public String checkWinner(){
        String winner;
        if(Integer.parseInt(player1.getText().toString())==Integer.parseInt(player2.getText().toString())){
            winner = "Nobody";
        }
        if(Integer.parseInt(player1.getText().toString())>Integer.parseInt(player2.getText().toString())){
            winner = "Red";
        }else{
            winner = "Yellow";
        }
        return winner;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder aleartDialog = new AlertDialog.Builder(MainActivity.this);
        aleartDialog.setTitle("Tic Tac Toy").setMessage("Do you want to exit the game?").setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).create();
        aleartDialog.show();
    }
}