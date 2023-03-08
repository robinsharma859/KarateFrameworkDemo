package Executor;
import com.intuit.karate.*;
import com.intuit.karate.junit5.Karate;
import com.intuit.karate.Runner.Builder;
import com.intuit.karate.junit5.*;
import org.junit.jupiter.api.Test;

public class TestPlayer {

    @Test
    public void RunTest() {
        Builder aRunner = new Builder();
        aRunner.path("classpath:FeatureFiles");
        aRunner.parallel(1);

    }
}
