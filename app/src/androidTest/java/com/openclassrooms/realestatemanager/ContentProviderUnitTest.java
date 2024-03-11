package com.openclassrooms.realestatemanager;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.repository.EstateDatabase;
import com.openclassrooms.realestatemanager.utils.ContentProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
@RunWith(AndroidJUnit4.class)
public class ContentProviderUnitTest {

    private ContentResolver mContentResolver;

    @Before
    public void setUp() {
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), EstateDatabase.class).allowMainThreadQueries().build();
        mContentResolver = InstrumentationRegistry.getInstrumentation().getContext().getContentResolver();
    }

    @Test
    public void getEstates() {
        Uri uri = ContentProvider.URI_ESTATE;
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(uri, 1), null, null, null, null);
        assertThat(cursor, notNullValue());
        cursor.close();

    }

}
