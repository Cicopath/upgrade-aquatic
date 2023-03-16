package com.teamabnormals.upgrade_aquatic.core;

import com.teamabnormals.blueprint.core.annotations.ConfigKey;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

public class UAConfig {

	public static class Common {
		public final ConfigValue<Boolean> clericsBuyThrasherTeeth;
		public final ConfigValue<Boolean> drownedSwimmingAnimation;
		public final ConfigValue<Boolean> leatherworkersSellBedrolls;

		@ConfigKey("kelpy_ocean_ruins")
		public final ConfigValue<Boolean> kelpyOceanRuins;
		public final ConfigValue<Integer> deepOceanMobMaxHeight;

		public Common(ForgeConfigSpec.Builder builder) {
			builder.push("items");
			builder.push("trades");
			this.clericsBuyThrasherTeeth = builder.define("Clerics buy thrasher teeth", true);
			this.leatherworkersSellBedrolls = builder.define("Leatherworkers sell bedrolls", true);
			builder.pop();
			builder.pop();

			builder.push("generation");
			builder.push("structures");
			this.kelpyOceanRuins = builder.comment("If Mossy Cobblestone and Stone Bricks are replaced with Kelpy Cobblestone and Stone Bricks in Ocean Ruins").define("Kelpy Ocean Ruins", true);
			builder.pop();
			builder.pop();

			builder.push("mobs");
			this.drownedSwimmingAnimation = builder.comment("Give Drowneds a swimming animation, like in bedrock edition").define("Drowned swimming animation", true);
			this.deepOceanMobMaxHeight = builder.comment("The max height that deep ocean mobs can spawn at").defineInRange("Deep ocean mob max height", 30, 0, 255);
			builder.pop();
		}
	}

	public static class Client {
		public final ConfigValue<Boolean> showUnobtainableDescription;
		public final ConfigValue<Integer> daysTillRenderInsomniaOverlay;
		public final ConfigValue<Boolean> replaceGlowSquidRenderer;

		public Client(ForgeConfigSpec.Builder builder) {
			builder.push("misc");
			this.showUnobtainableDescription = builder.comment("If unimplemented items should show that they are unobtainable in their item description").define("Show unobtainable description", true);
			this.daysTillRenderInsomniaOverlay = builder
					.comment("The amount of days till the insomnia overlay is rendered", "Setting to 3 will make the overlay indicate phantom spawns", "Setting to 0 will disable the overlay")
					.define("Days until insomnia overlay", 0);
			this.replaceGlowSquidRenderer = builder.comment("If Glow Squids should use our Glow Squid Renderer.").define("Replace Glow Squid Renderer", true);
			builder.pop();
		}
	}

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	static {
		Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = commonSpecPair.getRight();
		COMMON = commonSpecPair.getLeft();

		Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = clientSpecPair.getRight();
		CLIENT = clientSpecPair.getLeft();
	}
}
