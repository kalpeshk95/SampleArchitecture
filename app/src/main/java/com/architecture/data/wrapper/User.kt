package com.architecture.data.wrapper

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User {

    @PrimaryKey
    @ColumnInfo(name = "name")
    var name = ""

    @ColumnInfo(name = "age")
    var age : Int? = null

    @ColumnInfo(name = "salary")
    var salary : Int? = null

    override fun toString(): String {
        return "User(name='$name', age=$age, salary=$salary)"
    }
}