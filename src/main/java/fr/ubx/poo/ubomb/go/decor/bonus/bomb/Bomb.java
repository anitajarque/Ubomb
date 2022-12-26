package fr.ubx.poo.ubomb.go.decor.bonus.bomb;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.Takeable;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.decor.bonus.Bonus;

public class Bomb extends Bonus implements Takeable {
    public Bomb(Position position) {
        super(position);
    }

    @Override
    public void takenBy(Player player) {
        player.take(this);
        remove();
    }
}
