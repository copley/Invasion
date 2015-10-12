package gamelogic;

import gamelogic.entities.IndependentActor;
import gamelogic.entities.PylonAttackerStrategy;
import gamelogic.events.PlayerEvent;

import java.util.ArrayList;
import java.util.HashMap;

import javax.management.RuntimeErrorException;

/**
 * this class manages at a high level what the enemy entities in the game should be doing on every tick.
 * when it receives the tick/pulse, it scrapes the event buffer of each of the EnemyMaster instances (which it has stored in a collection).
 * These events are placed in a list and then returned back to the tick method in the server where they are applied to the game state
 *
 * Note that when an enemy "dies" in the game, that object is not really destroyed, its state just changes. e.g. if a player kills a zombie in room1, that zombie might have its "status"
 * field set to "dead" and then be moved to "respawn" in some other room.
 *
 *
 * SHOULD PERHAPS HAVE METHODS FOR ADDING/REMOVING ENTITIES IF THAT IS REQUIRED
 * @author brownmax1
 *
 */
public class IndependentActorManager {

	private final HashMap<Integer, IndependentActor> npcs;

	private int pylonAttackerCount = 0;//the amount of pylonAttackers currently managed
	private boolean attackTopPylonNext = true; //used to determine where to spawn the next wave of pylon attackers
	private final int TOP_PYLON_ROOM_ID = 0;
	private final int BOTTOM_PYLON_ROOM_ID = 5;


	private final WorldGameState trueWorldGameState;

	public IndependentActorManager( WorldGameState initialState){
		//set fields
		this.npcs = new HashMap<Integer, IndependentActor>();
		this.trueWorldGameState = initialState;


		//PUT THE PRE-DEFINED STARTUP ENTITIES INTO THE GAME
		//create startup entities
		this.initialiseStartupNpcs();


		//TODO: PROB THIS CLASS NEEDS A RUN METHOD THAT CREATES NEW ENTITIES WHEN THE COUNT GETS LOW.
		//else a better solution probably just to use the method in here that gets called on the tick
	}


	//used by setup to pouplate enemies map
	private void initialiseStartupNpcs(){
	//create pylon attacker wave (at the moment that is the enemies at the start)
		createPylonAttackerWave();
	}



	//ENEMY "WAVE" CREATION METHODS



	//USED TO CREATE A WAVE OF PYLON ATTACKERS IN THE PYLON ROOM THAT IS TO BE ATTACKED
	private void createPylonAttackerWave(){
		//determine which of the two pylon rooms this wave is for
		PylonRoomState roomToAttack;
		if(this.attackTopPylonNext){
			roomToAttack = ((PylonRoomState) this.trueWorldGameState.getRooms().get(TOP_PYLON_ROOM_ID));
		}else{//in the case that we attacking bottom room
			roomToAttack = ((PylonRoomState) this.trueWorldGameState.getRooms().get(BOTTOM_PYLON_ROOM_ID));
		}

		//create a pylon attacker above, below, left, right of the pylon

		//top
		IndependentActor top = new IndependentActor(CardinalDirection.SOUTH, 10, roomToAttack); //TODO: maintain an id allocator that goes up to like 500 and then resets back to 0
		PylonAttackerStrategy topStrat = new PylonAttackerStrategy(top, CardinalDirection.SOUTH);
		//bottom
		IndependentActor bottom = new IndependentActor(CardinalDirection.NORTH, 11, roomToAttack); //TODO: maintain an id allocator that goes up to like 500 and then resets back to 0
		PylonAttackerStrategy bottomStrat = new PylonAttackerStrategy(bottom, CardinalDirection.NORTH);
		//left
		IndependentActor left = new IndependentActor(CardinalDirection.EAST, 12, roomToAttack); //TODO: maintain an id allocator that goes up to like 500 and then resets back to 0
		PylonAttackerStrategy leftStrat = new PylonAttackerStrategy(left, CardinalDirection.EAST);
		//right
		IndependentActor right = new IndependentActor(CardinalDirection.WEST, 13, roomToAttack); //TODO: maintain an id allocator that goes up to like 500 and then resets back to 0
		PylonAttackerStrategy rightStrat = new PylonAttackerStrategy(right, CardinalDirection.WEST);//alternatively could just reuse the same ids and only respawn a wave when they all dead or some shit

		//we created all of our pylon attackers, so now attempt to place them all in the correct room
		//and then add the ones that were placed to our maps

		//add attackers to wave map
		HashMap<Integer, IndependentActor> waveMap = new HashMap<Integer, IndependentActor>();
		waveMap.put(top.getUniqueId(), top);
		waveMap.put(bottom.getUniqueId(), bottom);
		waveMap.put(left.getUniqueId(), left);
		waveMap.put(right.getUniqueId(), right);

		//attempt to spawn the attackers in the room, add the successfully spawned ones to our maps
		HashMap<Integer, IndependentActor> spawnedAttackers;
		spawnedAttackers = roomToAttack.spawnPylonAttackerWave(waveMap);


		//we might not have successfully spawned all of the attackers we made (e.g. if someone in the way of spawn area)
		//so add the attackers that were spawned successfully to the npcs map here and the MovableEntity map in the worldgamestate
		this.npcs.putAll(spawnedAttackers);
		for(IndependentActor each: spawnedAttackers.values()){
			this.trueWorldGameState.addMovableToMap(each);
		}
		//we added a certain amount of npcs to the game, so increment our count of them
		this.pylonAttackerCount += spawnedAttackers.size();

		//now our wave of pylon attackers has spawned and they should start generating events
		for(IndependentActor each: spawnedAttackers.values()){
			each.beginAi();
		}
	}



	/**
	 * used to start the thread which runs inside each individual enemy's AI thread object
	 * when this is called, the enemies will start generating their events so that this object can be scraped to collect all of their desired events.
	 * Note that we are not going to applying those events until the "main" ClockThread starts sending out pulses to actually scrape the enemies/players.
	 */
	public void startStartupIndependentEntities(){
		for(IndependentActor eachEnemy: this.npcs.values()){
			eachEnemy.beginAi();
		}
	}















	/**
	 * is called on clock tick to return a list of the events from all of the enemies
	 * @return List the list of events from the enemies that need to be applied
	 */
	ArrayList<PlayerEvent> retrieveEnemyEventsOnTick(){
		//create the list that we will fill with events
		ArrayList<PlayerEvent> enemyEvents = new ArrayList<PlayerEvent>(0);//initialise the list to 0 size that it will only be filled with added events

		//scrape the enemy events IF THEY ACTUALLY HAVE EVENTS THAT IS
		for(IndependentActor eachEnemy: this.npcs.values()){
			if(eachEnemy.hasEvent()){
				enemyEvents.add(eachEnemy.scrapeEnemyEvent());
			}

		}
		//TODO: debug lose efficeiny delte this shit
	/*	for(PlayerEvent eachEvent: enemyEvents){
			System.out.println("event generated by the ai: " + eachEvent.getUid() + " " + eachEvent);
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

		//return the events to be added to the queue of events that will be applied to game state on this tick
		return enemyEvents;
	}

	private void testInvariant(){
		assert(this.npcs.size() == this.pylonAttackerCount):"should not be managing more than count of added/removed ais";
	}

}
