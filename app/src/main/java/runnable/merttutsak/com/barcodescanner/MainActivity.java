package runnable.merttutsak.com.barcodescanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import runnable.merttutsak.com.barcodescanner.utils.BitmapModifyOrientation;

public class MainActivity extends BaseActivity {

    TextView textView;
    ImageView imageView;
    Button button;
    Button buttonTakePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.txtContent);
        imageView = (ImageView) findViewById(R.id.imgview);
        button = (Button) findViewById(R.id.button);
        buttonTakePhoto = (Button) findViewById(R.id.button_take_photo);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(decodeCode());
            }
        });

        buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bytes != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            imageView.getLayoutParams().width = displayMetrics.widthPixels;
            imageView.getLayoutParams().height = displayMetrics.heightPixels / 2;
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            Bitmap bitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
            imageView.setImageBitmap(BitmapModifyOrientation.rotate(bitmap, 0));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        } else {
            final Bitmap bitmap = BitmapFactory.decodeResource(
                    getApplicationContext().getResources(),
                    R.drawable.barcode_01);

            imageView.setImageBitmap(bitmap);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
    }

    public String decodeCode() {
        //Barcode Detector
        BarcodeDetector detector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(0)
                .build();

        Bitmap bitmap = null;

        if (bytes != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            bitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
        } else {
            bitmap = BitmapFactory.decodeResource(
                    getApplicationContext().getResources(),
                    R.drawable.barcode_01);
        }

        if (!detector.isOperational()) {
            return "Could not set up the detector!";
        }

        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Barcode> barcodes = detector.detect(frame);

        if (barcodes.size() > 0) {
            Barcode thisCode = barcodes.valueAt(0);
            return thisCode.rawValue;
        } else {
            return "Couldn't find the barcode";
        }
    }
}
