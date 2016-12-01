Prerequisites:

1. Java 8
2. Apache Maven 3.3.3
3. MySql

About the project:

Two http endpoints are provided which accept JSON base64 encoded binary data 

    <host>/v1/diff/<ID>/left
    <host>/v1/diff/<ID>/right

Provided data can be diff-ed and the results will be available on a third end point

    <host>/v1/diff/<ID>

The results will provide the following info in JSON format

    If equal return that
    If not of equal size just return that
    If of same size provide insight in where the diffs are

Input JSON format taken:

    {
        "firstName" :   "cmF2aUxlZnQ=",
        "lastName"  :   "YWdyYXdhbExlZnQ="
    }

Here, data in the fields firstName and lastName is base64 encoded.

Output/Response is also in JSON format

Steps to run the project using command line:
    
    Navigate to project directory structure and then in to myapp folder in it.
    Run the command "mvn clean install"

Test cases details:

    Unit test cases:
        DifferenceServiceImplTest.java

    Integration test cases:
        DifferenceContollerIntegrationTest.java

Database details:
    
    Below table is required under db name "myapp" :
    
    CREATE TABLE `diff_data` (
      `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
      `user_id` INT(10) DEFAULT NULL,
      `left_data` VARCHAR(2000) DEFAULT NULL,
      `right_data` VARCHAR(2000) DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=MYISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
