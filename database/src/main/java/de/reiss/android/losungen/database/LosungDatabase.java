package de.reiss.android.losungen.database;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

@Database(entities = {
        LanguageItem.class,
        DailyLosungDatabaseItem.class,
        WeeklyLosungDatabaseItem.class,
        MonthlyLosungDatabaseItem.class,
        YearlyLosungDatabaseItem.class,
        NoteItem.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class LosungDatabase extends RoomDatabase {

    public static LosungDatabase create(Application application) {

        // Accidentally released a version with the german Losung, but license for them is missing.
        // Therefore just delete all items in the table and the app should import valid content again
        // from the raw folder
        Migration deleteGermanLosungMigration = new Migration(1, 2) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
                database.execSQL("DELETE FROM `DailyLosungDatabaseItem`");
                database.execSQL("DELETE FROM `LanguageItem` WHERE languageCode = 'de'");
            }
        };

        return Room.databaseBuilder(application, LosungDatabase.class, "LosungDatabase.db")
                .allowMainThreadQueries() // for widgets
                .addMigrations(deleteGermanLosungMigration)
                .build();
    }

    public abstract DailyLosungItemDao dailyLosungItemDao();

    public abstract WeeklyLosungItemDao weeklyLosungItemDao();

    public abstract MonthlyLosungItemDao monthlyLosungItemDao();

    public abstract YearlyLosungItemDao yearlyLosungItemDao();

    public abstract LanguageItemDao languageItemDao();

    public abstract NoteItemDao noteItemDao();

}