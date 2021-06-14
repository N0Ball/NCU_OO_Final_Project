package umleditor.interactor.modes;

import java.awt.Point;

import umleditor.interactor.CanvasInteractor;

public class ObjectMode extends Mode{

    public ObjectMode(CanvasInteractor interactor) {
        super("OBJECT MODE", interactor);
    }

    @Override
    public void onPressed(Point pt) {
        INTERACTOR.createObject().setLocation(pt.x, pt.y);
    }

    @Override
    public void onDragged(Point pt) { }

    @Override
    public void onReleased(Point pt) { }
    
}
