package ssm.dao;


import java.util.List;
import org.apache.ibatis.annotations.Param;
import ssm.bean.TblDept;
import ssm.bean.TblDeptExample;

public interface TblDeptMapper {
    long countByExample(TblDeptExample example);

    int deleteByExample(TblDeptExample example);

    int deleteByPrimaryKey(Integer deptId);

    int insert(TblDept record);

    int insertSelective(TblDept record);

    List<TblDept> selectByExample(TblDeptExample example);

    TblDept selectByPrimaryKey(Integer deptId);

    int updateByExampleSelective(@Param("record") TblDept record, @Param("example") TblDeptExample example);

    int updateByExample(@Param("record") TblDept record, @Param("example") TblDeptExample example);

    int updateByPrimaryKeySelective(TblDept record);

    int updateByPrimaryKey(TblDept record);
}