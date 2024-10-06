from pydantic import BaseModel, EmailStr, Field


class StudentCreate(BaseModel):
    first_name: str | None = Field(
        None,
        min_length=1,
        max_length=200,
        example="John",
        description="The first name of the student",
        alias="firstName",
    )
    last_name: str = Field(
        ...,
        min_length=1,
        max_length=200,
        example="Doe",
        description="The last name of the student",
        alias="lastName",
    )
    email: EmailStr = Field(
        ...,
        example="john.doe@example.com",
        description="The email address of the student",
    )

    def dict(self, *args, **kwargs):
        original_dict = super().dict(*args, **kwargs)
        original_dict["name"] = original_dict.pop("first_name")
        return original_dict


class StudentUpdate(BaseModel):
    first_name: str | None = Field(
        None,
        min_length=1,
        max_length=200,
        example="John",
        description="The first name of the student",
        serialization_alias="firstName",
        alias="firstName",
    )
    last_name: str = Field(
        ...,
        min_length=1,
        max_length=200,
        example="Doe",
        description="The last name of the student",
        serialization_alias="lastName",
        alias="lastName",
    )
    email: EmailStr | None = Field(
        None,
        example="john.doe@example.com",
        description="The email address of the student",
    )

    def dict(self, *args, **kwargs):
        original_dict = super().dict(*args, **kwargs)
        original_dict["name"] = original_dict.pop("first_name")
        return original_dict


class StudentRead(BaseModel):
    id: str = Field(
        ..., example="1", description="The unique identifier of the student"
    )
    mnr: int = Field(..., ge=0, example=123, description="The student number")
    first_name: str | None = Field(
        None,
        min_length=1,
        max_length=200,
        example="John",
        description="The first name of the student",
        serialization_alias="firstName",
    )
    last_name: str = Field(
        ...,
        min_length=1,
        max_length=200,
        example="Doe",
        description="The last name of the student",
        serialization_alias="lastName",
    )
    email: EmailStr = Field(
        ...,
        example="john.doe@example.com",
        description="The email address of the student",
    )

    class Config:
        orm_mode = True
        allow_population_by_field_name = True
        from_attributes = True


class ListStudentResponse(BaseModel):
    students: list[StudentRead] = Field(
        ...,
        example=[],
    )
