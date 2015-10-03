package main;

import gamelogic.CardinalDirection;
import gamelogic.ClockThread;
import gamelogic.IndependentActorManager;
import gamelogic.RoomState;
import gamelogic.Server;
import gamelogic.TankStrategy;
import gamelogic.WorldGameState;
import gamelogic.entities.GameEntity;
import gamelogic.entities.IndependentActor;
import gamelogic.entities.KeyCard;
import gamelogic.entities.NullEntity;
import gamelogic.entities.OuterWall;
import gamelogic.entities.Player;
import gamelogic.tiles.GameRoomTile;
import gamelogic.tiles.SpaceShipInteriorStandardTile;
import graphics.GameCanvas;

import java.util.HashMap;

import ui.GameGui;
import ui.GameSetUpWindow;
import control.DummySlave;
import control.Listener;

public class MainInit {

	public static void main(String[] args) {
		System.out.println("running this shit");
		System.out.println("im helping too");

		System.out
				.println("welcome to the program. There should be an initialisation dialog that is shown now that\n lets you create a server and then create a new player and join that server separately\n we will pretend that is done automagically for now so that i dont have to create those dialogs");

		//IMPORTANT NOTE ON CURRENT OVERALL SETUP IMPLEMENTATION:
		//at the moment I am just using a dummy slave and dummy master to simulate how the information flows throughout the program.
		//in reality, the process of connecting clients to the server will be quite different and the whole slave/master system will be more complex
		//we should probably make this connection sequence happen in similar way to pacman. I dont want to think about how that will work tho
		//will find out what's needed there from miguel


//SSEETTUUPP GGAAMMEE SSTTAATTEE SSHHIITT




///CREATE ROOM 1///

		//CREATE DUMMY VERSIONS OF THE 2D ARRAY THAT ARE USED TO CREATE THE DUMMY ROOM

		int width = 23;
		int height = 23;


		GameRoomTile[][] dummyTiles = new GameRoomTile[width][height];
		GameEntity[][] dummyEntities = new GameEntity[width][height];

		//fill up tha tiles
		for(int i = 0; i < width ; i++){
			for(int j = 0; j < height ; j++){
				dummyTiles[i][j] = new SpaceShipInteriorStandardTile();
			}
		}
		//fill up the entities with null
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height ; j++){


				dummyEntities[i][j] = new NullEntity(CardinalDirection.NORTH);//default for null entities is north

			}
		}

		//add the walls to edge locations
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				//insert walls at the top and bottom
				//insert walls at the top and bottom
				if( i == 0   ){//walls on right with standard orientation
					dummyEntities[i][j] = new OuterWall(CardinalDirection.WEST);
				}
				if(i == width - 1){//walls on the left with standard orientation
					dummyEntities[i][j] = new OuterWall(CardinalDirection.EAST);
				}
				if(j == 0 ){//walls up top
					dummyEntities[i][j] = new OuterWall(CardinalDirection.NORTH); //TODO: this should probably be set to something sensible. e.g. if directionFaced is SOUTH, then that wall looks like a "top of the map wall" from NORTH is up viewing perspective. if directionFaced is NORTH, then that wall looks like a bottom of the map wall from a NORTH is up viewing perspective.
				}
				if(j == height - 1){//walls at bottom
					dummyEntities[i][j] = new OuterWall(CardinalDirection.SOUTH);
				}
			}
		}


		RoomState DummyRoom1 = new RoomState(dummyTiles, dummyEntities, width, height, 0, false);


