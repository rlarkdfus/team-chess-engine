package ooga.model;

import java.util.List;

public interface MoveVectorInterface {
    List<Vector> getMoveVectors();
    List<Vector> getTakeVectors();
    List<Vector> getInitialVectors();
    int getRowVector(int index);

    int getColVector(int index);
}
