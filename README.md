# Software Design Documentation
## Introduction
### Project Overview
Pocket Miner is a 2D arcade-style click-based mining game developed using the LibGDX framework. The primary objective is to create a dynamic, touch-interactive gameplay experience where players alternate between tools and resources to accumulate points. The game focuses on simplicity and the ability to be replayable, emphasizing real-time resource collection and score tracking. A central feature of the project is the implementation of an autosave system, designed to automatically preserve the player's progress at regular intervals or after key gameplay events.
- **Game Name**: Pocket Miner
- **Genre**: Arcade
- **Platform**: PC
- **Engine**: [LibGDX](https://libgdx.com/)

### Objectives
-	Implement gameplay features like resource gathering, tool switching, and scorekeeping.
-	Create an autosave system to preserve progress.
________________________________________
	
## System Architecture
### Overall System Design
Provide a diagram of the architecture. This could include:
- The main game loop (render(), update(), etc.).
-	Core components (Game logic, UI, Input handling, Game state management).
-	Dependencies (LibGDX libraries, JSON handling for saving/loading, etc.).

### Component Breakdown
•	Main class (Main):
-	Handles game loop (render(), create(), resize()).
-	Manages input (input()).
-	Manages visual components (sprites, font, UI).
-	Controls gameplay logic like resource gathering, tool-switching, etc.


•	Save System:
-	Saves player state (score, tool, resource) using Preferences and/or JSON.
-	Autosaves based on time or events.


•	UI/Graphics:
-	Manages SpriteBatch, Texture, and Sprite for rendering game assets.
-	Displays HUD elements like the score.
________________________________________

## Game Design
### Gameplay Mechanics
** Provide detailed descriptions of how the game works/operates.
•	Core Loop: Resource gathering -> Tool switching -> Scoring -> Autosave.
•	Controls:
-	Tap to switch resources and tools.
-	Tap resources to gather them, earning points.


### Features
-	Autosave: Automatically saves player progress periodically.
-	Resource Management: Different resources that can be mined by switching tools.


### Flow and Progression
**Describe how the game progresses:
-	Start: Game initializes with the miner, resources, and tools on the screen.
-	Player Interaction: Player switches between tools and resources and collects resources.
-	Game Loop: Each resource collection triggers score increase and autosave.

________________________________________
## Technical Design
### Class Design
**Describe the class hierarchy, including methods, attributes, and responsibilities.
Main class:
•	Attributes: Texture backgroundTexture, Sprite minerSprite, int score, etc.
•	Methods:
-	create(): Initializes textures, sprites, fonts, and UI components.
-	render(): Handles rendering the UI and game objects.
-	saveGame(): Saves the current game state using Preferences.
-	input(): Processes player input to switch tools/resources or gather resources.

### Data Structures
-	Preferences for Saving: Use LibGDX Preferences to store game data (e.g., score, current resource, tool).
-	SpriteBatch for Rendering: Use for drawing the game graphics efficiently.
-	Timers: Use float for timing-based events, like the autosave interval.

### Autosave Logic
-	Timer-based Autosave: A timer counts up in render() and calls saveGame() after a predefined interval (e.g., 10 seconds).
-	Event-based Autosave: Calls saveGame() when significant game actions occur, such as changing tools or gathering resources.
________________________________________
## User Interface (UI) Design
### UI Elements
•	Score Display: Shows the player's current score.
•	Resource/Tool Switch Buttons: Buttons for switching between resources and tools.


### Layout
•	Top-left: Score display.
•	Top-center: Resource/Tool selection buttons.

________________________________________

## Testing and Validation
### Test Plan
-	Functional Testing: Ensure each gameplay feature works (tool-switching, score incrementing, autosave).
-	UI Testing: Ensure all UI elements are displayed correctly and respond to user input.
-	Save System Testing: Ensure the save system works as expected: data is saved and reloaded correctly.


### Test Scenarios
-	Scenario 1: Player switches between tools and resources and sees that changes are reflected immediately.
-	Scenario 2: Player's score is saved and persists across sessions.
________________________________________
## Future Considerations
### Potential Enhancements
-	Cloud Saving: Implement a cloud save feature to allow players to save progress across devices.
-	Sound and Animation: Add more complex animations (fade-in/out) and sound effects.
-	Game Difficulty: Increase the difficulty by introducing new resources, locations, tools, or time limits.

## Known Limitations
•	Save System: Currently, only score and resources are saved, but other aspects like player preferences or game state can be added in future updates.
________________________________________
## References
•	LibGDX Documentation: (for texture management, input handling, etc.)
________________________________________

## Conclusion
In conclusion, this software program encapsulates a minimal yet extensible foundation for a resource-gathering arcade game. By focusing on core functionalities such as interactive input and real-time autosaving, Pocket Miner introduces essential principles of state persistence and user-centered design. This document outlines a structured development plan that facilitates modular growth, supports testing and iteration, and ensures maintainability. As development progresses, the framework can be further refined to include advanced mechanics, persistent settings, animations, and networked saving features. Ultimately, the project aims to provide not only an engaging player experience but also a valuable learning and development opportunity in a LibGDX-based game design.


---------------------------------------------

# pocket-miner

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and a main class extending `Game` that sets the first screen.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
