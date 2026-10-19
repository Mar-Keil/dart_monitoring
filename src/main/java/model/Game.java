package model;

import enums.CheckoutRule;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(name = "starting_score", nullable = false)
    private int startingScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "checkout_rule", nullable = false)
    private CheckoutRule checkoutRule;

    @Column(name = "final_score")
    private Integer finalScore;

    @Transient
    private int remainingScore;

    @Transient
    private List<Turn> turns = new ArrayList<>();

    public Game() {
    }

    public Game(Player player, int startingScore, CheckoutRule checkoutRule) {
        this.player = player;
        this.startingScore = startingScore;
        this.checkoutRule = checkoutRule;
        this.remainingScore = startingScore;
    }

    public Integer getId() {
        return id;
    }

    public void setFinalScore(Integer finalScore) {this.finalScore = finalScore;}

    public void setRemainingScore(int remainingScore) {
        this.remainingScore = remainingScore;
    }

    public void addTurn(Turn turn) {
        turns.add(turn);
    }
}
