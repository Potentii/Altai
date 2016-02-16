package view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import view.listview.GridViewAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.function.Supplier;

/**
 * @author Guilherme Reginaldo
 * @since 28/01/2016
 */
public class GridView<E> extends ScrollPane {
    @FXML
    private TilePane tilePane;


    private double cellWidth = 124;
    private double cellHeight = 124;

    private int exceedRows = 0;
    private int exceedColumns = 0;

    private double spaceBetween;

    private int totalCapacity;

    @Nullable
    private E selectedItem;
    @NotNull
    private List<E> itemList;

    private List<GridViewAdapter<E>> cellList;

    private Supplier<GridViewAdapter<E>> cellSupplier;
    private EventHandler<? super MouseEvent> onCellClickListener;

    private boolean waiting = false;
    private Bounds lastBounds;
    private double lastVValue;
    private Range visibleItemRange;
    private int capacityPerRow;
    private int capacityPerColumn;


    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public GridView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/control/control_grid_view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();


            // *Fields initialization:
            cellList = new Vector<>();
            itemList = new ArrayList<>();
            visibleItemRange = new Range(0, 0);
            lastTotalCapacity = 0;

            // *Setting bounds listener:
            layoutBoundsProperty().addListener(observable -> {
                if(!waiting){
                    waiting = true;
                    lastBounds = getLayoutBounds();
                    new Thread(() -> {
                        try {
                            Thread.sleep(333);
                            Bounds bounds = getLayoutBounds();
                            //System.out.println("boundsH = " + bounds.getHeight());
                            //System.out.println("boundsW = " + bounds.getWidth());
                            //System.out.println();
                            if(!bounds.equals(lastBounds)) {
                                Platform.runLater(() -> onSizeChanged(bounds));
                            }
                            waiting = false;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            });


            // *Setting scroll listener:
            vvalueProperty().addListener(observable -> onScrollChanged(getVvalue()));


        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    private int lastTotalCapacity;
    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Class methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    /*
    TODO possible logic:

    - Semi-lazy:
        When "setItems" is called externally, it adds the proper number of children on TilePane, but it doesn't calls the cell's bind method (It's important so the scroll works correctly by default);
            Or maybe just an empty node, or even an 'empty calculated space' (If I just decide to implement with the empty space technique, it turns into the real lazy loading);
                I can simulate this 'empty space' by adding some padding;
        It updates the cell capacity when onSizeChanged is called;
        When the user scrolls, it determines from which to which cell it has to bind;
    */
    private synchronized void onSizeChanged(Bounds currentBounds){
        totalCapacity = getTotalCellCapacity(currentBounds.getHeight(), currentBounds.getWidth());


        if(lastTotalCapacity < totalCapacity) {
            for (int i = lastTotalCapacity; i < totalCapacity; i++) {
                GridViewAdapter<E> cell = cellSupplier.get();
                tilePane.getChildren().add(cell.getRoot());
                cellList.add(cell);
            }
        } else{
            for (int i = cellList.size() - 1; i >= lastTotalCapacity; i--) {
                tilePane.getChildren().remove(cellList.get(i).getRoot());
                cellList.remove(i);
            }
        }
        lastTotalCapacity = totalCapacity;

        updateBinding();
    }



    private int getCapacityPerRow(double width){
        return (int) ((width + spaceBetween) / (spaceBetween + cellWidth));
    }
    private int getCapacityPerColumn(double height){
        return (int) ((height + spaceBetween) / (spaceBetween + cellHeight));
    }
    private int getTotalCellCapacity(double height, double width){
        int capacityPerRow = getCapacityPerRow(width);
        int capacityPerColumn = getCapacityPerColumn(height);

        return  (capacityPerRow * capacityPerColumn)
                + ((exceedRows * 2) * capacityPerRow)
                + ((exceedColumns * 2) * capacityPerColumn);
    }





    private void onScrollChanged(double vValue){
        lastVValue = vValue;
        updateBinding();
    }



    private void updateBinding(){
        System.out.println("cells: " + cellList.size());
        double unShownTopHeight = (tilePane.getHeight() * lastVValue) - (getHeight() * lastVValue);
        double unShownBottomHeight = (tilePane.getHeight() * lastVValue) + (getHeight() * lastVValue);

        int cellQuantityAbove = getTotalCellCapacity(unShownBottomHeight, tilePane.getWidth());
        int cellsBindThisPass = 0;

        for (GridViewAdapter<E> cell : cellList) {
            Node root = cell.getRoot();
            Bounds bounds = root.getBoundsInParent();
            if(bounds.getMaxY() >= unShownTopHeight && bounds.getMinY() <= unShownBottomHeight){
                // *This node is visible:
                cell.bindData(itemList.get(cellQuantityAbove + cellsBindThisPass));
                cellsBindThisPass++;
            } else{
            }
        }
    }


    public void setItems(List<E> itemList){
        this.itemList = itemList;

        if(tilePane.getChildren().size() > 0) {
            tilePane.getChildren().remove(0, tilePane.getChildren().size());
        }

        double calculatedH = (this.itemList.size() * (cellHeight + spaceBetween)) - spaceBetween;
        setMinHeight(calculatedH);
        setMaxHeight(calculatedH);

        updateBinding();
    }


    public void addItem(E item){
        itemList.add(item);


    }

    private void bindItem(E item){
        GridViewAdapter<E> cellAdapter = cellSupplier.get();
        cellList.add(cellAdapter);
    }


    public void setCellFactory(Supplier<GridViewAdapter<E>> cellSupplier){
        this.cellSupplier = cellSupplier;
    }


    private void buildCell(E item){
        if(cellSupplier == null){
            return;
        }
        GridViewAdapter<E> cellAdapter = cellSupplier.get();
        cellList.add(cellAdapter);
        Node node = cellAdapter.updateItem(item);
        node.setOnMouseClicked(event -> {
            selectedItem = item;
            if(onCellClickListener != null) {
                onCellClickListener.handle(event);
            }
        });
        tilePane.getChildren().add(node);
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Getters and setters:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public void setOnCellClickListener(EventHandler<? super MouseEvent> event){
        onCellClickListener = event;
    }

    @Nullable
    public E getSelectedItem() {
        return selectedItem;
    }

    public double getCellSize(){
        return cellWidth > cellHeight ? cellWidth:cellHeight;
    }
    public void setCellSize(double cellSize){
        setCellWidth(cellSize);
        setCellHeight(cellSize);
    }

    public double getCellWidth() {
        return cellWidth;
    }
    public void setCellWidth(double cellWidth) {
        this.cellWidth = cellWidth;
        tilePane.setPrefTileWidth(cellWidth);
    }

    public double getCellHeight() {
        return cellHeight;
    }
    public void setCellHeight(double cellHeight) {
        this.cellHeight = cellHeight;
        tilePane.setPrefTileHeight(cellHeight);
    }

    public double getSpaceBetween() {
        return spaceBetween;
    }
    public void setSpaceBetween(double spaceBetween) {
        this.spaceBetween = spaceBetween;
        tilePane.setHgap(spaceBetween);
        tilePane.setVgap(spaceBetween);
    }




    private class Range{
        private int min;
        private int max;

        public Range(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getMin() {
            return min;
        }
        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }
        public void setMax(int max) {
            this.max = max;
        }
    }
}
