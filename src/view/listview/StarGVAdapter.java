package view.listview;

import model.Star;

/**
 * @author Guilherme Reginaldo
 * @since 28/01/2016
 */
public class StarGVAdapter extends GridViewAdapter<Star> {

    @Override
    protected String getFXMLPath() {
        return "/res/layout/cell_gv_star.fxml";
    }

    @Override
    public void bindData(Star data) throws NullPointerException {
        System.out.println("bind");
    }
}
