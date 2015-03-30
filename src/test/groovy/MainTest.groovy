import static org.junit.Assert.*;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

import org.cristalise.kernel.utils.Logger;
import org.cristalise.nbkernel.KernelRouter;
import org.junit.Test;


class MainTest {

    @Test
    public void test() {
        //fail("Not yet implemented")
        
        Logger.addLogStream(System.out, 8)

        Vertx.vertx().deployVerticle(new KernelRouter());
    }

}
