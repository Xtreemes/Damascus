package org.xtreemes.damascus.code.block.action;

import org.xtreemes.damascus.code.RunInfo;
import org.xtreemes.damascus.code.parameters.Parameters;

public class Wait extends Action {
    @Override
    public void run(RunInfo info) {
        try {
            Parameters params = getParameters();
            Thread.sleep(Math.round(params.getNumber())*50L);
        } catch (InterruptedException ignored) {

        }
    }
}
