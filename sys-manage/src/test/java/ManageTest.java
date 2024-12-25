import com.haohaodayouxi.manage.ManageBootstrap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ManageTest
 *
 * @author TONE
 * @date 2024/8/11 11:33
 */
@Slf4j
@SpringBootTest(classes = ManageBootstrap.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ManageTest {

    @Test
    public void test() {
        log.info("ManageTest");
    }
}
