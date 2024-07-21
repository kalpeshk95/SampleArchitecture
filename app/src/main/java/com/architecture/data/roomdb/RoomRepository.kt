package com.architecture.data.roomdb

import androidx.lifecycle.LiveData
import com.architecture.utils.Constant
import com.architecture.wrapper.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class RoomRepository(private val dao: LocalDao) : RoomManager {

    var disposable: Disposable? = null

    override fun getAll(): LiveData<List<User>> {
        return dao.all
    }

    override suspend fun insert(callback: RoomManager.CallbackManager, user: User) {
        disposable = Completable.fromCallable { dao.insertAll(user) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onSetMessage("User added successfully")
            }, {
                callback.onSetMessage(it.message!!)
                Timber.i(Constant.TAG, "insert ex : ${it.message}")
            })
    }

    override suspend fun update(callback: RoomManager.CallbackManager, user: User) {

        Timber.i(Constant.TAG, "User : $user")
        disposable = Completable.fromCallable { dao.update(user) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onSetMessage("User updated successfully")
            }, {
                callback.onSetMessage(it.message!!)
                Timber.i(Constant.TAG, "update ex : ${it.message}")
            })
    }

    override suspend fun delete(callback: RoomManager.CallbackManager, user: User) {
        disposable = Completable.fromCallable { dao.delete(user) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onSetMessage("User deleted successfully")
            }, {
                callback.onSetMessage("Something went wrong...! ${it.message}")
                Timber.i(Constant.TAG, "delete ex : ${it.message}")
            })
    }

    override suspend fun deleteAll(callback: RoomManager.CallbackManager) {
        disposable = Completable.fromCallable { dao.deleteAll() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onSetMessage("Records deleted successfully")
            }, {
                callback.onSetMessage("Something went wrong...! ${it.message}")
                Timber.i(Constant.TAG, "delete all ex : ${it.message}")
            })
    }
}