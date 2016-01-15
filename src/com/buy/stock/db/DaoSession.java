package com.buy.stock.db;

import android.database.sqlite.SQLiteDatabase;
import java.util.Map;
import com.buy.stock.model.BaseStock;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

public class DaoSession extends AbstractDaoSession {

	private final DaoConfig stockDaoConfig;
	private final StockStationDao stockStationDao;

	public DaoSession(SQLiteDatabase db, IdentityScopeType type,
			Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> daoConfigMap) {
		super(db);
		stockDaoConfig = daoConfigMap.get(StockStationDao.class).clone();
		stockDaoConfig.initIdentityScope(type);

		stockStationDao = new StockStationDao(stockDaoConfig, this);

		registerDao(BaseStock.class, stockStationDao);
	}

	public void clear() {
		stockDaoConfig.getIdentityScope().clear();
	}

	public StockStationDao getStockStationDao() {
		return stockStationDao;
	}
}
