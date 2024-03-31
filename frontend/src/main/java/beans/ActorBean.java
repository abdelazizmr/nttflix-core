package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import models.Actor;

@ManagedBean(name = "actorBean", eager = true)
@SessionScoped
public class ActorBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String restResource =
            "http://localhost:8080/RestWebservice/api/actors/";

    private List<Actor> actorList = new ArrayList<>();

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }


    // Getter and setter for actorList

    public void getActorsByMovieId(int movieId) {
        try {
            Client client = ClientBuilder.newClient();
            actorList = client.target(restResource + "movie/" + movieId)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Actor>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
