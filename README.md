#ScollTextView
**一个自动滚动文字公告控件。支持上，下，左，右4个方向滚出。**  

##效果图
![sample](http://ww4.sinaimg.cn/large/9012f5bdjw1f5j1g7db9mg20bs0j613i.gif)

##使用方法

**build.gradle中加入**  
`compile 'com.lester:scolltextview:1.0.0'`  
**代码中**  
```
scollTextView= (ScollTextView) findViewById(R.id.view);
        List<String> data=new ArrayList<String>();
        for (int i=0;i<20;i++){
            data.add("第"+i+"条测试数据,左边出来");
        }
        scollTextView.setData(data);
```
**布局中**
```
<lester.sample.ScollTextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#6000"
        android:padding="5dp"
        android:id="@+id/view1"
        app:textSize="14sp"
        app:textColor="#fff"
        app:singleLine="true"
        app:ellipsize="end"
        app:direction="right"
        app:delayTime="2000"
        app:gravity="center"
        android:layout_below="@+id/view"
        android:layout_marginTop="30dp"/>
```

##属性说明
| 属性    | 说明   |
| ------- |:-----:|
|textSize|字体大小|
|textColor|字体颜色|
|singleLine|是否单航显示|
|ellipsize|单行显示不完时的处理方式|
|direction|文字滚动方向|
|delayTime|文字滚动间隔时间|
|gravity|文字对其方式|



