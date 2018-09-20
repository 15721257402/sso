package com.tritonsfs.cac.sso.dao;

import com.tritonsfs.cac.frame.dao.MapperProvider;
import com.tritonsfs.cac.sso.model.CacSystem;
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
public interface CacSystemMapper {
    @Select("select * from cac_system where id = #{id} ")
    CacSystem queryById(@Param("id") Long id);

    @SelectProvider(type = MapperProvider.class, method = "queryByEntity")
    List<CacSystem> queryByEntity(CacSystem cacSystem);

    @SelectProvider(type = MapperProvider.class, method = "queryByEntityOrder")
    List<CacSystem> queryByEntityOrder(CacSystem cacSystem, String... order);

    @InsertProvider(type = MapperProvider.class, method = "insertEntity")
    int insert(CacSystem cacSystem);

    @InsertProvider(type = MapperProvider.class, method = "insertBatch")
    int insertBatch(@Param("list") List<CacSystem> list);

    @UpdateProvider(type = MapperProvider.class, method = "updateEntity")
    int update(CacSystem cacSystem);

    @DeleteProvider(type = MapperProvider.class, method = "deleteEntity")
    int delete(CacSystem cacSystem);

    @Select("select id,short_name as shortName, name, url from cac_system where status='00'")
    List<CacSystem> getAllSys();
}