package fr.ubx.poo.ubomb.go;

import fr.ubx.poo.ubomb.go.decor.bonus.*;

// Double dispatch visitor pattern
public interface TakeVisitor {
    // Key and hearth
    default void take(Key key) {}
    default void take(Heart heart){}
    

}
