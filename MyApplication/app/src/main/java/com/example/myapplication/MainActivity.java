package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    private static final long START_TIME = 600000;
    private TextView mTextViewCountDown;
    private Button mPausarComecar;
    private Button mResetar;
    private TableLayout container;
    private ImageButton mAdicionar;
    static int rowIndex = 0;
    private CountDownTimer mCountDown;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_MyApplication);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (TableLayout) findViewById(R.id.containerLayout);
        mTextViewCountDown = findViewById(R.id.timeTV);
        mPausarComecar = findViewById(R.id.startStopButton);
        mResetar = findViewById(R.id.resetButton);
        mAdicionar = findViewById(R.id.imageButton3);

        mAdicionar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View newRowView = inflater.inflate(R.layout.rowadd,null);
                container.addView(newRowView,rowIndex);
                rowIndex++;
                            }});




        mPausarComecar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                if(mTimerRunning){
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        }

        );
        mResetar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                resetTimer();
            }
        });

        updateCountDownText();

    }
    private void startTimer(){
        mCountDown = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mPausarComecar.setText("Iniciar");
            }
        }.start();
        mTimerRunning = true;
        mPausarComecar.setText("Pausar");
    }
    private void pauseTimer(){
        mCountDown.cancel();
        mTimerRunning = false;
        mPausarComecar.setText("Iniciar");
    }
    private void resetTimer(){
        mTimeLeftInMillis = START_TIME;
        updateCountDownText();
    }

    private void updateCountDownText(){
        int hours = (int) (mTimeLeftInMillis/1000)/3600;
        int minutes = (int) ((mTimeLeftInMillis/1000)%3600)/60;
        int seconds = (int) (mTimeLeftInMillis/1000)%60;

        String timeLeftF;
        if(hours> 0){
            timeLeftF = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftF = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
        mTextViewCountDown.setText(timeLeftF);
    }
    public void onDelete(View view) {
        container.removeView((View) view.getParent());
        container.invalidate();
        rowIndex--;
    }

            public void onConfig(View view) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
            alerta.setTitle("Tempo em segundos");

            final EditText weightInput = new EditText( MainActivity.this);
            weightInput.setInputType(InputType.TYPE_CLASS_NUMBER);
            alerta.setView(weightInput);

            alerta.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int tempo = Integer.parseInt(String.valueOf(weightInput.getText()));
                    tempo = tempo*1000;
                    mTimeLeftInMillis = tempo;
                    updateCountDownText();

                }});
            alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            alerta.show();
        }
    }



