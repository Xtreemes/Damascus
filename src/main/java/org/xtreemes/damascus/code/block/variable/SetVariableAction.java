package org.xtreemes.damascus.code.block.variable;

import org.xtreemes.damascus.code.RunInfo;
import org.xtreemes.damascus.code.parameters.Parameter;
import org.xtreemes.damascus.code.parameters.Parameters;
import org.xtreemes.damascus.code.value.ValueType;

@Parameter(value=ValueType.VARIABLE, desc="Variable to set")
@Parameter(value=ValueType.ANY, desc="Value to set")
public class SetVariableAction extends VariableAction {
    @Override
    public void run(RunInfo info) {
        Parameters params = getParameters(info);
        info.setVariable(params.getVariable(), params.getValue());
    }
}
