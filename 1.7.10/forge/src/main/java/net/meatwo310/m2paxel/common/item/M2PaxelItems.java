package net.meatwo310.m2paxel.common.item;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class M2PaxelItems {
    public static final Item.ToolMaterial NETHERITE_MATERIAL =
        EnumHelper.addToolMaterial("M2PAXEL_NETHERITE", 4, 2031, 9.0F, 4.0F, 15);

    public static final Item WOODEN_PAXEL = new ItemPaxel(Item.ToolMaterial.WOOD, "wooden_paxel");
    public static final Item STONE_PAXEL = new ItemPaxel(Item.ToolMaterial.STONE, "stone_paxel");
    public static final Item IRON_PAXEL = new ItemPaxel(Item.ToolMaterial.IRON, "iron_paxel");
    public static final Item GOLDEN_PAXEL = new ItemPaxel(Item.ToolMaterial.GOLD, "golden_paxel");
    public static final Item DIAMOND_PAXEL = new ItemPaxel(Item.ToolMaterial.EMERALD, "diamond_paxel");
    public static final Item NETHERITE_PAXEL = new ItemPaxel(NETHERITE_MATERIAL, "netherite_paxel");

    public static final Item WOODEN_PHAXEL = new ItemPhaxel(Item.ToolMaterial.WOOD, "wooden_phaxel");
    public static final Item STONE_PHAXEL = new ItemPhaxel(Item.ToolMaterial.STONE, "stone_phaxel");
    public static final Item IRON_PHAXEL = new ItemPhaxel(Item.ToolMaterial.IRON, "iron_phaxel");
    public static final Item GOLDEN_PHAXEL = new ItemPhaxel(Item.ToolMaterial.GOLD, "golden_phaxel");
    public static final Item DIAMOND_PHAXEL = new ItemPhaxel(Item.ToolMaterial.EMERALD, "diamond_phaxel");
    public static final Item NETHERITE_PHAXEL = new ItemPhaxel(NETHERITE_MATERIAL, "netherite_phaxel");
}
