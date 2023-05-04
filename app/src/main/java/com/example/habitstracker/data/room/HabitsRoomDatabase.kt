package com.example.habitstracker.data.room

import android.content.Context
import android.graphics.Color
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Database(entities = [HabitSQLEntity::class], version = 4)
abstract class HabitsRoomDatabase : RoomDatabase() {

    abstract fun habitsDAO(): HabitsDAO

    companion object {
        @Volatile
        private var INSTANCE: HabitsRoomDatabase? = null

        private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

        fun getDatabase(
            context: Context
        ): HabitsRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
//                    context.applicationContext,
                    context,
                    HabitsRoomDatabase::class.java,
                    "habits_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    .fallbackToDestructiveMigration()
                    .addCallback(HabitsDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class HabitsDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
//                devDoSomeStuff {
//                    INSTANCE?.let { database ->
//                        scope.launch(Dispatchers.IO) {
//                            populateDatabase(database.habitsDAO())
//                        }
//                    }
//                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(habitsDAO: HabitsDAO) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            habitsDAO.deleteAll()
            habitsDAO.insert(
                HabitSQLEntity(
                    "Читать новости",
                    "Читать свежие новости",
                    2,
                    false,
                    1,
                    "Раз в день",
                    Color.GRAY
                )
            )
            habitsDAO.insert(
                HabitSQLEntity(

                    "Физические упражнения",
                    "Заниматься физической нагрузкой в течении часа",
                    0,
                    true,
                    1,
                    "Раз в неделю",
                    Color.RED
                )
            )
            habitsDAO.insert(
                HabitSQLEntity(
                    "Магазин",
                    "Ходить за продуктами",
                    3,
                    true,
                    1,
                    "Раз в неделю",
                    Color.CYAN
                )
            )
            habitsDAO.insert(
                HabitSQLEntity(
                    "Уборка",
                    "Прибраться в комнате",
                    1,
                    true,
                    1,
                    "Раз в неделю",
                    Color.MAGENTA
                )
            )
            habitsDAO.insert(
                HabitSQLEntity(
                    "Ходить в бар",
                    "Ходить с друзьями в бар",
                    3,
                    false,
                    2,
                    "Раз в месяц",
                    Color.YELLOW
                )
            )
        }
    }
}