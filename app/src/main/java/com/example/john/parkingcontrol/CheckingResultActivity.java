package com.example.john.parkingcontrol;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CheckingResultActivity extends AppCompatActivity {

    public static String enteredCarNumber;

    private TextView resultView;
    private MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking_result);

        resultView = findViewById(R.id.textViewResult);

        myAsyncTask = new MyAsyncTask(resultView);
        myAsyncTask.execute("http://parking.2click.money/ExtApi/carState?id="+enteredCarNumber+"&accToken=fdf909e4j3f03jikdsjfpsdg9sdfd0ifjsdik");
    }

    public static class MyAsyncTask extends AsyncTask<String, Integer, String>{

        private TextView resultView;

        public MyAsyncTask(TextView resultView){
            this.resultView = resultView;
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}
