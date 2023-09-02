"""Urls."""

from account_permissions import views as account_permission_views
from accounts import views as account_views
from django.conf import settings
from django.conf.urls.static import static
from django.contrib import admin
from django.urls import path, re_path
from drf_yasg import openapi
from drf_yasg.views import get_schema_view
from health import views as health_views
from permissions import views as permission_views
from rest_framework import permissions

schema_view = get_schema_view(
    openapi.Info(
        title="Backend API",
        default_version="v1",
        description="Test description",
        terms_of_service="https://www.google.com/policies/terms/",
        contact=openapi.Contact(email="contact@snippets.local"),
        license=openapi.License(name="MIT License"),
    ),
    public=True,
    permission_classes=(permissions.AllowAny,),
)
urlpatterns = static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)
urlpatterns += [
    path("accounts", account_views.Account.as_view(), name="account"),
    path(
        "accounts/<str:username>",
        account_views.AccountSingle.as_view(),
        name="account_single",
    ),
]
urlpatterns += [
    path("permissions", permission_views.Permission.as_view(), name="permission"),
    path(
        "permissions/<str:permission>",
        permission_views.PermissionSingle.as_view(),
        name="permission_single",
    ),
]
urlpatterns += [
    path(
        "accounts/<str:username>/permissions",
        account_permission_views.AccountPermission.as_view(),
        name="account_permission",
    ),
    path(
        "accounts/<str:username>/permissions/<str:permission>",
        account_permission_views.AccountPermissionSingle.as_view(),
        name="account_permission_single",
    ),
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
