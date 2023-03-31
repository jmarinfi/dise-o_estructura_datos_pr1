package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.traversal.Iterator;

import java.time.LocalDate;

public class Player {

    private String id;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private List<SportEvent> eventsPlayer;

    public Player(String id, String name, String surname, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.eventsPlayer = new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<SportEvent> getEventsPlayer() {
        return eventsPlayer;
    }

    public void setEventsPlayer(List<SportEvent> eventsPlayer) {
        this.eventsPlayer = eventsPlayer;
    }

    public int numEvents() {
        return this.eventsPlayer.size();
    }

    public boolean hasParticipated(SportEvent event) {
        Iterator<SportEvent> eventsIterator = eventsPlayer.values();
        while (eventsIterator.hasNext()) {
            SportEvent ev = eventsIterator.next();
            if (ev.equals(event)) {
                return true;
            }
        }
        return false;
    }
}
