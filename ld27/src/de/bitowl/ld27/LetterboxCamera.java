package de.bitowl.ld27;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * a camera that keeps aspect ratio by adding a border
 * 
 * most code like in my adventcalendar tutorial 
 * http://bitowl.de/day15/
 * 
 * @author bitowl
 *
 */
public class LetterboxCamera extends OrthographicCamera{
    Rectangle viewport;
    float virtualWidth=800;
    float virtualHeight=600;
    float virtualAspectRatio=virtualWidth/virtualHeight;
    public LetterboxCamera(float pWidth,float pHeight){
        super(pWidth,pHeight);
        viewport=new Rectangle();
        virtualWidth=pWidth;
        virtualHeight=pHeight;
    }
    public void resize(int width,int height){
        float aspectRatio=width/(float)height;

        float scale=1;
        float cropX=0;
        float cropY=0;

        // calculate scale and size of the letterbox
        if(aspectRatio > virtualAspectRatio){
            scale = (float)height/(float)virtualHeight;
            cropX = (width - virtualWidth*scale)/2f;
        }else if(aspectRatio < virtualAspectRatio){
            scale = (float)width/(float)virtualWidth;
            cropY = (height - virtualHeight*scale)/2f;
        }else{
            scale = (float)width/(float)virtualWidth;
        }

        // set the viewport
        viewport.set(cropX,cropY,virtualWidth*scale,virtualHeight*scale);
    }
    public void setViewport(){
        // set viewport
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
                                  (int) viewport.width, (int) viewport.height);
    }
    /**
     * unprojects screencoordinates into space
     * @param pX
     * @param pY
     * @return
     */
    public Vector3 unproject(float pX,float pY){
        if(Gdx.input.getX()>viewport.x&&Gdx.input.getX()<viewport.x+viewport.width&&Gdx.input.getY()>viewport.y&&Gdx.input.getY()<viewport.y+viewport.height){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            unproject(touchPos,viewport.x,viewport.y,viewport.width,viewport.height);
            return touchPos;
        }
        return null;
    }
}