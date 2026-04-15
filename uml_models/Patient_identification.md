# Patient Identification System  

This class diagram shows a subsystem of patient identification system. This subsystem will identify the data before other system uses the data and preventing to link the wrong data to the system. 

'PatientIdentifier' is the main logic. Get the patient ID and check wether the data matches with the hospitals system. But not accessing directory but stands as a access point to the patient data we will need the 'PatientRepository' from dividing it, it will protect the data more.

'HospitalPatient' is a system to record the hospital patients. This class will hold patient ID, simulator patients Id, patients name and other. It will connect the correct patients ID.

'MatchResult' will be used to match result. It will check wether it matches, or something detected, and holds the message to explain the result. If the patient matches, the certain 'HospitalPatient' object will be included as well.

'IdentityManager' will manage the patients data. 

Overall, this is important to protect patients data safe and manageable.