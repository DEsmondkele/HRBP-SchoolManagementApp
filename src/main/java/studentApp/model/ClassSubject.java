package studentApp.model;

import io.vertx.core.json.JsonObject;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

import javax.validation.constraints.NotNull;

public class ClassSubject {
    @Id
    private int id;
    @NotNull
    private String name;
    private Double score;

    public ClassSubject() {
        this.id = -1;
    }
    public ClassSubject(JsonObject json){
        this.name = json.getString("NAME");
        this.score = json.getDouble("SCORE");
        this.id = json.getInteger("ID");
    }
    public ClassSubject(String name, double score){
        this.name = name;
        this.score = score;
        this.id = -1;
    }



    public ClassSubject(int id, String name, Double score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
