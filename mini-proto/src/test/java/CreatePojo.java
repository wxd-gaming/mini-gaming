import org.junit.Test;
import wxdgaming.spring.boot.net.message.ProtoBuf2Pojo;

public class CreatePojo {

    @Test
    public void createPojo() {
        ProtoBuf2Pojo.actionProtoFile("./src/main/java", "./src/main/proto");
    }

}
