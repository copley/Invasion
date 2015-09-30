package gamelogic.events;


public class PlayerEvent {

	protected final int uid;

	public PlayerEvent(int uid) {
		this.uid = uid;
	}

	public int getUid() {
		return this.uid;
	}

}