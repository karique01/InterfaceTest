package pe.edu.upc.testfree.Widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import pe.edu.upc.testfree.Model.TextInExternalRound;
import pe.edu.upc.testfree.Model.TextInMiddleRound;
import pe.edu.upc.testfree.R;

public class RoundedComponentView extends AppCompatImageView {
    public int centerX;
    public int centerY;
    public float externalRadius;
    public float middleRadius;

    public float externalTextSize;
    public float externalBorderSize;
    public int externalGapTextBorder;
    public int externalTextColor;
    public int externalBorderColor;
    public float middleTextSize;
    public float middleBorderSize;
    public int middleGapTextBorder;
    public int middleTextColor;
    public int middleBorderColor;

    private float externalGapBetweenLetters;
    private float middleGapBetweenLetters;
    private TextInExternalRound externalQuotes;
    private Path externalCirclePath;
    private Paint externalCirclePaint;
    private Paint externalTextPaint;
    private TextInMiddleRound middleQuotes;
    private Path middleCirclePath;
    private Paint middleCirclePaint;
    private Paint middleTextPaint;

    Bitmap mImage;
    Rect srcRect;
    Rect destRect;
    private int radius;

    public RoundedComponentView(Context context) {
        super(context);
        init(null);
    }
    public RoundedComponentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }
    public RoundedComponentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set){
        if(set != null){
            TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.RoundedComponentView);
            centerX = ta.getInteger(R.styleable.RoundedComponentView_centerX, 570);
            centerY = ta.getInteger(R.styleable.RoundedComponentView_centerY, 1200);
            externalRadius = ta.getFloat(R.styleable.RoundedComponentView_externalRadius, 130);
            middleRadius = ta.getFloat(R.styleable.RoundedComponentView_middleRadius, 95);

            externalGapTextBorder = ta.getInteger(R.styleable.RoundedComponentView_externalGapTextBorder, 5);
            middleGapTextBorder = ta.getInteger(R.styleable.RoundedComponentView_middleGapTextBorder, 5);

            externalBorderSize = ta.getFloat(R.styleable.RoundedComponentView_externalBorderSize, 38);
            middleBorderSize = ta.getFloat(R.styleable.RoundedComponentView_middleBorderSize, 32);

            externalTextSize = ta.getFloat(R.styleable.RoundedComponentView_externalTextSize, 18);
            middleTextSize = ta.getFloat(R.styleable.RoundedComponentView_middleTextSize, 11);

            externalGapBetweenLetters = ta.getFloat(R.styleable.RoundedComponentView_externalGapBetweenLetters, 0.1f);
            middleGapBetweenLetters = ta.getFloat(R.styleable.RoundedComponentView_middleGapBetweenLetters, 0.15f);

            externalTextColor = ta.getColor(R.styleable.RoundedComponentView_externalTextColor, Color.GREEN);
            externalBorderColor = ta.getColor(R.styleable.RoundedComponentView_externalBorderColor, Color.GREEN);
            middleTextColor = ta.getColor(R.styleable.RoundedComponentView_middleTextColor, Color.GREEN);
            middleBorderColor = ta.getColor(R.styleable.RoundedComponentView_middleBorderColor, Color.GREEN);
            ta.recycle();
        }

        externalQuotes = new TextInExternalRound();
        externalQuotes.addText("THE PIONEER");
        externalQuotes.addText("THE PIONEER");
        middleQuotes = new TextInMiddleRound();
        middleQuotes.addText("THE PIONEER");
        middleQuotes.addText("FRANK W MICHELL");
        middleQuotes.addText("THE PIONEER");
        middleQuotes.addText("FRANK W MICHELL");

        externalCirclePath = new Path();
        externalTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        externalTextPaint.setStyle(Paint.Style.FILL);
        externalTextPaint.setColor(externalTextColor);
        externalTextPaint.setTextSize(externalTextSize);
        externalTextPaint.setLetterSpacing(externalGapBetweenLetters);
        externalCirclePaint = new Paint();
        externalCirclePaint.setStyle(Paint.Style.STROKE);
        externalCirclePaint.setStrokeWidth(externalBorderSize);
        externalCirclePaint.setColor(externalBorderColor);

        middleCirclePath = new Path();
        middleTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        middleTextPaint.setStyle(Paint.Style.FILL);
        middleTextPaint.setColor(middleTextColor);
        middleTextPaint.setTextSize(middleTextSize);
        middleTextPaint.setLetterSpacing(middleGapBetweenLetters);
        middleCirclePaint = new Paint();
        middleCirclePaint.setStyle(Paint.Style.STROKE);
        middleCirclePaint.setStrokeWidth(middleBorderSize);
        middleCirclePaint.setColor(middleBorderColor);

        mImage = BitmapFactory.decodeResource(getResources(), R.drawable.frank_michell);
        radius = (int)middleRadius;
        srcRect = new Rect(0,0, mImage.getWidth(), mImage.getHeight());
        destRect = new Rect(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
    private void drawBorders(Canvas canvas){
        canvas.drawCircle(centerX, centerY, externalRadius, externalCirclePaint);
        externalCirclePath.addCircle(centerX, centerY, externalRadius + externalGapTextBorder, Path.Direction.CCW);
        List<String> externalQuotesTexts = externalQuotes.getTexts();
        for (int i = 0; i < externalQuotesTexts.size(); i++) {
            String text = externalQuotesTexts.get(i);
            canvas.drawTextOnPath(text, externalCirclePath, i * 380, 0, externalTextPaint);
        }

        canvas.drawCircle(centerX, centerY, middleRadius, middleCirclePaint);
        middleCirclePath.addCircle(centerX, centerY, middleRadius + middleGapTextBorder, Path.Direction.CCW);
        List<String> middleQuotesTexts = middleQuotes.getTexts(); //130 + 40 y asi sigue
        String text = middleQuotesTexts.get(0);
        canvas.drawTextOnPath(text, middleCirclePath, 0, 0, middleTextPaint);
        text = middleQuotesTexts.get(1);
        canvas.drawTextOnPath(text, middleCirclePath, 120, 0, middleTextPaint);
        text = middleQuotesTexts.get(2);
        canvas.drawTextOnPath(text, middleCirclePath, 150, 0, middleTextPaint);
        text = middleQuotesTexts.get(3);
        canvas.drawTextOnPath(text, middleCirclePath, 300, 0, middleTextPaint);
        text = middleQuotesTexts.get(4);
        canvas.drawTextOnPath(text, middleCirclePath, 330, 0, middleTextPaint);
        text = middleQuotesTexts.get(5);
        canvas.drawTextOnPath(text, middleCirclePath, 440, 0, middleTextPaint);
        text = middleQuotesTexts.get(6);
        canvas.drawTextOnPath(text, middleCirclePath, 470, 0, middleTextPaint);
        text = middleQuotesTexts.get(7);
        canvas.drawTextOnPath(text, middleCirclePath, 615, 0, middleTextPaint);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        centerX = getWidth()/2;
        destRect.left = centerX - radius;
        destRect.right = centerX + radius;
        canvas.drawBitmap(mImage, srcRect, destRect, null);
        drawBorders(canvas);
    }

}









































