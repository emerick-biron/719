import os
import sys

import requests

KONG_ADMIN_URL = os.getenv("KONG_ADMIN_URL")

if not KONG_ADMIN_URL:
    raise ValueError("KONG_ADMIN_URL must be set")

create_service_bodies = {
    "eggs": {
        "protocol": "http",
        "host": "eggs-app",
        "port": 8080,
        "path": "/eggs",
        "enabled": True
    },
    "heroes": {
        "protocol": "http",
        "host": "heroes-app",
        "port": 8080,
        "path": "/heroes",
        "enabled": True
    },
    "incubators": {
        "protocol": "http",
        "host": "incubators-app",
        "port": 8080,
        "path": "/incubators",
        "enabled": True
    },
    "inventory": {
        "protocol": "http",
        "host": "inventory-app",
        "port": 8080,
        "path": "/inventory",
        "enabled": True
    },
    "monsters": {
        "protocol": "http",
        "host": "monsters-app",
        "port": 8080,
        "path": "/monsters",
        "enabled": True
    },
    "shop": {
        "protocol": "http",
        "host": "shop-app",
        "port": 8080,
        "path": "/shop",
        "enabled": True
    },
    "storage": {
        "protocol": "http",
        "host": "storage-app",
        "port": 8080,
        "path": "/storage",
        "enabled": True
    },
}

create_route_bodies = {
    "eggs": {
        "protocols": [
            "http",
        ],
        "paths": [
            "/eggs"
        ],
        "preserve_host": True,
        "service": {
            "id": ""
        }
    },
    "heroes": {
        "protocols": [
            "http",
        ],
        "paths": [
            "/heroes"
        ],
        "preserve_host": True,
        "service": {
            "id": ""
        }
    },
    "incubators": {
        "protocols": [
            "http",
        ],
        "paths": [
            "/incubators"
        ],
        "preserve_host": True,
        "service": {
            "id": ""
        }
    },
    "inventory": {
        "protocols": [
            "http",
        ],
        "paths": [
            "/inventory"
        ],
        "preserve_host": True,
        "service": {
            "id": ""
        }
    },
    "monsters": {
        "protocols": [
            "http",
        ],
        "paths": [
            "/monsters"
        ],
        "preserve_host": True,
        "service": {
            "id": ""
        }
    },
    "shop": {
        "protocols": [
            "http",
        ],
        "paths": [
            "/shop"
        ],
        "preserve_host": True,
        "service": {
            "id": ""
        }
    },
    "storage": {
        "protocols": [
            "http",
        ],
        "paths": [
            "/storage"
        ],
        "preserve_host": True,
        "service": {
            "id": ""
        }
    },
}

for service, body in create_service_bodies.items():
    resp = requests.post(f"{KONG_ADMIN_URL}/services", json=body)
    print(f"Created service for {service}. Status code {resp.status_code}")
    service_id = resp.json()["id"]
    create_route_bodies[service]["service"]["id"] = service_id

for service, body in create_route_bodies.items():
    resp = requests.post(f"{KONG_ADMIN_URL}/routes", json=body)
    print(f"Created route for {service}. Status code {resp.status_code}")

sys.exit(0)
