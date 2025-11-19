package com.example.first_sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // קבועים עבור מסד הנתונים והטבלה
    public static final String DATABASE_NAME = "Users.db";
    public static final int DATABASE_VERSION = 1;

    // קבועים עבור טבלת המשתמשים
    public static final String TABLE_USERS = "users_table";
    public static final String COL_ID = "ID"; // מפתח ראשי עם מספור אוטומטי
    public static final String COL_USERNAME = "USERNAME";
    public static final String COL_PASSWORD = "PASSWORD";
    public static final String COL_EMAIL = "EMAIL";

    // שאילתת SQL ליצירת הטבלה
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_USERNAME + " TEXT, " +
                    COL_PASSWORD + " TEXT, " +
                    COL_EMAIL + " TEXT)";

    /**
     * פעולה בונה
     */
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * הפעולה נקראת כאשר מסד הנתונים נוצר בפעם הראשונה.
     * כאן אנו יוצרים את הטבלאות שלנו.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    /**
     * הפעולה נקראת כאשר משדרגים את גרסת מסד הנתונים.
     * לדוגמה, אם נשנה את מבנה הטבלאות בגרסה חדשה של האפליקציה.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // במקרה שלנו, נמחק את הטבלה הישנה ונבנה מחדש.
        // באפליקציה אמיתית, יש לבצע מיגרציה של הנתונים.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    /**
     * פעולה להוספת משתמש חדש למסד הנתונים.
     * @return true אם ההוספה הצליחה, false אחרת.
     */
    public boolean addUser(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // מכניסים את הערכים לתוך обект ContentValues
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_PASSWORD, password);
        contentValues.put(COL_EMAIL, email);

        // הפעולה insert מחזירה -1 אם הייתה שגיאה.
        long result = db.insert(TABLE_USERS, null, contentValues);

        // אם התוצאה שונה מ-1, זה אומר שההוספה הצליחה.
        return result != -1;
    }
}
