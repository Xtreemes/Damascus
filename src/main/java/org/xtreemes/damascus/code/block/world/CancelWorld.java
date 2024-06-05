package org.xtreemes.damascus.code.block.world;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.xtreemes.damascus.code.RunInfo;


public class CancelWorld extends WorldAction {
    @Override
    public void run(RunInfo info) {
        Event e = info.getEvent();
        if(e != null){
            if(e instanceof Cancellable){
                info.cancelEvent(true);
            }
        }
    }
}
