package fr.ubx.poo.ubomb.go.decor.bonus.bombNumber;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.Takeable;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.decor.bonus.Bonus;

public class BombNumber extends Bonus implements Takeable {
    public BombNumber(Position position) {
        super(position);
    }

    @Override
    public void takenBy(Player player) {
        player.take(this);
        remove();
    }
}
