# CO₂ Emission Calculator

This tool calculates the CO₂ emissions for a trip between two cities using a specified transportation method.

## Setup

1. **Install Maven**: Ensure Maven is installed on your system.
2. **Set API Token**: Set your OpenRouteService API token as an environment variable:

### MAC OS
   ```bash
   export ORS_TOKEN="your_api_token_here"
   ```
### Windows (Command Prompt)
set ORS_TOKEN="your_api_key_here" 
### Windows (PowerShell)
$env:ORS_TOKEN="your_api_key_here"

## The application can be run using Makefile or by running the Maven commands:

### Running with Makefile (Recommended)
1. Clean
   ````bash
   make clean
   ````
2. Build & Compile
   ````bash
   make build
   ````
3. Run Unit Tests
   ````bash
   make test
   ````
4. Run the Application
   ````bash
   make run
   ````
You will be prompted to enter the start city, end city, and transportation method interactively.
![image](https://github.com/user-attachments/assets/3c199eae-352c-4299-8649-563d29017428)

### Clean and build using maven commands
1. Clean and Compile 
   ````bash 
   mvn clean compile
   ````
2. Run Tests
   ````bash
   mvn test
   ````
3. Package the Application
   ````bash
   mvn clean package
   ````

Run the JAR file to ensure everything works as expected
   ````bash
java -jar target/co2-calculator-1.0-SNAPSHOT.jar --start "Frankfurt" --end "Munich" --transportation-method bus-default
````

You should see output like:
````nginx
Your trip caused 68.6kg of CO2-equivalent.
````

