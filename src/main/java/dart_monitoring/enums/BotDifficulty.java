package dart_monitoring.enums;

public enum BotDifficulty {
    EASY(20, 50, 0.15),
    MEDIUM(40, 80, 0.30),
    HARD(60, 120, 0.50);

    private final int minScore;
    private final int maxScore;
    private final double checkoutChance;

    BotDifficulty(int minScore, int maxScore, double checkoutChance) {
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.checkoutChance = checkoutChance;
    }

    public int getMinScore() {
        return minScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public double getCheckoutChance() {
        return checkoutChance;
    }
}
