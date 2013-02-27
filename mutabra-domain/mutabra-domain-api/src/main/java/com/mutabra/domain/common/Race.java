package com.mutabra.domain.common;

import com.mutabra.domain.BaseEntity;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Race extends BaseEntity {

    String getCode();

    int getHealth();

    void setHealth(int health);

    int getMentalPower();

    void setMentalPower(int mentalPower);

    List<String> getCards();
}
