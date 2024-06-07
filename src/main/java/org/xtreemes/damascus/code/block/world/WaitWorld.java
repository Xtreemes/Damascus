package org.xtreemes.damascus.code.block.world;

import org.xtreemes.damascus.code.RunInfo;

public class WaitWorld extends WorldAction {
    @Override
    public void run(RunInfo info) {
        info.cancelEvent();
        int n = (int) getParameters(info).getNumber() * 50;
        if(n > 0) {
            try {
                Thread.sleep(n);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
