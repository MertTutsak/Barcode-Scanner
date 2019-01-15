package runnable.merttutsak.com.barcodescanner;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

    protected static byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
