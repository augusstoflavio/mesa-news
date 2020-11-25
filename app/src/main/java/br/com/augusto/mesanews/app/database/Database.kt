package br.com.augusto.mesanews.app.database

import io.realm.Realm
import io.realm.RealmConfiguration

class Database {

    companion object {
        fun getInstance(): Realm {
            val config = RealmConfiguration.Builder()
                .name("mesa_news.realm")
                .schemaVersion(1)
                .build()

            return Realm.getInstance(config)
        }
    }
}