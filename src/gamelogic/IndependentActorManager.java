package gamelogic;

import gamelogic.entities.IndependentActor;
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

	private final HashMap<Integer, IndependentActor> enemies;
	
	private final WorldGameState trueWorldGameState;

	public IndependentActorManager(HashMap<Integer, IndependentActor> enemies, WorldGameState initialState){
		if(enemies.size() > 1){
			throw new RuntimeException("note that in reality we cannot give the enemies as an argument to the constructor and just spawn them. we should just make this class maintain the correct amount of entities and choose where to spawn them. (ELSE you could just spawn them all initially and then when they DIE move them somewhere else but that method has less freedom");
		}
		this.enemies = enemies;
		this.trueWorldGameState = initialState;
		//add the enemies to the board state
		for(IndependentActor eachActor: this.enemies.values()){
			if(!(this.trueWorldGameState.addMovableEntityToRoomState(eachActor, 0, 5, 10))){ //obvs cant spawn them all in same place
				throw new RuntimeException("failed to spawn the actor in the roomm");
			}
			//actually add the entity to the entity map
			this.trueWorldGameState.addMovableToMap(eachActor);
		}
	}

	/**
	 * used to start the thread which runs inside each individual enemy's AI thread object
	 * when this is called, the enemies will start generating their events so that this object can be scraped to collect all of their desired events.
	 * Note that we are not going to applying those events until the "main" ClockThread starts sending out pulses to actually scrape the enemies/players.
	 */
	public void startIndependentEntities(){
		for(IndependentActor eachEnemy: this.enemies.values()){
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
		for(IndependentActor eachEnemy: this.enemies.values()){
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



}
