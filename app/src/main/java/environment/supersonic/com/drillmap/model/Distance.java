package environment.supersonic.com.drillmap.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Distance implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "distance")
    private Double distance;

    /*
    * Getters and Setters
    * */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
