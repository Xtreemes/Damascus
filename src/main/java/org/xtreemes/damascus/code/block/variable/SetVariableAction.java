package org.xtreemes.damascus.code.block.variable;

import org.xtreemes.damascus.code.RunInfo;
import org.xtreemes.damascus.code.parameters.Parameters;

public class SetVariableAction extends VariableAction {
    @Override
    public void run(RunInfo info) {
        Parameters params = getParameters(info);
        info.setVariable(params.getVariable(), VariableScope.GAME, params.getValue());
    }
}
