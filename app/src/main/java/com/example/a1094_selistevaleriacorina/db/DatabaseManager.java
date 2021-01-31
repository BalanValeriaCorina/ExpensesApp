package com.example.a1094_selistevaleriacorina.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.a1094_selistevaleriacorina.Expense;
import com.example.a1094_selistevaleriacorina.dao.ExpenseDao;
import com.example.a1094_selistevaleriacorina.util.TimeConverter;

@Database(exportSchema = false, version = 5, entities = {Expense.class})
@TypeConverters({TimeConverter.class})
public abstract class DatabaseManager extends RoomDatabase {
    private static final String DAM_DB = "dam_db";

    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(context, DatabaseManager.class, DAM_DB)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return databaseManager;
    }
   public  abstract ExpenseDao getExpenseDao();
}
