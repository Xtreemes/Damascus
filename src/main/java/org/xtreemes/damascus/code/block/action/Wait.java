package org.xtreemes.damascus.code.block.action;

import org.xtreemes.damascus.code.RunInfo;

public class Wait extends Action {
    @Override
    public void run(RunInfo info) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {

        }
    }
}
