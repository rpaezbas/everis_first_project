rmdir /s /q C:\Users\rpaezbas\Desktop\enviroment\payara41\glassfish\domains\domain1\autodeploy\Login-0.0.1-SNAPSHOT 
del /q C:\Users\rpaezbas\Desktop\enviroment\payara41\glassfish\domains\domain1\autodeploy\Login-0.0.1-SNAPSHOT_deployed 
mkdir C:\Users\rpaezbas\Desktop\enviroment\payara41\glassfish\domains\domain1\autodeploy\Login-0.0.1-SNAPSHOT
xcopy target\Login-0.0.1-SNAPSHOT C:\Users\rpaezbas\Desktop\enviroment\payara41\glassfish\domains\domain1\autodeploy\Login-0.0.1-SNAPSHOT  /s /e
