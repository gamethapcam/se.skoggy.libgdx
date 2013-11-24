package se.skoggy.entity;

import java.util.ArrayList;
import java.util.List;

import se.skoggy.entity.Transform;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Entity implements Disposable{

	private TextureRegion textureRegion;
	public Transform transform;
	public Vector2 origin;
	protected List<EntityBehavior> behaviors;
	protected boolean flipX;
	
	public Entity(TextureRegion texture){
		textureRegion = texture;
		transform = new Transform();
		origin = new Vector2();
		flipX = false;
		setSource(0,  0,  texture.getTexture().getWidth(),  texture.getTexture().getHeight());	
		
		behaviors = new ArrayList<EntityBehavior>();
	}
		
	public void addBehavior(EntityBehavior behavior){
		behaviors.add(behavior);
	}
	
	public void setFlipX(boolean flipX){
		this.flipX = flipX;
		textureRegion.flip(flipX, textureRegion.isFlipY());
	}
	
	@Override
	public void dispose() {
	}
	
	public void setSource(int x, int y, int width, int height){
		textureRegion.setRegion(x, y, width, height);
		origin.x =  width * 0.5f;
		origin.y = height * 0.5f;
		textureRegion.flip(flipX,  true);
	}
		
	public void update(float dt){
		for (EntityBehavior b : behaviors) {
			b.Update(dt, this);
		}
	}
	
	public void draw(SpriteBatch spriteBatch){
		for (EntityBehavior b : behaviors) {
			b.BeforeDraw(spriteBatch, this);
		}
				
		spriteBatch.draw(
				textureRegion, 
				transform.position.x - origin.x,
				transform.position.y - origin.y, 
				origin.x,
				origin.y,
				textureRegion.getRegionWidth(), 
				textureRegion.getRegionHeight(), 
				transform.scale.x, 
				transform.scale.y, 
				(float)(transform.rotation * 180f / Math.PI));
		
		for (EntityBehavior b : behaviors) {
			b.AfterDraw(spriteBatch, this);
		}
	}	
}