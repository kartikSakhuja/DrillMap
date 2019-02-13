package environment.supersonic.com.drillmap.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import environment.supersonic.com.drillmap.model.Distance;
import environment.supersonic.com.drillmap.model.DistanceDao;

@Database(entities = {Distance.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DistanceDao distanceDao();
}
