package com.example.taskbuddy;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class DAO_Impl implements DAO {
    private final RoomDatabase __db;

    private final EntityInsertionAdapter<Entity> __insertionAdapterOfEntity;

    private final EntityDeletionOrUpdateAdapter<Entity> __deletionAdapterOfEntity;

    private final EntityDeletionOrUpdateAdapter<Entity> __updateAdapterOfEntity;

    private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

    public DAO_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfEntity = new EntityInsertionAdapter<Entity>(__db) {
            @Override
            public String createQuery() {
                return "INSERT OR ABORT INTO `To_Do` (`id`,`title`,`priority`) VALUES (nullif(?, 0),?,?)";
            }

            @Override
            public void bind(SupportSQLiteStatement stmt, Entity value) {
                stmt.bindLong(1, value.getId());
                if (value.getTitle() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getTitle());
                }
                if (value.getPriority() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getPriority());
                }
            }
        };
        this.__deletionAdapterOfEntity = new EntityDeletionOrUpdateAdapter<Entity>(__db) {
            @Override
            public String createQuery() {
                return "DELETE FROM `To_Do` WHERE `id` = ?";
            }

            @Override
            public void bind(SupportSQLiteStatement stmt, Entity value) {
                stmt.bindLong(1, value.getId());
            }
        };
        this.__updateAdapterOfEntity = new EntityDeletionOrUpdateAdapter<Entity>(__db) {
            @Override
            public String createQuery() {
                return "UPDATE OR ABORT `To_Do` SET `id` = ?,`title` = ?,`priority` = ? WHERE `id` = ?";
            }

            @Override
            public void bind(SupportSQLiteStatement stmt, Entity value) {
                stmt.bindLong(1, value.getId());
                if (value.getTitle() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getTitle());
                }
                if (value.getPriority() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getPriority());
                }
                stmt.bindLong(4, value.getId());
            }
        };
        this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
            @Override
            public String createQuery() {
                final String _query = "Delete from to_do";
                return _query;
            }
        };
    }

    @Override
    public Object insertTask(final Entity entity, final Continuation<? super Unit> p1) {
        return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
            @Override
            public Unit call() throws Exception {
                __db.beginTransaction();
                try {
                    __insertionAdapterOfEntity.insert(entity);
                    __db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    __db.endTransaction();
                }
            }
        }, p1);
    }

    @Override
    public Object deleteTask(final Entity entity, final Continuation<? super Unit> p1) {
        return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
            @Override
            public Unit call() throws Exception {
                __db.beginTransaction();
                try {
                    __deletionAdapterOfEntity.handle(entity);
                    __db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    __db.endTransaction();
                }
            }
        }, p1);
    }

    @Override
    public Object updateTask(final Entity entity, final Continuation<? super Unit> p1) {
        return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
            @Override
            public Unit call() throws Exception {
                __db.beginTransaction();
                try {
                    __updateAdapterOfEntity.handle(entity);
                    __db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    __db.endTransaction();
                }
            }
        }, p1);
    }

    @Override
    public Object deleteAll(final Continuation<? super Unit> p0) {
        return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
            @Override
            public Unit call() throws Exception {
                final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
                __db.beginTransaction();
                try {
                    _stmt.executeUpdateDelete();
                    __db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    __db.endTransaction();
                    __preparedStmtOfDeleteAll.release(_stmt);
                }
            }
        }, p0);
    }

    @Override
    public Object getTasks(final Continuation<? super List<CardInfo>> p0) {
        final String _sql = "Select * from to_do";
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
        final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CardInfo>>() {
            @Override
            public List<CardInfo> call() throws Exception {
                final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
                try {
                    final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
                    final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
                    final List<CardInfo> _result = new ArrayList<CardInfo>(_cursor.getCount());
                    while(_cursor.moveToNext()) {
                        final CardInfo _item;
                        final String _tmpTitle;
                        if (_cursor.isNull(_cursorIndexOfTitle)) {
                            _tmpTitle = null;
                        } else {
                            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
                        }
                        final String _tmpPriority;
                        if (_cursor.isNull(_cursorIndexOfPriority)) {
                            _tmpPriority = null;
                        } else {
                            _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
                        }
                        _item = new CardInfo(_tmpTitle,_tmpPriority);
                        _result.add(_item);
                    }
                    return _result;
                } finally {
                    _cursor.close();
                    _statement.release();
                }
            }
        }, p0);
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}
