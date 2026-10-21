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

    @Column(name = "remaining_score")
    private int remainingScore;

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

    public Player getPlayer() {return player;}

    public int getRemainingScore() {return remainingScore;}

    public void setRemainingScore(int remainingScore) {this.remainingScore = remainingScore;}

    public CheckoutRule getCheckoutRule() {return checkoutRule;}

    public boolean gameFinished(){
        return remainingScore == 0;
    }

}
