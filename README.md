# CO₂ Emission Calculator

This tool calculates the CO₂ emissions for a trip between two cities using a specified transportation method.

## Setup

1. **Install Maven**: Ensure Maven is installed on your system.
2. **Set API Token**: Set your OpenRouteService API token as an environment variable:
   ```bash
   export ORS_TOKEN="your_api_token_here"

set ORS_TOKEN="your_api_key_here"  # Windows (Command Prompt)
$env:ORS_TOKEN="your_api_key_here"  # Windows (PowerShell)

### The application can be run using Makefile or Manually by running the below commands:

#### Through Makefile


#### Clean and build using maven commands
1. Clean and Compile 
   ````bash 
   mvn clean compile
   ````
2. Run Tests
   ````bash
   mvn test
   ````
4. Package the Application
   ````bash
   mvn clean package
   ````

Run the JAR file to ensure everything works as expected
   ````bash
java -jar target/co2-calculator-1.0-SNAPSHOT.jar --start "Mysore" --end "Bangalore" --transportation-method bus-default
````

You should see output like:

Your trip caused 3.8kg of CO2-equivalent.
