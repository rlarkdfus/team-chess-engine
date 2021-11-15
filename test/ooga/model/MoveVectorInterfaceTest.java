package ooga.model;

import ooga.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveVectorInterfaceTest {
    MoveVectorInterface moveVector;
    @BeforeEach
    void setUp() {
        List<Vector> vectors = new ArrayList<>();
        vectors.add(new Vector(-1, 0));
        vectors.add(new Vector(1, 0));
        vectors.add(new Vector(0, 1));
        vectors.add(new Vector(0, -1));
        moveVector = new MoveVector(vectors, vectors, vectors);
    }

    @Test
    void getMoveVectors() {
        Assertions.assertEquals(moveVector.getMoveVectors().size(), 4);
    }

    @Test
    void getRowVector() {
        Assertions.assertEquals(moveVector.getRowVector(0), -1);
    }

    @Test
    void getColVector() {
        Assertions.assertEquals(moveVector.getColVector(0), 0);
    }
}