package kz.zzhalelov.staffflow.server.event;

import kz.zzhalelov.staffflow.server.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContractSignedEventListener {
    private final EmailService emailService;

    @Async
    @EventListener
    public void onContractSigned(ContractSignedEvent event) {
        try {
            log.info("Отправляем письмо сотруднику: {}", event.getEmail());
            emailService.sendContractSignedEmail(event.getEmail(), event.getEmployeeName(), event.getContractId());
        } catch (Exception e) {
            log.error("ОШИБКА ОТПРАВКИ EMAIL (ignored)", e);
        }
    }
}