///CREATE ROOM 2///

		 width = 23;
		 height = 23;

		 dummyTiles = new GameRoomTile[width][height];
		 dummyEntities = new GameEntity[width][height];

		//fill up tha tiles
		for(int i = 0; i < width ; i++){
			for(int j = 0; j < height ; j++){
				dummyTiles[i][j] = new SpaceShipInteriorStandardTile();
			}
		}
		//fill up the entities with null
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height ; j++){


				dummyEntities[i][j] = new NullEntity(CardinalDirection.NORTH);//default for null entities is north

			}
		}



		//add the walls to edge locations
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				//insert walls at the top and bottom
				if( i == 0   ){//walls on right with standard orientation
					dummyEntities[i][j] = new OuterWall(CardinalDirection.WEST);
				}
				if(i == width - 1){//walls on the left with standard orientation
					dummyEntities[i][j] = new OuterWall(CardinalDirection.EAST);
				}
				if(j == 0 ){//walls up top
					dummyEntities[i][j] = new OuterWall(CardinalDirection.NORTH); //TODO: this should probably be set to something sensible. e.g. if directionFaced is SOUTH, then that wall looks like a "top of the map wall" from NORTH is up viewing perspective. if directionFaced is NORTH, then that wall looks like a bottom of the map wall from a NORTH is up viewing perspective.
				}
				if(j == height - 1){//walls at bottom
					dummyEntities[i][j] = new OuterWall(CardinalDirection.SOUTH);
				}
			}
		}






		//add the key card ent

				dummyEntities[2][2] = new KeyCard(0, CardinalDirection.NORTH);
				dummyEntities[2][3] = new KeyCard(0, CardinalDirection.NORTH);
				dummyEntities[2][4] = new KeyCard(0, CardinalDirection.NORTH);
				dummyEntities[2][5] = new KeyCard(0, CardinalDirection.NORTH);
				dummyEntities[2][6] = new KeyCard(0, CardinalDirection.NORTH);
				dummyEntities[2][7] = new KeyCard(0, CardinalDirection.NORTH);

		RoomState DummyRoom2 = new RoomState(dummyTiles, dummyEntities, width, height, 1, true);


		///CREATE ROOM 3///

		 width = 23;
		 height = 23;

		 dummyTiles = new GameRoomTile[width][height];
		 dummyEntities = new GameEntity[width][height];

		//fill up tha tiles
		for(int i = 0; i < width ; i++){
			for(int j = 0; j < height ; j++){
				dummyTiles[i][j] = new SpaceShipInteriorStandardTile();
			}
		}
		//fill up the entities with null
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height ; j++){


				dummyEntities[i][j] = new NullEntity(CardinalDirection.NORTH);//default for null entities is north

			}
		}



		//add the walls to edge locations
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				//insert walls at the top and bottom
				//insert walls at the top and bottom
				if( i == 0   ){//walls on right with standard orientation
					dummyEntities[i][j] = new OuterWall(CardinalDirection.WEST);
				}
				if(i == width - 1){//walls on the left with standard orientation
					dummyEntities[i][j] = new OuterWall(CardinalDirection.EAST);
				}
				if(j == 0 ){//walls up top
					dummyEntities[i][j] = new OuterWall(CardinalDirection.NORTH); //TODO: this should probably be set to something sensible. e.g. if directionFaced is SOUTH, then that wall looks like a "top of the map wall" from NORTH is up viewing perspective. if directionFaced is NORTH, then that wall looks like a bottom of the map wall from a NORTH is up viewing perspective.
				}
				if(j == height - 1){//walls at bottom
					dummyEntities[i][j] = new OuterWall(CardinalDirection.SOUTH);
				}
			}
		}








		//add the key card ent

				dummyEntities[2][2] = new KeyCard(0, CardinalDirection.NORTH);


		RoomState DummyRoom3 = new RoomState(dummyTiles, dummyEntities, width, height, 2, false);


		///CREATE ROOM 4///

		 width = 23;
		 height = 23;

		 dummyTiles = new GameRoomTile[width][height];
		 dummyEntities = new GameEntity[width][height];

		//fill up tha tiles
		for(int i = 0; i < width ; i++){
			for(int j = 0; j < height ; j++){
				dummyTiles[i][j] = new SpaceShipInteriorStandardTile();
			}
		}
		//fill up the entities with null
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height ; j++){


				dummyEntities[i][j] = new NullEntity(CardinalDirection.NORTH);//default for null entities is north

			}
		}



		//add the walls to edge locations
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				//insert walls at the top and bottom
				if( i == 0   ){//walls on right with standard orientation
					dummyEntities[i][j] = new OuterWall(CardinalDirection.WEST);
				}
				if(i == width - 1){//walls on the left with standard orientation
					dummyEntities[i][j] = new OuterWall(CardinalDirection.EAST);
				}
				if(j == 0 ){//walls up top
					dummyEntities[i][j] = new OuterWall(CardinalDirection.NORTH); //TODO: this should probably be set to something sensible. e.g. if directionFaced is SOUTH, then that wall looks like a "top of the map wall" from NORTH is up viewing perspective. if directionFaced is NORTH, then that wall looks like a bottom of the map wall from a NORTH is up viewing perspective.
				}
				if(j == height - 1){//walls at bottom
					dummyEntities[i][j] = new OuterWall(CardinalDirection.SOUTH);
				}
			}
		}








		//add the key card ent

				dummyEntities[2][3] = new KeyCard(0, CardinalDirection.NORTH);


		RoomState DummyRoom4 = new RoomState(dummyTiles, dummyEntities, width, height, 3, true);



		//spawn some teleporters IN THE ROOMS
		DummyRoom2.spawnTeleporter(CardinalDirection.NORTH, 5, 5, 8, 8, DummyRoom1);// 2 -> 1
		DummyRoom1.spawnTeleporter(CardinalDirection.NORTH, 5, 5, 3, 3, DummyRoom2); //1 -> 2
		DummyRoom2.spawnTeleporter(CardinalDirection.NORTH, 5, 5, 3, 6, DummyRoom3); //2-> 3
		DummyRoom3.spawnTeleporter(CardinalDirection.NORTH, 2, 2, 2, 2, DummyRoom4); //3 ->4
		DummyRoom4.spawnTeleporter(CardinalDirection.NORTH, 3, 3, 4, 4, DummyRoom1); //4 ->1





