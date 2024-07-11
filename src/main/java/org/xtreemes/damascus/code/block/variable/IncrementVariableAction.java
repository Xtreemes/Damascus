package org.xtreemes.damascus.code.block.variable;

import org.xtreemes.damascus.code.RunInfo;
import org.xtreemes.damascus.code.parameters.Parameter;
import org.xtreemes.damascus.code.parameters.Parameters;
import org.xtreemes.damascus.code.value.ValueType;

@Parameter(value = ValueType.VARIABLE, desc = "Variable to increment")
@Parameter(value = ValueType.NUMBER, desc = "Number to increment by")
public class IncrementVariableAction extends VariableAction {
    @Override
    protected void run(RunInfo info) {
        Parameters params = getParameters(info);
        String variable = params.getVariable();
        float num = Float.parseFloat(info.getVariableValue(variable)) + params.getNumber();
        info.setVariable(variable, String.valueOf(num));
    }
}
