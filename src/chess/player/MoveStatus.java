package chess.player;

public enum MoveStatus {
    ILLEGAL_MOVE {
        @Override
        public boolean isDone() {
            return false;
        }
    },
    DONE {
        @Override
        public boolean isDone() {
            return true;
        }
    },
    LEAVES_PLAYER_IN_CHECK {
        @Override
        public boolean isDone() {
            return false;
        }
    };
    public abstract boolean isDone();
}
