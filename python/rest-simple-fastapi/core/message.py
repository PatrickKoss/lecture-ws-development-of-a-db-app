from pydantic import BaseModel, Field


class Message(BaseModel):
    message: str | None = Field(
        None,
        min_length=1,
        max_length=200,
        example="Success",
        description="Message to describe what happens",
    )
