package mvc_test;


import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ssm.bean.TblEmp;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:springmvc-config.xml"})
@WebAppConfiguration
//这三个配置是必须的，分别是指定junit的运行方式， 指定 spring和springmvc的配置文件， 第三个配置是为了可以在程序中注入sringmvc 容器
public class mvcTest {
//    注入springmvc 容器
    @Autowired
    WebApplicationContext webApplicationContext;

//    进行controller 主要是用MockMvc类进行测试，mock的意思是虚假的意思，就是进行虚拟的springmvc测试
    MockMvc mockMvc;

//    读取springmvc 配置文件， 实例化mockMvc
    @Before
    public void intMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetEmps( ) throws Exception{
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "1")).andReturn();
//        这一句就是对某个具体url进行测试， 并且是带参数地访问这个url，andReturn() 是要获取它返回来的数据
        MockHttpServletRequest request = result.getRequest();
//        得到request，因为下边的操作时在request 中获取数据

        /**
         * *从这里开始的操作就是很简单的，就是对分页器， 键为pageinfo 进行获取响应的值而已
         */
        PageInfo pi = (PageInfo) request.getAttribute("pageInfo");
        System.out.println("当前页码" + pi.getPageNum());
        System.out.println("总页码" + pi.getPages());
        System.out.println("总记录数" + pi.getTotal());
        System.out.println("在页面需要显示的页码");
        int [] nums = pi.getNavigatepageNums();
        for(int i : nums){
            System.out.print(" " + i);
        }
//        获取员工数据
        List<TblEmp> list = pi.getList();
        for(TblEmp  emp : list){
            System.out.println("empName is " +  emp.getTblDept().getDeptName());
        }
    }
}
