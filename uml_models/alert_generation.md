# Alert Generation System  

This class diagram models the alert generation subsystem.  
Its purpose is to evaluate patient-related data, compare incoming values against personalized threshold rules, generate alerts when abnormal condition are detected, and route those alerts to medical staff for attention. 

'Patient' represents the patient whose health condition is being monitored. Each patient is associated with one or more `ThresholdRule` objects, allowing alert conditions to be personalized instead of using one global rule for every patient. This supports realistic hospital monitoring, where different patients may require different safe ranges depending on their condition.  

`AlertGenerator` is the central class in this subsystem. It evaluates the patient’s data against the assigned threshold rules. When a rule is violated, it creates an `Alert` object containing the patient identifier, the detected condition, and the timestamp of the event. This keeps the detection logic separate from the representation of the alert itself.

`AlertManager` is responsible for dispatching generated alerts to medical staff. Separating this responsibility from `AlertGenerator` improves modularity and makes future extensions easier, such as sending alerts to a dashboard, logging service, pager system, or mobile application. `Alert` acts as the message object passed between detection and notification.

Overall, this design emphasizes modularity, patient-specific monitoring, and clear class responsibilities. It reflects the safety-critical nature of the CHMS by ensuring that abnormal patient conditions can be detected and routed in a structured and traceable way.