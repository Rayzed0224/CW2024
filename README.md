# COMP2042 Coursework

## Table of Contents

1. [GitHub Repository](#github-repository)
2. [Compilation Instructions](#compilation-instructions)
3. [Implemented and Working Properly](#implemented-and-working-properly)
4. [Implemented but Not Working Properly](#implemented-but-not-working-properly)
5. [Features Not Implemented](#features-not-implemented)
6. [New Java Classes](#new-java-classes)
7. [Modified Java Classes](#modified-java-classes)
8. [Unexpected Problems](#unexpected-problems)

## GitHub Repository

By: Ki Jason (20398572)
https://github.com/Rayzed0224/CW2024.git


## Compilation Instructions

Steps to compile and run the application:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/Rayzed0224/CW2024.git
   ```

2. **Navigate to the project directory:**

   ```bash
   cd sky-battle
   ```

3. **Ensure JavaFX is configured:**

    - Download and install JavaFX SDK if not already installed.
    - Set the `--module-path` and `--add-modules` flags in your IDE or command-line execution.


4. **Run the application from the command line:**

   ```bash
   java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.media -jar SkyBattle.jar
   ```

5. **Dependencies:**

   **Check and Set Java SDK Version:**
    - Go to `File > Project Structure > Project`.
    - Ensure the **SDK** is set to Java 19 (Amazon Corretto 19.0.2, as shown in your screenshot).
    - Verify the **Language Level** is compatible with JavaFX (11 or higher).
    - Ensure all required libraries are included in your classpath.

---

### Core Features:

1. **Main Menu:**

    - The `MainMenu` class provides user with an interface before playing the game.
    - The "Settings" option allows user to change window sizes before playing the game.

2. **Player Movement:**

    - The `updatePosition()` method in the UserPlane class handles smooth and responsive player movement based on real-time input.
    - Player movement supports all four cardinal directions (up, down, left, right) via methods like `handleInput()` that process keyboard events.
    - Movement logic is constrained by predefined boundaries to ensure the UserPlane remains within the visible game area.
    - Integrates seamlessly with the game loop, enabling synchronization with projectile firing, collision detection, and scene updates.

3. **Enemies**

    - Classic default `enemyplane()` with a twist, all projectiles has homing attribute to it.
    - LevelOne 1 health, LevelTwo 3 Health

4. **Boss**

    - Boss with homing attribute and faster fire rate but with only 50health and 3 shields

5. **Window**

   ![img.png](img.png)

    - Lets user choose between having fullscreen, windowed borderless or Windowed modes.

---

## Implemented but Not Working Properly

1. **WinImage**

    - Not sure what the problems are that even after diagnosis with logs stating that WinImage was loaded successfully.

2. **Return to MainMenu when GameOver**

    - When trying to return to MainMenu, it will pop out an error window

3. **Show Credits Button**

    - Acidentally deleted it while changing something

---

## New Java Classes

| Class Name             | Description                                                                                  | Location                          |
|------------------------|----------------------------------------------------------------------------------------------|-----------------------------------|
| **ActorManager**       | Manages all actors in the game, including adding, removing, and updating their states.       | `com.example.demo.levels`        |
| **CollisionManager**   | Handles collision detection and resolution for all game entities like projectiles and actors.| `com.example.demo.levels`        |
| **LevelBackgroundManager** | Manages the background elements specific to each level, including parallax scrolling effects.| `com.example.demo.levels`        |
| **LevelGameStateManager** | Manages the state transitions within levels, such as starting, pausing, and completing the level.| `com.example.demo.levels`        |
| **LevelParentBase**    | Base class for all levels, providing shared functionality like initialization and updates.    | `com.example.demo.levels`        |
| **LevelThree**         | Represents the third level, including its unique enemies, challenges, and win conditions.    | `com.example.demo.levels`        |
| **LevelViewLevelThree**| Manages the UI and visual representation of LevelThree, including health bars and enemy displays.| `com.example.demo.levels`        |
| **HomingProjectiles**  | Represents a projectile that follows a target, adding challenge to evading attacks.          | `com.example.demo.projectiles`   |
| **MainMenu**           | Represents the main menu interface with options to start, load, or exit the game.            | `com.example.demo.ui`            |
| **GameConstants**      | Stores constant values used throughout the game, such as screen dimensions and physics settings.| `com.example.demo.utilities`     |
| **UserSettings**       | Handles player-specific settings, such as control preferences and difficulty levels.         | `com.example.demo.utilities`     |
| **WindowUtils**        | Provides utility functions for window management, including resizing and fullscreen toggles. | `com.example.demo.utilities`     |


## Modified Java Classes


| Class Name                  | Description                                                          | Location                       |
|-----------------------------|----------------------------------------------------------------------|--------------------------------|
| **ActiveActor**             | Added resizing function                                              | `com.example.demo.actors`      |
| **ActiveActorDestructible** | Added some getters                                                   | `com.example.demo.actors`      |
| **Boss**                    | Changes it to have homing and resizing                               | `com.example.demo.actors`      |
| **UserPlane**               | Make user smaller and also have resizing                             | `com.example.demo.actors`      |
| **EnemyPlane**              | enabled homing and resizing                                          | `com.example.demo.actors`      |
| **Main**                    | Redirects to MainMenu instead of starting right away                 | `com.example.demo.controller`  |
| **Controller**              | Receive calls from MainMenu to start game                            | `com.example.demo.controller`  |
| **LevelParent**             | Reduced responsibilities                                             | `com.example.demo.levels`      |
| **LevelOne**                | Tweaks made for window resizing scaling                              | `com.example.demo.levels`      |
| **LevelView**               | Slight tweaks                                                        | `com.example.demo.levels`      |
| **LevelTwo**                | Tweaks made for window resizing scaling and boss moved to LevelThree | `com.example.demo.levels`      |
| **LevelViewLevelTwo**       | Empty                                                                | `com.example.demo.levels`      |
| **BossProjectile**          | Added homing projectiles                                             | `com.example.demo.projectiles` |
| **EnemyProjectile**         | Added homing projectiles                                             | `com.example.demo.projectiles`   |
| **Projectile**              | Adapted code to enable homing                                        | `com.example.demo.projectiles`   |
| **UserProjectile**          | Did some balancing                                                   | `com.example.demo.projectiles`   |
| **GameOverImage**           | Added adjust position                                                | `com.example.demo.utilities`   |
| **GameOverUI**              | Show gameover menu                                                   | `com.example.demo.utilities`   |
| **HeartDisplay**            | Changed to include in root                                           | `com.example.demo.utilities`   |
| **ShieldImage**             | Edited code to make it work                                          | `com.example.demo.utilities`   |
| **WinImage**                | Added resizing for window                                            | `com.example.demo.utilities`   |


---

## Unexpected Problems

1. **Game freeze after winning**
2. **GameOver cant go back to Mainmenu**
3. **Projectile being buggy**

---