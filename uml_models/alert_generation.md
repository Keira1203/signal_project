# Alert Generation System  

This class diagram models is the subsystem of alert generation. Is to evaluate the patient data and if the data get above the threshold, the alert will be made and notified to the staff.

'Patient' shows all patient to be evaluate. Each patient relates to  `ThresholdRule` objects, and not same but every patient has their own alert condition. It is a better way to manage patient alert.  

`AlertGenerator` is the central class in this subsystem. Receiving the patients data and compare with a threshold rule. If the rule is broken, it will generate `Alert` object containing the patient identifier, the detected condition, and the timestamp of the event. This keeps the detection logic separate from the representation of the alert itself.

`AlertManager` is sending generated alert to a stuff. Separating `AlertGenerator` improves modularity and makes future extensions easier. `Alert` acts as the message object passed between detection and notification.

Overall, this design emphasizes modularity, patient-specific monitoring, and clear class responsibilities. It reflects the safety-critical nature of the CHMS by ensuring that abnormal patient conditions can be detected and routed in a structured and traceable way.