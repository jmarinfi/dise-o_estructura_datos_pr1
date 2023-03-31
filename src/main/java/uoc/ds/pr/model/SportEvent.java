package uoc.ds.pr.model;


import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.adt.sequential.Queue;
import edu.uoc.ds.adt.sequential.QueueArrayImpl;
import uoc.ds.pr.SportEvents4Club;
import uoc.ds.pr.collections.LinkedQueue;

import java.time.LocalDate;

public class SportEvent implements Comparable<SportEvent> {

    private String eventId;
    File file;
    OrganizingEntity organizingEntity;
    SportEvents4Club.Type type;
    byte resources;
    private int maxInscriptions;
    LocalDate startDate;
    LocalDate endDate;
    private Queue<Player> inscriptionQueue;
    private Queue<Player> inscriptionQueueSubstitutes;
    private List<Rating> ratingList;
    private int sumRating;

    public SportEvent(String eventId, File file, OrganizingEntity organizingEntity, SportEvents4Club.Type type, byte resources, int maxInscriptions, LocalDate startDate, LocalDate endDate) {
        this.eventId = eventId;
        this.file = file;
        this.organizingEntity = organizingEntity;
        this.type = type;
        this.resources = resources;
        this.maxInscriptions = maxInscriptions;
        this.startDate = startDate;
        this.endDate = endDate;
        this.inscriptionQueue = new QueueArrayImpl<>(SportEvents4Club.MAX_NUM_ENROLLMENT);
        this.inscriptionQueueSubstitutes = new LinkedQueue<>();
        this.ratingList = new LinkedList<>();
        this.sumRating = 0;
    }

    public String getEventId() {
        return eventId;
    }

    public int getMaxInscriptions() {
        return maxInscriptions;
    }

    public void setMaxInscriptions(int maxInscriptions) {
        this.maxInscriptions = maxInscriptions;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Queue<Player> getInscriptionQueue() {
        return inscriptionQueue;
    }

    public void setInscriptionQueue(Queue<Player> inscriptionQueue) {
        this.inscriptionQueue = inscriptionQueue;
    }

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    public Queue<Player> getInscriptionQueueSubstitutes() {
        return inscriptionQueueSubstitutes;
    }

    public void setInscriptionQueueSubstitutes(Queue<Player> inscriptionQueueSubstitutes) {
        this.inscriptionQueueSubstitutes = inscriptionQueueSubstitutes;
    }

    public int getSumRating() {
        return sumRating;
    }

    public void setSumRating(int sumRating) {
        this.sumRating = sumRating;
    }

    public double rating() {
        if (ratingList.size() == 0) {
            return 0.0;
        }
        return ((double) this.sumRating / (double) this.ratingList.size());
    }

    public int getTotalInscriptions() {
        return inscriptionQueue.size() + inscriptionQueueSubstitutes.size();
    }

    @Override
    public int compareTo(SportEvent se) {
        return Double.compare(rating(), se.rating());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SportEvent)) {
            return false;
        }
        SportEvent se = (SportEvent) obj;
        return this.eventId.equals(se.getEventId());
    }
}
