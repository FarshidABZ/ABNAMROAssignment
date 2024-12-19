package com.abnamro.core.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abnamro.core.data.database.AppDatabase
import com.abnamro.core.data.database.RepoDao
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import com.abnamro.core.data.MockTestData.givenRepoEntity
import com.abnamro.core.data.MockTestData.givenRepoEntityList
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var repoDao: RepoDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        repoDao = database.repoDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertRepo_getById_verifyTheRepo() = runTest {
        val item = givenRepoEntity()

        repoDao.upsertAll(listOf(item))
        val result = repoDao.getRepoById(item.id)

        assertThat(result, `is`(notNullValue()))
        assertThat(result.id, `is`(item.id))
    }

    @Test
    fun insertRepo_getAll_verifyOnlyOneAdded() = runTest {
        val item = givenRepoEntity()

        repoDao.upsertAll(listOf(item))
        val result = repoDao.getAllRepos()

        assertThat(result, `is`(notNullValue()))
        assertThat(result?.size, `is`(1))
    }

    @Test
    fun insertRepos_getAll_verifyAllAddedAndExist() = runTest {
        val repos = givenRepoEntityList()

        repoDao.upsertAll(repos)
        val result = repoDao.getAllRepos()

        assertThat(result, `is`(notNullValue()))
        assertEquals(result!!.size, 2)
        assertEquals(repos[0], result[0])
        assertEquals(repos[1], result[1])
    }

    @Test
    fun clearRepos_verifyClearsAllData() = runTest {
        val repos = givenRepoEntityList()
        repoDao.upsertAll(repos)

        repoDao.clearRepos()
        val result = repoDao.getAllRepos()

        assertTrue(result!!.isEmpty())
    }
}