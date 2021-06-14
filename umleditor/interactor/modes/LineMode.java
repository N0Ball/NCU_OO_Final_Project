package umleditor.interactor.modes;

import java.awt.Point;

import umleditor.entity.UMLObject;
import umleditor.interactor.CanvasInteractor;

public class LineMode extends Mode{

    private UMLObject.Base startShape;
    private UMLObject.Base startPort;
    private UMLObject.Base endPort;
    private MouseBehavior mouseBehavior;
    private UMLObject.Line currentLine; 

    public LineMode(CanvasInteractor interactor) {
        super("LINE MODE", interactor);
    }

    @Override
    public void onPressed(Point pt) {

        startShape = INTERACTOR.getCollideObject(pt);
        if (startShape != null){
            mouseBehavior = new SelectObject();
        }else{
            mouseBehavior = new SelectNull();
        }

        mouseBehavior.onPressed(pt);
    }

    @Override
    public void onDragged(Point pt) {
        mouseBehavior.onDragged(pt);
    }

    @Override
    public void onReleased(Point pt) {
        mouseBehavior.onReleased(pt);
    }

    abstract private class MouseBehavior{

        abstract public void onPressed(Point pt);
        abstract public void onDragged(Point pt);
        abstract public void onReleased(Point pt);

    }

    private class SelectObject extends MouseBehavior{

        @Override
        public void onPressed(Point pt) {
            startPort = startShape.getPort(INTERACTOR.getCollidePort(startShape, pt));
            currentLine = (UMLObject.Line) INTERACTOR.createObject();
            currentLine.setConnection(startPort, startPort);
        }

        @Override
        public void onDragged(Point pt) {
            currentLine.setLocation(startPort.getLocation(), pt);
        }

        @Override
        public void onReleased(Point pt) {
            endPort = INTERACTOR.getCollideObject(pt);
            if (endPort == null || endPort == startShape){
                INTERACTOR.deleteObject(currentLine);
            }else{
                endPort = endPort.getPort(INTERACTOR.getCollidePort(endPort, pt));
                currentLine.setConnection(startPort, endPort);
                currentLine.set();
            }
        }

    }

    private class SelectNull extends MouseBehavior{

        @Override
        public void onPressed(Point pt) {}

        @Override
        public void onDragged(Point pt) {}

        @Override
        public void onReleased(Point pt) {}

    }
    
}
