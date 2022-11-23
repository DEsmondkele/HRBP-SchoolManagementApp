package studentApp.web;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import studentApp.model.ClassSubject;


import java.io.IOException;
import java.net.ServerSocket;

@RunWith(VertxUnitRunner.class)
@SpringBootTest
public class SchoolManagementAppTest {
    private  Vertx vertx;

    private Integer port;


    public SchoolManagementAppTest(){

    }

    @Before
    public void setUp(TestContext context)throws IOException {
       vertx =Vertx.vertx();
        ServerSocket socket = new ServerSocket(0);
        port = socket.getLocalPort();
        socket.close();

        DeploymentOptions options = new DeploymentOptions()
                .setConfig(new JsonObject()
                        .put("http.port", port)
                        .put("url", "jdbc:hsqldb:mem:test?shutdown=true")
                        .put("driver_class", "org.postgresql.Driver")
                );
        vertx.deployVerticle(ClassSubject.class.getName(), options, context.asyncAssertSuccess());
    }
    @After
    public void tearDown(TestContext context){
        vertx.close(context.asyncAssertSuccess());
    }
    @Test
    public void studentApplicationTest(TestContext context){
        final Async async = context.async();
        vertx.createHttpClient().getNow(port,"localhost","/",res->{
            res.handler(body->{
                context.assertTrue(body.toString().contains("Hello"));
                async.complete();
            });
        });
    }
}
