package gamelogic.entities;

import gamelogic.CardinalDirection;
import gamelogic.Traversable;

//some kind of item that is used to create teleporters. note that the only strategy
//that can use this item atm is the Sorcerer Strategy
public class TeleporterGun extends Carryable implements Traversable{

	public TeleporterGun(CardinalDirection directionFacing) {
		super(directionFacing, 9); //can only be carried at top level
	}

	@Override
	void checkIfPickingUpThisItemChangesPlayerState(Player pickUpPlayer) {
		assert(pickUpPlayer != null);
		//some player just picked these up
		this.setCurrentHolder(pickUpPlayer);
		//make sure the picking up player has night vision
		pickUpPlayer.setHasTeleGun(true);
	}

	@Override
	void checkIfDroppingThisItemChangesPlayerState(Player droppingPlayer) {
		assert(droppingPlayer != null);
		//some player just picked these up
		this.setCurrentHolder(null);
		//make sure the picking up player has night vision
		droppingPlayer.setHasTeleGun(false);
		
	}

	@Override
	public RenderEntity generateDrawableCopy() {
		return new RenderTeleporterGun(CardinalDirection.NORTH);
	}

}