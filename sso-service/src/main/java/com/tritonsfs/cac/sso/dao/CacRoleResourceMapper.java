package com.tritonsfs.cac.sso.dao;

import com.tritonsfs.cac.frame.dao.MapperProvider;
import com.tritonsfs.cac.sso.model.CacRoleResource;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
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
public interface CacRoleResourceMapper {
    @Select("select * from cac_role_resource where id = #{id} ")
    CacRoleResource queryById(@Param("id") Long id);

    @SelectProvider(type = MapperProvider.class, method = "queryByEntity")
    List<CacRoleResource> queryByEntity(CacRoleResource cacRoleResource);

    @SelectProvider(type = MapperProvider.class, method = "queryByEntityOrder")
    List<CacRoleResource> queryByEntityOrder(CacRoleResource cacRoleResource, String... order);

    @InsertProvider(type = MapperProvider.class, method = "insertEntity")
    int insert(CacRoleResource cacRoleResource);

    @InsertProvider(type = MapperProvider.class, method = "insertBatch")
    int insertBatch(@Param("list") List<CacRoleResource> list);

    @UpdateProvider(type = MapperProvider.class, method = "updateEntity")
    int update(CacRoleResource cacRoleResource);

    @DeleteProvider(type = MapperProvider.class, method = "deleteEntity")
    int delete(CacRoleResource cacRoleResource);
    
    @Delete("DELETE FROM cac_role_resource WHERE role_id=#{roleId}")
    int deleteByRoleId(@Param("roleId") long roleId);
}