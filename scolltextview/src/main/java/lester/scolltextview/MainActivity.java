package lester.scolltextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import lester.scolltextview.scolltextview.ScollTextView;

public class MainActivity extends AppCompatActivity {
    ScollTextView scollTextView;
    List<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scollTextView= (ScollTextView) findViewById(R.id.view);

        data=new ArrayList<String>();

        for (int i=0;i<20;i++){
            data.add("第"+i+"条测试数据00009dasd哒哒哒");
        }
        scollTextView.setData(data);
    }
}
