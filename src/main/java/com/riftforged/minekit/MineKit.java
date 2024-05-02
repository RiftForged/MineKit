package com.riftforged.minekit;

import com.riftforged.glove.api.GlovePlugin;

public class MineKit extends GlovePlugin
{

    @Override
    public void enabled()
    {
        this.registerMainCommand("minekit");
    }

    @Override
    public void disabled()
    {
    }
}
