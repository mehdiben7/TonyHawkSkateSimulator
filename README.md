# Tony Hawk Skate Simulator

## Context

This program was developed as an integrative project for the final course (420-204-RE) of the Computer Science & Mathematics collegial program.

## Program description

The program consists of a skater that is released down an incline with variables such as the mass,
gravity, slope of the incline, and friction that will change the various energies (kinetic and potential energy) of the skater as it follows its path. Hence, we used concepts mostly related to
the mechanic’s course. The main physics concepts used are forces such as friction and gravity,
and kinematics concepts like speed and acceleration. To display the difference in gravity, many
planets are available to switch to.

![Image: GUI of TonyHawkSkateSimulator](https://drive.google.com/uc?export=view&id=16EJoFRg-byruXqES6SnnaP_LYxcc2bCb)
*Figure 1: The program in a working state, with the skater falling down the plane*

The user inputs a double for the mass, can modify the angle of the incline, the friction
coefficient, and can change the planet, to change the gravitational acceleration, using a combo
box. Furthermore, the user can play, pause and reset the animation, as well as to slow it down.
The user can change the planet the skater is in like earth, the Moon, and Mars.

## Technologies and frameworks used

This program is written in **Java** and uses...

### Frameworks used

* The JavaFX GUI framework
    * The GUI designed using FXML
* The Maven build automation tool

## Developement kits used

The current JDK is Azul Zulu OpenJDK version 15.0.9

# Contribution

You can open fork this repo, commit changes and open a pull request. I check my GitHub account fairly often, hence I will most likely merge your PR in a short time span.

## Issues

THSS is still a work in progress. I will in the near future list bugs and missing features in the form of issues.

### GitHub Actions

I plan on putting a workflow in place, notably with the following actions:
* Maven build action

    An action to ensure each push in a certain branch (probably a test branch) actually compiles correctly
* Maven Jar file action

    An action that, for each pull to the `master` branch, builds a jar file containing every library needed to execute the program, preferably a cross-plateform executable
    * An action to make sure every pull request is a valid compilable version of the code as per the Maven build action


