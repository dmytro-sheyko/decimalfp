@echo off
:: echo run original add
:: call j8 java -classpath target/classes;../core/target/classes sample.SampleAdd
echo run instrumeted add
call j8 java -classpath target/classes;../core/target/classes -javaagent:../agent/target/decimalfp-agent-0-SNAPSHOT.jar sample.SampleAdd

:: echo run original sub
:: call j8 java -classpath target/classes;../core/target/classes sample.SampleSub
echo run instrumeted sub
call j8 java -classpath target/classes;../core/target/classes -javaagent:../agent/target/decimalfp-agent-0-SNAPSHOT.jar sample.SampleSub

:: echo run original cvt
:: call j8 java -classpath target/classes;../core/target/classes sample.SampleCvt
echo run instrumeted cvt
call j8 java -classpath target/classes;../core/target/classes -javaagent:../agent/target/decimalfp-agent-0-SNAPSHOT.jar sample.SampleCvt
