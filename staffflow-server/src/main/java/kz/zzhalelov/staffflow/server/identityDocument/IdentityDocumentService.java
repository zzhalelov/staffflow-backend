package kz.zzhalelov.staffflow.server.identityDocument;

import java.util.List;

public interface IdentityDocumentService {
    IdentityDocument create(IdentityDocument document);

    List<IdentityDocument> findAll();

    IdentityDocument findById(long id);

    IdentityDocument update(long id, IdentityDocument updated);

    void delete(long id);
}