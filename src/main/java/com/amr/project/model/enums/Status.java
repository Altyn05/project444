package com.amr.project.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {

    @JsonProperty("Принят")
    START,
    @JsonProperty("Оплачен")
    PAID,
    @JsonProperty("Отправлен")
    SENT,
    @JsonProperty("Доставлен")
    DELIVERED,
    @JsonProperty("Выполнен")
    COMPLETE
}
