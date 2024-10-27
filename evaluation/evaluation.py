import os
import random
import re
import uuid
from collections import defaultdict

import requests
import rstr
import yaml
from faker import Faker

fake = Faker()


def read_openapi_spec(file_path):
    with open(file_path, "r") as file:
        spec = yaml.safe_load(file)
    return spec


def group_routes_by_entity(spec):
    paths = spec.get("paths", {})
    entities = defaultdict(dict)
    for path, methods in paths.items():
        # Extract the base entity from the path
        entity = path.strip("/").split("/")[0]
        for method, details in methods.items():
            operation_id = details.get("operationId", method + path)
            entities[entity][method.upper() + " " + path] = details
    return entities


def generate_sample_data(schema, spec):
    def resolve_schema(ref):
        ref_path = ref.lstrip("#/").split("/")
        ref_schema = spec
        for part in ref_path:
            ref_schema = ref_schema.get(part)
        return ref_schema

    if "$ref" in schema:
        schema = resolve_schema(schema["$ref"])

    data = {}
    properties = schema.get("properties", {})
    required = schema.get("required", [])
    for prop, prop_schema in properties.items():
        if "$ref" in prop_schema:
            prop_schema = resolve_schema(prop_schema["$ref"])

        if prop_schema.get("type") == "string":
            if prop == "email":
                value = fake.email()
            elif prop_schema.get("format") == "uuid":
                value = str(uuid.uuid4())
            elif "pattern" in prop_schema:
                pattern = prop_schema["pattern"]
                value = rstr.xeger(pattern)
            elif prop_schema.get("maxLength"):
                max_length = prop_schema.get("maxLength")
                value = fake.text(max_nb_chars=max_length).strip()
            else:
                value = fake.word()
        elif prop_schema.get("type") == "integer":
            value = random.randint(0, 100)
        elif prop_schema.get("type") == "number":
            value = random.uniform(0, 100)
        elif prop_schema.get("type") == "boolean":
            value = random.choice([True, False])
        elif prop_schema.get("type") == "array":
            items_schema = prop_schema.get("items", {})
            value = [generate_sample_data(items_schema, spec)]
        elif prop_schema.get("type") == "object":
            value = generate_sample_data(prop_schema, spec)
        else:
            value = None

        if prop in required or value is not None:
            data[prop] = value
    return data


def find_response_schema(responses):
    for code in responses:
        if code.startswith("2"):
            content = responses[code].get("content", {})
            if "application/json" in content:
                return content["application/json"]["schema"]
    return {}


def find_entity_id_via_api(data, base_url, get_route):
    if isinstance(data, dict):
        for key, value in data.items():
            if isinstance(value, dict):
                entity_id = find_entity_id_via_api(value, base_url, get_route)
                if entity_id:
                    return entity_id
            else:
                # Extract the path from the tuple
                path = get_route[0]
                # Replace path parameters with the potential ID
                path = re.sub(r"\{.*?\}", str(value), path)
                url = base_url + path
                response = requests.get(url)
                if response.status_code == 200:
                    return value
    return None


def main():
    openapi_yaml_path = os.environ.get("OPENAPI_YAML_PATH", "openapi.yaml")
    spec = read_openapi_spec(openapi_yaml_path)
    base_url = spec["servers"][0]["url"]
    entities = group_routes_by_entity(spec)

    for entity, routes in entities.items():
        print(f"\nTesting entity: {entity}")
        # Identify CRUD operations
        create_route = None
        list_route = None
        update_route = None
        delete_route = None
        get_route = None

        for route, details in routes.items():
            method, path = route.split(" ", 1)
            if method == "POST":
                create_route = (path, details)
            elif method == "GET" and "{" not in path:
                list_route = (path, details)
            elif method == "PUT":
                update_route = (path, details)
            elif method == "DELETE":
                delete_route = (path, details)
            elif method == "GET" and "{" in path:
                get_route = (path, details)

        if not all([create_route, list_route, update_route, delete_route, get_route]):
            print(f"Skipping entity '{entity}' due to incomplete CRUD operations.")
            continue

        # Create entity
        path, details = create_route
        url = base_url + path
        request_body_schema = details["requestBody"]["content"]["application/json"][
            "schema"
        ]
        sample_data = generate_sample_data(request_body_schema, spec)
        print(f"Creating entity with data: {sample_data}")
        response = requests.post(url, json=sample_data)
        assert response.status_code == 201, f"Failed to create entity: {response.text}"
        created_entity = response.json()

        entity_id = find_entity_id_via_api(created_entity, base_url, get_route)
        assert entity_id, "Failed to find entity ID after creation."
        print(f"Entity created with ID: {entity_id}")

        # List entities
        path, details = list_route
        url = base_url + path
        response = requests.get(url)
        assert response.status_code == 200, f"Failed to list entities: {response.text}"
        list_response = response.json()
        # Assuming the list is under a property named 'items' or similar
        entity_list = next(
            (v for v in list_response.values() if isinstance(v, list)), []
        )
        assert len(entity_list) >= 1, "Entity not found in list after creation."
        print(f"Entity list contains {len(entity_list)} items after creation.")

        # Update entity
        path, details = update_route
        # Replace path parameters with actual IDs
        path = re.sub(r"\{.*?\}", str(entity_id), path)
        url = base_url + path
        update_body_schema = details["requestBody"]["content"]["application/json"][
            "schema"
        ]
        updated_data = generate_sample_data(update_body_schema, spec)
        print(f"Updating entity with data: {updated_data}")
        response = requests.put(url, json=updated_data)
        assert response.status_code == 200, f"Failed to update entity: {response.text}"

        # Delete entity
        path, details = delete_route
        path = re.sub(r"\{.*?\}", str(entity_id), path)
        url = base_url + path
        response = requests.delete(url)
        assert response.status_code == 204, f"Failed to delete entity: {response.text}"
        print("Entity deleted successfully.")

        # Verify deletion
        path, details = list_route
        url = base_url + path
        response = requests.get(url)
        assert response.status_code == 200, f"Failed to list entities: {response.text}"
        list_response = response.json()
        entity_list = next(
            (v for v in list_response.values() if isinstance(v, list)), []
        )
        assert len(entity_list) == 0, "Entity list is not empty after deletion."
        print(f"Entity list is empty after deletion.")


if __name__ == "__main__":
    main()

