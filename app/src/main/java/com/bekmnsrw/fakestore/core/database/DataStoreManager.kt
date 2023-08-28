package com.bekmnsrw.fakestore.core.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(
    private val context: Context
) {

    companion object {
        private const val DATASTORE_NAME = "DATASTORE"
        const val SHOULD_SHOW_RATIONALE_KEY = "SHOULD_SHOW_RATIONALE"

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)
        private val SHOULD_SHOW_KEY = booleanPreferencesKey(name = SHOULD_SHOW_RATIONALE_KEY)
    }

    val getShouldShow: Flow<Boolean?> = context.dataStore.data.map { it[SHOULD_SHOW_KEY] }

    suspend fun saveShouldShow(
        shouldShow: Boolean
    ) = context.dataStore.edit { it[SHOULD_SHOW_KEY] = shouldShow }

    fun isKeyStored(
        key: Preferences.Key<Boolean>
    ): Flow<Boolean> = context.dataStore.data.map { it.contains(key) }
}
