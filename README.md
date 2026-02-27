# neo4j-network-management-api
## Project Overview
*This project is still in an early phase*
This project is a small learning-oriented application built to explore and experiment with **Neo4j** and graph databases.

The primary purpose of the project is to:

- Set up and run Neo4j using Docker
- Download and work with Neo4j’s official example data dump (e.g. the *network-management* dataset)
- Practice ingesting, querying, and interacting with graph data
- Build familiarity with Kotlin as an application layer interacting with Neo4j
- Establish a clean, reproducible Docker-based development workflow

The project is not intended to be a production system. Instead, it serves as a structured sandbox for:

- Understanding how graph databases differ from relational databases
- Experimenting with nodes, relationships, labels, and Cypher queries
- Learning how to containerize an application alongside a database
- Developing clean project structure and reproducible environments

All infrastructure is managed via Docker Compose, making it easy to clone the repository and run the project without manual database installation.

This repository exists primarily as a learning exercise and a demonstration of working with Neo4j in a modern containerized setup.

## Running the Project with Docker

This project is designed to run with Docker Compose in just a few steps.

---

## 1. Clone the Repository

```bash
git clone https://github.com/<your-username>/neo4j-network-management-api.git
cd neo4j-network-management-api
```

---

## 2. Start Neo4j (Background)

```bash
docker compose up -d neo4j
```

Neo4j will be available at:

- Browser UI: http://localhost:7474  
- Bolt endpoint: bolt://localhost:7687  

---

## 3. Run the Application

Run the application as a one-off CLI execution:

```bash
docker compose run --rm app
```

The `--rm` flag ensures the temporary container is removed after execution.  
Persistent data (Neo4j database and application state) is stored in Docker volumes and will remain available across runs and system reboots.

---

## 4. (Optional) Pass Arguments to the Application

At the moment the application doesn't support CLI arguments, however in the future it might. If so, simply pass them after `app`:

```bash
docker compose run --rm app --example-flag
```

Currently, no arguments are required.

---

# Clean Shutdown and Full Removal

## Stop Containers (Keep Data)

```bash
docker compose down
```

## Stop Containers and Remove All Data (Volumes)

This removes all persisted databases and application state:

```bash
docker compose down -v
```

## Remove Application Image (Optional)

```bash
docker image rm neo4j-network-management-api-app
```

If needed, list images first:

```bash
docker images
```

Then remove by name or image ID:

```bash
docker image rm <image_name_or_id>
```

## Remove Neo4j Image (Optional)

```bash
docker image rm neo4j:5-community
```

---

# Remove the Cloned Repository

From the parent directory:

```bash
cd ..
rm -rf neo4j-network-management-api
```

---

After these steps, all containers, volumes, images (if removed), and repository files related to this project will be deleted.
