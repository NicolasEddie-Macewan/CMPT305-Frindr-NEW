# Frindr

## Instructions to Run the App

1. Open the Maven sidebar
2. Under `Plugins` -> `dependency`, double-click the `dependency:unpack`
3. Under `Lifecycle`, double-click `compile`
4. Under `Plugins` -> `exec`, double-click `exec:java`

## Instructions to Use the App

Once the app has loaded, you can make use of the menus on the top left. There is a legend to explain the colors of the
pins. The `Filters` menu allows you to filter by fruit, by neighbourhood, or with `Likely bears fruit`, which removes
plants which are less than 5 years old. The `Location` menu allows you to enter an address or a point location, and set
a radius. The map then displays only pins which are within the radius of the entered location. This location menu
includes a `Help` button which opens a popup window explaining how to use that menu.

## Known Issues

The app's launch is unfortunately quite slow. As such, please wait 30-40 seconds after launch to allow the pins to
be loaded in. This is the only issue we have been able to find, but there may be other bugs we missed.

## Code Integrity Statement

We certify that this project is entirely our own work, except for the following sections:

The starting point for this project was taken from the following GitHub repository: https://github.com/Esri/java-maven-starter-project.
We have left the original README.md file from that project below.

showMenu() and hideActiveMenu() in MainController.java were made with the help of generative AI - in particular,
AI was used to add animations which improved the look of these menus.

The createNeighbourhoodSearch() method in MenuBuilder.java was adapted from code in the citySelector code in the
JavaFX demos. Thank you for that helpful code!

## java-maven-starter-project

Here is a starter project for the ArcGIS Maps SDK for Java with Maven.

The project includes the Maven wrapper, so there is no need to install Maven to run the app.

The app launches a window displaying a map.

![screenshot](screenshot.png)

## Instructions

### IntelliJ IDEA

1. Open IntelliJ IDEA and select _File > Open..._.
2. Choose the java-maven-starter-project directory and click _OK_.
3. Select _File > Project Structure..._ and ensure that the Project SDK and language level are set to use Java 17.
4. Open the Maven view with _View > Tool Windows > Maven_.
5. In the Maven view, under _Plugins > dependency_, double-click the `dependency:unpack` goal. This will unpack the native libraries into $USER_HOME/.arcgis.
6. In the Maven view, run the `compile` phase under _Lifecycle_ and then the `exec:java` goal to run the app.

### Eclipse

1. Open Eclipse and select _File > Import_.
2. In the import wizard, choose _Maven > Existing Maven Projects_, then click _Next_.
3. Select the java-maven-starter-project as the project root directory.
4. Click _Finish_ to complete the import.
5. Select _Project > Properties_ . In _Java Build Path_, ensure that under the Libraries tab, _Modulepath_ is set to JRE System Library (JavaSE-11). In _Java Compiler_, ensure that the _Use compliance from execution environment 'JavaSE-11' on the 'Java Build Path'_ checkbox is selected.
6. Right-click the project in the Project Explorer or Package Explorer and choose _Run As > Maven Build..._. In the _Edit Configuration_ dialog, create a new configuration with name `unpack`. In the _Goals_ field, enter `dependency:unpack`. Click _Run_ to run the goal. This will unpack the native libraries into $USER_HOME/.arcgis.
7. Again, create a new run configuration with name `run`. In the _Goals_ field, enter `compile exec:java`. Click _Run_ to run the goal. The app should compile and launch the JavaFX window.

### Command Line

1. `cd` into the project's root directory.
2. Run `./mvnw dependency:unpack` on Linux/Mac or `mvnw.cmd dependency:unpack` on Windows to unpack the native libraries to $USER_HOME/.arcgis.
3. Run `./mvnw compile exec:java` on Linux/Mac or `mvnw.cmd compile exec:java` on Windows to run the app.

## Requirements

See the Java Maps SDK [system requirements](https://developers.arcgis.com/java/reference/system-requirements/).

## Resources

* [ArcGIS Maps SDK for Java](https://developers.arcgis.com/java/)  
* [ArcGIS Blog](https://www.esri.com/arcgis-blog/developers/)  
* [Esri Twitter](https://twitter.com/arcgisdevs)

## Issues

Find a bug or want to request a new feature?  Please let us know by submitting an issue.

## Contributing

Esri welcomes contributions from anyone and everyone. Please see our [guidelines for contributing](https://github.com/esri/contributing).

## Licensing

Copyright 2023 Esri

Licensed under the Apache License, Version 2.0 (the "License"); you may not 
use this file except in compliance with the License. You may obtain a copy 
of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
License for the specific language governing permissions and limitations 
under the License.

A copy of the license is available in the repository's license.txt file.
