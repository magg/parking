package com.github.magg.parking.domain;

import com.github.magg.parking.exception.ParkingException;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.github.magg.parking.exception.ParkingException.INVALID_PARKING_EXIT_DATE;

@Setter
@Getter
public class ParkingTicket {

    private BigDecimal totalAmount;
    private LocalDateTime issueTime;
    private LocalDateTime exitTime;
    private TicketStatus status;

    public ParkingTicket() {
        this.issueTime = LocalDateTime.now();
        this.status = TicketStatus.ISSUED;
    }

    public void setExitTime(LocalDateTime exitTime) {

        if (exitTime.isBefore(issueTime)){
            throw new ParkingException(INVALID_PARKING_EXIT_DATE);
        }
        this.exitTime = exitTime;
    }
}
