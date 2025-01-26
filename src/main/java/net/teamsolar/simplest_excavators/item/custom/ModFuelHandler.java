package net.teamsolar.simplest_excavators.item.custom;

import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.teamsolar.simplest_excavators.SimplestExcavators;
import net.teamsolar.simplest_excavators.item.ModItems;

@Mod.EventBusSubscriber(modid = SimplestExcavators.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModFuelHandler {

    @SubscribeEvent
    public static void onFurnaceFuel(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().is(ModItems.WOODEN_EXCAVATOR.get())) {
            event.setBurnTime(200);
        }
    }
}