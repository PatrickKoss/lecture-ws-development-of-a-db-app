"""Example how to build a repository around a cursor."""
import abc
import dataclasses
import sqlite3
import uuid
from datetime import datetime
from typing import Union, List


class AbstractRepository(abc.ABC):
    """Interface for defining a repository."""

    @abc.abstractmethod
    def all(self) -> List[object]:
        """Get all entries of the database table."""
        raise NotImplementedError

    @abc.abstractmethod
    def get(self, id: Union[str | int]) -> object:
        """Get a single database entry based on id."""
        raise NotImplementedError

    @abc.abstractmethod
    def create(self, obj: object):
        """Create a new database entry."""
        raise NotImplementedError

    @abc.abstractmethod
    def update(self, obj: object):
        """Update an existing database entry."""
        raise NotImplementedError

    @abc.abstractmethod
    def delete(self, id: Union[str | int]):
        """Delete an existing database entry."""
        raise NotImplementedError


@dataclasses.dataclass
class Account(object):
    """Dataclass to wrap account data."""

    id: str
    username: str
    password: str
    email: str
    created_on: datetime


class AccountRepository(AbstractRepository):
    """Account repository for crud database operations."""

    def __init__(self):
        """Init."""
        self.con = sqlite3.Connection("accounts.db")
        self.cur = self.con.cursor()

    def get(self, id: Union[str | int]) -> Account:
        """Get a single account."""
        # keep in mind sql injection is a thing.
        # Avoid: self.cur.execute(f"select * from accounts where user_id={id}")
        res = self.cur.execute(
            "select user_id, username, password, email, created_on from accounts where user_id=:id",
            {"id": id},
        ).fetchone()

        return Account(
            res[0],
            res[1],
            res[2],
            res[3],
            datetime.strptime(res[4], "%Y-%m-%dT%H:%M:%SZ"),
        )

    def create(self, account: Account):
        """Create a new account."""
        self.cur.execute(
            """
        INSERT INTO accounts (user_id, username, password, email, created_on)
        VALUES (:id, :username, :password, :email, :created_on);
        """,
            {
                "id": account.id,
                "username": account.username,
                "password": account.password,
                "email": account.email,
                "created_on": account.created_on.strftime("%Y-%m-%dT%H:%M:%SZ"),
            },
        )
        self.con.commit()

    def update(self, account: Account):
        """Update an account."""
        self.cur.execute(
            """
        UPDATE accounts 
        SET username=:username, password=:password, email=:email, created_on=:created_on
        WHERE user_id=:id
        """,
            {
                "id": account.id,
                "username": account.username,
                "password": account.password,
                "email": account.email,
                "created_on": account.created_on.strftime("%Y-%m-%dT%H:%M:%SZ"),
            },
        )
        self.con.commit()

    def delete(self, id: Union[str | int]):
        """Delete an account."""
        self.cur.execute(
            "delete from accounts where user_id=:id",
            {"id": id},
        )
        self.con.commit()

    def all(self) -> List[Account]:
        """Get all accounts."""
        result = self.cur.execute(
            "select user_id, username, password, email, created_on from accounts",
        ).fetchall()

        return [
            Account(
                res[0],
                res[1],
                res[2],
                res[3],
                datetime.strptime(res[4], "%Y-%m-%dT%H:%M:%SZ"),
            )
            for res in result
        ]


if __name__ == "__main__":
    """Execute some functions."""
    # create repository
    repo = AccountRepository()

    # first check all db entries
    accounts = repo.all()
    print("all accounts: ", accounts)
    assert len(accounts) == 1
    assert accounts[0].username == "test"

    # get a single db entry
    account = repo.get(accounts[0].id)
    print("single account: ", account)
    assert account.username == "test"
    assert account.email == "test@test.com"

    # create new account
    new_id = str(uuid.uuid4())
    new_account = Account(
        new_id, "test2", "test2", "test2@test.com", account.created_on
    )
    repo.create(new_account)

    # update new account
    new_account.username = "test3"
    repo.update(new_account)

    # check new account
    account = repo.get(new_id)
    print("new account after update", account)
    assert account.username == "test3"

    # delete newly created account again
    repo.delete(new_id)

    # check accounts again
    accounts = repo.all()
    print("all accounts at the end: ", accounts)
    assert len(accounts) == 1
