package cz.req.ax.action.adapters;

import cz.req.ax.ui.AxWindowButton;

import java.util.function.BooleanSupplier;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 19.2.2016
 */
class AxWindowButtonAdapter extends ButtonAdapter {

    public AxWindowButtonAdapter(AxWindowButton target) {
        super(target);
    }

    @Override
    public void setExecution(BooleanSupplier callback) {
        ((AxWindowButton) target).addWindowClickListener(e -> {
            if (callback.getAsBoolean()) {
                e.getWindow().close();
            }
        });
    }

}
