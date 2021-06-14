package umleditor.interactor.modes;

import java.awt.Point;

import umleditor.interactor.CanvasInteractor;

public class SelectMode extends Mode{

    private Object target; 
    private MouseBehavior mouseBehavior;

    public SelectMode(CanvasInteractor interactor) {
        super("SELECT MODE", interactor);
    }

    @Override
    public void onPressed(Point pt) {
        
        target = this.INTERACTOR.getCollideObject(pt);
        if (target != null){
            mouseBehavior = new SelectObject(target);
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

        protected Point prevPt;

        protected Object target;

        public MouseBehavior(Object target){
            this.target = target;
        }

        abstract public void onPressed(Point pt);
        abstract public void onDragged(Point pt);
        abstract public void onReleased(Point pt);

    }

    private class SelectObject extends MouseBehavior{

        public SelectObject(Object target) {
            super(target);
        }

        @Override
        public void onPressed(Point pt) {
            prevPt = pt;
            INTERACTOR.deselectAll();
            INTERACTOR.select(target);
        }

        @Override
        public void onDragged(Point pt) {
            int dx = pt.x - prevPt.x;
            int dy = pt.y - prevPt.y;
            INTERACTOR.getObject(target).Move(dx, dy);
            prevPt = pt;
        }

        @Override
        public void onReleased(Point pt) {}
        
    }

    private class SelectNull extends MouseBehavior{

        private int xCorner;
        private int yCorner;
        private int width;
        private int height;

        public SelectNull() {
            super(null);
        }

        @Override
        public void onPressed(Point pt) {
            prevPt = pt;
            INTERACTOR.deselectAll();
            target = INTERACTOR.createObject();
            INTERACTOR.getObject(target).setLocation(pt.x, pt.y);
        }

        @Override
        public void onDragged(Point pt) {
            xCorner = Math.min(pt.x, prevPt.x);
            yCorner = Math.min(pt.y, prevPt.y);
            width = Math.abs(pt.x - prevPt.x);
            height = Math.abs(pt.y - prevPt.y);
            INTERACTOR.getObject(target).setLocation(xCorner, yCorner);
            INTERACTOR.getObject(target).setSize(width, height);
        }

        @Override
        public void onReleased(Point pt) {

            INTERACTOR.deselectAll();

            INTERACTOR.getCollideArea(
                new Point(xCorner, yCorner), 
                new Point(xCorner + width, yCorner + height)
            ).forEach( e -> {e.select();});

            INTERACTOR.deleteObject(target);
        }

    }
    
}
