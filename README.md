# Enterprise Retail Order Management System (EROMS)

## Student Details
- **Name:** vipul malviya
- **Registration Number:** 24BEC10128

## Overview
EROMS is a production-style Java desktop/console application designed to fulfill end-to-end retail order lifecycle workflows. Built using clean Object-Oriented principles, multi-tier architectural decoupling, and custom runtime exception hierarchies.

## Features
- **User Authentication:** Safe role verification and credential checks.
- **Inventory Engine:** Real-time stock reservation, replenishment, and shortage alerts.
- **Transactional Ordering:** Automated calculations, validation, and status tracking.
- **Analytics & Reporting:** Live metrics on revenue, pending orders, and restock status.
- **Persistent Engine:** Localized serialization preserving state across restarts.

## Non-Functional Requirements Addressed
- **Reliability:** Atomic state validation; orders fail gracefully without data corruption.
- **Maintainability:** Clear separation between models, services, repositories, and utilities.
- **Security:** Sanitized inputs and decoupled role-based operations.
- **Usability:** Streamlined CLI interface with defensive prompt validation.

## Prerequisites
- JDK 17 or higher
- Apache Maven 3.8+

## Build and Run Instructions

### 1. Compile and Package
```bash
mvn clean compile