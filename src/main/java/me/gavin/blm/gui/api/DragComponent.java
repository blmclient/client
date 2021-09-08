package me.gavin.blm.gui.api;

public abstract class DragComponent extends Component {
    public DragComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    protected boolean dragging = false;
    protected int dragX, dragY;

    protected void startDragging(int mouseX, int mouseY) {
        dragging = true;
        dragX = mouseX - x;
        dragY = mouseY - y;
    }

    protected void stopDragging() {
        dragging = false;
    }

    protected void updateDragPos(int mouseX, int mouseY) {
        if (dragging) {
            x = (mouseX - dragX);
            y = (mouseY - dragY);
        }
    }
}
