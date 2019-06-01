package ssm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssm.bean.TblEmp;
import ssm.bean.TblEmpExample;
import ssm.dao.TblEmpMapper;

import java.util.List;

@Service
public class EmpsService {


    @Autowired
    TblEmpMapper tblEmpMapper;

    public List<TblEmp>  getEmps(){
        return  tblEmpMapper.selectByExampleWithDept(null);
    }


    public boolean saveEmp(TblEmp emp){
        tblEmpMapper.insertSelective(emp);
        return true;
    }

    public boolean checkUser(String empName){
        TblEmpExample empExample = new TblEmpExample();
        TblEmpExample.Criteria criteria = empExample.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = tblEmpMapper.countByExample(empExample);
        return count == 0;
    }
}
