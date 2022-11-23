package studentApp.web;

import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import studentApp.model.Student;

import java.util.List;
import java.util.stream.Collectors;

import static io.vertx.core.Future.succeededFuture;

public class SchoolManagementApplication extends AbstractVerticle{
    private JDBCClient jdbc;

    @Override
    public void start(Future<Void> future){
        jdbc = JDBCClient.createShared(vertx, config(),"");
        startApp(
                (connection) -> createSchoolData(connection,
                        (nothings) ->  startSchoolManagementWebApp(
                                (http) ->completeStartup(http,future)
                        ),future),future);
    }

    private void completeStartup(AsyncResult<HttpServer> http, Future<Void> future) {
        if (http.succeeded()) {
            future.complete();
        } else {
            future.fail(http.cause());
        }
    }
    @Override
    public void stop() throws Exception {
        // Close the JDBC client.
        jdbc.close();
    }


    private void startApp(Handler<AsyncResult<SQLConnection>>next,Future<Void>future){
      jdbc.getConnection(ar->{
          if (ar.failed()){
              future.fail(ar.cause());
          }
          else {
              next.handle(succeededFuture(ar.result()));
          }
      });
    }
    private void startSchoolManagementWebApp(Handler<AsyncResult<HttpServer>>next){
       Router router = Router.router(vertx);
        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                    .putHeader("","")
                    .end("");
        });
        router.get().handler(this::getAllStudentScores);
        router.route("/");
        router.put("");
        router.post();
        router.get("");
        router.delete("");

              vertx.createHttpServer().requestHandler(router::accept).listen(
                        // Retrieve the port from the configuration,
                        // default to 8080.
                        config().getInteger("http.port", 8080),
                      next
                );
    }
    private void createSchoolData(AsyncResult<SQLConnection> result, Handler<AsyncResult<Void>> next, Future<Void> fut) {
        if (result.failed()) {
            fut.fail(result.cause());
        } else {
            SQLConnection connection = result.result();
            connection.execute(
                    "CREATE TABLE IF NOT EXISTS STUDENT(id integer identity primary key,firstname VARCHAR(200),lastname VARCHAR(200),email VARCHAR(100),password VARCHAR(100))",
                    ar->{
            if(ar.failed()){
                fut.fail(ar.cause());
                connection.close();
                return;
            }
            connection.query("",select->{
                if ((select.failed())){
                    fut.fail(select.cause());
                    connection.close();
                    return;
                }
                if(select.result().getNumRows() == 0){
                    insert(
                            new Student("Segun","Philip","segun@gmail.com","SegunPhilip"),connection,
                            (v)->insert(new Student("Adebayo","Philip","philip@gmail.com","adebayoPhilip"),connection,
                                    (r)->{
                                next.handle(Future.<Void>succeededFuture());
                                connection.close();
                                    }));
                }else { next.handle(Future.<Void>succeededFuture());
                connection.close();
                }});
            });
        }
    }
    private void insert(Student students, SQLConnection connection, Handler<AsyncResult<Student>> next) {
        String sql = "INSERT INTO STUDENT(firstname,lastname,email,password) VALUES ?, ?, ?, ?";
   connection.updateWithParams(sql,new JsonArray().add(students.getFirstName()).add(students.getLastName())
           .add(students.getEmail()).add(students.getPassword()),(ar)->{
       if (ar.failed()){
           next.handle(Future.failedFuture(ar.cause()));
           connection.close();
           return;
       }
       UpdateResult results = ar.result();
       Student newStudent = new Student(results.getKeys().getInteger(0),students.getFirstName(),students.getLastName(),students.getEmail(),students.getPassword());
    next.handle(succeededFuture(newStudent));
   });
    }
    private void getAllStudentScores(RoutingContext routingContext) {
    jdbc.getConnection(arr->{
        SQLConnection conn = arr.result();
        conn.query("SELECT * FROM STUDENt",results->{
            List<Student>students = results.result().getRows().stream().map(Student::new).collect(Collectors.toList());
        });
    });
    }

    }
