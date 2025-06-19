# BigLittleMatchMaking


This project is a Java-based application that automates the process of matching "Bigs" and "Littles" based on shared attributes such as interests, majors, and preferences. It is designed when I was the Vice President of the class board to combat the tideous task of matching Junior and Freshman as Bigs and Littles. 

## Features

- Reads Big and Little profiles from structured text or CSV files
- Matches Littles to Bigs using a compatibility scoring algorithm
- Outputs final matches and unmatched participants
- Allows customization of matchmaking logic
- Writes results to files for easy review and use

## Technologies Used

- Java (JDK 17+)
- OOP design principles
- File I/O and data parsing
- Custom matching algorithm

## File Overview

| File | Description |
|------|-------------|
| `Big.java` | Represents a Big with attributes and preferences |
| `Little.java` | Represents a Little with similar structure |
| `Person.java` | A parent class with shared fields for Bigs and Littles |
| `Matcher.java` | Contains logic to compute compatibility scores and assign matches |
| `Maker.java` | Coordinates reading input data and triggering the matching process |
| `Curator.java` | Handles parsing and organizing user data |
| `SendingToFile.java` | Handles writing matched and unmatched results to external files |

## Input Format

Each Big and Little has a profile containing:

- Name
- Email
- Major
- Interests (comma-separated)
- Campus preferences
- Team involvement

Example format:
