package com.tritonsfs.cac.sso.dao;

import com.tritonsfs.cac.frame.dao.MapperProvider;
import com.tritonsfs.cac.sso.model.*;

import java.util.List;

import com.tritonsfs.cac.sso.remote.model.CacRedisMenu;
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
public interface CacUserMapper {
    @Select("select * from cac_user where id = #{id} ")
    CacUser queryById(@Param("id") Long id);

    @SelectProvider(type = MapperProvider.class, method = "queryByEntity")
    List<CacUser> queryByEntity(CacUser cacUser);

    @SelectProvider(type = MapperProvider.class, method = "queryByEntityOrder")
    List<CacUser> queryByEntityOrder(CacUser cacUser, String... order);

    @InsertProvider(type = MapperProvider.class, method = "insertEntity")
    int insert(CacUser cacUser);

    @InsertProvider(type = MapperProvider.class, method = "insertBatch")
    int insertBatch(@Param("list") List<CacUser> list);

    @UpdateProvider(type = MapperProvider.class, method = "updateEntity")
    int update(CacUser cacUser);

    @DeleteProvider(type = MapperProvider.class, method = "deleteEntity")
    int delete(CacUser cacUser);

    @Select("select a.id as userId,d.id,d.parent_id as parentId,d.name,d.url,d.type, " +
            "d.system_id as systemId,d.create_time as createTime,e.short_name as shortNam " +
            "from cac_user a " +
            "left join cac_user_role b on a.id = b.user_id " +
            "left join cac_role_resource c on b.role_id = c.role_id " +
            "left join cac_resource d on c.resource_id = d.id " +
            "left join cac_system e on d.system_id = e.id " +
            "where a.id = #{id} and a.status = '01' and a.is_on = '01' and d.del_flag = '01' and e.status = '00'")
    List<CacRedisMenu> findAllMenuByUserId(@Param("id") Long id);

    @Select("select 1 as groupNum,b.id,b.role_name as roleName,b.system_id as systemId from cac_user_role a " +
            "left join cac_role b on a.role_id = b.id " +
            "where a.user_id = #{userId} and b.system_id = #{systemId} " + //该系统下分配给该用户的角色，groupNum=1
            "union all " +
            "select 2 as groupNum,b.id,b.role_name as roleName,b.system_id as systemId from cac_role b " +
            "where b.system_id = #{systemId} ")//该系统下所有权限，groupNum=2
    List<CacGroupRole> getEveryKindOfRoleBySystemId(@Param("userId") Long userId, @Param("systemId") Long systemId);

    @Select("select 2 as groupNum,b.id,b.role_name as roleName,b.system_id as systemId from cac_role b " +
            "where b.system_id = #{systemId} ")//该系统下所有权限，groupNum=2
    List<CacGroupRole> getAllRoleBySystemId(@Param("systemId") Long systemId);
    
    @Select("<script>" +
            "select a.id as userId,d.id,d.parent_id as parentId,d.name,d.url,d.type, " +
            "d.system_id as systemId,d.create_time as createTime,e.short_name as shortNam " +
            "from cac_user a " +
            "left join cac_user_role b on a.id = b.user_id " +
            "left join cac_role_resource c on b.role_id = c.role_id " +
            "left join cac_resource d on c.resource_id = d.id " +
            "left join cac_system e on d.system_id = e.id " +
            "where " +
            " a.status = '01' " +
            "<when test='id!= null'> " +
            " and a.id = #{id}" +
            "</when>" +
            " and a.is_on = '01' and d.del_flag = '01' and e.status = '00' and d.system_id= #{systemId}" +
            "</script>")
    List<CacRedisMenu> findAllMenuByUserIdAndSystemId(@Param("id") Long id,@Param("systemId") Long systemId);

    class SQLProvider {

    }
}