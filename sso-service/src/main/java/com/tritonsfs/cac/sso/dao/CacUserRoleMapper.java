package com.tritonsfs.cac.sso.dao;

import com.tritonsfs.cac.frame.dao.MapperProvider;
import com.tritonsfs.cac.sso.model.CacUserRole;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CacUserRoleMapper {
    @Select("select * from cac_user_role where id = #{id} ")
    CacUserRole queryById(@Param("id") Long id);

    @SelectProvider(type = MapperProvider.class, method = "queryByEntity")
    List<CacUserRole> queryByEntity(CacUserRole cacUserRole);

    @SelectProvider(type = MapperProvider.class, method = "queryByEntityOrder")
    List<CacUserRole> queryByEntityOrder(CacUserRole cacUserRole, String... order);

    @InsertProvider(type = MapperProvider.class, method = "insertEntity")
    int insert(CacUserRole cacUserRole);

    @InsertProvider(type = MapperProvider.class, method = "insertBatch")
    int insertBatch(@Param("list") List<CacUserRole> list);

    @UpdateProvider(type = MapperProvider.class, method = "updateEntity")
    int update(CacUserRole cacUserRole);

    @DeleteProvider(type = MapperProvider.class, method = "deleteEntity")
    int delete(CacUserRole cacUserRole);

    @Delete("delete a.* from cac_user_role a LEFT join cac_role b ON a.role_id = b.id " +
            "where a.user_id = #{userId} and b.system_id = #{systemId}")
    Integer deleteBySys(@Param("userId") Long userId,@Param("systemId") Long systemId);
}