package testsample.altvr.com.testsample.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import testsample.altvr.com.testsample.vo.PhotoVo;

/**
 * Created by tejus on 4/14/2016.
 */
public class DatabaseUtil extends SQLiteOpenHelper {
    private LogUtil log = new LogUtil(DatabaseUtil.class);

    private static final int DATABASE_VERSION = 2;
    //DB and tables
    private static final String DATABASE_NAME = "imagesearcher";
    private static final String TABLE_PHOTOS = "photos";

    //Columns for Images table
    private static final String KEY_PHOTO_ID = "id";
    private static final String KEY_PHOTO = "photo";

    SQLiteDatabase mDb;

    public DatabaseUtil(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mDb = getWritableDatabase();
        //onCreate(mDb);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_IMAGES_TABLE = "CREATE TABLE " + TABLE_PHOTOS + "("
                + KEY_PHOTO_ID + " STRING PRIMARY KEY," + KEY_PHOTO + " TEXT" + ")";
        db.execSQL(CREATE_IMAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);
        onCreate(db);
    }


    public boolean saveImage  (String photo_id, String photo_url)
    {
        SQLiteDatabase db = mDb;
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PHOTO_ID, photo_id);
        contentValues.put(KEY_PHOTO, photo_url);
        LogUtil.log("KEY_PHOTO "+photo_url);
        db.insert(TABLE_PHOTOS, null, contentValues);
        return true;
    }


    public List<String> getImages() {
        //TBD;
        ArrayList<String> result = new ArrayList<String>();
        String query = "SELECT "+KEY_PHOTO+" FROM "+TABLE_PHOTOS;
        Cursor cursor = mDb.rawQuery(query, null);
        LogUtil.log("Cursor size is "+cursor.getCount());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int index = cursor.getColumnIndex(KEY_PHOTO);
            LogUtil.log("INDEX IS "+index);
            result.add(cursor.getString(index));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()){
            cursor.close();;
        }
        LogUtil.log("TOTAL SAVED ITEMS "+result.size());
        return result;
    }


    public boolean getImage(String photo_url) {
        SQLiteDatabase db = mDb;
        Cursor cursor = db.query(TABLE_PHOTOS, new String[]{KEY_PHOTO},  KEY_PHOTO+"=?", new String[]{photo_url}, null, null, null, null);
        LogUtil.log("URL IS "+photo_url + " count is "+cursor.getCount());
        boolean isAvailable = cursor.getCount() > 0;
        cursor.close();;
        return isAvailable;
    }

    public boolean deleteImageWithID(String photo_id) {
        SQLiteDatabase db = mDb;
        db.delete(TABLE_PHOTOS, KEY_PHOTO+"=?", new String[]{photo_id});
        return true;
    }

    public boolean deleteImage(String photoURL) {
        SQLiteDatabase db = mDb;
        db.delete(TABLE_PHOTOS, KEY_PHOTO+"=?", new String[]{photoURL});
        return true;
    }
    /**
     * YOUR CODE HERE
     *
     * For part 1b, you should fill in the various CRUD operations below to manipulate the db
     * returned by getWritableDatabase() to store/load photos.
     */

}
