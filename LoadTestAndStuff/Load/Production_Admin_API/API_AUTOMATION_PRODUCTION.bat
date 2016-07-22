@REM OFF
cd /d D:\API_AUTOMATION_PRODUCTION
del D:\API_AUTOMATION_PRODUCTION\Results_Production.csv

cd /d D:\jakarta-jmeter-2.3.4\bin\
java -jar ApacheJMeter.jar -n -t D:\PRODUCTION_ADMIN_API.jmx

cd /d D:\API_AUTOMATION_PRODUCTION
ruby api_email_production.rb

echo today is %DATE% %TIME% 