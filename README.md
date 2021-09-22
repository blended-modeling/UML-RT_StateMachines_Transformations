# Transformations for UML-RT state machines
Transformations for UML-RT state machines using Epsilon Transformation Language (ETL). 


https://user-images.githubusercontent.com/75987636/134323473-6c324961-34ea-45ab-9af0-e2a0651f0635.mp4


## Description

This repository contains 

- Two domain specific languages for UML-RT state machines
  - Graphical DSL: The domain specific language used in Papyrus
  - Textual DSL: The domain specific language defined for RTist in Xtext  
- Models that conform to the graphical and textual DSLs. The models can be found under 2WayTransformations.
  - G.model (conforms to the graphical DSL)
  - T.hclscope (conforms to the textual DSL)
- Two unidirectional transformations defined used the Epsilon Transformation Language (ETL) and their launch configurations. The transformations can be found under 2WayTransformations.
  - G2T.etl (transformation rules that allow transforming G.model into T.hclscope) - GT2.launch (corresponding launch file)
  - T2G.etl (transformation rules that allow transforming T.hclscope into G.model) - T2G.launch (corresponding launch file) 

## Installation

1. In Eclipse, import the following folders that contain the domain specific languages
```
     - org.xtext.example.hclscope
     - org.xtext.example.hclscope.ide
     - org.xtext.example.hclscope.ui
     - org.xtext.example.hclscope.tests
     - org.xtext.example.hclscope.ui.tests
     
     -Papyrus
```

2. Run a second instance of Eclipse by:

``` 
Right-clicking in org.xtext.example.hclscope -> Run As -> Run Configurations -> Eclipse Application -> New Launch Configuration -> Run 
```

3. On the second Eclipse instance, import the following folder:
```
     - 2WayTransformations
```     
4. To make changes to the models: 
```
   -G.model: Use the Exceed Editor to add/remove/edit elements and the correponding properties
   -T.hclscope : Use the HclScope editor add/remove/edit elements and the correponding properties (You can also use the Exceed editor)
```
5. To run the transformations:
```
   -G2T: Right-click on G2T.launch -> Run As -> G2T
   -T2G: Right-click on T2G.launch -> Run As -> T2G
```

### Enjoy
Note: In case you encounter any issues or want to discuss further, you can contact me at malvina.latifaj@mdh.se.
