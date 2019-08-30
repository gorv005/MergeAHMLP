package com.dartmic.mergeahmlp.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dartmic.mergeahmlp.Constants.MechListBean;
import com.dartmic.mergeahmlp.room.entity.MechData;

import java.util.List;

@Dao
public interface  MechDataDao {
    @Insert
    public void insert(MechData... mech);

    @Update
    public void update(MechData... mech);

    @Query("DELETE FROM mechdata")
    public void delete();

    @Query("SELECT * FROM mechdata")
    public List<MechData> getMech();

    @Query("SELECT m_id,m_name,m_point,m_remark FROM mechdata")
    public List<MechData> getMechForReport();

    @Query("SELECT * FROM mechdata WHERE m_point<:points AND m_remark=:remarks")
    public List<MechData> getMechDataWithLessThenRequiredPoints(int points,String remarks);

    @Query("SELECT * FROM mechdata WHERE m_id = :id")
    public MechData getMechWithId(String id);

    @Query("UPDATE mechdata SET m_point = :points AND total_point=:total_points WHERE m_id = :mid")
    int updateMechPoints(String mid, String total_points,int points);

    @Query("UPDATE mechdata SET m_remark = :remarks WHERE m_id = :mid")
    int updateMechRemarks(String mid, String remarks);
}
