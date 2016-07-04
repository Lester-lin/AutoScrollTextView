package lester.scolltextview.scolltextview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.List;

import lester.scolltextview.R;

/**
 * 作者：liuwanlin
 * 创建日期：16/7/1
 */
public abstract class BaseScollTextView<T> extends TextSwitcher implements ViewSwitcher.ViewFactory {
    private List<T> data;
    private Context context;
    private int textSize = 14;
    private int textColor = 0xff000000;
    private boolean singleLine = false;
    private int gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;

    private Handler handler;
    private int count = 0;
    private int position = 0;
    private long DELAY_TIME = 2000;
    protected final int MSG_WHAT = 0x0;
    private Direction direction = Direction.RIGHT;
    private OnItemClickListener onItemClickListener;

    public BaseScollTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BaseScollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }
    public enum Direction {
        TOP,
        DOWN,
        LEFT,
        RIGHT
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void init() {
        handler = new ScollHandle();
        switch (direction) {
            case TOP:
                setOutAnimation(context, R.anim.push_up_out);
                setInAnimation(context, R.anim.push_up_in);
                break;
            case DOWN:
                setOutAnimation(context, R.anim.push_down_out);
                setInAnimation(context, R.anim.push_down_in);
                break;
            case LEFT:
                setOutAnimation(context, R.anim.push_left_out);
                setInAnimation(context, R.anim.push_left_in);
                break;
            case RIGHT:
                setOutAnimation(context, R.anim.push_right_out);
                setInAnimation(context, R.anim.push_right_in);
                break;
        }
        setFactory(this);
    }

    public void setData(List<T> data) {
        if (null == data) {
            return;
        }
        this.data = data;
        position = count % data.size();
        setText(getItemText(data, position));

        handler.sendEmptyMessageDelayed(MSG_WHAT, DELAY_TIME);
    }

    public abstract String getItemText(List<T> data, int postion);

    @Override
    public View makeView() {
        final TextView tv = new TextView(this.context);
        tv.setGravity(gravity);
        tv.setTextColor(textColor);
        tv.setTextSize(textSize);
        tv.setSingleLine(singleLine);
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, tv);
                }
            }
        });
        return tv;
    }


    class ScollHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WHAT:
                    count++;
                    position = count % data.size();
                    setText(getItemText(data, position));
                    sendEmptyMessageDelayed(MSG_WHAT, DELAY_TIME);
                    break;
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, TextView textView);
    }
}
