package org.xtreemes.damascus.code.block.world;

import org.xtreemes.damascus.code.RunInfo;
import org.xtreemes.damascus.code.parameters.Parameter;
import org.xtreemes.damascus.code.value.ValueType;

@Parameter(value= ValueType.NUMBER, desc="Amount of ticks to wait")
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
