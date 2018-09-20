package com.tritonsfs.cac.sso.dao;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import com.tritonsfs.cac.frame.dao.MapperProvider;
import com.tritonsfs.cac.sso.entity.CacResourceVO;
import com.tritonsfs.cac.sso.model.CacRole;

@Mapper
@Repository
public interface CacRoleMapper {
    @Select("select * from cac_role where id = #{id} ")
    CacRole queryById(@Param("id") Long id);

    @SelectProvider(type = MapperProvider.class, method = "queryByEntity")
    List<CacRole> queryByEntity(CacRole cacRole);

    @SelectProvider(type = MapperProvider.class, method = "queryByEntityOrder")
    List<CacRole> queryByEntityOrder(CacRole cacRole, String... order);

    @InsertProvider(type = MapperProvider.class, method = "insertEntity")
    int insert(CacRole cacRole);

    @InsertProvider(type = MapperProvider.class, method = "insertBatch")
    int insertBatch(@Param("list") List<CacRole> list);

    @UpdateProvider(type = MapperProvider.class, method = "updateEntity")
    int update(CacRole cacRole);

    @DeleteProvider(type = MapperProvider.class, method = "deleteEntity")
    int delete(CacRole cacRole);
    
    @Select("SELECT r.id,r.`name`,r.url,parent_id parentId,rr.role_id roleId,r.type FROM cac_resource r "
    		+ "LEFT JOIN cac_role_resource rr ON r.id=rr.resource_id AND rr.role_id= #{roleId} "
    		+ "WHERE r.system_id= #{systemId} and r.del_flag='01' ")
    List<CacResourceVO> queryBySytemId(@Param("systemId") long systemId,@Param("roleId") long roleId);

}