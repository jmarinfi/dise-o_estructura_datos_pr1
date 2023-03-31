package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.List;

public class OrganizingEntity {

    private int id;
    private String name;
    private String description;
    private List<SportEvent> eventsEntity;

    public OrganizingEntity(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.eventsEntity = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SportEvent> getEventsEntity() {
        return eventsEntity;
    }

    public void setEventsEntity(List<SportEvent> eventsEntity) {
        this.eventsEntity = eventsEntity;
    }
}
