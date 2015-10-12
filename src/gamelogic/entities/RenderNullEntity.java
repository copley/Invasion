package gamelogic.entities;

import gamelogic.CardinalDirection;

import java.awt.Image;
import java.awt.Point;

public class RenderNullEntity extends RenderCarryable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7779631880041938902L;
	private Point offset = new Point();
	//textual desc
	private static final String INV_IMAGE_TEXTUAL_DESC = "this slot is empty";
	//image file names:
	private static final String INV_IMAGE_FILE_NAME = "maxchangethislol IDK THO THIS NEVER ACTUALLY DRAWS SO DO WATEV";
	private static final String GAME_IMAGE_NAME = "joelychangethislol";


	public RenderNullEntity(CardinalDirection directionFacing){
		super(directionFacing, INV_IMAGE_TEXTUAL_DESC, INV_IMAGE_FILE_NAME, GAME_IMAGE_NAME);
		offset.x = 0;
		offset.y = 0;
	}

	@Override
	public Image getImg() {
		throw new RuntimeException("cannot do thisyet fame");
	}

	@Override 
	public Point getOffset(){
		return offset;
	}

}
