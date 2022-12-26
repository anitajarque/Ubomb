package fr.ubx.poo.ubomb.go;

import fr.ubx.poo.ubomb.go.decor.bonus.*;
import fr.ubx.poo.ubomb.go.decor.bonus.bomb.Bomb;
import fr.ubx.poo.ubomb.go.decor.bonus.bombRange.BombRange;

// Double dispatch visitor pattern
public interface TakeVisitor {
    // Key and hearth
    default void take(Key key) {}
    default void take(Heart heart){}
    default void take(BombRange bombRange){}
    default void take(Bomb bomb){}

}
