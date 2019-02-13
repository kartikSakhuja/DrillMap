package environment.supersonic.com.drillmap.model;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

//to draw line between noedes
public class DrawView extends View {

    Paint paint = new Paint();
    float sx,sy;
    float fx,fy;
    String cost;

    // from startx,starty to finishx,finishy line will be drawn
    public DrawView(Context context, float sx, float sy, float fx, float fy, String cost) {
        super(context);
        this.sx = sx;
        this.sy = sy;
        this.fx = fx;
        this.fy = fy;
        this.cost = cost;

    }

    @Override
    public void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        AlertDialog.Builder alert;          // to use in activtiy


        //For road value
        Paint text = new Paint();
        text.setColor(Color.BLACK);
        text.setTextSize(35f);
        text.setFakeBoldText(true);
        text.setAntiAlias(true);
        text.setTextAlign(Paint.Align.CENTER);
        //paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))



        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);

        canvas.drawLine(sx, sy, fx, fy, paint);
       canvas.drawText(cost, (sx + fx) / 2,(sy+fy)/2,text);



    }

}
