package com.tritonsfs.cac.sso.dao;

import com.tritonsfs.cac.frame.dao.MapperProvider;
import com.tritonsfs.cac.sso.model.CacOperateLog;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CacOperateLogMapper {
    @Select("select * from cac_operate_log where id = #{id} ")
    CacOperateLog queryById(@Param("id") Long id);

    @SelectProvider(type = MapperProvider.class, method = "queryByEntity")
    List<CacOperateLog> queryByEntity(CacOperateLog cacOperateLog);

    @SelectProvider(type = MapperProvider.class, method = "queryByEntityOrder")
    List<CacOperateLog> queryByEntityOrder(CacOperateLog cacOperateLog, String... order);

    @InsertProvider(type = MapperProvider.class, method = "insertEntity")
    int insert(CacOperateLog cacOperateLog);

    @InsertProvider(type = MapperProvider.class, method = "insertBatch")
    int insertBatch(@Param("list") List<CacOperateLog> list);

    @UpdateProvider(type = MapperProvider.class, method = "updateEntity")
    int update(CacOperateLog cacOperateLog);

    @DeleteProvider(type = MapperProvider.class, method = "deleteEntity")
    int delete(CacOperateLog cacOperateLog);
}