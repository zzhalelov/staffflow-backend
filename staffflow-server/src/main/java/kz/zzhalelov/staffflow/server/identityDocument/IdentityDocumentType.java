package kz.zzhalelov.staffflow.server.identityDocument;

public enum IdentityDocumentType {
    RESIDENT_CARD("Вид на жительство"),
    FOREIGN_PASSPORT("Заграничный паспорт"),
    IDENTITY_CARD("Удостоверение личности"),
    BIRTH_CERTIFICATE("Свидетельство о рождении"),
    DRIVER_LICENSE("Водительское удостоверение"),
    MILITARY_ID("Военный билет");

    IdentityDocumentType(String idType) {
    }
}