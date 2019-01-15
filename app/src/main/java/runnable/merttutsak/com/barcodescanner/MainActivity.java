package runnable.merttutsak.com.barcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class MainActivity extends BaseActivity {

    TextView textView;
    ImageView imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.txtContent);
        imageView = (ImageView) findViewById(R.id.imgview);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Image
        if (bytes != null) {
            /*
            final Bitmap bitmap = BitmapFactory.decodeResource(
                    getApplicationContext().getResources(),
                    R.drawable.ean13);
            imageView.setImageBitmap(bitmap);
            */
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            Bitmap bitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
            imageView.setImageBitmap(BitmapModifyOrientation.rotate(bitmap,90));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            //Barcode Detector
            BarcodeDetector detector = new BarcodeDetector.Builder(getApplicationContext())
                    .setBarcodeFormats(0)
                    .build();

            if (!detector.isOperational()) {
                textView.setText("Could not set up the detector!");
                return;
            }

            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<Barcode> barcodes = detector.detect(frame);

            if (barcodes.size() > 0) {
                Barcode thisCode = barcodes.valueAt(0);
                textView.setText(thisCode.rawValue);
            }else {
                textView.setText("Couldn't find the barcode");
            }
        }
    }
}
