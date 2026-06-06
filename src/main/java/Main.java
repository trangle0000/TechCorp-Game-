cat > src/main/java/Main.java << 'EOF'
import engine.GameEngine;
import constants.GameConstants;

public class Main {
    public static void main(String[] args) {
        GameEngine engine = new GameEngine(
            GameConstants.STARTING_BUDGET,
            GameConstants.AI_STARTING_BUDGET,
            GameConstants.GAME_ROUNDS
        );
        engine.runGame();
    }
}
EOF