"""Urls."""

from django.conf import settings
from django.conf.urls.static import static
from django.contrib import admin
from django.urls import path, re_path
from drf_yasg import openapi
from drf_yasg.views import get_schema_view
from rest_framework import permissions

from health import views as health_views
from student import views as student_views

schema_view = get_schema_view(
    openapi.Info(
        title="Rest Simple",
        default_version="v1",
        description="This api is an example in python how to build a simple rest api with database connection.",
        terms_of_service="https://www.google.com/policies/terms/",
        contact=openapi.Contact(email="lecture@example.com"),
        license=openapi.License(name="MIT License"),
    ),
    public=True,
    permission_classes=(permissions.AllowAny,),
)
urlpatterns = static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)
urlpatterns += [
    path("students", student_views.StudentGetCreateView.as_view(), name="students"),
    path("students/<uuid:id>", student_views.StudentUpdateDeleteView.as_view(), name="students_single"),
]
urlpatterns += [
    re_path(
        r"^swagger(?P<format>\.json|\.yaml)$",
        schema_view.without_ui(cache_timeout=0),
        name="schema-json",
    ),
    re_path(
        r"^swagger/$",
        schema_view.with_ui("swagger", cache_timeout=0),
        name="schema-swagger-ui",
    ),
    re_path(
        r"^redoc/$", schema_view.with_ui("redoc", cache_timeout=0), name="schema-redoc"
    ),
    path("admin/", admin.site.urls),
    path("", health_views.Health.as_view()),
]
