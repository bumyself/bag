package dao_test;




import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ssm.bean.TblDept;
import ssm.bean.TblEmp;
import ssm.dao.TblDeptMapper;
import ssm.dao.TblEmpMapper;

import java.util.UUID;

/**
 * 以下两个注解分别是指定spring的Junit和指定spring的配置文件
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DaoTest {


    @Autowired
    TblDeptMapper deptMapper;

    @Autowired
    TblEmpMapper empMapper;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Test
    public void testDao(){
//        测试deptMapper
//        TblDept tblDept1 = new TblDept("开发部");
//       deptMapper.insertSelective(tblDept1);

//        测试empMapperI String empName, String gender, Integer dId)
//        TblEmp emp = new TblEmp("lufy", "1", 1);
//        empMapper.insertSelective(emp);
        TblEmpMapper empMapper = sqlSessionTemplate.getMapper(TblEmpMapper.class);
        for(int i = 0; i < 1000; i++){
            String uid = UUID.randomUUID().toString().substring(0, 5) + i;
//            String empName, String gender,  String email, Integer dId
        empMapper.insertSelective(new TblEmp(uid, "1", uid + "@gmailcom", 1) );
        }

    }


}
