package kz.zzhalelov.staffflow.server.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ContractSignedEvent extends ApplicationEvent {
    private final String email;
    private final String employeeName;
    private final Long contractId;

    public ContractSignedEvent(Object source, String email, String employeeName, Long contractId) {
        super(source);
        this.email = email;
        this.employeeName = employeeName;
        this.contractId = contractId;
    }
}