# lw3dge 
#### also known as `LW 3D GE` or `L-Wedge`
A Light Weight 3D Game Engine in Java, written using LWJGL 3.2.1 and custom physics and object management code.
This Game Engine focuses on high readability, light-weight implementation, and developer empowerment (developer = Library User).

## Getting Started

  1. Create a Maven project in your favorite editor (has to be maven for the file structure).
  2. Open your projects location in a shell to clone from git i.e. your shell should do this:
 
  `PATH_TO_YOUR_PROJECT> git clone https://github.com/slamdewey/lw3dge`
 
  3. After cloning, ensure your pom.xml and src/main/ files/folders have been overwritten.
  4. Edit `src/main/java/lw3dge/components/Config.java` and change the ABS_PROJ_PATH variable to the path to your project folder.
  5. Look through the rest of the `Config.java` file and ensure you don't want to change any other settings.
  6. Navigate to `src/test/java/` and Begin Using the Engine
  
  *There should be sample test files included, which you can delete if you'd like.*

## Project Goals
  - Easy to Implement
    - Open Source, all dependencies are also Open Source
    - Non-Restrictive License
  - Easy to Understand
    - I built this in a manner to make readability quite high.  (check documentation for examples)
  - Easy to Alter (high customability for the developer)
    - Developer gets the Entry Point
    - Developer gets control over when to enter game loop
    - Developer gets control over Models And Textures
      - You decide your own Model Framework, but there will be a suggested format in the Documentation
  - Lightweight To Simply provide a framework
      - This is essentially a giant wrapper on LWJGL, and Custom Game Engine code for your use!
### This project is under construction!
Feel free to look through the code in the meantime, as this project should be updating rapidly.
## Brief Features List
I'll do this when I make all the features
## Documentation
Basic documentation will be included in the `docs/` subfolder, but it isn't hosted anywhere yet and my not be up to date.
## Contributing
Want to contribute to the Project?  Fork the repository and offer a pull request with you code contributions.  Be sure to fully document any public functions using standard JavaDoc protocol.
## Got an Issue?
Found an issue?  Opening Issue topics is highly encouraged during this projects development.
##
### A Broken Todo List:
  - [x] create generic 3d rending engine
  - [x] create basic engine entry point and init() structure
  - [x] link dependencies and license in git repo
  - [x] open eclipse
  - [x] setup project for easy maven integration
  - [x] implement quaternions in object transforms
  - [ ] 3D collision detection
  - [ ] 3d mesh simplifier
  - [ ] Add entity structure
  - [ ] Add enviornment rendering
  - [x] Add spot/point lighting
  - [ ] finish readme
  - [ ] reconsider license
  - [ ] sleep?
  - [ ] Project Wiki/Documentation
