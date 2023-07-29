package org.lecture;

import java.util.List;

public interface AbstractRepository {
    List<Account> all() throws Exception;

    Account get(String id) throws Exception;

    void create(Account account) throws Exception;

    void update(Account account) throws Exception;

    void delete(String id) throws Exception;
}
