package com.eroms.model;

import java.io.Serializable;

public enum OrderStatus implements Serializable {
    PENDING,
    CONFIRMED,
    SHIPPED,
    CANCELLED
}