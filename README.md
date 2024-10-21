# Contact Duplicate Finder

## Description

The Contact Duplicate Finder application is a Spring Boot service that identifies potential duplicate contacts from a provided CSV file containing contact information. It analyses fields such as names, email addresses, postal codes, and addresses to determine possible matches and their accuracy scores. This tool helps manage and clean contact data by highlighting duplicates for further review.


## Table of contents

1. [Endpoints](#endpoints)
2. [Response Format](#response-format)
3. [Steps to Execute in Postman](#steps-to-execute-in-postman)
4. [Requirements](#requirements)
5. [Running the Application](#running-the-application)


## Endpoints

### 1. Upload CSV File

- **Endpoint**: `POST /contacts/upload-csv`
- **Description**: This endpoint allows you to upload a CSV file containing contact information. The application will process the file and return a list of potential duplicate contacts with their accuracy scores.
- **Request Format**:
    - **Content-Type**: `multipart/form-data`
    - **Body**:
        - `file`: The CSV file containing contact data.

### Response Format

On a successful upload, the response will contain a list of potential matches:

```json
[
  {
    "sourceContactId": "1",
    "matchedContactId": "501",
    "accuracy": "High"
  },
  ...
]
```

## Steps to Execute in Postman

1. **Open Postman**.
2. **Create a new request**:
    - Set the request type to `POST`.
    - Enter the URL: `http://localhost:8080/contacts/upload-csv` (ensure the port matches your local Spring Boot server).
3. **Configure the request**:
    - Go to the **Body** tab.
    - Select **form-data**.
    - Add a new key called `file`, set its type to `File`, and choose your CSV file.
    - The test .csv file is located in the `src/main/resources` directory.
4. **Send the request** by clicking the **Send** button.
5. **View the response** in the Postman console. The application will return potential duplicate contacts with accuracy scores.

## Requirements

- Java 23
- Spring Boot
- Maven

## Running the Application

1. Clone the repository to your local machine.
2. Navigate to the project directory in your terminal.
3. Run the following command to start the application:
   ```bash
   mvn spring-boot:run
   ```
4. Once the application is running, use Postman to interact with the API as described above.

## Authors

- [@Juan Ignacio Caprioli (ChanoChoca)](https://github.com/ChanoChoca)
