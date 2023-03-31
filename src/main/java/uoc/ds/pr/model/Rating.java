package uoc.ds.pr.model;

import uoc.ds.pr.SportEvents4Club;

public class Rating {

    private Player player;
    private SportEvent event;
    private SportEvents4Club.Rating rating;
    private String message;

    public Rating(Player player, SportEvent event, SportEvents4Club.Rating rating, String message) {
        this.player = player;
        this.event = event;
        this.rating = rating;
        this.message = message;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public SportEvent getEvent() {
        return event;
    }

    public void setEvent(SportEvent event) {
        this.event = event;
    }

    public SportEvents4Club.Rating rating() {
        return rating;
    }

    public void setRating(SportEvents4Club.Rating rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
