import com.cyy.FinanceAdminApiApplication;
import com.cyy.finance.biz.domain.Tenant;
import com.cyy.finance.biz.domain.TenantField;
import com.cyy.finance.biz.mapper.TenantMapper;
import com.cyy.finance.biz.service.TenantService;
import com.cyy.mybatis.help.MyBatisWrapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@SpringBootTest(classes = FinanceAdminApiApplication.class)
@MapperScan(basePackages = "./com/cyy/finance/biz/mapper/*.xml")
public class Test1 {

    TenantService tenantService;

    TenantMapper tenantMapper;
    @Test
    void test() {
//        Tenant tenant = new Tenant();
//        tenant.setName("test");
//        tenant.initDefault();
//        tenantMapper.insert(tenant);
//        System.out.println(tenant.getId());

        MyBatisWrapper<Tenant> example = new MyBatisWrapper<Tenant>();
        example.select(TenantField.Id).whereBuilder();
        System.out.println(tenantMapper.get(example));
    }

}
