package com.example.administrator.tictactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttons[][]=new Button[3][3];
    private boolean player1Turn=true;
    private int roundCount;
    private int player1Points,player2Points;
    private TextView textViewPlayer1, textViewPlayer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewPlayer1=findViewById(R.id.text_view_p1);
        textViewPlayer2=findViewById(R.id.text_view_p2);

        for(int i=0;i<3;i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID= "button_"+ i + j;
                int resID=getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j]=findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
       Button buttonReset=findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        if(player1Turn)
        {
            ((Button)v).setText("X");
            ((Button)v).setBackgroundColor(Color.RED);
        }
        else{
            ((Button)v).setText("O");
            ((Button)v).setBackgroundColor(Color.GREEN);
        }
        roundCount++;
        if(checkForWin()){
            if(player1Turn)
            {
                player1Wins();
            }else{
                player2Wins();
            }
        }else if(roundCount==9){
            draw();
        }else{
            player1Turn=!player1Turn;
        }
    }

    private boolean checkForWin()
    {
        String[][] field=new String[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                field[i][j]=buttons[i][j].getText().toString();
            }
        }
        //rowwise
        for(int i=0;i<3;i++)
        {
            if(field[i][0].equals(field[i][1])&&field[i][1].equals(field[i][2])&&!(field[i][0].equals("")))
                return true;
        }
        //columnwise
        for(int i=0;i<3;i++)
        {
            if(field[0][i].equals(field[1][i])&&field[1][i].equals(field[2][i])&&!(field[0][i].equals("")))
                return true;
        }
        if(field[0][0].equals(field[1][1])&&field[1][1].equals(field[2][2])&&!(field[0][0].equals("")))
            return true;
        if(field[0][2].equals(field[1][1])&&field[1][1].equals(field[2][0])&&!(field[0][2].equals("")))
            return true;
        return false;
    }

    private void player1Wins(){
        player1Points++;
        Toast.makeText(this,"Player1 wins!",Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard();
    }
    private void player2Wins(){
        player2Points++;
        Toast.makeText(this,"Player2 wins!",Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard();
    }
    private void draw(){
        Toast.makeText(this,"Draw!!",Toast.LENGTH_LONG).show();
        resetBoard();
    }
    private void updatePointsText(){
        textViewPlayer1.setText("Player1 :"+player1Points);
        textViewPlayer2.setText("Player2: "+player2Points);
    }
    private void resetBoard(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
                buttons[i][j].setBackgroundResource(android.R.drawable.btn_default);
            }
        }
        roundCount=0;
        player1Turn=true;
    }
//when reset button is pressed
    private void resetGame(){
        player1Points=0;
        player2Points=0;
        updatePointsText();
        resetBoard();
    }
//rotation handling. text is frozen for the button
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount",roundCount);
        outState.putInt("player1Points",player1Points);
        outState.putInt("player2Points",player2Points);
        outState.putBoolean("player1Turn",player1Turn);
    }
//retrieving states after rotation
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount=savedInstanceState.getInt("roundCount");
        player1Points=savedInstanceState.getInt("player1Points");
        player2Points=savedInstanceState.getInt("player2Points");
        player1Turn=savedInstanceState.getBoolean("player1Turn");
    }
}
