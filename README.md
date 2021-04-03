# Data analyzing programm (Java) UPDATED VERSION (OOP)
This repository contains the data analyzing program written in Java (Maven) OOP.
## Usage
1. Go to /out/artifacts/data_analyzer_oop_jar
2. Download and save data-analyzer-oop.jar file on your machine
3. Run the jar file with the following the rules:

#### There are 3 must-haves arguments:
       -F or --FILE       -  Path to txt file(s) for analyzing
       -S or --STOPWORDS  -  Stopwords which will not be counted in analyzing
       -C or --CHARCOUNT  -  Counts characters in (each) file (stopwords are not included in count)
#### There is 1 optional argument:
       -L or --CAPITALCOUNT  -  Count words which starts with capital letter (stopwords are not included in count)
 
## Examples of using
 
### Single file
       java -jar data-analyzer-oop.jar -F=f1.txt -S=at,the,on -C -L
#### or
       java -jar data-analyzer-oop.jar -F f1.txt -S=at,the,on -C -L
       
### Multiple files
      java -jar data-analyzer-oop.jar -F=f1.txt,f2.txt,f3.txt -S=at,the,on -C -L
