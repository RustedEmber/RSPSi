package rspsi;

public class RSInterfaceConstants {

	public static String[] regions = {
		    "Screen", "Inventory", "Chatbox", "Fullscreen"
	};

	public static int[][] regionSizes = {
			{ 512, 334 }, { 190, 261 }, { 479, 96 }, { 765, 503 }
	};

	public static String[] types = {
			"Container",
			"Unknown boolean/int",
			"Inventory",
			"Box",
			"Text",
			"Sprite",
			"Model",
			"Item List",
			"Tooltip"
	};

	public static String[][] editableValues = {
			{ "id", "parentId", "type", "actionType", "contentType",
						"width", "height", "alpha", "trigger", "tooltip" },

			{ "scrollMax", "triggered" },

			{  },

			{ "inv",
						"invStackSizes",
						"inventoryInterface",
						"invItemsCanBeSwapped",
						"usableItemInterface",
						"invSwapDeletesTargetSlot",
						"invSpritePadX", "invSpritePadY",
						"spritesX", "spritesY",
						"sprites", "spritesDir", "spritesId", 
						"actions", "selectedActionName",
						"spellName", "spellUsableOn" },

			{ "filled",
						"disabledColor", "enabledColor",
						"mouseOverDisabledColor", "mouseOverEnabledColor" },

			{ "disabledText", "enabledText",
						"centered", "shadowed", "fontId",
						"disabledColor", "enabledColor",
						"mouseOverDisabledColor", "mouseOverEnabledColor" },

			{ "disabledSpriteDir", "disabledSpriteId",
						"enabledSpriteDir", "enabledSpriteId" },

			{ "disabledMediaType", "disabledMediaId",
						"enabledMediaType", "enabledMediaId",
						"disabledAnimation", "enabledAnimation",
						"modelZoom", "verticalRotation", "horizontalRotation" },

			{ "inv", "invStackSizes", "isInventoryInterface",
						"centered", "shadowed",
						"disabledColor",
						"invSpritePadX", "invSpritePadY",
						"actions" },

			{ "disabledText" }
	};
}
