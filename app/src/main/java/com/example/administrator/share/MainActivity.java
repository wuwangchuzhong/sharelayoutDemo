package com.example.administrator.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;

import static com.example.administrator.share.Commot.convertViewToBitmap;
import static com.example.administrator.share.Commot.saveBitmapFile;


public class MainActivity extends Activity {

    private RelativeLayout ll_wholef;
    private ScrollView activity_main;
    private ImageView iv_share;
    private ImageView iv_erweima;
    private TextView tv_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        View view = LayoutInflater.from(this).inflate(
//                R.layout.activity_main, null);
        ll_wholef = (RelativeLayout) findViewById(R.id.ll_wholef);

        activity_main = (ScrollView) findViewById(R.id.activity_main);

        iv_share = (ImageView) findViewById(R.id.iv_share);

        tv_click = (TextView)findViewById(R.id.tv_click);

        iv_erweima = (ImageView)findViewById(R.id.iv_erweima);

//        Bitmap bitmap=getImgFile(activity_main, MainActivity.this);

        tv_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //直接生成二维码http://www.csdn.net
        Bitmap qrBitmap = generateBitmap("http://www.csdn.net",200, 200);
        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.appposition);
        Bitmap bitmap = addLogo(qrBitmap, logoBitmap);
        Log.i("图片bitmap",bitmap.toString()+"二维码");
        iv_erweima.setImageBitmap(bitmap);
        //布局生成bitmap
        Bitmap bitmap1=convertViewToBitmap(activity_main);
        //保存图片
        saveBitmapFile(bitmap1);
        Log.i("图片bitmap",bitmap1.toString());
        //显示生成整个的图片
        iv_share.setImageBitmap(bitmap1);
        Commot.saveImageToGallery(getApplicationContext(),bitmap1);
        Commot.saveImage(bitmap1);
    }



    public Bitmap generateBitmap(String content,int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bitmap addLogo(Bitmap qrBitmap, Bitmap logoBitmap) {
        int qrBitmapWidth = qrBitmap.getWidth();
        int qrBitmapHeight = qrBitmap.getHeight();
        int logoBitmapWidth = logoBitmap.getWidth();
        int logoBitmapHeight = logoBitmap.getHeight();
        Bitmap blankBitmap = Bitmap.createBitmap(qrBitmapWidth, qrBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blankBitmap);
        canvas.drawBitmap(qrBitmap, 0, 0, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        float scaleSize = 1.0f;
        while ((logoBitmapWidth / scaleSize) > (qrBitmapWidth / 5) || (logoBitmapHeight / scaleSize) > (qrBitmapHeight / 5)) {
            scaleSize *= 2;
        }
        float sx = 1.0f / scaleSize;
        canvas.scale(sx, sx, qrBitmapWidth / 2, qrBitmapHeight / 2);
        canvas.drawBitmap(logoBitmap, (qrBitmapWidth - logoBitmapWidth) / 2, (qrBitmapHeight - logoBitmapHeight) / 2, null);
        canvas.restore();
        return blankBitmap;
    }
}
