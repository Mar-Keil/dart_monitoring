package dart_monitoring.persistence;

import dart_monitoring.enums.CheckoutRule;
import dart_monitoring.model.Game;
import dart_monitoring.model.Player;
import dart_monitoring.model.Turn;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false, properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class RepositoryIntegrationTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TurnRepository turnRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void clearDatabase() {
        turnRepository.deleteAll();
        gameRepository.deleteAll();
        playerRepository.deleteAll();
    }

    @Test
    void sortPlayersById() {
        Player baker = savePlayer("Amy", "Baker");
        Player zoe = savePlayer("Zoe", "Adams");
        Player firstAmy = savePlayer("Amy", "Adams");
        Player secondAmy = savePlayer("Amy", "Adams");

        assertThat(playerRepository.findAll())
                .extracting(Player::getId)
                .containsExactly(baker.getId(), zoe.getId(), firstAmy.getId(), secondAmy.getId());
    }

    @Test
    void persistTurnsAndUpdateGameAcrossTransactions() {
        Player player = savePlayer("Alex", "Smith");
        Game game = gameRepository.saveWithUpdatedAt(new Game(player, 501, CheckoutRule.DOUBLE_OUT));

        assertThat(game.getId()).isNotNull();
        assertThat(game.getUpdatedAt()).isNotNull();

        Turn turn = new Turn(game, 1);
        turn.setThrows(new String[]{"t20", "t20", "t20"});
        turnRepository.save(turn);

        game.setRemainingScore(321);
        gameRepository.saveWithUpdatedAt(game);

        Game reloaded = gameRepository.findById(game.getId()).orElseThrow();
        assertThat(reloaded.getRemainingScore()).isEqualTo(321);
        assertThat(gameRepository.count()).isEqualTo(1);
        assertThat(jdbcTemplate.queryForObject(
                "select count(*) from turns where game_id = ?", Long.class, game.getId()))
                .isEqualTo(1L);
    }

    @Test
    void refreshTimestampWhenSavingUnchangedGame() {
        Player player = savePlayer("Alex", "Smith");
        Game game = gameRepository.saveWithUpdatedAt(new Game(player, 501, CheckoutRule.DOUBLE_OUT));
        jdbcTemplate.update("update games set updated_at = ? where game_id = ?",
                OffsetDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC), game.getId());
        Game unchanged = gameRepository.findById(game.getId()).orElseThrow();

        assertThat(unchanged.getUpdatedAt()).isEqualTo(Instant.EPOCH);
        gameRepository.saveWithUpdatedAt(unchanged);

        Game reloaded = gameRepository.findById(game.getId()).orElseThrow();
        assertThat(reloaded.getUpdatedAt()).isAfter(Instant.EPOCH);
        assertThat(reloaded.getRemainingScore()).isEqualTo(501);
    }

    private Player savePlayer(String firstname, String lastname) {
        Player player = new Player();
        player.setFirstname(firstname);
        player.setLastname(lastname);
        return playerRepository.save(player);
    }
}
