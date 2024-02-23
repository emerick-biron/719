import json
import os
import sys

import requests

KONG_ADMIN_URL = os.getenv("KONG_ADMIN_URL")

if not KONG_ADMIN_URL:
    raise ValueError("KONG_ADMIN_URL must be set")


def main():
    with open("./create-service-bodies.json") as f:
        create_service_bodies = json.load(f)

    with open("./create-route-bodies.json") as f:
        create_route_bodies = json.load(f)

    for service, body in create_service_bodies.items():
        resp = requests.post(f"{KONG_ADMIN_URL}/services", json=body)
        print(f"Created service for {service}. Status code {resp.status_code}")
        service_id = resp.json()["id"]
        create_route_bodies[service]["service"]["id"] = service_id

    for service, body in create_route_bodies.items():
        resp = requests.post(f"{KONG_ADMIN_URL}/routes", json=body)
        print(f"Created route for {service}. Status code {resp.status_code}")

    sys.exit(0)


if __name__ == '__main__':
    main()
