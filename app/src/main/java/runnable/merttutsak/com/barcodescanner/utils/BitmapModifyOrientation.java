package runnable.merttutsak.com.barcodescanner.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.IOException;

public class BitmapModifyOrientation {

    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException, IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, false);
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, true);
            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        Log.i("BITMAP_PHOTO", "width : " + bitmap.getWidth() + " - height : " + bitmap.getHeight());

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean orientationVertical) {
        Matrix matrix = new Matrix();
        if (orientationVertical) {
            Log.i("PROFIL_PHOTO", "Photo :Vertical");
            matrix.preScale(-1, 1);
        } else {
            Log.i("PROFIL_PHOTO", "Photo :horizontal");
            matrix.preScale(1, -1);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

}
