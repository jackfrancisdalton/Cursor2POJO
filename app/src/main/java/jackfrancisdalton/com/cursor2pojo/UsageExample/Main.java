package jackfrancisdalton.com.cursor2pojo.UsageExample;

import android.content.Context;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import jackfrancisdalton.com.cursor2pojo.C2PMapper;
import jackfrancisdalton.com.cursor2pojo.ResultDescriber;

/**
 * Created by Jack F. Dalton on 0015 15 07 2017.
 */

public class Main {
    private static void main(String[] args) {
        SQLiteDatabase db = createAndPopulateTestTable();
        Cursor cursor = db.rawQuery("SELECT * FROM hello_pojo", null);
        HelloPOJO obj = (HelloPOJO) C2PMapper.execute(cursor, new ResultDescriber(ResultDescriber.StructureType.object, HelloPOJO.class));
    }

    private static SQLiteDatabase createAndPopulateTestTable() {
        try {
            SQLiteDatabase myDB = SQLiteDatabase.openOrCreateDatabase("hellodata.db", null);
            myDB.execSQL("CREATE TABLE IF NOT EXISTS hello_pojo (name_column TEXT, value_column INTEGER, predicate_value INTEGER);");
            myDB.execSQL("INSERT INTO hello_pojo (name_column, value_column, predicate_value) VALUES ('John Doe', 22, 1);");
            return myDB;
        }catch (Exception e) {
            throw e;
        }
    }
}


