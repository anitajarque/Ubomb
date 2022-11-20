package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;

public class Heart extends Decor{
    public Heart(Game game, Position position) {
        super(game, position);
    }

    public Heart(Position position) {
        super(position);
    }
}
