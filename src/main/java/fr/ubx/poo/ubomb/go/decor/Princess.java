package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.Takeable;
import fr.ubx.poo.ubomb.go.character.Player;
import javafx.application.Platform;

public class Princess extends Decor implements Takeable {
    public Princess(Game game, Position position) {
        super(game, position);
    }

    public Princess(Position position) {
        super(position);
    }

    public void takenBy(Player player) {
        Platform.exit();
        System.out.println("I've the princess I win");
        System.exit(0);
    }
}
