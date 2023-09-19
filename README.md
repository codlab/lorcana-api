### Lorcana.api.com

# Backend

This is the backend code for [Lorcana-api.com](https://lorcana-api.com).
Lorcana-Api is a website for easiley fetching and filtering Lorcana cards.
Several endpoints are avaliable, like /strict/, /fuzzy/, /lists/, and /search/. An in-depth explanation of all of these endpoints is avaliable at [Lorcana-api.com](lorcana-api.com/How-To.html)

# Front end

A Kotlin Multiplatform implementation is available for :

- Android
- iOS
- JVM (Windows, Linux, MacOS)

Still WIP but will bring management support

# Shared implementation

The cards are available in src/data and are bundled inside the shared project available for use by both the server & apps

# Usage

## Requirements

- Android Studio
- Java

## Development

It will be refactored but the apps exists alongside "app-ui" which exists solely to fix an Android Studio "issue". To use compose multiplatform, only android or desktop are supported for preview so this project is only referring to the commonMain folder as androidMain.

## Roadmap

- remove the preview & safearea libs to be refactored into outside libs
- refactor the project structure
- upgrade backend code
- add actual UI content
- update documentation

# Resources

Some svg assets are from https://github.com/glimmerdb/lorcana-icons credits to @GlimmerDB and specifically nateofthecards (https://www.behance.net/89c6305f)
