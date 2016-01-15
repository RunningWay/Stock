package com.buy.stock.db;

import com.buy.stock.model.BaseStock;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * DAO for table NOTE.
 */
public class StockStationDao extends AbstractDao<BaseStock, Long> {
	public static final String TABLENAME = "stock";

	public static class Properties {
		public final static Property stockId = new Property(0, String.class, "_id", true, "_id");
		public final static Property stockName = new Property(1, String.class,"_name", false, "_id");
	};

	public StockStationDao(DaoConfig config) {
		super(config);
	}

	public StockStationDao(DaoConfig config, DaoSession daoSession) {
		super(config, daoSession);
	}

	public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
		String constraint = ifNotExists ? "IF NOT EXISTS " : "";
		db.execSQL("CREATE TABLE " + constraint + "'stock' ("
				+ "'id' INTEGER PRIMARY KEY NOT NULL,"
				+ "'_id' TEXT NOT NULL ,"
				+ "'_name' TEXT);");
	}

	public static void dropTable(SQLiteDatabase db, boolean ifExists) {
		String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'stock'";
		db.execSQL(sql);
	}

	@Override
	protected void bindValues(SQLiteStatement stmt, BaseStock entity) {
		stmt.clearBindings();
		stmt.bindLong(1, entity.id);
		stmt.bindString(2, entity.stockId);
		stmt.bindString(3, entity.stockName);
	}

	@Override
	public Long readKey(Cursor cursor, int offset) {
		return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
	}

	@Override
	public BaseStock readEntity(Cursor cursor, int offset) {
		BaseStock bStock = new BaseStock();
		bStock.id = cursor.getLong(offset+0);
		bStock.stockId = cursor.getString(offset + 1);
		bStock.stockName = cursor.getString(offset + 2);
		return bStock;
	}

	@Override
	public void readEntity(Cursor cursor, BaseStock entity, int offset) {
		entity.id = cursor.getLong(offset + 0);
		entity.stockId = cursor.getString(offset + 1);
		entity.stockName = cursor.getString(offset + 2);
	}

	@Override
	protected Long updateKeyAfterInsert(BaseStock entity, long rowId) {
		entity.id = rowId;
		return rowId;
	}

	@Override
	public Long getKey(BaseStock entity) {
		if (entity != null) {
			return entity.id;
		} else {
			return null;
		}
	}

	@Override
	protected boolean isEntityUpdateable() {
		return true;
	}

}
