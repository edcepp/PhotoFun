package net.zdome.cs371.epp.photofun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Color;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;

public class PhotoFun extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_fun);

        Button grayFilterButton = (Button) findViewById(R.id.grayFilterButton);
        grayFilterButton.setOnClickListener(new filterButtonListener());
        Button brightnessFilterButton = (Button) findViewById(R.id.brightnessFilterButton);
        brightnessFilterButton.setOnClickListener(new filterButtonListener());
    }

    private int constrain (int color){
        if (color > 255)
            return 255;
        else if (color < 0)
            return 0;
        else
            return color;
    }

    private class filterButtonListener implements View.OnClickListener {
        public void onClick(View button) {

            ImageView originalImageView = (ImageView) findViewById(R.id.originalImage);
            BitmapDrawable originalDrawableBmp = (BitmapDrawable) originalImageView.getDrawable();
            Bitmap originalBmp = originalDrawableBmp.getBitmap();

            int width = originalBmp.getWidth();
            int height = originalBmp.getHeight();
            Bitmap newBmp = Bitmap.createBitmap(width, height, originalBmp.getConfig());

            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    int pixel = originalBmp.getPixel(w, h);
                    if (button.getId() == R.id.grayFilterButton) {
                        int intensity = (Color.red(pixel) + Color.green(pixel) + Color.blue(pixel)) / 3;
                        int grayPixel = Color.argb(Color.alpha(pixel), intensity, intensity, intensity);
                        newBmp.setPixel(w, h, grayPixel);
                    } else if (button.getId() == R.id.brightnessFilterButton) {
                        int adjustment = 50;
                        int red = constrain(Color.red(pixel) + adjustment);
                        int green = constrain(Color.green(pixel) + adjustment);
                        int blue = constrain(Color.blue(pixel) + adjustment);
                        int colorPixel = Color.argb(Color.alpha(pixel), red, green, blue);
                        newBmp.setPixel(w, h, colorPixel);
                    }
                    else {
                        //TBD add error box
                    }
                }
            }

            ImageView newImageView = (ImageView) findViewById(R.id.newImage);
            newImageView.setImageBitmap(newBmp);
        }
    }
}

