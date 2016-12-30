@echo off
echo run original
call j8 java -classpath target/classes;../core/target/classes sample.Sample
echo run instrumeted
call j8 java -classpath gen;../core/target/classes sample.Sample
