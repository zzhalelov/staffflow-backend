package kz.zzhalelov.staffflow.server.regulatoryIndicator;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Table(name = "regulatory_indicators")
@Entity
@Schema(description = "Информация о регламентированных показателях")
public class RegulatoryIndicator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор рег.показателя", example = "1")
    private Long id;

    @Schema(description = "Дата", example = "2025-01-01")
    private LocalDate date;

    @Schema(description = "Значение МЗП", example = "85000.00")
    @Column(name = "mzp_value")
    private BigDecimal mzpValue;

    @Schema(description = "Значение МРП", example = "3932")
    @Column(name = "mrp_value")
    private BigDecimal mrpValue;

    @Schema(description = "Описание", example = "МРП и МЗП на 2025 год")
    private String description;
}