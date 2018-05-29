import com.dream.entity.Account;
import com.dream.util.ValidationUtils;
import org.junit.Test;

/**
 * @author huxingnan
 * @date 2018/5/17 11:44
 */
public class TestUser {
    @Test
    public void test1(){

        ValidationUtils.ValidResult validResult = ValidationUtils.validateBean(new Account());
        if(validResult.hasErrors()){
            System.out.println(validResult.getErrors());
        }

    }
}
