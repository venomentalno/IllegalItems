# Illegal Items

A Fabric mod for Minecraft 1.21.4 that allows you to give yourself items in creative mode on restricted servers without needing OP permissions. Perfect for servers that grant creative mode but restrict command execution.

## Features

✨ **Give Illegal Items** - Give yourself any item, including those not in the creative inventory
🎨 **Beautiful UI** - Easy-to-use interface for inputting give commands
📦 **Full NBT Support** - Apply custom NBT tags, enchantments, and attributes to items
⌨️ **Command-Based** - Use the `/give` command syntax without actually executing commands on the server
🔑 **Keybinding** - Press `I` (configurable) to open the item giving interface

## Installation

1. Download the latest release from [GitHub Releases](https://github.com/venomentalno/IllegalItems/releases)
2. Place the `.jar` file in your `mods` folder
3. Requires [Fabric Loader](https://fabricmc.net/use/) and [Fabric API](https://www.curseforge.com/minecraft/mods/fabric-api)

## Usage

### Via Command
1. Type `/give <item_id> [amount] {nbt_data}`
2. The mod will parse the command and give you the item directly

### Via UI
1. Press `I` (default keybinding) to open the Give Command interface
2. Enter the item command in the format: `item_id [amount] {nbt}`
3. Click "Give Item" to receive the item

### Examples

```
/give diamond
/give diamond 64
/give diamond 64 {Enchantments:[{id:"minecraft:sharpness",lvl:5}]}
/give stick 1 {display:{Name:'{"text":"Cool Stick"}'}}
```

## Requirements

- Minecraft 1.21.4
- Fabric Loader 0.15.11+
- Java 21+
- Fabric API 0.102.0+

## How It Works

The mod intercepts commands and simulates item giving by:
1. Parsing the `/give` command syntax
2. Validating the item ID against the game's item registry
3. Creating an ItemStack with the specified amount and NBT data
4. Directly adding it to your inventory (no server-side execution)

This allows you to receive items that might be restricted on the server while still respecting creative mode limitations.

## Building from Source

### Prerequisites
- JDK 21 or higher
- Git

### Build Steps

```bash
git clone https://github.com/venomentalno/IllegalItems.git
cd IllegalItems
./gradlew build
```

The compiled JAR will be in `build/libs/`

## GitHub Actions

This project includes automated compilation via GitHub Actions. Every push to `main` or `develop` branches automatically builds the mod and uploads artifacts.

## Configuration

The mod is entirely client-side and requires no configuration file. All settings are in-game. Keybindings can be configured in the game's controls menu under "Illegal Items" category.

## Compatibility

- ✅ Fabric 1.21.4
- ✅ Client-side only
- ✅ Works with any server (creative mode required)
- ℹ️ Does NOT work with non-creative mode

## Limitations

- Requires Creative mode to be enabled on the server
- Item stacks are capped at 64 items per stack
- Only works with items registered in the game's item registry
- Server-side checks may still prevent some items from being used

## License

MIT License - See [LICENSE](LICENSE) file for details

## Author

Created by [@venomentalno](https://github.com/venomentalno)

## Support

For issues, feature requests, or questions:
- Open an issue on [GitHub Issues](https://github.com/venomentalno/IllegalItems/issues)
- Check existing issues for solutions

## Disclaimer

This mod is intended for educational and legitimate purposes only, such as:
- Single-player survival
- Creative building
- Testing purposes on your own server

Use responsibly and respect server rules. This mod is not intended to bypass server security in malicious ways.