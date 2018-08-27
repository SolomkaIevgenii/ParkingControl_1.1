package com.example.john.parkingcontrol;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.parkingcontrol.API.HttpClient;

import java.io.IOException;
import java.net.URISyntaxException;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myAsyncTask.cancel(true);
    }

    public static class MyAsyncTask extends AsyncTask<String, Integer, String>{

        private TextView resultView;

        public MyAsyncTask(TextView resultView){
            this.resultView = resultView;
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return new HttpClient().request(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            resultView.setText("Loading..."+values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s==null){
                Toast.makeText(resultView.getContext(), "Error", Toast.LENGTH_SHORT).show();
            }else {
                resultView.setText(s);
            }
        }
    }
}
