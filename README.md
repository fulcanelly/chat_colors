# ColorChat Plugin

A simple Minecraft plugin that adds color to player chat messages based on their username.

## Features

- Automatically assigns a unique color to each player based on their username
- Colors persist across server restarts
- Simple and lightweight
- No configuration needed
- Command to randomize your chat color

## Installation

1. Download the latest version of the plugin
2. Place the jar file in your server's `plugins` folder
3. Restart your server

## How it works

The plugin assigns colors to players based on a hash of their username. This means:
- Each player gets a consistent color
- The same player will always get the same color
- Colors are distributed evenly among players

## Commands

- `/saltcolor` - Randomize your chat color (requires permission)

## Permissions

- `colorchat.salt` - Allows players to use the `/saltcolor` command

### Setting up permissions with LuckPerms

If you're using LuckPerms, you can set up the permissions with the following commands:

```mcfunction
# Give the permission to all players
/lp group default permission set colorchat.salt true

# Or give the permission to a specific group
/lp group vip permission set colorchat.salt true

# Or give the permission to a specific player
/lp user <player> permission set colorchat.salt true
```

## Supported Minecraft Versions

- Tested on 1.20.1
- Should work on most recent versions

## License

MIT License
