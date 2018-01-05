package libraries.legency.com.mkblurrydialog;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.text.Html;
import android.text.Spanned;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by madrit on 14/11/2017.
 */
//Utils functions
public class Utils {


    // get the screenshoot of a viewgroup by passing @view ViewGroup  it as pareamether

    public static Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    // html tags

    public static Spanned fromHtml(String string){
        Spanned mString = Html.fromHtml(string);
        return mString;
    }

    //---------------------------------------DATES--------------------------------------------------

    public static String getFormattedDate(String startFormat, String endFormat, String dateString) {

        if (dateString == null){
            return null;
        }

        SimpleDateFormat simpleDateFormatStart = new SimpleDateFormat(startFormat);
        SimpleDateFormat simpleDateFormatEnd = new SimpleDateFormat(endFormat);

        Date objectDate = null;
        try {
           objectDate = simpleDateFormatStart.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (objectDate == null){
            return null;
        }

       return simpleDateFormatEnd.format(objectDate);
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}
