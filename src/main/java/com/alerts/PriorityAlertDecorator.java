package com.alerts;

public class PriorityAlertDecorator extends AlertDecorator{
    private String priorityLevel;

    public PriorityAlertDecorator(Alert alert, String priorityLevel){
        super(alert);
        this.priorityLevel=priorityLevel;
    }

    @Override
    public String getCondition(){
        return "[" + priorityLevel +" PRIORITY]" + super.getCondition();
    }
}
