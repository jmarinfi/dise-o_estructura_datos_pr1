package uoc.ds.pr;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.util.ResourceUtil;

import java.time.LocalDate;

import static uoc.ds.pr.util.DateUtils.createLocalDate;

public class SportEvents4ClubPR1Test2 {

    private SportEvents4Club sportEvents4Club;

    @Before
    public void setUp() throws Exception {
        this.sportEvents4Club = FactorySportEvents4Club.getSportEvents4Club();
    }

    @After
    public void tearDown() {
        this.sportEvents4Club = null;
    }

    public void initialState() {
        Assert.assertEquals(12, this.sportEvents4Club.numPlayers());
        Assert.assertEquals(5, this.sportEvents4Club.numOrganizingEntities());
        Assert.assertEquals(6, this.sportEvents4Club.numFiles());
        Assert.assertEquals(1, this.sportEvents4Club.numPendingFiles());

        Assert.assertEquals(1, this.sportEvents4Club.numSportEventsByPlayer("idPlayer1"));
        Assert.assertEquals(3, this.sportEvents4Club.numSportEventsByPlayer("idPlayer2"));
        Assert.assertEquals(1, this.sportEvents4Club.numSportEventsByPlayer("idPlayer3"));
        Assert.assertEquals(1, this.sportEvents4Club.numSportEventsByPlayer("idPlayer4"));
        Assert.assertEquals(1, this.sportEvents4Club.numSportEventsByPlayer("idPlayer5"));
        Assert.assertEquals(1, this.sportEvents4Club.numSportEventsByPlayer("idPlayer6"));
        Assert.assertEquals(1, this.sportEvents4Club.numSportEventsByPlayer("idPlayer7"));
        Assert.assertEquals(1, this.sportEvents4Club.numSportEventsByPlayer("idPlayer8"));
        Assert.assertEquals(1, this.sportEvents4Club.numSportEventsByPlayer("idPlayer9"));

        Assert.assertEquals(7, this.sportEvents4Club.numPlayersBySportEvent("EV-1101"));
        Assert.assertEquals(3, this.sportEvents4Club.numPlayersBySportEvent("EV-1102"));
    }

    @Test
    public void updateFileTest2() throws DSException {

        initialState();

        byte resources1 = ResourceUtil.getFlag(SportEvents4Club.FLAG_BASIC_LIFE_SUPPORT,  SportEvents4Club.FLAG_VOLUNTEERS);
        this.sportEvents4Club.addFile("XXX-001", "EV-010", 1,"description EV-010",
                SportEvents4Club.Type.MEDIUM, resources1, 50,
                createLocalDate("22-11-2022"), createLocalDate("28-11-2022"));

        Assert.assertEquals(2, this.sportEvents4Club.numPendingFiles() );
        Assert.assertEquals(7, this.sportEvents4Club.numFiles() );

        this.sportEvents4Club.updateFile(SportEvents4Club.Status.DISABLED, LocalDate.now(), "rechazado");

        Assert.assertEquals(1, this.sportEvents4Club.numPendingFiles());
        Assert.assertEquals(7, this.sportEvents4Club.numFiles());
        Assert.assertEquals(2, this.sportEvents4Club.numRejectedFiles());
        Assert.assertEquals(4, this.sportEvents4Club.numSportEvents());

        Assert.assertEquals("XXX-001", this.sportEvents4Club.currentFile().getFileId());
        Assert.assertEquals(1, this.sportEvents4Club.numPendingFiles());

        this.sportEvents4Club.updateFile(SportEvents4Club.Status.ENABLED, LocalDate.now(), "aceptado");

        Assert.assertEquals(0, this.sportEvents4Club.numPendingFiles());
        Assert.assertEquals(7, this.sportEvents4Club.numFiles());
        Assert.assertEquals(2, this.sportEvents4Club.numRejectedFiles());
        Assert.assertEquals(5, this.sportEvents4Club.numSportEvents());

        Assert.assertNull(this.sportEvents4Club.currentFile());
        Assert.assertThrows(NoFilesException.class, () ->
                this.sportEvents4Club.updateFile(SportEvents4Club.Status.DISABLED, LocalDate.now(), "rechazado"));
    }

    @Test
    public void signUpEventTest2() throws DSException {

        initialState();

        Assert.assertEquals(1, this.sportEvents4Club.numPlayersBySportEvent("EV-1104"));
        Assert.assertEquals(5, this.sportEvents4Club.getSportEvent("EV-1104").getMaxInscriptions());

        Assert.assertThrows(PlayerNotFoundException.class, () ->
                this.sportEvents4Club.signUpEvent("NotExists", "EV-1104"));

        Assert.assertThrows(SportEventNotFoundException.class, () ->
                this.sportEvents4Club.signUpEvent("idPlayer1", "NotExists"));

        Assert.assertEquals("idPlayer2", this.sportEvents4Club.mostActivePlayer().getId());
        Assert.assertEquals(3, this.sportEvents4Club.mostActivePlayer().getEventsPlayer().size());
        Assert.assertEquals(1, this.sportEvents4Club.getPlayer("idPlayer1").getEventsPlayer().size());

        Assert.assertEquals(1, this.sportEvents4Club.getSportEvent("EV-1104").getTotalInscriptions());
        this.sportEvents4Club.signUpEvent("idPlayer1", "EV-1104");
        Assert.assertEquals(2, this.sportEvents4Club.getPlayer("idPlayer1").getEventsPlayer().size());
        Assert.assertEquals(2, this.sportEvents4Club.getSportEvent("EV-1104").getTotalInscriptions());

        Assert.assertEquals("EV-1106", this.sportEvents4Club.currentFile().getEventId() );
        this.sportEvents4Club.updateFile(SportEvents4Club.Status.ENABLED, LocalDate.now(), "aceptado");
        this.sportEvents4Club.signUpEvent("idPlayer1", "EV-1102");
        this.sportEvents4Club.signUpEvent("idPlayer1", "EV-1106");

        Assert.assertEquals("idPlayer1", this.sportEvents4Club.mostActivePlayer().getId());
    }

    @Test
    public void getSportEventsByPlayer2() throws DSException {

        initialState();

        Assert.assertThrows(NoSportEventsException.class, () ->
                this.sportEvents4Club.getEventsByPlayer("idPlayer10"));
    }

    @Test
    public void addRatingAndBestEventTest2() throws DSException {

        initialState();

        Assert.assertThrows(SportEventNotFoundException.class, () ->
                this.sportEvents4Club.getRatingsByEvent("NotExists"));

        Assert.assertThrows(PlayerNotFoundException.class, () ->
                this.sportEvents4Club.addRating("NotExists", "EV-1104", SportEvents4Club.Rating.FIVE, "Great!"));

        Assert.assertThrows(SportEventNotFoundException.class, () ->
                this.sportEvents4Club.addRating("idPlayer1", "NotExists", SportEvents4Club.Rating.FIVE, "Great!"));

        Assert.assertThrows(PlayerNotInSportEventException.class, () ->
                this.sportEvents4Club.addRating("idPlayer1", "EV-1104", SportEvents4Club.Rating.FIVE, "Great!"));
    }
}
