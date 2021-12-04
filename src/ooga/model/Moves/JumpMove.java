package ooga.model.Moves;

public class JumpMove extends TranslationMove {
    /**
     * jump move to go directly to a location
     * @param dRow delta row
     * @param dCol delta col
     * @param take move takes
     * @param limited move is limited
     */
    public JumpMove(int dRow, int dCol, boolean take, boolean limited) {
        super(dRow, dCol, take, limited);
    }
}
