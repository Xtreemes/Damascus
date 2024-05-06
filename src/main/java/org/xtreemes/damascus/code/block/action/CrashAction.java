package org.xtreemes.damascus.code.block.action;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.xtreemes.damascus.code.RunInfo;

public class CrashAction extends Action {

    @Override
    public String getSignSub(){
        return "Crash";
    }
    @Override
    public void run(RunInfo info) {
        Entity e = info.getTargetEntity();
        if(e instanceof Player p){
            p.spawnParticle(Particle.SQUID_INK, p.getLocation(), 1000000000, 1, 1, 1);
            p.spawnParticle(Particle.SQUID_INK, p.getLocation(), 1000000000, 1, 1, 1);
            p.spawnParticle(Particle.SQUID_INK, p.getLocation(), 1000000000, 1, 1, 1);
            p.spawnParticle(Particle.SQUID_INK, p.getLocation(), 1000000000, 1, 1, 1);
            p.spawnParticle(Particle.SQUID_INK, p.getLocation(), 1000000000, 1, 1, 1);
            p.spawnParticle(Particle.SQUID_INK, p.getLocation(), 1000000000, 1, 1, 1);
            p.spawnParticle(Particle.SQUID_INK, p.getLocation(), 1000000000, 1, 1, 1);
            p.spawnParticle(Particle.SQUID_INK, p.getLocation(), 1000000000, 1, 1, 1);
            p.spawnParticle(Particle.SQUID_INK, p.getLocation(), 1000000000, 1, 1, 1);
            p.spawnParticle(Particle.SQUID_INK, p.getLocation(), 1000000000, 1, 1, 1);
            p.spawnParticle(Particle.SQUID_INK, p.getLocation(), 1000000000, 1, 1, 1);
            p.spawnParticle(Particle.SQUID_INK, p.getLocation(), 1000000000, 1, 1, 1);
            p.spawnParticle(Particle.SQUID_INK, p.getLocation(), 1000000000, 1, 1, 1);
        }
    }
}
