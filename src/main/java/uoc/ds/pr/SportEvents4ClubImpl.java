package uoc.ds.pr;

import edu.uoc.ds.adt.nonlinear.Dictionary;
import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.adt.sequential.Queue;
import edu.uoc.ds.adt.sequential.QueueArrayImpl;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.collections.*;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.*;

import java.time.LocalDate;

public class SportEvents4ClubImpl implements SportEvents4Club {

    private Player[] players;
    private int numPlayers;
    private Player mostActivePlayer;
    private OrganizingEntity[] organizingEntities;
    private int numOrganizingEntities;
    private Queue<File> files;
    private int numTotalFiles;
    private int numPendingFiles;
    private int numRejectedFiles;
    private Dictionary<String, SportEvent> sportEventsOrderedById;
    private OrderedArray<SportEvent> sportEventsOrderedByRating;

    public SportEvents4ClubImpl() {
        this.players = new Player[MAX_NUM_PLAYER];
        this.numPlayers = 0;
        this.mostActivePlayer = null;
        this.organizingEntities = new OrganizingEntity[MAX_NUM_ORGANIZING_ENTITIES];
        this.numOrganizingEntities = 0;
        this.files = new QueueArrayImpl<>();
        this.numTotalFiles = 0;
        this.numPendingFiles = 0;
        this.numRejectedFiles = 0;
        this.sportEventsOrderedById = new OrderedDictionaryArray<>(MAX_NUM_SPORT_EVENTS);
        this.sportEventsOrderedByRating = new OrderedArray<>(MAX_NUM_SPORT_EVENTS);
    }

    @Override
    public void addPlayer(String id, String name, String surname, LocalDate dateOfBirth) {
        Player pl = getPlayer(id);
        if (pl == null) {
            players[numPlayers] = new Player(id, name, surname, dateOfBirth);
            numPlayers++;
        } else {
            pl.setName(name);
            pl.setSurname(surname);
            pl.setDateOfBirth(dateOfBirth);
        }
    }

    @Override
    public void addOrganizingEntity(int id, String name, String description) {
        OrganizingEntity oe = getOrganizingEntity(id);
        if (oe == null) {
            organizingEntities[numOrganizingEntities] = new OrganizingEntity(id, name, description);
            numOrganizingEntities++;
        } else {
            oe.setName(name);
            oe.setDescription(description);
        }
    }

    @Override
    public void addFile(String id, String eventId, int orgId, String description, Type type, byte resources, int max, LocalDate startDate, LocalDate endDate) throws OrganizingEntityNotFoundException {
        OrganizingEntity org = getOrganizingEntity(orgId);
        if (org == null) {
            throw new OrganizingEntityNotFoundException("La entidad organizadora con id " + orgId + " no existe");
        } else {
            files.add(new File(id, eventId, org, description, type, resources, max, startDate, endDate));
            numTotalFiles++;
            numPendingFiles++;
        }
    }

    @Override
    public File updateFile(Status status, LocalDate date, String description) throws NoFilesException {
        if (files.isEmpty()) {
            throw new NoFilesException("No hay ninguna ficha en el sistema");
        }
        File file = files.poll();
        file.setStatus(status);
        file.setUpdated(LocalDate.now());
        if (file.getStatus() == Status.DISABLED) {
            numPendingFiles--;
            numRejectedFiles++;
        }
        if (file.getStatus() == Status.ENABLED) {
            SportEvent event = new SportEvent(file.getEventId(), file, file.getOrganizingEntity(), file.getType(), file.getResources(), file.getMax(), file.getStartDate(), file.getEndDate());
            OrganizingEntity entity = file.getOrganizingEntity();
            sportEventsOrderedById.put(event.getEventId(), event);
            sportEventsOrderedByRating.insert(event);
            if (entity != null) {
                entity.getEventsEntity().insertEnd(event);
            }
            numPendingFiles--;
        }
        return file;
    }

    @Override
    public void signUpEvent(String playerId, String eventId) throws PlayerNotFoundException, SportEventNotFoundException, LimitExceededException {
        Player player = getPlayer(playerId);
        SportEvent event = getSportEvent(eventId);
        if (player == null) {
            throw new PlayerNotFoundException("El jugador no existe");
        }
        if (event == null) {
            throw new SportEventNotFoundException("El evento no existe");
        }
        if (event.getInscriptionQueue().size() == event.getMaxInscriptions()) {
            event.getInscriptionQueueSubstitutes().add(player);
            throw new LimitExceededException("Se han alcanzado el máximo de inscripciones en el evento. Se ha inscrito el jugador como suplente");
        } else {
            event.getInscriptionQueue().add(player);
            player.getEventsPlayer().insertEnd(event);
            if ((mostActivePlayer == null) || (player.numEvents() > mostActivePlayer.numEvents())) {
                mostActivePlayer = player;
            }
        }
    }

    @Override
    public double getRejectedFiles() {
        return (double) this.numRejectedFiles / (double) this.numTotalFiles;
    }

