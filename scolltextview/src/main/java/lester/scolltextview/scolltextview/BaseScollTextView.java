package lester.scolltextview.scolltextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
 * 创建日期：16/7/4
 */
public abstract class BaseScollTextView<T> extends TextSwitcher implements ViewSwitcher.ViewFactory {
    private static final int TEXT_GRAVITY_CENTER = 1;
    private static final int TEXT_GRAVITY_RIGHT = 2;
    private static final int TOP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    private List<T> data;
    private Context context;
    private int textSize = 14;
    private int textColor = 0xff000000;
    private boolean singleLine = false;
    private TextUtils.TruncateAt ellipsize= TextUtils.TruncateAt.START;
    private int gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;

    private Handler handler;
    private int count = 0;
    private int position = 0;
    private int delayTime = 2000;
    protected final int MSG_WHAT = 0x0;
    private OnItemClickListener onItemClickListener;
    private int direction = TOP;

    public BaseScollTextView(Context context) {
        this(context, null);
    }

    public BaseScollTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseScollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        this.context = context;
        init(attrs, defStyleAttr);
    }

    public void setEllipsize(TextUtils.TruncateAt ellipsize) {
        this.ellipsize = ellipsize;
    }
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setSingleLine(boolean singleLine) {
        this.singleLine = singleLine;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        handler = new ScollHandle();
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BaseScollTextView, defStyleAttr, 0);
        singleLine = typedArray.getBoolean(R.styleable.BaseScollTextView_singleLine, false);
        delayTime = typedArray.getInteger(R.styleable.BaseScollTextView_delayTime, delayTime);
        if (typedArray.hasValue(R.styleable.BaseScollTextView_textSize)) {
            textSize = (int) typedArray.getDimension(R.styleable.BaseScollTextView_textSize, textSize);
        }
        textColor = typedArray.getColor(R.styleable.BaseScollTextView_textColor, textColor);
        int ep = typedArray.getInt(R.styleable.BaseScollTextView_ellipsize, 0);
        switch (ep){
            case 0:
                ellipsize=TextUtils.TruncateAt.START;
                break;
            case 1:
                ellipsize=TextUtils.TruncateAt.MIDDLE;
                break;
            case 2:
                ellipsize=TextUtils.TruncateAt.END;
                break;
            case 3:
                ellipsize=TextUtils.TruncateAt.MARQUEE;
                break;
        }
        int gravityType = typedArray.getInt(R.styleable.BaseScollTextView_gravity, gravity);
        switch (gravityType) {
            case TEXT_GRAVITY_CENTER:
                gravity = Gravity.CENTER;
                break;
            case TEXT_GRAVITY_RIGHT:
                gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
        direction = typedArray.getInt(R.styleable.BaseScollTextView_direction, 0);
        typedArray.recycle();
        setFactory(this);
    }

    public void setData(List<T> data) {
        if (null == data) {
            return;
        }
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
        this.data = data;
        position = count % data.size();
        setText(getItemText(data, position));

        handler.sendEmptyMessageDelayed(MSG_WHAT, delayTime);
    }

    public abstract String getItemText(List<T> data, int postion);

    @Override
    public View makeView() {
        final TextView tv = new TextView(this.context);
        tv.setGravity(gravity);
        tv.setTextColor(textColor);
        tv.setTextSize(textSize);
        tv.setSingleLine(singleLine);
        tv.setEllipsize(ellipsize);
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
                    sendEmptyMessageDelayed(MSG_WHAT, delayTime);
                    break;
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, TextView textView);
    }
}
