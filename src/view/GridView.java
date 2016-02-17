package view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import view.listview.GridViewAdapter;

import java.io.IOException;
import java.util.*;
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

    private double spaceBetween;

    @Nullable
    private E selectedItem;
    @NotNull
    private List<E> itemList;

    private Supplier<GridViewAdapter<E>> cellSupplier;
    private EventHandler<? super MouseEvent> onCellClickListener;

    private List<GridViewAdapter<E>> cellList;

    private int cellsPerRow;
    private boolean waiting;



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
            itemList = new ArrayList<>();
            cellList = new ArrayList<>();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Listener methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    private void onViewPortSizeChanged(){
        cellsPerRow = getCellsPerRow(tilePane.getWidth());
        reBind();
    }


    private synchronized void reBind(){
        if(!waiting){
            waiting = true;
            new Thread(() -> {
                try {
                    Thread.sleep(333);
                    Platform.runLater(() -> {
                        double viewPortHeight = getHeight();
                        double vValue = getVvalue();
                        double beforeGap = (tilePane.getHeight() * vValue) - (viewPortHeight * vValue);

                        // *Calculating the visible cells:
                        int startIndex = cellsPerRow * getCellsPerColumn(beforeGap);
                        int endIndex = startIndex + (cellsPerRow * getCellsPerColumn(viewPortHeight));

                        // *Binding the data on each cell:
                        for (int i = startIndex; i <= endIndex; i++) {
                            try {
                                GridViewAdapter<E> cell = cellList.get(i);
                                E data = itemList.get(i);

                                // *If it's not a re-binding:
                                if(!data.equals(cell.getData())) {
                                    cell.updateItem(data);
                                }
                            } catch (IndexOutOfBoundsException e){}
                        }
                    });
                    waiting = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Class methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public void setItems(List<E> itemList){
        if(cellSupplier == null){
            return;
        }
        this.itemList = itemList;
        cellList.clear();


        // *Adding the items:
        itemList.forEach(item -> {
            GridViewAdapter<E> cellAdapter = cellSupplier.get();
            Node cellRoot = cellAdapter.getRoot();
            cellRoot.setOnMouseClicked(event -> {
                selectedItem = item;
                if(onCellClickListener != null){
                    onCellClickListener.handle(event);
                }
            });
            cellList.add(cellAdapter);
            tilePane.getChildren().add(cellRoot);
        });


        // *Adding the listeners:
        onViewPortSizeChanged();
        widthProperty().addListener(observable -> onViewPortSizeChanged());
        heightProperty().addListener(observable -> reBind());
        vvalueProperty().addListener(event -> reBind());
    }

    /**
     * Calculates how much cells needed to fill an row with some specified width.
     * @param width The row's width
     * @return returns the number of cells
     */
    @Contract(pure = true)
    private int getCellsPerRow(double width){
        return (int) ((width + spaceBetween) / (spaceBetween + cellWidth));
    }

    /**
     * Calculates how much cells needed to fill an column with some specified height.
     * @param height The column's height
     * @return returns the number of cells
     */
    @Contract(pure = true)
    private int getCellsPerColumn(double height){
        return (int) ((height + spaceBetween) / (spaceBetween + cellHeight));
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Getters and setters:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public void setOnCellClickListener(EventHandler<? super MouseEvent> event){
        onCellClickListener = event;
    }

    public void setCellFactory(Supplier<GridViewAdapter<E>> cellSupplier){
        this.cellSupplier = cellSupplier;
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
}
