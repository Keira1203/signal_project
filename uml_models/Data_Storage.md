# Data Storage System

This class diagram shows the subsystem of data storing. The main purpose is to store the patient data and organize, and set a getter to monitor or analyze the data. And to manage the accessibility and holding term too. This structure is separating all function and let to module and let easy to protect.

`DataStorage` is the central class of this subsystem. Saving the patient data and adding a new value, set a getter of the patient data in certain term. `Patient` groups the stored records for a single patient, while `PatientRecord` represents one individual measurement. Each record includes version control.

`DataRetriever` is a class to manage the kind of person to access the past data. Not accessing directly but using certain class makes easier to make hither quality search or extending logic.

`AccessControl` is a privacy, security protecting class. It will decide the user can access or no to the data. `RetentionPolicy` is holding a rule to delate old data. 

Overall, this design stores the patient data safely and protect it.