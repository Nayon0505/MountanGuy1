package com.example.mountanguy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static DataBaseHelper instance;
    private SQLiteDatabase db;
    protected static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "USER_RECORD";
    public static final String TABLE_NAME = "USER_DATA";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "EMAIL";
    public static final String COL_4 = "PASSWORD";
    public static final String COL_5 = "USERGESCHLECHT";
    public static final String COL_6 = "USERALTER";
    public static final String COL_7 = "USERGRÖßE";
    public static final String COL_8 = "USERGEWICHT";
    public static final String COL_9 = "USERNIVEAU";
    public static final String COL_10 = "USER_ZIEL_GEWICHT";
    public static final String COL_11 = "USER_ZIEL_PROTEIN";
    public static final String FOOD_TABLE_NAME = "FOOD_DATA";
    public static final String FOOD_COL_1 = "ID";
    public static final String FOOD_COL_2 = "NAME";
    public static final String FOOD_COL_3 = "KALORIEN";
    public static final String FOOD_COL_4 = "EIWEIßE";
    public static final String FOOD_COL_5 = "KOHLENHYDRATE";
    public static final String FOOD_COL_6 = "FETTE";
    public static final String FOOD_COL_7 = "GEWICHT";
    public static final String DIARY_TABLE_NAME = "TAGEBUCH";
    public static final String DIARY_COL_1 = "ID";
    public static final String DIARY_COL_2 = "FOOD_NAME";
    public static final String DIARY_COL_3 = "FOOD_AMOUNT";


    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    //synchronisiert die Datenbank über die Verschiedenen Activities
    public static synchronized DataBaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseHelper(context.getApplicationContext());
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
        insertInitialFoodItems(db);
    }

    public void insertInitialFoodItemsIfNeeded() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + FOOD_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() == 0) {
            insertInitialFoodItems(db);
        }
        cursor.close();
    }

    private void insertInitialFoodItems(SQLiteDatabase db) {
        insertFoodItem(db, "Apfel", 52.0, 0.3, 14.0, 0.2, 100);
        insertFoodItem(db, "Banane", 89.0, 1.1, 23.0, 0.3, 100);
        insertFoodItem(db, "Ei", 155.0, 12.6, 1.1, 10.6, 100);
        insertFoodItem(db, "Milch (Vollmilch)", 64.0, 3.3, 4.7, 3.6, 100);
        insertFoodItem(db, "Joghurt (Natur)", 59.0, 3.6, 4.7, 3.3, 100);
        insertFoodItem(db, "Hähnchenbrust", 165.0, 31.0, 0.0, 3.6, 100);
        insertFoodItem(db, "Rindfleisch (Rumpsteak)", 250.0, 26.0, 0.0, 15.0, 100);
        insertFoodItem(db, "Lachs", 208.0, 20.4, 0.0, 13.6, 100);
        insertFoodItem(db, "Reis (gekocht)", 130.0, 2.7, 28.0, 0.3, 100);
        insertFoodItem(db, "Nudeln (gekocht)", 158.0, 5.8, 31.0, 1.0, 100);
        insertFoodItem(db, "Spinat (gekocht)", 23.0, 2.9, 3.6, 0.4, 100);
        insertFoodItem(db, "Tomate", 18.0, 0.9, 3.9, 0.2, 100);
        insertFoodItem(db, "Gurke", 15.0, 0.6, 3.1, 0.1, 100);
        insertFoodItem(db, "Karotte", 41.0, 0.9, 9.6, 0.2, 100);
        insertFoodItem(db, "Kartoffel (gekocht)", 87.0, 2.0, 20.0, 0.1, 100);
        insertFoodItem(db, "Haferflocken", 389.0, 13.0, 66.0, 7.0, 100);
        insertFoodItem(db, "Müsli (ungesüßt)", 368.0, 13.0, 60.0, 8.0, 100);
        insertFoodItem(db, "Brot (Vollkorn)", 247.0, 9.4, 48.0, 1.9, 100);
        insertFoodItem(db, "Quark (Magerstufe)", 55.0, 11.2, 3.9, 0.3, 100);
        insertFoodItem(db, "Schokolade (Vollmilch)", 535.0, 5.4, 57.5, 30.0, 100);
        insertFoodItem(db, "Kekse", 483.0, 6.3, 70.0, 20.0, 100);
        insertFoodItem(db, "Nüsse (gemischt)", 607.0, 14.0, 19.0, 53.0, 100);
        insertFoodItem(db, "Honig", 304.0, 0.3, 82.0, 0.0, 100);
        insertFoodItem(db, "Olivenöl", 884.0, 0.0, 0.0, 100.0, 100);
        insertFoodItem(db, "Avocado", 160.0, 2.0, 9.0, 15.0, 100);
        insertFoodItem(db, "Zucker (weiß)", 387.0, 0.0, 100.0, 0.0, 100);
        insertFoodItem(db, "Salz", 0.0, 0.0, 0.0, 0.0, 100);
        insertFoodItem(db, "Pfeffer", 251.0, 10.4, 51.0, 3.3, 100);
        insertFoodItem(db, "Ingwer", 80.0, 1.8, 17.8, 0.8, 100);
        insertFoodItem(db, "Knoblauch", 149.0, 6.4, 33.1, 0.5, 100);
    }


    private void insertFoodItem(SQLiteDatabase db, String name, double kalorien, double eiweiße, double kohlenhydrate, double fette, double gewicht) {
        ContentValues values = new ContentValues();
        values.put(FOOD_COL_2, name);
        values.put(FOOD_COL_3, kalorien);
        values.put(FOOD_COL_4, eiweiße);
        values.put(FOOD_COL_5, kohlenhydrate);
        values.put(FOOD_COL_6, fette); // Fette
        values.put(FOOD_COL_7, gewicht); // Gewicht
        db.insert(FOOD_TABLE_NAME, null, values);
    }

    public void resetDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DIARY_TABLE_NAME);
        createTable(db);
    }

    public void resetDiary() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DIARY_TABLE_NAME, null, null);
    }


    private void createTable(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2 + " TEXT, "
                + COL_3 + " TEXT, "
                + COL_4 + " TEXT, "
                + COL_5 + " TEXT, "
                + COL_6 + " TEXT, "
                + COL_7 + " TEXT, "
                + COL_8 + " TEXT, "
                + COL_9 + " TEXT, "
                + COL_10 + " TEXT,"
                + COL_11 + " TEXT)";
        db.execSQL(createTableQuery);

        String createFoodTableQuery = "CREATE TABLE " + FOOD_TABLE_NAME + " ("
                + FOOD_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FOOD_COL_2 + " TEXT, "
                + FOOD_COL_3 + " REAL, "
                + FOOD_COL_4 + " REAL, "
                + FOOD_COL_5 + " REAL, "
                + FOOD_COL_6 + " REAL, "
                + FOOD_COL_7 + " REAL)";
        db.execSQL(createFoodTableQuery);

        String createDiaryTableQuery = "CREATE TABLE " + DIARY_TABLE_NAME + " ("
                + DIARY_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DIARY_COL_2 + " TEXT, "
                + DIARY_COL_3 + " REAL)";
        db.execSQL(createDiaryTableQuery);
    }

    public boolean addFood(String name, double kalorien, double eiweiße, double kohlenhydrate, double fette, double gewicht) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FOOD_COL_2, name);
        values.put(FOOD_COL_3, kalorien);
        values.put(FOOD_COL_4, eiweiße);
        values.put(FOOD_COL_5, kohlenhydrate);
        values.put(FOOD_COL_6, fette); // Fette
        values.put(FOOD_COL_7, gewicht); // Gewicht
        long result = db.insert(FOOD_TABLE_NAME, null, values);
        return result != -1;
    }

    // Methode zum Abrufen aller Lebensmittel
    public Cursor getAllFood() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + FOOD_TABLE_NAME, null);
    }
    public boolean addDiaryEntry(String foodName, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DIARY_COL_2, foodName);
        values.put(DIARY_COL_3, amount);
        long result = db.insert(DIARY_TABLE_NAME, null, values);
        return result != -1;
    }


    public List<DiaryEntry> getDiaryEntries() {
        List<DiaryEntry> diaryEntries = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT f.NAME AS FOOD_NAME, d.FOOD_AMOUNT, f.ID, f.KALORIEN, f.EIWEIßE, f.KOHLENHYDRATE, f.FETTE " +
                "FROM " + DIARY_TABLE_NAME + " d " +
                "INNER JOIN " + FOOD_TABLE_NAME + " f ON d.FOOD_NAME = f.NAME", null);
        if (cursor.moveToFirst()) {
            do {
                String foodName = cursor.getString(cursor.getColumnIndex("FOOD_NAME"));
                double foodAmount = cursor.getDouble(cursor.getColumnIndex("FOOD_AMOUNT"));
                int foodId = cursor.getInt(cursor.getColumnIndex("ID"));
                double kalorien = cursor.getDouble(cursor.getColumnIndex("KALORIEN"));
                double eiweiße = cursor.getDouble(cursor.getColumnIndex("EIWEIßE"));
                double kohlenhydrate = cursor.getDouble(cursor.getColumnIndex("KOHLENHYDRATE"));
                double fette = cursor.getDouble(cursor.getColumnIndex("FETTE"));
                diaryEntries.add(new DiaryEntry(foodName, foodAmount, foodId, kalorien, eiweiße, kohlenhydrate, fette));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return diaryEntries;
    }



    public class DiaryEntry {
        private String foodName;
        private double foodAmount;
        private int foodId;
        private double kalorien;
        private double eiweiße;
        private double kohlenhydrate;
        private double fette;

        public DiaryEntry(String foodName, double foodAmount, int foodId, double kalorien, double eiweiße, double kohlenhydrate, double fette) {
            this.foodName = foodName;
            this.foodAmount = foodAmount;
            this.foodId = foodId;
            this.kalorien = kalorien;
            this.eiweiße = eiweiße;
            this.kohlenhydrate = kohlenhydrate;
            this.fette = fette;
        }

        public String getFoodName() {
            return foodName;
        }

        public double getFoodAmount() {
            return foodAmount;
        }

        public double getTotalKalorien() {
            return foodAmount * kalorien;
        }

        public double getTotalEiweiße() {
            return foodAmount * eiweiße;
        }

        public double getTotalKohlenhydrate() {
            return foodAmount * kohlenhydrate;
        }

        public double getTotalFette() {
            return foodAmount * fette;
        }
    }







    //Checkt ob Datenbank existiert
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }



    public boolean registerUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, username);
        values.put(COL_3, email);
        values.put(COL_4, password);
        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateUserInfo(String username, String userGeschlecht, String userAlter, String userGröße, String userGewicht, String userNiveau, String userZielGewicht, String userZielProtein) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_5, userGeschlecht);
        values.put(COL_6, userAlter);
        values.put(COL_7, userGröße);
        values.put(COL_8, userGewicht);
        values.put(COL_9, userNiveau);
        values.put(COL_10, userZielGewicht);
        values.put(COL_11, userZielProtein);

        int rowsAffected = db.update(TABLE_NAME, values, COL_2 + " = ?", new String[]{username});

        Log.d("UpdateUserInfo", "Username: " + username);
        Log.d("UpdateUserInfo", "Rows affected: " + rowsAffected);

        return rowsAffected>0;

    }

    public boolean checkusername(String USERNAME){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from USER_DATA where USERNAME = ?", new String []{USERNAME});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public boolean checkuseremail(String USERNAME){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from USER_DATA where EMail = ?", new String []{USERNAME});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkusernamepassword(String USERNAME, String PASSWORD){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from USER_DATA where USERNAME = ? and PASSWORD = ?", new String[] {USERNAME,PASSWORD});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Cursor getUserData(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_2 + " = ?", new String[]{username});
    }


    public void insertIntoDatabase(String userID, String col, Object value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (value instanceof String) {
            contentValues.put(col, (String) value);
        } else if (value instanceof Integer) {
            contentValues.put(col, (Integer) value);
        } else {
            throw new IllegalArgumentException("Dieser Datentyp wird nicht unterstützt");
        }

        // Beispiel: Aktualisieren der Zeile mit der gegebenen userID
        int rowsAffected = db.update(TABLE_NAME, contentValues, COL_1 + " = ?", new String[]{userID});

        if (rowsAffected > 0) {
            Log.d("DataBaseHelper", "Daten erfolgreich aktualisiert für UserID: " + userID);
        } else {
            Log.d("DataBaseHelper", "Fehler beim Aktualisieren der Daten für UserID: " + userID);


        }
    }

    public Cursor searchFood(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT * FROM " + FOOD_TABLE_NAME + " WHERE " + FOOD_COL_2 + " LIKE ?";
        return db.rawQuery(sqlQuery, new String[]{"%" + query + "%"});
    }


    public SQLiteDatabase openDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("TAG", "Database opened");
        return db;
    }

    public void closeDatabase() {
        this.close();
        Log.d("TAG", "Database closed");
    }
}