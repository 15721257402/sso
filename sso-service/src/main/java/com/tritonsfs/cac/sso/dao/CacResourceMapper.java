package com.tritonsfs.cac.sso.dao;

import com.tritonsfs.cac.frame.dao.MapperProvider;
import com.tritonsfs.cac.sso.model.CacResource;
import java.util.List;
import java.util.Map;

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
public interface CacResourceMapper {
    @Select("select * from cac_resource where id = #{id} ")
    CacResource queryById(@Param("id") Long id);

    @SelectProvider(type = MapperProvider.class, method = "queryByEntity")
    List<CacResource> queryByEntity(CacResource cacResource);

    @SelectProvider(type = MapperProvider.class, method = "queryByEntityOrder")
    List<CacResource> queryByEntityOrder(CacResource cacResource, String... order);

    @InsertProvider(type = MapperProvider.class, method = "insertEntity")
    int insert(CacResource cacResource);

    @InsertProvider(type = MapperProvider.class, method = "insertBatch")
    int insertBatch(@Param("list") List<CacResource> list);

    @UpdateProvider(type = MapperProvider.class, method = "updateEntity")
    int update(CacResource cacResource);

    @DeleteProvider(type = MapperProvider.class, method = "deleteEntity")
    int delete(CacResource cacResource);
    
    /**
    * Description: 根据id得到所有父级id（1，2，3）
    * @author chenshunyu  
    * @date 2018年4月27日  
    * @version 1.0
     */
    @Select("select getCacResourceParentList(#{id}) ")
    String selectAllParentId(@Param("id") Long id);
    
    /**
    * Description: 查询当前节点所有父级对象
    * @author chenshunyu  
    * @date 2018年4月27日  
    * @version 1.0
     */
    @Select("select * FROM cac_resource where id in (${ids}) ")
    List<CacResource> getListByIds(@Param("ids") String ids);

    @Select("select a.* from cac_resource a " +
            "left join cac_role_resource b on b.role_id = a.id " +
            "left join cac_user_role c on c.role_id = b.role_id " +
            "where c.user_id = #{userId} and a.system_id = #{systemId} " +
            "GROUP BY a.id")
    List<CacResource> selectResByUserAndSys(Map<String, Object> params);
}