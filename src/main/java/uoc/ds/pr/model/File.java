package uoc.ds.pr.model;

import uoc.ds.pr.*;
import uoc.ds.pr.SportEvents4Club;

import java.time.LocalDate;

public class File {

    private String id;
    private String eventId;
    private OrganizingEntity organizingEntity;
    private String description;
    private SportEvents4Club.Type type;
    private byte resources;
    private int max;
    private LocalDate startDate;
    private LocalDate endDate;
    private SportEvents4Club.Status status;
    private LocalDate updated;

    public File(String id, String eventId, OrganizingEntity organizingEntity, String description, SportEvents4Club.Type type, byte resources, int max, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.eventId = eventId;
        this.organizingEntity = organizingEntity;
        this.description = description;
        this.type = type;
        this.resources = resources;
        this.max = max;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = SportEvents4Club.Status.PENDING;
        this.updated = null;
    }

    public String getFileId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public OrganizingEntity getOrganizingEntity() {
        return organizingEntity;
    }

    public void setOrganizingEntity(OrganizingEntity organizingEntity) {
        this.organizingEntity = organizingEntity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SportEvents4Club.Type getType() {
        return type;
    }

    public void setType(SportEvents4Club.Type type) {
        this.type = type;
    }

    public byte getResources() {
        return resources;
    }

    public void setResources(byte resources) {
        this.resources = resources;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStatus(SportEvents4Club.Status status) { this.status = status; }

    public SportEvents4Club.Status getStatus() { return status; }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }
}
