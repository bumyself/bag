package ssm.dao;


import java.util.List;
import org.apache.ibatis.annotations.Param;
import ssm.bean.TblEmp;
import ssm.bean.TblEmpExample;

public interface TblEmpMapper {
    long countByExample(TblEmpExample example);

    int deleteByExample(TblEmpExample example);

    int deleteByPrimaryKey(Integer empId);

    int insert(TblEmp record);

    int insertSelective(TblEmp record);

    List<TblEmp> selectByExample(TblEmpExample example);

    TblEmp selectByPrimaryKey(Integer empId);

    List<TblEmp> selectByExampleWithDept(TblEmpExample example);

    TblEmp selectByPrimaryKeyWithDept(Integer empId);

    int updateByExampleSelective(@Param("record") TblEmp record, @Param("example") TblEmpExample example);

    int updateByExample(@Param("record") TblEmp record, @Param("example") TblEmpExample example);

    int updateByPrimaryKeySelective(TblEmp record);

    int updateByPrimaryKey(TblEmp record);
}