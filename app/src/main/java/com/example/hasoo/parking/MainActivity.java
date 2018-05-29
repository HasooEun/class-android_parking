package com.example.hasoo.parking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView textView1;
    TextView tvCarType[] = new TextView[4];
    TextView tvCarNum[] = new TextView[4];
    TextView tvInTime[] = new TextView[4];

    ParkingInfo parkingInfo[] = new ParkingInfo[4];
    int parkNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=0; i<4; i++){
            parkingInfo[i] = new ParkingInfo();
        }

        // make instance
        textView1 = (TextView)findViewById(R.id.textView);

        tvCarType[0] = (TextView)findViewById(R.id.textView2);
        tvCarNum[0] = (TextView)findViewById(R.id.textView3);
        tvInTime[0] = (TextView)findViewById(R.id.textView4);

        tvCarType[1] = (TextView)findViewById(R.id.textView5);
        tvCarNum[1] = (TextView)findViewById(R.id.textView6);
        tvInTime[1] = (TextView)findViewById(R.id.textView7);

        tvCarType[2] = (TextView)findViewById(R.id.textView8);
        tvCarNum[2] = (TextView)findViewById(R.id.textView9);
        tvInTime[2] = (TextView)findViewById(R.id.textView10);

        tvCarType[3] = (TextView)findViewById(R.id.textView11);
        tvCarNum[3] = (TextView)findViewById(R.id.textView12);
        tvInTime[3] = (TextView)findViewById(R.id.textView13);

        final EditText editText1 = (EditText)findViewById(R.id.editText);
        final EditText editText2 = (EditText)findViewById(R.id.editText2);

        Button button1 = (Button)findViewById(R.id.button);
        Button button2 = (Button)findViewById(R.id.button2);

        // set OnClick Listener for inCar button
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String carType = editText1.getText().toString();
                String carNum = editText2.getText().toString();

                if(carType.isEmpty()){
                    Toast.makeText(getApplicationContext(), "차종을 입력하세요.", Toast.LENGTH_SHORT).show();
                } else if(carNum.isEmpty()){
                    Toast.makeText(getApplicationContext(), "차번을 입력하세요.", Toast.LENGTH_SHORT).show();
                }

                Date inDate = new Date();
                long inTime = inDate.getTime();
                parkingInfo[parkNumber-1].setParkingTime(carType, carNum, inTime);
                printInformation();
            }
        });

        // set OnClick Listener for outCar button
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!parkingInfo[parkNumber-1].isParked()){
                    Toast.makeText(getApplicationContext(), "차량이 없습니다.", Toast.LENGTH_SHORT).show();
                }

                Date outDate = new Date();
                long outTime = outDate.getTime();
                long inTime = parkingInfo[parkNumber-1].getParkingTime();
                long elapsedTime = outTime - inTime;
                int elapsedMinute = (int)(elapsedTime/(1000 * 60));
                int price = (elapsedMinute % 30 + 1) * 1000;
                String sPrice = "주차시간 " + elapsedMinute + "분의 주차요금은 "
                        + price + "원 입니다.";
                Toast.makeText(getApplicationContext(), sPrice, Toast.LENGTH_SHORT).show();

                // cleaning
                parkingInfo[parkNumber-1] = new ParkingInfo();
                printInformation();
            }
        });
    }

    public void onParkSelectClicked(View v){

        if(v.getId() == R.id.button3){
            parkNumber = 1;
        } else if(v.getId() == R.id.button4){
            parkNumber = 2;
        } else if(v.getId() == R.id.button5){
            parkNumber = 3;
        } else if(v.getId() == R.id.button6){
            parkNumber = 4;
        }

        String sParkNumber = parkNumber + "번";
        textView1.setText(sParkNumber);
    }

    private void printInformation(){

        String sCarType = "차종류: " + parkingInfo[parkNumber-1].getCarType();
        String sCarNum = "차번호: " + parkingInfo[parkNumber-1].getCarNum();
        long lInTime = parkingInfo[parkNumber-1].getParkingTime();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
        String sInTime = "입차시간: " + (lInTime==0?"":sdfDate.format(lInTime));

        tvCarType[parkNumber-1].setText(sCarType);
        tvCarNum[parkNumber-1].setText(sCarNum);
        tvInTime[parkNumber-1].setText(sInTime);
    }
}