//create the rooms collection which we will be giving to the world game state

		HashMap<Integer, RoomState> rooms = new HashMap<>();
		rooms.put(0, DummyRoom1);
		rooms.put(1, DummyRoom2);
		rooms.put(2, DummyRoom3);
		rooms.put(3, DummyRoom4);



		//CREATE THE WORLD GAME STATE FROM THE ROOMS WE MADE
		WorldGameState initialState = new WorldGameState(rooms);//this initial state would be read in from an xml file (basically just rooms i think)


		//CREATE THE ENEMIES FOR THE SERVER would prob be done in the actual server constructor
		HashMap<Integer, IndependentActor> enemyMapSet = new HashMap<>();
		enemyMapSet.put(10, new IndependentActor(CardinalDirection.NORTH, 10));
		enemyMapSet.put(11, new IndependentActor(CardinalDirection.NORTH, 11));
		enemyMapSet.put(12, new IndependentActor(CardinalDirection.NORTH, 12));
		
		IndependentActorManager enemyManager = new IndependentActorManager(enemyMapSet, initialState); //incredibly important that ids for zombies will not conflict with ids from players as they both share the MovableEntity map in the worldgamestate object.
		


	


		//CREATE SERVER FROM THE GAME STATE WE MADE
		Server theServer = new Server(initialState, enemyManager); //this init state will be read in from xml or json or watev




		









//PPLLAAYYEERR''SS SSHHIITT.

	//CREATE A PLAYER AND ADD IT TO THE SERVER
		Player myPlayer = new Player("CoolMax;);)", 0, new TankStrategy(), CardinalDirection.NORTH); //name, uid, spawnroom SETTING THE PLAYER TO FACE NORTH
	//	todo:
	/*		1)make sure in set up that all the movable entities being added to the worldgamestate and having internal fields set and placed in the map in there
			1.5) review consistency of ids used. should use 10->20 range for ais. use 1 and 2 for players u fucked up using 0
			2) test all this ai shit out fam
			3)implement one of the easy af game object like key or light or s/t
			4)nwen fam*/
	
		//CREATE THE GUI AND CANVAS SHITS
			
		GameGui topLevelGui = new GameGui(new GameCanvas());


		//CREATE A SLAVE AND CONNECT IT TO THE SERVER WHICH MAKES A MASTER FOR THEM
		DummySlave mySlave = new DummySlave(0, topLevelGui); //the uid of the dummy player we are using in this hacky shit



		//INIT THE LISTENER
		Listener theListener = new Listener(topLevelGui, new GameSetUpWindow(), mySlave);

		
		//add the player to the game state. the enemies are registered via the actor manager
		theServer.registerPlayerWithGameState(myPlayer);

		//connect the slave to the server which creates/spawns the player too
		mySlave.connectToServer(theServer);
		
		
		

		/*some good shit here vv idk what alternative to tick/overflow counter for player actings is
		can test it out i guess
		id say just use main clock and make sure that it is always exactly consistent a nd a reasonable frame rate and then just use that for how often we can perform events. taht way we dont need a "EventCoolDown" clock on both server and client side too...
best thing to do now is to create the basic AI enemies that just move up and down continuosly
then from there you can start working on attacking/health/ability/character  strategies (engineer and fighter)

note: imo the AI cannot submit an event to the server on every single tick because that is too often, they would be moving way more than the players.
need to enfore like a strict 30 fps frame rate which means we need the clock interval to be 33 i.e. 1000 milliseconds per second /  30 frames we want per second.
so we need interval to be 33ms - amountOfMsNeededToapplyoureventtogamestate. We can make this consistent by taking system time millis before and after we apply change to game state.
i think it will be so miniscule that we can probably just ignore this and enforce the 33 ms delay between ticks.

regardless of how absolutely even our tick interval is (atm it only ticks every 50 ms or watev but because it has to apply game events before sending out redraws, the redraw "frame rate" is inconsistent)
, we will need some kind of counter in the AIs (AND MAYBE USE SIMILAR THING IN CLIENTS TO DETERMINE WHEN THEY CAN MOVE AGAIN) That only allows the ai's to offer another event to be enqued when their count reaches like 10
or something (the counter would overflow back to 0 when it gets to 10 in that case). This way the ai would only be able to perform an event 3 times per second (still quite a lot)
presuming that we had 30 fps of course.

not that keen on doing it that way because that means that the speed between events that entities can perform is determined by the clokcspeed. That's not that cool cause u get
shit like in snowball where u increase tick rate and suddenly scores go up faster. idk might be easiest way tho.*/


		//...AND START THE CLOCK SO THAT THE SERVER SENDS THINGS BACK ON TICK
		//start the inependent ents threads?
				enemyManager.startIndependentEntities();
		ClockThread clock = new ClockThread(35, theServer);
		clock.start();




	}



}
