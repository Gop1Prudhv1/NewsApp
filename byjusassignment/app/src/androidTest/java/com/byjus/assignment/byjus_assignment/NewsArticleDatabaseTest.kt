package com.byjus.assignment.byjus_assignment

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.byjus.assignment.byjusAssignment.database.NewsArticle
import com.byjus.assignment.byjusAssignment.database.NewsArticlesDao
import com.byjus.assignment.byjusAssignment.database.NewsArticlesDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class NewsArticleDatabaseTest {
    private lateinit var newsDao: NewsArticlesDao
    private lateinit var db: NewsArticlesDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, NewsArticlesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        newsDao = db.newsArticlesDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertArticle() {
        val article = NewsArticle()
        newsDao.insertArticle(article)

    }
}