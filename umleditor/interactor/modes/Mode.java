package umleditor.interactor.modes;

import java.awt.Point;

import umleditor.interactor.CanvasInteractor;

public abstract class Mode {

    private final String name;
    public final CanvasInteractor INTERACTOR;
    private int targetId; 
    
    public Mode(String name, CanvasInteractor interactor){
        this.INTERACTOR = interactor;
        this.name = name;
    }

    public int getTarget() { return targetId; }
    public String getMode () { return name; }

    public void setTarget(int objectId){
        this.targetId = objectId;
    }

    public abstract void onPressed(Point pt);
    public abstract void onDragged(Point pt);
    public abstract void onReleased(Point pt);

}
