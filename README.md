[![Java](https://img.shields.io/badge/Java-21-%23ED8B00.svg?style=flat&logo=openjdk&logoColor=white)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-success?logo=springboot)]()
[![CI](https://github.com/zzhalelov/staffflow-backend/actions/workflows/ci-cd.yml/badge.svg)]()
[![codecov](https://codecov.io/gh/zzhalelov/staffflow-backend/graph/badge.svg?token=EJWHMEHWI2)](https://codecov.io/gh/zzhalelov/staffflow-backend)

# 🧾 StaffFlow Server — Technical Specification

## 📘 Overview
**StaffFlow Server** — это серверная часть корпоративной системы учёта сотрудников, организаций, табелей рабочего времени и начислений заработной платы.  
Проект реализован на **Spring Boot (Java 17+)** и использует **JPA (Hibernate)** для работы с базой данных.

---

## 🧱 Архитектура проекта

### Основные принципы
- Модульная архитектура (по доменам: `organization`, `employee`, `timesheet`, `position`, `payroll`).
- Разделение на **entity**, **repository**, **service**, **controller**, **dto**, **mapper**.
- REST API с JSON обменом.
- Использование Lombok (`@Data`, `@FieldDefaults`, `@RequiredArgsConstructor`) для сокращения шаблонного кода.
- DTO-модели для возврата только необходимых данных.
- Историчность для важных сущностей (например, табели и виды начислений).

---

## ⚙️ Технологический стек

| Компонент | Используемое решение       |
|------------|----------------------------|
| Язык | Java 17+                   |
| Фреймворк | Spring Boot                |
| ORM | Hibernate / JPA            |
| База данных | PostgreSQL                 |
| Сборка | Maven                      |
| Документация | Markdown / OpenAPI |
| Логирование | SLF4J / Logback            |
| Тестирование | JUnit 5 / Mockito |

---


