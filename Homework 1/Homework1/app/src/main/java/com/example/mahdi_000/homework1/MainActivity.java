package com.example.mahdi_000.homework1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements OnClickListener{


    Button a1, a2, a3, b1, b2, b3, c1, c2, c3, newGame;
    Button[] table;
    boolean turn = true;
    int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Linking all buttons a1 through c3 to the A1 through C3 in the layout
        a1 =(Button)findViewById(R.id.A1);
        a2 =(Button)findViewById(R.id.A2);
        a3 =(Button)findViewById(R.id.A3);
        b1 =(Button)findViewById(R.id.B1);
        b2 =(Button)findViewById(R.id.B2);
        b3 =(Button)findViewById(R.id.B3);
        c1 =(Button)findViewById(R.id.C1);
        c2 =(Button)findViewById(R.id.C2);
        c3 =(Button)findViewById(R.id.C3);
        newGame = (Button) findViewById(R.id.NewGame);

        //array table
        table = new Button[]{a1, a2, a3, b1, b2, b3, c1, c2, c3};

        //now for every button i in the array table set click listener to this.
        //"this" makes MainActivity class the event handler of the buttons
        //this class should implement interface called OnClickListener that contains
        //the method OnClick which is called when the button is clicked
        for(Button i : table){
            i.setOnClickListener(this);
           // boolean winner = checkForWinner();

        }

        newGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                turn = true;
                clickCount = 0;
                //disables all the buttons
                for(Button j : table){
                    j.setClickable(true);
                    j.setText("");
                    //j.setBackgroundColor(getResources().getColor(R.color.BackGround_Gray));
                    j.setBackgroundResource(android.R.drawable.btn_default);
                }

            }
        });


    }

    @Override
    public void onClick(View v) {
        //OnClick is called every time theres a click
        Button b = (Button) v;
        click(b);
    }

    public void click(Button b){
        if(turn == true){
            //X's turn
            b.setText("X");
            turn = false;
        }

        else {
            //O's turn
            b.setText("O");
            turn = true;
        }
        b.setClickable(false);
        boolean winner = false;
        winner = checkForWinner();





        if(winner == true) {
            if (!turn) {
                messageDisplay("X wins");
            } else {
                messageDisplay("O wins");
            }
            //disables all the buttons
            for(Button j : table){
                j.setClickable(false);
            }
        }

        clickCount++;
        if (clickCount == 9 && winner == false){
            messageDisplay("Draw");
        }

    }

    private boolean checkForWinner(){
        //check for hoizontal

        if    ( a1.getText()== a2.getText() && a2.getText() == a3.getText() && !a1.isClickable() ||
                b1.getText()== b2.getText() && b2.getText() == b3.getText() && !b1.isClickable() ||
                c1.getText()== c2.getText() && c2.getText() == c3.getText() && !c1.isClickable() )
        {

            if( a1.getText()== a2.getText() && a2.getText() == a3.getText() && !a1.isClickable()){
                a1.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
                a2.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
                a3.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
            }

            else if( b1.getText()== b2.getText() && b2.getText() == b3.getText() && !b1.isClickable()){
                b1.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
                b2.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
                b3.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
            }

            else if( c1.getText()== c2.getText() && c2.getText() == c3.getText() && !c1.isClickable()){
                c1.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
                c2.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
                c3.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
            }


            return true;

        }



        //check for vertical
        else if(a1.getText()== b1.getText() && b1.getText() == c1.getText() && !a1.isClickable() ||
                a2.getText()== b2.getText() && b2.getText() == c2.getText() && !a2.isClickable() ||
                a3.getText()== b3.getText() && b3.getText() == c3.getText() && !a3.isClickable()) {

            if( a1.getText()== b1.getText() && b1.getText() == c1.getText() && !a1.isClickable()){
                a1.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
                b1.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
                c1.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
            }

            else if( a2.getText()== b2.getText() && b2.getText() == c2.getText() && !a2.isClickable()){
                a2.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
                b2.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
                c2.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
            }

            else if( a3.getText()== b3.getText() && b3.getText() == c3.getText() && !c1.isClickable()){
                a3.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
                b3.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
                c3.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
            }

            return true;
        }

        //check for diagonals
        else if(a1.getText()== b2.getText() && b2.getText() == c3.getText() && !a1.isClickable() ||
                a3.getText()== b2.getText() && b2.getText() == c1.getText() && !a3.isClickable()) {

            if( a1.getText()== b2.getText() && b2.getText() == c3.getText() && !a1.isClickable()){
                a1.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
                b2.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
                c3.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
            }

            else if( a3.getText()== b2.getText() && b2.getText() == c1.getText() && !a3.isClickable()){
                a3.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
                b2.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
                c1.setBackgroundColor(getResources().getColor(R.color.Winner_Green));
            }


            return true;
        }


        return false;

    }

    private void messageDisplay(String message){
        //following line displays a message
        Toast msg;
        msg = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        msg.setGravity(Gravity.TOP, 0, 180);
        msg.show();


    }
}
