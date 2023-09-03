from django.db import models

class Student(models.Model):
    """Student model."""

    id = models.UUIDField(editable=False, unique=True, blank=True, null=True)
    mnr = models.AutoField(primary_key=True)
    # TODO: implement

    def save(self, *args, **kwargs):
        if not self.id:
            import uuid
            self.id = uuid.uuid4()
        super(Student, self).save(*args, **kwargs)
