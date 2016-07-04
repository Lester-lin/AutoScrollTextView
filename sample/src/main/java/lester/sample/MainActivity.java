package lester.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ScollTextView scollTextView,v1,v2,v3;
    List<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scollTextView= (ScollTextView) findViewById(R.id.view);
        v1= (ScollTextView) findViewById(R.id.view1);
        v2= (ScollTextView) findViewById(R.id.view2);
        v3= (ScollTextView) findViewById(R.id.view3);

        data=new ArrayList<String>();

        for (int i=0;i<20;i++){
            data.add("第"+i+"条测试数据00009dasd哒哒哒");
        }
        scollTextView.setData(data);
        v1.setData(data);
        v2.setData(data);
        v3.setData(data);
    }
}
