package environment.supersonic.com.drillmap.model;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import environment.supersonic.com.drillmap.MainActivity;


public class Ball extends View {

    private final float x;
    private final float y;
    private final int r;
    private int number;               // number of the node


    //private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public Ball(Context context, float x, float y, int r, int number) {
        super(context);
        this.x = x;
        this.y = y;
        this.r = r;
        this.number =number;
        MainActivity.number++;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(20f);
        paint.setFakeBoldText(true);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER); */

        /*Rect bounds = new Rect();
        paint.getTextBounds(number, 0, "1".length(), bounds); */

        //canvas.drawCircle(x, y, r, mPaint);
        //canvas.drawText(String.valueOf(number), x, y, paint);


    }
}