    @Override
    public Iterator<SportEvent> getSportEventsByOrganizingEntity(int organizationId) throws NoSportEventsException {
        OrganizingEntity entity = getOrganizingEntity(organizationId);
        if (entity == null || entity.getEventsEntity().isEmpty()) {
            throw new NoSportEventsException("No existe ningún evento organizado por esta entidad");
        }
        return entity.getEventsEntity().values();
    }

    @Override
    public Iterator<SportEvent> getAllEvents() throws NoSportEventsException {
        if (sportEventsOrderedById.isEmpty()) {
            throw new NoSportEventsException("No existe ningún evento");
        }
        return sportEventsOrderedById.values();
    }

    @Override
    public Iterator<SportEvent> getEventsByPlayer(String playerId) throws NoSportEventsException {
        List<SportEvent> eventsList = getPlayer(playerId).getEventsPlayer();
        if (eventsList.isEmpty()) {
            throw new NoSportEventsException("No existe ningún evento en el que haya participado este jugador");
        }
        return eventsList.values();
    }

    @Override
    public void addRating(String playerId, String eventId, Rating rating, String message) throws SportEventNotFoundException, PlayerNotFoundException, PlayerNotInSportEventException {
        Player player = getPlayer(playerId);
        SportEvent event = getSportEvent(eventId);
        if (player == null) {
            throw new PlayerNotFoundException("El jugador no existe");
        }
        if (event == null) {
            throw new SportEventNotFoundException("El evento no existe");
        }
        if (!player.hasParticipated(event)) {
            throw new PlayerNotInSportEventException("El jugador no ha participado en el evento");
        }
        event.getRatingList().insertEnd(new uoc.ds.pr.model.Rating(player, event, rating, message));
        event.setSumRating(event.getSumRating() + rating.getValue());
        sportEventsOrderedByRating.sort();
    }

    @Override
    public Iterator<uoc.ds.pr.model.Rating> getRatingsByEvent(String eventId) throws SportEventNotFoundException, NoRatingsException {
        SportEvent event = getSportEvent(eventId);
        if (event == null) {
            throw new SportEventNotFoundException("El evento no existe");
        }
        List<uoc.ds.pr.model.Rating> ratingList = event.getRatingList();
        if (ratingList.isEmpty()) {
            throw new NoRatingsException("No hay valoraciones para este evento");
        }
        return ratingList.values();
    }

    @Override
    public Player mostActivePlayer() throws PlayerNotFoundException {
        if (numPlayers == 0) {
            throw new PlayerNotFoundException("No existe ningún jugador");
        }
        return this.mostActivePlayer;
    }

    @Override
    public SportEvent bestSportEvent() throws SportEventNotFoundException {
        if (numSportEvents() == 0) {
            throw new SportEventNotFoundException("No existe ningún evento");
        }
        return this.sportEventsOrderedByRating.getLast();
    }

    @Override
    public int numPlayers() {
        return this.numPlayers;
    }

    @Override
    public int numOrganizingEntities() {
        return this.numOrganizingEntities;
    }

    @Override
    public int numFiles() {
        return this.numTotalFiles;
    }

    @Override
    public int numRejectedFiles() {
        return this.numRejectedFiles;
    }

    @Override
    public int numPendingFiles() {
        return this.numPendingFiles;
    }

    @Override
    public int numSportEvents() {
        return this.sportEventsOrderedById.size();
    }

    @Override
    public int numSportEventsByPlayer(String playerId) {
        Player player = getPlayer(playerId);
        if (player == null) {
            return 0;
        }
        return player.getEventsPlayer().size();
    }

    @Override
    public int numPlayersBySportEvent(String sportEventId) {
        SportEvent event = getSportEvent(sportEventId);
        if (event == null) {
            return 0;
        }
        return event.getTotalInscriptions();
    }

    @Override
    public int numSportEventsByOrganizingEntity(int orgId) {
        OrganizingEntity org = getOrganizingEntity(orgId);
        if (org == null) {
            return 0;
        }
        return org.getEventsEntity().size();
    }

    @Override
    public int numSubstitutesBySportEvent(String sportEventId) {
        SportEvent se = getSportEvent(sportEventId);
        if (se == null) {
            return 0;
        }
        return se.getInscriptionQueueSubstitutes().size();
    }

    @Override
    public Player getPlayer(String playerId) {
        for (int i = 0; i < numPlayers; i++) {
            if (players[i].getId().equals(playerId)) {
                return players[i];
            }
        }
        return null;
    }

    @Override
    public SportEvent getSportEvent(String eventId) {
        return sportEventsOrderedById.get(eventId);
    }

    @Override
    public OrganizingEntity getOrganizingEntity(int id) {
        for (int i = 0; i < numOrganizingEntities; i++) {
            if (organizingEntities[i].getId() == id) {
                return organizingEntities[i];
            }
        }
        return null;
    }

    @Override
    public File currentFile() {
        return this.files.peek();
    }
}
