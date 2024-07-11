package org.xtreemes.damascus;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public enum Rank {
    DEFAULT("",TextColor.fromHexString("FFFFFF"), "9999"),
    MODERATOR("\u26E8", TextColor.fromHexString("#5e9fff"), "0010"),
    ADMIN("\u2606", TextColor.fromHexString("#ff823b"), "0003"),
    DEV("\u203A\u208B",TextColor.fromHexString("#ff8aa9"),"0002"),
    OWNER("\u2605", TextColor.fromHexString("#f02222"), "0001"),
    SKIBIDI("\uD83D\uDEBD", TextColor.fromHexString("#D29D1E"), "0000");

    private final String PREFIX;
    private final TextColor COLOR;
    private final String SORT;

    Rank(String prefix, TextColor rank_color, String sort) {
        PREFIX = prefix;
        COLOR = rank_color;
        SORT = sort;
    }

    public Component getFormattedPrefix(){
        return Component.text(this.PREFIX, this.COLOR);
    }
    public TextColor getTextColor(){return this.COLOR;}
    public String getSort() {return this.SORT;}
}
