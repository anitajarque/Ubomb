package fr.ubx.poo.ubomb.go.decor.bonus.bomb;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.Takeable;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.decor.bonus.Bonus;

public class BombNumberInc extends Bomb{
    public BombNumberInc(Position position) {
        super(position);
    }

    @Override
    public void takenBy(Player player) {
        if(player.getNumBombs()< player.getBombBagCapacity()){
            super.takenBy(player);
        }
    }
}