# Problem Statement & Scope

## Author Information
- **Name:** vipul malviya
- **Registration Number:** 24BEC10128

## 1. Problem Statement
Small and medium-scale retail operations frequently encounter inventory discrepancies, order processing latencies, and unauthorized record alterations when relying on unstandardized manual logs. This project implements a robust Enterprise Retail Order Management System (EROMS) designed to coordinate inventory control, authentication, atomic order placement, and persistent file-backed data storage without relying on heavy external database dependencies.

## 2. Scope of the Project
- User management and credential validation.
- Inventory tracking, automated stock decrement, and threshold alerts.
- Transactional order creation, tracking, and cancellation.
- Aggregate operational reporting and business analytics.
- Disk-level persistence using serializable binary/file storage.

## 3. Target Users
- **System Administrators / Inventory Managers:** Manage product listings, restock quantities, and inspect analytical reports.
- **Customers / Sales Staff:** Browse catalogs, place atomic multi-item orders, and inspect transaction histories.

## 4. High-Level Features
- Credential hashing and session-level authentication.
- Generic, thread-safe object persistence layer.
- Transaction validation preventing orders with insufficient inventory.
- Analytics engine calculating revenue, top-selling items, and stock health.